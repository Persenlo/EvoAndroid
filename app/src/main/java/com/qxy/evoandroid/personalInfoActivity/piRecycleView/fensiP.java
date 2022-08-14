package com.qxy.evoandroid.personalInfoActivity.piRecycleView;

public class fensiP {
    private String nickName;
    private String avatar;
    private String locate;
    private String gender;

    public fensiP(String avatar,String nickName,String locate,String gender){
        this.avatar=avatar;
        this.nickName=nickName;
        this.locate=locate;
        this.gender=gender;
    }
    public fensiP(){}

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

}
