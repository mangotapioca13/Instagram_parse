package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.instagram.fragments.ComposeFragment;
import com.example.instagram.fragments.PostsFragment;
import com.example.instagram.fragments.ProfileFragment;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {

    private final String TAG = "HomeActivity";
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;

                switch (item.getItemId()) {
                    case R.id.miHome:
                        fragment = new PostsFragment();
                        break;
                    case R.id.miCompose:
                        fragment = new ComposeFragment();
                        break;
                    case R.id.miProfile:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.miLogOut:
                    default:
                        fragment = new ProfileFragment();
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        // set default selection
        bottomNavigationView.setSelectedItemId(R.id.miHome);
    }

    // methods to handle click on the LogOut menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the tool bar if it is present
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.miLogOut) {
            Toast.makeText(this, "LogOut Selected", Toast.LENGTH_SHORT).show();
            ParseUser.logOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.miCreate) {
            Intent intent = new Intent(HomeActivity.this, PostActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}