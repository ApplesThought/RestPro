package com.example.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.adapter.NoteTypeLvAdapter;
import com.example.customview.CircleTextImageView;
import com.example.entity.MyUser;
import com.example.entity.ReadNote;
import com.example.utils.SplitStrUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class WriteNoteActivity extends KLBaseActivity {

    private EditText edt_title,edt_content;
    private Button btn_save;

    private CircleTextImageView imgType;

    private String type = "";//记录选择的类型
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        setToolBarName("笔记");

        initView();
    }

    private void initView() {
        edt_title = (EditText) findViewById(R.id.edt_title);
        edt_content = (EditText) findViewById(R.id.edt_content);
        btn_save = (Button) findViewById(R.id.btn_save);

        imgType = (CircleTextImageView) findViewById(R.id.imgType);
        imgType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edt_title.getText().toString().trim();
                String content = edt_content.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    toast("标题不能为空");
                } else {
                    if (TextUtils.isEmpty(content)) {
                        toast("写点东西吧...");
                    } else {
                        if (TextUtils.isEmpty(type)) {
                            toast("请选择类别");
                        } else {
                            MyUser user = BmobUser.getCurrentUser(WriteNoteActivity.this, MyUser.class);
                            ReadNote note = new ReadNote();
                            note.setUsername(user);
                            note.setNoteTitle(title);
                            note.setNoteContent(content);
                            note.setNoteType(type);
                            note.save(WriteNoteActivity.this, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    toast("保存成功");
                                    finish();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    toast("保存失败");
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    private void createDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.lv_notetype,null);
        ListView lv = (ListView) view.findViewById(R.id.lv_type);

        final List list = new ArrayList();
        list.add("移动开发");
        list.add("前端开发");
        list.add("后端开发");
        list.add("数据处理");
        list.add("图像处理");
        list.add("其他");

        NoteTypeLvAdapter adapter = new NoteTypeLvAdapter(this,list);
        lv.setAdapter(adapter);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择类别").setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type = list.get(position).toString();
                /*截取类型的第一个字显示在图片上*/
                String typeStr = SplitStrUtils.splitStringType(type);
                imgType.setText(typeStr);
                dialog.dismiss();
            }
        });
    }
}
