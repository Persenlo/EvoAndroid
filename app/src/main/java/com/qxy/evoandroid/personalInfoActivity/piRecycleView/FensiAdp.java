package com.qxy.evoandroid.personalInfoActivity.piRecycleView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.AdapterListFensiBinding;

import java.util.List;


public class FensiAdp extends RecyclerView.Adapter<FensiAdp.ViewHolder> {

    private final List<FensiP> list;

    static class ViewHolder extends RecyclerView.ViewHolder{

        AdapterListFensiBinding binding;

        public ViewHolder(AdapterListFensiBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public FensiAdp(List<FensiP> list){
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterListFensiBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.adapter_list_fensi,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FensiAdp.ViewHolder holder, int position) {
        FensiP p=list.get(position);
        holder.binding.setVMFensiItem(p);
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}