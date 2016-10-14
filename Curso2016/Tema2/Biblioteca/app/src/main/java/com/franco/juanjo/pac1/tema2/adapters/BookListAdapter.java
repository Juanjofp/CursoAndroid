package com.franco.juanjo.pac1.tema2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franco.juanjo.pac1.tema2.R;
import com.franco.juanjo.pac1.tema2.models.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanjo on 25/9/16.
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder>
implements View.OnClickListener{

    // Interfaz para informar de los eventos generados dentro del adaptador
    // de libros
    public interface OnBookAdapterListener {
        public void onBookSelected(View view, Book book);
    }

    private static final int EVEN = 0;
    private static final int ODD = 1;

    // Callback para responder a los eventos de cada elemento
    // de una fila del RV
    private OnBookAdapterListener mCallback;
    // Alamcen donde mantendremos actualizado el listado de libros
    private ArrayList<Book> mBookList;

    public BookListAdapter(List<Book> books) {
        // Cargamos el listdo de libros que usará el
        // adaptador
        mBookList = new ArrayList<>(books);
    }

    public void setOnBookClicked(OnBookAdapterListener listener) {
        mCallback = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2; // Pares 0, Impares 1
    }

    @Override
    public BookListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.item_even_book_list_fragment;

        // Seleccionamos la vista a mostrar en función del viewType
        switch (viewType) {
            case EVEN:
                layout = R.layout.item_even_book_list_fragment;
                break;
            case ODD:
                layout = R.layout.item_odd_book_list_fragment;
                break;
        }
        // Instanciamos el componente que usaremos como UI de cada fila del RecyclerView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        // Añadimos le evnto onclick para detectar las pulsaciones sobre la card
        itemView.setOnClickListener(this);
        // Devolvemos la vista lista para ser usada
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookListAdapter.ViewHolder holder, int position) {
        // Bindemaos los datos del adaptador con la vista generada/reutilizada
        // por el onCreateViewHolder
        // Recuperamos el libro de la posicion indicada
        Book book = mBookList.get(position);

        // Cargamos la vista con los datos del libro
        holder.mBook = book;
        holder.mIndexBook = position;
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());

        // Alamacenamosle modelo dentro de la vista para poder enviarlo
        // cuando se pulse sobre la card
        holder.itemView.setTag(book);

    }

    @Override
    public int getItemCount() {
        // Indicamos le número de elementos q contine el adaptador
        return mBookList.size();
    }

    @Override
    public void onClick(View v) {
        if(mCallback != null) {
            mCallback.onBookSelected(v, (Book)v.getTag());
        }
    }

    // ViewHolder usado para recuperar los widgets del xml adecuado
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvAuthor;

        // Callback para responder a la activity/fragment cuando pulsemos sobre este item
        private OnBookAdapterListener mCallback;

        // Datos del libro que esta mostrando, útil para cuando avisemos a
        // la activity
        public Book mBook;
        public int mIndexBook;

        public ViewHolder(View itemView) {
            super(itemView);

            // Recuperamos los widgets del layout
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitleItemBookListFragment);
            tvAuthor = (TextView)itemView.findViewById(R.id.tvAuthorItemBookListFragment);
        }
    }
}
