package com.wce.tractorapp.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.wce.tractorapp.AddProductActivity;
import com.wce.tractorapp.DetailsActivity;
import com.wce.tractorapp.LoadingView;
import com.wce.tractorapp.ProductAdapter.AllProductsAdapter;
import com.wce.tractorapp.ProductAdapter.ProductsAdapter;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.RenterData;
import com.wce.tractorapp.model.Sort;

import org.parceler.Parcels;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ExploreFragment extends Fragment implements ProductsAdapter.OnProductClickedListener {

    private ProductsAdapter mFirebaseAdapter;
    private RecyclerView productItems;
    private DatabaseReference mFirebaseDatabaseReference;
    FloatingActionButton fab;
    LoadingView progressBar;
    FirebaseUser user;

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
        progressBar = view.findViewById(R.id.progress_bar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity());

                Intent i = new Intent(getContext(), AddProductActivity.class);
                startActivity(i,activityOptions.toBundle());
            }
        });


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Query messagesRef = mFirebaseDatabaseReference.child("RentInfo");
        FirebaseRecyclerOptions<RenterData> options =
                new FirebaseRecyclerOptions.Builder<RenterData>()
                        .setQuery(messagesRef, RenterData.class)
                        .build();

        mFirebaseAdapter = new AllProductsAdapter(options,this);
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


    public void sort(Sort sort)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query query = mFirebaseDatabaseReference.child("RentInfo");;
        switch(sort.getSortType())
        {
            case 0:
            {
                query=mFirebaseDatabaseReference.child("RenterInfo").child(user.getUid()).orderByChild("cost");
                break;
            }

            case 1:
            {
                 query=mFirebaseDatabaseReference.child("RenterInfo").child(user.getUid()).orderByChild("cost");
               break;
            }

            case 2:
            {
                query= mFirebaseDatabaseReference.child("RentInfo");
                break;
            }
            case 3:
            {
                query = mFirebaseDatabaseReference.child("RentInfo");
                break;
            }

        }
//        if(!sort.getLocation().equals("Any"))
//        {
//            query..equalTo(sort.getLocation());
//
//        }
//        if(!sort.getType().equals("Any"))
//        {
//            query.orderByChild("equipmentType").equalTo(sort.getType());
//        }

        FirebaseRecyclerOptions<RenterData> options =
                new FirebaseRecyclerOptions.Builder<RenterData>()
                        .setQuery(query, RenterData.class)
                        .build();

        mFirebaseAdapter=new AllProductsAdapter(options,this);
        if(sort.getSortType()==1||sort.getSortType()==2)
            mFirebaseAdapter.setReverse(true);
        productItems.setAdapter(mFirebaseAdapter);
    }


    @Override
    public void onProductClicked(RenterData renterData) {
        Intent i = new Intent(getActivity(), DetailsActivity.class);
        i.putExtra("Product", Parcels.wrap(renterData));
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
        startActivity(i, options.toBundle());
    }
}
