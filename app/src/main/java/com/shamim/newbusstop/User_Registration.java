package com.shamim.newbusstop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shamim.newbusstop.drawer_layout.login;

import java.io.IOException;
import java.util.UUID;

public class User_Registration extends AppCompatActivity implements View.OnClickListener {
    final  String TAG="User_Registration";
    ImageView user_image;
    EditText user_fullname,user_email,user_phone,user_password,user_password1;
    Button user_cancel,user_signup,user_have_account,user_back;


    private Uri filepath;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    //image
    private FirebaseStorage Storage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__registration);

        user_fullname = findViewById(R.id.user_fullname);
        user_email = findViewById(R.id.user_email);
        user_phone = findViewById(R.id.user_phone);
        user_password = findViewById(R.id.user_password);
        user_password1 = findViewById(R.id.user_password1);
        user_cancel = findViewById(R.id.user_cancel);
       user_back = findViewById(R.id.user_back);
        user_signup = findViewById(R.id.user_signup);
        user_image = findViewById(R.id.user_image);
        user_have_account = findViewById(R.id.user_have_account);


        //image
        Storage = FirebaseStorage.getInstance();
        storageReference=Storage.getReference();

        //register
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        firebaseAuth = FirebaseAuth.getInstance();


        user_signup.setOnClickListener(this);
        user_cancel.setOnClickListener(this);
        user_image.setOnClickListener(this);
        user_back.setOnClickListener(this);
        user_have_account.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.user_signup:
                final String Fullname = user_fullname.getText().toString();
                final String Email = user_email.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String Phone = user_phone.getText().toString();
                final String Password = user_password.getText().toString();
                String Password1 = user_password.getText().toString();

                  if (Fullname.isEmpty()) {
                Toast.makeText(User_Registration.this, "Please Enter your Fullname", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Full Name is Empty" + " " + Fullname);
            } else if (Email.isEmpty()) {
                Toast.makeText(User_Registration.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Eamil Null" + " " + Email);
            }
            else if (!Email.matches(emailPattern))
            {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            }


            else if (Phone.isEmpty()) {
                Toast.makeText(User_Registration.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Phone" + " " + Phone);
            } else if (Phone.charAt(0) != '+' || Phone.charAt(1) != '8' || Phone.charAt(2) != '8' || Phone.charAt(3) != '0') {

                Toast.makeText(User_Registration.this, "Must Be First 4 Digit=+880", Toast.LENGTH_LONG).show();
                Log.d(TAG, "First Position Number is" + " " + Phone);
            } else if (Phone.charAt(4) != '1') {
                Toast.makeText(User_Registration.this, "5th Position Digit is = 1 ", Toast.LENGTH_LONG).show();
                Log.d(TAG, "5th Position Number is" + " " + Phone);
            } else if (Phone.charAt(5) != '9' && Phone.charAt(5) != '8' &&
                    Phone.charAt(5) != '7' && Phone.charAt(5) != '3' &&
                    Phone.charAt(5) != '4' && Phone.charAt(5) != '5'
                    && Phone.charAt(5) != '6') {
                Toast.makeText(User_Registration.this, "6th Position Digit is = 3 or 4 or 5 or 6 or 7 or 8 or 9 ", Toast.LENGTH_LONG).show();
                Log.d(TAG, "6th Position Number is" + " " + Phone);

            } else if (Phone.length() != 14) {
                Toast.makeText(User_Registration.this, "Number Must be 14 length with +880", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Phone Number Not =14" + " " + Phone);
            } else if (Password.isEmpty()) {
                Toast.makeText(User_Registration.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Password" + " " + Password);
            } else if (Password1.isEmpty()) {
                Toast.makeText(User_Registration.this, "Please Enter Your Conform Password", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Password1" + " " + Password1);
            } else if (!Password1.equals(Password)) {
                Toast.makeText(User_Registration.this, "Please Enter Same Password", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Password  Password" + " " + Password + " " + Password1);
            } else if (Password.length() < 7) {
                Toast.makeText(User_Registration.this, "Password Must be 6 grater than", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Password Length Not Grater then 7" + " " + Password);
            }
            else
                  {

                      firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                              .addOnCompleteListener(User_Registration.this, new OnCompleteListener<AuthResult>() {

                                  @Override
                                  public void onComplete(@NonNull final Task<AuthResult> task) {
                                      if (task.isSuccessful()) {
                                          Log.d(TAG,"Task value"+task);
                                          User info = new User(
                                                  Fullname,
                                                  Email,
                                                  Phone,
                                                  Password
                                          );
                                          firebaseDatabase.getInstance().getReference("User")
                                                  .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                  .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {

                                              @Override
                                              public void onComplete(@NonNull Task<Void> task) {


                                                  upload_image();

                                                  FragmentManager manager = getSupportFragmentManager();
                                                  FragmentTransaction transaction = manager.beginTransaction();
                                                  login loginFragment = new login();
                                                  transaction.replace(R.id.test2, loginFragment);
                                                  transaction.addToBackStack(null);
                                                  transaction.commit();

                                                  Toast.makeText(User_Registration.this, "Registration is Complete ", Toast.LENGTH_SHORT).show();



                                              }


                                          });
                                      } else {
                                          Toast.makeText(User_Registration.this, "Already This Email has Registered ", Toast.LENGTH_SHORT).show();
                                      }
                                  }

                              });


                  }


                break;

            case R.id.user_image:
                choose_image();

                break;

            case R.id.user_have_account:
                Intent user_have_account = new Intent(User_Registration.this,Home.class);
                startActivity(user_have_account);

            case R.id.user_cancel:
                Intent user_cancel = new Intent(User_Registration.this,Home.class);
                startActivity(user_cancel);
        }

    }

    private void choose_image() {
        Intent choose = new Intent();
        choose.setType("image/*");
        choose.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(choose,"Select Image"),1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==1  && resultCode == RESULT_OK
                &&  data != null && data.getData() != null )
        {
            Log.d(TAG,"image"+requestCode+" "+resultCode);
            Uri imageUri = data.getData();
            filepath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                user_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void upload_image() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        StorageReference reference = storageReference.child("image/*"+ UUID.randomUUID().toString());
        reference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(User_Registration.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                        double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded"+(int)progress+"%");

                    }
                });
    }


}
