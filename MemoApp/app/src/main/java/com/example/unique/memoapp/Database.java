package com.example.unique.memoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class Database {

    private static final String TAG = com.example.unique.memoapp.Database.class.getSimpleName();


    // table configuration
    private static final String TABLE_NAME = "MemoTable";         // Table name
    private static final String ID = "_id";     // a column named "_id" is required for cursor
    private static final String Col_Title = "Title";
    private static final String Col_Note= "Note";

    private com.example.unique.memoapp.Database openHelper;
    private SQLiteDatabase database;

    // this is a wrapper class. that means, from outside world, anyone will communicate with PersonDatabaseHelper,
    // but under the hood actually DatabaseOpenHelper class will perform database CRUD operations
    public Database(Context aContext) {

        openHelper = new com.example.unique.memoapp.Database(aContext);

    }

    public void insertData (String title, String note) {

        // we are using ContentValues to avoid sql format errors

        ContentValues contentValues = new ContentValues();

        contentValues.put(Col_Title, title);
        contentValues.put(Col_Note, note);

        database.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAllData () {

        String buildSQL = "SELECT * FROM " + TABLE_NAME;

        Log.d(TAG, "getAllData SQL: " + buildSQL);

        return database.rawQuery(buildSQL, null);
    }



    public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context aContext) {
        super(aContext, "memo.dp", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create your tables here

        String buildSQL =String.format("create table %s (%s INTEGER PRIMARY KEY,%s  TEXT,%s  TEXT)",
                TABLE_NAME,ID,Col_Title,Col_Note);

        Log.d(TAG, "onCreate SQL: " + buildSQL);

        sqLiteDatabase.execSQL(buildSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Database schema upgrade code goes here

        String buildSQL = "DROP TABLE IF EXISTS " + TABLE_NAME;

        Log.d(TAG, "onUpgrade SQL: " + buildSQL);

        sqLiteDatabase.execSQL(buildSQL);       // drop previous table

        onCreate(sqLiteDatabase);               // create the table from the beginning
    }
}
}