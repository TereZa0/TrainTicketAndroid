package com.trainticket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainPage extends Activity implements AdapterView.OnItemSelectedListener {

    TextView lbluser;
    DataBase db;
    Spinner cbstart,cbend;
    ConstraintLayout editorlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        load_comp();
    }

    private void load_comp(){
        db = new DataBase(this);
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME").toString();

        lbluser = findViewById(R.id.lbluser);
        lbluser.setText(username);

        editorlay = findViewById(R.id.layouteditor);
        check_user();

        cbstart = findViewById(R.id.cbstart);
        cbend = findViewById(R.id.cbend);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.DestArray, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbstart.setAdapter(adapter);
        cbend.setAdapter(adapter);

        cbstart.setOnItemSelectedListener(this);
        cbend.setOnItemSelectedListener(this);
    }

    private void check_user(){
        int role = getIntent().getIntExtra("ROLE",0);

        if (role == 1){
            editorlay.setVisibility(View.VISIBLE);
        }
        else {
            editorlay.setVisibility(View.INVISIBLE);
        }
    }
    public void NewTrain(View view){
        Intent intent = new Intent(this, TrainList.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.cbstart){
            db.message(this, "Start: " + cbstart.getSelectedItem().toString());
        }
        else {
            db.message(this, "Destination: " + cbend.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void SetStartDate(View view){
        Message msg = new Message();
    }
}
