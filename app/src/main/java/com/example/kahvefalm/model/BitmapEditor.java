package com.example.kahvefalm.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Size;
import androidx.core.os.HandlerCompat;
import com.example.kahvefalm.İnterfaces.ByteImageProcessListener;
import com.example.kahvefalm.İnterfaces.ImageProccessListener;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BitmapEditor {

    Executor executor;
    Handler mainThreadHandler;

    public BitmapEditor(){

        this.executor = Executors.newSingleThreadExecutor();
        this.mainThreadHandler =  HandlerCompat.createAsync(Looper.getMainLooper());
    }

    public void createScaledBitmap(final String imagePath, final Size size, final ImageProccessListener imageProccessListener){

        executor.execute(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
                Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap,size.getWidth(),size.getHeight(),false);

                notifyController(imageProccessListener,newBitmap);

            }
        });
        

    }





    public void createByteArrayBitmap(final String imagePath, final ByteImageProcessListener byteImageProccessListener) throws Exception{

        executor.execute(new Runnable() {
            @Override
            public void run(){
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);

                ByteArrayOutputStream  byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);

                byte[] byteImage = byteArrayOutputStream.toByteArray();

                try {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                }catch (Exception e){

                }

                notifyController(byteImageProccessListener,byteImage);


            }
        });


    }


    private void notifyController(final ImageProccessListener imageProccessListener, final Bitmap bitmap){

        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                imageProccessListener.onComplete(bitmap);
            }
        });

    }

    private void notifyController(final ByteImageProcessListener byteImageProcessListener, final byte[] bytes){

        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                byteImageProcessListener.onComplete(bytes);
            }
        });

    }




}