package com.trainticket;

import android.app.Activity;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainPage extends Activity implements AdapterView.OnItemSelectedListener {

    TextView lbluser;
    DataBase db;
    Spinner cbstart,cbend;
    ConstraintLayout editorlay;
    private static String username;
    private static int role;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        load_comp();
        check_user();

        //VarBank vb = new VarBank(username,role);
        VarBank.setUserName(username);

        //role = getIntent().getIntExtra("ROLE",0);
        //VarBank.setUserRole(role);

        if (VarBank.getUserName() != null){
            username = VarBank.getUserName();
             if(username == "Admin"){
                 role = 1;
             }
             else {
                 role = 0;
             }
        }
        else {
            db.message(this, "Username is Null");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        //VarBank vb = new VarBank(username,role);
        //VarBank.setUserName(username);
        //VarBank.setUserRole(role);

        db.message(this, "Stopped");
    }

    @Override
    protected void onResume() {
        super.onResume();

        VarBank vb = new VarBank(username,role);

        db.message(this, "Resumed");
        if (VarBank.getUserName() != null){
            username = VarBank.getUserName();

            db.message(this, VarBank.getUserName());
            db.message(this, Integer.toString(VarBank.getUserRole()));
            if(VarBank.getUserRole() == 1){
                role = 1;
            }
            else {
                role = 0;
            }
        }
        else {
            db.message(this, "Username is Null");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.message(this, "Paused");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        db.message(this, "Restarted");
    }

    private void load_comp(){
        db = new DataBase(this);

        if (username == null) {
            Intent intent = getIntent();
            String username = intent.getStringExtra("USERNAME").toString();
            this.username = username;
        }

        lbluser = findViewById(R.id.lbluser);
        lbluser.setText(username);

        editorlay = findViewById(R.id.layouteditor);

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
        Intent intent = getIntent();
        int role = intent.getIntExtra("ROLE",1);
        this.role = role;
        new VarBank(username,this.role);
        db.message(this, "Roles: " + VarBank.getUserRole());

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

    public void Logout(View view){

        Intent intent = new Intent(this, MainActivity.class);

        new AlertDialog.Builder(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setTitle("Logout")
                .setMessage("Are you sure wanna logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        username = null;
                        role = 0;

                        startActivity(intent);

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
        .show();
    }

    public void Passengers(View view){
        Intent intent = new Intent(this, Passengers.class);

        startActivity(intent);
    }
}
