package com.franco.juanjo.pac1.tema2.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by juanjo on 25/9/16.
 */

public class BookContent {

    public static final ArrayList<Book> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(new Book("isbn1", "Titulo libro 1", "Autor 1", new Date(), "Resumen titulo  libro 1", "http://google.com?q=libros"));
        ITEMS.add(new Book("isbn1", "Titulo libro 2", "Autor 2", new Date(), "Resumen titulo  libro 2", "http://google.com?q=libros"));
        ITEMS.add(new Book("isbn1", "Titulo libro 3", "Autor 3", new Date(), "Resumen titulo  libro 3", "http://google.com?q=libros"));
        ITEMS.add(new Book("isbn1", "Titulo libro 4", "Autor 4", new Date(), "Resumen titulo  libro 4", "http://google.com?q=libros"));
        ITEMS.add(new Book("isbn1", "Titulo libro 5", "Autor 5", new Date(), "Resumen titulo  libro 5", "http://google.com?q=libros"));
        ITEMS.add(new Book("isbn1", "Titulo libro 6", "Autor 6", new Date(), "Resumen titulo  libro 6", "http://google.com?q=libros"));
        ITEMS.add(new Book("isbn1", "Titulo libro 7", "Autor 7", new Date(), "Resumen titulo  libro 7", "http://google.com?q=libros"));
        ITEMS.add(new Book("isbn1", "Titulo libro 8", "Autor 8", new Date(), "Resumen titulo  libro 8", "http://google.com?q=libros"));
        ITEMS.add(new Book("isbn1", "Titulo libro 9", "Autor 9", new Date(), "Resumen titulo  libro 9", "http://google.com?q=libros"));
        ITEMS.add(new Book("isbn1", "Titulo libro 10", "Autor 10", new Date(), "Resumen titulo  libro 10", "http://google.com?q=libros"));
        ITEMS.add(new Book("isbn1", "Titulo libro 11", "Autor 11", new Date(), "Resumen titulo  libro 11", "http://google.com?q=libros"));
    }

}
