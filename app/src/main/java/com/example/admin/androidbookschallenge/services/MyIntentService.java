package com.example.admin.androidbookschallenge.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.admin.androidbookschallenge.model.Book;

import java.util.ArrayList;
import java.util.List;

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentServiceTag";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
