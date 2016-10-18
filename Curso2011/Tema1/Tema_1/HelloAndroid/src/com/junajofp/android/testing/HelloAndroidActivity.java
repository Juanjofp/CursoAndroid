package com.junajofp.android.testing;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

public class HelloAndroidActivity extends Activity {
    /** Called when the activity is first created. */
	
	private Button mButton;
	private CheckBox mChk;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mButton = (Button)findViewById(R.id.button2);
        mButton.setText("Hello Android");
        mChk = (CheckBox)findViewById(R.id.checkBox1);
        mChk.setChecked(true);
    }
}