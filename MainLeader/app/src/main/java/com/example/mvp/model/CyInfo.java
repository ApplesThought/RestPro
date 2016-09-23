package com.example.mvp.model;

import java.util.List;

/**
 * Created by ${hcc} on 2016/09/10.
 */
public class CyInfo {

    private String pinyin;
    private String chengyujs;
    private String from_;
    private String example;
    private String yufa;
    private String ciyujs;
    private String yinzhengjs;
    private List<String> tyList;
    private List<String> fyList;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getChengyujs() {
        return chengyujs;
    }

    public void setChengyujs(String chengyujs) {
        this.chengyujs = chengyujs;
    }

    public String getFrom_() {
        return from_;
    }

    public void setFrom_(String from_) {
        this.from_ = from_;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getYufa() {
        return yufa;
    }

    public void setYufa(String yufa) {
        this.yufa = yufa;
    }

    public String getCiyujs() {
        return ciyujs;
    }

    public void setCiyujs(String ciyujs) {
        this.ciyujs = ciyujs;
    }

    public String getYinzhengjs() {
        return yinzhengjs;
    }

    public void setYinzhengjs(String yinzhengjs) {
        this.yinzhengjs = yinzhengjs;
    }

    public List<String> getTyList() {
        return tyList;
    }

    public void setTyList(List<String> tyList) {
        this.tyList = tyList;
    }

    public List<String> getFyList() {
        return fyList;
    }

    public void setFyList(List<String> fyList) {
        this.fyList = fyList;
    }
}
