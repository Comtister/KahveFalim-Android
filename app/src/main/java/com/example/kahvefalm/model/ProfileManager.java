package com.example.kahvefalm.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.example.kahvefalm.controllers.ProfileScreenController;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import java.lang.ref.WeakReference;

public class ProfileManager {

    private Context context;
    private Profile profile;
    private SharedPreferences sharedPreferences;
    private GoogleSignInClient mGoogleSignInClient;


    public ProfileManager(Context context){

        this.context = context;
        sharedPreferences = this.context.getSharedPreferences("Account",Context.MODE_PRIVATE);

    }

    public boolean isFirstLogin(){

        if(sharedPreferences.getInt("ilkGiris",1) == 1){
            return true;
        }else {
            return false;
        }


    }

    public void setFirstLogin(){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("ilkGiris",0);
        editor.commit();

    }

    public void saveAccount(Profile profile){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        editor.putString("Account",json);
        editor.commit();

    }

    public Profile getAccount(){

        Gson gson = new Gson();
        String json = sharedPreferences.getString("Account", null);
        Profile account = gson.fromJson(json, Profile.class);

        return account;

    }




        public void exitAuth(WeakReference<ProfileScreenController> profileScreenController){

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("941550661857-70ebctrk589jmfg8nhf9n200966n203n.apps.googleusercontent.com")
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(context, gso);


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("ilkGiris",1);
            editor.commit();
            FirebaseAuth.getInstance().signOut();
            signOut(profileScreenController);
        }

        private void signOut(final WeakReference<ProfileScreenController> profileScreenController) {

            mGoogleSignInClient.signOut()
                    .addOnSuccessListener((Activity)context, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            ProfileScreenController profileScreenControllerRefence = profileScreenController.get();
                            profileScreenControllerRefence.exitScreen();

                        }
                    });


        }









}
