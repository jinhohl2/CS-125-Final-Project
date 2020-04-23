package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Diagnose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);
        findViewById(R.id.sendButton).setOnClickListener(v -> {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            main.putExtra("symptom", "o");
            finish();

        });
    }
}
