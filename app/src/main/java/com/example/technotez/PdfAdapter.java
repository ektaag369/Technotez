package com.example.technotez;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {

    Context context;
    List<FileInfoModel> pdflist;

    public PdfAdapter(Context context, List<FileInfoModel> pdflist) {
        this.context = context;
        this.pdflist = pdflist;
    }
    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_item_view,parent,false);
        return new PdfViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        FileInfoModel pos=pdflist.get(position);
        holder.title.setText(pos.getTitle());
        holder.pdfimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.pdfimg.getContext(), Viewpdf.class);
                i.putExtra("filename",pos.getTitle());
                i.putExtra("fileurl",pos.getPdfUrl());

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.pdfimg.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdflist.size();
    }

    public static class PdfViewHolder extends RecyclerView.ViewHolder{
        ImageView pdfimg;
        TextView title;
        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfimg=itemView.findViewById(R.id.viewpdf);
            title=itemView.findViewById(R.id.pdftitle);
        }
    }
}
