package com.example.navigationbottompractice.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.navigationbottompractice.R;
import com.example.navigationbottompractice.fragment.Files.CreateFolder;
import com.example.navigationbottompractice.fragment.Files.folderList;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class jpg_to_pdf extends AppCompatActivity {
    Button btnUploadJPG2PDF;
    ImageView ivImage;
    int PICK_IMAGE_MULTIPLE = 1;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpg_to_pdf);
        btnUploadJPG2PDF = findViewById(R.id.btnUploadJPG2PDF);
        ivImage = findViewById(R.id.imageTP2);
        toolbar = findViewById(R.id.main_tool_bar1);
        setTitle("JPG TO PDF");
        setSupportActionBar(toolbar);

        btnUploadJPG2PDF.setOnClickListener(v -> {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Document Editor/";

            if ((!(new File(path).exists()) ||
                    (!(new File(path + "/Scanned Images/").exists())) ||
                    (!(new File(path + "/PDF Files/").exists())))) {
                CreateFolder.createFolder(Environment.getExternalStorageDirectory());
            }
            String pathOfScannedImages = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Document Editor/Scanned Images/";
            File images = new File(pathOfScannedImages);
            File[] listOfImages = images.listFiles();
            if (listOfImages != null) {
                int i = 0;
                while (i < listOfImages.length) {
                    //noinspection ResultOfMethodCallIgnored
                    listOfImages[i].delete();
                    i++;
                }
            }

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGE_MULTIPLE);
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // When an Image is picked
        super.onActivityResult(requestCode, resultCode, data);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Document Editor/Scanned Images/";
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK) {

            if (data.getData() != null) {
                // Get the Image from data
                Uri uri = data.getData();
                ContentResolver cR = getContentResolver();
                String[] type = cR.getType(uri).split("/");
                if (type[0].equals("image")) {
                    ivImage.setImageURI(uri);
                    Bitmap image = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();

                    String ms = "_".concat(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                    String name = ("IMG_".concat(sdf.format(new Date()).concat(ms))).concat(".jpeg");
                    File file = new File(path, name);
                    try {
                        //noinspection ResultOfMethodCallIgnored
                        file.createNewFile();
                        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent(this, folderList.class);
                intent.putExtra("path", path);
                intent.putExtra("btnCreatePdf", 2);
                intent.putExtra("camOrPhone", 2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();

                    ContentResolver cR = getContentResolver();
                    String[] type = cR.getType(uri).split("/");
                    if (type[0].equals("image")) {
                        ivImage.setImageURI(uri);
                        Bitmap image = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();

                        String ms = "_".concat(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                        String name = ("IMG_".concat(sdf.format(new Date()).concat(ms))).concat(".jpeg");
                        File file = new File(path, name);
                        try {
                            //noinspection ResultOfMethodCallIgnored
                            file.createNewFile();
                            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                            image.compress(Bitmap.CompressFormat.JPEG, 100, os);
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Intent intent = new Intent(this, folderList.class);
                intent.putExtra("path", path);
                intent.putExtra("btnCreatePdf", 2);
                intent.putExtra("camOrPhone", 2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
    }
}
