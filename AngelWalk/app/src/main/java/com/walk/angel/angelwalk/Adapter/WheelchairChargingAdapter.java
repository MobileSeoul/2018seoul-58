package com.walk.angel.angelwalk.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walk.angel.angelwalk.R;

import java.util.ArrayList;

public class WheelchairChargingAdapter extends RecyclerView.Adapter<WheelchairChargingViewHolder>{
    private Context context;
    private ArrayList<String> arrayListOfLocation = new ArrayList<>();
    private ArrayList<String> arrayListOfAddress = new ArrayList<>();

    public WheelchairChargingAdapter(ArrayList<String> arrayListOfLocation, ArrayList<String> arrayListOfAddress){
        this.arrayListOfLocation = arrayListOfLocation;
        this.arrayListOfAddress = arrayListOfAddress;
    }

    @NonNull
    @Override
    public WheelchairChargingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext() ;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View chargingView = layoutInflater.inflate(R.layout.data_wheelchair_charging, parent, false);

        WheelchairChargingViewHolder viewHolder = new WheelchairChargingViewHolder(chargingView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WheelchairChargingViewHolder holder, int position) {
        String LocationName = arrayListOfLocation.get(position);
        String LocationAddress = arrayListOfAddress.get(position);

        TextView txtName = holder.txtName;
        txtName.setText(LocationName);

        TextView txtAddress = holder.txtAddress;
        txtAddress.setText(LocationAddress);

    }

    @Override
    public int getItemCount() {
        return arrayListOfLocation.size();
    }


}

class WheelchairChargingViewHolder extends RecyclerView.ViewHolder{
    public TextView txtName;
    public TextView txtAddress;

    public WheelchairChargingViewHolder(View itemView) {
        super(itemView);
        txtName = (TextView) itemView.findViewById(R.id.txtChargingName);
        txtAddress = (TextView) itemView.findViewById(R.id.txtChargingAddress);
    }
}

