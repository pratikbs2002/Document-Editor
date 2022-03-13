package com.example.navigationbottompractice.fragment.Files;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                try{
                    String type = "image/*";
                    intent.setDataAndType(Uri.parse(selectedFile.getAbsolutePath()), type);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                catch (Exception e) {
                    e.printStackTrace();
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