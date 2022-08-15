package com.qxy.evoandroid.personalInfoActivity.videoInfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.evoandroid.R;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class videoAdp extends RecyclerView.Adapter<videoAdp.ViewHolder>{

    private final List<videoItem> list;
    private final Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView title;
        ImageView on_top;
        TextView play_count;
        TextView comment_count;
        TextView time;
        ConstraintLayout part_container;

        public ViewHolder(View view){
            super(view);
            //test=view.findViewById(R.id.tv_video_test);
            cover=view.findViewById(R.id.iv_video_cover);
            title=view.findViewById(R.id.tv_video_title);
            on_top=view.findViewById(R.id.iv_video_onTop);
            play_count=view.findViewById(R.id.tv_video_play);
            comment_count=view.findViewById(R.id.tv_video_comment);
            time=view.findViewById(R.id.tv_video_time);
            part_container=view.findViewById(R.id.layout_video_part_container);
        }
    }

    public videoAdp(Context context,List<videoItem> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public videoAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ViewHolder holder=new videoAdp.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_video,parent,false));
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;//=new Intent(...)
                context.startActivity(intent);
            }
        };
        holder.cover.setOnClickListener(listener);
        holder.title.setOnClickListener(listener);
        holder.part_container.setOnClickListener(listener);
        return holder;
    }


    public void onBindViewHolder(@NonNull videoAdp.ViewHolder holder, int position) {
        videoItem p=list.get(position);
        //holder.test.setText(p.getTest());
        //封面先放放
        holder.title.setText(p.getTitle());

        if(p.getOn_top()) holder.on_top.setVisibility(View.VISIBLE);
        else holder.on_top.setVisibility(View.INVISIBLE);

        holder.play_count.setText(p.getPlay_count());
        holder.comment_count.setText(p.getComment_count());
        holder.time.setText(p.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
