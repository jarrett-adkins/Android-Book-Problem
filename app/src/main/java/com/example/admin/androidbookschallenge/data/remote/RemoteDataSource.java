package com.example.admin.androidbookschallenge.data.remote;

import com.example.admin.androidbookschallenge.model.Book;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource {

    public static final String BASE_URL = "http://de-coding-test.s3.amazonaws.com/";

    public static Retrofit create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( BASE_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .addCallAdapterFactory( RxJava2CallAdapterFactory.create() )
                .build();

        return retrofit;
    }

    public static Observable<List<Book>> getBooks() {
        Retrofit retrofit = create();
        RemoteService remoteService = retrofit.create( RemoteService.class );

        return remoteService.getBooks();
    }
}

/*
Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create();

Retrofit retrofit = new Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build();
 */