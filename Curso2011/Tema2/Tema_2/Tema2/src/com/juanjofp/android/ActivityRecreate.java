package com.juanjofp.android;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ActivityRecreate extends Activity {
	
	public static final String ACTIVITY_NAME = "com.juanjofp.android.COMANDO_PARA_LANZAR";
	
    String textPosition;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recreate);

        // Watch for button clicks.
        button = (Button)findViewById(R.id.recreate);
        button.setOnClickListener(mRecreateListener);
    }

    private OnClickListener mRecreateListener = new OnClickListener() {
        public void onClick(View v) {
            //Eliminamos la instancia existente
            finish();
        }
    };
    
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
    	
    	super.onConfigurationChanged(newConfig);
    	
    	if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
    		Toast.makeText(this, "LANDSCAPE MODE!!!", Toast.LENGTH_LONG).show();
    		button.setBackgroundColor(Color.RED);
    	}else{
    		Toast.makeText(this, "PORTRAIT MODE!!!", Toast.LENGTH_LONG).show();
    		button.setBackgroundColor(Color.BLUE);
    	}
    };
}