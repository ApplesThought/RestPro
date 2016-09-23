package com.example.mvp.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by ${hcc} on 2016/05/29.
 */
public class Post extends BmobObject{
    private MyUser username;//发布者
    private String talkContent;//内容
    private BmobFile talkImg;//头像
    private String talkTitle;//标题
    private String type;//类型(精美文章大类型)
    private String typeDetail;//标题细节（小类型）

    private boolean isSpecial;//是否关注


    public Post() {

    }

    public String getTypeDetail() {
        return typeDetail;
    }

    public void setTypeDetail(String typeDetail) {
        this.typeDetail = typeDetail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MyUser getUsername() {
        return username;
    }

    public void setUsername(MyUser username) {
        this.username = username;
    }

    public String getTalkContent() {
        return talkContent;
    }

    public void setTalkContent(String talkContent) {
        this.talkContent = talkContent;
    }

    public BmobFile getTalkImg() {
        return talkImg;
    }

    public void setTalkImg(BmobFile talkImg) {
        this.talkImg = talkImg;
    }

    public String getTalkTitle() {
        return talkTitle;
    }

    public void setTalkTitle(String talkTitle) {
        this.talkTitle = talkTitle;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

}
