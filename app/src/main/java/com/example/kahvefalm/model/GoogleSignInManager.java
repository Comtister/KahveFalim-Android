package com.example.kahvefalm.model;

import android.content.Context;
import android.content.Intent;

import com.example.kahvefalm.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleSignInManager {

    private GoogleSignInClient mGoogleSignInClient;
    private Context context;

    public final int RC_SIGN_IN = 500;

    public GoogleSignInManager(Context context){
        this.context = context;
    }

    public void googleSignInSetup(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("941550661857-70ebctrk589jmfg8nhf9n200966n203n.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

    }

    public Intent getSignInIntent(){

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        return signInIntent;
    }


    public GoogleSignInAccount singInTask(Intent data) throws Exception{

        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

        GoogleSignInAccount account = task.getResult(ApiException.class);
        return account;



    }


}
