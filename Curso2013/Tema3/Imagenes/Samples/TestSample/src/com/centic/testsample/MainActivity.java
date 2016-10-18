package com.centic.testsample;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tvOutput;
	private EditText etInput;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tvOutput = (TextView)findViewById(R.id.tvOutput);
        etInput = (EditText)findViewById(R.id.etInput);
    }

    public void reverseText(View v)
    {
    	Editable text = etInput.getText();
    	StringBuilder sb = new StringBuilder(text.length());
    	if(text != null && text.length() > 0)
    	{
    		for(int i= (text.length()-1); i >= 0; i--)
    		{
    			sb.append(text.charAt(i));
    		}
    		
    		tvOutput.setText(sb.toString());
    	}
    }
    
}