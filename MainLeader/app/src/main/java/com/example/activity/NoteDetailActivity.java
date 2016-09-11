package com.example.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.customview.LoadingDialog;
import com.example.entity.ReadNote;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class NoteDetailActivity extends KLBaseActivity {

    private TextView noteTitle, noteDate, noteContent, noteType;

    private LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        setToolBarName("笔记正文");

        initView();
    }

    private void initView() {
        noteTitle = (TextView) findViewById(R.id.noteTitle);
        noteDate = (TextView) findViewById(R.id.noteDate);
        noteContent = (TextView) findViewById(R.id.noteContent);
        noteType = (TextView) findViewById(R.id.noteType);

        dialog = new LoadingDialog(this);
        initData();
    }

    private void initData() {
        dialog.show();
        String id = getIntent().getStringExtra("objectId");
        BmobQuery<ReadNote> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", id);
        query.findObjects(this, new FindListener<ReadNote>() {
            @Override
            public void onSuccess(List<ReadNote> list) {
                String title = "", date = "", type = "", content = "";
                for (ReadNote note : list) {
                    title = note.getNoteTitle();
                    date = note.getCreatedAt();
                    type = note.getNoteType();
                    content = note.getNoteContent();
                }

                noteTitle.setText(title);
                noteDate.setText(date);
                noteType.setText(type);
                noteContent.setText(content);
                dialog.dismiss();
            }

            @Override
            public void onError(int i, String s) {
                toast("获取内容失败");
                dialog.dismiss();
            }
        });
    }
}
