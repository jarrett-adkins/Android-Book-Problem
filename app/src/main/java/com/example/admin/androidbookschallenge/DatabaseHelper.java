package com.example.admin.androidbookschallenge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.admin.androidbookschallenge.model.Book;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 10/12/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;

    public static final String DATABASE_NAME = "Books.db";
    public static final String TABLE_NAME = "Books";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_AUTHOR = "Author";
    public static final String COLUMN_IMAGE_URL = "URL";
    public static final String COLUMN_CALL_TIME = "Time";
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Called when the database is created for the first time.

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( Id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, " +  COLUMN_AUTHOR + " TEXT, " + COLUMN_IMAGE_URL + " TEXT, "
                + COLUMN_CALL_TIME + " TEXT" + ")";

        sqLiteDatabase.execSQL( CREATE_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //called when version number is changed. Like if you edit the class to have a diff schema,
        //change version num to 2.

        Log.d("dbhelper", "onUpgrade: ");

        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate( sqLiteDatabase );
    }

    public long saveBookList( List<Book> books ) {
        Log.d(TAG, "saveBookList: Adding books to database");

        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate( database );

        //parse object to content value object and add values to database
        ContentValues ct = new ContentValues();
        long l = 0l;
        Calendar rightNow;

        for (int i = 0; i < books.size(); i++) {
            rightNow = Calendar.getInstance();

            ct.put( COLUMN_TITLE, books.get(i).getTitle() );
            ct.put( COLUMN_AUTHOR, books.get(i).getAuthor() );
            ct.put( COLUMN_IMAGE_URL, books.get(i).getImageURL() );
            ct.put( COLUMN_CALL_TIME, rightNow.getTimeInMillis() );

            l = database.insert( TABLE_NAME, null, ct );
        }

        return l;
    }

    public List<Book> getBookList() {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery( query, null );

        if( cursor.moveToFirst() ) {
            do {
                Book b = new Book(
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(2));

                bookList.add(b);
            } while (cursor.moveToNext());
        }

        return bookList;
    }

    public boolean readyToMakeCall() {
        SQLiteDatabase db = this.getWritableDatabase();
        int maxId = 0;

        String query = "SELECT MAX(ID) FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery( query, null );
        ///Log.d(TAG, "readyToMakeCall: number of results: " + cursor.getCount());

        if( cursor.moveToFirst() )
            maxId = cursor.getInt(0);

        //Log.d(TAG, "getLastCallTime: max id: " + maxId );

        query = "SELECT " + COLUMN_CALL_TIME + " FROM " + TABLE_NAME + " WHERE ID =" + maxId;
        cursor = db.rawQuery( query, null );

        long time = 0l;

        if( cursor.moveToFirst() ) {
            time = cursor.getLong(0);
            //Log.d(TAG, "getLastCallTime: " + time);
        }

        long now = Calendar.getInstance().getTimeInMillis();
        long diffInSeconds = (now - time) / 1000;
        //Log.d(TAG, "getLastCallTime: " + diffInSeconds);
        long diffInMinutes = diffInSeconds / 60;
        Log.d(TAG, "readyToMakeCall: minutes since last call: " + diffInMinutes);

        return diffInMinutes > 30;
    }
}
