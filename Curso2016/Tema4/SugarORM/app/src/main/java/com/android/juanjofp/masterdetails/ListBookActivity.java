package com.android.juanjofp.masterdetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.juanjofp.masterdetails.models.Book;

import java.util.List;

public class ListBookActivity extends AppCompatActivity {

    private boolean fav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);

        List<Book> books = Book.listAll(Book.class);
        if(books != null) {
            for(int i = 0; i< books.size(); i++) {
                Log.e("Book", books.get(i).getTitle());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuStarred = menu.findItem(R.id.starred);
        menuStarred.setIcon(fav ? android.R.drawable.star_big_on : android.R.drawable.star_big_off);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.starred:
                fav = !fav;
                item.setIcon(fav ? android.R.drawable.star_big_on : android.R.drawable.star_big_off);
                Book book = new Book("Libro", "isbn");
                book.save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
