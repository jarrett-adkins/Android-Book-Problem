package com.example.admin.androidbookschallenge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.admin.androidbookschallenge.model.Book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 10/12/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

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
        SQLiteDatabase database = this.getWritableDatabase();

        Log.d(TAG, "saveBookList: Adding books to database");

        //parse object to content value object and add values to database
        ContentValues ct = new ContentValues();
        long l = 0l;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;// = new Date();

        for (int i = 0; i < books.size(); i++) {
            date = new Date();

            ct.put( COLUMN_TITLE, books.get(i).getTitle() );
            ct.put( COLUMN_AUTHOR, books.get(i).getAuthor() );
            ct.put( COLUMN_IMAGE_URL, books.get(i).getImageURL() );
            ct.put( COLUMN_CALL_TIME, dateFormat.format(date) );
            //strftime('%Y-%m-%d %H-%M','now');

            l = database.insert( TABLE_NAME, null, ct );
        }

        return l;
    }

    public void getLastCallTime() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery( query, null );
        Log.d(TAG, "getLastCallTime: " + cursor.getCount() );

        //loop through cursor, create person objects, add to list
        Log.d(TAG, "getLastCallTime: Query Results ");

        if( cursor.moveToFirst() ) {
            for (int i = 0; i < 5; i++) {
                Log.d(TAG, "getLastCallTime: " + cursor.getString(0));
                Log.d(TAG, "getLastCallTime: " + cursor.getString(1));
                Log.d(TAG, "getLastCallTime: " + cursor.getString(2));
                Log.d(TAG, "getLastCallTime: " + cursor.getString(3));
                Log.d(TAG, "getLastCallTime: " + cursor.getString(4));
                Log.d(TAG, "getLastCallTime: ");
                cursor.moveToNext();
            }
        }
        //first entry: 2017-10-12 23:40:00
        //last entry: 2017-10-12 23:40:22
    }
}
