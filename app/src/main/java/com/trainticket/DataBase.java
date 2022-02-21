package com.trainticket;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "Ticket";
    static final int DATABASE_VERSION = 1;
    SQLiteDatabase db;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean ExecDDL(String ddl){
        try {
            db.execSQL(ddl);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public Cursor ExecQuery(String query){
        try{
            return db.rawQuery(query, null);
        }
        catch (Exception ex){
            return null;
        }
    }

    public void message(Context ctx, String text){
        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
    }
}
