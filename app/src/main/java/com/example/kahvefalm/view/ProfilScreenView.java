package com.example.kahvefalm.view;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.kahvefalm.R;
import com.example.kahvefalm.controllers.ProfileScreenController;
import com.example.kahvefalm.model.Helpers;
import com.example.kahvefalm.model.Profile;
import com.example.kahvefalm.enums.Cinsiyet;
import com.example.kahvefalm.enums.MedeniDurum;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ProfilScreenView implements AdapterView.OnItemSelectedListener {

    ProfileScreenController profileScreenController;

    private View rootView;

    private ImageView durumImage;

    private TextView isimView;
    private TextView mailView;

    private Spinner cinsiyetSpinner;
    private Spinner medeniDurumSpinner;

    private EditText yasEditText;

    private Button cikisBtn;
    private Button kaydetBtn;

    private MaterialToolbar toolbar;

    private ArrayAdapter cinsiyetSpinnerAdapter;
    private ArrayAdapter menediDurumSpinnerAdapter;

    private String[] cinsiyetDatas;
    private String[] medeniDurumDatas;


    public ProfilScreenView(Context context, ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_profile,viewGroup);
        profileScreenController = new ProfileScreenController(this);


    }

    public void initViews(){
        //Initialize Views
        durumImage = (ImageView)rootView.findViewById(R.id.imageView6);

        isimView = (TextView)rootView.findViewById(R.id.adView);
        mailView = (TextView)rootView.findViewById(R.id.mailView);

        cinsiyetSpinner = (Spinner)rootView.findViewById(R.id.cinsiyetSpinner);
        medeniDurumSpinner = (Spinner)rootView.findViewById(R.id.medeniDurumSpinner);

        yasEditText = (EditText)rootView.findViewById(R.id.yasEditText);

        cikisBtn = (Button)rootView.findViewById(R.id.button3);
        kaydetBtn = (Button)rootView.findViewById(R.id.button2);

        //Initialize toolbar
        toolbar = (MaterialToolbar)rootView.findViewById(R.id.toolbarProfile);
        setToolbar();

        //Initialize Spinner
        cinsiyetSpinner = (Spinner)rootView.findViewById(R.id.cinsiyetSpinner);
        medeniDurumSpinner = (Spinner)rootView.findViewById(R.id.medeniDurumSpinner);
        setSpinners();

        kaydetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileScreenController.saveProfile();
            }
        });

        cikisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileScreenController.exitProfile();
            }
        });

        loadProfile();

    }

    private void setToolbar(){

        toolbar.setTitle("Profil");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileScreenController.closeProfilScreen();
            }
        });


    }

    private void setSpinners(){

        cinsiyetDatas = new String[]{Cinsiyet.KADIN.toString(),Cinsiyet.ERKEK.toString(),Cinsiyet.DİĞER.toString()};
        cinsiyetSpinnerAdapter = new ArrayAdapter(rootView.getContext(),R.layout.support_simple_spinner_dropdown_item,cinsiyetDatas);
        cinsiyetSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cinsiyetSpinner.setAdapter(cinsiyetSpinnerAdapter);
        cinsiyetSpinner.setOnItemSelectedListener(this);

        medeniDurumDatas = new String[]{MedeniDurum.Bekar.toString(),MedeniDurum.Nisanli.toString(),MedeniDurum.Evli.toString(),MedeniDurum.Dul.toString()
                ,MedeniDurum.Ayri.toString(),MedeniDurum.Diğer.toString()};
        menediDurumSpinnerAdapter = new ArrayAdapter(rootView.getContext(),R.layout.support_simple_spinner_dropdown_item,medeniDurumDatas);
        menediDurumSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medeniDurumSpinner.setAdapter(menediDurumSpinnerAdapter);
        medeniDurumSpinner.setOnItemSelectedListener(this);
    }

    public void loadProfile(){
        profileScreenController.setupProfile();
    }

    public void setLayoutFirstLogin(Profile profile){
        isimView.setText(profile.getName());
        mailView.setText(profile.getMail());
        cikisBtn.setVisibility(View.GONE);

        toolbar.setVisibility(View.GONE);
        cinsiyetSpinner.setSelection(2);
        medeniDurumSpinner.setSelection(5);
    }

    public void setLayoutStandart(Profile profile){
        isimView.setText(profile.getName());
        mailView.setText(profile.getMail());
        cikisBtn.setVisibility(View.VISIBLE);

        yasEditText.setText(String.valueOf(profile.getYas()));

        cinsiyetSpinner.setSelection(Helpers.BinarySearchStr(cinsiyetDatas,profile.getCinsiyet().toString()));
        medeniDurumSpinner.setSelection(Helpers.BinarySearchStr(medeniDurumDatas,profile.getMedeniDurum().toString()));
    }

    public void yasAlert(){
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(rootView.getContext())
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

    public View getRootView() {
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

       profileScreenController.spinnerItemSelected(adapterView,view,i,l);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public Spinner getCinsiyetSpinner() {
        return cinsiyetSpinner;
    }

    public Spinner getMedeniDurumSpinner() {
        return medeniDurumSpinner;
    }

    public EditText getYasEditText() {
        return yasEditText;
    }

    public void setDurumImage(boolean state){

        if(state){
            durumImage.setImageResource(R.drawable.olumlu);
        }else{
            durumImage.setImageResource(R.drawable.olumsuz);
        }

    }
}
