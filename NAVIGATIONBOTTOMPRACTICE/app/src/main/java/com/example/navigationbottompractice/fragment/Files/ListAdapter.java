package com.example.navigationbottompractice.fragment.Files;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbottompractice.*;

import java.io.File;

public class ListAdapter extends  RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    File[] fileList;
    public ListAdapter(Context c, File[] fileList){
        this.context = c;
        this.fileList = fileList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.filesitems, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File selectedFile = fileList[position];
        holder.tvName.setText(selectedFile.getName());

        if(selectedFile.isDirectory()){
            holder.ivIcon.setImageResource(R.drawable.ic_baseline_folder_24);
        }
        else{
            holder.ivIcon.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
        }

        holder.itemView.setOnClickListener(v -> {
            if(selectedFile.isDirectory()){
                Intent intent = new Intent(context, folderList.class);
                String path = selectedFile.getAbsolutePath();
                intent.putExtra("path", path);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
            else{

//                try{
//                    String type = "image/*";
//                    intent.setDataAndType(Uri.parse(selectedFile.getAbsolutePath()), type);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }


                try {

                    Uri uri = Uri.parse(selectedFile.getAbsolutePath());
//                    Uri uri = Uri.fromFile(selectedFile);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);

                    if (selectedFile.getName().endsWith(".doc") || selectedFile.getName().endsWith(".docx")) {
                        // Word document
                        intent.setDataAndType(uri, "application/msword");
                    } else if (selectedFile.getName().endsWith(".pdf")) {
                        // PDF file
                        intent.setDataAndType(uri, "application/pdf");
                    } else if (selectedFile.getName().endsWith(".ppt") || selectedFile.getName().endsWith(".pptx")) {
                        // Powerpoint file
                        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                    } else if (selectedFile.getName().endsWith(".xls") || selectedFile.getName().endsWith(".xlsx")) {
                        // Excel file
                        intent.setDataAndType(uri, "application/vnd.ms-excel");
                    } else if (selectedFile.getName().endsWith(".jpg") || selectedFile.getName().endsWith(".jpeg") || selectedFile.getName().endsWith(".png")) {
                        // JPG file
                        intent.setDataAndType(uri, "image/jpeg");
                    } else if (selectedFile.getName().endsWith(".txt")) {
                        // Text file
                        intent.setDataAndType(uri, "text/plain");
                    } else {
                        intent.setDataAndType(uri, "*/*");
                    }

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No application found which can open the file", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return fileList.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        ImageView ivIcon;
        public ViewHolder(View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvFileName);
            ivIcon = itemView.findViewById(R.id.icon_view);
        }
    }
}