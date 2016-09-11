package com.example.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by ${hcc} on 2016/06/16.
 */
public class DailyMood extends BmobObject {
    private MyUser username;//发布者
    private String content;//内容
    private Integer lookTimes;

    private String photoUrl;//说说图片地址

    public DailyMood() {
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getLookTimes() {
        return lookTimes;
    }

    public void setLookTimes(Integer lookTimes) {
        this.lookTimes = lookTimes;
    }

    public MyUser getUsername() {
        return username;
    }

    public void setUsername(MyUser username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
