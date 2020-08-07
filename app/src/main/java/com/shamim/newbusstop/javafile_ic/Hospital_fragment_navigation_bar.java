package com.shamim.newbusstop.javafile_ic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shamim.newbusstop.R;

public class Hospital_fragment_navigation_bar extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.hospital_fragment_navigation_bar,container,false);
    }
}
