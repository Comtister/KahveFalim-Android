package com.example.kahvefalm.controllers;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.example.kahvefalm.activities.MainActivity;
import com.example.kahvefalm.activities.ProfileActivity;
import com.example.kahvefalm.activities.SplashScreenActivity;
import com.example.kahvefalm.model.Profile;
import com.example.kahvefalm.model.ProfileManager;
import com.example.kahvefalm.view.ProfilScreenView;
import com.example.kahvefalm.enums.Cinsiyet;
import com.example.kahvefalm.enums.MedeniDurum;
import java.lang.ref.WeakReference;


public class ProfileScreenController {

    private ProfilScreenView profilScreenView;

    private ProfileManager profileManager;
    private Profile profile;

    private Cinsiyet accountCinsiyet;
    private MedeniDurum accountMedeniDurum;

    public ProfileScreenController(ProfilScreenView profilScreenView){

        this.profilScreenView = profilScreenView;
        profileManager = new ProfileManager(profilScreenView.getRootView().getContext());
    }

    public void closeProfilScreen(){
        ((ProfileActivity)profilScreenView.getRootView().getContext()).finish();
    }

    public void setupProfile(){

        profile = profileManager.getAccount();
        Intent intent = ((ProfileActivity)profilScreenView.getRootView().getContext()).getIntent();

        if(intent.getIntExtra("AnahtarG",1) == 0){
            profilScreenView.setLayoutFirstLogin(profile);
        }else{
            profilScreenView.setLayoutStandart(profile);
        }
    }

    public void saveProfile(){



        if(!(profilScreenView.getYasEditText().getText().toString().equals(""))){

            profileManager.setFirstLogin();
            int yas = Integer.parseInt(profilScreenView.getYasEditText().getText().toString());

            Profile account = new Profile(profile.getName(),profile.getMail(),profile.getId(),accountCinsiyet,yas,accountMedeniDurum);

            profileManager.saveAccount(account);

            Intent intent = new Intent(profilScreenView.getRootView().getContext(), MainActivity.class);
            ((ProfileActivity)profilScreenView.getRootView().getContext()).startActivity(intent);

        }else{

            profilScreenView.yasAlert();

        }

    }

    public void exitProfile(){
        profileManager.exitAuth(new WeakReference<ProfileScreenController>(this));

    }

    public void exitScreen(){

            Log.i("Durum","ASDAS");
            Intent intent = new Intent(profilScreenView.getRootView().getContext(), SplashScreenActivity.class);
            ((ProfileActivity)profilScreenView.getRootView().getContext()).startActivity(intent);
            ((ProfileActivity) profilScreenView.getRootView().getContext()).finish();

    }

    public void spinnerItemSelected(AdapterView<?> adapterView, View view, int i, long l){

        if(adapterView.getId() == profilScreenView.getCinsiyetSpinner().getId()){
            accountCinsiyet = Cinsiyet.values()[i];

        }

        if(adapterView.getId() == profilScreenView.getMedeniDurumSpinner().getId()){
            accountMedeniDurum = MedeniDurum.values()[i];
        }

        checkProfileState();
    }

    private void checkProfileState(){

        if(accountCinsiyet == Cinsiyet.DİĞER || accountMedeniDurum == MedeniDurum.Diğer){
            profilScreenView.setDurumImage(false);
        }else{
            profilScreenView.setDurumImage(true);
        }

    }


    public int BinarySearchStr(String[] array,String value){

        int index = 0;

        for(int i = 0;i < array.length;i++){
            if(array[i].equals(value)){
                index = i;
            }
        }

        return index;

    }

}
