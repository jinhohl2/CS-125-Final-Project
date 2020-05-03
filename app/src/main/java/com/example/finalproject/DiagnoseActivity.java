package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DiagnoseActivity extends AppCompatActivity {
    Switch tempButton;
    Switch coughButton;
    Switch throatButton;
    Switch diarrheaButton;
    TextView color;
    int count = 0;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthtateListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);
        tempButton = findViewById(R.id.tempButton);
        coughButton = findViewById(R.id.coughButton);
        throatButton = findViewById(R.id.throatButton);
        diarrheaButton = findViewById(R.id.diarrheaButton);
        color = findViewById(R.id.color);
        mFirebaseAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String mode = intent.getStringExtra("id");
        String nick = intent.getStringExtra("nick");
        color.setText(mode);
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
                /**
                Intent yellow = new Intent(this, YellowActivity.class);
                 **/
                final DatabaseReference rootref;
                rootref = FirebaseDatabase.getInstance().getReference("users");
                DatabaseReference userRef = rootref.child(nick);
                Map<String, Object> userUpdates = new HashMap<>();
                userUpdates.put("status", "red");
                userRef.updateChildren(userUpdates);

                Intent green = new Intent(this, MainActivity.class);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Coronavirus contagion suspected. Please visit the nearest hospital or call 1339 for emergency medical aid.");
                builder.setOnDismissListener(unused -> {
                    green.putExtra("nick", nick);
                    startActivity(green);
                });
                builder.create().show();

            } else if (count > 1) {
                final DatabaseReference rootref;
                rootref = FirebaseDatabase.getInstance().getReference("users");
                DatabaseReference userRef = rootref.child(nick);
                Map<String, Object> userUpdates = new HashMap<>();
                userUpdates.put("status", "yellow");
                userRef.updateChildren(userUpdates);

                Intent green = new Intent(this, MainActivity.class);
                green.putExtra("nick", nick);
                startActivity(green);

                /**
                Intent yellow = new Intent(this, YellowActivity.class);
                yellow.putExtra("id", "Caution");
                startActivity(yellow);
                finish();
                 **/
            } else {
                Intent main = new Intent(this, MainActivity.class);
                main.putExtra("nick", nick);
                startActivity(main);
                finish();
            }

        });
    }
}
