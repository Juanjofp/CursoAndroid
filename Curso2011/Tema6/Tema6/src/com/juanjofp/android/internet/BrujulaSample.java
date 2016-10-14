package com.juanjofp.android.internet;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class BrujulaSample extends Activity {
	
	private static SensorManager mSensorManager;
	private boolean sersorrunning;
	private BrujulaView mBrujula;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass_layout);
        
        mBrujula = (BrujulaView)findViewById(R.id.bvBrujula);
        
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> mySensors = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        
        if(mySensors.size() > 0){
        	mSensorManager.registerListener(mySensorEventListener, mySensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        	sersorrunning = true;
        	Toast.makeText(this, "Start ORIENTATION Sensor", Toast.LENGTH_LONG).show();	
        	
        }
        else{
        	Toast.makeText(this, "No ORIENTATION Sensor", Toast.LENGTH_LONG).show();
        	sersorrunning = false;
        	finish();	
        }
    }
    
    private SensorEventListener mySensorEventListener = new SensorEventListener(){

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			mBrujula.updateDirection((float)event.values[0]);
		}
    };

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if(sersorrunning){
			mSensorManager.unregisterListener(mySensorEventListener);	
		}
	}

}