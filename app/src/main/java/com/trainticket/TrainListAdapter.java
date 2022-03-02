package com.trainticket;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

import java.io.File;
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
        //Bitmap bm =

        holder.trainname.setText(data.get(position).getName());
        holder.classname.setText(data.get(position).getTclasss());
        holder.capacity.setText(data.get(position).getCapacity());
        holder.trainid.setText(data.get(position).getId());
        holder.trainimg.setImageURI(holder.uri);
        holder.trainimg.setImageBitmap(BitmapFactory.decodeFile(holder.uri.getPath() + data.get(position).getImages()));

        Toast.makeText(ctx, holder.uri + data.get(position).getImages(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView trainname,classname,capacity,trainid;
        ImageView trainimg;
        File fl = new File("Android/data/com.trainticket/files/Pictures");
        Uri uri = Uri.fromFile(fl);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trainid = itemView.findViewById(R.id.lbltrainid);
            trainname = itemView.findViewById(R.id.lbltrainname);
            classname = itemView.findViewById(R.id.lbltrainclass);
            capacity = itemView.findViewById(R.id.lbltraincapacity);
            trainimg = itemView.findViewById(R.id.trainimage);
        }
    }
}
