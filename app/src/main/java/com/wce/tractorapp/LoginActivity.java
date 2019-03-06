package com.wce.tractorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wce.tractorapp.main.MainActivity;
import com.wce.tractorapp.model.SignUpData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class LoginActivity extends AppCompatActivity  implements LoginFragment.OnFragmentInteractionListener, SignUpFragment.onSignUp{
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    ViewGroup parent;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        parent = findViewById(R.id.parent);
        getSupportFragmentManager().beginTransaction().replace(R.id.parent, new LoginFragment()).commit();
    }

    @Override
    public void onFragmentInteraction(int id) {
        switch (id)
        {
            case R.id.create_account_btn : {
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.parent, new SignUpFragment()).addToBackStack("loginfragment").commit();
            }

        }
    }

    @Override
    public void onLoginClick(String email, String password) {
        if( email==null || email.isEmpty()){
            Toast.makeText(LoginActivity.this,"Email cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(password==null || password.isEmpty()){
            Toast.makeText(LoginActivity.this,"Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user.isEmailVerified()) {
                            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Please verify Your email first", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();

                        //finish();
                        //startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()==0)
            super.onBackPressed();
        else getSupportFragmentManager().popBackStack();
    }

    @Override
    public void signUp(final SignUpData signUpData, final Uri imageUri) {
        if(imageUri == null) {
            Toast.makeText(this, "Upload Profile Image first", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Registering User");
        dialog.show();
            FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(signUpData.getEmail(), signUpData.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sendEmailVerification();

                    Toast.makeText(LoginActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                    storageReference = firebaseStorage.getReference(user.getUid());

                    final StorageReference reference = storageReference.child(user.getUid()).child(imageUri.getLastPathSegment());
                    reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            signUpData.setAvatarUrl(downloadUrl.toString());
                            if(user!=null){
                                databaseReference.child("UserInfo").child(user.getUid()).setValue(signUpData);
                                dialog.dismiss();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                        }
                    });


                }
                else{
                    Toast.makeText(LoginActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            }
        });
    }
    private void sendEmailVerification(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Verify your email-id",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }

                }
            });
        }
    }



}