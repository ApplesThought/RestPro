package com.example.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by ${hcc} on 2016/06/16.
 */
public class DailyMoodComment extends BmobObject {
    private String comment;//评论的内容
    private MyUser user;//评论的用户

    private DailyMood mood;//评论对应的帖子

    public DailyMoodComment() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public DailyMood getMood() {
        return mood;
    }

    public void setMood(DailyMood mood) {
        this.mood = mood;
    }
}
