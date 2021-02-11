package com.example.kahvefalm.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kahvefalm.R;
import com.example.kahvefalm.view.FallarScreenView;

public class FallarActivity extends AppCompatActivity {

    FallarScreenView fallarScreenView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fallar);
        fallarScreenView = new FallarScreenView(this,null);
        setContentView(fallarScreenView.getRootView());
        fallarScreenView.initViews();
    }



}
