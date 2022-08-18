package com.qxy.evoandroid.personalInfoActivity.piRecycleView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ItemListFansBinding;

import java.util.List;


public class FansAdapter extends RecyclerView.Adapter<FansAdapter.ViewHolder> {

    private final List<FansP> list;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ItemListFansBinding binding;

        public ViewHolder(ItemListFansBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public FansAdapter(List<FansP> list){
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListFansBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_list_fans,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FansAdapter.ViewHolder holder, int position) {
        FansP p=list.get(position);
        holder.binding.setVMFensiItem(p);
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}