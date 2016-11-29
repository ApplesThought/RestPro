package com.example.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.view.activity.R;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private ArrayList<Fragment> list;
    private final String[] mTitles = {
            "娱乐","电影","电视","游戏","国内",
            "趣图", "笑话", "互联网" , "汽车",
            "房产","社会","理财","女人","美容护肤",
            "情感两性","健康养生","数码","科普", "国际",
            "体育","军事","科技","教育", "财经","电脑"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_one, container, false);

        initList();

        initView(view);
        return view;
    }

    private void initView(View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        SlidingTabLayout stl = (SlidingTabLayout) view.findViewById(R.id.stl);
        stl.setViewPager(viewPager,mTitles,getActivity(),list);
        viewPager.setCurrentItem(0);
    }

    private void initList() {
        list = new ArrayList<>();
        list.add(new HomeYuLeFragment());
        list.add(new HomeMovieFragment());
        list.add(new HomeTVFragment());
        list.add(new HomeGameFragment());
        list.add(new GuoNeiFragment());
        list.add(new HomeQuTuFragment());
        list.add(new HomeXiaoHuaFragment());
        list.add(new HomeNetFragment());
        list.add(new HomeCarFragment());
        list.add(new HomeHouseFragment());
        list.add(new HomeSheHuiFragment());
        list.add(new HomeLiCaiFragment());
        list.add(new HomeWomenFragment());
        list.add(new HomeMeiRongFragment());
        list.add(new HomeEmotionFragment());
        list.add(new HomeHealthFragment());
        list.add(new HomeShuMaFragment());
        list.add(new HomeKePuFragment());
        list.add(new HomeGuoJiFragment());
        list.add(new HomeTiYuFragment());
        list.add(new HomeJunShiFragment());
        list.add(new HomeKeJiFragment());
        list.add(new HomeJiaoYuFragment());
        list.add(new HomeCaiJingFragment());
        list.add(new HomeComputerFragment());
    }
}
