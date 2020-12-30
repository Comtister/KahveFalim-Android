package com.example.kahvefalm.activities;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.example.kahvefalm.R;
import com.example.kahvefalm.classes.AccountProfile;
import com.example.kahvefalm.classes.AccountProfileManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;



public class SplashScreenActivity extends AppCompatActivity{

    String name[];
    String basHarfEski;
    String basHarfYeni;

    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;

    final int RC_SIGN_IN = 500;
    final String TAG = "tag";

    MaterialAlertDialogBuilder materialAlertDialogBuilder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(!(checkNet())){
            Log.i("vay anamk","Net Yok");
        }

        name = new String[2];

        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("941550661857-70ebctrk589jmfg8nhf9n200966n203n.apps.googleusercontent.com")
                        .requestEmail()
                        .build();

                mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

                if(account != null){
                    //Kullanıcı önceden oturum açtı ise gizle
                    //updateUI(account);
                }else{

                }

                signIn();


            }
        },1000);


    }


    private boolean checkNet(){

        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        Network netx = connectivityManager.getActiveNetwork();
        NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(netx);
        boolean durum = nc == null ? false : true;

        return durum;

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {


            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }


    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
            AccountProfileManager profileManager = new AccountProfileManager(getApplicationContext());
            //Giriş Kontrolü 1:ilk giriş 0:sonraki girişler



            if(profileManager.isFirstAuth()){

                setAccount(account);

                Intent intent = new Intent(SplashScreenActivity.this,ProfileActivity.class);
                intent.putExtra("AnahtarG",0);
                startActivity(intent);
                finish();

            }else{

                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }


        } catch (ApiException e) {

            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

            popAlert();

        }
    }


    private void firebaseAuthWithGoogle(String token){

        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());


                        }


                    }
                });
    }





    private void popAlert(){

        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this)
                .setTitle("Uyarı")
                .setMessage("Uygulamanın çalışabilmesi için google hesabınızı girmeniz" +
                        " gerekmektedir")
                .setNegativeButton("Uygulamadan Çık", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .setPositiveButton("Bağlan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signIn();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                        System.exit(0);
                    }
                });
        materialAlertDialogBuilder.show();

    }

    //Setup profile object and saving
    private void setAccount(GoogleSignInAccount account){

        AccountProfile accountProfile = new AccountProfile(setName(account),account.getEmail(),account.getId());
        AccountProfileManager profileManager = new AccountProfileManager(accountProfile,getApplicationContext());
        profileManager.accountSave(accountProfile);

    }



    //Name firt name uppercased
    private String setName(GoogleSignInAccount account){

        name = account.getDisplayName().split(" ");
        basHarfEski = String.valueOf(name[0].charAt(0));
        basHarfYeni = basHarfEski.toUpperCase();
        System.out.println(name[0].replaceFirst(basHarfEski,basHarfYeni));
        return name[0].replaceFirst(basHarfEski,basHarfYeni);

    }



}