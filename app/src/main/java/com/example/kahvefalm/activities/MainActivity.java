package com.example.kahvefalm.activities;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.kahvefalm.view.MainScreenView;



public class MainActivity extends AppCompatActivity{


    MainScreenView mainScreenView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainScreenView = new MainScreenView(this,null);
        setContentView(mainScreenView.getRootView());
        mainScreenView.initViews();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainScreenView.backPressListener();
    }



}