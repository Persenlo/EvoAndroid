package com.qxy.evoandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.model.VideoRank.DataDTO;
import com.qxy.evoandroid.databinding.ItemListBinding;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyHolder> {
    private final DataDTO dataDTO;
    private final Context context;
    private final int type;

    public ListAdapter(DataDTO dataDTO, Context context,int type) {
        this.dataDTO = dataDTO;
        this.context = context;
        this.type=type;
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

            //设置排名
            StringBuilder ranks=new StringBuilder();
            if(position==0||position==1||position==2) {
                ranks.append("Top");
            }
            ranks.append(position+1);
            holder.binding.tvRank.setText(ranks.toString());
            holder.binding.tvRank.bringToFront();

            //设置海报
            String tag = "ListAdapterTag";
            holder.binding.ivList.setTag(tag);
            if(holder.binding.ivList.getTag()!=null) {
                Glide.with(context)
                    .load(data.getPoster())
                    .apply(new RequestOptions().transform(new RoundedCorners(20)))
                    .placeholder(R.drawable.loading)
                    .into(holder.binding.ivList);
            }

            //设置名称
            StringBuilder name=new StringBuilder();
            name.append(data.getName());
            if(!data.getNameEn().equals("")) name.append("(").append(data.getNameEn()).append(")");
            holder.binding.listName.setText(name.toString());

            //设置发行时间+地域
            StringBuilder area=new StringBuilder();
            area.append(data.getReleaseDate());
            if(data.getAreas()!=null){
                for(String s:data.getAreas()){
                    area.append("/").append(s);
                }
            }
            if(area.toString().equals("")) holder.binding.listArea.setVisibility(View.GONE);
            else holder.binding.listArea.setText(area.toString());

            //设置Tags
            if(data.getTags()!=null) {
                StringBuilder tags = new StringBuilder();
                for (int i = 0; i < data.getTags().size(); i++) {
                    tags.append(data.getTags().get(i));
                    if (i != data.getTags().size() - 1) tags.append("/");
                }
                holder.binding.listTag.setText(tags.toString());
            }else holder.binding.listTag.setVisibility(View.GONE);

            //设置Actors
            if(data.getActors()!=null) {
                StringBuilder actors = new StringBuilder();
                for (int i = 0; i < data.getActors().size(); i++) {
                    actors.append(data.getActors().get(i));
                    if (i != data.getActors().size() - 1) actors.append("/");
                }
                holder.binding.listActors.setText(actors.toString());
            }

            //综艺榜单设置导演信息
            else if(data.getDirectors()!=null){
                StringBuilder directors = new StringBuilder();
                directors.append("导演:");
                for (int i = 0; i < data.getDirectors().size(); i++) {
                    directors.append(data.getDirectors().get(i));
                    if (i != data.getDirectors().size() - 1) directors.append("/");
                }
                holder.binding.listActors.setText(directors.toString());
            }

            //热度点击事件
            holder.binding.listHot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder hot_dialog=new AlertDialog.Builder(context);
                    hot_dialog.setTitle("总热度:"+data.getHot());
                    //dialog内添加LinearLayout（包含四个数据TextView）
                    LinearLayout layout=new LinearLayout(context);
                    //讨论热度
                    TextView discussion_hot=new TextView(context);
                    discussion_hot.setText("讨论热度："+data.getDiscussionHot());
                    //主题热度
                    TextView topic_hot=new TextView(context);
                    topic_hot.setText("主题热度："+data.getTopicHot());
                    //搜索热度
                    TextView search_hot=new TextView(context);
                    search_hot.setText("搜索热度："+data.getSearchHot());
                    //影响力热度
                    TextView influence_hot=new TextView(context);
                    influence_hot.setText("影响力热度："+data.getInfluenceHot());
                    //设置LinearLayout
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setPadding(10,0,0,0);
                    layout.addView(discussion_hot);
                    layout.addView(topic_hot);
                    layout.addView(search_hot);
                    layout.addView(influence_hot);
                    //dialog显示
                    hot_dialog.setView(layout);
                    hot_dialog.show();
                }
            });
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
