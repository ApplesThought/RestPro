package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.adapter.LaughLvAdapter;
import com.example.customview.LoadingDialog;
import com.example.entity.Post;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class LaughActivity extends KLBaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ListView lv_laugh;
    private LaughLvAdapter adapter;
    private LoadingDialog dialog;
    //下拉刷新
    private SwipeRefreshLayout swipe;
    private static final int REFRESH = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laugh);

        setToolBarName("搞笑段子");

        initView();
    }

    private void initView() {
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);
        lv_laugh = (ListView) findViewById(R.id.lv_laugh);

        /*点击ToolBar上的图标跳转*/
        addNew(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaughActivity.this,PublishTalkActivity.class);
                intent.putExtra("type","搞笑段子");
                startActivity(intent);
            }
        });

        initData();
    }

    private void initData() {
        dialog = new LoadingDialog(this);
        if (!isFinishing()) {
            dialog.show();
        }
        BmobQuery<Post> query = new BmobQuery<>();
        query.include("username");
        query.order("-updateAt");
        query.addWhereEqualTo("type", "搞笑段子");
        query.include("username");
        query.findObjects(this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                adapter = new LaughLvAdapter(LaughActivity.this, list);
                lv_laugh.setAdapter(adapter);
                dialog.dismiss();

                onClick(list);
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
                    initData();
                    swipe.setRefreshing(false);
                    break;
            }
        }
    };


    private void onClick(final List<Post> list){
        lv_laugh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = list.get(position).getTalkTitle();
                String objectId = list.get(position).getObjectId();
                Bundle bundle = new Bundle();
                bundle.putString("title",title);
                bundle.putString("objectId",objectId);
                Log.i("OVJHome", objectId);
                Intent intent = new Intent(LaughActivity.this, TalkDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
