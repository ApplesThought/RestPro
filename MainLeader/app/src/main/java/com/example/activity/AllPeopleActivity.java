package com.example.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.customview.LoadingDialog;
import com.example.entity.MyUser;
import com.example.viewpager.MyViewpagerAdapter;
import com.example.viewpager.SlidingBallPageTransformer;
import com.example.viewpager.SlidingBallViewPager;
import com.example.viewpager.ViewPager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class AllPeopleActivity extends KLBaseActivity {

//    private RecyclerView gv_allPeople;
//    private PhotoDetailAdapter adapter;

    private LoadingDialog dialog;
    private SlidingBallViewPager viewPager;
    private MyViewpagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_people);

        setToolBarName("所有人");

        initView();
    }

    private void initView() {
//        gv_allPeople = (RecyclerView) findViewById(R.id.gv_allPeople);
//
//        gv_allPeople.setLayoutManager(
//                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        viewPager = (SlidingBallViewPager) findViewById(R.id.viewPager);
        initData();
        initListener();
    }

    private void initData() {
        dialog = new LoadingDialog(this);
        if (!isFinishing()) {
            dialog.show();
        }
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.findObjects(this, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
//                adapter = new PhotoDetailAdapter(AllPeopleActivity.this, list);
//                gv_allPeople.setAdapter(adapter);

                /*设置间距*/
//                SpacesItemDecoration decoration=new SpacesItemDecoration(5);
//                gv_allPeople.addItemDecoration(decoration);
                adapter = new MyViewpagerAdapter(AllPeopleActivity.this, list);
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(4);

                viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
                viewPager.setPageTransformer(true, new SlidingBallPageTransformer(0.8f, 0.6f));
                dialog.dismiss();
                onItemClick(list);
            }

            @Override
            public void onError(int i, String s) {
                toast("用户获取失败");
                dialog.dismiss();
            }
        });
    }

    private void onItemClick(final List<MyUser> list) {
        adapter.setOnItemClickListener(new MyViewpagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(AllPeopleActivity.this, PeopleInfoActivity.class);
                String objectId = list.get(position).getObjectId();
                intent.putExtra("objectId", objectId);
                startActivity(intent);
            }
        });
    }


    private void initListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.removeAllViews();
    }
}
