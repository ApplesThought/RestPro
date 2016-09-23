package com.example.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class NoPwdLoginActivity extends KLBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_pwd_login);

        setToolBarName("无密码登录");

        initView();
    }

    private void initView() {
        Button noPwdBtn = (Button) findViewById(R.id.noPwdBtn);
        final EditText noPwdEdt = (EditText) findViewById(R.id.noPwdEdt);
        ImageView img_clear = (ImageView) findViewById(R.id.img_clear);

        if (noPwdEdt.isFocusable()) {
            String phoneNum = noPwdEdt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneNum)) {
                img_clear.setVisibility(View.GONE);
            } else {

            }
        }

        img_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}
