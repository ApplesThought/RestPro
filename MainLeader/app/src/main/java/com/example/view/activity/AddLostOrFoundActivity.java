package com.example.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.mvp.model.Found;
import com.example.mvp.model.Lost;
import com.example.mvp.model.MyUser;
import com.example.view.customview.CustomDialog;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class AddLostOrFoundActivity extends KLBaseActivity {

    private int flag;
    private EditText titleEdt, addressEdt, contactsEdt, phoneEdt, QQEdt, thankEdt, describeEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lost_or_found);

        initToolBarTitle();

        initView();
    }


    private void initToolBarTitle() {
        flag = getIntent().getIntExtra("FLAG", 0);
        if (flag == 0) {
            setToolBarName("添加失物信息");
        } else if (flag == 1) {
            setToolBarName("添加招领信息");
        }
    }

    private void initView() {
        titleEdt = (EditText) findViewById(R.id.titleEdt);
        addressEdt = (EditText) findViewById(R.id.addressEdt);
        contactsEdt = (EditText) findViewById(R.id.contactsEdt);
        phoneEdt = (EditText) findViewById(R.id.phoneEdt);
        QQEdt = (EditText) findViewById(R.id.QQEdt);
        thankEdt = (EditText) findViewById(R.id.thankEdt);
        describeEdt = (EditText) findViewById(R.id.describeEdt);

        publishLostOrFound();
    }

    private void publishLostOrFound() {
        comfirm(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEdt.getText().toString().trim();
                String address = addressEdt.getText().toString().trim();
                String contacts = contactsEdt.getText().toString().trim();
                String phone = phoneEdt.getText().toString().trim();
                String QQ = QQEdt.getText().toString().trim();
                String thanks = thankEdt.getText().toString().trim();
                String describe = describeEdt.getText().toString().trim();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(address) ||
                        TextUtils.isEmpty(contacts) || TextUtils.isEmpty(phone) ||
                        TextUtils.isEmpty(thanks) || TextUtils.isEmpty(describe)) {
                    CustomDialog.showTipsDialog(AddLostOrFoundActivity.this, "请填写完整", null);
                } else {
                    MyUser user = BmobUser.getCurrentUser(AddLostOrFoundActivity.this, MyUser.class);
                    if (flag == 0) {//发布Lost
                        Lost lost = new Lost();
                        lost.setLostTitle(title);
                        lost.setLostAddress(address);
                        lost.setLostContacts(contacts);
                        lost.setLostDescribe(describe);
                        lost.setLostPhone(phone);
                        lost.setLostQQ(QQ);
                        lost.setLostReward(thanks);
                        lost.setUsername(user);
                        lost.setIsRuning("进行中");
                        lost.save(AddLostOrFoundActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                toast("发布成功");
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                toast("发布失败");
                            }
                        });
                    } else {//发布Found
                        Found found = new Found();
                        found.setFoundTitle(title);
                        found.setFoundAddress(address);
                        found.setFoundContacts(contacts);
                        found.setFoundDescribe(describe);
                        found.setFoundPhone(phone);
                        found.setFoundQQ(QQ);
                        found.setFoundReward(thanks);
                        found.setUsername(user);
                        found.setIsRuning("进行中");
                        found.save(AddLostOrFoundActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                toast("发布成功");
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                toast("发布失败");
                            }
                        });
                    }
                }
            }
        });
    }

}
