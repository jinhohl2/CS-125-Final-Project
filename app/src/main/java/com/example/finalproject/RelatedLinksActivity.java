package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class RelatedLinksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_link);
        findViewById(R.id.mckinley).setOnClickListener(v -> {
            openMckinley();
            finish();
        });
        findViewById(R.id.cdc).setOnClickListener(v -> {
            openCDC();
            finish();
        });
    }
    public void openMckinley() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mckinley.illinois.edu/"));
        startActivity(browserIntent);
    }
    public void openCDC() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cdc.gov/coronavirus/2019-nCoV/index.html"));
        startActivity(browserIntent);
    }
}
