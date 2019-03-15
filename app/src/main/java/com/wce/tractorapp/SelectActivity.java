package com.wce.tractorapp;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.wce.tractorapp.ProductAdapter.ProductsAdapter;
import com.wce.tractorapp.ProductAdapter.SelectProductAdapter;
import com.wce.tractorapp.model.RenterData;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectActivity extends ToolBarActivity implements ProductsAdapter.OnProductClickedListener {

    SelectProductAdapter mFirebaseAdapter;
    RecyclerView productItems;
    private DatabaseReference mFirebaseDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productItems = findViewById(R.id.products);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        Query messagesRef = mFirebaseDatabaseReference.child("RentInfo");
        FirebaseRecyclerOptions<RenterData> options =
                new FirebaseRecyclerOptions.Builder<RenterData>()
                        .setQuery(messagesRef, RenterData.class)
                        .build();

        mFirebaseAdapter = new SelectProductAdapter(options,this, this);
        productItems.setLayoutManager(new LinearLayoutManager(this));
        productItems.setAdapter(mFirebaseAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAdapter.stopListening();
    }

    @Override
    protected boolean isBackEnabled() {
        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_select ;
    }

    @Override
    public void onProductClicked(RenterData renterData) {

    }
}
