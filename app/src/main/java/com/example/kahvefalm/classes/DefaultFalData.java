package com.example.kahvefalm.classes;

import android.net.Uri;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;


public class DefaultFalData implements Serializable {

    private Dictionary<Integer,byte[]> imageDatas;
    private ArrayList<Uri> imageDataURL;
    private String message;
    private String falTipi;

    public DefaultFalData(Dictionary<Integer,byte[]> imageDatas,String message,String falTipi){
        this.imageDatas = imageDatas;
        this.message = message;
        this.falTipi = falTipi;
    }

    public DefaultFalData(DefaultFalData ownDatas,ArrayList<Uri> imageDataURL){
       this(ownDatas.imageDatas,ownDatas.message,ownDatas.falTipi);
       this.imageDataURL = imageDataURL;
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
}
