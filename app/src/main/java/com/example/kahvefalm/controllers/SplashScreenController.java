package com.example.kahvefalm.controllers;

import android.content.Intent;
import android.os.Handler;
import com.example.kahvefalm.activities.MainActivity;
import com.example.kahvefalm.activities.ProfileActivity;
import com.example.kahvefalm.activities.SplashScreenActivity;
import com.example.kahvefalm.model.FirebaseAuthManager;
import com.example.kahvefalm.model.GoogleSignInManager;
import com.example.kahvefalm.model.NetworkManager;
import com.example.kahvefalm.model.Profile;
import com.example.kahvefalm.model.ProfileManager;
import com.example.kahvefalm.view.SplashScreenView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SplashScreenController {
    //Initialize view reference
    private SplashScreenView splashScreenView;
    //Initialize essential classes
    private GoogleSignInManager googleSignInManager;
    private FirebaseAuthManager firebaseAuthManager;
    private ProfileManager profileManager;
    private NetworkManager networkManager;

    private GoogleSignInAccount googleSignInAccount;

    public SplashScreenController(SplashScreenView splashScreenView){

        this.splashScreenView = splashScreenView;

        googleSignInManager = new GoogleSignInManager(splashScreenView.rootView.getContext());
        firebaseAuthManager = new FirebaseAuthManager(splashScreenView.rootView.getContext());
        profileManager = new ProfileManager(splashScreenView.rootView.getContext());
        networkManager = new NetworkManager(splashScreenView.rootView.getContext());
        MobileAds.initialize(splashScreenView.rootView.getContext());
    }

    public void setupGoogleSign(){

        if(networkManager.checkNet()){

        }else{
            splashScreenView.networkDialog();
            return;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

             googleSignInManager.googleSignInSetup();
             Intent intent = googleSignInManager.getSignInIntent();
             ((SplashScreenActivity) splashScreenView.rootView.getContext()).startActivityForResult(intent,googleSignInManager.RC_SIGN_IN);


            }
        },1000);


    }

    public void signInResult(int requestCode,int resultCode,Intent data){

        if(requestCode == googleSignInManager.RC_SIGN_IN){

            try {
               googleSignInAccount = googleSignInManager.singInTask(data);
               firebaseAuthManager.firebaseAuthTask(googleSignInAccount.getIdToken());
               setProfile(googleSignInAccount);

            } catch (Exception e) {
                e.printStackTrace();

                splashScreenView.popAlert();

            }

        }

    }

    private void setProfile(GoogleSignInAccount googleSignInAccount){

        if(profileManager.isFirstLogin()){

            Profile profile = new Profile(googleSignInAccount.getDisplayName(),googleSignInAccount.getEmail(),googleSignInAccount.getId());
            profileManager.saveAccount(profile);
            goProfileScreen();
        }else{
            goMainScreen();
        }


    }

    private void goProfileScreen(){

        Intent intent = new Intent(splashScreenView.rootView.getContext(), ProfileActivity.class);
        intent.putExtra("AnahtarG",0);
        splashScreenView.rootView.getContext().startActivity(intent);
        ((SplashScreenActivity) splashScreenView.rootView.getContext()).finish();

    }

    private void goMainScreen(){

        Intent intent = new Intent(splashScreenView.rootView.getContext(),MainActivity.class);
        splashScreenView.rootView.getContext().startActivity(intent);
        ((SplashScreenActivity) splashScreenView.rootView.getContext()).finish();
    }

    public void closeApp(){

        ((SplashScreenActivity) splashScreenView.rootView.getContext()).finish();
        System.exit(0);

    }

}
