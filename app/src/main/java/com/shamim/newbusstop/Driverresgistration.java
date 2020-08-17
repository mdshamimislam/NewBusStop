package com.shamim.newbusstop;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.IOException;
import java.util.UUID;

public class Driverresgistration extends AppCompatActivity implements View.OnClickListener {
    EditText fullname, email, phone, password, password1, license, nid, company;
    ImageView driver_reg_image;
    String TAG = "Driver Activity";
    Button cancel, singup, back;
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
        setContentView(R.layout.activity_driverresgistration);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        password1 = findViewById(R.id.password1);
        license = findViewById(R.id.license);
        nid = findViewById(R.id.nid);
        company = findViewById(R.id.company);
        cancel = findViewById(R.id.cancel);
        back = findViewById(R.id.back);
        singup = findViewById(R.id.signup);
        driver_reg_image = findViewById(R.id.profile_image);

        //image
        Storage =FirebaseStorage.getInstance();
        storageReference=Storage.getReference();

        //register
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Driver");
        firebaseAuth = FirebaseAuth.getInstance();


        cancel.setOnClickListener(this);
        singup.setOnClickListener(this);
        driver_reg_image.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.cancel:
                final Intent cancel = new Intent(this, Home.class);
                startActivity(cancel);
                break;

            case R.id.signup:
                final String Fullname = fullname.getText().toString();
                final String Email = email.getText().toString();
                final String Phone = phone.getText().toString();
                final String Password = password.getText().toString();
                String Password1 = password1.getText().toString();
                final String License = license.getText().toString();
                final String NID = nid.getText().toString();
                final String Company = company.getText().toString();

                if (driver_reg_image == null) {
                    Toast.makeText(Driverresgistration.this, "Please Insert Your Image", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Image");
                }

               else if (Fullname.isEmpty()) {
                    Toast.makeText(Driverresgistration.this, "Please Enter your Fullname", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Full Name is Empty" + " " + Fullname);
                } else if (Email.isEmpty()) {
                    Toast.makeText(Driverresgistration.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Eamil Null" + " " + Email);
                }


                /*else if (!Email.equals(email+"@"))
                {
                    Toast.makeText(Driverresgistration.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();

                    Log.d(TAG,"Email"+" "+Email);
                }*/

                else if (Phone.isEmpty()) {
                    Toast.makeText(Driverresgistration.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Phone" + " " + Phone);
                } else if (Phone.charAt(0) != '+' || Phone.charAt(1) != '8' || Phone.charAt(2) != '8' || Phone.charAt(3) != '0') {

                    Toast.makeText(Driverresgistration.this, "Must Be First 4 Digit=+880", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "First Position Number is" + " " + Phone);
                } else if (Phone.charAt(4) != '1') {
                    Toast.makeText(Driverresgistration.this, "5th Position Digit is = 1 ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "5th Position Number is" + " " + Phone);
                } else if (Phone.charAt(5) != '9' && Phone.charAt(5) != '8' &&
                        Phone.charAt(5) != '7' && Phone.charAt(5) != '3' &&
                        Phone.charAt(5) != '4' && Phone.charAt(5) != '5'
                        && Phone.charAt(5) != '6') {
                    Toast.makeText(Driverresgistration.this, "6th Position Digit is = 3 or 4 or 5 or 6 or 7 or 8 or 9 ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "6th Position Number is" + " " + Phone);

                } else if (Phone.length() != 14) {
                    Toast.makeText(Driverresgistration.this, "Number Must be 14 length with +880", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Phone Number Not =14" + " " + Phone);
                } else if (Password.isEmpty()) {
                    Toast.makeText(Driverresgistration.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password" + " " + Password);
                } else if (Password1.isEmpty()) {
                    Toast.makeText(Driverresgistration.this, "Please Enter Your Conform Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password1" + " " + Password1);
                } else if (!Password1.equals(Password)) {
                    Toast.makeText(Driverresgistration.this, "Please Enter Same Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password  Password" + " " + Password + " " + Password1);
                } else if (Password.length() < 7) {
                    Toast.makeText(Driverresgistration.this, "Password Must be 6 grater than", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Password Length Not Grater then 7" + " " + Password);
                } else if (License.isEmpty()) {
                    Toast.makeText(Driverresgistration.this, "Please Enter Your License", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "License" + " " + License);
                } else if (NID.isEmpty()) {
                    Toast.makeText(Driverresgistration.this, "Please Enter Your NID Number", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "NID" + " " + NID);
                } else if (NID.length() != 10) {
                    Toast.makeText(Driverresgistration.this, "NID Card Number Must be 10", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "NID Card Number Must be 10" + " " + NID);
                } else if (Company.isEmpty()) {
                    Toast.makeText(Driverresgistration.this, "Please Enter Your Company Name Of Bus", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Company" + " " + Company);
                }
                else {

                    Log.d(TAG,"else");


                    firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        driver info = new driver(
                                                Fullname,
                                                Email,
                                                Phone,
                                                Password,
                                                License,
                                                NID,
                                                Company
                                        );
                                        firebaseDatabase.getInstance().getReference("Driver")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {


                                                upload_image();

                                                Intent success = new Intent(Driverresgistration.this, Home.class);

                                                startActivity(success);
                                                Toast.makeText(Driverresgistration.this, "Registration is Complete ", Toast.LENGTH_SHORT).show();



                                            }


                                        });
                                    } else {
                                        Toast.makeText(Driverresgistration.this, "Already This Email has Registered ", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }



             break;

            case R.id.profile_image:
                choose_image();

                break;
        }
    }

    private void upload_image()
    {
       if (filepath !=null)
       {
           final ProgressDialog progressDialog = new ProgressDialog(this);
           progressDialog.setTitle("Uploading...");
           StorageReference reference = storageReference.child("image/*"+ UUID.randomUUID().toString());
          reference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
              @Override
              public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  progressDialog.dismiss();
                  Toast.makeText(Driverresgistration.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
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

    private void choose_image()
    {
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
                driver_reg_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}