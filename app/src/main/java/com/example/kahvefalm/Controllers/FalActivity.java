package com.example.kahvefalm.Controllers;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.example.kahvefalm.R;
import com.example.kahvefalm.ModelClasses.AccountProfile;
import com.example.kahvefalm.ModelClasses.AccountProfileManager;
import com.example.kahvefalm.ModelClasses.DefaultFalData;
import com.example.kahvefalm.ModelClasses.FirebaseManagerClass;
import com.example.kahvefalm.enums.FalTipi;
import com.example.kahvefalm.enums.NetworkResult;
import com.example.kahvefalm.İnterfaces.Progress;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;


public class FalActivity extends AppCompatActivity implements Progress {

    MaterialToolbar toolbar;

    ChipGroup chipGroup;
    Chip chip[];
    Chip selectedChip;
    Dictionary<Integer,FalTipi> chipList;

    ImageView imageView[];
    ImageView selectedView;

    EditText message;
    String messageText;

    Dictionary<Integer,byte[]> imageDatas;
    boolean selectedControl[];

    ByteArrayOutputStream byteArrayOutputStream;
    String currentPhotoPath;

    AccountProfileManager accountProfileManager;
    AccountProfile accountProfile;

    MaterialAlertDialogBuilder networkDialog;
    MaterialAlertDialogBuilder photoDialog;

    ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fal);
        //Get profile
        accountProfileManager = new AccountProfileManager(this);
        accountProfile = accountProfileManager.getAccount();

        progressBar = (ProgressBar)findViewById(R.id.ProgresBar);

        progressBar.setVisibility(View.GONE);

        message = (EditText)findViewById(R.id.editTextMessage);

        //Image control array and imageBuffer array
        selectedControl = new boolean[3];
        imageDatas = new Hashtable<>();
        imageView = new ImageView[3];

        imageView[0] = (ImageView)findViewById(R.id.imageView1);
        imageView[1] = (ImageView)findViewById(R.id.imageView2);
        imageView[2] = (ImageView)findViewById(R.id.imageView3);



        chipsSetup();

        toolbar = (MaterialToolbar) findViewById(R.id.toolbarFal);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void chipsSetup(){


        chipGroup = (ChipGroup) findViewById(R.id.chipGroup);

        chip = new Chip[4];

        chip[0] = (Chip) findViewById(R.id.chipGenel);
        chip[1] = (Chip) findViewById(R.id.chipAsk);
        chip[2] = (Chip) findViewById(R.id.chipPara);
        chip[3] = (Chip) findViewById(R.id.chipSaglık);

        chip[0].setChecked(true);
        selectedChip = chip[0];

        chipList = new Hashtable<>();

        chipList.put(chip[0].getId(),FalTipi.GENEL);
        chipList.put(chip[1].getId(),FalTipi.AŞK);
        chipList.put(chip[2].getId(),FalTipi.PARA);
        chipList.put(chip[3].getId(),FalTipi.SAĞLIK);

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

    }


    public void fotoYukle(View view){

      selectedView =(ImageView)view;

      if((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
      && ContextCompat.checkSelfPermission(this,Manifest.permission_group.STORAGE) == PackageManager.PERMISSION_GRANTED){
                //Kameraya gitme işlemi
                fotoCek();
      }else{
          Log.i("de11","ds12");
          ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
      }

    }

    private File createImageFile() throws IOException{

        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+time+"_";
        File stortageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


        File image = File.createTempFile(imageFileName,".JPG",stortageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;


    }

    public void fotoCek(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();

        }catch (Exception e){
            Log.e("Kamera Açma Hatası",null,e);
        }
        if(photoFile != null){

            Uri photoUri = FileProvider.getUriForFile(this,"com.example.android.fileprovider",photoFile);
            System.out.println(photoUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
            startActivityForResult(intent,1);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED) && (grantResults[2] == PackageManager.PERMISSION_GRANTED)){
           //Kameraya Gitme İşlemi
           fotoCek();
        }else{
            //Dialog Açılıp Uyarı Gösterilecek


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null) {

            int width = imageView[0].getDrawable().getIntrinsicWidth();
            int height = imageView[0].getDrawable().getIntrinsicHeight();

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();

            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap,width,height,false);

            byteArrayOutputStream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);

            if(selectedView == imageView[0]){
                imageView[0].setImageBitmap(newBitmap);
                selectedControl[0] = true;
                imageDatas.put(0,byteArrayOutputStream.toByteArray());

                try {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(selectedView == imageView[1]){
                imageView[1].setImageBitmap(newBitmap);
                selectedControl[1] = true;
                imageDatas.put(1,byteArrayOutputStream.toByteArray());

                try {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(selectedView == imageView[2]){
                imageView[2].setImageBitmap(newBitmap);
                selectedControl[2] = true;
                imageDatas.put(2,byteArrayOutputStream.toByteArray());

                try {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        }

    }


    public void gonder(View view){

        if(selectedControl[0] && selectedControl[1] && selectedControl[2]){

            messageText = message.getText().toString();

            DefaultFalData data = new DefaultFalData(imageDatas,messageText,selectedChip.getText().toString());

            final FirebaseManagerClass.SendingManager sendingManager = new FirebaseManagerClass(getApplicationContext()).new SendingManager(this,data);

            sendingManager.sendFal();


        }else{
            //AlertDialog Pop
            photoDialogPop();
        }



    }

    private void networkDialogPop(){

        networkDialog = new MaterialAlertDialogBuilder(this);
        networkDialog.setTitle("Hata")
                .setMessage("Uygulamayı kullanabilmeniz için ağ bağlantınızın olması gerekir.")
                .setNeutralButton("Kapat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        networkDialog.show();

    }

    private void photoDialogPop(){

        photoDialog = new MaterialAlertDialogBuilder(this);
        photoDialog.setTitle("Uyarı")
                .setMessage("Lütfen 3 adet fotoğraf çekiniz.")
                .setNeutralButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        photoDialog.show();

    }


    @Override
    public void progressIndicator(int key) {

        if(key == 1){
            progressBar.setVisibility(View.VISIBLE);
        }else if(key == 0){
            progressBar.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public void successDialog() {

    }

    @Override
    public void failureDialog() {

    }


}
