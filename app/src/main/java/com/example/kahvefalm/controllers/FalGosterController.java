package com.example.kahvefalm.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.kahvefalm.activities.FalGosterActivity;
import com.example.kahvefalm.view.FalGosterView;

import java.util.ArrayList;

public class FalGosterController {

    FalGosterView falGosterView;


    public FalGosterController(FalGosterView falGosterView){

        this.falGosterView = falGosterView;


        Intent intent = ((FalGosterActivity)falGosterView.getRootView().getContext()).getIntent();
        Bundle dataBundle = intent.getBundleExtra("Datas");
        setDatas(dataBundle);

    }

    private void setDatas(Bundle datas){

        String falDate = datas.getString("falDate");
        ArrayList<String> imageUrlString = datas.getStringArrayList("imageUrls");
        String message = datas.getString("message");
        String falTipi = datas.getString("falTipi");
        String cevap = datas.getString("cevap");

        falGosterView.setDetay(message,falTipi,falDate);
        falGosterView.setFalYorumu(cevap);
        falGosterView.setImages(imageUrlString);
    }

    public void closeScreen(){

        ((FalGosterActivity)falGosterView.getRootView().getContext()).finish();

    }

}
