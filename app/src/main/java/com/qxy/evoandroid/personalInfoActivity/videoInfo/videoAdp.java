package com.qxy.evoandroid.personalInfoActivity.videoInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.evoandroid.R;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class videoAdp extends RecyclerView.Adapter<videoAdp.ViewHolder>{

    private final List<videoItem> list;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView userName;
        TextView gender;
        TextView locate;

        public ViewHolder(View view){
            super(view);
            //test=view.findViewById(R.id.tv_video_test);
        }
    }

    public videoAdp(List<videoItem> list){
        this.list=list;
    }

    @NonNull
    @Override
    public videoAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new videoAdp.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_video,parent,false));
    }


    public void onBindViewHolder(@NonNull videoAdp.ViewHolder holder, int position) {
        videoItem p=list.get(position);
        //holder.test.setText(p.getTest());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
