package com.wce.tractorapp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wce.tractorapp.model.ChatPerson;
import com.wce.tractorapp.model.RenterData;
import com.wce.tractorapp.model.SignUpData;

import org.parceler.Parcels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;

public class DetailsActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewGroup detailsContainer;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    ViewGroup summaryContainer;
    TextView title, location, price, description, type, name;
    ImageView mainImage, avatar;
    TextView callBtn, chatBtn;
    private RenterData renterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        summaryContainer = findViewById(R.id.summary_container);
        appBarLayout = findViewById(R.id.appbar);
        detailsContainer = findViewById(R.id.details_container);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        location = findViewById(R.id.location);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        type = findViewById(R.id.type);
        name = findViewById(R.id.name);
        avatar = findViewById(R.id.avatar);
        mainImage = findViewById(R.id.mainImage);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getIntent()!=null) {
             renterData = Parcels.unwrap(getIntent().getExtras().getParcelable("Product"));
            title.setText(renterData.getTitle());
            location.setText(renterData.getCity());
            price.setText(String.format("%s per month", String.valueOf(renterData.getRent())));
            description.setText(renterData.getDescription());
            type.setText(renterData.getEquipmentType());
            name.setText(renterData.getName());
            Glide.with(this).load(renterData.getAvatarUrl()).into(avatar);
            if(renterData.getUrls()!=null)
            Glide.with(this).asBitmap().load(renterData.getUrls().get(0)).transition(new BitmapTransitionOptions().crossFade()).addListener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                        public void onGenerated(Palette p) {
                            int darkColor = p.getDarkMutedColor(Color.parseColor("#585858"));
                            summaryContainer.setBackgroundColor(darkColor);
                            int color = p.getMutedColor(Color.parseColor("#808890"));
                            ValueAnimator animator = ObjectAnimator.ofArgb(Color.WHITE,darkColor );
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    summaryContainer.setBackgroundColor(((int) animation.getAnimatedValue()));
                                }
                            });
                            animator.setDuration(350L);
                            animator.start();
                            getWindow().setStatusBarColor(color);
                            collapsingToolbarLayout.setContentScrimColor(color);
                        }
                    });                    return false;
                }
            }).into(mainImage);
        }

        callBtn = findViewById(R.id.call_btn);
        chatBtn = findViewById(R.id.chat_btn);

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference reference  = FirebaseDatabase.getInstance().getReference();
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d("USEREMAIL", "onClick: "+user.getEmail());
                Log.d("RENTEREMAIL", "onClick: "+renterData.getEmail());
                if(user.getEmail().equals(renterData.getEmail()))
                {
                    Snackbar.make(getWindow().getDecorView(), "You cannot chat with yourself", Snackbar.LENGTH_SHORT).show();
                    return;
                    }
                 reference.child("UserInfo").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         SignUpData signUpData = dataSnapshot.getValue(SignUpData.class);
                         ChatPerson person = new ChatPerson(renterData.getName(),renterData.getEmail(),renterData.getAvatarUrl(), renterData.getUid());
                         ChatPerson person2 = new ChatPerson(signUpData.getName(),signUpData.getEmail(),signUpData.getAvatarUrl());

                         reference.child("ChatList").child(user.getUid()).push().setValue(person);
                         reference.child("ChatList").child(renterData.getUid()).push().setValue(person2);
                         Intent intent = new Intent(DetailsActivity.this, ConversationActivity.class);
                         intent.putExtra("ChatDetail", Parcels.wrap(person));
                         startActivity(intent);

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });


            }
        });


    }



}
