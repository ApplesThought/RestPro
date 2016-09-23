package com.example.mvp.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by ${hcc} on 2016/06/20.
 */
public class Found extends BmobObject{
    private MyUser username;
    private String foundTitle;
    private String foundDescribe;
    private String foundAddress;
    private String foundContacts;
    private String foundPhone;
    private String foundQQ;
    private String foundReward;
    private String isRuning;

    public MyUser getUsername() {
        return username;
    }

    public void setUsername(MyUser username) {
        this.username = username;
    }

    public String getFoundTitle() {
        return foundTitle;
    }

    public void setFoundTitle(String foundTitle) {
        this.foundTitle = foundTitle;
    }

    public String getFoundDescribe() {
        return foundDescribe;
    }

    public void setFoundDescribe(String foundDescribe) {
        this.foundDescribe = foundDescribe;
    }

    public String getFoundAddress() {
        return foundAddress;
    }

    public void setFoundAddress(String foundAddress) {
        this.foundAddress = foundAddress;
    }

    public String getFoundContacts() {
        return foundContacts;
    }

    public void setFoundContacts(String foundContacts) {
        this.foundContacts = foundContacts;
    }

    public String getFoundPhone() {
        return foundPhone;
    }

    public void setFoundPhone(String foundPhone) {
        this.foundPhone = foundPhone;
    }

    public String getFoundQQ() {
        return foundQQ;
    }

    public void setFoundQQ(String foundQQ) {
        this.foundQQ = foundQQ;
    }

    public String getFoundReward() {
        return foundReward;
    }

    public void setFoundReward(String foundReward) {
        this.foundReward = foundReward;
    }

    public String getIsRuning() {
        return isRuning;
    }

    public void setIsRuning(String isRuning) {
        this.isRuning = isRuning;
    }
}
