package com.example.navigationbottompractice.fragment.Files;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationbottompractice.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class folderList extends AppCompatActivity {
    private static final int PICK_IMAGE_MULTIPLE = 2;
    Uri imageUri;
    static final int reqCap=1;
    ImageView ivImage;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_list);
        int showBtnCreatePdf = getIntent().getIntExtra("btnCreatePdf", 0);
        int camOrPhone = getIntent().getIntExtra("camOrPhone", 0);
        ivImage = findViewById(R.id.imageTP1);
        Button btnCreatePdf = findViewById(R.id.btnCreatePdf);
        Button btnAddMore = findViewById(R.id.btnAddMore);

        btnCreatePdf.setVisibility(View.INVISIBLE);
        btnCreatePdf.setClickable(false);
        btnAddMore.setVisibility(View.INVISIBLE);
        btnAddMore.setClickable(false);
        if(showBtnCreatePdf == 2){
            btnCreatePdf.setVisibility(View.VISIBLE);
            btnAddMore.setVisibility(View.VISIBLE);

            btnCreatePdf.setOnClickListener(v -> {
                JPEG_PDF obj = new JPEG_PDF();
                try {
                    obj.createPdf(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

            btnAddMore.setOnClickListener(v -> {
                if(camOrPhone == 1){
                    //CAM
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    imageUri = createImage();
                    i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(i, reqCap);
                }
                else{
                    //PHONE
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
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

    private Uri createImage(){
        Uri uri;
        ContentResolver resolver = getContentResolver();

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q) {
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        }
        else{
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        String ms = "_".concat(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String name = ("IMG_".concat(sdf.format(new Date()).concat(ms))).concat(".jpeg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues contentValues=new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME,name);
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH,"Document Editor/Scanned Images");
            return resolver.insert(uri, contentValues);
        }else{
            try {
                String imgSaved = MediaStore.Images.Media.insertImage(resolver, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()+"Document Editor/Scanned Images", name, ".");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return uri;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // When an Image is picked
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
                this.finish();

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
                this.finish();
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == reqCap) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent(this, folderList.class);
                intent.putExtra("path", path);
                intent.putExtra("btnCreatePdf", 2);
                intent.putExtra("camOrPhone", 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}