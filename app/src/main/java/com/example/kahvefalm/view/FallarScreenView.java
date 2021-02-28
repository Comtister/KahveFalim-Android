package com.example.kahvefalm.view;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kahvefalm.R;
import com.example.kahvefalm.controllers.FallarScreenController;
import com.example.kahvefalm.model.FalData;
import com.example.kahvefalm.model.FallarListAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class FallarScreenView {

    private View rootView;
    private FallarScreenController fallarScreenController;
    private RecyclerView fallarList;
    private FallarListAdapter adapter;
    private String[] data;
    private ProgressBar progressBar;

    private MaterialToolbar toolbar;

    public FallarScreenView(Context context , ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_fallar,viewGroup);

        progressBar = (ProgressBar) rootView.findViewById(R.id.ProgresBarFallar);
        progressBar.setVisibility(View.GONE);

        this.fallarScreenController = new FallarScreenController(this);

        data = new String[5];

        data[0] = "asdas";
        data[1] = "Veri2";
        data[2] = "Veri3";


    }

    public void initViews(){

        fallarList = (RecyclerView) rootView.findViewById(R.id.fallarList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(),RecyclerView.VERTICAL,false);
        fallarList.setLayoutManager(layoutManager);

        toolbar = (MaterialToolbar)rootView.findViewById(R.id.toolbarFallar);
        setToolbar();

    }

    private void setToolbar(){

        toolbar.setTitle("Gönderilen Fallar");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fallarScreenController.closeFallarScreen();
            }
        });

    }

    public void setList(ArrayList<Pair<String,FalData>> data){

        adapter = new FallarListAdapter(data);
        fallarList.setAdapter(adapter);

    }

    public void setIndicator(boolean state){

    if(state){
        progressBar.setVisibility(View.VISIBLE);
    }else{
        progressBar.setVisibility(View.GONE);
    }

    }

    public View getRootView() {
        return rootView;
    }
}
