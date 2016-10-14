package com.franco.juanjo.pac1.tema2.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franco.juanjo.pac1.tema2.R;
import com.franco.juanjo.pac1.tema2.models.Book;


public class BookDetailFragment extends Fragment {

    private OnFragmentBookDetailListener mListener;

    private TextView tvAuthor;
    private TextView tvDetails;
    private Book mBook;

    public BookDetailFragment() {
        // Required empty public constructor
    }


    public static BookDetailFragment newInstance() {
        BookDetailFragment fragment = new BookDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_detail, container, false);

        tvAuthor = (TextView)v.findViewById(R.id.tvAuthorBookDetailFragment);
        tvDetails = (TextView)v.findViewById(R.id.tvDetailsBookDetailFragment);
        updateBook();

        return v;
    }

    // Metodo para actualizar el libro del fragment
    public void updateBook(Book book) {
        mBook = book;
        updateBook(); // Forzamos repintar el libro
    }

    private void updateBook() {
        if(mBook == null) {
            return;
        }

        if(tvAuthor != null) {
            tvAuthor.setText(mBook.getAuthor());
        }

        if(tvDetails != null) {
            tvDetails.setText(mBook.getResume());
        }
    }

    // Metodo para saber si hay un libro mostrado
    public boolean hasBook() {
        return mBook != null;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentBookDetailListener) {
            mListener = (OnFragmentBookDetailListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentBookDetailListener {
        // TODO: Update argument type and name
        void onBookCardClicked();
    }
}
