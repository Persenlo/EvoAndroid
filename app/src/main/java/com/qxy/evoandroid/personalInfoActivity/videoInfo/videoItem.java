package com.qxy.evoandroid.personalInfoActivity.videoInfo;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class videoItem {

    private String title;
    private Boolean on_top;
    private String play_count;
    private String comment_count;
    private String time;
    private Uri cover;

    public Uri getCover() {
        return cover;
    }

    public void setCover(Uri cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getOn_top() {
        return on_top;
    }

    public void setOn_top(Boolean on_top) {
        this.on_top = on_top;
    }

    public String getPlay_count() {
        return play_count;
    }

    public void setPlay_count(String play_count) {
        this.play_count = play_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public videoItem(){
    }


}
