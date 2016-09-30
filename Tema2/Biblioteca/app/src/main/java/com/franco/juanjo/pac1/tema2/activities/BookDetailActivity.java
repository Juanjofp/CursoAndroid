package com.franco.juanjo.pac1.tema2.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.franco.juanjo.pac1.tema2.R;
import com.franco.juanjo.pac1.tema2.fragments.BookDetailFragment;
import com.franco.juanjo.pac1.tema2.models.Book;

/**
 * Esta activity sólo se usa para cargar el fragment BookDetailFragment cuando la
 * pantalla del dispistivo NO CUMPLE con el selector w900dp en cuyo caso el listado y el detalle
 * se mostrarán en actividades distintas
 */
public class BookDetailActivity extends AppCompatActivity
implements BookDetailFragment.OnFragmentBookDetailListener {

    private static final String TAG = "BookDetailActivity";
    private static final String PARAM_BOOK = "book_to_show";

    // Fragment con los detalles del libro
    private BookDetailFragment mDetail;
    private Book mBook;

    // Titulo del libro en la cabecera
    private TextView tvTitle;

    public static Intent newInstance(Context ctx, Book book) {
        Intent it = new Intent(ctx, BookDetailActivity.class);
        it.putExtra(PARAM_BOOK, book);
        return it;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        mBook = (Book) getIntent().getParcelableExtra(PARAM_BOOK);
        // Si no hay un libro lo indicamos al usuario con un toast y cerramos la activity
        if(mBook == null) {
            Toast.makeText(this, getString(R.string.book_not_found), Toast.LENGTH_SHORT).show();
            finish();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mBook.getTitle());

        // Ponemos le titulo
        tvTitle = (TextView)findViewById(R.id.tvTitleBookDetailActivity);
        tvTitle.setText(mBook.getTitle());

        mDetail = (BookDetailFragment)getFragmentManager().findFragmentById(R.id.fragBookDetailBookDetailActivity);
        // Mostramos el libro en el fragment
        mDetail.updateBook(mBook);
    }

    @Override
    public void onBookCardClicked() {
        Log.i(TAG, "onBookCardClicked");
    }
}
