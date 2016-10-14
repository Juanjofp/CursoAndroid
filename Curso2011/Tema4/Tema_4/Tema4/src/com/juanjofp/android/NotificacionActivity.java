package com.juanjofp.android;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

public class NotificacionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		nm.cancel(Tema4Activity.NOTIFICATION_ID);
	}
}
