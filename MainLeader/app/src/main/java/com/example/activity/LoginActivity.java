package com.example.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.common.AppConstants;
import com.example.entity.MyUser;
import com.example.error.ErrorList;
import com.example.utils.DialogUtils;
import com.example.utils.JudgeFormatUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private EditText loginNameEdt,loginPwdEdt;
    private SharedPreferences preferences;
    private boolean isLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        /*登录*/
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainActivity();
            }
        });


        /*注册*/
        TextView registerTxt = (TextView) findViewById(R.id.registerTxt);
        registerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegisterActivity();
            }
        });

        /*无密码登录*/
        TextView noPwdLoginTxt = (TextView) findViewById(R.id.noPwdLoginTxt);
        noPwdLoginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNoPwdLoginActivity();
            }
        });

        loginNameEdt = (EditText) findViewById(R.id.loginNameEdt);
        loginPwdEdt = (EditText) findViewById(R.id.loginPwdEdt);

        preferences = getSharedPreferences(AppConstants.PREFS_NAME,MODE_PRIVATE);
    }

    /*跳转无密码登录界面*/
    private void goNoPwdLoginActivity() {
        Intent intent = new Intent(this,NoPwdLoginActivity.class);
        startActivity(intent);
    }

    /*跳转注册界面*/
    private void goRegisterActivity() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }


    /*跳转主界面*/
    public void goMainActivity(){
        DialogUtils.getInstance().showProgressDialog(this,"正在登录");

        final String name = loginNameEdt.getText().toString().trim();
        final String pwd = loginPwdEdt.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            DialogUtils.getInstance().showWarningDialogWithoutCancel(LoginActivity.this,"请输入用户名",null);
        } else {
            if (JudgeFormatUtils.isPhone(name) || JudgeFormatUtils.isEmail(name)) {
                /*用户名和密码都不为空*/
                if (!TextUtils.isEmpty(pwd)) {
                    MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(pwd);
                    user.login(this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            MyUser user = BmobUser.getCurrentUser(LoginActivity.this, MyUser.class);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(AppConstants.LOGIN_NAME, name);
                            editor.putString(AppConstants.LOGIN_PWD, pwd);
                            isLogin = true;//登录后标识变为true，用作判断自动登录
                            editor.putBoolean(AppConstants.ISLOGIN, isLogin);
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            DialogUtils.getInstance().dimissDialog();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            DialogUtils.getInstance().showWarningDialogWithoutCancel(LoginActivity.this, ErrorList.error(i), null);
                        }
                    });
                } else {
                    DialogUtils.getInstance().showWarningDialogWithoutCancel(LoginActivity.this, "请输入密码", null);
                }
            } else {
                DialogUtils.getInstance().showWarningDialogWithoutCancel(LoginActivity.this, "登录名格式有误", null);
            }
        }
    }
}
