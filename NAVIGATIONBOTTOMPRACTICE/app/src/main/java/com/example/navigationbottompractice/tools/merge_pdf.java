package com.example.navigationbottompractice.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.navigationbottompractice.R;

import java.util.Objects;

public class merge_pdf extends AppCompatActivity {
    Toolbar toolbar;
    Button btnUploadPdfMerge;
    int UploadPdf = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_pdf);
        btnUploadPdfMerge = findViewById(R.id.btnUploadPdfMerge);
        toolbar = findViewById(R.id.main_tool_bar1);
        setTitle("Merge PDFs");
        setSupportActionBar(toolbar);


        btnUploadPdfMerge.setOnClickListener(v -> {
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