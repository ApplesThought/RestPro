package com.example.mvp.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by ${hcc} on 2016/06/20.
 */
public class Lost extends BmobObject{
    private MyUser username;
    private String lostTitle;
    private String lostDescribe;
    private String lostAddress;
    private String lostContacts;
    private String lostPhone;
    private String lostQQ;
    private String lostReward;
    private String isRuning;

    public MyUser getUsername() {
        return username;
    }

    public void setUsername(MyUser username) {
        this.username = username;
    }

    public String getLostTitle() {
        return lostTitle;
    }

    public void setLostTitle(String lostTitle) {
        this.lostTitle = lostTitle;
    }

    public String getLostDescribe() {
        return lostDescribe;
    }

    public void setLostDescribe(String lostDescribe) {
        this.lostDescribe = lostDescribe;
    }

    public String getLostAddress() {
        return lostAddress;
    }

    public void setLostAddress(String lostAddress) {
        this.lostAddress = lostAddress;
    }

    public String getLostContacts() {
        return lostContacts;
    }

    public void setLostContacts(String lostContacts) {
        this.lostContacts = lostContacts;
    }

    public String getLostPhone() {
        return lostPhone;
    }

    public void setLostPhone(String lostPhone) {
        this.lostPhone = lostPhone;
    }

    public String getLostQQ() {
        return lostQQ;
    }

    public void setLostQQ(String lostQQ) {
        this.lostQQ = lostQQ;
    }

    public String getLostReward() {
        return lostReward;
    }

    public void setLostReward(String lostReward) {
        this.lostReward = lostReward;
    }

    public String getIsRuning() {
        return isRuning;
    }

    public void setIsRuning(String isRuning) {
        this.isRuning = isRuning;
    }
}
