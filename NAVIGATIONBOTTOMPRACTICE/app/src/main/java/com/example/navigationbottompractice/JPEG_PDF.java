package com.example.navigationbottompractice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import androidx.exifinterface.media.ExifInterface;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        while (i < images.length) {
            //Creating a blank page
            PDPage blankPage = new PDPage();

            //Adding the blank page to the document
            document.addPage(blankPage);
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
            PDImageXObject pdImage = PDImageXObject.createFromFile(images[i].getPath(), doc);

            //creating the PDPageContentStream object
            PDPageContentStream contents = new PDPageContentStream(doc, page);

            Bitmap myBitmap = BitmapFactory.decodeFile(images[i].getAbsolutePath());
            float x = (float) ((width - myBitmap.getWidth())/2.0);
            float y = (float) ((height - myBitmap.getHeight())/2.0);
            //Drawing the image in the PDF document
            contents.drawImage(pdImage, x, y);

            //Closing the PDPageContentStream object
            contents.close();
            i++;
        }
        doc.save(path);

        //Closing the document
        doc.close();
        Toast.makeText(c.getApplicationContext(), "PDF Created Successfully.", Toast.LENGTH_SHORT).show();
    }
}
