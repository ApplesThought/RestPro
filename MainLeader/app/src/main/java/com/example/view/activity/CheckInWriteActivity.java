package com.example.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mvp.model.DailyMood;
import com.example.mvp.model.MyUser;
import com.example.utils.IntentUtils;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class CheckInWriteActivity extends KLBaseActivity {

    private EditText contentEdt;
    private ImageView img_checkIn;

    private String photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_write);

        setToolBarName("签到");

        initView();
    }

    private void initView() {
        contentEdt = (EditText) findViewById(R.id.contentEdt);
        img_checkIn = (ImageView) findViewById(R.id.img_checkIn);
        photoUrl = getIntent().getStringExtra("photoUrl");
        Picasso.with(this).load(photoUrl).into(img_checkIn);

        comfirmCheckIn();
    }

    private void comfirmCheckIn() {
        comfirm(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEdt.getText().toString().trim();
                MyUser user = BmobUser.getCurrentUser(CheckInWriteActivity.this, MyUser.class);
                DailyMood mood = new DailyMood();
                mood.setContent(content);
                mood.setPhotoUrl(photoUrl);
                mood.setUsername(user);
                mood.save(CheckInWriteActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        toast("签到成功");
                        finish();
                        IntentUtils.toGoalAcrivity(CheckInWriteActivity.this, FriendStatusActivity.class);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toast("签到失败");
                    }
                });
            }
        });
    }
}
