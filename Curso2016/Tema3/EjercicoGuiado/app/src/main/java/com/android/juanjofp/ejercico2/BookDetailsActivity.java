package com.android.juanjofp.ejercico2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.juanjofp.ejercico2.models.Book;

public class BookDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_BOOK = "extra_book";

    private Book mBook;

    private BookDetailsFragment mDetails;

    public static Intent navigateTo(Context ctx, Book book) {
        Intent it = new Intent(ctx, BookDetailsActivity.class);
        it.putExtra(EXTRA_BOOK, book);
        return it;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBook = (Book)getIntent().getSerializableExtra(EXTRA_BOOK);
        if(mBook == null) {
            Toast.makeText(this, getString(R.string.no_book_book_details_Activity), Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }

        setContentView(R.layout.activity_book_details);

        mDetails = (BookDetailsFragment)getFragmentManager().findFragmentById(R.id.fraDetailsBookActivity);
        mDetails.updateBook(mBook);
    }
}
