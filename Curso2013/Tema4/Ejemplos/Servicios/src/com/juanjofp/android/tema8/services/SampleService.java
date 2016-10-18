package com.juanjofp.android.tema8.services;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore.Audio;

import com.juanjofp.android.tema8.Tema_8Activity;

public class SampleService extends Service {

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//Gestion manual del threading
		new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				//Trabajo pesado
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				lanzarNotificacion(getNotificacion());
				//detener servicio
				stopSelf();
			}
		}).start();
		
		
		return START_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void lanzarNotificacion(Notification notification){
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		mNotificationManager.notify(12345, notification);
	}

	private Notification getNotificacion(){
		
		int icon = R.drawable.ic_menu_help;
		CharSequence tickerText = "SampleService";
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
		
		return notification;
	}
}