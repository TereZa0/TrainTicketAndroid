package com.trainticket;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

import androidx.annotation.Nullable;

public class TrainEdit extends Activity {

    DataBase db;
    EditText dtleavedate,dtleavetime,dtarrivetime,trainname,trainclass,quota,price;
    Spinner cbstart,cbend;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittrain);

        load_comp();
    }

    private void load_comp(){
        db = new DataBase(this);

        dtleavedate = findViewById(R.id.dtleavedateedit);
        dtleavetime = findViewById(R.id.dtstartedit);
        dtarrivetime = findViewById(R.id.dtendedit);

        cbstart = findViewById(R.id.cbtrainstartedit);
        cbend = findViewById(R.id.cbtrainendedit);

        trainname = findViewById(R.id.tbtrainnameedit);
        trainclass = findViewById(R.id.tbtrainclassedit);
        quota = findViewById(R.id.tbquotaedit);
        price = findViewById(R.id.tbpriceedit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.DestArray, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbstart.setAdapter(adapter);
        cbend.setAdapter(adapter);

        String train_id = getIntent().getStringExtra("TRAIN_ID");

        //db.message(this,train_id);

        String query = "SELECT * FROM train WHERE id = '" + train_id + "'";

        Cursor cs = db.ExecQuery(query);
        if (cs.getCount() == 1){
            cs.moveToFirst();

            trainname.setText(cs.getString(1));
            int position = adapter.getPosition(cs.getString(2));
            int positionend = adapter.getPosition(cs.getString(3));
            cbstart.setSelection(position);
            cbend.setSelection(positionend);

            dtleavetime.setText(cs.getString(4));
            dtarrivetime.setText(cs.getString(5));

            dtleavedate.setText(cs.getString(6));

            trainclass.setText(cs.getString(7));

            quota.setText(cs.getString(8));
            price.setText(cs.getString(9));
        }
    }

    public void StartClickEdit(View view){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dtleavetime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void EndClickEdit(View view){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dtarrivetime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void SetLeaveDateEdit(View view){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dtleavedate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void CommitEditrain(View view){
        String start,end,leave;
        leave = dtleavedate.getText().toString();

        Context ctx = this;

        new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setTitle("Update Data")
                .setMessage("Are you sure you want to update this Data?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String statement = "UPDATE train  SET name = '" + trainname.getText().toString() + "',origin = '" + cbstart.getSelectedItem().toString() + "'" +
                                ",destination = '" + cbend.getSelectedItem().toString() +"',timeleave = '" + dtleavetime.getText().toString() +
                                "',timearrive = '" + dtarrivetime.getText().toString() + "',date = '" + dtleavedate.getText().toString() + "'," +
                                "carriagename = '" + trainclass.getText().toString() + "',quota = '" + quota.getText().toString() + "'," +
                                "price = '" + price.getText().toString() + "' WHERE id = '" + getIntent().getStringExtra("TRAIN_ID") + "'";

                        if (dtleavetime.getText().toString() != "" && cbstart.getSelectedItem().toString() != "" && cbend.getSelectedItem().toString() != ""
                                && trainname.getText().toString() != "" && trainclass.getText().toString() != "" && quota.getText().toString() != "" && price.getText().toString() != ""){
                            if (db.ExecDDL(statement) == true){
                                db.message(ctx,"Data has Updated");
                                Intent intent = new Intent(ctx, MainPage.class);
                                startActivity(intent);
                            }
                            else {
                                db.message(ctx,"Error While Executing Statement");
                            }
                        }
                        else {
                            db.message(ctx,"Please Fill All The Data Before Commit");
                        }

                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public void DeleteTrain(View view) {

        String statement = "DELETE FROM train WHERE id = '" + getIntent().getStringExtra("TRAIN_ID") + "'";
        Context ctx = this;

        new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setTitle("Delete Data")
                .setMessage("Are you sure you want to delete this Data?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (dtleavetime.getText().toString() != "" && cbstart.getSelectedItem().toString() != "" && cbend.getSelectedItem().toString() != ""
                                && trainname.getText().toString() != "" && trainclass.getText().toString() != "" && quota.getText().toString() != "" && price.getText().toString() != ""){
                            if (db.ExecDDL(statement) == true){
                                db.message(ctx,"Data has Deleted");
                                Intent intent = new Intent(ctx, MainPage.class);
                                startActivity(intent);
                            }
                            else {
                                db.message(ctx,"Error While Executing Statement");
                            }
                        }
                        else {
                            db.message(ctx,"Please Fill All The Data Before Deleting");
                        }

                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
}
