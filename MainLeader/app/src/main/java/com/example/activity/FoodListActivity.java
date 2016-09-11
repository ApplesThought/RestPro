package com.example.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.error.VolleyError;
import com.example.adapter.FoodListLvAdapter;
import com.example.application.AppUrl;
import com.example.ccinterface.RequestListener;
import com.example.entity.FoodList;
import com.example.manager.ReFreshManager;
import com.example.manager.RequestManager;
import com.example.utils.DialogUtils;
import com.example.utils.IntentUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodListActivity extends KLBaseActivity implements PullToRefreshBase.OnRefreshListener2 {

    private PullToRefreshListView lv;
    private int page = 2;
    private ListView listView;
    private int PULL_DOWN_FLAG = 0;
    private int classifyId;
    private List<FoodList> list = new ArrayList<>();
    private FoodListLvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        classifyId = getIntent().getExtras().getInt("id");
        String title = getIntent().getExtras().getString("title");
        setToolBarName(title);

        initView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int foodId = list.get(position - 1).getId();
                String title = list.get(position - 1).getName();
                Bundle bundle = new Bundle();
                bundle.putInt("id", foodId);
                bundle.putString("title", title);
                IntentUtils.toGoalAcrivityWithBundle(FoodListActivity.this,
                        FoodDetailActivity.class, bundle);
            }
        });
    }

    private void initView() {
        lv = (PullToRefreshListView) findViewById(R.id.lv);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        ReFreshManager.getReFreshManager().customPullToRefreshListViewTopText(lv);
        ReFreshManager.getReFreshManager().customPullToRefreshListViewBottomText(lv);
        lv.setOnRefreshListener(this);
        listView = lv.getRefreshableView();
        refreshOrDefault();
        listView.setAdapter(adapter);

        View footView = LayoutInflater.from(this).inflate(R.layout.xiaohua_bottom_view, null);
        listView.addFooterView(footView);
    }

    private void loadMore(int p) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", classifyId);
        params.put("page", p);
        params.put("rows", 20);
        RequestManager.getRequestManager().dealDataByGetWithToken(this, AppUrl.foodListBaseUrl, params, new RequestListener() {
            @Override
            public void dealDataSuccess(JSONObject jsonObject) {
                page++;
                if (lv.isRefreshing()) {
                    lv.onRefreshComplete();
                }
                String JsonStr = jsonObject.toString();
                parseJsonData(JsonStr);
            }

            @Override
            public void dealDataFail(VolleyError volleyError) {
                if (lv.isRefreshing()) {
                    lv.onRefreshComplete();
                }
            }
        });
    }

    private void refreshOrDefault() {
        DialogUtils.getInstance().showProgressDialog(this, "获取美食中...");
        Map<String, Object> params = new HashMap<>();
        params.put("id", classifyId);
        params.put("page", 1);
        params.put("rows", 20);
        RequestManager.getRequestManager().dealDataByGetWithToken(this, AppUrl.foodListBaseUrl, params, new RequestListener() {
            @Override
            public void dealDataSuccess(JSONObject jsonObject) {
                DialogUtils.getInstance().dimissDialog();
                if (lv.isRefreshing()) {
                    lv.onRefreshComplete();
                }
                list.clear();
                String JsonStr = jsonObject.toString();
                parseJsonData(JsonStr);
                listView.setAdapter(adapter);
            }

            @Override
            public void dealDataFail(VolleyError volleyError) {
                if (lv.isRefreshing()) {
                    lv.onRefreshComplete();
                }
                DialogUtils.getInstance().dimissDialog();
            }
        });
    }

    private void parseJsonData(String jsonStr) {
        try {
            JSONObject object = new JSONObject(jsonStr);
            JSONArray array = object.optJSONArray("tngou");
            for (int i = 0; i < array.length(); i++) {
                FoodList foodList = new FoodList();
                JSONObject obj = array.optJSONObject(i);
                foodList.setId(obj.optInt("id"));
                foodList.setDescription(obj.optString("description"));
                foodList.setFood(obj.optString("food"));
                foodList.setImgUrl(AppUrl.imgBaseUrl + obj.optString("img"));
                foodList.setKeywords(obj.optString("keywords"));
                foodList.setName(obj.optString("name"));
                list.add(foodList);
            }
            adapter = new FoodListLvAdapter(FoodListActivity.this, list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        PULL_DOWN_FLAG = 10;
        refreshOrDefault();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        loadMore(page);
    }
}
