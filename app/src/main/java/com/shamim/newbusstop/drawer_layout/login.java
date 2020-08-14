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

public class login extends Fragment {
    Button user, driver, admin,controller, subadmin,admin1, backhome;

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_drawerlayout, container, false);




return view;
    }

}
