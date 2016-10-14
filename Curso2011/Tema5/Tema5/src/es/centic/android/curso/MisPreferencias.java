package es.centic.android.curso;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MisPreferencias extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.menu_preferencias);
	}
}
