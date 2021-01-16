package com.example.kahvefalm.activities;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kahvefalm.view.DfFalScreenView;

public class FalActivity extends AppCompatActivity  {

    DfFalScreenView dfFalScreenView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dfFalScreenView = new DfFalScreenView(this,null);
        setContentView(dfFalScreenView.getRootView());
        dfFalScreenView.initViews();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        dfFalScreenView.getDfFalScreenController().permissionResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dfFalScreenView.getDfFalScreenController().photoCameraResult(requestCode,resultCode,data);

    }


}
