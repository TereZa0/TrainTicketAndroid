package com.trainticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrainListAdapter extends RecyclerView.Adapter<TrainListAdapter.ViewHolder> {

    ArrayList<TrainDataSet> data;
    Context ctx;

    public TrainListAdapter(Context ctx, ArrayList<TrainDataSet> data) {
        this.data = data;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public TrainListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainListAdapter.ViewHolder holder, int position) {
        holder.trainname.setText(data.get(position).getName());
        holder.classname.setText(data.get(position).getTclasss());
        holder.capacity.setText(data.get(position).getCapacity());
        holder.trainid.setText(data.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView trainname,classname,capacity,trainid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trainid = itemView.findViewById(R.id.lbltrainid);
            trainname = itemView.findViewById(R.id.lbltrainname);
            classname = itemView.findViewById(R.id.lbltrainclass);
            capacity = itemView.findViewById(R.id.lbltraincapacity);
        }
    }
}
