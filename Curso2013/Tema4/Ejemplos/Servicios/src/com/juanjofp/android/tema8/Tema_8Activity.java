package com.juanjofp.android.tema8;

import java.util.Calendar;

import com.juanjofp.android.tema8.alarm.SampleAlarm;
import com.juanjofp.android.tema8.receiver.CustomReceiver;
import com.juanjofp.android.tema8.receiver.CustomReceiver2;
import com.juanjofp.android.tema8.services.SampleBoundedService;
import com.juanjofp.android.tema8.services.SampleBoundedService.LocalBinder;
import com.juanjofp.android.tema8.services.SampleIntentService;
import com.juanjofp.android.tema8.services.SampleService;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

public class Tema_8Activity extends Activity {
    /** Called when the activity is first created. */

	public static final int MSG_RESPONSE_HELLO = 1;
	
	private SampleBoundedService mService = null;
    boolean mBound;
	
    private CustomReceiver2 mCustomReceiver2;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void sampleIntentService(View v){
    	startService(new Intent(this, SampleIntentService.class));
    }
    
    public void sampleService(View v){
    	startService(new Intent(this, SampleService.class));
    }
    
    public void sampleBoundedService(View v) {
    	if (mBound) {
            // Call a method from the LocalService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            int num = mService.getvalue();
            Toast.makeText(this, "Valor: " + num, Toast.LENGTH_SHORT).show();
        }
    }
    
    public void sendCustomreceiver(View v){
    	sendBroadcast(new Intent(CustomReceiver.CUSTOM_RECEIVER));
    	sendBroadcast(new Intent(CustomReceiver2.CUSTOM_RECEIVER2));
    }
    
    public void registerReceiver(View v){
    	mCustomReceiver2 = new CustomReceiver2();
    	registerReceiver(mCustomReceiver2, new IntentFilter(CustomReceiver2.CUSTOM_RECEIVER2));
    }
    
    public void unregisterReceiver(View v){
    	unregisterReceiver(mCustomReceiver2);
    }
    
    public void showAlarm(View view){
    	//Lanzar una alarma dentro de 5 s.
    	// get a Calendar object with current time
		 Calendar cal = Calendar.getInstance();
		 // add 5 minutes to the calendar object
		 cal.add(Calendar.SECOND, 5);
		 Intent intent = new Intent(SampleAlarm.ALARM_SENDER);
		 intent.putExtra("Alarm_Message", "Buenos dias!!!");
		 PendingIntent sender = PendingIntent.getBroadcast(this, 12345, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		 // Get the AlarmManager service
		 AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		 am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
    }
    
    public void cancelAlarm(View view){
    	
    	Intent intent = new Intent(SampleAlarm.ALARM_SENDER);
    	AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    	PendingIntent sender = PendingIntent.getBroadcast(this, 12345, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    	am.cancel(sender);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        // Bind to the service
        bindService(new Intent(this, SampleBoundedService.class), mConnection,
            Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
    
    private ServiceConnection mConnection = new ServiceConnection() {
    	
        public void onServiceConnected(ComponentName className, IBinder service) {
        	LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
            mBound = false;
        }
    };
}