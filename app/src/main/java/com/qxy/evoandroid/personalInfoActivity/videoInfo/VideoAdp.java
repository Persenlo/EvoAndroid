package com.qxy.evoandroid.personalInfoActivity.videoInfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.AdapterListVideoBinding;


import java.util.List;

public class VideoAdp extends RecyclerView.Adapter<VideoAdp.ViewHolder>{

    private final List<VideoItem> list;
    private Context context;
    private View.OnClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder{

        AdapterListVideoBinding binding;

        public ViewHolder(AdapterListVideoBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
    public VideoAdp(Context context, List<VideoItem> list){
        this.context=context;
        this.list=list;
        listener= view -> {
            Intent intent = null;//=new Intent(...)
            context.startActivity(intent);
        };
    }

    @NonNull
    @Override
    public VideoAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterListVideoBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.adapter_list_video,parent,false);
        return new ViewHolder(binding);
    }


    public void onBindViewHolder(@NonNull VideoAdp.ViewHolder holder, int position) {
        VideoItem p=list.get(position);
        holder.binding.getRoot().setOnClickListener(listener);
        holder.binding.setVMVideoItem(p);
        holder.binding.executePendingBindings();
        //holder.test.setText(p.getTest());
        //封面先放放
//        holder.title.setText(p.getTitle());
//
//        if(p.getOn_top()) holder.on_top.setVisibility(View.VISIBLE);
//        else holder.on_top.setVisibility(View.INVISIBLE);
//        holder.play_count.setText(p.getPlay_count());
//        holder.comment_count.setText(p.getComment_count());
//        holder.time.setText(p.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
