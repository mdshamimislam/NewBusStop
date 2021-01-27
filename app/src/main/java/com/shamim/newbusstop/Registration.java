package com.shamim.newbusstop;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.shamim.newbusstop.drawer_layout.login;

import java.io.IOException;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    EditText fullname, email, phone, password, password1, license, nid, company, mEditTextFileName;
    ImageView driver_reg_image;
    String TAG = "Driver Activity";
    Button cancel, driver_singup, back,user_signup,admin_signup,subadmin_singup;
    private Uri filepath;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageTask mUploadTask;
    private DatabaseReference mDatabaseRef_driver;
    StorageReference mStorageRef_driver;
    private DatabaseReference mDatabaseRef_user;
    StorageReference mStorageRef_user;
    String usertype;
    //image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverresgistration);

        Intent intent = getIntent();
        usertype=intent.getStringExtra("user_type");


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
        driver_singup = findViewById(R.id. driver_singup);
        user_signup = findViewById(R.id. user_signup);
        admin_signup = findViewById(R.id. admin_signup);
        driver_reg_image = findViewById(R.id.profile_image);
        subadmin_singup = findViewById(R.id.subadmin_singup);
        mEditTextFileName= findViewById(R.id.title_text);




        if (usertype.equals("user") )
        {
            license.setVisibility(View.GONE);
            admin_signup.setVisibility(View.GONE);
            driver_singup.setVisibility(View.GONE);
            nid.setVisibility(View.GONE);
            company.setVisibility(View.GONE);


        }
        else if(usertype.equals("driver"))
        {
            admin_signup.setVisibility(View.GONE);
            user_signup.setVisibility(View.GONE);
            subadmin_singup.setVisibility(View.GONE);
        }
        else if(usertype.equals("admin1"))
        {
            subadmin_singup.setVisibility(View.GONE);
            user_signup.setVisibility(View.GONE);
            driver_singup.setVisibility(View.GONE);
        }
        else if(usertype.equals("subadmin"))
        {
            admin_signup.setVisibility(View.GONE);
            user_signup.setVisibility(View.GONE);
            driver_singup.setVisibility(View.GONE);
            license.setVisibility(View.GONE);
        }
        else if (usertype.equals("admin1"))
        {
            subadmin_singup.setVisibility(View.GONE);
            user_signup.setVisibility(View.GONE);
            driver_singup.setVisibility(View.GONE);
        }

        //image
        mStorageRef_driver = FirebaseStorage.getInstance().getReference("Driver_Image");
        mDatabaseRef_driver = FirebaseDatabase.getInstance().getReference("Driver_Image");

        mStorageRef_user = FirebaseStorage.getInstance().getReference("User_Image");
        mDatabaseRef_user = FirebaseDatabase.getInstance().getReference("User_Image");

        //register
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Driver");
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference = FirebaseDatabase.getInstance().getReference("SuB Admin");
        firebaseAuth = FirebaseAuth.getInstance();


        cancel.setOnClickListener(this);
        driver_singup.setOnClickListener(this);
        driver_reg_image.setOnClickListener(this);
        user_signup.setOnClickListener(this);
        admin_signup.setOnClickListener(this);
        subadmin_singup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.cancel:
               final Intent cancel = new Intent(this, Home.class);
                startActivity(cancel);

                break;

            case R.id.driver_singup:
                final String Fullname = fullname.getText().toString();
                final String Email = email.getText().toString();
                String emailmatch = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String Phone = phone.getText().toString();
                final String Password = password.getText().toString();
                String Password1 = password1.getText().toString();
                final String License = license.getText().toString();
                final String NID = nid.getText().toString();
                final String Company = company.getText().toString();


                if (driver_reg_image == null) {
                    Toast.makeText(Registration.this, "Please Insert Your Image", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Image");
                }

                else if (Fullname.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter your Fullname", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Full Name is Empty" + " " + Fullname);
                } else if (Email.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Eamil Null" + " " + Email);
                }
                else if (!Email.matches(emailmatch))
                {
                    Toast.makeText(Registration.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }

                else if (Phone.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Phone" + " " + Phone);
                } else if (Phone.charAt(0) != '+' || Phone.charAt(1) != '8' || Phone.charAt(2) != '8' || Phone.charAt(3) != '0') {

                    Toast.makeText(Registration.this, "Must Be First 4 Digit=+880", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "First Position Number is" + " " + Phone);
                } else if (Phone.charAt(4) != '1') {
                    Toast.makeText(Registration.this, "5th Position Digit is = 1 ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "5th Position Number is" + " " + Phone);
                } else if (Phone.charAt(5) != '9' && Phone.charAt(5) != '8' &&
                        Phone.charAt(5) != '7' && Phone.charAt(5) != '3' &&
                        Phone.charAt(5) != '4' && Phone.charAt(5) != '5'
                        && Phone.charAt(5) != '6') {
                    Toast.makeText(Registration.this, "6th Position Digit is = 3 or 4 or 5 or 6 or 7 or 8 or 9 ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "6th Position Number is" + " " + Phone);

                } else if (Phone.length() != 14) {
                    Toast.makeText(Registration.this, "Number Must be 14 length with +880", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Phone Number Not =14" + " " + Phone);
                } else if (Password.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password" + " " + Password);
                } else if (Password1.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Conform Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password1" + " " + Password1);
                } else if (!Password1.equals(Password)) {
                    Toast.makeText(Registration.this, "Please Enter Same Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password  Password" + " " + Password + " " + Password1);
                } else if (Password.length() < 7) {
                    Toast.makeText(Registration.this, "Password Must be 6 grater than", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Password Length Not Grater then 7" + " " + Password);
                } else if (License.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your License", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "License" + " " + License);
                } else if (NID.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your NID Number", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "NID" + " " + NID);
                } else if (NID.length() != 10) {
                    Toast.makeText(Registration.this, "NID Card Number Must be 10", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "NID Card Number Must be 10" + " " + NID);
                } else if (Company.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Company Name Of Bus", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Company" + " " + Company);
                }
                else {
                    final Loading loading = new Loading(Registration.this);
                    loading.startLoading();


                    upload_image();

                    Log.d(TAG,"else");

                    firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Driver info = new Driver(
                                                Fullname,
                                                Email,
                                                Phone,
                                                Password,
                                                License,
                                                NID,
                                                Company,
                                                usertype


                                        );

                                        firebaseDatabase.getInstance().getReference("Bus Stop BD").child("Registration").child("Driver").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                FragmentManager manager = getSupportFragmentManager();
                                                FragmentTransaction transaction = manager.beginTransaction();
                                                login loginFragment = new login();
                                                transaction.replace(R.id.test2, loginFragment);
                                                transaction.addToBackStack(null);
                                                transaction.commit();

                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        loading.dismissDialog();
                                                    }
                                                },5000);


                                                Toast.makeText(Registration.this, "Registration is Complete ", Toast.LENGTH_SHORT).show();



                                            }


                                        });
                                    } else {
                                        Toast.makeText(Registration.this, "Already This Email has Registered ", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }



                break;

            case R.id.profile_image:
                choose_image();

                break;

            case R.id.user_signup:
                final String User_Fullname = fullname.getText().toString();
                final String User_Email = email.getText().toString();
                String User_emailmatch = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String User_Phone = phone.getText().toString();
                final String User_Password = password.getText().toString();
                String User_Password1 = password1.getText().toString();

                if (driver_reg_image == null) {
                    Toast.makeText(Registration.this, "Please Insert Your Image", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Image");
                }

                else if (User_Fullname.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter your Fullname", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Full Name is Empty" + " " + User_Fullname);
                } else if (User_Email.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Eamil Null" + " " + User_Email);
                }
                else if (!User_Email.matches(User_emailmatch))
                {
                    Toast.makeText(Registration.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }

                else if (User_Phone.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Phone" + " " + User_Phone);
                } else if (User_Phone.charAt(0) != '+' ||User_Phone.charAt(1) != '8' || User_Phone.charAt(2) != '8' || User_Phone.charAt(3) != '0') {

                    Toast.makeText(Registration.this, "Must Be First 4 Digit=+880", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "First Position Number is" + " " + User_Phone);
                } else if (User_Phone.charAt(4) != '1') {
                    Toast.makeText(Registration.this, "5th Position Digit is = 1 ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "5th Position Number is" + " " + User_Phone);
                } else if (User_Phone.charAt(5) != '9' && User_Phone.charAt(5) != '8' &&
                        User_Phone.charAt(5) != '7' && User_Phone.charAt(5) != '3' &&
                        User_Phone.charAt(5) != '4' && User_Phone.charAt(5) != '5'
                        && User_Phone.charAt(5) != '6') {
                    Toast.makeText(Registration.this, "6th Position Digit is = 3 or 4 or 5 or 6 or 7 or 8 or 9 ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "6th Position Number is" + " " + User_Phone);

                } else if (User_Phone.length() != 14) {
                    Toast.makeText(Registration.this, "Number Must be 14 length with +880", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Phone Number Not =14" + " " + User_Phone);
                } else if (User_Password.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password" + " " + User_Password);
                } else if (User_Password1.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Conform Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password1" + " " + User_Password1);
                } else if (!User_Password1.equals(User_Password)) {
                    Toast.makeText(Registration.this, "Please Enter Same Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password  Password" + " " + User_Password + " " + User_Password1);
                } else if (User_Password.length() < 7) {
                    Toast.makeText(Registration.this, "Password Must be 6 grater than", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Password Length Not Grater then 7" + " " + User_Password);
                }
                else {

                    Log.d(TAG,"else");
                    user_image();


                    firebaseAuth.createUserWithEmailAndPassword(User_Email, User_Password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        User info = new User(
                                                User_Fullname,
                                                User_Email,
                                                User_Phone,
                                                User_Password
                                        );
                                        firebaseDatabase.getInstance().getReference("Bus Stop BD").child("Registration").child("User")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {


                                                upload_image();
                                                Intent customerIntent = new Intent(Registration.this,Customer_MapsActivity.class);
                                                startActivity(customerIntent);

                                               /* FragmentManager manager = getSupportFragmentManager();
                                                FragmentTransaction transaction = manager.beginTransaction();
                                                login loginFragment = new login();
                                                transaction.replace(R.id.test2, loginFragment);
                                                transaction.addToBackStack(null);
                                                transaction.commit();*/


                                                Toast.makeText(Registration.this, "Registration is Complete ", Toast.LENGTH_SHORT).show();



                                            }


                                        });
                                    } else {
                                        Toast.makeText(Registration.this, "Already This Email has Registered ", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
                break;

            case R.id.subadmin_singup:

                final String SubAdmin_Fullname = fullname.getText().toString();
                final String SubAdmin_Email = email.getText().toString();
                String SubAdmin_emailmatch = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String SubAdmin_Phone = phone.getText().toString();
                final String SubAdmin_Password = password.getText().toString();
                String SubAdmin_Password1 = password1.getText().toString();
                final String SubAdmin_NID = nid.getText().toString();
                final String SubAdmin_Company = company.getText().toString();

                if (driver_reg_image == null) {
                    Toast.makeText(Registration.this, "Please Insert Your Image", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Image");
                }

                else if (SubAdmin_Fullname.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter your Fullname", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Full Name is Empty" + " " + SubAdmin_Fullname);
                } else if (SubAdmin_Email.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Eamil Null" + " " + SubAdmin_Email);
                }
                else if (!SubAdmin_Email.matches(SubAdmin_emailmatch))
                {
                    Toast.makeText(Registration.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }

                else if (SubAdmin_Phone.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Phone" + " " +SubAdmin_Phone);
                } else if (SubAdmin_Phone.charAt(0) != '+' || SubAdmin_Phone.charAt(1) != '8' || SubAdmin_Phone.charAt(2) != '8' || SubAdmin_Phone.charAt(3) != '0') {

                    Toast.makeText(Registration.this, "Must Be First 4 Digit=+880", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "First Position Number is" + " " + SubAdmin_Phone);
                } else if (SubAdmin_Phone.charAt(4) != '1') {
                    Toast.makeText(Registration.this, "5th Position Digit is = 1 ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "5th Position Number is" + " " + SubAdmin_Phone);
                } else if (SubAdmin_Phone.charAt(5) != '9' && SubAdmin_Phone.charAt(5) != '8' &&
                        SubAdmin_Phone.charAt(5) != '7' && SubAdmin_Phone.charAt(5) != '3' &&
                        SubAdmin_Phone.charAt(5) != '4' && SubAdmin_Phone.charAt(5) != '5'
                        && SubAdmin_Phone.charAt(5) != '6') {
                    Toast.makeText(Registration.this, "6th Position Digit is = 3 or 4 or 5 or 6 or 7 or 8 or 9 ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "6th Position Number is" + " " + SubAdmin_Phone);

                } else if (SubAdmin_Phone.length() != 14) {
                    Toast.makeText(Registration.this, "Number Must be 14 length with +880", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Phone Number Not =14" + " " + SubAdmin_Phone);
                } else if (SubAdmin_Password.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password" + " " + SubAdmin_Password);
                } else if (SubAdmin_Password1.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Conform Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password1" + " " + SubAdmin_Password1);
                } else if (!SubAdmin_Password1.equals(SubAdmin_Password)) {
                    Toast.makeText(Registration.this, "Please Enter Same Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password  Password" + " " + SubAdmin_Password + " " + SubAdmin_Password1);
                } else if (SubAdmin_Password.length() < 7) {
                    Toast.makeText(Registration.this, "Password Must be 6 grater than", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Password Length Not Grater then 7" + " " + SubAdmin_Password);
                }  else if (SubAdmin_NID.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your NID Number", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "NID" + " " + SubAdmin_NID);
                } else if (SubAdmin_NID.length() != 10) {
                    Toast.makeText(Registration.this, "NID Card Number Must be 10", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "NID Card Number Must be 10" + " " + SubAdmin_NID);
                } else if (SubAdmin_Company.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Company Name Of Bus", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Company" + " " + SubAdmin_Company);
                }
                else {



                    Log.d(TAG,"else");


                    firebaseAuth.createUserWithEmailAndPassword(SubAdmin_Email, SubAdmin_Password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Admin info = new Admin(
                                                SubAdmin_Fullname,
                                                SubAdmin_Email,
                                                SubAdmin_Phone,
                                                SubAdmin_Password,
                                                SubAdmin_NID,
                                                SubAdmin_Company
                                        );
                                        firebaseDatabase.getInstance().getReference("Bus Stop BD").child("Registration").child("Sub Admin").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                upload_image();
                                                /*FragmentManager manager = getSupportFragmentManager();
                                                FragmentTransaction transaction = manager.beginTransaction();
                                                login loginFragment = new login();
                                                transaction.replace(R.id.test2, loginFragment);
                                                transaction.addToBackStack(null);
                                                transaction.commit();*/


                                                Toast.makeText(Registration.this, "Registration is Complete ", Toast.LENGTH_SHORT).show();



                                            }


                                        });
                                    } else {
                                        Toast.makeText(Registration.this, "Already This Email has Registered ", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }

                break;

            case R.id.admin_signup:
                final String Admin_Fullname = fullname.getText().toString();
                final String Admin_Email = email.getText().toString();
               final String Admin_emailmatch = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String Admin_Phone = phone.getText().toString();
                final String Admin_Password = password.getText().toString();
               final String Admin_Password1 = password1.getText().toString();
                final String Admin_License = license.getText().toString();
                final String Admin_NID = nid.getText().toString();
                final String Admin_Company = company.getText().toString();

                if (driver_reg_image == null) {
                    Toast.makeText(Registration.this, "Please Insert Your Image", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Image");
                }

                else if (Admin_Fullname.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter your Fullname", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Full Name is Empty" + " " + Admin_Fullname);
                } else if (Admin_Email.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Eamil Null" + " " + Admin_Email);
                }
                else if (!Admin_Email.matches(Admin_emailmatch))
                {
                    Toast.makeText(Registration.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }

                else if (Admin_Phone.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Phone" + " " +Admin_Phone);
                } else if (Admin_Phone.charAt(0) != '+' || Admin_Phone.charAt(1) != '8' || Admin_Phone.charAt(2) != '8' || Admin_Phone.charAt(3) != '0') {

                    Toast.makeText(Registration.this, "Must Be First 4 Digit=+880", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "First Position Number is" + " " + Admin_Phone);
                } else if (Admin_Phone.charAt(4) != '1') {
                    Toast.makeText(Registration.this, "5th Position Digit is = 1 ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "5th Position Number is" + " " + Admin_Phone);
                } else if (Admin_Phone.charAt(5) != '9' && Admin_Phone.charAt(5) != '8' &&
                        Admin_Phone.charAt(5) != '7' && Admin_Phone.charAt(5) != '3' &&
                        Admin_Phone.charAt(5) != '4' && Admin_Phone.charAt(5) != '5'
                        && Admin_Phone.charAt(5) != '6') {
                    Toast.makeText(Registration.this, "6th Position Digit is = 3 or 4 or 5 or 6 or 7 or 8 or 9 ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "6th Position Number is" + " " + Admin_Phone);

                } else if (Admin_Phone.length() != 14) {
                    Toast.makeText(Registration.this, "Number Must be 14 length with +880", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Phone Number Not =14" + " " + Admin_Phone);
                } else if (Admin_Password.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password" + " " + Admin_Password);
                } else if (Admin_Password1.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Conform Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password1" + " " + Admin_Password1);
                } else if (!Admin_Password1.equals(Admin_Password)) {
                    Toast.makeText(Registration.this, "Please Enter Same Password", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Password  Password" + " " + Admin_Password + " " + Admin_Password1);
                } else if (Admin_Password.length() < 7) {
                    Toast.makeText(Registration.this, "Password Must be 6 grater than", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Password Length Not Grater then 7" + " " + Admin_Password);
                }  else if (Admin_NID.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your NID Number", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "NID" + " " + Admin_NID);
                } else if (Admin_NID.length() != 10) {
                    Toast.makeText(Registration.this, "NID Card Number Must be 10", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "NID Card Number Must be 10" + " " + Admin_NID);
                } else if (Admin_Company.isEmpty()) {
                    Toast.makeText(Registration.this, "Please Enter Your Company Name Of Bus", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Company" + " " + Admin_Company);
                }
                else {



                    Log.d(TAG,"else");


                    firebaseAuth.createUserWithEmailAndPassword(Admin_Email, Admin_Password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Admin info = new Admin(
                                                Admin_Fullname,
                                                Admin_Email,
                                                Admin_Phone,
                                                Admin_Password,
                                                Admin_License,
                                                Admin_NID,
                                                Admin_Company
                                        );
                                        firebaseDatabase.getInstance().getReference("Bus Stop BD").child("Registration").child("Admin").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                               upload_image();
                                                /* FragmentManager manager = getSupportFragmentManager();
                                                FragmentTransaction transaction = manager.beginTransaction();
                                                login loginFragment = new login();
                                                transaction.replace(R.id.test2, loginFragment);
                                                transaction.addToBackStack(null);
                                                transaction.commit();*/


                                                Toast.makeText(Registration.this, "Registration is Complete ", Toast.LENGTH_SHORT).show();



                                            }


                                        });
                                    } else {
                                        Toast.makeText(Registration.this, "Already This Email has Registered ", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }

                break;


        }




    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void upload_image()
    {
        if (filepath !=null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            StorageReference fileReference = mStorageRef_driver.child( System.currentTimeMillis()
                    + "." + getFileExtension(filepath));

            mUploadTask = fileReference.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //mProgressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(Registration.this, "Upload successful", Toast.LENGTH_LONG).show();
                            Driver_Image upload = new Driver_Image(mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getUploadSessionUri().toString());
                            String uploadId = mDatabaseRef_driver.push().getKey();
                            mDatabaseRef_driver.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //mProgressBar.setProgress((int) progress);
                        }
                    });
        }
        else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();

        }

    }

    //image for User method

    private void user_image()
    {
        if (filepath !=null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            StorageReference fileReference = mStorageRef_user.child( System.currentTimeMillis()
                    + "." + getFileExtension(filepath));

            mUploadTask = fileReference.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //mProgressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(Registration.this, "Upload successful", Toast.LENGTH_LONG).show();
                            Driver_Image upload = new Driver_Image(mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getUploadSessionUri().toString());
                            String uploadId = mDatabaseRef_user.push().getKey();
                            mDatabaseRef_user.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //mProgressBar.setProgress((int) progress);
                        }
                    });
        }
        else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();

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