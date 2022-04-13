package com.example.navigationbottompractice;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class JPEG_PDF {
    String ms = "_".concat(String.valueOf(Calendar.getInstance().getTimeInMillis()));
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
    String name = ("PDF_".concat(sdf.format(new Date()).concat(ms))).concat(".pdf");
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Document Editor/PDF Files/" + name;
    String source = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Document Editor/Scanned Images/";
    File imagesFolder = new File(source);
    File[] images = imagesFolder.listFiles();
    float width = 8.5f * 72;
    float height = 11f * 72;

    public void createPdf(Context c) throws IOException {

        PDFBoxResourceLoader.init(c);

        int i = 0;
        PDDocument document = new PDDocument();
        Toast.makeText(c, String.valueOf(images.length), Toast.LENGTH_SHORT).show();
        while(i < images.length){
            Toast.makeText(c, String.valueOf(i), Toast.LENGTH_SHORT).show();
            //Creating a blank page
            PDPage blankPage = new PDPage();

            //Adding the blank page to the document
            document.addPage( blankPage );
            i++;
        }
        //Saving the document
        document.save(path);

        //Closing the document
        document.close();
        i = 0;
        //Loading an existing document
        File file = new File(path);
        PDDocument doc = PDDocument.load(file);
        while (i < images.length) {

            //Retrieving the page
            PDPage page = doc.getPage(i);

            //Creating PDImageXObject object
            PDImageXObject pdImage = PDImageXObject.createFromFile(images[i].getPath(),doc);

            //creating the PDPageContentStream object
            PDPageContentStream contents = new PDPageContentStream(doc, page);

            //Drawing the image in the PDF document
            contents.drawImage(pdImage, 0, 0);

            //Closing the PDPageContentStream object
            contents.close();
            i++;
        }
        doc.save(path);

        //Closing the document
        doc.close();
    }
}
