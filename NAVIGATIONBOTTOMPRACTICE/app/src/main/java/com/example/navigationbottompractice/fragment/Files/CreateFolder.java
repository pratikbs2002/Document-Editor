package com.example.navigationbottompractice.fragment.Files;

import java.io.File;

public class CreateFolder {
    public static void createFolder(File root){
        File dir = new File (root.getAbsolutePath() + "/Document Editor/"); //it is my root directory


        File Scanned_Images = new File (root.getAbsolutePath() + "/Document Editor/" + "Scanned Images");

        File PDFs = new File (root.getAbsolutePath() + "/Document Editor/" + "PDF Files");

        try {
            if(!dir.exists()) {
                //noinspection ResultOfMethodCallIgnored
                dir.mkdirs();
            }
            if(!Scanned_Images.exists()) {
                //noinspection ResultOfMethodCallIgnored
                Scanned_Images.mkdirs();
            }
            if(!PDFs.exists()) {
                //noinspection ResultOfMethodCallIgnored
                PDFs.mkdirs();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
