package com.example.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.entity.MyUser;
import com.example.error.ErrorList;
import com.example.fragment.RegisterInMailFragment;
import com.example.fragment.RegisterInPhoneFragment;
import com.example.utils.DialogUtils;
import com.example.utils.JudgeFormatUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends KLBaseActivity implements ViewPager.OnPageChangeListener,
        View.OnClickListener {

    private ViewPager viewPager;

    private TextView regTxtInPhone,regTxtInMail;

    private List<Fragment> views;

    private Button registerBtn;
    private RegisterInPhoneFragment phoneFragment;
    private RegisterInMailFragment mailFragment;

    private String FLAG_PHONE_OR_MAIL = "1";//标识是手机注册还是邮箱注册 1-手机  2-邮箱

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setToolBarName("注册");

        initView();

        initEvent();
    }

    private void initEvent() {
        regTxtInPhone.setOnClickListener(this);
        regTxtInMail.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);

        registerBtn.setOnClickListener(this);
    }

    private void initView() {
        regTxtInPhone = (TextView) findViewById(R.id.regTxtInPhone);
        regTxtInMail = (TextView) findViewById(R.id.regTxtInMail);

        viewPager = (ViewPager) findViewById(R.id.registerViewPager);

        views = new ArrayList<>();
        phoneFragment = new RegisterInPhoneFragment();
        mailFragment = new RegisterInMailFragment();
        views.add(phoneFragment);
        views.add(mailFragment);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public Fragment getItem(int position) {
                return views.get(position);
            }
        };
        viewPager.setAdapter(adapter);


        registerBtn = (Button) findViewById(R.id.registerBtn);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                regTxtInPhone.setBackgroundResource(R.drawable.left_corner_bg_orange);
                regTxtInPhone.setTextColor(getResources().getColor(R.color.white));
                regTxtInMail.setBackgroundResource(R.drawable.right_corner_bg_white);
                regTxtInMail.setTextColor(getResources().getColor(R.color.black));
                FLAG_PHONE_OR_MAIL = "1";
                break;

            case 1:
                regTxtInMail.setBackgroundResource(R.drawable.right_corner_bg_orange);
                regTxtInMail.setTextColor(getResources().getColor(R.color.white));
                regTxtInPhone.setBackgroundResource(R.drawable.left_corner_bg_white);
                regTxtInPhone.setTextColor(getResources().getColor(R.color.black));
                FLAG_PHONE_OR_MAIL = "2";
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regTxtInPhone:
                regTxtInPhone.setBackgroundColor(Color.parseColor("#FF8000"));
                regTxtInPhone.setTextColor(getResources().getColor(R.color.white));
                regTxtInMail.setBackgroundColor(Color.parseColor("#FFFFFF"));
                regTxtInMail.setTextColor(getResources().getColor(R.color.black));
                FLAG_PHONE_OR_MAIL = "1";
                viewPager.setCurrentItem(0);
                break;

            case R.id.regTxtInMail:
                regTxtInMail.setBackgroundColor(Color.parseColor("#FF8000"));
                regTxtInMail.setTextColor(getResources().getColor(R.color.white));
                regTxtInPhone.setBackgroundColor(Color.parseColor("#FFFFFF"));
                regTxtInPhone.setTextColor(getResources().getColor(R.color.black));
                FLAG_PHONE_OR_MAIL = "2";
                viewPager.setCurrentItem(1);
                break;

            case R.id.registerBtn:
                //手机注册
                if (FLAG_PHONE_OR_MAIL.equals("1")) {
                    //获取Fragment中输入的信息
                    String num = phoneFragment.getPhoneNum();
//                    toast(num+"手机注册===================");
                    String passwd = phoneFragment.getPassWd();
                    registerInPhone(num, passwd);
                }
                //邮箱注册
                else {
                    String mail = mailFragment.getMail();
                    String passwd = mailFragment.getPassWd();
                    registerInMail(mail, passwd);
                }

                break;
        }
    }


    /*手机注册*/
    private void registerInPhone(final String phone, String passWd) {
        DialogUtils.getInstance().showProgressDialog(this,"正在注册");
        if (TextUtils.isEmpty(phone)) {//不输入手机号
            DialogUtils.getInstance().showWarningDialogWithoutCancel(RegisterActivity.this, "请输入手机号码", null);
        } else {
            if (JudgeFormatUtils.isPhone(phone)) {//符合手机号码格式
                if (TextUtils.isEmpty(passWd)) {
                    DialogUtils.getInstance().showWarningDialogWithoutCancel(RegisterActivity.this, "密码不能为空", null);
                } else {
                    if (passWd.length() < 6) {//密码不为空但长度小于6位
                        DialogUtils.getInstance().showWarningDialogWithoutCancel(RegisterActivity.this, "密码至少6位", null);
                    } else {
                        MyUser userEntity = new MyUser();
                        userEntity.setUsername(phone);
                        userEntity.setPassword(passWd);
                        Log.i("REGISTER:", phone + "===" + passWd);
                        userEntity.signUp(this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                DialogUtils.getInstance().showSuccessDialog(RegisterActivity.this, "注册成功");
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                DialogUtils.getInstance().showWarningDialogWithoutCancel(RegisterActivity.this, ErrorList.error(i), null);
                            }
                        });
                    }
                }
            } else {
                DialogUtils.getInstance().showWarningDialogWithoutCancel(RegisterActivity.this, "手机号码格式错误", null);
            }
        }


    }


    /*邮箱注册*/
    private void registerInMail(final String mail,String passWd){
        DialogUtils.getInstance().showProgressDialog(this,"正在注册");
        if (TextUtils.isEmpty(mail)) {
            DialogUtils.getInstance().showWarningDialogWithoutCancel(RegisterActivity.this, "邮箱地址为空", null);
        } else {
            if (!JudgeFormatUtils.isEmail(mail)) {//不符合邮箱格式
                DialogUtils.getInstance().showWarningDialogWithoutCancel(RegisterActivity.this, "邮箱地址格式不正确", null);
            } else {
                if (TextUtils.isEmpty(passWd)) {
                    DialogUtils.getInstance().showWarningDialogWithoutCancel(RegisterActivity.this, "密码不能为空", null);
                } else {
                    if (passWd.length() < 6) {//密码不为空但长度小于6位
                        DialogUtils.getInstance().showWarningDialogWithoutCancel(RegisterActivity.this, "密码至少6位", null);
                    } else {
                        MyUser userEntity = new MyUser();
                        userEntity.setUsername(mail);
                        userEntity.setPassword(passWd);
                        userEntity.setEmail(mail);
                        Log.i("REGISTER:", mail + "===" + passWd);
                        userEntity.signUp(this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                DialogUtils.getInstance().showSuccessDialog(RegisterActivity.this, mail +"注册成功");
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                DialogUtils.getInstance().showWarningDialogWithoutCancel(RegisterActivity.this, ErrorList.error(i), null);
                            }
                        });
                    }
                }
            }
        }
    }
}
