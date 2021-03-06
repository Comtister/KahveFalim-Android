package com.example.kahvefalm.model;

import android.net.Uri;
import android.widget.ArrayAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;

public class FalData implements Serializable {

    private Dictionary<Integer,byte[]> imageDatas;
    private ArrayList<Uri> imageDataURL;
    private String message;
    private String falTipi;
    private String cevap = "";

    public FalData(Dictionary<Integer,byte[]> imageDatas,String message,String falTipi){
        this.imageDatas = imageDatas;
        this.message = message;
        this.falTipi = falTipi;
    }

    public FalData(FalData ownDatas, ArrayList<Uri> imageDataURL){
        this(ownDatas.imageDatas,ownDatas.message,ownDatas.falTipi);
        this.imageDataURL = imageDataURL;
    }

    public FalData(ArrayList<Uri> imageDataURL , String message , String falTipi){
        this.imageDataURL = imageDataURL;
        this.message = message;
        this.falTipi = falTipi;
    }

    public FalData(FalData data , String cevap){
        this(data.imageDataURL,data.message,data.falTipi);
        this.cevap = cevap;
    }

    public Dictionary<Integer, byte[]> getImageDatas() {
        return imageDatas;
    }

    public ArrayList<Uri> getImageDataURL() {
        return imageDataURL;
    }

    public String getMessage() {
        return message;
    }

    public String getFalTipi() {
        return falTipi;
    }

    public String getCevap() {
        return cevap;
    }

    public ArrayList<String> getUrlString(){

        ArrayList<String> stringUrls = new ArrayList<>();

        for(int i = 0 ; i < this.imageDataURL.size() ; i++ ){

            stringUrls.add(this.imageDataURL.get(i).toString());

        }
        System.out.println("SINIFTAN DÖNEN" + stringUrls);
        return stringUrls;
    }

}
