package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.adapter.FoundLvAdapter;
import com.example.adapter.LostLvAdapter;
import com.example.customview.LoadingDialog;
import com.example.entity.Found;
import com.example.entity.Lost;
import com.example.entity.MyUser;
import com.example.utils.SortComparator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class MyLostAndFoundActivity extends KLBaseActivity {

    private ListView myLostAndFoundLV;
    private int FLAG = 0;//区分是Lost还是Found
    private LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lost_and_found);

        setLostAndFoundVisiable();

        initView();
    }


    private void initView() {

        myLostAndFoundLV = (ListView) findViewById(R.id.myLostAndFoundLV);

        initEvent();
    }


    private void initEvent() {
        dialog = new LoadingDialog(this);
        lostClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG = 0;
                setLostSelect();
                getLostData();
            }
        });


        foundClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG = 1;
                setFoundSelect();
                getFoundData();
            }
        });


        addNew(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLostAndFoundActivity.this, AddLostOrFoundActivity.class);
                intent.putExtra("FLAG", FLAG);
                startActivity(intent);
            }
        });
    }


    private void getLostData(){
        if (!isFinishing()) {
            dialog.show();
        }
        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        String objectId = user.getObjectId();
        BmobQuery<MyUser> queryInner = new BmobQuery<>();
        String[] id = {objectId};
        queryInner.addWhereContainedIn("objectId", Arrays.asList(id));

        BmobQuery<Lost> query = new BmobQuery<>();
        query.addWhereMatchesQuery("username", "_User", queryInner);
        query.include("username");
        query.findObjects(this, new FindListener<Lost>() {
            @Override
            public void onSuccess(List<Lost> list) {
                LostLvAdapter adapter = new LostLvAdapter(MyLostAndFoundActivity.this, list);
                Comparator comparator = new SortComparator();
                Collections.sort(list, comparator);
                myLostAndFoundLV.setAdapter(adapter);
                dialog.dismiss();

                onLostItemClick(list);
            }

            @Override
            public void onError(int i, String s) {
                dialog.dismiss();
            }
        });
    }


    /*查询招领信息*/
    private void getFoundData(){
        if (!isFinishing()) {
            dialog.show();
        }

        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        String objectId = user.getObjectId();
        BmobQuery<MyUser> queryInner = new BmobQuery<>();
        String[] id = {objectId};
        queryInner.addWhereContainedIn("objectId", Arrays.asList(id));

        BmobQuery<Found> query = new BmobQuery<>();
        query.addWhereMatchesQuery("username", "_User", queryInner);
        query.include("username");
        query.findObjects(this, new FindListener<Found>() {
            @Override
            public void onSuccess(List<Found> list) {
                FoundLvAdapter adapter = new FoundLvAdapter(MyLostAndFoundActivity.this, list);
                Comparator comparator = new SortComparator();
                Collections.sort(list, comparator);
                myLostAndFoundLV.setAdapter(adapter);
                dialog.dismiss();

                onFoundItemClick(list);
            }

            @Override
            public void onError(int i, String s) {
                dialog.dismiss();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (FLAG == 0) {
            getLostData();
        } else {
            getFoundData();
        }
    }


    /*失物*/
    private void onLostItemClick(final List<Lost> list) {
        myLostAndFoundLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String objectId = list.get(position).getObjectId();
                Intent intent = new Intent(MyLostAndFoundActivity.this, LostAndFoundDetailActivity.class);
                intent.putExtra("FLAG", FLAG);
                intent.putExtra("objectId",objectId);
                startActivity(intent);
            }
        });
    }

    /*招领*/
    private void onFoundItemClick(final List<Found> list) {
        myLostAndFoundLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String objectId = list.get(position).getObjectId();
                Intent intent = new Intent(MyLostAndFoundActivity.this, LostAndFoundDetailActivity.class);
                intent.putExtra("FLAG", FLAG);
                intent.putExtra("objectId",objectId);
                startActivity(intent);
            }
        });
    }
}
