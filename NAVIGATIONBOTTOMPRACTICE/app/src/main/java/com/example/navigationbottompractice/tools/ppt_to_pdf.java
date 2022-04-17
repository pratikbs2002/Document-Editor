package com.example.navigationbottompractice.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.example.navigationbottompractice.R;

import java.util.Objects;

public class ppt_to_pdf extends AppCompatActivity {
    Button btnUploadPPT2PDF;
    int UploadPdf = 1;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppt_to_pdf);
        btnUploadPPT2PDF = findViewById(R.id.btnUploadPPT2PDF);
        toolbar = findViewById(R.id.main_tool_bar1);
        setTitle("PPT TO PDF");
        setSupportActionBar(toolbar);

        btnUploadPPT2PDF.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("application/vnd.ms-powerpoint");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select PPT"), UploadPdf);
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