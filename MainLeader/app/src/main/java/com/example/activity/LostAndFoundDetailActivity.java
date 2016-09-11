package com.example.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.customview.CircleTextImageView;
import com.example.customview.LoadingDialog;
import com.example.entity.Found;
import com.example.entity.Lost;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class LostAndFoundDetailActivity extends KLBaseActivity {

    private int flag;
    private CircleTextImageView img_photo;
    private TextView publisher, publish_date, titleTxt, describeTxt, statusTxt, publishTimeTxt,
            addTxt, addressTxt, contactsTxt, phoneTxt, QQtxt, rewardTxt;
    private String objectId;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found_detail);

        initToolBarTitle();

        initView();
    }

    private void initView() {
        img_photo = (CircleTextImageView) findViewById(R.id.img_photo);
        publisher = (TextView) findViewById(R.id.publisher);
        publish_date = (TextView) findViewById(R.id.publish_date);
        titleTxt = (TextView) findViewById(R.id.title);
        describeTxt = (TextView) findViewById(R.id.describeTxt);
        statusTxt = (TextView) findViewById(R.id.statusTxt);
        publishTimeTxt = (TextView) findViewById(R.id.publishTimeTxt);
        addTxt = (TextView) findViewById(R.id.addTxt);
        addressTxt = (TextView) findViewById(R.id.addressTxt);
        contactsTxt = (TextView) findViewById(R.id.contactsTxt);
        phoneTxt = (TextView) findViewById(R.id.phoneTxt);
        QQtxt = (TextView) findViewById(R.id.QQtxt);
        rewardTxt = (TextView) findViewById(R.id.rewardTxt);

        initData();
    }

    private void initData() {
        dialog = new LoadingDialog(this);
        if (!isFinishing()) {
            dialog.show();
        }
        objectId = getIntent().getStringExtra("objectId");
        if (flag == 0) {
            addTxt.setText("丢物地址");
            initLostData();
        } else {
            addTxt.setText("拾物地址");
            initFoundData();
        }
    }

    /*初始化招领信息*/
    private void initFoundData() {
        BmobQuery<Found> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.include("username");
        query.findObjects(this, new FindListener<Found>() {
            @Override
            public void onSuccess(List<Found> list) {
                dialog.dismiss();
                String photoUrl = "", userName = "", publishTime = "", title = "",
                        describe = "", status = "", address = "",
                        contacts = "", phone = "", QQ = "", reward = "", nick = "";
                for (Found lost : list) {
                    photoUrl = lost.getUsername().getUserPhoto();
                    userName = lost.getUsername().getUsername();
                    nick = lost.getUsername().getNick();
                    publishTime = lost.getUpdatedAt();
                    title = lost.getFoundTitle();
                    describe = lost.getFoundDescribe();
                    status = lost.getIsRuning();
                    address = lost.getFoundAddress();
                    contacts = lost.getFoundContacts();
                    phone = lost.getFoundPhone();
                    QQ = lost.getFoundQQ();
                    reward = lost.getFoundReward();
                }

                /*设置头像*/
                if (TextUtils.isEmpty(photoUrl)) {
                    img_photo.setImageResource(R.drawable.health_guide_men_selected);
                } else {
                    Picasso.with(LostAndFoundDetailActivity.this).load(photoUrl).into(img_photo);
                }

                /*设置用户*/
                if (TextUtils.isEmpty(nick)) {
                    publisher.setText(userName);
                } else {
                    publisher.setText(nick);
                }

                publish_date.setText(publishTime);
                titleTxt.setText(title);
                describeTxt.setText(describe);
                statusTxt.setText(status);
                addressTxt.setText(address);
                publishTimeTxt.setText(publishTime);
                contactsTxt.setText(contacts);
                phoneTxt.setText(phone);
                QQtxt.setText(QQ);
                rewardTxt.setText(reward);
            }

            @Override
            public void onError(int i, String s) {
                dialog.dismiss();
                toast("信息获取失败");
            }
        });
    }

    /*初始化失物信息*/
    private void initLostData() {
        BmobQuery<Lost> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.include("username");
        query.findObjects(this, new FindListener<Lost>() {
            @Override
            public void onSuccess(List<Lost> list) {
                dialog.dismiss();
                String photoUrl = "", userName = "", publishTime = "", title = "",
                        describe = "", status = "", address = "",
                        contacts = "", phone = "", QQ = "", reward = "", nick = "";
                for (Lost lost : list) {
                    photoUrl = lost.getUsername().getUserPhoto();
                    userName = lost.getUsername().getUsername();
                    nick = lost.getUsername().getNick();
                    publishTime = lost.getUpdatedAt();
                    title = lost.getLostTitle();
                    describe = lost.getLostDescribe();
                    status = lost.getIsRuning();
                    address = lost.getLostAddress();
                    contacts = lost.getLostContacts();
                    phone = lost.getLostPhone();
                    QQ = lost.getLostQQ();
                    reward = lost.getLostReward();
                }

                /*设置头像*/
                if (TextUtils.isEmpty(photoUrl)) {
                    img_photo.setImageResource(R.drawable.health_guide_men_selected);
                } else {
                    Picasso.with(LostAndFoundDetailActivity.this).load(photoUrl).into(img_photo);
                }

                /*设置用户*/
                if (TextUtils.isEmpty(nick)) {
                    publisher.setText(userName);
                } else {
                    publisher.setText(nick);
                }

                publish_date.setText(publishTime);
                titleTxt.setText(title);
                describeTxt.setText(describe);
                statusTxt.setText(status);
                addressTxt.setText(address);
                contactsTxt.setText(contacts);
                publishTimeTxt.setText(publishTime);
                phoneTxt.setText(phone);
                QQtxt.setText(QQ);
                rewardTxt.setText(reward);
            }

            @Override
            public void onError(int i, String s) {
                dialog.dismiss();
                toast("信息获取失败");
            }
        });
    }

    private void initToolBarTitle() {
        flag = getIntent().getIntExtra("FLAG", 0);
        if (flag == 0) {
            setToolBarName("失物详情");
        } else if (flag == 1) {
            setToolBarName("招领详情");
        }
    }
}
