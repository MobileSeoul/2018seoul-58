package com.walk.angel.angelwalk.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.walk.angel.angelwalk.Data.SightsData;
import com.walk.angel.angelwalk.Data.board.BoardData;
import com.walk.angel.angelwalk.R;

import java.util.ArrayList;
import java.util.List;

public class SightsAdapter extends RecyclerView.Adapter<SightViewHolder>{
    private Context context;
    private ArrayList<SightsData> arrayListOfSightData = new ArrayList<>();

    public SightsAdapter(ArrayList<SightsData> arrayListOfSightData){
        this.arrayListOfSightData = arrayListOfSightData;
    }

    @NonNull
    @Override
    public com.walk.angel.angelwalk.Adapter.SightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext() ;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View sightView = layoutInflater.inflate(R.layout.data_sights, parent, false);

        com.walk.angel.angelwalk.Adapter.SightViewHolder viewHolder = new com.walk.angel.angelwalk.Adapter.SightViewHolder(sightView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.walk.angel.angelwalk.Adapter.SightViewHolder holder, int position) {
        SightsData sightData = arrayListOfSightData.get(position);

        TextView txtName = holder.txtName;
        txtName.setText(sightData.getName());

        TextView txtAddress = holder.txtAddress;
        txtAddress.setText(sightData.getAddress());

        ImageView sightImageView = holder.sightImageView;
        Glide.with(context).load(sightData.getImage()).into(sightImageView);

    }

    @Override
    public int getItemCount() {
        return arrayListOfSightData.size();
    }


}

class SightViewHolder extends RecyclerView.ViewHolder{
    public TextView txtName;
    public TextView txtAddress;
    public ImageView sightImageView;

    public SightViewHolder(View itemView) {
        super(itemView);
        txtName = (TextView) itemView.findViewById(R.id.txtSightName);
        txtAddress = (TextView) itemView.findViewById(R.id.txtSightAddress);
        sightImageView = (ImageView) itemView.findViewById(R.id.imageView_sight);
    }
}

