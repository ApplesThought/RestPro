package com.example.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.fragment.NoteAfterFragment;
import com.example.fragment.NoteAllFragment;
import com.example.fragment.NoteDataDealFragment;
import com.example.fragment.NoteFrontFragment;
import com.example.fragment.NoteGraphDealFragment;
import com.example.fragment.NoteMobileFragment;
import com.example.fragment.NoteOtherFragment;
import com.example.utils.IntentUtils;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

public class ReadNoteActivity extends KLBaseActivity {

    private ArrayList<Fragment> list;

    private final String[] mTitles = {"全部分类", "移动开发", "前端开发"
            , "后端开发", "数据处理", "图像处理", "其他笔记" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_note);

        setToolBarName("读书笔记");

        initList();
        initView();
    }

    private void initList() {
        list = new ArrayList<>();
        list.add(new NoteAllFragment());
        list.add(new NoteMobileFragment());
        list.add(new NoteFrontFragment());
        list.add(new NoteAfterFragment());
        list.add(new NoteDataDealFragment());
        list.add(new NoteGraphDealFragment());
        list.add(new NoteOtherFragment());
    }

    private void initView() {
        /*点击ToolBar上的图标跳转*/
        addNew(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.toGoalAcrivity(ReadNoteActivity.this, WriteNoteActivity.class);
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        SlidingTabLayout stl = (SlidingTabLayout) findViewById(R.id.tl);
        stl.setViewPager(viewPager,mTitles,this,list);
        viewPager.setCurrentItem(0);
    }

}
