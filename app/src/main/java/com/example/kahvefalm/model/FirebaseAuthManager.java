package com.example.kahvefalm.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import static android.content.ContentValues.TAG;

public class FirebaseAuthManager {

    FirebaseAuth firebaseAuth;
    Context context;

    public FirebaseAuthManager(Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public void firebaseAuthTask(String profileToken){

        AuthCredential credential = GoogleAuthProvider.getCredential(profileToken, null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //FirebaseUser user = firebaseAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());


                        }


                    }
                });



    }



}
