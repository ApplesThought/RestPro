package com.example.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.mvp.model.MyUser;
import com.example.mvp.model.Post;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class EditArticleActivity extends KLBaseActivity {

    private EditText articleTitle,articleContent;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_article);

        setToolBarName("编辑文章");

        initView();
    }

    private void initView() {
        articleTitle = (EditText) findViewById(R.id.articleTitle);
        articleContent = (EditText) findViewById(R.id.articleContent);

        type = getIntent().getStringExtra("type");
        initData();
    }


    private void initData() {
        comfirm(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = articleTitle.getText().toString().trim();
                String content = articleContent.getText().toString().trim();
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                    toast("标题或内容为空");
                } else {
                    MyUser user = BmobUser.getCurrentUser(EditArticleActivity.this, MyUser.class);
                    Post post = new Post();
                    post.setTalkContent(content);
                    post.setUsername(user);
                    post.setTalkTitle(title);
                    post.setType("精美文章");
                    post.setTypeDetail(type);
                    post.save(EditArticleActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            toast("发布成功");
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            toast("发布失败"+s);
                        }
                    });
                }
            }
        });
    }
}
