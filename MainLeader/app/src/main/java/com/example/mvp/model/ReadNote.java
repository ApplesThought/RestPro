package com.example.mvp.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by ${hcc} on 2016/06/12.
 */
public class ReadNote extends BmobObject{
    private MyUser username;//写读书笔记的人
    private String noteTitle;//标题
    private String noteContent;//内容
    private String noteType;//类型

    public ReadNote() {
    }

    public MyUser getUsername() {
        return username;
    }

    public void setUsername(MyUser username) {
        this.username = username;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }
}
