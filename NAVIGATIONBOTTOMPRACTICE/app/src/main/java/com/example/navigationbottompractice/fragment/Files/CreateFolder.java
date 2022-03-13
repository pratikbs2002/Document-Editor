package com.example.navigationbottompractice.fragment.Files;

import android.content.Context;

import java.io.File;

public class CreateFolder {
    public static void createFolder(File root, Context c){
        File dir = new File (root.getAbsolutePath() + "/Document Editor/"); //it is my root directory


        File Scanner = new File (root.getAbsolutePath() + "/Document Editor/" + "Scanned"); // it is my sub folder directory .. it can vary..

        try {
            if(!dir.exists()) {
                //noinspection ResultOfMethodCallIgnored
                dir.mkdirs();
            }
            if(!Scanner.exists()) {
                //noinspection ResultOfMethodCallIgnored
                Scanner.mkdirs();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
