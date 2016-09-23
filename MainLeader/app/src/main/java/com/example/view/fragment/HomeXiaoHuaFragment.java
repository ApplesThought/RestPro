package com.example.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.error.VolleyError;
import com.example.application.AppUrl;
import com.example.ccinterface.RequestListener;
import com.example.manager.RequestManager;
import com.example.manager.SPManager;
import com.example.mvp.model.XiaoHuaInfo;
import com.example.view.activity.R;
import com.example.view.adapter.HomeXiaoHuaAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeXiaoHuaFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2 {
    private PullToRefreshListView lv;
    private HomeXiaoHuaAdapter adapter;
    private List<XiaoHuaInfo> list = new ArrayList<>();
//    private MySwipeRefreshLayout swipe;
    private int page = 2;
    private ListView listView;
    private int PULL_DOWN_FLAG = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_xiaohua_fragmenet, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        lv = (PullToRefreshListView) view.findViewById(R.id.lv);
        View footView = LayoutInflater.from(getActivity()).inflate(R.layout.xiaohua_bottom_view, null);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(this);
        listView = lv.getRefreshableView();
        listView.addFooterView(footView);
//        swipe = (MySwipeRefreshLayout) view.findViewById(R.id.swipe);
//        swipe.setOnRefreshListener(this);
        getData();
    }

    private void getData() {
        String xiaohuaJson = SPManager.getInstance().getString(getActivity(), SPManager.KEY_HOME_XIAOHUA);
        if (TextUtils.isEmpty(xiaohuaJson)) {
            getDataFromNet(1);
        } else {
            getDataFromSDCard(xiaohuaJson);
        }
        listView.setAdapter(adapter);
    }


    private void getDataFromNet(int p) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", p);
        params.put("pagesize", 20);
        params.put("key", AppUrl.xiaohuaAppKey);
        RequestManager.getRequestManager().dealDataByGet(getActivity(), AppUrl.xiaohuaBaseUrl, params, new RequestListener() {
            @Override
            public void dealDataSuccess(JSONObject jsonObject) {
                if (lv.isRefreshing()) {
                    lv.onRefreshComplete();
                }
                /*防止每次下拉刷新清除数据会出现空白,所以清除数据要在请求成功之后清除*/
                if (lv.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START && PULL_DOWN_FLAG == 10) {
                    list.clear();
                }
                String JsonStr = jsonObject.toString();
                parseJsonData(JsonStr);
                if (page == 2) {
                    listView.setAdapter(adapter);
                    SPManager.getInstance().saveString(getActivity(), SPManager.KEY_HOME_XIAOHUA, JsonStr);
                }
            }

            @Override
            public void dealDataFail(VolleyError volleyError) {
                if (lv.isRefreshing()) {
                    lv.onRefreshComplete();
                }
                Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getDataFromSDCard(String str) {
        parseJsonData(str);
    }

    private void parseJsonData(String jsonStr) {
        try {
            JSONObject object = new JSONObject(jsonStr);
            JSONObject resultObj = object.optJSONObject("result");
            JSONArray array = resultObj.optJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                XiaoHuaInfo info = new XiaoHuaInfo();
                JSONObject obj = array.optJSONObject(i);
                info.setUpdatetime(obj.optString("updatetime"));
                info.setContent(obj.optString("content"));
                list.add(info);
            }

            adapter = new HomeXiaoHuaAdapter(getActivity(),list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public void onRefresh() {
        getDataFromNet();
        handler.sendEmptyMessageDelayed(10, 2000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
            }
        }
    };*/

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        PULL_DOWN_FLAG = 10;
        getDataFromNet(1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getDataFromNet(page);
        page++;
    }
}
