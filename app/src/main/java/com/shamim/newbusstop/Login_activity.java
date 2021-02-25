package com.shamim.newbusstop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_activity extends AppCompatActivity implements View.OnClickListener {
    TextView liginHere;
    EditText login_email, login_password;
    Button login_forgotten_password, login_donot_account, user_login,
            admin_login,subAdmin_login,driver_login;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    String login;
    String TAG= "Login_Activity";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        firebaseAuth = FirebaseAuth.getInstance();

        preferences = getSharedPreferences("BusBD_Info", Context.MODE_PRIVATE);
        editor = preferences.edit();


        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_forgotten_password = findViewById(R.id.login_forgotten);
        login_donot_account = findViewById(R.id.login_not_account);
        user_login = findViewById(R.id.user_login);
        admin_login = findViewById(R.id.admin_login);
        subAdmin_login = findViewById(R.id.subadmin_login);
        driver_login= findViewById(R.id.driver_login);
        liginHere = findViewById(R.id.login_here);

        Intent intent = getIntent();
        login=intent.getStringExtra("login");

        if (login.equals("customer") )
        {
            user_login.setVisibility(View.VISIBLE);
            driver_login.setVisibility(View.GONE);
            admin_login.setVisibility(View.GONE);
            subAdmin_login.setVisibility(View.GONE);

        }
        else if(login.equals("driver"))
        {
            user_login.setVisibility(View.GONE);
            driver_login.setVisibility(View.VISIBLE);
            admin_login.setVisibility(View.GONE);
            subAdmin_login.setVisibility(View.GONE);

        }
        else if(login.equals("admin"))
        {
            user_login.setVisibility(View.GONE);
            driver_login.setVisibility(View.GONE);
            admin_login.setVisibility(View.VISIBLE);
            subAdmin_login.setVisibility(View.GONE);

        }
        else if(login.equals("subadmin"))
        {
            user_login.setVisibility(View.GONE);
            driver_login.setVisibility(View.GONE);
            admin_login.setVisibility(View.GONE);
            subAdmin_login.setVisibility(View.VISIBLE);
        }

        login_forgotten_password.setOnClickListener(this);
        login_donot_account.setOnClickListener(this);
        user_login.setOnClickListener(this);
        admin_login.setOnClickListener(this);
        subAdmin_login.setOnClickListener(this);
        driver_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            /*case R.id.login_not_account:
                Intent login_not_account = new Intent(Login_activity.this, Registration.class);
                startActivity(login_not_account);
                break;*/


            case R.id.login_forgotten:

                Intent login_forgotten_password = new Intent(this, Forgotten_password.class);
                startActivity(login_forgotten_password);

                break;

            case R.id.user_login:

                User_sign();

                break;
            case R.id.driver_login:
                Driver_sign();

                break;

        }

    }

    private void User_sign() {

        final String Email = login_email.getText().toString();
        final String Password = login_password.getText().toString();
        String emailmatch = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (Email.isEmpty()) {
            Toast.makeText(this, "Please Enter Your Valid Email", Toast.LENGTH_SHORT).show();
        }
        else if (!Email.matches(emailmatch))
        {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }
        else if (Password.isEmpty())
        {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();

        }
        else {

            firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        //here check user check thhen sharedPreference.

                        preferences = getSharedPreferences("BusBD_Info", Context.MODE_PRIVATE);
                        editor = preferences.edit();
                        editor.putString("emailkey", Email);
                        editor.putString("passwordkey", Password);
                        editor.putBoolean("User_isLogged", true);
                        editor.commit();

                        Intent intToMain = new Intent(Login_activity.this,Customer_MapsActivity.class);
                        startActivity(intToMain);
                        Toast.makeText(Login_activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        finish();



                    } else {
                        Toast.makeText(Login_activity.this, "Email or Password Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void Driver_sign()
    {

        final String Email = login_email.getText().toString();
        final String Password = login_password.getText().toString();
        String emailmatch = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (Email.isEmpty()) {
            Toast.makeText(this, "Please Enter Your Valid Email", Toast.LENGTH_SHORT).show();
        }
        else if (!Email.matches(emailmatch))
        {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }
        else if (Password.isEmpty())
        {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();

        }
        else {
            Log.d(TAG,"driver_login");
            firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    Log.d(TAG,"driver_login"+task);
                    if (task.isSuccessful()) {



                        //here check user check thhen sharedPreference.

                        preferences = getSharedPreferences("BusBD_Info", Context.MODE_PRIVATE);
                        editor = preferences.edit();
                        editor.putString("emailkey", Email);
                        editor.putString("passwordkey", Password);
                        editor.putBoolean("Driver_isLogged", true);
                        editor.commit();


                        Intent drivermap = new Intent(Login_activity.this,Driver_maps_Activity.class);
                        startActivity(drivermap);
                        Toast.makeText(Login_activity.this, "Login Successful", Toast.LENGTH_SHORT).show();




                    }
                    else {


                        Toast.makeText(Login_activity.this, "Email or Password Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



}

