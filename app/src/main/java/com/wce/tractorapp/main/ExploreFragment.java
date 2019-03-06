package com.wce.tractorapp.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wce.tractorapp.AddProductActivity;
import com.wce.tractorapp.DetailsActivity;
import com.wce.tractorapp.ProductsAdapter;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.RenterData;

import org.parceler.Parcels;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ExploreFragment extends Fragment implements ProductsAdapter.OnProductClickedListener {

    private FirebaseRecyclerAdapter<RenterData, ProductsAdapter.ProductViewHolder> mFirebaseAdapter;
    private RecyclerView productItems;
    private DatabaseReference mFirebaseDatabaseReference;
    FloatingActionButton fab;
    public ExploreFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseAdapter.startListening();

    }

    @Override
    public void onPause() {
        super.onPause();
        mFirebaseAdapter.stopListening();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        fab = view.findViewById(R.id.add_event_btn);
        productItems = view.findViewById(R.id.products);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity());

                Intent i = new Intent(getContext(), AddProductActivity.class);
                startActivity(i,activityOptions.toBundle());
            }
        });


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        Log.d("AFTER REFERENCE", "onCreateView: ");
        SnapshotParser<RenterData> parser = new SnapshotParser<RenterData>() {
            @Override
            public RenterData parseSnapshot(DataSnapshot dataSnapshot) {

                return dataSnapshot.getValue(RenterData.class);
            }
        };
        DatabaseReference messagesRef = mFirebaseDatabaseReference.child("RentInfo");
        FirebaseRecyclerOptions<RenterData> options =
                new FirebaseRecyclerOptions.Builder<RenterData>()
                        .setQuery(messagesRef, RenterData.class)
                        .build();

        mFirebaseAdapter = new ProductsAdapter(options,this);
        productItems.setLayoutManager(new LinearLayoutManager(getContext()));
        productItems.setAdapter(mFirebaseAdapter);
        return view;
    }


    @Override
    public void onProductClicked(RenterData renterData) {
        Intent i = new Intent(getActivity(), DetailsActivity.class);
        i.putExtra("Product", Parcels.wrap(renterData));
        startActivity(i);
    }
}
