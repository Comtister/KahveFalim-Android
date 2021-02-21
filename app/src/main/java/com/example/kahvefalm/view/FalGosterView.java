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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FalGosterView {

    private View rootView;
    private FalGosterController falGosterController;

    private TextView cevapText;
    TextView konuText;
    TextView iletiText;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;

    public FalGosterView(Context context , ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_fal_goster,viewGroup);
        initViews();
        falGosterController = new FalGosterController(this);
    }

    public void initViews(){

       cevapText = (TextView)rootView.findViewById(R.id.CevapText);
       konuText = (TextView)rootView.findViewById(R.id.FalkonuDetayText);
       iletiText = (TextView)rootView.findViewById(R.id.FalMesajDetayText);

       image1 = (ImageView)rootView.findViewById(R.id.GelenFoto1);
       image2 = (ImageView)rootView.findViewById(R.id.GelenFoto2);
       image3 = (ImageView)rootView.findViewById(R.id.GelenFoto3);


    }

    public void setImages(ArrayList<String> imageUrls){

        System.out.println(imageUrls.get(0));
        System.out.println(imageUrls.get(1));
        System.out.println(imageUrls.get(2));

        Picasso.get().load(imageUrls.get(0).replaceAll("\\[","")).into(image1);
        Picasso.get().load(imageUrls.get(1).replaceAll(" ","")).into(image2);
        Picasso.get().load(imageUrls.get(2).replaceAll("]","").replaceAll(" ","")).into(image3);

    }

    public void setDetay(String message , String falTipi){
        Log.i("Faltipi = ",falTipi);
        konuText.setText(falTipi);
        iletiText.setText(message);

    }


    public void setFalYorumu(String data){
        cevapText.setText(data);
    }

    public View getRootView() {
        return rootView;
    }
}
