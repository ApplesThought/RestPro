package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customview.CircleTextImageView;
import com.example.customview.LoadingDialog;
import com.example.entity.MyUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class PeopleInfoActivity extends KLBaseActivity {

    private Intent intent;
    private String objectId;//说说id

    private CircleTextImageView img_circle_photo;
    private TextView txt_name,txt_account,txt_signal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_info);

        setToolBarName("个人资料");

        initView();
    }

    private void initView() {
        intent = getIntent();
        objectId = intent.getStringExtra("objectId");

        img_circle_photo = (CircleTextImageView) findViewById(R.id.img_circle_photo);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_account = (TextView) findViewById(R.id.txt_account);
        txt_signal = (TextView) findViewById(R.id.txt_signal);

        getAccInfo();
    }

    private void getAccInfo() {
        final LoadingDialog dialog = new LoadingDialog(this);
        if (!isFinishing()) {
            dialog.show();
        }
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(this, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                String remark = "",acc = "",signal = "",photoUrl = "";
                for (MyUser u:list) {
                    remark = u.getNick();
                    acc = u.getUsername();
                    signal = u.getSignal();
                    photoUrl = u.getUserPhoto();
                }
                if (TextUtils.isEmpty(remark)) {
                    txt_name.setText(acc);
                } else {
                    txt_name.setText(remark);
                }

                if (TextUtils.isEmpty(photoUrl)) {
                    img_circle_photo.setImageResource(R.drawable.health_guide_men_selected);
                } else {
                    Picasso.with(PeopleInfoActivity.this).load(photoUrl).into(img_circle_photo);
                }

                txt_account.setText(acc);
                txt_signal.setText(signal);
                dialog.dismiss();
            }

            @Override
            public void onError(int i, String s) {
                dialog.dismiss();
                toast("获取信息失败");
            }
        });
    }
}
