package com.example.navigationbottompractice.fragment.Camera;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.navigationbottompractice.R;

public class showCapturedImage extends AppCompatActivity {

    ImageView ivShowImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_captured_image);

        Uri uri = getIntent().getParcelableExtra("UriOfImage");

        ivShowImage = findViewById(R.id.ivShowImage);
        ivShowImage.setImageURI(uri);
    }
}