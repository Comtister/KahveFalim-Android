package com.example.kahvefalm.controllers;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.kahvefalm.activities.FalActivity;
import com.example.kahvefalm.activities.MainActivity;
import com.example.kahvefalm.model.BitmapEditor;
import com.example.kahvefalm.model.FalData;
import com.example.kahvefalm.model.FileManager;
import com.example.kahvefalm.model.FirebaseDbManager;
import com.example.kahvefalm.view.DfFalScreenView;
import com.example.kahvefalm.enums.FalTipi;
import com.example.kahvefalm.enums.NetworkResult;
import com.example.kahvefalm.İnterfaces.ByteImageProcessListener;
import com.example.kahvefalm.İnterfaces.FirebaseDbListener;
import com.example.kahvefalm.İnterfaces.ImageProccessListener;
import java.io.File;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import static android.app.Activity.RESULT_OK;

public class DfFalScreenController {
    //required classes
    private DfFalScreenView dfFalScreenView;
    private FileManager fileManager;
    private BitmapEditor bitmapEditor;
    //used to determine chip tics
    private Dictionary<Integer,FalTipi> falTypeEquivalentChip;
    private FalTipi selectedFaltipi;
    //holding photoView frame id and photo file path
    private int photoHolderId;
    private String currentPhotoPath;
    //holding photos
    Dictionary<Integer,byte[]> imageDatas;
    //holding photo setup state
    boolean selectedControl[];


    public DfFalScreenController(DfFalScreenView dfFalScreenView){
        this.dfFalScreenView = dfFalScreenView;
        fileManager = new FileManager(dfFalScreenView.getRootView().getContext());
        bitmapEditor = new BitmapEditor();

        falTypeEquivalentChip = new Hashtable<>();
        imageDatas = new Hashtable<>();
        selectedControl = new boolean[3];

    }


    public void fotoYukle(int photoHolderId){

        this.photoHolderId = photoHolderId;
        requestPermissions();

    }



    private void requestPermissions(){

        if((ContextCompat.checkSelfPermission(dfFalScreenView.getRootView().getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                && ContextCompat.checkSelfPermission(dfFalScreenView.getRootView().getContext(),Manifest.permission_group.STORAGE) == PackageManager.PERMISSION_GRANTED){
            //Kameraya gitme işlemi
            gotoCamera();
        }else{
            Log.i("de11","ds12");
            ActivityCompat.requestPermissions(((FalActivity)dfFalScreenView.getRootView().getContext()),new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

    }

    public void permissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        if((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED) && (grantResults[2] == PackageManager.PERMISSION_GRANTED)){
            //Kameraya Gitme İşlemi
            gotoCamera();
        }else{
            //Dialog Açılıp Uyarı Gösterilecek

        }

    }


    private void gotoCamera(){

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,setPhotoFile());
            ((FalActivity)dfFalScreenView.getRootView().getContext()).startActivityForResult(cameraIntent,1);

        }catch (Exception e){
            //Error Message
            e.printStackTrace();
            e.getLocalizedMessage();
        }

    }

    public void photoCameraResult(int requestCode, int resultCode, @Nullable Intent data){

        if(requestCode == 1 && resultCode == RESULT_OK){

            Size photoHolderSize = dfFalScreenView.getPhotoHolderSize();
            final int[] photoHolderNumberBuffer = new int[1];


            bitmapEditor.createScaledBitmap(currentPhotoPath, photoHolderSize, new ImageProccessListener() {
                @Override
                public void onComplete(Bitmap bitmap) {
                   photoHolderNumberBuffer[0] = dfFalScreenView.setPhotoHolderImage(photoHolderId,bitmap);
                   selectedControl[photoHolderNumberBuffer[0]] = true;
                }
            });

            try {

               bitmapEditor.createByteArrayBitmap(currentPhotoPath, new ByteImageProcessListener() {
                   @Override
                   public void onComplete(byte[] data) {
                       imageDatas.put(photoHolderNumberBuffer[0],data);
                   }
               });



            } catch (Exception e) {
                //Error Message
                e.printStackTrace();
            }


        }

        

    }

    public void sendFal(){

        if(selectedControl[0] && selectedControl[1] && selectedControl[2]){
            dfFalScreenView.setProgressIndicatorVisible();
            String falMessage = dfFalScreenView.getMessageText();
            FalData falData = new FalData(imageDatas,falMessage,selectedFaltipi.toString());

            FirebaseDbManager.SendingManager sendingManager = new FirebaseDbManager(dfFalScreenView.getRootView().getContext()).new SendingManager();

            sendingManager.sendFal(falData, new FirebaseDbListener() {
                @Override
                public void onFailureListener(NetworkResult result) {
                    //Show Error Alert
                }

                @Override
                public void onSuccessListener(NetworkResult result) {
                    dfFalScreenView.setProgressIndicatorInvisible();
                    Intent intent = new Intent(dfFalScreenView.getRootView().getContext(), MainActivity.class);
                    ((FalActivity)dfFalScreenView.getRootView().getContext()).startActivity(intent);
                }
            });

        }else{
            //Show Alert
            dfFalScreenView.photoErrorDialog();
        }


    }

    private Uri setPhotoFile()throws Exception{

        File photoFile = fileManager.createImageFile();
        Uri photoFileUri = fileManager.createUri(photoFile);
        currentPhotoPath = fileManager.getPhotoPath(photoFile);
        return photoFileUri;
    }




    public void setFalTipi(int chipId){
        selectedFaltipi = falTypeEquivalentChip.get(chipId);
        Log.i("TYPE",selectedFaltipi.toString());
    }

    public void searchFalTipi(int checkedId){

        Enumeration nums = falTypeEquivalentChip.keys();

        for(int i = 0;i < falTypeEquivalentChip.size();i++){

            if((int)nums.nextElement() == checkedId){

                setFalTipi(checkedId);
                return;
            }

        }

    }

    public void setFalTypeEquivalentChip(int[] ids){

        FalTipi[] falTipleri;
        falTipleri = FalTipi.values();

        for(int i = 0;i < ids.length;i++){
            falTypeEquivalentChip.put(ids[i],falTipleri[i]);
        }

    }



    public void closeScreen(){
        ((FalActivity)dfFalScreenView.getRootView().getContext()).finish();
    }


}
