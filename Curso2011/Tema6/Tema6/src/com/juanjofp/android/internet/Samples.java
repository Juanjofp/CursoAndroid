package com.juanjofp.android.internet;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Samples extends Activity {
	
	private TextView mTvSalida;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTvSalida = (TextView)findViewById(R.id.tvSalida);
    }
    
	
    public void connectWIFI(View view){
		enableWIFI();
		
		Toast.makeText(this, "IsOnline: "+isOnline(), Toast.LENGTH_LONG).show();
        checkNetworkType();
	}
	
    public void doRequest(View view){
    	
    	doGET();
    	
    	//doPOST();
    	
	    //doPOSTFile(getResources().openRawResource(R.raw.noticias_json));

		//doPUT();
		
    	//doDELETE();
    	
    }
    
	public void loadWebviewActv(View v){
		Intent intent = new Intent(this, WebviewSample.class);
		 startActivity(intent);
	}
	
	public void loadWebview(View v){
		 Uri uri = Uri.parse("http://www.google.com");
		 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		 startActivity(intent);
	}
	
	public void loadXML(View v){
		
		List<Noticia> noticias = null;
        
        DOMParser dom = new DOMParser();
        noticias = dom.parse(getResources().openRawResource(R.raw.noticias_xml));
        
        //SaxParser sax = new SaxParser();
        //noticias = sax.parse(getResources().openRawResource(R.raw.noticias_xml));
        
        //PULLParser pull = new PULLParser();
        //noticias = pull.parse(getResources().openRawResource(R.raw.noticias_xml));
        
        mTvSalida.setText(writeXml(noticias));
        
	}
	
	public void loadJSON(View v) {
		
		List<Noticia> noticias = null;

		JSONParser json = new JSONParser();
		try {
			noticias = json.parse(getResources().openRawResource(
					R.raw.noticias_json));
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mTvSalida.setText(writeXml(noticias));
	}
	
	
	public void showMap(View v){
		startActivity(new Intent(this, MapSample.class));
	}
	
	public void showLocation(View view){
		startActivity(new Intent(this, LocationSample.class));
	}
	
	public void showBrujula(View view){
		startActivity(new Intent(this, BrujulaSample.class));
	}
	
	public void showRecorder(View view){
		startActivity(new Intent(this, MediaSample.class));
	}
	
	public void showVideoView(View view){
		startActivity(new Intent(this, VideoViewDemo.class));
	}
	
	public void showVieoPlayer(View view){
		startActivity(new Intent(this, MediaPlayerSample.class));
	}
	
	private void checkNetworkType(){
    	ConnectivityManager cm = 
   		 (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
   	 
   	 	NetworkInfo i = cm.getActiveNetworkInfo();
   	 	if(i == null){
   	 		mTvSalida.setText("Network Type: No hay ninguno");
   	 		return;
   	 	}
   	 	
   	 	int type = i.getType();
   	 
   	 	switch (type) {
   	 		case ConnectivityManager.TYPE_MOBILE:
   	 			mTvSalida.setText("Network Type: TYPE_MOBILE");
   	 			break;
   	 		case ConnectivityManager.TYPE_WIFI:
   	 			mTvSalida.setText("Network Type: TYPE_WIFI");
   	 			break;
   	 	}
   	 
   	 //Obtener tipo como nombre
   	 Toast.makeText(this, "Network Type: "+ i.getTypeName(), Toast.LENGTH_SHORT).show();
    }
    
    public boolean isOnline() {
    	
    	 ConnectivityManager cm = 
    		 (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	 
    	 NetworkInfo i = cm.getActiveNetworkInfo();
    	  if (i == null)
    	    return false;
    	  if (!i.isConnected())
    	    return false;
    	  if (!i.isAvailable())
    	    return false;
    	  return true;

    }
    
    private void enableWIFI(){

    	
    	  WifiManager wifiManager = 
    		  (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

    	 if(wifiManager.isWifiEnabled()){
    	    wifiManager.setWifiEnabled(false);
    	  }else{
    	    wifiManager.setWifiEnabled(true);
    	  }
    }
    
	public void doGET() {
		
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			String getURL = "http://android.juanjofp.com/noticias_xml.xml";
			HttpGet get = new HttpGet(getURL);
			HttpResponse responseGet = client.execute(get);
			HttpEntity resEntityGet = responseGet.getEntity();
			if (resEntityGet != null) {
				// do something with the response
				mTvSalida.setText(EntityUtils.toString(resEntityGet));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
    
	public void doPOST() {
		try {
			
			DefaultHttpClient client = new DefaultHttpClient();
			String postURL = "http://android.juanjofp.com/noticias_json.json";
			HttpPost post = new HttpPost(postURL);
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("user", "juanjo"));
			params.add(new BasicNameValuePair("pass", "secreto"));
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,
					HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);
			HttpEntity resEntity = responsePOST.getEntity();
			if (resEntity != null) {
				mTvSalida.setText(EntityUtils.toString(resEntity));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void doPOSTFile(InputStream is){
		
    	DefaultHttpClient httpClient = new DefaultHttpClient();
    	String FILE_NAME = "file_name";
        try {
        	
        	//TimeOut
        	httpClient.getParams().setParameter("http.socket.timeout", new Integer(90000));
            HttpPost post = new HttpPost(new URI("http://android.juanjofp.com/noticias_xml.xml"));
            InputStreamEntity entity;
            entity = new InputStreamEntity(is, is.available());
            entity.setChunked(true);
            post.setEntity(entity);
            post.addHeader(FILE_NAME, "file.json");
            HttpResponse response = httpClient.execute(post);
            
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            	//Error en la peticion
            	mTvSalida.setText("Error "+response.getStatusLine().getStatusCode()+": "+EntityUtils.toString(response.getEntity()));
            }else {
            	// Peticion correcta.
            	mTvSalida.setText(EntityUtils.toString(response.getEntity()));
            }
            
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
        	httpClient.getConnectionManager().shutdown();
        }
}
    
    public void doPUT(){
    	
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPut put = new HttpPut(new URI(
					"http://android.juanjofp.com/noticias_xml.xml"));

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("key1", "value1"));
			pairs.add(new BasicNameValuePair("key2", "value2"));
			put.setEntity(new UrlEncodedFormEntity(pairs));

			HttpResponse responsePUT = client.execute(put);
			HttpEntity resEntityPUT = responsePUT.getEntity();
			if (resEntityPUT != null) {
				// do something with the response
				mTvSalida.setText(EntityUtils.toString(resEntityPUT));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
	public void doDELETE() {
		
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			String deleteURL = "http://android.juanjofp.com/noticias_xml.xml";
			HttpDelete delete = new HttpDelete(deleteURL);
			HttpResponse responseDEL = client.execute(delete);
			HttpEntity resEntityDEL = responseDEL.getEntity();
			if (resEntityDEL != null) {
				// do something with the response
				mTvSalida.setText(EntityUtils.toString(resEntityDEL));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String writeXml(List<Noticia> noticias){
		
	    XmlSerializer serializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
	    
	    try {
	    	
	        serializer.setOutput(writer);
	        serializer.startDocument("UTF-8", true);
	        serializer.startTag("", Noticia.NOTICIAS);
	        
	        for (Noticia news: noticias){
	            news.serializeMe(serializer);
	        }
	        
	        serializer.endTag("", Noticia.NOTICIAS);
	        serializer.endDocument();
	        return writer.toString();
	        
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    } 
	}
}