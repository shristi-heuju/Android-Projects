package com.shristi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String TABLE_ITEMS = "ITEMS";
    private static final String DATA = "DATA";


    public Database(Context context) {
        super(context, "slide.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql = String.format("create table %s(%s INTEGER PRIMARY KEY,%s TEXT,%s TEXT,%s INTEGER)", TABLE_ITEMS, ID, SLIDES, SLIDE_NAME, TIME_REQUIRED);
        String create_table = "CREATE TABLE " + TABLE_ITEMS + "("
                + DATA + " TEXT" + ")";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addAllItems(List<String> items) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_ITEMS, null, null);


        for (int i = 0; i < items.size(); i++) {
            ContentValues values = new ContentValues();


            values.put(DATA, items.get(i));


            db.insert(TABLE_ITEMS, null, values);
        }

        Cursor c = db.rawQuery((String.format("SELECT * FROM %s", TABLE_ITEMS)), null);

        db.close();
    }

    public ArrayList<String> getAllItems() {

        List<String> items = new ArrayList<String>();


        SQLiteDatabase db = getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", TABLE_ITEMS);

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            items.add(cursor.getString(0));
        }
        db.close();
        return (ArrayList<String>) items;

    }

    public void deleteAllItems() {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_ITEMS, null, null);

        db.close();

        //SetupDataActivity.items.clear();
        //SetupDataActivity.itemsSlideName.clear();
        //SetupDataActivity.itemsTimeRequired.clear();

    }

}
