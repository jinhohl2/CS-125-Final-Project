package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    private GoogleMap map;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthtateListner;
    private BroadcastReceiver broadcastReceiver;
    private static final float REQUIRED_LOCATION_ACCURACY = 28f;
    private LatLng latLngToUpdate;
    private String nick;
    DatabaseReference rootref;
    List<User> userList;
    TextView color;

    String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        color = findViewById(R.id.color);
        nick = getIntent().getStringExtra("nick");
        mFirebaseAuth = FirebaseAuth.getInstance();
        userList = new ArrayList<>();
        rootref = FirebaseDatabase.getInstance().getReference("users");
        System.out.println(1);
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    System.out.println(2);
                    userList.add(user.getValue(User.class));
                    System.out.println(2.5);
                    System.out.println(userList.size() + "size");


                }
                for (User item : userList) {
                    if (nick.equals(item.getNick())) {
                        System.out.println(101);
                        status = item.getStatus();
                        System.out.println(status);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        System.out.println(userList.size() + "size");
        System.out.println(nick);
        System.out.println(status);
        if (status.equals("yellow")) {
            findViewById(R.id.diagnose).setOnClickListener(v -> {
                Intent intent = new Intent(this, DiagnoseActivity.class);
                intent.putExtra("id", "yellow");
                intent.putExtra("nick", nick);
                startActivity(intent);
                finish();
            });
        }
        findViewById(R.id.diagnose).setOnClickListener(v -> {
            Intent intent = new Intent(this, DiagnoseActivity.class);
            intent.putExtra("id", "green");
            intent.putExtra("nick", nick);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.relatedLinks).setOnClickListener(v -> {
            Intent intent = new Intent(this, RelatedLinksActivity.class);
            startActivity(intent);
            finish();
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
            finish();
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

    /**
    public void save(String text) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String load() {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            String string = sb.toString();
            return string;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
**/
