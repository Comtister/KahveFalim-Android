package com.example.kahvefalm.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kahvefalm.R;

import java.util.ArrayList;

public class FallarListAdapter extends RecyclerView.Adapter<FallarListAdapter.FallarListViewHolder>  {

    private ArrayList<FalData> datas;

    public FallarListAdapter(ArrayList<FalData> datas){
        this.datas = datas;

    }

    @NonNull
    @Override
    public FallarListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.fallar_list_layout,parent,false);

        FallarListViewHolder holder = new FallarListViewHolder(card);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FallarListViewHolder holder, int position) {

        holder.baslik.setText(datas.get(position).getMessage());


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class FallarListViewHolder extends RecyclerView.ViewHolder{

        private TextView baslik;
        private TextView detay;
        private TextView durum;

        public FallarListViewHolder(@NonNull View itemView) {
            super(itemView);

            baslik = (TextView) itemView.findViewById(R.id.fallarBaslik);
            detay = (TextView)itemView.findViewById(R.id.fallarKonu);
            durum = (TextView)itemView.findViewById(R.id.fallarDurum);
        }
    }



}
