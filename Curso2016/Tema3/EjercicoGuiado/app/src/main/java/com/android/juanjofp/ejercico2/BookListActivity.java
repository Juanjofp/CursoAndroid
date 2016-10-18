package com.android.juanjofp.ejercico2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.juanjofp.ejercico2.models.Book;
import com.android.juanjofp.ejercico2.models.BookFactory;

public class BookListActivity extends AppCompatActivity
implements BookListFragment.OnListFragmentInteractionListener{

    private static final int RESULT_LOGIN = 1001;
    private static final int RESULT_DETAILS = 2001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(BookFactory.TOKEN == null) {
            startActivityForResult(LoginActivity.navigateTo(this), RESULT_LOGIN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOGIN) {
            if(resultCode == RESULT_OK) {
                // TODO: Pedir los libros al ws
            }
            else {
                Toast.makeText(this, getString(R.string.no_token_book_list_activity), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onListFragmentInteraction(Book item) {
        Toast.makeText(this, "Libro " + item.getmTitulo(), Toast.LENGTH_SHORT).show();
        // Lanzar activity detalles
        startActivityForResult(BookDetailsActivity.navigateTo(this, item), RESULT_DETAILS);
    }
}
