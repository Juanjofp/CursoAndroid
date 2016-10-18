package com.juanjofp.android.tema8.services;

import android.R;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore.Audio;

import com.juanjofp.android.tema8.Tema_8Activity;

public class SampleIntentService extends IntentService{
	
	public SampleIntentService() {
		// TODO Auto-generated constructor stub
		super("SampleIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		//Espera 5s. antes de lanzar la notificaci�n
		//Aqu� va el trabajo del servicio
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lanzarNotificacion();
		
	}
	
	private void lanzarNotificacion(){
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.ic_menu_help;
		CharSequence tickerText = "SampleIntentService";
		long when = System.currentTimeMillis();
		
		Notification notification = new Notification(icon, tickerText, when);
		notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "3");
		long[] vibrate = {0,100,200,300};
		notification.vibrate = vibrate;
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		Context context = getApplicationContext();
		CharSequence contentTitle = "IntentService Notification";
		CharSequence contentText = "Hello IntentService!";
		Intent notificationIntent = new Intent(this, Tema_8Activity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		mNotificationManager.notify(12345, notification);
	}

}