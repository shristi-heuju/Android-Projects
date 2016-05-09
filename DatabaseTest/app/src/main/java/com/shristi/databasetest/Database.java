package com.shristi.databasetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dell on 4/23/2016.
 */
public class Database extends SQLiteOpenHelper {


    private static final String DATABASE_TABLE = "TABLE_DATABASE";
    private static final String TABLE_ID = "ID";
    private static final String TABLE_NAME = "NAME";
    private static final String TABLE_ADDRESS = "ADDRESS";

    public Database(Context context) {
        super(context, "database1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s(%s INTEGER PRIMARY KEY,%s TEXT,%s TEXT )", DATABASE_TABLE, TABLE_ID, TABLE_NAME, TABLE_ADDRESS);

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveData(String dataName, String dataAddress) {
        SQLiteDatabase db = getWritableDatabase();
        // db.delete(DATABASE_TABLE, null, null);


        ContentValues values = new ContentValues();

        values.put(TABLE_ID, 1);
        values.put(TABLE_NAME, dataName);
        values.put(TABLE_ADDRESS, dataAddress);

        db.insert(DATABASE_TABLE, null, values);

        db.close();

    }

    public String retrieveData() {

        SQLiteDatabase db = getReadableDatabase();

        String sql = String.format("SELECT %s from %s ORDER BY %s", TABLE_NAME, DATABASE_TABLE, TABLE_ID);
        String nameVal = "";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            nameVal = (cursor.getString(0));
        }
        db.close();

        return (nameVal);

    }
}
