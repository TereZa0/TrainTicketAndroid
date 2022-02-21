package com.trainticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    DataBase db;
    EditText tbusername,tbpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load_comp();
    }

    private void load_comp(){
        //Init Db
        db = new DataBase(this);

        //Init Component
        tbpassword = findViewById(R.id.tbpassword);
        tbusername = findViewById(R.id.tbusername);

        //Create Table
        CreateTable();
    }

    private void CreateTable(){
        String table_payment, table_train, table_passenger, table_admin, table_order;
        table_passenger = "CREATE TABLE passenger (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "passport TEXT," +
                "nik TEXT," +
                "jk TEXT," +
                "email TEXT," +
                "hp TEXT," +
                "password TEXT)";
        table_admin = "CREATE TABLE admin (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT)";
        table_train = "CREATE TABLE train (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "origin TEXT," +
                "destination TEXT," +
                "timeleave TEXT," +
                "timearrive TEXT," +
                "date TEXT," +
                "carriagename TEXT," +
                "quota INTEGER," +
                "price INTEGER)";
        table_order = "CREATE TABLE order(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "passenger_id INTEGER," +
                "train_id INTEGER," +
                "dateorder TEXT," +
                "dateleave TEXT," +
                "chairnumber INT," +
                "amountorder INT," +
                "FOREIGN KEY (passenger_id) " +
                "REFERENCES passenger (id)," +
                "FOREIGN KEY (train_id) " +
                "REFERENCES trin (id))";
        table_payment = "CREATE TABLE payment(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "order_id INTEGER," +
                "is_paid INTEGER," +
                "FOREIGN KEY (order_id) " +
                "REFERENCES order (id))";

        try{
            db.ExecDDL(table_admin);
            db.message(this, "Table Admin Created");
        }
        catch (Exception ex){
            db.message(this, "Fail Creating Table Admin");
        }

        try{
            db.ExecDDL(table_order);
            db.message(this, "Table Order Created");
        }
        catch (Exception ex){
            db.message(this, "Fail Creating Table Order");
        }

        try{
            db.ExecDDL(table_passenger);
            db.message(this, "Table Passenger Created");
        }
        catch (Exception ex){
            db.message(this, "Fail Creating Table Passenger");
        }

        try{
            db.ExecDDL(table_payment);
            db.message(this, "Table Payment Created");
        }
        catch (Exception ex){
            db.message(this, "Fail Creating Table Payment");
        }

        try{
            db.ExecDDL(table_train);
            db.message(this, "Table Train Created");
        }
        catch (Exception ex){
            db.message(this, "Fail Creating Train Payment");
        }

        String query = "SELECT * FROM admin";
        String statement = "INSERT INTO admin (username,password) VALUES (" +
                "'Admin','123')";

        Cursor cs = db.ExecQuery(query);

        if (cs.getCount() == 0){
            if (db.ExecDDL(statement) == true) {
                db.message(this, "Admin has Created");
            }
        }

        String query2 = "SELECT * FROM passenger";
        String statement2 = "INSERT INTO passenger (name," +
                "passport," +
                "nik," +
                "jk," +
                "email," +
                "hp," +
                "password) VALUES ('asep','','123','L','asep12','0812','123')";

        Cursor cs2 = db.ExecQuery(query2);

        if (cs2.getCount() == 0){
            if (db.ExecDDL(statement2) == true) {
                db.message(this, "Passenger has Created");
            }
        }
    }

    public void login(View vw){
        String query = "SELECT * FROM admin WHERE username = '" + tbusername.getText().toString() + "'" +
                " AND password = '" + tbpassword.getText().toString() +"'";
        String query2 = "SELECT * FROM passenger WHERE email = '" + tbusername.getText().toString() +"' AND password = '" + tbpassword.getText().toString() +"'";

        Cursor cs = db.ExecQuery(query);

        if (cs.getCount() > 0){
            cs.moveToFirst();
            String username = cs.getString(1);

            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("USERNAME", username);
            intent.putExtra("ROLE",1);
            startActivity(intent);
        }
        else {
            Cursor cs2 = db.ExecQuery(query2);

            if(cs2.getCount() > 0){
                cs2.moveToFirst();
                String username = cs2.getString(1);

                Intent intent = new Intent(this, MainPage.class);
                intent.putExtra("USERNAME", username);
                intent.putExtra("ROLE",0);
                startActivity(intent);
            }
            else  {
                db.message(this, "Username or Password Are Incorrect");
            }
        }
    }
}