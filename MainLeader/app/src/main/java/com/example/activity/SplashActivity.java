package com.example.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.common.AppConstants;

import cn.bmob.v3.Bmob;

public class SplashActivity extends AppCompatActivity {

    //延迟2秒
    private static final long SPLASH_DELAY_MILLIS = 2000;
    //去登录页
    private static final int GO_LOGIN = 1000;
    //去引导页
    private static final int GO_GUIDE = 1001;
    //判断是否第一次登陆
    private boolean isFirst = false;
    private boolean isLogin = false;
    private SharedPreferences preferences,sp;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        Bmob.initialize(this, "984d516f5c3a033052f27ea4e1e84be5");

        preferences = getSharedPreferences(AppConstants.PREFS_FIRST_NAME,MODE_PRIVATE);
        sp = getSharedPreferences(AppConstants.PREFS_NAME,MODE_PRIVATE);
        initPrefs();
    }

    private void initPrefs() {
        SharedPreferences.Editor editor = preferences.edit();
        isFirst = preferences.getBoolean(AppConstants.PREFS_ISFIRST, true);
        //判断是否为第一次登录
        if(isFirst){
            handler.sendEmptyMessageDelayed(GO_GUIDE,SPLASH_DELAY_MILLIS);
            editor.putBoolean(AppConstants.PREFS_ISFIRST, false);
            editor.commit();
        }else{
            handler.sendEmptyMessageDelayed(GO_LOGIN, SPLASH_DELAY_MILLIS);
        }
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GO_GUIDE:
                    goGuideActivity();
                    break;
                case GO_LOGIN:
                    isLogin = sp.getBoolean(AppConstants.ISLOGIN,false);
                    if (isLogin){//登录过就自动登录跳转到主界面
                        goMainActivity();
                    } else {
                        goLoginActivity();
                    }
                    break;
            }
        }
    };


    /* 跳转登录页 */
    private void goLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /* 跳转欢迎页 */
    private void goGuideActivity() {
        Intent intent = new Intent();
        intent.setClass(this,GuideActivity.class);
        startActivity(intent);
        finish();
    }

    /* 跳转主页 */
    private void goMainActivity() {
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
