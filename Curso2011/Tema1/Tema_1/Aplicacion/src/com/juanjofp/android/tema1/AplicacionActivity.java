package com.juanjofp.android.tema1;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

public class AplicacionActivity extends Activity {
    /** Called when the activity is first created. */
	ImageView mIvPhoto;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        try {
        	
			new Downloader().execute(new URL("http://..."));
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    class Downloader extends AsyncTask<URL, Integer, String>{
    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		super.onPreExecute();
    	}
    	
    	@Override
    	protected String doInBackground(URL... params) {
    		// TODO Auto-generated method stub
    		publishProgress(5);
    		return new String();
    	}
    	
    	@Override
    	protected void onProgressUpdate(Integer... values) {
    		// TODO Auto-generated method stub
    		super.onProgressUpdate(values);
    	}
    	
    	@Override
    	protected void onPostExecute(String result) {
    		// TODO Auto-generated method stub
    		super.onPostExecute(result);
    	}
    }
}