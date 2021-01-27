package com.shamim.newbusstop.drawer_layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shamim.newbusstop.Customer_MapsActivity;
import com.shamim.newbusstop.Forgotten_password;
import com.shamim.newbusstop.Home;
import com.shamim.newbusstop.R;

public class login extends Fragment implements View.OnClickListener {
    EditText login_email, login_password;
    Button login_forgotten_password, login_donot_account, login_login;

    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    SharedPreferences sharedPref;


    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_drawerlayout, container, false);


        //localy
        Context context = getActivity();
         sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        login_email = view.findViewById(R.id.login_email);
        login_password = view.findViewById(R.id.login_password);
        login_forgotten_password = view.findViewById(R.id.login_forgotten_password);
        login_donot_account = view.findViewById(R.id.login_donot_account);
        login_login = view.findViewById(R.id.login_login);

        firebaseAuth = FirebaseAuth.getInstance();

        login_forgotten_password.setOnClickListener(this);
        login_donot_account.setOnClickListener(this);
        login_login.setOnClickListener(this);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {


                }
            }
        };


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_donot_account:
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                register registerFragment = new register();
                transaction.replace(R.id.test2, registerFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;


            case R.id.login_forgotten_password:
                Intent login_forgotten_password = new Intent(getActivity(), Forgotten_password.class);
                startActivity(login_forgotten_password);

                break;

            case R.id.login_login:

                sign();


        }

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    private void sign() {

        final String Email = login_email.getText().toString();
        final String Password = login_password.getText().toString();
        String emailmatch = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (Email.isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter Your Valid Email", Toast.LENGTH_SHORT).show();
        }
        else if (!Email.matches(emailmatch))
        {
            Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
        }
        else if (Password.isEmpty())
        {
                Toast.makeText(getActivity(), "Please Enter Your Password", Toast.LENGTH_SHORT).show();

        }
            else {

                firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();


                            //here check user check thhen sharedPreference.


                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("userType","user");
                            editor.putString("userType","driver");

                            editor.commit();

                            Intent intToMain = new Intent(getActivity(),Customer_MapsActivity.class);

                            startActivity(intToMain);
                        } else {
                            Toast.makeText(getActivity(), "Email or Password Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }

}

