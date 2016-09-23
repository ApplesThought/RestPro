package com.example.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.error.VolleyError;
import com.example.application.AppUrl;
import com.example.ccinterface.RequestListener;
import com.example.manager.ReFreshManager;
import com.example.manager.RequestManager;
import com.example.manager.SPManager;
import com.example.mvp.model.BaiduInfo;
import com.example.view.activity.R;
import com.example.view.adapter.BaiduHomeAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.thefinestartist.finestwebview.FinestWebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeKeJiFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2 {
    private PullToRefreshListView lv;
    private BaiduHomeAdapter adapter;
    private List<BaiduInfo> list = new ArrayList<>();
    private int page = 2;
    private ListView listView;
    private int PULL_DOWN_FLAG = 0;
    private String channelId = "5572a10ab3cdc86cf39001f4";

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
        String quTuJson = SPManager.getInstance().getString(getActivity(), SPManager.KEY_HOME_KEJI);
        if (TextUtils.isEmpty(quTuJson)) {
            getDataFromNet(1);
        } else {
            getDataFromSDCard(quTuJson);
        }
        listView.setAdapter(adapter);
    }


    private void getDataFromNet(int p) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", p);
        params.put("channelId", channelId);
        RequestManager.getRequestManager().dealDataByGetWithToken(getActivity(), AppUrl.baiduUrl, params, new RequestListener() {
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
                    SPManager.getInstance().saveString(getActivity(), SPManager.KEY_HOME_KEJI, JsonStr);
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
            JSONObject resultObj = object.optJSONObject("showapi_res_body")
                    .optJSONObject("pagebean");
            JSONArray arrayContentList = resultObj.optJSONArray("contentlist");
            for (int i = 0; i < arrayContentList.length(); i++) {
                BaiduInfo info = new BaiduInfo();
                JSONObject obj = arrayContentList.optJSONObject(i);
                info.setPubDate(obj.optString("pubDate"));
                info.setSource(obj.optString("source"));
                info.setLink(obj.optString("link"));
                info.setTitle(obj.optString("title"));
                JSONArray imgArray = obj.optJSONArray("imageurls");
                List<String> imgList = new ArrayList<>();
                for (int j = 0; j < imgArray.length(); j++) {
                    String url = imgArray.optJSONObject(j).optString("url");
                    imgList.add(url);
                }
                info.setImageurls(imgList);
                list.add(info);
            }

            adapter = new BaiduHomeAdapter(getActivity(), list);
            onItemClick(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void onItemClick(final List<BaiduInfo> list) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new FinestWebView.Builder(getActivity())
                        .webViewJavaScriptEnabled(true)
                        .webViewDisplayZoomControls(false)
                        .progressBarHeight(5)
                        .progressBarColor(getResources().getColor(android.R.color.holo_red_light))
                        .swipeRefreshColors(getResources().getIntArray(R.array.swipe_colors))
                        .show(list.get(i - 1).getLink());
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
