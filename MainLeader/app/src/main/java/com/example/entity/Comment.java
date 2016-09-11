package com.example.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by ${hcc} on 2016/05/31.
 */
public class Comment extends BmobObject{
    private String talkTitle;
    private String comment;//评论的内容
    private MyUser user;//评论的用户

    private Post post; //所评论的帖子

    public Comment() {
    }

    public String getTalkTitle() {
        return talkTitle;
    }

    public void setTalkTitle(String talkTitle) {
        this.talkTitle = talkTitle;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
