package com.example.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.FriendStatusLvAdapter;
import com.example.customview.CircleTextImageView;
import com.example.customview.LoadingDialog;
import com.example.entity.DailyMood;
import com.example.utils.IntentUtils;
import com.example.utils.SortComparator;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class FriendStatusActivity extends KLBaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ListView lv_status;
    private FriendStatusLvAdapter adapter;
    //下拉刷新
    private SwipeRefreshLayout swipe;
    private static final int REFRESH = 200;
    private LoadingDialog dialog;
    private CircleTextImageView img_photo;

    private ImageView backImg;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friend_status);

        getWindow().setStatusBarColor(Color.parseColor("#FF8000"));
//
        setToolBarName("即兴说说");
        initView();
    }

    private void initView() {
        View v = LayoutInflater.from(this).inflate(R.layout.info_img, null);
        LinearLayout fra = (LinearLayout) v.findViewById(R.id.fra);
        /*这个是ListView的第一项，写监听抢夺事件防止崩溃*/
        fra.setOnClickListener(null);
        /*写状态*/
        TextView writeTxt = (TextView) v.findViewById(R.id.writeTxt);
        writeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendStatusActivity.this, PublishTalkActivity.class);
                intent.putExtra("type", "即兴说说");
                startActivity(intent);
            }
        });

        /*说说*/
        TextView talkTxt = (TextView) v.findViewById(R.id.talkTxt);
        talkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.toGoalAcrivity(FriendStatusActivity.this, ISaidActivity.class);
            }
        });

        /*签到*/
        TextView checkInTxt = (TextView) v.findViewById(R.id.checkInTxt);
        checkInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.toGoalAcrivity(FriendStatusActivity.this, CheckInActivity.class);
            }
        });
        img_photo = (CircleTextImageView) v.findViewById(R.id.img_photo);


        lv_status = (ListView) findViewById(R.id.lv_status);


        lv_status.addHeaderView(v);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

        getData();
    }


    @Override
    protected void onResume() {
        super.onResume();
//        getData();
    }

    /*查询结果*/
    private void getData() {
        dialog = new LoadingDialog(this);
        if (!isFinishing()) {
            dialog.show();
        }


        String userPhoto = (String) BmobUser.getObjectByKey(this, "userPhoto");
        if (TextUtils.isEmpty(userPhoto)) {
            img_photo.setImageResource(R.drawable.health_guide_men_selected);
        } else {
            Picasso.with(this).load(userPhoto).into(img_photo);
        }

        BmobQuery<DailyMood> query = new BmobQuery<>();
        query.include("username");
        query.findObjects(this, new FindListener<DailyMood>() {
            @Override
            public void onSuccess(List<DailyMood> list) {
                adapter = new FriendStatusLvAdapter(FriendStatusActivity.this, list);
                /*将List按时间先后进行排序*/
                Comparator comparator = new SortComparator();
                Collections.sort(list, comparator);
                lv_status.setAdapter(adapter);
                dialog.dismiss();
                onItemClick(list);
            }

            @Override
            public void onError(int i, String s) {
                dialog.dismiss();
            }
        });

    }


    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(REFRESH, 2000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH:
                    getData();
                    swipe.setRefreshing(false);
                    break;
            }
        }
    };


    private void onItemClick(final List<DailyMood> list) {
        lv_status.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String objectId = list.get(position - 1).getObjectId();
                Bundle bundle = new Bundle();
                bundle.putString("objectId", objectId);
                Log.i("OVJHome", objectId);
                Intent intent = new Intent(FriendStatusActivity.this, MoodDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
