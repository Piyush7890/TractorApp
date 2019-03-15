package com.wce.tractorapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wce.tractorapp.model.RenterData;
import com.wce.tractorapp.widget.Utils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity implements SearchResultsAdapter.Listener {

    ImageView backBtn, micBtn;
    View shadow;
    EditText searchEt;
    RecyclerView recyclerView;
    SearchResultsAdapter adapter ;
    ViewGroup container;
    ProgressBar loadingView;
    AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Utils.setLightStatusBar(this, Color.WHITE);
        backBtn = findViewById(R.id.back_btn);
        micBtn = findViewById(R.id.mic_btn);
        searchEt = findViewById(R.id.search_et);
        adapter = new SearchResultsAdapter(this);
        recyclerView = findViewById(R.id.search_results);
        container = findViewById(R.id.container);
        appBarLayout = findViewById(R.id.appbar);
        shadow = findViewById(R.id.shadow);
        loadingView = findViewById(R.id.loading_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        shadow.setAlpha(0f);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(-1)) {
                    shadow.setAlpha(0f);
                }
                else shadow.setAlpha(1f);
            }
        });
        ViewTreeObserver vto = container.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //container.setPadding(container.getPaddingStart(),container.getPaddingTop()+appBarLayout.getHeight(),container.getPaddingEnd(),container.getPaddingBottom());
                container.setTranslationY(-appBarLayout.getHeight());
                container.getLayoutParams().height = container.getHeight()+appBarLayout.getHeight();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("RentInfo");
         EditTextDebounce.create(searchEt) .watch(new EditTextDebounce.DebounceCallback() {
              ValueEventListener listener;

             @Override
             public void onFinished(@NonNull final String result) {
                 loadingView.setVisibility(View.VISIBLE);
                 adapter.clearData();
                 if(result.isEmpty()) {
                  loadingView.setVisibility(View.GONE);
                     return;
                 }
                     if(listener!=null) {
                     mRef.removeEventListener(listener);
                 }


                    listener = new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         List<RenterData> results = new ArrayList<>();


                         for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                             RenterData data = dataSnapshot1.getValue(RenterData.class);
                             String fresult = result.toLowerCase();
                             if(data.getTitle().toLowerCase().contains(fresult)|| data.getDescription().toLowerCase().contains(fresult))
                             {
                                 loadingView.setVisibility(View.GONE);
                                 results.add(data);

                             }
                         }
                         if(results.isEmpty())
                             loadingView.setVisibility(View.GONE);
                         adapter.setRenterData(results);

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 };
                    mRef.addListenerForSingleValueEvent(listener);

             }
         });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchEt.setText(result.get(0));
                    searchEt.setSelection(searchEt.getText().length());

                }
                break;
        }
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
    public void onProductClicked(RenterData renterData) {
        Intent i = new Intent(this, DetailsActivity.class);
        i.putExtra("Product", Parcels.wrap(renterData));
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        startActivity(i, options.toBundle());
    }
}
