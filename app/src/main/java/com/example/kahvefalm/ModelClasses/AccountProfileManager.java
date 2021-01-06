package com.example.kahvefalm.ModelClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.example.kahvefalm.Controllers.SplashScreenActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

public class AccountProfileManager extends NetworkManager {

    AccountProfile profile = null;
    SharedPreferences sharedPreferences;

    private GoogleSignInClient mGoogleSignInClient;

    public AccountProfileManager(Context context) {
        super(context);
        sharedPreferences = this.context.getSharedPreferences("Account",Context.MODE_PRIVATE);
    }

    public AccountProfileManager(AccountProfile profile,Context context) {
        super(context);
        this.profile = profile;
        sharedPreferences = this.context.getSharedPreferences("Account",Context.MODE_PRIVATE);


    }

    public void accountSave(AccountProfile profile){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        editor.putString("Account",json);
        editor.commit();


    }

    public boolean isFirstAuth(){

        if(sharedPreferences.getInt("ilkGiris",1) == 1){
            return true;
        }else {
            return false;
        }

    }

    public void setFirstAuth(){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("ilkGiris",0);
        editor.commit();

    }

    public void exitAuth(Activity activity){


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("941550661857-70ebctrk589jmfg8nhf9n200966n203n.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("ilkGiris",1);
        editor.commit();
        FirebaseAuth.getInstance().signOut();
        signOut(activity);
    }

    private void signOut(final Activity activity) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                            Intent intent = new Intent(context, SplashScreenActivity.class);
                            activity.startActivity(intent);
                    }
                });
    }


    public AccountProfile getAccount(){

        Gson gson = new Gson();
        String json = sharedPreferences.getString("Account", null);
        AccountProfile account = gson.fromJson(json, AccountProfile.class);

        return account;

    }



}
