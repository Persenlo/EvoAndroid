package com.qxy.evoandroid.personalInfoActivity.piRecycleView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.evoandroid.R;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class FensiAdp extends RecyclerView.Adapter<FensiAdp.ViewHolder> {

    private final List<fensiP> list;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView userName;
        TextView gender;
        TextView locate;

        public ViewHolder(View view){
            super(view);
            icon=view.findViewById(R.id.iv_fs_icon);
            userName=view.findViewById(R.id.tv_fs_username);
            gender=view.findViewById(R.id.iv_fs_gender);
            locate=view.findViewById(R.id.tv_fs_locate);
        }
    }

    public FensiAdp(List<fensiP> list){
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_fensi,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FensiAdp.ViewHolder holder, int position) {
        fensiP p=list.get(position);
        holder.locate.setText(p.getLocate());
        holder.gender.setText(p.getGender());
        holder.userName.setText(p.getNickName());
        //holder.icon.setImageURI(Uri.parse(p.getAvatar()));
        try {
            URL aURL = new URL(p.getAvatar());
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            Bitmap bm = BitmapFactory.decodeStream(bis);
            holder.icon.setImageBitmap(bm);
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}