package com.example.admin.androidbookschallenge.data.remote;

import com.example.admin.androidbookschallenge.model.Book;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RemoteService {

    @GET("books.json")
    Observable<List<Book>> getBooks();
}
