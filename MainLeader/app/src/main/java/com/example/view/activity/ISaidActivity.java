package com.example.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mvp.model.DailyMood;
import com.example.mvp.model.MyUser;
import com.example.utils.SortComparator;
import com.example.view.adapter.FriendStatusLvAdapter;
import com.example.view.customview.LoadingDialog;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class ISaidActivity extends KLBaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ListView lv_said;
    //下拉刷新
    private SwipeRefreshLayout swipe;
    private static final int REFRESH = 200;

    private FriendStatusLvAdapter adapter;

    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isaid);

        setToolBarName("说说");

        initView();
    }

    private void initView() {
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

        lv_said = (ListView) findViewById(R.id.lv_said);

        getData();
    }


    /*查询结果*/
    private void getData() {
        dialog = new LoadingDialog(this);
        if (!isFinishing()) {
            dialog.show();
        }
        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        String objectId = user.getObjectId();
        Log.i("TalkFragment", objectId);

        BmobQuery<MyUser> queryInner = new BmobQuery<>();
        String[] id = {objectId};
        queryInner.addWhereContainedIn("objectId", Arrays.asList(id));

        /*查询帖子*/
        BmobQuery<DailyMood> query = new BmobQuery<>();
        query.addWhereMatchesQuery("username", "_User", queryInner);
        query.include("username");
        query.findObjects(ISaidActivity.this, new FindListener<DailyMood>() {
            @Override
            public void onSuccess(List<DailyMood> list) {
                Comparator comparator = new SortComparator();
                Collections.sort(list, comparator);
                adapter = new FriendStatusLvAdapter(ISaidActivity.this, list);
                lv_said.setAdapter(adapter);
                dialog.dismiss();
                onItemClick(list);
            }

            @Override
            public void onError(int i, String s) {

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

    private void onItemClick(final List<DailyMood> list){
        lv_said.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String objectId = list.get(position).getObjectId();
                Bundle bundle = new Bundle();
                bundle.putString("objectId", objectId);
                Intent intent = new Intent(ISaidActivity.this, MoodDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
