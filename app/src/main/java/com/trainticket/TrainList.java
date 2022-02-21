package com.trainticket;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TrainList extends Activity {

    DataBase db;
    TextView trainname,classname,capacity;
    RecyclerView recyclerView;
    ArrayList<TrainDataSet> trainDataSets = new ArrayList<TrainDataSet>();
    TrainListAdapter trainListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtrain);

        load_comp();
    }

    public void NewTrain(View view){
        Intent intent = new Intent(this, NewTrain.class);
        startActivity(intent);
    }

    public void load_comp(){
        db = new DataBase(this);

        trainname = findViewById(R.id.lbltrainname);
        classname = findViewById(R.id.lblclass);
        capacity = findViewById(R.id.lbltraincapacity);

        recyclerView = findViewById(R.id.rectrain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ReturnTrains();
    }

    public void ReturnTrains(){
        String query = "SELECT * FROM train";
        Cursor cs = db.ExecQuery(query);

        if (cs.getCount() > 0){
            while (cs.moveToNext()){
                String id,name,tclass,capacity;
                id = cs.getString(0);
                name = cs.getString(1);
                tclass = cs.getString(7);
                capacity = cs.getString(8);

                trainDataSets.add(new TrainDataSet(id,name,tclass,capacity));
            }
            trainListAdapter = new TrainListAdapter(this, trainDataSets);
            recyclerView.setAdapter(trainListAdapter);
            trainListAdapter.notifyDataSetChanged();
        }
        else {
            db.message(this,"There is no data detected");
        }
    }

    public void EditTrain(View view){
        TextView id,name,capacity,tclass;
        int x = recyclerView.getLayoutManager().getPosition(view);

        id = recyclerView.getLayoutManager().findViewByPosition(x).findViewById(R.id.lbltrainid);
        name = recyclerView.getLayoutManager().findViewByPosition(x).findViewById(R.id.lbltrainname);
        capacity = recyclerView.getLayoutManager().findViewByPosition(x).findViewById(R.id.lbltraincapacity);
        tclass = recyclerView.getLayoutManager().findViewByPosition(x).findViewById(R.id.lbltrainclass);

        Intent intent = new Intent(this, TrainEdit.class);
        intent.putExtra("TRAIN_ID",id.getText());
        startActivity(intent);
    }
}
