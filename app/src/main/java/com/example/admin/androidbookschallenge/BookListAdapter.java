package com.example.admin.androidbookschallenge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.androidbookschallenge.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 10/12/2017.
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    List<Book> bookList = new ArrayList<>();
    Context context;

    public BookListAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater
                .from( parent.getContext() )
                .inflate( R.layout.book_list_item, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = bookList.get( position );

        holder.authorName.setText( book.getAuthor() );
        holder.bookTitle.setText( book.getTitle() );

        Glide.with( context )
                .load( book.getImageURL() )
                .into( holder.cover );
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        TextView bookTitle, authorName;

        public ViewHolder(View itemView) {
            super(itemView);

            cover = itemView.findViewById( R.id.ivBookCover );
            bookTitle = itemView.findViewById( R.id.tvBookTitle );
            authorName = itemView.findViewById( R.id.tvBookAuthor );
        }
    }
}
