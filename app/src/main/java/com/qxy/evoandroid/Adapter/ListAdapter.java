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

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyHolder> {
    private final List<ListDTO> list;
    private final Context context;

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
                String tag = "ListAdapterTag";
                holder.binding.ivList.setTag(tag);
                Glide.with(context).load(data.getPoster()).into(holder.binding.ivList);
            }
            holder.binding.listName.setText(data.getName());
            StringBuilder tags= new StringBuilder();
            for(String s:data.getTags()) tags.append(s).append("/");
            holder.binding.listTag.setText(tags.toString());
            StringBuilder actors= new StringBuilder();
            for(String s:data.getActors()) actors.append(s).append("/");
            holder.binding.listActors.setText(actors.toString());
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
