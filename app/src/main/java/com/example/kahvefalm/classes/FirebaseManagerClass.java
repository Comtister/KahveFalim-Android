package com.example.kahvefalm.classes;

import android.app.Activity;
import android.net.Uri;
import androidx.annotation.NonNull;
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
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class FirebaseManagerClass {


    private AccountProfile profile;
    private DefaultFalData defaultFalData;


    public FirebaseManagerClass(AccountProfile profile){
        this.profile = profile;
    }




    public class SendingManager{

        private Activity activity;

        private Dictionary<Integer,byte[]> imageDatas;

        private String date;
        private String[] filePath;
        private StorageReference[] fileName;
        final private ArrayList<Uri> uriS;
        private int sendCounter = 0;
        private String message;
        private String falTipi;

        private FirebaseStorage firebaseStorage;
        private StorageReference referenceParent;




      public SendingManager(Activity activity,Dictionary<Integer,byte[]> imageDatas,String message,String falTipi){



            this.activity = activity;
            this.falTipi = falTipi;
            this.message = message;
            this.imageDatas = imageDatas;
            this.date = new SimpleDateFormat("dd.MM.yy 'at' HH:mm:ss").format(new Date());
            this.filePath = new String[3];
            this.fileName = new StorageReference[3];
            this.uriS = new ArrayList<Uri>();
            this.firebaseStorage = FirebaseStorage.getInstance();
            this.referenceParent = firebaseStorage.getReference();

        }

        public void sendFal(){

            filePath[sendCounter] = profile.getId()+"/"+date+"/"+"foto"+sendCounter;
            fileName[sendCounter] = referenceParent.child(filePath[sendCounter]);

            final StorageReference currentReferance = fileName[sendCounter];
            UploadTask uploadTask = currentReferance.putBytes(imageDatas.get(sendCounter));

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!(task.isSuccessful())){
                        throw task.getException();
                    }
                    return currentReferance.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if(sendCounter == 2){
                        //Default fal objesi oluştup ve yolla
                        uriS.add(task.getResult());
                        DefaultFalData falData = new DefaultFalData(profile,uriS,message,falTipi);
                        sendData(falData);

                        sendCounter = 0;
                    }else {
                        uriS.add(task.getResult());
                        sendCounter++;
                        sendFal();
                    }


                }
            });

        }


        private void sendData(DefaultFalData falData){

            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

             DocumentReference reference = firestore.collection("gonderilen_fallar").document(falData.accountProfile.getId()).collection(date).document("Fal");

            Map<String,Object> data = new HashMap<>();
            data.put("id",falData.accountProfile.getId());
            data.put("isim",falData.accountProfile.getName());
            data.put("cinsiyet",falData.accountProfile.getCinsiyet());
            data.put("yas",falData.accountProfile.getYas());
            data.put("medeni_durum",falData.accountProfile.getMedeniDurum());
            data.put("ilgi",falData.falTipi);
            data.put("message",falData.message);
            data.put("mail",falData.accountProfile.getMail());
            data.put("images",falData.imageDataURL.toString());

             reference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void aVoid) {
                     //Okay Animation and finish activity

                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     //Alert Dialog Pop

                 }
             });

        }




    }





}
