package com.example.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.android.volley.error.VolleyError;
import com.example.adapter.PhotoDetailAdapter;
import com.example.application.AppUrl;
import com.example.ccinterface.MyItemClickListener;
import com.example.ccinterface.RequestListener;
import com.example.entity.PhotoDetailInfo;
import com.example.manager.RequestManager;
import com.example.utils.IntentUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoDetailActivity extends KLBaseActivity {

    private RecyclerView recyclerView;
    private PhotoDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        setToolBarName("详情");

        initView();

        initData();

        refresh();
    }

    private void refresh() {
        refresh(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }


    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initData() {
        int detailID = getIntent().getExtras().getInt("id");
        Map<String, Object> params = new HashMap<>();
        params.put("id", detailID);
        RequestManager.getRequestManager().dealDataByGetWithToken(this, AppUrl.photoDetailBaseUrl, params, new RequestListener() {
            @Override
            public void dealDataSuccess(JSONObject jsonObject) {
                parsesDetailData(jsonObject);
            }

            @Override
            public void dealDataFail(VolleyError volleyError) {

            }
        });
    }

    private void parsesDetailData(JSONObject jsonObject) {
        JSONArray array = jsonObject.optJSONArray("list");
        final List<PhotoDetailInfo> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.optJSONObject(i);
            String src = AppUrl.imgBaseUrl + object.optString("src");
            PhotoDetailInfo info = new PhotoDetailInfo();
            info.setSrc(src);
            list.add(info);
        }
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new PhotoDetailAdapter(this, list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListner(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("imgUrl", list.get(position).getSrc());
                IntentUtils.toGoalAcrivityWithBundle(view.getContext(), PhotoBigActivity.class, bundle);
            }
        });
    }
}
