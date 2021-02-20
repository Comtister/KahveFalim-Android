package com.example.kahvefalm.model;

import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kahvefalm.R;
import com.example.kahvefalm.activities.FalGosterActivity;

import java.util.ArrayList;

public class FallarListAdapter extends RecyclerView.Adapter<FallarListAdapter.FallarListViewHolder>  {

    private ArrayList<Pair<String,FalData>> datas;

    public FallarListAdapter(ArrayList<Pair<String,FalData>> datas){
        this.datas = datas;

    }

    @NonNull
    @Override
    public FallarListViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        final View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.fallar_list_layout,parent,false);



        final FallarListViewHolder holder = new FallarListViewHolder(card);

        card.findViewById(R.id.falCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(),FalGosterActivity.class);
                intent.putExtra("falDate",datas.get(holder.getLayoutPosition()).first);
                intent.putExtra("Data",datas.get(holder.getLayoutPosition()).second);
                parent.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FallarListViewHolder holder, int position) {

        holder.baslik.setText("Gönderi Tarihi : " + datas.get(position).first);


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
