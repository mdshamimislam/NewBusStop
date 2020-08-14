package com.shamim.newbusstop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Choose_Option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__option);
    }

    public void user(View view) {


    }

    public void driver(View view) {

    }

    public void admin(View view) {

    }

    public void backtoHome(View view) {
        Intent back = new Intent(Choose_Option.this, Home.class);
        startActivity(back);
    }
}