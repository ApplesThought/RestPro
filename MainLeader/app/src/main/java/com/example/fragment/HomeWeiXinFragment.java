package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.error.VolleyError;
import com.example.activity.R;
import com.example.adapter.HomeWeiXinAdapter;
import com.example.application.AppUrl;
import com.example.ccinterface.RequestListener;
import com.example.entity.WeiXinInfo;
import com.example.manager.ReFreshManager;
import com.example.manager.RequestManager;
import com.example.manager.SPManager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.thefinestartist.finestwebview.FinestWebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeWeiXinFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2 {
    private ListView listView;
    private HomeWeiXinAdapter adapter;
    private List<WeiXinInfo> list = new ArrayList<>();
    private PullToRefreshListView lv;
    private int page = 2;
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
        String weixinJson = SPManager.getInstance().getString(getActivity(), SPManager.KEY_HOME_WEIXIN);
        if (TextUtils.isEmpty(weixinJson)) {
            getDataFromNet(1);
        } else {
            getDataFromSDCard(weixinJson);
        }
        listView.setAdapter(adapter);
    }


    private void getDataFromNet(int p) {
        Map<String, Object> params = new HashMap<>();
        params.put("ps",30);
        params.put("pno",p);
        params.put("key", AppUrl.weiXinAppKey);
        RequestManager.getRequestManager().dealDataByGet(getActivity(), AppUrl.weiXinBaseUrl, params, new RequestListener() {
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
                    SPManager.getInstance().saveString(getActivity(), SPManager.KEY_HOME_WEIXIN, JsonStr);
                }
            }

            @Override
            public void dealDataFail(VolleyError volleyError) {
                if (lv.isRefreshing()) {
                    lv.onRefreshComplete();
                }
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
            JSONArray array = resultObj.optJSONArray("list");
            list.clear();
            for (int i = 0; i < array.length(); i++) {
                WeiXinInfo info = new WeiXinInfo();
                JSONObject obj = array.optJSONObject(i);
                info.setTitle(obj.optString("title"));
                info.setSource(obj.optString("source"));
                info.setFirstImg(obj.optString("firstImg"));
                info.setUrl(obj.optString("url"));
                list.add(info);
            }

            adapter = new HomeWeiXinAdapter(getActivity(),list);
            lv.setAdapter(adapter);
            onItemClick(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void onItemClick(final List<WeiXinInfo> list) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Location", i + "");
//                Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                intent.putExtra("url", list.get(i).getUrl());
//                startActivity(intent);
                new FinestWebView.Builder(getActivity())
//                        .webViewJavaScriptEnabled(false)
                        .webViewDisplayZoomControls(false)
                        .progressBarHeight(5)
                        .progressBarColor(getResources().getColor(android.R.color.holo_red_light))
                        .swipeRefreshColors(getResources().getIntArray(R.array.swipe_colors))
                        .show(list.get(i).getUrl());
            }
        });
    }


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
