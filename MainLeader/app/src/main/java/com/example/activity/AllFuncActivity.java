package com.example.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.adapter.MoreFuncAdapter;
import com.example.utils.IntentUtils;

public class AllFuncActivity extends KLBaseActivity {

    private ListView lv;
    private MoreFuncAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_func);

        setToolBarName("更多");
        initView();
        clickEvent();
    }


    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        adapter = new MoreFuncAdapter(this);
        lv.setAdapter(adapter);
    }


    private void clickEvent() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://手机号码归属地
                        break;
                    case 1://身份证查询
                        break;
                    case 2://IP地址
                        break;
                    case 3://银行卡查询
                        break;
                    case 4://健康菜谱
                        IntentUtils.toGoalAcrivity(view.getContext(), HealthFoodClassifyActivity.class);
                        overridePendingTransition(R.anim.slide_bottom_in, android.R.anim.fade_out);
                        break;
                    case 5://福利图区
                        IntentUtils.toGoalAcrivity(view.getContext(), FLPhotoActivity.class);
                        break;
                    case 6://成语词典
                        IntentUtils.toGoalAcrivity(view.getContext(), ChengyuDicActivity.class);
                        break;
                    case 7://新华字典
                        break;
                    case 8://老黄历
                        break;
                    case 9://周公解梦
                        break;
                    case 10://历史上的今天
                        IntentUtils.toGoalAcrivity(view.getContext(), HistoryTodayActivity.class);
                        break;
                    case 11://星座运势
                        IntentUtils.toGoalAcrivity(view.getContext(), XingzuoActivity.class);
                        break;
                    case 12://天气预报
                        break;
                }
            }
        });
    }
}
