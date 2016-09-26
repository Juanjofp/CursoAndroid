package tema1.centic.es.tema1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Tema 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperamos el widget
        TextView tvWelcome = (TextView)findViewById(R.id.tvWelcomeMainActivity);
        String msg = tvWelcome.getText().toString();
        Log.e(TAG, msg);
        tvWelcome.setText("Hello Android World");
    }
}
