package com.qxy.evoandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ItemListBinding;
import com.qxy.evoandroid.model.VideoRank.DataDTO.ListDTO;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyHolder> {
    private List<ListDTO> list=new ArrayList<>();
    private Context context;
    private String Tag="ListAdapterTag";

    public ListAdapter(List<ListDTO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ListDTO data=list.get(position);
        if(holder.binding!=null){
            if(holder.binding.ivList.getTag()==null){
                holder.binding.ivList.setTag(Tag);
                Glide.with(context).load(data.getPoster()).into(holder.binding.ivList);
            }
            holder.binding.listName.setText(data.getName());
            String tags="";
            for(String s:data.getTags()) tags = tags + s + "/";
            holder.binding.listTag.setText(tags);
            String actors="";
            for(String s:data.getActors()) actors = actors + s + "/";
            holder.binding.listActors.setText(actors);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        private final ItemListBinding binding;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
