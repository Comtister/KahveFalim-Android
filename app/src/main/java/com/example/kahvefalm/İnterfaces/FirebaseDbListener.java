package com.example.kahvefalm.İnterfaces;

import com.example.kahvefalm.enums.NetworkResult;

public interface FirebaseDbListener {

    void onFailureListener(NetworkResult result);
    void onSuccessListener(NetworkResult result);

}
