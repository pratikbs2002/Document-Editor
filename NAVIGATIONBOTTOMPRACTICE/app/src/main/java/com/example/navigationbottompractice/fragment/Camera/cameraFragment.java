package com.example.navigationbottompractice.fragment.Camera;

import static android.Manifest.permission.CAMERA;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.navigationbottompractice.R;
import com.example.navigationbottompractice.fragment.Files.CreateFolder;
import com.example.navigationbottompractice.fragment.Files.folderList;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cameraFragment extends Fragment {

    int PICK_IMAGE_MULTIPLE = 2;
    ImageView ivImage;
    String[] permission={"android.permission.CAMERA"};
    static final int reqCap=1;
    Uri imageUri;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public cameraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment localfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static cameraFragment newInstance(String param1, String param2) {
        cameraFragment fragment = new cameraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camerafragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnScan = view.findViewById(R.id.btnScan);
        Button btnSelectFromStorage = view.findViewById(R.id.btnSelectFromStorage);
        ivImage = view.findViewById(R.id.imageTP);

        btnScan.setOnClickListener(v -> {
            if(!permission()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permission, reqCap);
                }
            }else {
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

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = createImage();
                i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(i, reqCap);
            }
        });

        btnSelectFromStorage.setOnClickListener(v -> {
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
            startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // When an Image is picked
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Document Editor/Scanned Images/";
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK) {

            if (data.getData() != null) {
                // Get the Image from data
                Uri uri = data.getData();
                ContentResolver cR = requireActivity().getContentResolver();
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

                Intent intent = new Intent(getActivity(), folderList.class);
                intent.putExtra("path", path);
                intent.putExtra("btnCreatePdf", 2);
                intent.putExtra("camOrPhone", 2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                requireActivity().startActivity(intent);

            } else if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();

                    ContentResolver cR = requireActivity().getContentResolver();
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

                Intent intent = new Intent(getActivity(), folderList.class);
                intent.putExtra("path", path);
                intent.putExtra("btnCreatePdf", 2);
                intent.putExtra("camOrPhone", 2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                requireActivity().startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == reqCap) {
            if (resultCode == Activity.RESULT_OK) {
                String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Document Editor/";

                if ((!(new File(path1).exists()) ||
                        (!(new File(path1 + "/Scanned Images/").exists())) ||
                        (!(new File(path1 + "/PDF Files/").exists())))) {
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
                Intent intent = new Intent(getActivity(), folderList.class);
                intent.putExtra("path", path);
                intent.putExtra("btnCreatePdf", 2);
                intent.putExtra("camOrPhone", 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                requireActivity().startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Uri createImage(){
        Uri uri;
        ContentResolver resolver = getActivity().getContentResolver();

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

    public boolean permission() {
        int cam = ContextCompat.checkSelfPermission(requireActivity(), CAMERA);

        return cam == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(reqCap==1){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = createImage();
                i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(i, reqCap);
            }
            else
                Toast.makeText(getActivity(),"Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }
}