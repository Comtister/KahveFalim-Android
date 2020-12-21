package com.example.kahvefalm.classes;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;

public class FirebaseManagerClass {


    private AccountProfile profile;
    private DefaultFalData defaultFalData;


    public FirebaseManagerClass(AccountProfile profile){
        this.profile = profile;
    }




    public class SendingManager{

        private Dictionary<Integer,byte[]> imageDatas;

        private String date;
        private String[] filePath;
        private StorageReference[] fileName;
        final private  Uri[] uriS;
        private int sendCounter = 0;

        private FirebaseStorage firebaseStorage;
        private StorageReference referenceParent;

      public SendingManager(Dictionary<Integer,byte[]> imageDatas){

            this.imageDatas = imageDatas;
            this.date = new SimpleDateFormat("dd.MM.yy 'at' HH:mm:ss").format(new Date());
            this.filePath = new String[3];
            this.fileName = new StorageReference[3];
            this.uriS = new Uri[3];
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
                        sendCounter = 0;
                    }else {
                        uriS[sendCounter] = task.getResult();
                        sendCounter++;
                        sendFal();
                    }


                }
            });

        }




    }





}
