package com.junajofp.android.testing.test;

import com.junajofp.android.testing.HelloAndroidActivity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import com.junajofp.android.testing.R;

public class HelloAndroidTest extends
		ActivityInstrumentationTestCase2<HelloAndroidActivity> {
	
	private Activity mActv;
	private Button mBtn;
	private CheckBox mchk;

	public HelloAndroidTest() {
		super("com.junajofp.android.testing",HelloAndroidActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		mActv = getActivity();
		mBtn = (Button)mActv.findViewById(R.id.button2);
		mchk = (CheckBox)mActv.findViewById(R.id.checkBox1);
	}
	
	public void testPreconditions(){
		assertNotNull(mBtn);
		assertNotNull(mchk);
	}
	
	public void testCheckedDefault(){
		assertEquals(true, mchk.isChecked());
	}
	
	public void testButtonText(){
		assertEquals("Hello Android", mBtn.getText());
	}

}
