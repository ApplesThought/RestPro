package com.example.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.error.VolleyError;
import com.example.adapter.PhotoClassAdapter;
import com.example.adapter.PhotoListAdapter;
import com.example.application.AppUrl;
import com.example.ccinterface.RequestListener;
import com.example.entity.PhotoClassInfo;
import com.example.manager.ReFreshManager;
import com.example.manager.RequestManager;
import com.example.utils.IntentUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FLPhotoActivity extends KLBaseActivity implements PullToRefreshBase.OnRefreshListener2 {

    private ListView lv_classify, listView;
    private PullToRefreshListView lv_content;
    private PhotoClassAdapter classAdapter;
    private PhotoListAdapter listAdapter;
    private List<PhotoClassInfo> classList = new ArrayList<>();
    private List<PhotoClassInfo> listList;
    private int page = 2;
    private int listID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flphoto);

        setToolBarName("福利");

        initView();
    }

    private void initView() {
        lv_classify = (ListView) findViewById(R.id.lv_classify);
        lv_content = (PullToRefreshListView) findViewById(R.id.lv_content);

        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        ReFreshManager.getReFreshManager().customPullToRefreshListViewTopText(lv_content);
        ReFreshManager.getReFreshManager().customPullToRefreshListViewBottomText(lv_content);
        lv_content.setOnRefreshListener(this);
        listView = lv_content.getRefreshableView();
        listList = new ArrayList<>();
        getClassData();

        clickEvent();
    }

    private void clickEvent() {
        /*点击分类看列表*/
        lv_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classAdapter.setCurPositon(position);
                classAdapter.notifyDataSetChanged();
                lv_classify.smoothScrollToPositionFromTop(position, (parent.getHeight() - view.getHeight()) / 2);

                listID = classList.get(position).getId();

                listList = new ArrayList<>();
                getListDefaultData(listID, 1);
            }
        });

        /*点击列表看详情*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", listList.get(position - 1).getId());
                IntentUtils.toGoalAcrivityWithBundle(view.getContext(), PhotoDetailActivity.class, bundle);
            }
        });
    }

    private void getClassData() {
        RequestManager.getRequestManager().dealDataByGetWithToken(this, AppUrl.photoClassBaseUrl, null, new RequestListener() {
            @Override
            public void dealDataSuccess(JSONObject jsonObject) {
                parseClassJson(jsonObject);
            }

            @Override
            public void dealDataFail(VolleyError volleyError) {

            }
        });
    }

    private void getListDefaultData(int id, int p) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("page", p);
        RequestManager.getRequestManager().dealDataByGetWithToken(this, AppUrl.photoListBaseUrl, params, new RequestListener() {
            @Override
            public void dealDataSuccess(JSONObject jsonObject) {
                if (lv_content.isRefreshing()) {
                    lv_content.onRefreshComplete();
                }
                listList.clear();
                parseListJson(jsonObject);
                listView.setAdapter(listAdapter);
            }

            @Override
            public void dealDataFail(VolleyError volleyError) {
                if (lv_content.isRefreshing()) {
                    lv_content.onRefreshComplete();
                }
            }
        });
    }

    /*上拉加载*/
    private void getMoreListData(int id, int p) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("page", p);
        RequestManager.getRequestManager().dealDataByGetWithToken(this, AppUrl.photoListBaseUrl, params, new RequestListener() {
            @Override
            public void dealDataSuccess(JSONObject jsonObject) {
                if (lv_content.isRefreshing()) {
                    lv_content.onRefreshComplete();
                }
                page++;
                parseListJson(jsonObject);
            }

            @Override
            public void dealDataFail(VolleyError volleyError) {
                if (lv_content.isRefreshing()) {
                    lv_content.onRefreshComplete();
                }
            }
        });
    }

    /*解析列表*/
    private void parseListJson(JSONObject jsonObject) {
        JSONArray array = jsonObject.optJSONArray("tngou");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.optJSONObject(i);
            PhotoClassInfo info = new PhotoClassInfo();
            String title = object.optString("title");
            int id = object.optInt("id");
            String imgUrl = AppUrl.imgBaseUrl + object.optString("img");
            info.setTitle(title);
            info.setId(id);
            info.setImgUrl(imgUrl);
            listList.add(info);
        }
        listAdapter = new PhotoListAdapter(this, listList);
//        listView.setAdapter(listAdapter);
    }

    /*解析分类*/
    private void parseClassJson(JSONObject jsonObject) {
        JSONArray array = jsonObject.optJSONArray("tngou");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.optJSONObject(i);
            String title = object.optString("title");
            int id = object.optInt("id");
            PhotoClassInfo info = new PhotoClassInfo();
            info.setTitle(title);
            info.setId(id);
            classList.add(info);
        }

        classAdapter = new PhotoClassAdapter(this, classList);
        lv_classify.setAdapter(classAdapter);

        listID = classList.get(0).getId();
        getListDefaultData(listID, 1);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getListDefaultData(listID, 1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getMoreListData(listID, page);
    }
}
