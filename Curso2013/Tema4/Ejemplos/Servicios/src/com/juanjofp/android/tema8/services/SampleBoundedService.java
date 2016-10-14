package com.juanjofp.android.tema8.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class SampleBoundedService extends Service {
	
	private final IBinder mBinder = new LocalBinder();
	private int currentValue;
	private volatile boolean isActive;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		isActive = true;
		new CalculateValue().start();
	}
	
	public class LocalBinder extends Binder {
	        public SampleBoundedService getService() {
	            // Return this instance of LocalService so clients can call public methods
	            return SampleBoundedService.this;
	        }
	    }
	 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	
	public synchronized int getvalue(){
		return currentValue;
	}

	class CalculateValue extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isActive){
				
				synchronized (SampleBoundedService.this) {
					currentValue++;
				}
				
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		isActive = false;
		return super.onUnbind(intent);
	}

}
