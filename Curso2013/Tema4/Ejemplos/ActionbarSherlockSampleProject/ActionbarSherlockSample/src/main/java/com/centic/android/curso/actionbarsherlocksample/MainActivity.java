package com.centic.android.curso.actionbarsherlocksample;


import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


public class MainActivity extends com.actionbarsherlock.app.SherlockFragmentActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSherlock().getActionBar().hide();

        //getSherlock().getActionBar().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Usin Compability Pack
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Toast.makeText(this, "Icono pulsado", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Accion 1 pulsada", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_2:
                Toast.makeText(this, "Accion 2 pulsada", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_3:
                Toast.makeText(this, "Accion 3 pulsada", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void runCAB()
    {
        startActionMode(cabCallback);
    }

    ActionMode.Callback cabCallback = new ActionMode.Callback(){

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu)
        {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode)
        {

        }
    };
}
