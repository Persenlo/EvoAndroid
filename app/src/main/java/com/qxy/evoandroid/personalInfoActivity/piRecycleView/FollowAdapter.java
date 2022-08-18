package com.qxy.evoandroid.personalInfoActivity.piRecycleView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ItemListFollowBinding;

import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {

    private final List<FollowP> list;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ItemListFollowBinding binding;

        public ViewHolder(ItemListFollowBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public FollowAdapter(List<FollowP> list){
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListFollowBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_list_follow,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowAdapter.ViewHolder holder, int position) {
        FollowP p=list.get(position);
        holder.binding.setVMGuanzhuItem(p);
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
            return list.size();
    }
}
