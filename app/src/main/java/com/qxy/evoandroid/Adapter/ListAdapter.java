package com.qxy.evoandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ItemListBinding;
import com.qxy.evoandroid.model.VideoRank.DataDTO;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyHolder> {
    private final DataDTO dataDTO;
    private final Context context;

    public ListAdapter(DataDTO dataDTO, Context context) {
        this.dataDTO = dataDTO;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DataDTO.ListDTO data=dataDTO.getList().get(position);
        if(holder.binding!=null){
            if(position==0||position==1||position==2) {
                holder.binding.tvRank.setText("Top"+ (position + 1));
            }else holder.binding.tvRank.setText(position+1);
            //设置海报
            String tag = "ListAdapterTag";
            holder.binding.ivList.setTag(tag);
            if(holder.binding.ivList.getTag()!=null){
                Glide.with(context)
                        .load(data.getPoster())
                        .apply(new RequestOptions().transform(new RoundedCorners(20)))
                        .placeholder(R.drawable.loading)
                        .into(holder.binding.ivList);
            }
            //设置名称
            holder.binding.listName.setText(data.getName());
            //设置Tags
            if(data.getTags()!=null) {
                StringBuilder tags = new StringBuilder();
                for (int i = 0; i < data.getTags().size(); i++) {
                    tags.append(data.getTags().get(i));
                    if (i != data.getTags().size() - 1) tags.append("/");
                }
                holder.binding.listTag.setText(tags.toString());
            }
            //设置Actors
            if(data.getActors()!=null) {
                StringBuilder actors = new StringBuilder();
                for (int i = 0; i < data.getActors().size(); i++) {
                    actors.append(data.getActors().get(i));
                    if (i != data.getActors().size() - 1) actors.append("/");
                }
                holder.binding.listActors.setText(actors.toString());
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataDTO.getList().size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        private final ItemListBinding binding;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
