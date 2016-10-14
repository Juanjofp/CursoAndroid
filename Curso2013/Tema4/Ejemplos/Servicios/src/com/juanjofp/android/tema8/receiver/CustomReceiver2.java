package com.juanjofp.android.tema8.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver2 extends BroadcastReceiver {

	public static final String CUSTOM_RECEIVER2 = "String com.juanjofp.android.CUSTOM_RECEIVER2";

	@Override
	public void onReceive(Context ctx, Intent data) {
		// TODO Auto-generated method stub
		Toast.makeText(ctx, "Recibido CustomReceiver2", Toast.LENGTH_LONG).show();
	}

}
