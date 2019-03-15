package com.wce.tractorapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wce.tractorapp.model.Chat;
import com.wce.tractorapp.model.ChatPerson;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ConversationActivity extends ToolBarActivity {

    EditText mCompose;
    RecyclerView chats;
    ImageButton sendBtn;
    ImageView avatar;
    TextView title;

    ChatPerson detail;
    private ChatAdapter mFirebaseAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return  true;

        }
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mCompose = findViewById(R.id.compose_edit_text);
        sendBtn = findViewById(R.id.send_btn);
        avatar = findViewById(R.id.avatar);
        chats = findViewById(R.id.chats);
        chats.setVisibility(View.GONE);

        title = findViewById(R.id.name);
        if(getIntent()!=null)
        {
            detail = Parcels.unwrap(getIntent().getParcelableExtra("ChatDetail"));
            title.setText(detail.getName());
            if(detail.getAvatarUrl()!=null)
            Glide.with(this).load(detail.getAvatarUrl()).transition(new DrawableTransitionOptions().crossFade()).into(avatar);
        }
        mCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()) {
                    sendBtn.setEnabled(false);
                    sendBtn.setAlpha(0.7f);
                }

                else{
                    sendBtn.setAlpha(1f);
                    sendBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference messagesRef = mFirebaseDatabaseReference.child("ChatRooms");
        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String room_type_1 = user.getUid() + "_" + detail.getUid();
                final String room_type_2 = detail.getUid() + "_" + user.getUid();
                DatabaseReference reference;
                if(dataSnapshot.hasChild(room_type_1))
                {
                    reference =     messagesRef.child(room_type_1);
                }
                else if(dataSnapshot.hasChild(room_type_2))
                {
                    reference = messagesRef.child(room_type_2);
                }
                else {
                    reference =     messagesRef.child(room_type_1);

                }
                FirebaseRecyclerOptions<Chat> options =
                        new FirebaseRecyclerOptions.Builder<Chat>()
                                .setQuery(reference, Chat.class)
                                .build();

                mFirebaseAdapter = new ChatAdapter(options,user.getUid());
                final LinearLayoutManager mLinearLayoutManager  = new LinearLayoutManager(ConversationActivity.this);
                mLinearLayoutManager.setStackFromEnd(true);
                chats.setLayoutManager(mLinearLayoutManager);

                chats.setAdapter(mFirebaseAdapter);
                mFirebaseAdapter.startListening();
                chats.setVisibility(View.VISIBLE);
                mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onItemRangeInserted(int positionStart, int itemCount) {
                        super.onItemRangeInserted(positionStart, itemCount);
                        chats.setVisibility(View.VISIBLE);
                        int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                        int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                        if (lastVisiblePosition == -1 ||
                                (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                            chats.scrollToPosition(positionStart);
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                Date d = Calendar.getInstance().getTime();


                sendMessageToUser(new Chat(user.getUid(), detail.getUid(),dateFormat.format(d), mCompose.getText().toString() , detail.getHisAvatarUrl(), detail.getAvatarUrl()));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mFirebaseAdapter!=null)
            mFirebaseAdapter.startListening();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mFirebaseAdapter!=null)
            mFirebaseAdapter.stopListening();

    }

    private void sendMessageToUser(final Chat chat) {
        final String room_type_1 = chat.getSenderUid() + "_" + chat.getReceiverUid();
        final String room_type_2 = chat.getReceiverUid() + "_" + chat.getSenderUid();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final String ARG_CHAT_ROOMS = "ChatRooms";
        databaseReference.child(ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long millis = System.currentTimeMillis();


                if (dataSnapshot.hasChild(room_type_1)) {
                    databaseReference.child(ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(millis)).setValue(chat);
                } else if (dataSnapshot.hasChild(room_type_2)) {
                    databaseReference.child(ARG_CHAT_ROOMS).child(room_type_2).child(String.valueOf(millis)).setValue(chat);
                } else {
                    databaseReference.child(ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(millis)).setValue(chat);
                }
                mCompose.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected boolean isBackEnabled() {
        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_conversation;
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mCompose.setText(result.get(0));
                    mCompose.setSelection(mCompose.getText().length());

                }
                break;
        }
    }
}