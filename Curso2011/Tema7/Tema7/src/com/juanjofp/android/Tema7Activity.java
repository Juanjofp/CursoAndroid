package com.juanjofp.android;

import java.net.URLDecoder;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Tema7Activity extends Activity {
    /** Called when the activity is first created. */

	private WebView mWebView;
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mWebView = new WebView(this);
	 
	    // We're testing, clear the cache.
	    mWebView.clearCache(true);
	 
	    setContentView(mWebView);
	 
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    
	 
	    final Activity activity = this;
	    
	    mWebView.setWebViewClient(new WebViewClient() {
	      int alert = 1;
	 
	      public void onReceivedError(WebView view, int errorCode,
	          String description, String failingUrl) {
	        Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
	      }
	 
	      public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        // Is it a hack?
	    	  Log.e("URL", url);
	        if (url.startsWith("android")) {
	          String ns = Context.NOTIFICATION_SERVICE;
	          NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
	          int icon = R.drawable.icon;
	          CharSequence tickerText = "WebApp Hibrida";
	          long when = System.currentTimeMillis();
	 
	          Notification notification = new Notification(icon,
	              tickerText, when);
	          notification.flags = Notification.FLAG_AUTO_CANCEL;
	          Context context = getApplicationContext();
	 
	          String[] split = url.split("/");
	 
	          CharSequence contentTitle = URLDecoder.decode(split[2]);
	          CharSequence contentText = URLDecoder.decode(split[3]);
	 
	          Intent notificationIntent = new Intent(activity, Tema7Activity.class);
	          PendingIntent contentIntent = PendingIntent.getActivity(
	              activity, 0, notificationIntent, 0);
	 
	          notification.setLatestEventInfo(context, contentTitle,
	              contentText, contentIntent);
	 
	          mNotificationManager.notify(alert++, notification);
	          
	        } else {
	          view.loadUrl(url);
	        }
	        return true;
	      }
	    });
	 
	    mWebView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
	    
	    mWebView.loadUrl("file:///android_asset/tema7.html");
	  }
	  
	  public class JavaScriptInterface {
		    Context mContext;

		    /** Instantiate the interface and set the context */
		    JavaScriptInterface(Context c) {
		        mContext = c;
		    }

		    /** Show a toast from the web page */
		    public void showAndroidToast(String texto) {
		    	Toast.makeText(mContext, texto, Toast.LENGTH_LONG).show();
		    }
		}
}