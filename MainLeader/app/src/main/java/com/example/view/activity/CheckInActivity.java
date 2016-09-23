package com.example.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.mvp.model.CheckInImg;
import com.example.view.adapter.CheckInGridViewAdapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class CheckInActivity extends KLBaseActivity {

    private GridView gv_checkImg;
    private CheckInGridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        setToolBarName("签到");

        initView();
    }

    private void initView() {
        gv_checkImg = (GridView) findViewById(R.id.gv_checkImg);

        initData();

    }


    private void initData() {
        BmobQuery<CheckInImg> query = new BmobQuery<>();
        query.findObjects(this, new FindListener<CheckInImg>() {
            @Override
            public void onSuccess(List<CheckInImg> list) {
                adapter = new CheckInGridViewAdapter(CheckInActivity.this, list);
                gv_checkImg.setAdapter(adapter);
                onItemClick(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    private void onItemClick(final List<CheckInImg> list) {
        gv_checkImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String photoStr = list.get(position).getUrlStr();//图片地址
                Intent intent = new Intent(CheckInActivity.this, CheckInWriteActivity.class);
                intent.putExtra("photoUrl",photoStr);
                startActivity(intent);
            }
        });
    }
}
