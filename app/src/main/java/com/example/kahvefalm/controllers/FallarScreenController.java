package com.example.kahvefalm.controllers;

import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.widget.ArrayAdapter;
import com.example.kahvefalm.enums.NetworkResult;
import com.example.kahvefalm.model.FalData;
import com.example.kahvefalm.model.FirebaseDbManager;
import com.example.kahvefalm.view.FallarScreenView;
import com.example.kahvefalm.İnterfaces.FirebaseFetchListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FallarScreenController {


    private FallarScreenView fallarScreenView;
    //private ArrayList<FalData> datas;
    private ArrayList<Pair<String,FalData>> datas;
    private ArrayList<String> docNames;

    public FallarScreenController(FallarScreenView fallarScreenView){

        this.fallarScreenView = fallarScreenView;
        datas = new ArrayList<>();
        docNames = new ArrayList<>();

        setDatas();
    }

    private void setDatas(){
        fallarScreenView.setIndicator(true);
        FirebaseDbManager.fetchManager fetchManager = new FirebaseDbManager(fallarScreenView.getRootView().getContext()).new fetchManager();
        fetchManager.fetchFals(new FirebaseFetchListener() {
            @Override
            public void onSuccesListener(NetworkResult result, ArrayList<Pair<String , Map<String , Object>>> data) {




                for(int i = 0 ; i < data.size() ; i++){
                    String[] imageBuffer = data.get(i).second.get("images").toString().split(",");

                    //docNames.add(data.get(i).first.split(" ")[0]);
                    ArrayList<Uri> imageUrls = new ArrayList<>();

                    for(int z = 0 ; z < imageBuffer.length ; z++){
                        imageUrls.add(Uri.parse(imageBuffer[z]));
                    }
                    FalData fetchData = new FalData(imageUrls,data.get(i).second.get("message").toString(),data.get(i).second.get("ilgi").toString());
                    System.out.println("OLUŞAN DATA" + fetchData.getImageDataURL());
                    FalData fetchData2 = new FalData(fetchData,data.get(i).second.get("cevap").toString());
                    System.out.println("OLUŞAN DATA" + fetchData2.getImageDataURL());
                    datas.add(new Pair<String, FalData>(data.get(i).first,fetchData2));
                    //imageUrls.clear();
                }


                fallarScreenView.setList(datas);
                fallarScreenView.setIndicator(false);


                /*
                ArrayList<Uri> imagesUrls = new ArrayList<>();

                String[] imageBuffer = data.get("images").toString().split(",");

                for(int i = 0 ; i<imageBuffer.length ; i++){
                    imagesUrls.add(Uri.parse(imageBuffer[i]));
                }


                FalData fetchData = new FalData(imagesUrls,data.get("message").toString(),data.get("ilgi").toString());
                datas.add(fetchData);

                fallarScreenView.setList(datas);*/

            }

            @Override
            public void onFailureListener(NetworkResult result) {

            }
        });

    }


}
