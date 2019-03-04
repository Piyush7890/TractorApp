package com.wce.tractorapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.wce.tractorapp.ItemAnimators.PopItemAnimator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddProductActivity extends AppCompatActivity implements PhotosAdapter.OnItemClickedListener {

    private static final int REQUEST_IMAGE = 2;
    RecyclerView photos;
    PhotosAdapter adapter;
    ImageView scrimImage;
    private boolean first= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        scrimImage = findViewById(R.id.scrim_image);
        photos = findViewById(R.id.photos_rv);
        photos.setItemAnimator(PopItemAnimator.create());
        adapter = new PhotosAdapter(this);
        photos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        photos.setAdapter(adapter);


    }

    @Override
    public void onAddPhotoClicked() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onCameraClicked() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE: {
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        if(first)
                        {
                            first = false;
                            Glide.with(this).load(data.getData()).transition(DrawableTransitionOptions.withCrossFade()).into(scrimImage);
                        }
                        final Uri uri = data.getData();
                        adapter.addImage(uri);

                    }
                }
            }
        }
    }
}
