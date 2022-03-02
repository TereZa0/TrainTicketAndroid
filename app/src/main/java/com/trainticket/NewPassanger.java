package com.trainticket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class NewPassanger extends Activity {
    EditText tbname,tbjk,tbemail,tbpassword;
    DataBase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_passenger);

        load_comp();
    }

    private void load_comp(){
        db = new DataBase(this);

        tbname = findViewById(R.id.tbnamepasssanger);
        tbjk = findViewById(R.id.tbgenderpassanger);
        tbemail = findViewById(R.id.tbemailpassanger);
        tbpassword = findViewById(R.id.tbpasswordpassanger);
    }

    public void CommitPassanger(View view){
        String ddl = "INSERT INTO passenger (name, jk, email, password) VALUES (" +
                "'" + tbname.getText().toString() + "','" + tbjk.getText().toString() +"'," +
                "'" + tbemail.getText().toString() +"','" + tbpassword.getText().toString() +"')";

        String name,password,email,jk;
        name = tbname.getText().toString();
        password = tbpassword.getText().toString();
        email = tbemail.getText().toString();
        jk = tbjk.getText().toString();

        if (name != "" && password != "" && email != "" && jk != ""){
            if (db.ExecDDL(ddl) == true){
                db.message(this, "Data Committed Succesfully");
                Intent intent = new Intent(this, Passengers.class);

                startActivity(intent);
            }
            else {
                db.message(this, "Data Commit Failed");
            }
        }
        else {
            db.message(this, "Please Fill All data Before Commit");
        }
    }

    public void BackPassanger(View view){
        Intent intent = new Intent(this, Passengers.class);

        startActivity(intent);
    }
}
