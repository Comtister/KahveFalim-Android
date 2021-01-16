package com.example.kahvefalm.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.example.kahvefalm.view.SplashScreenView;

public class SplashScreenActivity extends AppCompatActivity{

    SplashScreenView splashScreenView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.splashScreenView = new SplashScreenView(this,null);
        setContentView(splashScreenView.rootView);
        splashScreenView.initViews();
        splashScreenView.signRequest();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        splashScreenView.signInResultRequest(requestCode,resultCode,data);
    }

/*
    //Name firt name uppercased
    private String setName(GoogleSignInAccount account){

        name = account.getDisplayName().split(" ");
        basHarfEski = String.valueOf(name[0].charAt(0));
        basHarfYeni = basHarfEski.toUpperCase();
        System.out.println(name[0].replaceFirst(basHarfEski,basHarfYeni));
        return name[0].replaceFirst(basHarfEski,basHarfYeni);

    }

            */

}