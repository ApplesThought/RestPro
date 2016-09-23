package com.example.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.manager.SPManager;
import com.example.mvp.model.MyUser;
import com.example.utils.IntentUtils;
import com.example.utils.ToastUtils;
import com.example.view.activity.AllPeopleActivity;
import com.example.view.activity.FriendStatusActivity;
import com.example.view.activity.ISaidActivity;
import com.example.view.activity.MyLostAndFoundActivity;
import com.example.view.activity.PersonalInfoActivity;
import com.example.view.activity.R;
import com.example.view.adapter.MineGridViewAdapter;
import com.example.view.customview.CircleTextImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;


public class MineFragment extends Fragment {

    private CircleTextImageView circleImg;
    private TextView loginName, introTxt;

    private LinearLayout ll_allPeople;

    private GridView gridView;
    private MineGridViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_four, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        gridView = (GridView) view.findViewById(R.id.gridView);
        /*个人信息*/
        RelativeLayout rel_personer = (RelativeLayout) view.findViewById(R.id.rel_personer);
        rel_personer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalInfoActivity.class);
                startActivity(intent);
            }
        });

        /*好友动态*/
        LinearLayout ll_status = (LinearLayout) view.findViewById(R.id.ll_status);
        ll_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendStatusActivity.class);
                startActivity(intent);
            }
        });

        /*失物招领*/
        /*LinearLayout myLostAndFound = (LinearLayout) view.findViewById(R.id.myLostAndFound);
        myLostAndFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.toGoalAcrivity(getActivity(), MyLostAndFoundActivity.class);
            }
        });*/

        /*LinearLayout myMood = (LinearLayout) view.findViewById(R.id.myMood);
        myMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.toGoalAcrivity(getActivity(), ISaidActivity.class);
            }
        });*/

        /*所有人*/
        ll_allPeople = (LinearLayout) view.findViewById(R.id.ll_allPeople);
        ll_allPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.toGoalAcrivity(getActivity(), AllPeopleActivity.class);
            }
        });

        adapter = new MineGridViewAdapter(getActivity());
        gridView.setAdapter(adapter);


        circleImg = (CircleTextImageView) view.findViewById(R.id.img_head);
        loginName = (TextView) view.findViewById(R.id.loginName);
        introTxt = (TextView) view.findViewById(R.id.introTxt);

        initData();

        initEvent();
    }

    private void initEvent() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        IntentUtils.toGoalAcrivity(getActivity(), MyLostAndFoundActivity.class);
                        break;

                    case 1:
                        IntentUtils.toGoalAcrivity(getActivity(), ISaidActivity.class);
                        break;
                }
            }
        });
    }

    private void initData() {
        String nick = SPManager.getInstance().getUserString(getContext(), SPManager.USER_ACCOUNT, SPManager.KEY_USER_NICK);
        String name = SPManager.getInstance().getUserString(getContext(), SPManager.USER_ACCOUNT, SPManager.KEY_USER_NAME);
        String signal = SPManager.getInstance().getUserString(getContext(), SPManager.USER_ACCOUNT, SPManager.KEY_USER_SIGNAL);
        String photo = SPManager.getInstance().getUserString(getContext(), SPManager.USER_ACCOUNT, SPManager.KEY_USER_PHOTO);

        if (nick.isEmpty()) {
            final MyUser user = BmobUser.getCurrentUser(getActivity(), MyUser.class);
            String objectId = user.getObjectId();
            BmobQuery<MyUser> query = new BmobQuery<>();
            query.addWhereEqualTo("objectId", objectId);
            query.findObjects(getActivity(), new FindListener<MyUser>() {
                @Override
                public void onSuccess(List<MyUser> list) {
                    SPManager.getInstance().saveMyUser(getContext(), user);
                    String nick = "", signal = "", name = "", photoStr = "";
                    for (MyUser u : list) {
                        nick = u.getNick();
                        signal = u.getSignal();
                        name = u.getUsername();
                        photoStr = u.getUserPhoto();
                    }
                    if (TextUtils.isEmpty(nick)) {
                        loginName.setText(name);
                    } else {
                        loginName.setText(nick);
                    }
                    if (TextUtils.isEmpty(signal)) {
                        introTxt.setText("简介:暂无介绍");
                    } else {
                        introTxt.setText("简介:" + signal);
                    }

                    if (TextUtils.isEmpty(photoStr)) {
                        circleImg.setImageResource(R.drawable.health_guide_men_selected);
                    } else {
                        Picasso.with(getActivity()).load(photoStr).into(circleImg);
                    }
                }

                @Override
                public void onError(int i, String s) {
                    ToastUtils.showToast(getActivity(), "信息获取失败");
                }
            });
        } else {
            if (TextUtils.isEmpty(nick)) {
                loginName.setText(name);
            } else {
                loginName.setText(nick);
            }
            if (TextUtils.isEmpty(signal)) {
                introTxt.setText("简介:暂无介绍");
            } else {
                introTxt.setText("简介:" + signal);
            }

            if (TextUtils.isEmpty(photo)) {
                circleImg.setImageResource(R.drawable.health_guide_men_selected);
            } else {
                Glide.with(getActivity()).load(photo).into(circleImg);
            }
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
