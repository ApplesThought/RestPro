package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.adapter.MainLvAdapter;
import com.example.customview.LoadingDialog;
import com.example.entity.MyUser;
import com.example.entity.Post;
import com.example.utils.SortComparator;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshHeaderListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class TieZiActivity extends KLBaseActivity implements PullToRefreshBase.OnRefreshListener2 {

    private LoadingDialog dialog;
    private MainLvAdapter adapter;
    //下拉刷新
    private PullToRefreshHeaderListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tie_zi);

        setToolBarName("帖子文章");

        initView();
        initRefresh();
    }

    private void initRefresh() {
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(this);
    }

    private void initView() {
        listView = (PullToRefreshHeaderListView) findViewById(R.id.list_view);

        getData();
    }

    private void getData() {
        dialog = new LoadingDialog(this);
        if (!isFinishing()) {
            dialog.show();
        }
        /*查询发布者*/
        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        BmobQuery<Post> query = new BmobQuery<>();
        query.include("username");
        query.findObjects(this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                adapter = new MainLvAdapter(TieZiActivity.this, list);
                /*将List按时间先后进行排序*/
                Comparator comparator = new SortComparator();
                Collections.sort(list, comparator);
                listView.setAdapter(adapter);
                dialog.dismiss();
                onItemClick(list);
            }

            @Override
            public void onError(int i, String s) {
                dialog.dismiss();
            }
        });
    }



    private void onItemClick(final List<Post> list) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = list.get(position).getTalkTitle();
                String objectId = list.get(position).getObjectId();
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("objectId", objectId);
                Log.i("OVJHome", objectId);
                Intent intent = new Intent(TieZiActivity.this, TalkDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /*下拉刷新*/
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getData();
        listView.onRefreshComplete();
    }

    /*上啦加载*/
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }
}
