package com.juanjofp.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Tema2Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void saveState(View view){
    	startActivity(new Intent(this, SaveRestoreState.class));
    }
    
    public void recreate(View view){
    	startActivity(new Intent(ActivityRecreate.ACTIVITY_NAME));
    }
    
    public void reorder(View view){
    	startActivity(new Intent(this, ReorderOnLaunch.class));
    }
    
    public void dialogTheme(View view){
    	startActivity(new Intent(this, WallpaperActivity.class));
    }
}