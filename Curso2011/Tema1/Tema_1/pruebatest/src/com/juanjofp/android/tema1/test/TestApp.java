package com.juanjofp.android.tema1.test;

import com.juanjofp.android.tema1.AplicacionActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;
import android.widget.TextView;

import com.juanjofp.android.tema1.R;

public class TestApp extends ActivityInstrumentationTestCase2<AplicacionActivity> {

	private TextView mFoto;
	
	public TestApp() {
		super("com.juanjofp.android.tema", AplicacionActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		mFoto = (TextView)getActivity().findViewById(R.id.tvPrueba);
	}
	
	public void testPrueba1(){
		assertEquals("Hola mundo", mFoto.getText());
	}

}