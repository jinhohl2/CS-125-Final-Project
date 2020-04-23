package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class YellowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellow);
        TextView text = findViewById(R.id.textView10);
        Intent item = getIntent();
        String id = item.getStringExtra("id");
        if (id.equals("Danger")) {
            text.setText("Danger");
        }
        findViewById(R.id.diagnose).setOnClickListener(v -> {
            Intent intent = new Intent(this, DiagnoseActivity.class);
            intent.putExtra("id", "yellow");
            startActivity(intent);
            finish();
        });
        findViewById(R.id.relatedLinks).setOnClickListener(v -> {
            Intent intent = new Intent(this, RelatedLinksActivity.class);
            startActivity(intent);
            finish();

        });
    }
}
