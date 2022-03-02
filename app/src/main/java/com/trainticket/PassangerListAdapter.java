package com.trainticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PassangerListAdapter extends RecyclerView.Adapter<PassangerListAdapter.ViewHolder> {

    ArrayList<PassangerDataSet> passangerDataSets;

    public PassangerListAdapter(ArrayList<PassangerDataSet> passangerDataSets, Context ctx) {
        this.passangerDataSets = passangerDataSets;
        this.ctx = ctx;
    }

    Context ctx;

    @NonNull
    @Override
    public PassangerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.passangerlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassangerListAdapter.ViewHolder holder, int position) {
        holder.lblname.setText(passangerDataSets.get(position).getName());
        holder.lblemail.setText(passangerDataSets.get(position).getEmail());
        holder.lbljk.setText(passangerDataSets.get(position).getJk());
    }

    @Override
    public int getItemCount() {
        return passangerDataSets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblname,lblemail,lbljk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lblname = itemView.findViewById(R.id.lblpassangername);
            lblemail = itemView.findViewById(R.id.lblpassangeremail);
            lbljk = itemView.findViewById(R.id.lblpassangerjk);
        }
    }
}
