package com.example.kahvefalm.ModelClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

public class NetworkManager {

   protected Context context;

   public NetworkManager(Context context){
        this.context = context;
    }

    public boolean checkNet(){

        ConnectivityManager connectivityManager =context.getSystemService(ConnectivityManager.class);
        Network netx = connectivityManager.getActiveNetwork();
        NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(netx);
        boolean durum = nc == null ? false : true;

        return durum;

    }


}
