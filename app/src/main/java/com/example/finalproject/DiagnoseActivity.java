package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class DiagnoseActivity extends AppCompatActivity {
    Switch tempButton;
    Switch coughButton;
    Switch throatButton;
    Switch diarrheaButton;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);
        tempButton = findViewById(R.id.tempButton);
        coughButton = findViewById(R.id.coughButton);
        throatButton = findViewById(R.id.throatButton);
        diarrheaButton = findViewById(R.id.diarrheaButton);
        Intent intent = getIntent();
        String mode = intent.getStringExtra("id");
        tempButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    count++;
                }
            }
        });
        coughButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    count++;
                }
            }
        });
        throatButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    count++;
                }
            }
        });
        diarrheaButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    count++;
                }
            }
        });
        findViewById(R.id.sendButton).setOnClickListener(v -> {
            if (count > 1 && mode.equals("yellow")) {
                Intent yellow = new Intent(this, YellowActivity.class);
                yellow.putExtra("id", "danger");
                startActivity(yellow);
                finish();
            } else if (count > 1) {
                Intent yellow = new Intent(this, YellowActivity.class);
                yellow.putExtra("id", "caution");
                startActivity(yellow);
                finish();
            } else {
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                finish();
            }

        });
    }
}
