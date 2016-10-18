package com.android.juanjofp.masterdetails;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.juanjofp.masterdetails.models.Book;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBookActivity extends AppCompatActivity {

    private boolean fav = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private String mToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);

        List<Book> books = Book.listAll(Book.class);
        if (books != null) {
            for (int i = 0; i < books.size(); i++) {
                Log.e("Book", books.get(i).getTitle());
            }
        }

        SharedPreferences sp = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        mToken = sp.getString("token", null);
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
            case R.id.register:
                try {
                    doRegister();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.login:
                try {
                    doLogin();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.books:
                try {
                    getBooks();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doRegister() throws JSONException {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://biblioteca.juanjofp.com/register";

        JSONObject body = new JSONObject();
        body.put("username", "juanjo");
        body.put("password", "123456");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Register Respnse", response.toString());
                Toast.makeText(ListBookActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    mToken = response.getString("token");
                    SharedPreferences sp = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("token", mToken);
                    ed.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("Register", error.toString());
                Toast.makeText(ListBookActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }

    private void doLogin() throws JSONException {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://biblioteca.juanjofp.com/login";

        JSONObject body = new JSONObject();
        body.put("username", "juanjo");
        body.put("password", "123456");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Login Respnse", response.toString());
                Toast.makeText(ListBookActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    mToken = response.getString("token");
                    SharedPreferences sp = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("token", mToken);
                    ed.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("Login", error.toString());
                Toast.makeText(ListBookActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }

    private void getBooks() throws JSONException {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://biblioteca.juanjofp.com/book";

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.e("Get Books Respnse", response.toString());
                Toast.makeText(ListBookActivity.this, response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("Get Books", error.toString());
                Toast.makeText(ListBookActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                if(headers != null) {
                    headers = new HashMap<>(headers);
                }
                headers.put("Authorization", "Bearer " + mToken);
                return headers;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }

}
