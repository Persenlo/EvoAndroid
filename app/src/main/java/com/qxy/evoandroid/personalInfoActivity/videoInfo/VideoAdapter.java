package com.qxy.evoandroid.personalInfoActivity.videoInfo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ItemListVideoBinding;
import com.qxy.evoandroid.utils.SingleClickListener;
import com.qxy.evoandroid.videoPlayer.VideoPlayerActivity;


import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{

    private final List<VideoItem> list;
    private Context context;
    private View.OnClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ItemListVideoBinding binding;

        public ViewHolder(ItemListVideoBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public VideoAdapter(Context context, List<VideoItem> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListVideoBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_list_video,parent,false);
        return new ViewHolder(binding);
    }


    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, int position) {
        VideoItem p=list.get(position);
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

        //设置点击监听，跳转到播放器
        holder.binding.getRoot().setOnClickListener(new SingleClickListener() {
            @Override
            protected void onSignalClick(View v) {
                Intent intent = new Intent(context,VideoPlayerActivity.class);
                intent.putExtra("video_id",p.getItemId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
