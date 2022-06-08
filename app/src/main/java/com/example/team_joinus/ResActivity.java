package com.example.team_joinus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dibslist);

        Intent intent = getIntent();
        String my_facility = intent.getStringExtra("fac_title");
        Toast.makeText(getApplicationContext(), my_facility, Toast.LENGTH_SHORT).show();
    }
}
