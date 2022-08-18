package com.qxy.evoandroid.personalInfoActivity.piRecycleView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class FollowP extends BaseObservable {
    private String nickName;
    private String avatar;
    private String locate;
    private String gender;

    public FollowP(){}

    @Bindable
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        notifyPropertyChanged(BR.nickName);
    }

    @Bindable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }

    @Bindable
    public String getGender() {
        return gender;
    }

    public void setGender(int gender) {
        if(gender==1) this.gender="♂";
        else if(gender==2) this.gender="♀";
        else this.gender="未知";
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
        notifyPropertyChanged(BR.locate);
    }

}
