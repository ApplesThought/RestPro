package com.example.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.error.VolleyError;
import com.example.application.AppUrl;
import com.example.ccinterface.RequestListener;
import com.example.manager.RequestManager;
import com.example.mvp.model.FoodList;
import com.example.utils.DialogUtils;
import com.example.utils.IntentUtils;
import com.example.view.adapter.FoodOtherLvAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OtherFoodListActivity extends KLBaseActivity {

    private ListView listView;
    private List<FoodList> list = new ArrayList<>();
    private FoodOtherLvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_food);

        setToolBarName("更多做法");

        String title = getIntent().getExtras().getString("title");

        initView(title);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int foodId = list.get(position).getId();
                String title = list.get(position).getName();
                Bundle bundle = new Bundle();
                bundle.putInt("id", foodId);
                bundle.putString("title", title);
                IntentUtils.toGoalAcrivityWithBundle(OtherFoodListActivity.this,
                        FoodDetailActivity.class, bundle);
            }
        });
    }

    private void initView(String title) {
        listView = (ListView) findViewById(R.id.lv);
        refreshOrDefault(title);
        listView.setAdapter(adapter);
    }

    private void refreshOrDefault(String title) {
        DialogUtils.getInstance().showProgressDialog(this, "获取美食中...");
        Map<String, Object> params = new HashMap<>();
        params.put("name", title);
        RequestManager.getRequestManager().dealDataByGetWithToken(this, AppUrl.foodHByNameBaseUrl, params, new RequestListener() {
            @Override
            public void dealDataSuccess(JSONObject jsonObject) {
                DialogUtils.getInstance().dimissDialog();
                String JsonStr = jsonObject.toString();
                parseJsonData(JsonStr);
                listView.setAdapter(adapter);
            }

            @Override
            public void dealDataFail(VolleyError volleyError) {
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
            adapter = new FoodOtherLvAdapter(OtherFoodListActivity.this, list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
