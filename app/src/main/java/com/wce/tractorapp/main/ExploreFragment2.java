package com.wce.tractorapp.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wce.tractorapp.AddProductActivity;
import com.wce.tractorapp.DetailsActivity;
import com.wce.tractorapp.LoadingView;
import com.wce.tractorapp.ProductAdapter.AllProductAdapter;
import com.wce.tractorapp.ProductAdapter.ProductsAdapter;
import com.wce.tractorapp.R;
import com.wce.tractorapp.comparator.PriceHighToLowCompartor;
import com.wce.tractorapp.comparator.PriceLowToHighCompartor;
import com.wce.tractorapp.model.RenterData;
import com.wce.tractorapp.model.Sort;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ExploreFragment2 extends Fragment implements ProductsAdapter.OnProductClickedListener {

    private AllProductAdapter mFirebaseAdapter;
    private RecyclerView productItems;
    private DatabaseReference mFirebaseDatabaseReference;
    FloatingActionButton fab;
    ValueEventListener mValueEventListener;
    LoadingView progressBar;
    FirebaseUser user;
    int sortOption = 0;
    private Sort sort;

    public ExploreFragment2() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        fab = view.findViewById(R.id.add_event_btn);
        productItems = view.findViewById(R.id.products);
        progressBar = view.findViewById(R.id.progress_bar);
         sort = new Sort(2, "Any", "Any");
        fab.setOnClickListener(v -> {

            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity());

            Intent i = new Intent(getContext(), AddProductActivity.class);
            startActivity(i,activityOptions.toBundle());
        });


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
       mFirebaseDatabaseReference = mFirebaseDatabaseReference.child("RentInfo");
       mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<RenterData> results = new ArrayList<>();


                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    RenterData data = dataSnapshot1.getValue(RenterData.class);
                    results.add(data);

                }
                sortDataAndSet(results, sort);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
      mFirebaseDatabaseReference.addValueEventListener(mValueEventListener);

        mFirebaseAdapter = new AllProductAdapter();
        productItems.setLayoutManager(new LinearLayoutManager(getContext()));
        productItems.setAdapter(mFirebaseAdapter);
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                progressBar.setVisibility(View.GONE);
            }
        });
        return view;
    }


    private void sortDataAndSet(List<RenterData> list, Sort sort)
    {
        switch (sort.getSortType()){
            case 0:
                Collections.sort(list,new PriceHighToLowCompartor());
                break;
            case 1:
                Collections.sort(list, new PriceLowToHighCompartor());
                break;
            case 2:
                break;
            case 3:
                Collections.reverse(list);
                break;
        }
        if(!sort.getLocation().equals("Any"))
for(RenterData renterData: list)
{
    if(!renterData.getCity().equals(sort.getLocation()))
        list.remove(renterData);
}

if(!sort.getType().equals("Any"))
    for(RenterData renterData: list)
    {
        if(!renterData.getEquipmentType().equals(sort.getType()))
            list.remove(renterData);
    }
        mFirebaseAdapter.setList(list);
    }


    public void sort(Sort sort)
    {
        this.sort= sort;
        if(mValueEventListener!=null)
            mFirebaseDatabaseReference.removeEventListener(mValueEventListener);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabaseReference.addValueEventListener(mValueEventListener);
    }


    @Override
    public void onProductClicked(RenterData renterData) {
        Intent i = new Intent(getActivity(), DetailsActivity.class);
        i.putExtra("Product", Parcels.wrap(renterData));
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
        startActivity(i, options.toBundle());
    }
}
