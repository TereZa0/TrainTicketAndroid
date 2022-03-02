package com.trainticket;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Passengers extends Activity {

    DataBase db;
    PassangerListAdapter passangerListAdapter;
    ArrayList<PassangerDataSet> dataset = new ArrayList<PassangerDataSet>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passengers);

        load_comp();
        ReturnPass();
    }

    private void load_comp(){
        db = new DataBase(this);

        recyclerView = findViewById(R.id.recpassangers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void NewPassengers(View view){
        Intent intent = new Intent(this, NewPassanger.class);

        startActivity(intent);
    }

    public void BackPasList(View view){
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }

    private void ReturnPass(){
        String query = "SELECT * FROM passenger";

        Cursor cs = db.ExecQuery(query);

        if (cs.getCount() > 0){
            while (cs.moveToNext()){
                String name,email,jk;

                name = cs.getString(1);
                email = cs.getString(3);
                jk = cs.getString(2);

                dataset.add(new PassangerDataSet(name,email,jk));
            }
            passangerListAdapter = new PassangerListAdapter(dataset,this);
            recyclerView.setAdapter(passangerListAdapter);
            passangerListAdapter.notifyDataSetChanged();
        }
        else {
            db.message(this, "No Data in The Database");
        }
    }
}
