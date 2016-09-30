package com.franco.juanjo.pac1.tema2.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.franco.juanjo.pac1.tema2.R;
import com.franco.juanjo.pac1.tema2.fragments.BookDetailFragment;
import com.franco.juanjo.pac1.tema2.fragments.BookListFragment;
import com.franco.juanjo.pac1.tema2.models.Book;
import com.franco.juanjo.pac1.tema2.models.BookContent;

public class BookListActivity extends AppCompatActivity
implements BookListFragment.OnFragmentBookListListener, // La activity principal necesita implementar ambos listener
        BookDetailFragment.OnFragmentBookDetailListener {// porque puede contener los dos fragments

    private static final String TAG = "BookListActivity";
    private static final int RESULT_DETAILS = 1001;

    private BookListFragment mBookList;
    private BookDetailFragment mDetail;

    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Esta actividad cargará el layout adecuado basandose en el selector
         * w900dp, para decidir en que modalidad estamos trabajando podemos
         * basarnos en la existencia del fragment de detalla.
         *
         * Otra variante es usar un layout para contener el fragment de detalle
         * y si existe el layout (el findViewById(idLayout) NO devuelve null) cargar de forma
         * manual el fragment con
         *
         * He optado por la primera forma por ser la usada en el articulo 'Best Practices > supporting tablets and handsets'
         * de la doc de android (https://developer.android.com/guide/practices/tablets-and-handsets.html),
         * pero no he conseguido encontrar una explicación clara sobre que mecanismo sería mas recomendado (salvo el ahorro de código),
         * por lo leido en foros es el mismo proceso hecho por el programador o delegado al sistema.
         *
         */
        setContentView(R.layout.activity_book_list);

        mBookList = (BookListFragment) getFragmentManager().findFragmentById(R.id.fragBookListBookListActivity);
        // Cuando mDetail sea null sabremos q no estamos en modo master/detail
        if (findViewById(R.id.fragBookDetailBookListActivity) != null) {

            mTwoPane = true;

            mDetail = (BookDetailFragment) getFragmentManager().findFragmentById(R.id.fragBookDetailBookListActivity);
            if (mDetail != null && !mDetail.hasBook()) {
                // Si estamos en master/details y no hay un libro en el detalle
                // Usamos el primer libro de la libreria como libro por defecto
                mDetail.updateBook(BookContent.ITEMS.get(0));
            }
        }

        // Alternativa 2
        /*
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.flBookDetailBookListActivity) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            BookDetailFragment mDetail = BookDetailFragment.newInstance();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            mDetail.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flBookDetailBookListActivity, mDetail).commit();
        }
        */
    }

    @Override
    public void onBookCardClicked() {
        Log.i(TAG, "onBookCardClicked");
    }


    @Override
    public void onBookSelected(Book book) {
        Log.i(TAG, "onBookSelected: " + book.getTitle() + " at " + book.getId());
        // Si estamos en master/details actualizamos el fragment
        // con el libro solicitado
        if(mTwoPane) {
            mDetail.updateBook(book);
        }
        else {
            // En el otro caso lanzamos la activity de detalle y forzamos esperar su respuesta
            startActivityForResult(BookDetailActivity.newInstance(this, book), RESULT_DETAILS);
        }
    }
}
