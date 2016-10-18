package com.android.juanjofp.ejercico2;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.juanjofp.ejercico2.models.Book;


public class BookDetailsFragment extends Fragment {
    private static final String ARG_BOOK = "book_selected";

    private Book mBook;

    private TextView tvTitle;

    public BookDetailsFragment() {
        // Required empty public constructor
    }


    public static BookDetailsFragment newInstace(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBook = (Book) getArguments().getSerializable(ARG_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);
        tvTitle = (TextView)v.findViewById(R.id.tvTitleBookDetailsFragment);

        if(mBook != null) {
            tvTitle.setText(mBook.getmTitulo());
        }

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void updateBook(Book book) {
        if(book == null) return;

        mBook = book;

        if(tvTitle != null) {
            tvTitle.setText(mBook.getmTitulo());
        }
    }
}
