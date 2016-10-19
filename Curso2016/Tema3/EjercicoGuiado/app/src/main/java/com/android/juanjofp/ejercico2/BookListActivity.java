package com.android.juanjofp.ejercico2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.juanjofp.ejercico2.models.Book;
import com.android.juanjofp.ejercico2.models.BookFactory;

public class BookListActivity extends AppCompatActivity
implements BookListFragment.OnListFragmentInteractionListener{

    private static final int RESULT_LOGIN = 1001;
    private static final int RESULT_DETAILS = 2001;

    private BookDetailsFragment mDetails;

    private boolean mStarred = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        mDetails = (BookDetailsFragment)getFragmentManager().findFragmentById(R.id.fraDetailsBookListFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        int res = mStarred ? android.R.drawable.star_big_on : android.R.drawable.star_big_off;
        MenuItem menuFav = menu.findItem(R.id.starred_main_menu);
        menuFav.setIcon(res);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.starred_main_menu:
                mStarred = !mStarred;
                int res = mStarred ? android.R.drawable.star_big_on : android.R.drawable.star_big_off;
                item.setIcon(res);
                Toast.makeText(this, "Libro como favorito", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        if(mDetails != null && mDetails.isVisible()) {
            mDetails.updateBook(item);
        }
        else {
            startActivityForResult(BookDetailsActivity.navigateTo(this, item), RESULT_DETAILS);
        }
    }
}
