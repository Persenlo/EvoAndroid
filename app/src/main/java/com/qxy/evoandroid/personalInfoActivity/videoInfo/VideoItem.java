package com.qxy.evoandroid.personalInfoActivity.videoInfo;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class VideoItem extends BaseObservable {

    private String title;
    private Boolean on_top=false;//暂缓
    private String play_count;
    private String comment_count;
    private String time;
    private String cover;
    private String itemId;

    @Bindable
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public Boolean getOn_top() {
        return on_top;
    }

    public void setOn_top(Boolean on_top) {
        this.on_top = on_top;
    }

    @Bindable
    public String getPlay_count() {
        return play_count;
    }

    public void setPlay_count(String play_count) {
        this.play_count = play_count;
    }

    @Bindable
    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Bindable
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public VideoItem(){}
}
