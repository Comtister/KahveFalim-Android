package com.example.kahvefalm.model;

import android.content.Context;




public class FirebaseManager extends NetworkManager {

    protected Profile profile;

    public FirebaseManager(Context context) {
        super(context);
        this.profile = new ProfileManager(context).getAccount();
    }



}
