package com.example.kahvefalm.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.kahvefalm.R;
import com.example.kahvefalm.controllers.FalGosterController;

public class FalGosterView {

    private View rootView;
    private FalGosterController falGosterController;

    public FalGosterView(Context context){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_fal_goster,null);
        falGosterController = new FalGosterController();
    }

    public void initViews(){



    }

    public View getRootView() {
        return rootView;
    }
}
