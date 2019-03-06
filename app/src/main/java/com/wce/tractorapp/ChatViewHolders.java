package com.wce.tractorapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatViewHolders extends RecyclerView.ViewHolder {
    TextView message;
    TextView time;

    public ChatViewHolders(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.message);
        time = itemView.findViewById(R.id.time);
    }


}

class TopChatViewHolders extends ChatViewHolders {
    ImageView avatar;
    public TopChatViewHolders(@NonNull View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.avatar);
    }
}


