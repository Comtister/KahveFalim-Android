package com.example.kahvefalm.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.kahvefalm.activities.SplashScreenActivity;
import com.example.kahvefalm.R;
import com.example.kahvefalm.controllers.SplashScreenController;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SplashScreenView {

    //İnitialize fields
    public View rootView;
    SplashScreenController splashScreenController;

    //İnitialize View Objects
    private ImageView appIcon;

    public SplashScreenView(Context context, ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_splash_screen,viewGroup);
        splashScreenController = new SplashScreenController(this);

    }

    public void initViews(){

        appIcon = (ImageView)rootView.findViewById(R.id.splashIcon);

    }

    public void signRequest(){
        splashScreenController.setupGoogleSign();
    }

    public void signInResultRequest(int requestCode, int resultCode, Intent data){
        splashScreenController.signInResult(requestCode,resultCode,data);
    }


    public void popAlert(){

         MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this.rootView.getContext())
                .setTitle("Uyarı")
                .setMessage("Uygulamanın çalışabilmesi için google hesabınızı girmeniz" +
                        " gerekmektedir")
                .setNegativeButton("Uygulamadan Çık", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        splashScreenController.closeApp();

                    }
                })
                .setPositiveButton("Bağlan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        splashScreenController.setupGoogleSign();


                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                     @Override
                     public void onCancel(DialogInterface dialogInterface) {
                         splashScreenController.closeApp();
                     }
                 });

        materialAlertDialogBuilder.show();


    }

    public void networkDialog(){

       MaterialAlertDialogBuilder networkDialog = new MaterialAlertDialogBuilder(this.rootView.getContext());
        networkDialog.setTitle("Hata")
                .setMessage("Uygulamayı kullanabilmeniz için ağ bağlantınızın olması gerekir.")
                .setNeutralButton("Çıkış", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((SplashScreenActivity) rootView.getContext()).finish();
                        System.exit(0);
                    }
                });
        networkDialog.show();



    }





}
