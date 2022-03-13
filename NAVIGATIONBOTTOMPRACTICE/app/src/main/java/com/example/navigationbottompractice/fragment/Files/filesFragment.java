package com.example.navigationbottompractice.fragment.Files;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.navigationbottompractice.R;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link filesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class filesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public filesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gradefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static filesFragment newInstance(String param1, String param2) {
        filesFragment fragment = new filesFragment();
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
        return inflater.inflate(R.layout.fragment_filesfragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnInternalStorage = view.findViewById(R.id.btnInternalStorage);
        Button btnDownloads = view.findViewById(R.id.btnDownloads);
        Button btnAppFolder = view.findViewById(R.id.btnAppFolder);


        btnInternalStorage.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), folderList.class);
            String path = Environment.getExternalStorageDirectory().getPath();
            intent.putExtra("path", path);
            startActivity(intent);
        });

        btnDownloads.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), folderList.class);
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            intent.putExtra("path", path);
            startActivity(intent);
        });

        btnAppFolder.setOnClickListener(view1 -> {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Document Editor/";
            if((!(new File(path).exists()) || (!(new File(path + "/Scanned/").exists()))) ){
                CreateFolder.createFolder(Environment.getExternalStorageDirectory(), getActivity());
            }
            Intent intent = new Intent(getActivity(), folderList.class);
            intent.putExtra("path", path);
            startActivity(intent);
        });
    }
}