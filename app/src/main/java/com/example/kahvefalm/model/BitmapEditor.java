package com.example.kahvefalm.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.util.Size;
import androidx.core.os.HandlerCompat;
import androidx.exifinterface.media.ExifInterface;

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

                ExifInterface exifInterface = null;

                try {

                    exifInterface = new ExifInterface(imagePath);

                }catch (Exception e){
                    e.printStackTrace();
                    e.getLocalizedMessage();
                }

                int oriantation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
                Matrix matrix = new Matrix();

                switch (oriantation){
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.setRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.setRotate(180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.setRotate(270);
                        break;
                }

                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
                Bitmap finalBitmap = Bitmap.createScaledBitmap(rotatedBitmap,960,540,true);

                ByteArrayOutputStream  byteArrayOutputStream = new ByteArrayOutputStream();
                finalBitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);

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
