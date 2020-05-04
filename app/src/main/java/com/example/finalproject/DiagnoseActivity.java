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
            if (mode.equals("red")) {
                if (count > 1) {
                    Intent rtr = new Intent(this, MainActivity.class);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("COVID-19 contagion suspected. Please visit the nearest hospital or call 1339 for emergency medical aid." + "Your location will be tracked when you turn on map.");
                    builder.setOnDismissListener(unused -> {
                        rtr.putExtra("nick", nick);
                        startActivity(rtr);
                    });
                    builder.create().show();
                } else {
                    final DatabaseReference rootref;
                    rootref = FirebaseDatabase.getInstance().getReference("users");
                    DatabaseReference userRef = rootref.child(nick);
                    Map<String, Object> userUpdates = new HashMap<>();
                    userUpdates.put("status", "yellow");
                    userRef.updateChildren(userUpdates);

                    Intent rty = new Intent(this, MainActivity.class);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("You are showing COVID-19 symptoms. Please abstain from outdoor activity." + "Your location will be tracked when you turn on map.");
                    builder.setOnDismissListener(unused -> {
                        rty.putExtra("nick", nick);
                        startActivity(rty);
                    });
                    builder.create().show();
                }
            } else if (mode.equals("yellow")) {
                if (count > 1) {
                    final DatabaseReference rootref;
                    rootref = FirebaseDatabase.getInstance().getReference("users");
                    DatabaseReference userRef = rootref.child(nick);
                    Map<String, Object> userUpdates = new HashMap<>();
                    userUpdates.put("status", "red");
                    userRef.updateChildren(userUpdates);

                    Intent ytr = new Intent(this, MainActivity.class);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("COVID-19 contagion suspected. Please visit the nearest hospital or call 1339 for emergency medical aid." + "Your location will be tracked when you turn on map.");
                    builder.setOnDismissListener(unused -> {
                        ytr.putExtra("nick", nick);
                        startActivity(ytr);
                    });
                    builder.create().show();
                } else {
                    final DatabaseReference rootref;
                    rootref = FirebaseDatabase.getInstance().getReference("users");
                    DatabaseReference userRef = rootref.child(nick);
                    Map<String, Object> userUpdates = new HashMap<>();
                    userUpdates.put("status", "green");
                    userRef.updateChildren(userUpdates);

                    Intent ytg = new Intent(this, MainActivity.class);
                    ytg.putExtra("nick", nick);
                    startActivity(ytg);
                }
            } else {
                if (count > 1) {
                    final DatabaseReference rootref;
                    rootref = FirebaseDatabase.getInstance().getReference("users");
                    DatabaseReference userRef = rootref.child(nick);
                    Map<String, Object> userUpdates = new HashMap<>();
                    userUpdates.put("status", "yellow");
                    userRef.updateChildren(userUpdates);


                    Intent gty = new Intent(this, MainActivity.class);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("You are showing COVID-19 symptoms. Please abstain from outdoor activity." + "Your location will be tracked when you turn on map.");
                    builder.setOnDismissListener(unused -> {
                        gty.putExtra("nick", nick);
                        startActivity(gty);
                    });
                    builder.create().show();
                } else {
                    Intent gtg = new Intent(this, MainActivity.class);
                    gtg.putExtra("nick", nick);
                    startActivity(gtg);
                }
            }
        });
    }
}
