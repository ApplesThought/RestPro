package com.example.view.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.address.addresswheel.OnWheelChangedListener;
import com.example.address.addresswheel.WheelView;
import com.example.address.addresswheeladapter.ArrayWheelAdapter;
import com.example.ccinterface.IAlertDialogListener;
import com.example.mvp.model.MyUser;
import com.example.view.customview.CustomDialog;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

public class EditInfoActivity extends KLBaseActivity implements OnWheelChangedListener {

    private TextView loginNameTxt, genderTxt, addressTxt, birthdayTxt;
    private EditText nickNameEdt, introEdt, mailEdt, QQEdt;

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private View addressdialogView, birthDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        setToolBarName("编辑资料");

        initView();
    }

    private void initView() {
        loginNameTxt = (TextView) findViewById(R.id.loginNameTxt);
        genderTxt = (TextView) findViewById(R.id.genderTxt);
        addressTxt = (TextView) findViewById(R.id.addressTxt);
        birthdayTxt = (TextView) findViewById(R.id.birthdayTxt);
        nickNameEdt = (EditText) findViewById(R.id.nickNameEdt);
        introEdt = (EditText) findViewById(R.id.introEdt);
        mailEdt = (EditText) findViewById(R.id.mailEdt);
        QQEdt = (EditText) findViewById(R.id.QQEdt);


        birthDialogView = LayoutInflater.from(this).inflate(R.layout.custom_birthselect_dialog, null);

        addressdialogView = LayoutInflater.from(this).inflate(R.layout.custom_addressselect_dialog, null);
        mViewProvince = (WheelView) addressdialogView.findViewById(R.id.province);
        mViewCity = (WheelView) addressdialogView.findViewById(R.id.city);
        mViewDistrict = (WheelView) addressdialogView.findViewById(R.id.district);

        initData();
    }

    private void initData() {
        String loginName = getIntent().getStringExtra("loginname");
        String nick = getIntent().getStringExtra("nick");
        String gender = getIntent().getStringExtra("gender");
        String address = getIntent().getStringExtra("address");
        String intro = getIntent().getStringExtra("intro");
        String birth = getIntent().getStringExtra("birth");
        String mail = getIntent().getStringExtra("mail");
        String QQ = getIntent().getStringExtra("QQ");

        loginNameTxt.setText(loginName);
        nickNameEdt.setText(nick);
        genderTxt.setText(gender);
        addressTxt.setText(address);
        introEdt.setText(intro);
        birthdayTxt.setText(birth);
        mailEdt.setText(mail);
        QQEdt.setText(QQ);

        initProvinceDatas();
        Log.i("解析", mViewProvince + "===" + mProvinceDatas);
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(EditInfoActivity.this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();

        initEvent();
    }

    AlertDialog addressDialog, birthDialog;

    private void initEvent() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);


        /*确认编辑*/
        comfirm(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickNmae = nickNameEdt.getText().toString().trim();
                String gender = genderTxt.getText().toString().trim();
                String address = addressTxt.getText().toString().trim();
                String intro = introEdt.getText().toString().trim();
                String birth = birthdayTxt.getText().toString().trim();
                String mail = mailEdt.getText().toString().trim();
                String QQ = QQEdt.getText().toString().trim();
                MyUser user = new MyUser();
                user.setNick(nickNmae);
                user.setGender(gender);
                user.setAddress(address);
                user.setSignal(intro);
                user.setBirth(birth);
                user.setUserMail(mail);
                user.setQQ(QQ);
                MyUser myUser = BmobUser.getCurrentUser(EditInfoActivity.this,MyUser.class);
                user.update(EditInfoActivity.this, myUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        toast("资料修改成功");
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toast("资料修改失败");
                    }
                });
            }
        });

        /*选择性别*/
        genderTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDialog();
            }
        });


        /*选择地址*/
        addressTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditInfoActivity.this);

                TextView dialog_comfirm = (TextView) addressdialogView.findViewById(R.id.dialog_comfirm);

                dialog_comfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (addressDialog != null) {
                            addressDialog.dismiss();
                        }

                        addressTxt.setText(mCurrentProviceName + "-" + mCurrentCityName + "-" + mCurrentDistrictName);
                    }
                });

                /*为dialog设置View*/
                builder.setView(addressdialogView);
                if (addressDialog == null) {
                    addressDialog = builder.create();
                }

                /*显示对话框*/
                addressDialog.show();
            }
        });


        /*选择生日*/
        birthdayTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditInfoActivity.this);

                TextView dialog_comfirm = (TextView) birthDialogView.findViewById(R.id.dialog_comfirm);

                final DatePicker picker = (DatePicker) birthDialogView.findViewById(R.id.picker);
                dialog_comfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (birthDialog != null) {
                            birthDialog.dismiss();
                            int month = picker.getMonth() + 1;
                            birthdayTxt.setText(picker.getYear() + "-" + month + "-" + picker.getDayOfMonth());
                        }
                    }
                });

                /*为dialog设置View*/
                builder.setView(birthDialogView);
                if (birthDialog == null) {
                    birthDialog = builder.create();
                }

                /*显示对话框*/
                birthDialog.show();
            }
        });
    }


    private void showSelectDialog() {
        CustomDialog.showSelectDialog(this, "男生", "女生", "保密", new IAlertDialogListener() {
            @Override
            public void onClick() {//第一项点击
                genderTxt.setText("男生");
            }
        }, new IAlertDialogListener() {
            @Override
            public void onClick() {//第二项点击
                genderTxt.setText("女生");
            }
        }, new IAlertDialogListener() {
            @Override
            public void onClick() {
                genderTxt.setText("保密");
            }
        });
    }


    /*选择省市区*/
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }


    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }
}
