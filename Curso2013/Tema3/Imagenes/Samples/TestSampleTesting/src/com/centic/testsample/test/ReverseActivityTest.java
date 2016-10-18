package com.centic.testsample.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.centic.testsample.MainActivity;

public class ReverseActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Activity mActivity;
	private TextView tvResult;
	private EditText etSource;
	private Button btnReverse;
	
	public ReverseActivityTest() 
	{
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void setUp() throws Exception 
	{
		// TODO Auto-generated method stub
		super.setUp();
		
		setActivityInitialTouchMode(false);
		
		mActivity = getActivity();
		
		tvResult = (TextView)mActivity.findViewById(com.centic.testsample.R.id.tvOutput);
		etSource = (EditText)mActivity.findViewById(com.centic.testsample.R.id.etInput);
		btnReverse = (Button)mActivity.findViewById(com.centic.testsample.R.id.btReverse);
		
		
	}
	
	public void testPreConditions()
	{
		assertTrue(TextUtils.isEmpty(etSource.getText()));
		
		assertTrue(!TextUtils.isEmpty(tvResult.getText()));
	}
	
	@UiThreadTest
	public void testReverse()
	{
		
		
		etSource.setText("Hello World");
		btnReverse.requestFocus();
		btnReverse.performClick();
		
		assertEquals(tvResult.getText().toString(), "dlro olleH");
	}
	
}