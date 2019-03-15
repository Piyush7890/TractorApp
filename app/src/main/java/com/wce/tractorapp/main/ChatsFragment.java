package com.wce.tractorapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wce.tractorapp.ChatListAdapter;
import com.wce.tractorapp.ConversationActivity;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.ChatPerson;

import org.parceler.Parcels;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ChatsFragment extends Fragment implements ChatListAdapter.Listener {

    RecyclerView chatList;
    private ChatListAdapter mFirebaseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_chats, container, false);
        chatList = view.findViewById(R.id.chat_list);
        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference messagesRef = mFirebaseDatabaseReference
                .child("ChatList")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirebaseRecyclerOptions<ChatPerson> options =
                new FirebaseRecyclerOptions.Builder<ChatPerson>()
                        .setQuery(messagesRef, ChatPerson.class)
                        .build();
        mFirebaseAdapter = new ChatListAdapter(options,this);
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));
        chatList.setAdapter(mFirebaseAdapter);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        if(mFirebaseAdapter!=null)
            mFirebaseAdapter.startListening();
    }


    @Override
    public void onPause() {
        super.onPause();
        if(mFirebaseAdapter!=null)
            mFirebaseAdapter.stopListening();
    }

    @Override
    public void onChatClicked(int pos) {
        Intent intent = new Intent(getContext(), ConversationActivity.class);
        intent.putExtra("ChatDetail", Parcels.wrap(mFirebaseAdapter.getItem(pos)));
        startActivity(intent);
    }
}
