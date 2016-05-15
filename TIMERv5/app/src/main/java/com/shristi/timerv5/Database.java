package com.shristi.timerv5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 4/26/2016.
 */
public class Database extends SQLiteOpenHelper {

    private static final String TABLE_ITEMS = "ITEMS";
    private static final String ID = "ID";
    private static final String SLIDES = "SLIDES";
    private static final String SLIDE_NAME = "SLIDE_NAME";
    private static final String TIME_REQUIRED = "TIME_REQUIRED";

    public Database(Context context) {
        super(context, "slide.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql = String.format("create table %s(%s INTEGER PRIMARY KEY,%s TEXT,%s TEXT,%s INTEGER)", TABLE_ITEMS, ID, SLIDES, SLIDE_NAME, TIME_REQUIRED);
        String create_table = "CREATE TABLE " + TABLE_ITEMS + "("
                + ID + " INTEGER," + SLIDES + " TEXT," + SLIDE_NAME + " TEXT,"
                + TIME_REQUIRED + " INTEGER" + ")";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addAllItems(List<String> items, List<String> itemsSlideName, List<Integer> itemsTimeRequired) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_ITEMS, null, null);


        for (int i = 0; i < itemsSlideName.size(); i++) {
            ContentValues values = new ContentValues();

            values.put(ID, i);
            values.put(SLIDES, items.get(i));
            values.put(SLIDE_NAME, itemsSlideName.get(i));
            values.put(TIME_REQUIRED, itemsTimeRequired.get(i));

            db.insert(TABLE_ITEMS, null, values);
        }

        Cursor c = db.rawQuery((String.format("SELECT * FROM %s", TABLE_ITEMS)), null);

        db.close();
    }

    public void getAllItems() {

        List<String> items = new ArrayList<String>();
        List<String> itemsSlideName = new ArrayList<String>();
        List<Integer> itemsTimeRequired = new ArrayList<Integer>();

        SQLiteDatabase db = getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", TABLE_ITEMS);

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            items.add(cursor.getString(1));
            itemsSlideName.add(cursor.getString(2));
            itemsTimeRequired.add(cursor.getInt(3));
        }
        db.close();

        SetupDataActivity.items.addAll(items);
        SetupDataActivity.itemsSlideName.addAll(itemsSlideName);
        SetupDataActivity.itemsTimeRequired.addAll(itemsTimeRequired);
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
