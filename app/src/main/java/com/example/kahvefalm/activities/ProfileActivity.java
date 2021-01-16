package com.example.kahvefalm.activities;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kahvefalm.view.ProfilScreenView;

public class ProfileActivity extends AppCompatActivity  {

    ProfilScreenView profilScreenView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profilScreenView = new ProfilScreenView(this,null);
        profilScreenView.initViews();
        setContentView(profilScreenView.getRootView());

    }

}
