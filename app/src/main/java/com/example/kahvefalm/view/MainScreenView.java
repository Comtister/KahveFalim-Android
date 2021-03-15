package com.example.kahvefalm.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.kahvefalm.activities.MainActivity;
import com.example.kahvefalm.R;
import com.example.kahvefalm.controllers.MainScreenController;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

public class MainScreenView implements NavigationView.OnNavigationItemSelectedListener{

    private View rootView;
    private MainScreenController mainScreenController;

    private MaterialCardView standartFalBtn;
    private MaterialCardView premiumFalBtn;

    private MaterialToolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle toggle;

    private AdView ustReklam;
    private AdView altReklam;


    public MainScreenView(Context context, ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_main,viewGroup);
        mainScreenController = new MainScreenController(this);

    }

    public void initViews(){
        //Initialize Views
        standartFalBtn = (MaterialCardView)rootView.findViewById(R.id.standartFalBtn);
        premiumFalBtn = (MaterialCardView)rootView.findViewById(R.id.premiumFalBtn);
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

        premiumFalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPreDialog();
            }
        });



    }

    public void setAds(){
        ustReklam = rootView.findViewById(R.id.adView);
        altReklam = rootView.findViewById(R.id.adView2);

    }

    public void loadAds(AdRequest adRequest){

        ustReklam.loadAd(adRequest);
        altReklam.loadAd(adRequest);

    }

    public View getRootView() {
        return rootView;
    }

    private void showPreDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(rootView.getContext());
        alert.setTitle("Ops");
        alert.setMessage("Yakında eklenecektir");
        alert.setNeutralButton("Kapat",null);
        alert.show();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){

            case R.id.profil_action :
                mainScreenController.openProfileScreen();
                break;
            case R.id.fallarim_action :
                mainScreenController.openFallarScreen();
                break;

            case R.id.hakkinda_action :
                mainScreenController.openHakkindaScreen();
                break;

            case R.id.bizeulasin_action :
                mainScreenController.openİletisimScreen();
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
