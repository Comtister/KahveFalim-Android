package com.example.kahvefalm.model;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import androidx.core.content.FileProvider;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManager {

    private Context context;


    public FileManager(Context context){
        this.context = context;

    }

    public  File createImageFile() throws Exception{



        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+time+"_";
        File stortageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = null;

        image = File.createTempFile(imageFileName,".JPG",stortageDir);

        return image;


    }

    public String getPhotoPath(File file){
        return file.getAbsolutePath();
    }

    public Uri createUri(File file){

        return FileProvider.getUriForFile(context,"com.example.android.fileprovider",file);

    }



}
