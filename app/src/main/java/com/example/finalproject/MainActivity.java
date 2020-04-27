package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

    private GoogleMap map;
    private Button btnLogout;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthtateListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.diagnose).setOnClickListener(v -> {
            Intent intent = new Intent(this, DiagnoseActivity.class);
            intent.putExtra("id", "");
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
