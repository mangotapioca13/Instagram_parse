package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private Button btnLogInSelect;
    private Button btnNewSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser currUser = ParseUser.getCurrentUser();
        // if there is a current user, then bring them to their home
        // else bring user to start page for login or create new account
        if (currUser != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            btnLogInSelect = (Button) findViewById(R.id.btnLoginSelect);
            btnNewSelect = (Button) findViewById(R.id.btnNewAccount);

            btnLogInSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

            btnNewSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}