package com.shamim.newbusstop.drawer_layout;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shamim.newbusstop.Driverresgistration;
import com.shamim.newbusstop.Home;
import com.shamim.newbusstop.R;
import com.shamim.newbusstop.User_Registration;

public class register extends Fragment implements View.OnClickListener {
    Button user, driver, admin, controller, subadmin, admin1, backhome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_drawerlayout, container, false);


        user = view.findViewById(R.id.user);
        driver = view.findViewById(R.id.driver);
        admin = view.findViewById(R.id.admin);
        admin1 = view.findViewById(R.id.admin1);
        backhome = view.findViewById(R.id.backhome);
        controller = view.findViewById(R.id.controller);
        subadmin = view.findViewById(R.id.subadmin);


        controller.setVisibility(View.INVISIBLE);
        controller.setEnabled(false);

        subadmin.setVisibility(View.INVISIBLE);
        subadmin.setEnabled(false);
        admin1.setVisibility(View.INVISIBLE);
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
        switch (view.getId()) {
            case R.id.user:

                Intent intent = new Intent(getActivity(), User_Registration.class);
                startActivity(intent);


                break;
            case R.id.admin:
                controller.setVisibility(View.VISIBLE);
                controller.setEnabled(true);

                admin1.setVisibility(View.VISIBLE);
                admin1.setEnabled(true);

                subadmin.setVisibility(View.VISIBLE);
                subadmin.setEnabled(true);



                admin.setVisibility(View.GONE);
                admin.setEnabled(false);

                user.setVisibility(View.GONE);
                user.setEnabled(false);

                driver.setVisibility(View.GONE);
                driver.setEnabled(false);

                break;

            case R.id.backhome:
                Intent back = new Intent(getActivity(), Home.class);
                startActivity(back);

                break;

            case R.id.driver:
                Intent intent1 = new Intent(getActivity(), Driverresgistration.class);
                startActivity(intent1);
                break;


            case R.id.admin1:
                Intent admin = new Intent(getActivity(), Driverresgistration.class);
                startActivity(admin);
                break;
        }

    }


}
