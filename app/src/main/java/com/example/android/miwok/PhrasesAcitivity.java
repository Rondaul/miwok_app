package com.example.android.miwok;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class PhrasesAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.container);

        if(fragment == null) {
            fragment = new PhrasesFragment();
        }

        fm.beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
    }
}

