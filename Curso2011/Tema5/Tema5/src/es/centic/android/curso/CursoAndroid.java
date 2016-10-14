package es.centic.android.curso;



import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class CursoAndroid extends Activity {

	
	private TextView mTvMensaje;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	mTvMensaje = (TextView)findViewById(R.id.text);
    	
    	//insertData();
    	
    	
    	androidProviders();
    	
    	
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("CheckBox: ").append(pref.getBoolean(getResources().getString(R.string.ck_pref), false)).append("\n");
    	sb.append("EditText: ").append(pref.getString(getResources().getString(R.string.txt_pref), "Vacio")).append("\n");
    	sb.append("List: ").append(pref.getString(getResources().getString(R.string.lst_pref), "Vacio")).append("\n");
    	
    	mTvMensaje.setText(sb.toString());
    }
    
    public void showPreferences(View v){
    	startActivity(new Intent(this, MisPreferencias.class));
    }
    
    private void insertData(){
    	NewsDBAdapter db = new NewsDBAdapter(this);
    	
    	db.openDB();

    	for(int i=0;i<10;i++){
    		News n = new News(i+"Libertad Condicional","Alguien sale en libertad condicional","http://google.com?q=libertad%20condicional",new Date().getTime());
    		db.insertNews(n);
    		Log.e("Noticia "+i, n.toString());
    	}

    	
    	Cursor c = db.getAllNews();
    	if(c != null && c.moveToFirst()){
    		do{
    			News n = new News(c);
    			Log.e("Noticia "+n.getId(), n.getTitle()+"::"+n.getDatePosted());
    		}while(c.moveToNext());
    	}
    	db.closeDB();
    }
    
    private void androidProviders(){
    	
    	ContentValues cv = new ContentValues();
    	
    	cv.put(Data.DISPLAY_NAME, "Juanjo");
    	Uri uri = getContentResolver().insert(RawContacts.CONTENT_URI, cv);
    	Log.e("URI","Hay uri:"+uri.toString());
    	mTvMensaje.setText(mTvMensaje.getText()+"\nContacto: "+uri.toString());
    	/*cv.clear();
    	cv.put(Data.DISPLAY_NAME, "Manolete");
    	getContentResolver().update(RawContacts.CONTENT_URI, 
				cv, 
				Data._ID+"="+uri.getPathSegments().get(1), null);
    	
    	getContentResolver().delete(RawContacts.CONTENT_URI, 
				Data._ID+"="+uri.getPathSegments().get(1), null);
				*/
    }
    
    private void customProvider(){
    	Cursor c = getContentResolver().query(News.CONTENT_URI, null, null, null, null);
    	if(c != null && c.moveToFirst()){
    		do{
    			String title = c.getString(c.getColumnIndexOrThrow(News.Columns.KEY_TITLE));
    			int id = c.getInt(c.getColumnIndexOrThrow(News.Columns._ID));
    			Log.e("Noticia "+id, title);
    		}while(c.moveToNext());
    	}
    	c.close();
    	
    	
    	Uri aNews = Uri.withAppendedPath(News.CONTENT_URI, "1");
    	Log.e("URI", aNews.toString());
    	Cursor c2 = getContentResolver().query(aNews, null, null, null, null);
    	if(c2 != null && c2.moveToFirst()){
    		do{
    			String title = c2.getString(c2.getColumnIndexOrThrow(News.Columns.KEY_TITLE));
    			int id = c2.getInt(c2.getColumnIndexOrThrow(News.Columns._ID));
    			Log.e("Noticia Unica "+id, title);
    		}while(c2.moveToNext());
    	}
    	c2.close();
    }
}