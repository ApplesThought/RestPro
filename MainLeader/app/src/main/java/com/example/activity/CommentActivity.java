package com.example.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.entity.Comment;
import com.example.entity.DailyMood;
import com.example.entity.DailyMoodComment;
import com.example.entity.MyUser;
import com.example.entity.Post;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class CommentActivity extends KLBaseActivity {

    private EditText commentEdt;
    private Button commentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        setToolBarName("我的牛评");

        initView();
    }

    /*关联评论表*/
    private void attachComment() {
        String type = getIntent().getStringExtra("type");
        final MyUser user = BmobUser.getCurrentUser(this, MyUser.class);

        if (type.equals("非即兴说说")) {
            final Post post = new Post();
            String title = getIntent().getStringExtra("title");
            String objectId = getIntent().getStringExtra("objectId");
            Log.i("Comment", objectId);
            post.setObjectId(objectId);
            Comment comment = new Comment();
            String content = commentEdt.getText().toString().trim();
            comment.setComment(content);
            comment.setPost(post);
            comment.setUser(user);
            if (TextUtils.isEmpty(content)) {
                toast("先随便写点什么吧...");
            } else {
                comment.save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        toast("评论成功");
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toast("评论失败"+s);
                    }
                });
            }
        } else {
            final DailyMood mood = new DailyMood();
            String objectId = getIntent().getStringExtra("objectId");
            Log.i("Comment", objectId);
            mood.setObjectId(objectId);
            DailyMoodComment comment = new DailyMoodComment();
            String content = commentEdt.getText().toString().trim();
            comment.setComment(content);
            comment.setMood(mood);
            comment.setUser(user);
            if (TextUtils.isEmpty(content)) {
                toast("先随便写点什么吧...");
            } else {
                comment.save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        toast("评论成功");
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toast("评论失败"+s);
                    }
                });
            }
        }


    }

    private void initView() {
        commentEdt = (EditText) findViewById(R.id.commentEdt);
        commentBtn = (Button) findViewById(R.id.commentBtn);

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachComment();
            }
        });
    }
}
