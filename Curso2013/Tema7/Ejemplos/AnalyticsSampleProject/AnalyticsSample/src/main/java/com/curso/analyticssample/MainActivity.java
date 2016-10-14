package com.curso.analyticssample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.Tracker;
import com.google.analytics.tracking.android.Transaction;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GAServiceManager.getInstance().setDispatchPeriod(10);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        EasyTracker.getInstance().activityStart(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        EasyTracker.getInstance().activityStop(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.action_error)
        {
            try
            {
                String nullpointer = null;
                nullpointer.length();
            }
            catch (Exception e)
            {
                EasyTracker.getTracker().sendException(e.getMessage(), false);
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickDimension(View v)
    {
        EasyTracker.getTracker().setCustomDimension(1, String.valueOf(v.getId()));
        EasyTracker.getTracker().sendView();
    }

    public void onClickMetrics(View v)
    {
        EasyTracker.getTracker().setCustomDimension(1, String.valueOf(v.getId()));
        EasyTracker.getTracker().sendView("MainACtivity");
    }

    public void onClickCommerce(View v)
    {
        Transaction myTrans = new Transaction.Builder(
                "0_123456",                                           // (String) Transaction Id, should be unique.
                (long) (2.16 * 1000000))                              // (long) Order total (in micros)
                .setAffiliation("In-App Store")                       // (String) Affiliation
                .setTotalTaxInMicros((long) (0.17 * 1000000))         // (long) Total tax (in micros)
                .setShippingCostInMicros(0)                           // (long) Total shipping cost (in micros)
                .build();

        myTrans.addItem(new Transaction.Item.Builder(
                "L_789",                                              // (String) Product SKU
                "Level Pack: Space",                                  // (String) Product name
                (long) (1.99 * 1000000),                              // (long) Product price (in micros)
                (long) 1)                                             // (long) Product quantity
                .setProductCategory("Game expansions")                // (String) Product category
                .build());

        Tracker myTracker = EasyTracker.getTracker(); // Get reference to tracker.
        myTracker.sendTransaction(myTrans); // Send the transaction.
    }

    public void onClickEvents(View v)
    {
        EasyTracker.getTracker().sendEvent("MainActivity", "Testing Events", "Event Button", Long.valueOf((long)v.getHeight()));
    }

    public void onClickSocial(View v)
    {
        Tracker tracker = EasyTracker.getTracker();  // Get tracker object.
        tracker.sendSocial("Twitter", "Tweet", "https://www.centic.es/");  // Send social interaction.
    }

    public void onClickTiming(View v)
    {
        EasyTracker.getTracker().sendTiming("Listado Contactos", v.getDrawingTime(), "Contactos", "Mis Contactos");
    }
}
