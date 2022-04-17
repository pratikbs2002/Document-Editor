package com.example.navigationbottompractice.fragment.Files;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationbottompractice.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class folderList extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_list);
        int showBtnCreatePdf = getIntent().getIntExtra("btnCreatePdf", 0);

        Button btnCreatePdf = findViewById(R.id.btnCreatePdf);
        btnCreatePdf.setVisibility(View.INVISIBLE);
        btnCreatePdf.setClickable(false);
        if(showBtnCreatePdf == 2){
            btnCreatePdf.setVisibility(View.VISIBLE);
            btnCreatePdf.setOnClickListener(v -> {
                JPEG_PDF obj = new JPEG_PDF();
                try {
                    obj.createPdf(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }

        String path = getIntent().getStringExtra("path");
        File root = new File(path);
        FileFilter filterDirectory = file -> !file.isHidden() && (file.getName().endsWith(".pdf") ||
                file.getName().endsWith(".doc") || file.getName().endsWith(".docx") ||
                file.getName().endsWith(".ppt") || file.getName().endsWith(".jpg") || file.getName().endsWith(".png") ||
                file.getName().endsWith(".jpeg") || file.isDirectory() || file.getName().endsWith(".txt") ||
                file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx"));
        File[] fileList = root.listFiles(filterDirectory);

        toolbar = findViewById(R.id.main_tool_bar1);
        setTitle(root.getName());
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

        RecyclerView rvRecyclerView = findViewById(R.id.recyclerView);
        TextView tvNoFiles = findViewById(R.id.NoFiles);

        if(fileList == null || fileList.length == 0){
            tvNoFiles.setVisibility(View.VISIBLE);
            return;
        }
        tvNoFiles.setVisibility(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Arrays.sort(fileList, Comparator.comparing(file -> file.getName().toUpperCase()));
            Arrays.sort(fileList, Comparator.comparing(file -> !file.isDirectory()));
        }
        rvRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rvRecyclerView.setAdapter(new ListAdapter(getApplicationContext(), fileList));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}