package com.android.juanjofp.ejercico2.models;

import java.util.ArrayList;

/**
 * Created by juanjofp on 18/10/16.
 */

public class BookFactory {

    public static final ArrayList<Book> BOOKS = new ArrayList<>();

    static {
        BOOKS.add(new Book("Titulo 1", "Resumen", "1"));
        BOOKS.add(new Book("Titulo 2", "Resumen", "1"));
        BOOKS.add(new Book("Titulo 3", "Resumen", "1"));
        BOOKS.add(new Book("Titulo 4", "Resumen", "1"));
        BOOKS.add(new Book("Titulo 5", "Resumen", "1"));
        BOOKS.add(new Book("Titulo 6", "Resumen", "1"));
        BOOKS.add(new Book("Titulo 7", "Resumen", "1"));
    };

    public static String TOKEN = null;
}
