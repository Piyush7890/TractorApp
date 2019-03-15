package com.wce.tractorapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.wce.tractorapp.model.ChatPerson;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatListAdapter extends FirebaseRecyclerAdapter<ChatPerson, ChatListAdapter.ChatListViewHolder> {


    private final Listener listener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChatListAdapter(@NonNull FirebaseRecyclerOptions<ChatPerson> options, Listener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatListViewHolder chatListViewHolder, int i, @NonNull ChatPerson chatPerson) {
        Glide.with(chatListViewHolder.itemView.getContext())
                .load(chatPerson.getHisAvatarUrl())
                .transition(new DrawableTransitionOptions().crossFade())
                .into(chatListViewHolder.avatar);
        chatListViewHolder.title.setText(chatPerson.getName());
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false));
    }

     class ChatListViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView title;
         ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onChatClicked(getAdapterPosition());
                }
            });
        }
    }


   public interface Listener
    {
        void onChatClicked(int pos);
    }
}
