package com.example.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by ${hcc} on 2016/06/19.
 */
public class CheckInImg extends BmobObject{
    private String urlStr;
    private BmobFile photoFile;
    private String photoTitle;

    public CheckInImg() {
    }

    public String getUrlStr() {
        return urlStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    public BmobFile getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(BmobFile photoFile) {
        this.photoFile = photoFile;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public void setPhotoTitle(String photoTitle) {
        this.photoTitle = photoTitle;
    }
}
