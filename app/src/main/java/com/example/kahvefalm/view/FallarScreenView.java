package com.example.kahvefalm.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kahvefalm.R;
import com.example.kahvefalm.controllers.FallarScreenController;
import com.example.kahvefalm.model.FalData;
import com.example.kahvefalm.model.FallarListAdapter;

import java.util.ArrayList;

public class FallarScreenView {

    private View rootView;
    private FallarScreenController fallarScreenController;
    private RecyclerView fallarList;
    private FallarListAdapter adapter;
    private String[] data;

    public FallarScreenView(Context context , ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_fallar,viewGroup);
        this.fallarScreenController = new FallarScreenController(this);

        data = new String[5];

        data[0] = "asdas";
        data[1] = "Veri2";
        data[2] = "Veri3";


    }

    public void initViews(){


    }

    public void setList(ArrayList<FalData> data){

        fallarList = rootView.findViewById(R.id.fallarList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(),RecyclerView.VERTICAL,false);
        fallarList.setLayoutManager(layoutManager);
        adapter = new FallarListAdapter(data);
        fallarList.setAdapter(adapter);

    }

    public void setIndicator(boolean state){



    }

    public View getRootView() {
        return rootView;
    }
}
