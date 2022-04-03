package com.example.navigationbottompractice;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.navigationbottompractice.fragment.Files.CreateFolder;
import com.example.navigationbottompractice.fragment.Files.filesFragment;
import com.example.navigationbottompractice.fragment.homefragment;
import com.example.navigationbottompractice.fragment.Camera.cameraFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    boolean check1 = false, check2 = true;
    int check = 0;
    final static int REQUEST_CODE = 333;
    Toolbar toolbar;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        check = 1;
        transaction.replace(R.id.container,new homefragment()).commit();

        toolbar = findViewById(R.id.main_tool_bar);
        setSupportActionBar(toolbar);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()){
                case R.id.home:
                    check = 1;
                    transaction1.replace(R.id.container,new homefragment()).commit();
                    break;

                case R.id.files:
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Document Editor/";
                    if (!permission()) {
                        RequiresPermission_Dialog();
                    }
                    else{
                        transaction1.replace(R.id.container,new filesFragment()).commit();
                    }
                    if((!(new File(path).exists()) ||
                            (!(new File(path + "/Scanned Images/").exists()))  ||
                            (!(new File(path + "/PDF Files/").exists())))){
                        CreateFolder.createFolder(Environment.getExternalStorageDirectory());
                    }
                    break;

                case R.id.camera:
                    check = 2;
                    transaction1.replace(R.id.container,new cameraFragment()).commit();
                    break;
            }
            return true;
        });
    }

    // start : method for toolbar(task bar)......................
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_button,menu);
        return true;
    }
    // finish : method for toolbar(task bar).......................

    //start : method for calling favourite and setting (task bar).......................
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, setting.class);
                startActivity(intent);
                return true;

            case R.id.favourite:
                Intent intent1 = new Intent(MainActivity.this, favourite.class);
                startActivity(intent1);
                return true;

            default:
                    return super.onOptionsItemSelected(item);

        }
    }
    //finish : method for calling favourite and setting (task bar).......................

    public void RequiresPermission_Dialog() {
        if(SDK_INT >= Build.VERSION_CODES.R){
            try{
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2000);
            }catch (Exception e){
                Intent obj = new Intent();
                obj.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(obj, 2000);
            }
        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean storage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (storage && read) {
                    //Granted
                    Toast.makeText(MainActivity.this, "Permission is Granted Successfully", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.replace(R.id.container, new filesFragment()).commit();
                } else {
                    //Not Granted
                    Toast.makeText(MainActivity.this, "Permission Is Required To Open Files", Toast.LENGTH_SHORT).show();
                    bottomNavigationView = findViewById(R.id.bottom_nav);
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    if(check == 1){
                        bottomNavigationView.getMenu().getItem(1).setChecked(true);
                        transaction1.replace(R.id.container, new homefragment()).commit();
                    }
                    else if(check == 2){
                        bottomNavigationView.getMenu().getItem(2).setChecked(true);
                        transaction1.replace(R.id.container, new cameraFragment()).commit();
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(check1 || check2) {
            if(check2){
                check2 = false;
                check1 = true;
            }
            else {
                check2 = true;
                check1 = false;
                if (requestCode == 2000) {
                    if (SDK_INT >= Build.VERSION_CODES.R) {
                        if (Environment.isExternalStorageManager()) {
                            Toast.makeText(MainActivity.this, "Permission is Granted Successfully", Toast.LENGTH_SHORT).show();
                            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                            transaction1.replace(R.id.container, new filesFragment()).commit();
                        } else {
                            Toast.makeText(MainActivity.this, "Permission Is Required To Open Files", Toast.LENGTH_SHORT).show();
                            bottomNavigationView = findViewById(R.id.bottom_nav);
                            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                            if(check == 1){
                                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                                transaction1.replace(R.id.container, new homefragment()).commit();
                            }
                            else if(check == 2){
                                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                                transaction1.replace(R.id.container, new cameraFragment()).commit();
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean permission() {
        if(SDK_INT >= Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }
        else{
            int write = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED || read == PackageManager.PERMISSION_GRANTED;
        }
    }
}