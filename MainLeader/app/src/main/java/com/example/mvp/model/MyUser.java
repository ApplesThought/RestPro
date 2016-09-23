package com.example.mvp.model;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by ${hcc} on 2016/05/28.
 */
public class MyUser extends BmobUser{
    private BmobRelation special;//关注

    private boolean isSpecial;//标识是否已经关注

    private String nick;//备注
    private String signal;//签名

    private String QQ;//QQ

    private String gender;//性别
    private String address;//地址
    private String birth;//生日
    private String userMail;//邮箱
    private String userPhoto;//用户头像地址

    private BmobFile userImg;

    public BmobFile getUserImg() {
        return userImg;
    }

    public void setUserImg(BmobFile userImg) {
        this.userImg = userImg;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String remark) {
        this.nick = remark;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public BmobRelation getSpecial() {
        return special;
    }

    public void setSpecial(BmobRelation special) {
        this.special = special;
    }
}
