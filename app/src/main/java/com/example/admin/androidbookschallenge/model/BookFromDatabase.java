package com.example.admin.androidbookschallenge.model;

/**
 * Created by Admin on 10/13/2017.
 */

public class BookFromDatabase {

    String Title, Author, ImageURL;

    public BookFromDatabase(String title, String author, String imageURL) {
        Title = title;
        Author = author;
        ImageURL = imageURL;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
