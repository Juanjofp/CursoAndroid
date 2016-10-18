package com.juanjofp.android.tema3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class SaveRestoreState extends Activity
{
    /**
     * Initialization of the Activity after it is first created.  Here we use
     * {@link android.app.Activity#setContentView setContentView()} to set up
     * the Activity's content, and retrieve the EditText widget whose state we
     * will save/restore.
     */
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        // Be sure to call the super class.
        super.onCreate(savedInstanceState);

        // See assets/res/any/layout/save_restore_state.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        setContentView(R.layout.save_restore_state);


    }

    /**
     * Retrieve the text that is currently in the "saved" editor.
     */
    CharSequence getSavedText() {
        return ((EditText)findViewById(R.id.saved)).getText();
    }

    /**
     * Change the text that is currently in the "saved" editor.
     */
    void setSavedText(CharSequence text) {
        ((EditText)findViewById(R.id.saved)).setText(text);
    }
}