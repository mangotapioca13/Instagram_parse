package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CreateActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etUsername;
    private EditText etPassword;
    private Button btCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btCreate = (Button) findViewById(R.id.btnCreate);

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                createAccount(email, username, password);
            }
        });
    }

    private void createAccount(String email, String username, String password) {
        // log out any current user
        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
        }

        // create the ParseUser
        ParseUser user = new ParseUser();

        // set core properties
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        // invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("CreateActivity", "Account creation successful!");
                    final Intent intent = new Intent(CreateActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d("CreateActivity", "Account creation failed.");
                    e.printStackTrace();
                }
            }
        });
    }
}