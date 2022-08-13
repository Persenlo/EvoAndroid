package com.qxy.evoandroid.personalInfoActivity.piRecycleView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.evoandroid.R;

import java.util.List;

public class GuanzhuAdp extends RecyclerView.Adapter<GuanzhuAdp.ViewHolder> {

    private final List<guanzhuP> list;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView userName;
        TextView gender;
        TextView locate;

        public ViewHolder(View view){
            super(view);
            icon=view.findViewById(R.id.iv_gz_icon);
            userName=view.findViewById(R.id.tv_gz_username);
            gender=view.findViewById(R.id.iv_gz_gender);
            locate=view.findViewById(R.id.tv_gz_locate);
        }
    }

    public GuanzhuAdp(List<guanzhuP> list){
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_guanzhu,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GuanzhuAdp.ViewHolder holder, int position) {
        guanzhuP p=list.get(position);
        holder.locate.setText(p.getLocate());
        holder.gender.setText(p.getGender());
        holder.userName.setText(p.getNickName());
        holder.icon.setImageURI(Uri.parse(p.getAvatar()));
    }

    @Override
    public int getItemCount() {
            return list.size();
    }
}
