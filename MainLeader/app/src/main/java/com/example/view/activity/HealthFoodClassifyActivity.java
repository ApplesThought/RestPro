package com.example.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.error.VolleyError;
import com.example.application.AppUrl;
import com.example.ccinterface.RequestListener;
import com.example.manager.RequestManager;
import com.example.manager.SPManager;
import com.example.mvp.model.FoodClassify;
import com.example.utils.DialogUtils;
import com.example.utils.IntentUtils;
import com.example.view.adapter.FoodClassifyLvAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 健康食谱分类
 */
public class HealthFoodClassifyActivity extends KLBaseActivity {

    private ListView listView;
    private List<FoodClassify> list = new ArrayList<>();
    private FoodClassifyLvAdapter adapter;
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_food);

        setToolBarName("菜谱分类");

        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);

        String jsonStr = SPManager.getInstance().getString(this, SPManager.KEY_FOOD_CLASSIFY);
        if (jsonStr.isEmpty()) {
            getFoodData(0);//网络数据
        } else {
            parseData(jsonStr);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int classifyId = list.get(position).getId();
                String title = list.get(position).getTitle();
                Bundle bundle = new Bundle();
                bundle.putInt("id", classifyId);
                bundle.putString("title", title);
                IntentUtils.toGoalAcrivityWithBundle(HealthFoodClassifyActivity.this,
                        FoodListActivity.class, bundle);
            }
        });
    }

    private void getFoodData(int type) {
        DialogUtils.getInstance().showProgressDialog(this, "获取美食分类");
        Map<String, Object> params = new HashMap<>();
        params.put("id", type);
        RequestManager.getRequestManager().dealDataByGetWithToken(this, AppUrl.foodClassifyBaseUrl, params, new RequestListener() {
            @Override
            public void dealDataSuccess(JSONObject jsonObject) {
                SPManager.getInstance().saveString(HealthFoodClassifyActivity.this,
                        SPManager.KEY_FOOD_CLASSIFY, jsonObject.toString());//保存本地
                DialogUtils.getInstance().dimissDialog();
                parseData(jsonObject.toString());
            }

            @Override
            public void dealDataFail(VolleyError volleyError) {
                DialogUtils.getInstance().dimissDialog();
            }
        });
    }

    private void parseData(String objStr) {
        try {
            JSONObject jsonObject = new JSONObject(objStr);
            JSONArray array = jsonObject.optJSONArray("tngou");
            for (int i = 0; i < array.length(); i++) {
                FoodClassify classify = new FoodClassify();
                JSONObject object = array.optJSONObject(i);
                classify.setDescription(object.optString("description"));
                classify.setId(object.optInt("id"));
                classify.setTitle(object.optString("title"));
                list.add(classify);
            }
            adapter = new FoodClassifyLvAdapter(HealthFoodClassifyActivity.this, list);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clickBack() {
        super.clickBack();
        overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
    }
}
