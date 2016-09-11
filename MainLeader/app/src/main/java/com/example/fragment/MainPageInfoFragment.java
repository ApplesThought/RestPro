package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.activity.EditInfoActivity;
import com.example.activity.R;
import com.example.entity.MyUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class MainPageInfoFragment extends Fragment {

    private TextView loginName_info,nickName_info,gender_info,QQ_info,registerTime_info;
    private TextView address_info,intro_info,birthday_info,mail_info,mark_info;

    private LinearLayout ll_nick,ll_gender,ll_address,ll_intro,ll_birth,ll_mail,
                    ll_QQ,ll_mark,ll_regTime;

    private Button editBtn;

    private ImageView maleOrfemale;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_main_page, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        loginName_info = (TextView) view.findViewById(R.id.loginName_info);//登录名
        nickName_info = (TextView) view.findViewById(R.id.nickName_info);//昵称
        gender_info = (TextView) view.findViewById(R.id.gender_info);//性别
        address_info = (TextView) view.findViewById(R.id.address_info);//地址
        intro_info = (TextView) view.findViewById(R.id.intro_info);//简介
        birthday_info = (TextView) view.findViewById(R.id.birthday_info);//生日
        mail_info = (TextView) view.findViewById(R.id.mail_info);//邮箱
        QQ_info = (TextView) view.findViewById(R.id.QQ_info);//QQ
        mark_info = (TextView) view.findViewById(R.id.mark_info);//积分
        registerTime_info = (TextView) view.findViewById(R.id.registerTime_info);//注册时间

        maleOrfemale = (ImageView) view.findViewById(R.id.maleOrfemale);

        ll_nick = (LinearLayout) view.findViewById(R.id.ll_nick);
        ll_gender = (LinearLayout) view.findViewById(R.id.ll_gender);
        ll_address = (LinearLayout) view.findViewById(R.id.ll_address);
        ll_intro = (LinearLayout) view.findViewById(R.id.ll_intro);
        ll_birth = (LinearLayout) view.findViewById(R.id.ll_birth);
        ll_mail = (LinearLayout) view.findViewById(R.id.ll_mail);
        ll_QQ = (LinearLayout) view.findViewById(R.id.ll_QQ);
        ll_mark = (LinearLayout) view.findViewById(R.id.ll_mark);
        ll_regTime = (LinearLayout) view.findViewById(R.id.ll_regTime);

        editBtn = (Button) view.findViewById(R.id.editBtn);

        initData();

        initEvent();
    }

    private void initEvent() {
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),EditInfoActivity.class);
                intent.putExtra("loginname",loginName_info.getText().toString().trim());
                intent.putExtra("nick",nickName_info.getText().toString().trim());
                intent.putExtra("gender",gender_info.getText().toString().trim());
                intent.putExtra("address",address_info.getText().toString().trim());
                intent.putExtra("intro",intro_info.getText().toString().trim());
                intent.putExtra("birth",birthday_info.getText().toString().trim());
                intent.putExtra("mail",mail_info.getText().toString().trim());
                intent.putExtra("QQ",QQ_info.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        MyUser user = BmobUser.getCurrentUser(getActivity(), MyUser.class);
        String loginName = user.getUsername();
        String regTime = user.getCreatedAt();
        loginName_info.setText(loginName);
        registerTime_info.setText(regTime);

        String objectId = user.getObjectId();
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId",objectId);
        query.findObjects(getActivity(), new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                String nick = "",signal = "",QQ = "",gender = "",address = "",
                        birthday = "",mail = "",mark = "";//积分
                for (MyUser u:list) {
                    nick = u.getNick();
                    signal = u.getSignal();
                    QQ = u.getQQ();
                    gender = u.getGender();
                    address = u.getAddress();
                    birthday = u.getBirth();
                    mail = u.getUserMail();
                }
                nickName_info.setText(nick);
                intro_info.setText(signal);
                QQ_info.setText(QQ);
                gender_info.setText(gender);
                address_info.setText(address);
                birthday_info.setText(birthday);
                mail_info.setText(mail);

                if (gender.equals("男生")) {
                    maleOrfemale.setImageResource(R.mipmap.list_male);
                } else if (gender.equals("女生")){
                    maleOrfemale.setImageResource(R.mipmap.list_female);
                } else {
                    maleOrfemale.setImageResource(R.mipmap.list_unknown);
                }
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

}
