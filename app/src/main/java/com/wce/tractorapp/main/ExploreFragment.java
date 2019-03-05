package com.wce.tractorapp.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wce.tractorapp.AddProductActivity;
import com.wce.tractorapp.R;

import androidx.fragment.app.Fragment;


public class ExploreFragment extends Fragment {


    FloatingActionButton fab;
    public ExploreFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        fab = view.findViewById(R.id.add_event_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity());

                Intent i = new Intent(getContext(), AddProductActivity.class);
                startActivity(i,activityOptions.toBundle());
            }
        });
        return view;
    }


}
