package com.android.juanjofp.masterdetails.models;

import com.orm.SugarRecord;

/**
 * Created by juanjofp on 18/10/16.
 */

public class Book extends SugarRecord {

    String title;
    String isbn;

    public Book() {
    }

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }
}
