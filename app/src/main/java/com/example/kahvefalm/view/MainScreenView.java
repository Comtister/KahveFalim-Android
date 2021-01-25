package com.example.kahvefalm.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.kahvefalm.activities.MainActivity;
import com.example.kahvefalm.R;
import com.example.kahvefalm.controllers.MainScreenController;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

public class MainScreenView implements NavigationView.OnNavigationItemSelectedListener{

    private View rootView;
    private MainScreenController mainScreenController;

    private MaterialCardView standartFalBtn;

    private MaterialToolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle toggle;




    public MainScreenView(Context context, ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_main,viewGroup);
        mainScreenController = new MainScreenController(this);

    }

    public void initViews(){
        //Initialize Views
        standartFalBtn = (MaterialCardView)rootView.findViewById(R.id.standartFalBtn);
        toolbar = (MaterialToolbar)rootView.findViewById(R.id.toolbarMain);
        navigationView = (NavigationView)rootView.findViewById(R.id.NavigationView);
        drawerLayout = (DrawerLayout)rootView.findViewById(R.id.drawerLayout);
        //Setting navigation and drawer
        navigationView.setNavigationItemSelectedListener(this);

        ((MainActivity)rootView.getContext()).setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(((MainActivity)rootView.getContext()),drawerLayout,toolbar,0,0);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Set ClickListeners

        standartFalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainScreenController.openFalScreen();
            }
        });




    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){

            case R.id.profil_action :
                mainScreenController.openProfileScreen();
                break;
            case R.id.fallarim_action :

                break;

            case R.id.hakkinda_action :

                break;

            case R.id.bizeulasin_action :

                break;

        }

        return false;

    }

    public void backPressListener(){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            mainScreenController.closeDrawer();
        }

    }

}
