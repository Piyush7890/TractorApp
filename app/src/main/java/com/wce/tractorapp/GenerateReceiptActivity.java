package com.wce.tractorapp;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GenerateReceiptActivity extends AppCompatActivity {

    TextView title, location, type, date;
    ImageView image;
    ViewGroup parent;
    Button send, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_receipt);
        cancel = findViewById(R.id.cancel_btn);
        send = findViewById(R.id.send_btn);
        title = findViewById(R.id.title);
    }
}
