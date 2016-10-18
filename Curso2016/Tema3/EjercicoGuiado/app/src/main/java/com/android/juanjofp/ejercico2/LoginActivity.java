package com.android.juanjofp.ejercico2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.juanjofp.ejercico2.models.BookFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;

    public static Intent navigateTo(Context ctx) {
        return new Intent(ctx, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText)findViewById(R.id.etUsernameLoginActivity);
        etPassword = (EditText)findViewById(R.id.etPasswordLoginActivity);

        findViewById(R.id.btLoginActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Connectar al ws y loguear
                BookFactory.TOKEN = "codigosupersecreto";
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
