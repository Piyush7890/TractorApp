package com.wce.tractorapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.wce.tractorapp.model.Chat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter  extends FirebaseRecyclerAdapter<Chat, RecyclerView.ViewHolder> {

    private static final int LEFT_TOP=0;
    private static final int LEFT_MID=1;
    private static final int RIGHT_MID=3;
    private static final int RIGHT_TOP=2;

    private String uid;

    public ChatAdapter(@NonNull FirebaseRecyclerOptions<Chat> options, String uid) {
        super(options);
        this.uid = uid;
    }


    @Override
    public int getItemViewType(int position) {
        int type;
        if(position==0)
        {
            if(getItem(position).getSenderUid().equals(uid))
            {
                type =  RIGHT_TOP;
            }
            else
            {
                type =  LEFT_TOP;
            }
        }
      else {
          if(getItem(position).getSenderUid().equals(uid) && getItem(position - 1).getSenderUid().equals(uid))
          {
              type =  RIGHT_MID;
          }
          else if(getItem(position).getReceiverUid().equals(uid) && getItem(position - 1).getReceiverUid().equals(uid))
          {
              type =  LEFT_MID;
          }
          else if(getItem(position).getSenderUid().equals(uid))
          {
              type =  RIGHT_TOP;
          }
          else
          {
            type =  LEFT_TOP;
          }

        }
        Log.d("TYPEHOLDER", "getItemViewType: "+type);
        return type;
   }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull Chat chat) {
      ChatViewHolders chatViewHolders = ((ChatViewHolders) viewHolder);
      chatViewHolders.message.setText(chat.getText());
      chatViewHolders.time.setText(chat.getTime());
        switch( getItemViewType(i))
       {
           case LEFT_TOP:
           {
               TopChatViewHolders holder = ((TopChatViewHolders) viewHolder);
               Glide.with(holder.itemView.getContext()).load(chat.getReciverAvatarUrl()).into(holder.avatar);
                break;
           }
           case LEFT_MID:
           {
break;
           }
           case RIGHT_TOP:
           {
               TopChatViewHolders holder = ((TopChatViewHolders) viewHolder);
               Glide.with(holder.itemView.getContext()).load(chat.getReciverAvatarUrl()).into(holder.avatar);
            break;
           }
           case RIGHT_MID:
           {
break;
           }
       }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case LEFT_TOP:
            {
                return new TopChatViewHolders(LayoutInflater.from(parent.getContext()).inflate(R.layout.left_chat_bubble, parent, false));
            }
            case LEFT_MID:
            {
                return new ChatViewHolders(LayoutInflater.from(parent.getContext()).inflate(R.layout.left_middle_chat_bubble, parent, false));

            }
            case RIGHT_TOP:
            {
                return new TopChatViewHolders(LayoutInflater.from(parent.getContext()).inflate(R.layout.right_chat_bubble, parent, false));

            }
            case RIGHT_MID:
            {
                return new ChatViewHolders(LayoutInflater.from(parent.getContext()).inflate(R.layout.right_middle_chat_bubble, parent, false));

            }
        }
        return null;
    }
}
