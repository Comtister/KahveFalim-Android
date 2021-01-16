package com.example.kahvefalm.model;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import com.example.kahvefalm.enums.NetworkResult;
import com.example.kahvefalm.İnterfaces.FirebaseDbListener;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FirebaseDbManager extends FirebaseManager {

    Executor executor;
    Handler mainThreadHandler;

    public FirebaseDbManager(Context context) {
        super(context);
        this.executor = Executors.newSingleThreadExecutor();
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    }


    public class SendingManager{

        private FalData data;
        private String date;
        private String[] filePath;
        private StorageReference[] fileName;
        final private ArrayList<Uri> uriS;
        private int sendCounter = 0;

        private FirebaseStorage firebaseStorage;
        private StorageReference referenceParent;


        public SendingManager(){

            this.date = new SimpleDateFormat("dd.MM.yy 'at' HH:mm:ss").format(new Date());
            this.filePath = new String[3];
            this.fileName = new StorageReference[3];
            this.uriS = new ArrayList<Uri>();

            this.firebaseStorage = FirebaseStorage.getInstance();
            this.referenceParent = firebaseStorage.getReference();

        }


        public void sendFal(FalData falData, FirebaseDbListener firebaseDbListener){

            if(!(checkNet())){
                //Show UI Error
                notifiyControllerFailure(firebaseDbListener,NetworkResult.netErr);
                return;
            }



            this.data = falData;
            sendHandler(firebaseDbListener);
        }

       private void sendHandler(final FirebaseDbListener firebaseDbListener){

           filePath[sendCounter] = profile.getId()+"/"+date+"/"+"foto"+sendCounter;
           fileName[sendCounter] = referenceParent.child(filePath[sendCounter]);

           final StorageReference currentReferance = fileName[sendCounter];
           UploadTask uploadTask = currentReferance.putBytes(data.getImageDatas().get(sendCounter));


           uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
               @Override
               public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                   if(!(task.isSuccessful())){
                       notifiyControllerFailure(firebaseDbListener,NetworkResult.photoUpFailed);

                   }
                   return currentReferance.getDownloadUrl();
               }
           }).addOnCompleteListener(new OnCompleteListener<Uri>() {
               @Override
               public void onComplete(@NonNull Task<Uri> task) {

                   if(sendCounter == 2){
                       //Default fal objesi oluştup ve yolla
                       uriS.add(task.getResult());
                       FalData falData = new FalData(data,uriS);
                       sendData(falData,firebaseDbListener);
                       sendCounter = 0;

                   }else {
                       uriS.add(task.getResult());
                       sendCounter++;
                       sendHandler(firebaseDbListener);

                   }


               }
           });


       }

        private void sendData(FalData falData, final FirebaseDbListener firebaseDbListener){

            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            DocumentReference reference = firestore.collection("gonderilen_fallar").document(profile.getId()).collection(date).document("Fal");

            Map<String,Object> data = new HashMap<>();
            data.put("id",profile.getId());
            data.put("isim",profile.getName());
            data.put("cinsiyet",profile.getCinsiyet());
            data.put("yas",profile.getYas());
            data.put("medeni_durum",profile.getMedeniDurum());
            data.put("ilgi",falData.getFalTipi());
            data.put("message",falData.getMessage());
            data.put("mail",profile.getMail());
            data.put("images",falData.getImageDataURL().toString());

            reference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    //Okay Animation and finish activity
                    notifiyControllerSuccess(firebaseDbListener,NetworkResult.dataUpSuccess);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Alert Dialog Pop
                    notifiyControllerFailure(firebaseDbListener,NetworkResult.dataUpFailed);
                }
            });

        }

        private void notifiyControllerSuccess(final FirebaseDbListener firebaseDbListener, final NetworkResult result){

            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    firebaseDbListener.onSuccessListener(result);
                }
            });

        }

        private void notifiyControllerFailure(final FirebaseDbListener firebaseDbListener, final NetworkResult result){

            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    firebaseDbListener.onFailureListener(result);
                }
            });

        }



    }

}
