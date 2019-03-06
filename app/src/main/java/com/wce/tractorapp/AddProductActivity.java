package com.wce.tractorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wce.tractorapp.ItemAnimators.PopItemAnimator;
import com.wce.tractorapp.model.RenterData;
import com.wce.tractorapp.model.SignUpData;
import com.wce.tractorapp.widget.FABAnimation.FABBehaviour;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddProductActivity extends AppCompatActivity implements PhotosAdapter.OnItemClickedListener {

    private static final int REQUEST_IMAGE = 2;
    RecyclerView photos;
    PhotosAdapter adapter;
    ImageView scrimImage;
    AppBarLayout nestedScrollView;
    private boolean first= true;
    Button addProduct;
    Date currDate;
    private String date;
    private EditText title,description,cost;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    final List<String>urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Fade transition = new Fade();
        getWindow().setEnterTransition(transition);
        scrimImage = findViewById(R.id.scrim_image);
        nestedScrollView = findViewById(R.id.appbar);
        addProduct = findViewById(R.id.fab);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        cost = findViewById(R.id.cost);
        progressDialog  = new ProgressDialog(this);
        Calendar currDate = Calendar.getInstance();

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference(user.getUid());


        final Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
        addProduct.setOnClickListener(new View.OnClickListener() {
            String name, email;

            @Override
            public void onClick(View view) {

                databaseReference = firebaseDatabase.getReference().child("UserInfo").child(user.getUid());
                //uploadImage();
                ValueEventListener addproductListner = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("Chutiya", "onDataChange: ");
                        SignUpData signUpData = dataSnapshot.getValue(SignUpData.class);
                        name = signUpData.getName();
                        email = signUpData.getEmail();
                        String city = signUpData.getCity();
                        RenterData renterData = new RenterData(name,
                                email,
                                title.getText().toString(),
                                spinner.getSelectedItem().toString(),
                                description.getText().toString(),
                                true,
                                date,
                                Integer.valueOf(cost.getText().toString()),
                                urls,
                                city,
                                signUpData.getAvatarUrl(),
                                user.getUid());
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        if (user != null) {
                            databaseReference.child("RentInfo").push().setValue(renterData);
                        }
                        finish();

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Chutiya SHivani", "onDataChange: ");

                    }
                };
                if (databaseReference != null) {
                    databaseReference.addValueEventListener(addproductListner);
                }
            }
        });

        nestedScrollView.addOnOffsetChangedListener(new FABBehaviour(findViewById(R.id.fab)));
        photos = findViewById(R.id.photos_rv);
        photos.setItemAnimator(PopItemAnimator.create());
        adapter = new PhotosAdapter(this);
        photos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        photos.setAdapter(adapter);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Type, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }
    public void uploadImage(){
        Log.d("UPLOAD", "uploadImage: ");
        List<Uri>photos = adapter.getPhotos();

        for(int i=0;i<photos.size();i++){
            Uri uri = photos.get(i);
            StorageReference reference = storageReference.child(user.getUid()).child(uri.getLastPathSegment());
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddProductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                    //Toast.makeText(AddProductActivity.this,"Uploaded");
                    urls.add(taskSnapshot.getStorage().getDownloadUrl().toString());
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(AddProductActivity.this,"failed",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
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

                        progressDialog.setMessage("Uploading Image");
                        progressDialog.show();
                        final StorageReference reference = storageReference.child(user.getUid()).child(uri.getLastPathSegment());
                        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(AddProductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urlTask.isSuccessful());
                                Uri downloadUrl = urlTask.getResult();
                                urls.add(downloadUrl.toString());
                                progressDialog.dismiss();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(AddProductActivity.this,"failed",Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                    }
                                });
                        adapter.addImage(uri);

                    }
                }
            }
        }
    }
}

