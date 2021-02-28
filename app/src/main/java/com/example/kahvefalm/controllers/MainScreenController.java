package com.example.kahvefalm.controllers;

import android.content.Intent;

import com.example.kahvefalm.activities.FalActivity;
import com.example.kahvefalm.activities.FallarActivity;
import com.example.kahvefalm.activities.HakkindaActivity;
import com.example.kahvefalm.activities.MainActivity;
import com.example.kahvefalm.activities.ProfileActivity;
import com.example.kahvefalm.activities.UlasinActivity;
import com.example.kahvefalm.view.FallarScreenView;
import com.example.kahvefalm.view.MainScreenView;

public class MainScreenController {

    MainScreenView mainScreenView;

    public MainScreenController(MainScreenView mainScreenView){
        this.mainScreenView = mainScreenView;
    }

    public void closeDrawer(){

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((MainActivity)mainScreenView.getRootView().getContext()).startActivity(intent);

    }

    public void openProfileScreen(){

        Intent intent = new Intent(((MainActivity)mainScreenView.getRootView().getContext()), ProfileActivity.class);
        ((MainActivity)mainScreenView.getRootView().getContext()).startActivity(intent);


    }

    public void openFalScreen(){

        Intent intent = new Intent(((MainActivity)mainScreenView.getRootView().getContext()), FalActivity.class);
        ((MainActivity)mainScreenView.getRootView().getContext()).startActivity(intent);



    }

    public void openFallarScreen(){

        Intent intent = new Intent(((MainActivity)mainScreenView.getRootView().getContext()), FallarActivity.class);
        ((MainActivity)mainScreenView.getRootView().getContext()).startActivity(intent);

    }

    public void openHakkindaScreen(){

        Intent intent = new Intent(((MainActivity)mainScreenView.getRootView().getContext()), HakkindaActivity.class);
        ((MainActivity)mainScreenView.getRootView().getContext()).startActivity(intent);

    }

    public void openİletisimScreen(){

        Intent intent = new Intent(((MainActivity)mainScreenView.getRootView().getContext()), UlasinActivity.class);
        ((MainActivity)mainScreenView.getRootView().getContext()).startActivity(intent);

    }



}
