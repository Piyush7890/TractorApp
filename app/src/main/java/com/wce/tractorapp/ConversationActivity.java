package com.wce.tractorapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;

public class ConversationActivity extends ToolBarActivity {

    EditText mCompose;
    ImageButton sendBtn;
    ChatPerson detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompose = findViewById(R.id.compose_edit_text);
        sendBtn = findViewById(R.id.send_btn);
        if(getIntent()!=null)
        {
            detail = Parcels.unwrap(getIntent().getParcelableExtra("ChatDetail"));
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
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToUser(new Chat(user.getUid(), detail.getUid(),System.currentTimeMillis(), mCompose.getText().toString() ));
            }
        });

    }

    private void sendMessageToUser(final Chat chat) {
        final String room_type_1 = chat.getSenderUid() + "_" + chat.getReceiverUid();
        final String room_type_2 = chat.getReceiverUid() + "_" + chat.getSenderUid();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final String ARG_CHAT_ROOMS = "ChatRooms";
        databaseReference.child(ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(room_type_1)) {
                    databaseReference.child(ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(chat.getTime())).setValue(chat);
                } else if (dataSnapshot.hasChild(room_type_2)) {
                    databaseReference.child(ARG_CHAT_ROOMS).child(room_type_2).child(String.valueOf(chat.getTime())).setValue(chat);
                } else {
                    databaseReference.child(ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(chat.getTime())).setValue(chat);
//                    getMessageFromFirebaseUser(chat.senderUid, chat.receiverUid);
                }
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
