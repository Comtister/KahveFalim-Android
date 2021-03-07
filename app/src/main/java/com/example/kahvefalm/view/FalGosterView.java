package com.example.kahvefalm.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.kahvefalm.R;
import com.example.kahvefalm.controllers.FalGosterController;
import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FalGosterView {

    private View rootView;
    private FalGosterController falGosterController;

    private TextView cevapText;
    TextView konuText;


    private ImageView image1;
    private ImageView image2;
    private ImageView image3;

    private MaterialToolbar toolbar;

    public FalGosterView(Context context , ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_fal_goster,viewGroup);
        initViews();
        falGosterController = new FalGosterController(this);
    }

    public void initViews(){

       cevapText = (TextView)rootView.findViewById(R.id.CevapText);
       konuText = (TextView)rootView.findViewById(R.id.FalkonuDetayText);


       image1 = (ImageView)rootView.findViewById(R.id.GelenFoto1);
       image2 = (ImageView)rootView.findViewById(R.id.GelenFoto2);
       image3 = (ImageView)rootView.findViewById(R.id.GelenFoto3);

       toolbar = (MaterialToolbar)rootView.findViewById(R.id.toolbarFalGoster);


    }

    private void setToolbar(String date){

        toolbar.setTitle(date);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                falGosterController.closeScreen();

            }
        });

    }

    public void setImages(ArrayList<String> imageUrls){

        System.out.println(imageUrls.get(0));
        System.out.println(imageUrls.get(1));
        System.out.println(imageUrls.get(2));

        Picasso.get().load(imageUrls.get(0).replaceAll("\\[","")).into(image1);
        Picasso.get().load(imageUrls.get(1).replaceAll(" ","")).into(image2);
        Picasso.get().load(imageUrls.get(2).replaceAll("]","").replaceAll(" ","")).into(image3);

    }

    public void setDetay(String message , String falTipi,String date){
        Log.i("Faltipi = ",falTipi);
        konuText.setText("Fal Konusu : " + falTipi);
        setToolbar(date);

    }


    public void setFalYorumu(String data){
        cevapText.setText(data);
    }

    public View getRootView() {
        return rootView;
    }
}
