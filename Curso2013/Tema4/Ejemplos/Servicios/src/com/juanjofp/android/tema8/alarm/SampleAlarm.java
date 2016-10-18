package com.juanjofp.android.tema8.alarm;

import java.util.Calendar;

import com.juanjofp.android.tema8.Tema_8Activity;

import android.R;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore.Audio;
import android.widget.Toast;

public class SampleAlarm extends BroadcastReceiver {

	public static final String ALARM_SENDER = "com.juanjofp.android.ALARM_SENDER";
	
	@Override
	public void onReceive(Context ctx, Intent data) {
		// TODO Auto-generated method stub
		Toast.makeText(ctx, data.getAction(), Toast.LENGTH_LONG).show();
		
		if(data.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
			registerAlarm(ctx);
		else if(data.getAction().equals(ALARM_SENDER))
			lanzarNotificacion(ctx);

	}
	
	private void lanzarNotificacion(Context ctx){
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(ns);
		int icon = R.drawable.ic_menu_help;
		CharSequence tickerText = "AlarmSample";
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
		
		Context context = ctx.getApplicationContext();
		CharSequence contentTitle = "Notificacion de Alarma";
		CharSequence contentText = "Buenos dias!!";
		Intent notificationIntent = new Intent(ctx, Tema_8Activity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		mNotificationManager.notify(12345, notification);
	}
	
	private void registerAlarm(Context ctx){
		
		// get a Calendar object with current time
		 Calendar cal = Calendar.getInstance();
		 // add 5 minutes to the calendar object
		 cal.add(Calendar.SECOND, 5);
		 Intent intent = new Intent(ALARM_SENDER);
		 intent.putExtra("Alarm_Message", "Buenos dias!!!");
		 // In reality, you would want to have a static variable for the request code instead of 192837
		 PendingIntent sender = PendingIntent.getBroadcast(ctx, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		 // Get the AlarmManager service
		 AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
		 am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
	}

}