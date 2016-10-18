package com.android.juanjofp.ejercico2.models;

import java.io.Serializable;

/**
 * Created by juanjofp on 18/10/16.
 */

public class Book implements Serializable{

    private String mTitulo;
    private String mResumen;
    private String mISBN;

    public Book(String mTitulo, String mResumen, String mISBN) {
        this.mTitulo = mTitulo;
        this.mResumen = mResumen;
        this.mISBN = mISBN;
    }

    public String getmTitulo() {
        return mTitulo;
    }

    public String getmResumen() {
        return mResumen;
    }

    public String getmISBN() {
        return mISBN;
    }
}
