package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String nick;
    DatabaseReference rootref;
    List<User> userList;

    String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nick = getIntent().getStringExtra("nick");
        userList = new ArrayList<>();
        rootref = FirebaseDatabase.getInstance().getReference("users");
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    userList.add(user.getValue(User.class));
                }
                for (User item : userList) {
                    if (nick.equals(item.getNick())) {
                        status = item.getStatus();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        findViewById(R.id.diagnose).setOnClickListener(v -> {
            if (status.equals("yellow")) {
                Intent intent = new Intent(this, DiagnoseActivity.class);
                intent.putExtra("id", "yellow");
                intent.putExtra("nick", nick);
                startActivity(intent);
            } else if (status.equals("red")) {
                Intent intent = new Intent(this, DiagnoseActivity.class);
                intent.putExtra("id", "red");
                intent.putExtra("nick", nick);
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, DiagnoseActivity.class);
                intent.putExtra("id", "green");
                intent.putExtra("nick", nick);
                startActivity(intent);
            }

        });
        findViewById(R.id.relatedLinks).setOnClickListener(v -> {
            Intent intent = new Intent(this, RelatedLinksActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.EmergencyCall).setOnClickListener(v -> {
            dialContactPhone("1339");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to call 1339 for an immediate medical aid?");
            builder.setPositiveButton("Call", (unused1, unused2) -> dialContactPhone("1339"));
            builder.setNegativeButton("Cancel", null);
            builder.create().show();
        });
        findViewById(R.id.map).setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("nick", nick);
            startActivity(intent);
        });
        findViewById(R.id.logout).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intToLogin = new Intent (MainActivity.this, Login.class);
            startActivity(intToLogin);
        });
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}
