package com.example.kahvefalm.İnterfaces;

import android.util.Pair;

import com.example.kahvefalm.enums.NetworkResult;
import java.util.ArrayList;
import java.util.Map;


public interface FirebaseFetchListener {


    void onSuccesListener(NetworkResult result , ArrayList<Pair<String , Map<String , Object>>> data);
    void onFailureListener(NetworkResult result);


}
