package com.wce.tractorapp.main;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.wce.tractorapp.DetailsActivity;
import com.wce.tractorapp.LoadingView;
import com.wce.tractorapp.ProductAdapter.MyProductsAdapter;
import com.wce.tractorapp.ProductAdapter.ProductsAdapter;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.RenterData;

import org.parceler.Parcels;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MyAdsFragment extends Fragment implements ProductsAdapter.OnProductClickedListener, MyProductsAdapter.MoreOptionListener {



    RecyclerView myAds;
    LoadingView progressBar;
    private DatabaseReference mFirebaseDatabaseReference;
    private MyProductsAdapter mFirebaseAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_my_ads, container, false);
        myAds = view.findViewById(R.id.my_ads);
        progressBar = view.findViewById(R.id.progress_bar);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        Log.d("AFTER REFERENCE", "onCreateView: ");

        Query messagesRef = mFirebaseDatabaseReference.child("RentInfo").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());
        FirebaseRecyclerOptions<RenterData> options =
                new FirebaseRecyclerOptions.Builder<RenterData>()
                        .setQuery(messagesRef, RenterData.class)
                        .build();
        mFirebaseAdapter = new MyProductsAdapter(options,this,this);

        myAds.setLayoutManager(new LinearLayoutManager(getContext()));
        myAds.setAdapter(mFirebaseAdapter);
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                progressBar.setVisibility(View.GONE);
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(mFirebaseAdapter!=null)
            mFirebaseAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        if(mFirebaseAdapter!=null)
            mFirebaseAdapter.stopListening();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onProductClicked(RenterData renterData) {
        Intent i = new Intent(getActivity(), DetailsActivity.class);
        i.putExtra("Product", Parcels.wrap(renterData));
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
        startActivity(i, options.toBundle());
    }

    @Override
    public void onDeleteProduct(final int id) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.delete_dialog, null);
        final AlertDialog.Builder builder =new  AlertDialog.Builder(getContext());
        final AlertDialog dialog = builder.setView(v).create();

        v.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        v.findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                mFirebaseDatabaseReference.child("RentInfo").child(mFirebaseAdapter.getRef(id).getKey()).removeValue();

            }
        });
        dialog.show();

    }

    @Override
    public void onChangePrice(final int id) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.edit_price_dialog, null);
        ((TextView) v.findViewById(R.id.current_price)).setText(String.format("Current price : %d", mFirebaseAdapter.getItem(id).getRent()));
        final AlertDialog.Builder builder =new  AlertDialog.Builder(getContext());
        final AlertDialog dialog = builder.setView(v).create();
        final TextInputEditText priceEt = v.findViewById(R.id.price_et);
        v.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        v.findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(priceEt.getText().toString().isEmpty())
                    return;
                dialog.cancel();
                mFirebaseDatabaseReference.child("RentInfo").child(mFirebaseAdapter.getRef(id).getKey()).child("rent").setValue(Integer.valueOf(priceEt.getText().toString()));

            }
        });
        dialog.show();
    }

    @Override
    public void onChangeStatus(int id) {
        RenterData renterData = mFirebaseAdapter.getItem(id);
        boolean status = !renterData.isAvailabilityStatus();

        mFirebaseDatabaseReference.child("RentInfo").child(mFirebaseAdapter.getRef(id).getKey()).child("availabilityStatus").setValue(status);
        Snackbar.make(getActivity().getWindow().getDecorView(), "Availability status changed to "+ (status?"Available":"Not available"), Snackbar.LENGTH_LONG).show();
    }
}
