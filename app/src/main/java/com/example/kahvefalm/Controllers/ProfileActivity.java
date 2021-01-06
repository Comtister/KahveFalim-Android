package com.example.kahvefalm.Controllers;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kahvefalm.R;
import com.example.kahvefalm.ModelClasses.AccountProfile;
import com.example.kahvefalm.ModelClasses.AccountProfileManager;
import com.example.kahvefalm.enums.MedeniDurum;
import com.example.kahvefalm.enums.Cinsiyet;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;




public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Intent intent;

    SharedPreferences sharedPreferences;

    MaterialToolbar toolbar;

    Spinner spinner;
    Spinner durumSpinner;

    TextView isimView;
    TextView mailView;

    EditText yasEditText;

    String cinsiyetler[];
    String medeniDurumlar[];

    MedeniDurum medeniDurum;
    Cinsiyet accountCinsiyet;
    int yas;

    AccountProfile gelenAccount;

    ArrayAdapter adapter;
    ArrayAdapter durumAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


       setToolbar();
       setSpinners();

        //Setting Views
        isimView = (TextView)findViewById(R.id.adView);
        mailView = (TextView)findViewById(R.id.mailView);
        yasEditText = (EditText)findViewById(R.id.yasEditText);

        //Get shared preference
        sharedPreferences = getSharedPreferences("Account",MODE_PRIVATE);

        //Set profile
        profilDoldur();






    }

    private void setSpinners(){

        //Setting Cinsiyet Spinner
        spinner = (Spinner)findViewById(R.id.cinsiyetSpinner);

        cinsiyetler = new String[]{Cinsiyet.KADIN.toString(),Cinsiyet.ERKEK.toString(),Cinsiyet.DİĞER.toString()};
        adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,cinsiyetler);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //Setting Durum Spinner

        durumSpinner = (Spinner)findViewById(R.id.medeniDurumSpinner);

        medeniDurumlar = new String[]{MedeniDurum.Bekar.toString(),MedeniDurum.Nisanli.toString(),MedeniDurum.Evli.toString(),MedeniDurum.Dul.toString()
                ,MedeniDurum.Ayri.toString(),MedeniDurum.Diğer.toString()};

        durumAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,medeniDurumlar);
        durumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durumSpinner.setAdapter(durumAdapter);
        durumSpinner.setOnItemSelectedListener(this);


    }

    private void setToolbar(){

        //Set Toolbar Settings
        toolbar = (MaterialToolbar)findViewById(R.id.toolbarProfile);

        toolbar.setTitle("Profil");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });



    }





    public void profilDoldur(){

        AccountProfileManager profileManager = new AccountProfileManager(getApplicationContext());

        gelenAccount = profileManager.getAccount();
        isimView.setText(profileManager.getAccount().getName());
        mailView.setText(profileManager.getAccount().getMail());

        intent = getIntent();

        //Hangi Ekranadan gelindiğinin kontrolü
        if(intent.getIntExtra("AnahtarG",1) == 0){
            //İlk Giriş Ekranı
            toolbar.setVisibility(View.INVISIBLE);
            spinner.setSelection(2);
            durumSpinner.setSelection(5);
        }else{

            try {
                yasEditText.setText(String.valueOf(profileManager.getAccount().getYas()));
                //Search index value current cinsiyet and durum value and set index spinner view
                spinner.setSelection(BinarySearchStr(cinsiyetler,profileManager.getAccount().getCinsiyet().toString()));

                durumSpinner.setSelection(BinarySearchStr(medeniDurumlar,profileManager.getAccount().getMedeniDurum().toString()));

            }catch (Exception e){
                e.printStackTrace();
            }

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView.getId() == spinner.getId()){
            accountCinsiyet = Cinsiyet.values()[i];

        }

        if(adapterView.getId() == durumSpinner.getId()){
            medeniDurum = MedeniDurum.values()[i];
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void saveProfile(View view){

        AccountProfileManager profileManager = new AccountProfileManager(getApplicationContext());
        //Set First Auth
        profileManager.setFirstAuth();

        try {

            if(!(yasEditText.getText().toString().equals(""))){
                yas = Integer.parseInt(yasEditText.getText().toString());

                AccountProfile account = new AccountProfile(gelenAccount.getName(),gelenAccount.getMail(),gelenAccount.getId(),accountCinsiyet,yas,medeniDurum);

                profileManager.accountSave(account);

                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);

            }else{

                yasAlert();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void exitProfile(View view){
        try {
            AccountProfileManager profileManager = new AccountProfileManager(getApplicationContext());
            profileManager.exitAuth(this);
        }catch (Exception e){
            e.printStackTrace();
            e.getLocalizedMessage();
        }


    }


    public void yasAlert(){

        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this)
                .setTitle("Uyarı")
                .setMessage("Lütfen Yaşınızı Belirtiniz")
                .setNeutralButton("Kapat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        materialAlertDialogBuilder.show();


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
