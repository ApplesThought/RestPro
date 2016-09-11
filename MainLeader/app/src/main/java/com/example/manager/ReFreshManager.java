package com.example.manager;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by ${hcc} on 2016/08/12.
 */
public class ReFreshManager {
    private static ReFreshManager reFreshManager;

    public ReFreshManager() {
    }

    public static ReFreshManager getReFreshManager() {
        if (reFreshManager == null) {
            reFreshManager = new ReFreshManager();
        }
        return reFreshManager;
    }

    public void customPullToRefreshListViewBottomText(PullToRefreshListView listView){
        ILoadingLayout endLayout = listView.getLoadingLayoutProxy(false,true);
        endLayout.setPullLabel("获取更多");
        endLayout.setReleaseLabel("放手或许得到更多");
        endLayout.setRefreshingLabel("玩命加载中");
    }

    public void customPullToRefreshListViewTopText(PullToRefreshListView listView){
        ILoadingLayout startLayout = listView.getLoadingLayoutProxy(true,false);
        startLayout.setPullLabel("获取最新内容");
        startLayout.setReleaseLabel("放手发现新鲜事");
        startLayout.setRefreshingLabel("玩命加载中");
    }
}
