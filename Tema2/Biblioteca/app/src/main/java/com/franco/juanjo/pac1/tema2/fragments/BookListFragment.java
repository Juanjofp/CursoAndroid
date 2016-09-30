package com.franco.juanjo.pac1.tema2.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franco.juanjo.pac1.tema2.R;
import com.franco.juanjo.pac1.tema2.adapters.BookListAdapter;
import com.franco.juanjo.pac1.tema2.models.Book;
import com.franco.juanjo.pac1.tema2.models.BookContent;

import java.util.List;


public class BookListFragment extends Fragment implements BookListAdapter.OnBookAdapterListener {

    // Listener para informar a la activity de los
    // eventos ocurridos enel fragment
    private OnFragmentBookListListener mListener;

    // RecyclerView
    private RecyclerView mBookList;
    // Adaptador del RecyclerView
    private BookListAdapter mAdapter;
    // Layout manager para el RecyclerView
    private RecyclerView.LayoutManager mLayoutManager;

    // Obtenemos el catalogo de libros para mostrar en el RV
    private List<Book> mLibrary = BookContent.ITEMS;

    public BookListFragment() {
        // Required empty public constructor
    }

    public static BookListFragment newInstance() {
        BookListFragment fragment = new BookListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_list, container, false);


        // Recuperamos el RecyclerView
        mBookList = (RecyclerView)v.findViewById(R.id.rviewBookListFragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // Los Layouts son del mismo tamaño, al menos en esta version
        //mBookList.setHasFixedSize(true);

        // Instanciamos el LayoutManager para que muestre los libros
        // en modo lista, para eso usamos el LinearLayuotManager
        // y lo inyectamos en el RV
        mLayoutManager = new LinearLayoutManager(getActivity());
        mBookList.setLayoutManager(mLayoutManager);


        // Instanciamos el adaptador y lo cargamos con los
        // libros almacenados en BookContent
        mAdapter = new BookListAdapter(mLibrary);
        mAdapter.setOnBookClicked(this);
        mBookList.setAdapter(mAdapter);


        return v;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentBookListListener) {
            mListener = (OnFragmentBookListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onBookSelected(View v, Book book) {
        // Cuando recibimos el evento de Book Selected del adaptador
        // reenviamos este evento hasta la activity para que lo procese
        // El criterio de separar el flujo de este evento en dos capas (interfaces)
        // es evitar acoplar la clase BookListAdapter a este fragment y así poder seguir usando
        // BookListAdapter en otros Fragment que implementen la interfaz BookListAdapter.OnBookAdapterListener
        if(mListener != null) {
            mListener.onBookSelected(book);
        }
    }


    public interface OnFragmentBookListListener {
        // Callback que avisará a la Activity que libro ha sido seleccionado
        // y que posición ocupa enel adaptador
        void onBookSelected(Book book);
    }
}
