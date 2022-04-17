package com.example.navigationbottompractice.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.navigationbottompractice.R;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class signature extends AppCompatActivity {
    Button btnUploadPdf2Sign;
    int UploadPdf = 1;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        btnUploadPdf2Sign = findViewById(R.id.btnUploadPdf2Sign);
        toolbar = findViewById(R.id.main_tool_bar1);
        setTitle("SIGNATURE");
        setSupportActionBar(toolbar);

        btnUploadPdf2Sign.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select PDF"), UploadPdf);
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}