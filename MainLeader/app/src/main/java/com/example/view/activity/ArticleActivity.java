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

import com.example.mvp.model.Post;
import com.example.utils.SortComparator;
import com.example.view.adapter.ArticleLvAdapter;
import com.example.view.customview.LoadingDialog;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class ArticleActivity extends KLBaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private String articleType;

    private ListView lv_article;
    private ArticleLvAdapter adapter;

    private LoadingDialog dialog;

    //下拉刷新
    private SwipeRefreshLayout swipe;
    private static final int REFRESH = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        articleType = getIntent().getStringExtra("class");
        setToolBarName(articleType);

        initView();
    }

    private void initView() {
        lv_article = (ListView) findViewById(R.id.lv_article);


        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

        swipe.measure(0,200);


//        lv_article.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                boolean enable = false;
//                if (lv_article != null && lv_article.getChildCount() > 0) {
//                    // check if the first item of the list is visible
//                    boolean firstItemVisible = lv_article.getFirstVisiblePosition() == 0;
//                    // check if the top of the first item is visible
//                    boolean topOfFirstItemVisible = lv_article.getChildAt(0).getTop() == 0;
//                    // enabling or disabling the refresh layout
//                    enable = firstItemVisible && topOfFirstItemVisible;
//                }
//                swipe.setEnabled(enable);
//            }
//        });

        addNew(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleActivity.this,EditArticleActivity.class);
                intent.putExtra("type",articleType);
                startActivity(intent);
            }
        });

        initData();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        initData();
//    }

    private void initData() {
        dialog = new LoadingDialog(this);
        if (!isFinishing()) {
            dialog.show();
        }
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("typeDetail", articleType);
        query.include("username");
//        query.order("-updateAt");
        query.findObjects(this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                adapter = new ArticleLvAdapter(ArticleActivity.this,list);
                Comparator comparator = new SortComparator();
                Collections.sort(list, comparator);
                lv_article.setAdapter(adapter);
                dialog.dismiss();

                onClick(list);
            }

            @Override
            public void onError(int i, String s) {
                toast("获取文章失败");
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(REFRESH,2000);
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
        lv_article.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = list.get(position).getTalkTitle();
                String objectId = list.get(position).getObjectId();
                Bundle bundle = new Bundle();
                bundle.putString("title",title);
                bundle.putString("objectId",objectId);
                Log.i("OVJHome", objectId);
                Intent intent = new Intent(ArticleActivity.this, TalkDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
