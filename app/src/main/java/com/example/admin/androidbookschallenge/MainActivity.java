package com.example.admin.androidbookschallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.admin.androidbookschallenge.data.remote.RemoteDataSource;
import com.example.admin.androidbookschallenge.model.Book;
import com.example.admin.androidbookschallenge.services.MyIntentService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    RecyclerView recyclerView;
    List<Book> bookList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemAnimator itemAnimator;
    private BookListAdapter bookListAdapter;
    private DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the views
        recyclerView = findViewById( R.id.rvBookList );
        layoutManager = new LinearLayoutManager( this );
        itemAnimator = new DefaultItemAnimator();

        //check when last call was
        dbh = new DatabaseHelper( this );

        if( !dbh.readyToMakeCall() ) {
            //if more than 30 mins

            RemoteDataSource.getBooks()
                    .observeOn( AndroidSchedulers.mainThread() )
                    .subscribeOn(  Schedulers.io() )
                    .map(new Function<List<Book>, List<Book>>() {
                        @Override
                        public List<Book> apply(@NonNull List<Book> books) throws Exception {
                            for (Book book : books) {
                                String bookName = "Amazon Book: " + book.getTitle();
                                book.setTitle(bookName);
                            }

                            return books;
                        }
                    })
                    .subscribe(new Observer<List<Book>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            Log.d(TAG, "onSubscribe: ");
                        }

                        @Override
                        public void onNext(@NonNull List<Book> books) {
                            bookList = books;
                            Log.d(TAG, "onNext: Adding Books to database.");
                            long l = dbh.saveBookList( books );
                            Log.d(TAG, "onNext: Saved " + l + " books.");
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d(TAG, "onError: " + e.toString() );
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: " + Thread.currentThread() );

                            bookListAdapter = new BookListAdapter( bookList );
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(itemAnimator);
                            recyclerView.setAdapter( bookListAdapter );
                        }
                    });
        } else {
            //if less than 30 mins
            //pull books from the database.
            Log.d(TAG, "onCreate: Pulling Books from database");
            bookListAdapter = new BookListAdapter( dbh.getBookList() );
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(itemAnimator);
            recyclerView.setAdapter( bookListAdapter );
        }
    }
}

//10-13 10:33:34.309
//10-13 10:33:55.583
