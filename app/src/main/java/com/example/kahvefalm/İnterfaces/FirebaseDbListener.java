package com.example.kahvefalm.İnterfaces;

import com.example.kahvefalm.enums.NetworkResult;

import java.util.HashMap;

public interface FirebaseDbListener {

    void onFailureListener(NetworkResult result);
    void onSuccessListener(NetworkResult result);

    //void fetchSuccesListener(NetworkResult result , HashMap<String , Object> data);
}
