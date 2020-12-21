package com.example.kahvefalm.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.kahvefalm.R;
import com.example.kahvefalm.enums.FalTipi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class FalActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    ChipGroup chipGroup;
    Chip chip[];
    Chip selectedChip;
    Dictionary<Integer,FalTipi> chipList;

    ImageView imageView[];
    ImageView selectedView;

    FirebaseFirestore firestore;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fal);

        firestore = FirebaseFirestore.getInstance();

        toolbar = (MaterialToolbar) findViewById(R.id.toolbarFal);
        chipGroup = (ChipGroup) findViewById(R.id.chipGroup);

        chip = new Chip[4];

        chip[0] = (Chip) findViewById(R.id.chipGenel);
        chip[1] = (Chip) findViewById(R.id.chipAsk);
        chip[2] = (Chip) findViewById(R.id.chipPara);
        chip[3] = (Chip) findViewById(R.id.chipSaglık);

        chip[0].setChecked(true);

        chipList = new Hashtable<>();

        chipList.put(chip[0].getId(),FalTipi.GENEL);
        chipList.put(chip[1].getId(),FalTipi.ASK);
        chipList.put(chip[2].getId(),FalTipi.PARA);
        chipList.put(chip[3].getId(),FalTipi.SAGLIK);

        imageView = new ImageView[3];

        imageView[0] = (ImageView)findViewById(R.id.imageView1);
        imageView[1] = (ImageView)findViewById(R.id.imageView2);
        imageView[2] = (ImageView)findViewById(R.id.imageView3);





        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                    for(Chip chip : chip){
                        if(chip.getId() == checkedId){
                            selectedChip = chip;
                        }
                    }
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    public void fotoYukle(View view){

      selectedView =(ImageView)view;

      if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                //Kameraya gitme işlemi
                fotoCek();
      }else{
          Log.i("de11","ds12");
          ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},0);
      }

    }

    public void fotoCek(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {

            startActivityForResult(intent,1);
        }catch (Exception e){
            Log.e("Kamera Açma Hatası",null,e);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
           //Kameraya Gitme İşlemi
           fotoCek();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null) {

            try {

                Bundle extras = data.getExtras();
                Bitmap oldBitmap = (Bitmap) extras.get("data");
                int width = imageView[0].getDrawable().getIntrinsicWidth();
                int height = imageView[0].getDrawable().getIntrinsicHeight();
                Bitmap newBitmap = Bitmap.createScaledBitmap(oldBitmap,width,height,false);


                if(selectedView == imageView[0]) imageView[0].setImageBitmap(newBitmap);
                if(selectedView == imageView[1]) imageView[1].setImageBitmap(newBitmap);
                if(selectedView == imageView[2]) imageView[2].setImageBitmap(newBitmap);

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    public void gonder(View view){

        Map<String,Object> data = new HashMap<>();
        data.put("Ad","Oguz");
        //data.put("Cinsiyet","Erkek");
        data.put("Yaş", FieldValue.serverTimestamp());



        firestore.collection("giden_fallar").document("fal1")
                .set(data,SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });

    }


}
