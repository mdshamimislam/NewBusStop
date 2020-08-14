package com.shamim.newbusstop.drawer_layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.shamim.newbusstop.Choose_Option;
import com.shamim.newbusstop.Driverresgistration;
import com.shamim.newbusstop.Home;
import com.shamim.newbusstop.R;

public class login extends Fragment implements View.OnClickListener {
    Button user, driver, admin,admin1,controller, subadmin, backhome;

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_drawerlayout, container, false);


        user = view.findViewById(R.id.user);
        driver = view.findViewById(R.id.driver);
        admin = view.findViewById(R.id.admin);
        backhome = view.findViewById(R.id.backhome);
        controller = view.findViewById(R.id.controller);
        subadmin = view.findViewById(R.id.subadmin);
        admin1=view.findViewById(R.id.admin1);


        controller .setVisibility(View.INVISIBLE);
        controller.setEnabled(false);

        subadmin .setVisibility(View.INVISIBLE);
       subadmin.setEnabled(false);

        admin1 .setVisibility(View.INVISIBLE);
        admin1.setEnabled(false);

        backhome.setOnClickListener(this);
        user.setOnClickListener(this);
        driver.setOnClickListener(this);
        admin.setOnClickListener(this);
        admin1.setOnClickListener(this);
       controller.setOnClickListener(this);
        subadmin.setOnClickListener(this);


return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.user:
                Intent home = new Intent(getActivity(), Home.class);
                startActivity(home);

                break;
            case R.id.admin:
                controller.setVisibility(View.VISIBLE);
                controller.setEnabled(true);

                subadmin .setVisibility(View.VISIBLE);
                subadmin.setEnabled(true);
               admin1 .setVisibility(View.VISIBLE);
                admin1.setEnabled(true);
                user.setVisibility(View.GONE);
                user.setEnabled(false);
                driver.setVisibility(View.GONE);
               driver.setEnabled(false);
                admin.setVisibility(View.GONE);
                admin.setEnabled(false);


                break;

            case R.id.backhome:
                Intent intent = new Intent(getActivity(), Home.class);
                startActivity(intent);

                break;
            case R.id.admin1:
                Intent intent1 = new Intent(getActivity(), Home.class);
                startActivity(intent1);

            case R.id.driver:
                Intent driver = new Intent(getActivity(), Driverresgistration.class);
                startActivity(driver);

                break;
        }

    }


}
