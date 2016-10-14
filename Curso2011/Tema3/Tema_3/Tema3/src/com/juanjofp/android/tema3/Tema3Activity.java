package com.juanjofp.android.tema3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Tema3Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void lanzarRestoreSample(View view){
    	startActivity(new Intent(this, SaveRestoreState.class));
    }
    public void lanzarLinearLayout(View view){
    	startActivity(new Intent(this, LinearLayout1.class));
    }
    public void lanzarRelativeLayout(View view){
    	startActivity(new Intent(this, RelativeLayout1.class));
    }
    public void lanzarTableLayout(View view){
    	startActivity(new Intent(this, TableLayout1.class));
    }
    public void lanzarScrollBar(View view){
    	startActivity(new Intent(this, ScrollBar1.class));
    }
    public void lanzarButton(View view){
    	startActivity(new Intent(this, Buttons1.class));
    }
    public void lanzarImageButton(View view){
    	startActivity(new Intent(this, ImageButton1.class));
    }
    public void lanzarImageView(View view){
    	startActivity(new Intent(this, ImageView1.class));
    }
    public void lanzarDateWidget(View view){
    	startActivity(new Intent(this, DateWidgets1.class));
    }
    public void lanzarAutocomplete(View view){
    	startActivity(new Intent(this, AutoCompleteSample.class));
    }
    public void lanzarSpinner(View view){
    	startActivity(new Intent(this, Spinner1.class));
    }
    public void lanzarChronometer(View view){
    	startActivity(new Intent(this, ChronometerDemo.class));
    }
    public void lanzarControles(View view){
    	startActivity(new Intent(this, Controls1.class));
    }
    public void lanzarGallery(View view){
    	startActivity(new Intent(this, Gallery1.class));
    }
    public void lanzarImageSwitcher(View view){
    	startActivity(new Intent(this, ImageSwitcher1.class));
    }
    public void lanzarAnimation(View view){
    	startActivity(new Intent(this, AnimationSample.class));
    }
}