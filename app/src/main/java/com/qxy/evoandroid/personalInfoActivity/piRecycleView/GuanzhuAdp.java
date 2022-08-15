package com.qxy.evoandroid.personalInfoActivity.piRecycleView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.AdapterListGuanzhuBinding;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class GuanzhuAdp extends RecyclerView.Adapter<GuanzhuAdp.ViewHolder> {

    private final List<GuanzhuP> list;

    static class ViewHolder extends RecyclerView.ViewHolder{
        AdapterListGuanzhuBinding binding;

        public ViewHolder(AdapterListGuanzhuBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public GuanzhuAdp(List<GuanzhuP> list){
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterListGuanzhuBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.adapter_list_guanzhu,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GuanzhuAdp.ViewHolder holder, int position) {
        GuanzhuP p=list.get(position);
        holder.binding.setVMGuanzhuItem(p);
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
            return list.size();
    }
}
