package com.example.navigationbottompractice.fragment.Files;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


public class ActualPath {

    public static String RealPath(Uri uri, Activity activity){

        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(uri,filePath,null,null,null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePath[0]);
        String myPath = cursor.getString(columnIndex);
        cursor.close();

        return myPath;
    }
}
