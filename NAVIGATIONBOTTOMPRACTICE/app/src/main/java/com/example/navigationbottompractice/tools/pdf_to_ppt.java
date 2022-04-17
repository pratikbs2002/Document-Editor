package com.example.navigationbottompractice.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.navigationbottompractice.R;

import java.util.Objects;

public class pdf_to_ppt extends AppCompatActivity {
    Button btnUploadPdf2PPT;
    int UploadPdf = 1;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_to_ppt);
        btnUploadPdf2PPT = findViewById(R.id.btnUploadPdf2PPT);
        toolbar = findViewById(R.id.main_tool_bar1);
        setTitle("PDF TO PPT");
        setSupportActionBar(toolbar);

        btnUploadPdf2PPT.setOnClickListener(v -> {
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

