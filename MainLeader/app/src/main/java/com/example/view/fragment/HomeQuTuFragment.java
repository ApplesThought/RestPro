package com.example.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.error.VolleyError;
import com.example.application.AppUrl;
import com.example.ccinterface.RequestListener;
import com.example.manager.ReFreshManager;
import com.example.manager.RequestManager;
import com.example.manager.SPManager;
import com.example.mvp.model.QuTuInfo;
import com.example.view.activity.R;
import com.example.view.adapter.HomeQuTuAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.thefinestartist.finestwebview.FinestWebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeQuTuFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2 {
    private PullToRefreshListView lv;
    private HomeQuTuAdapter adapter;
    private List<QuTuInfo> list = new ArrayList<>();
    private String page = "2";
    private ListView listView;
    private int PULL_DOWN_FLAG = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_qutu_fragmenet, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        lv = (PullToRefreshListView) view.findViewById(R.id.lv);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        ReFreshManager.getReFreshManager().customPullToRefreshListViewTopText(lv);
        ReFreshManager.getReFreshManager().customPullToRefreshListViewBottomText(lv);
        lv.setOnRefreshListener(this);
        listView = lv.getRefreshableView();
        getData();
    }

    private void getData() {
        String quTuJson = SPManager.getInstance().getString(getActivity(), SPManager.KEY_HOME_QUTU);
        if (TextUtils.isEmpty(quTuJson)) {
            getDataFromNet("1");
        } else {
            getDataFromSDCard(quTuJson);
        }
        listView.setAdapter(adapter);
    }


    private void getDataFromNet(String p) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", p);
        RequestManager.getRequestManager().dealDataByGetWithToken(getActivity(), AppUrl.quTuBaseUrl, params, new RequestListener() {
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
                if (page.equals("2")) {
//                    listView.setAdapter(adapter);
                    SPManager.getInstance().saveString(getActivity(), SPManager.KEY_HOME_QUTU, JsonStr);
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
            JSONObject resultObj = object.optJSONObject("showapi_res_body");
            JSONArray array = resultObj.optJSONArray("contentlist");
            for (int i = 0; i < array.length(); i++) {
                QuTuInfo info = new QuTuInfo();
                JSONObject obj = array.optJSONObject(i);
                info.setTitle(obj.optString("title"));
                info.setCt(obj.optString("ct"));
                info.setImg(obj.optString("img"));
                list.add(info);
            }

            adapter = new HomeQuTuAdapter(getActivity(), list);
            onItemClick(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void onItemClick(final List<QuTuInfo> list) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new FinestWebView.Builder(getActivity())
                        .webViewJavaScriptEnabled(true)
                        .webViewDisplayZoomControls(false)
                        .progressBarHeight(5)
                        .progressBarColor(getResources().getColor(android.R.color.holo_red_light))
                        .swipeRefreshColors(getResources().getIntArray(R.array.swipe_colors))
                        .show(list.get(i - 1).getImg());
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        PULL_DOWN_FLAG = 10;
        getDataFromNet("1");
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getDataFromNet(page);
        int p = Integer.valueOf(page);
        int pa = p++;
        page = String.valueOf(pa);
        Log.d("Page", page + "/" + p + "/" + pa);
    }
}
