package com.example.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ccinterface.IAlertDialogListener;
import com.example.common.AppConstants;
import com.example.customview.CustomDialog;
import com.example.entity.MyUser;
import com.example.fragment.FindFragment;
import com.example.fragment.HomeFragment;
import com.example.fragment.MessFragment;
import com.example.fragment.MineFragment;
import com.example.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, ViewPager.OnPageChangeListener {

    // 底部菜单4个Linearlayout
    private LinearLayout ll_main_page;
    private LinearLayout ll_main_mess;
    private LinearLayout ll_main_find;
    private LinearLayout ll_main_mine;

    // 底部菜单4个ImageView
    private ImageView img_main;
    private ImageView img_mess;
    private ImageView img_find;
    private ImageView img_mine;

    // 底部菜单4个菜单标题
    private TextView txt_main;
    private TextView txt_mess;
    private TextView txt_find;
    private TextView txt_mine;

    private TextView title_txt;

    // 中间内容区域
    private ViewPager viewPager;

    private FragmentPagerAdapter adapter;
    private List<Fragment> views;

    private Button exitBtn;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /*设置状态栏颜色*/
        getWindow().setStatusBarColor(Color.parseColor("#FF8000"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // 初始化控件
        initView();
        // 初始化底部按钮事件
        initEvent();
        // 初始化并设置当前Fragment
    }


    private void initEvent() {
        // 设置按钮监听
        ll_main_page.setOnClickListener(this);
        ll_main_mess.setOnClickListener(this);
        ll_main_find.setOnClickListener(this);
        ll_main_mine.setOnClickListener(this);

        //设置ViewPager滑动监听
        viewPager.setOnPageChangeListener(this);
        /*设置ViewPager预加载的页数*/
        viewPager.setOffscreenPageLimit(3);
    }

    private void initView() {
        title_txt = (TextView) findViewById(R.id.title_txt);

        // 底部菜单4个Linearlayout
        this.ll_main_page = (LinearLayout) findViewById(R.id.ll_main_page);
        this.ll_main_mess = (LinearLayout) findViewById(R.id.ll_main_mess);
        this.ll_main_find = (LinearLayout) findViewById(R.id.ll_main_find);
        this.ll_main_mine = (LinearLayout) findViewById(R.id.ll_main_mine);

        // 底部菜单4个ImageView
        this.img_main = (ImageView) findViewById(R.id.img_main);
        this.img_mess = (ImageView) findViewById(R.id.img_mess);
        this.img_find = (ImageView) findViewById(R.id.img_find);
        this.img_mine = (ImageView) findViewById(R.id.img_mine);

        // 底部菜单4个菜单标题
        this.txt_main = (TextView) findViewById(R.id.txt_main);
        this.txt_mess = (TextView) findViewById(R.id.txt_mess);
        this.txt_find = (TextView) findViewById(R.id.txt_find);
        this.txt_mine = (TextView) findViewById(R.id.txt_mine);

        exitBtn = (Button) findViewById(R.id.exitBtn);

        // 中间内容区域ViewPager
        viewPager = (ViewPager) findViewById(R.id.vp_content);

        HomeFragment home = new HomeFragment();
        MessFragment mess = new MessFragment();
        FindFragment find = new FindFragment();
        MineFragment mine = new MineFragment();

        views = new ArrayList<>();
        views.add(home);
        views.add(mess);
        views.add(find);
        views.add(mine);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public Fragment getItem(int position) {
                return views.get(position);
            }
        };
        viewPager.setAdapter(adapter);


        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*确认退出*/
                confirmExit();
            }
        });
    }

    private void confirmExit() {
        CustomDialog.showOkOrCancleDialog(this, "确定退出登录？", new IAlertDialogListener() {
            @Override
            public void onClick() {
                MyUser.logOut(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                SharedPreferences preferences;
                preferences = getSharedPreferences(AppConstants.PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                boolean isLogin = false;//登录后标识变为true，用作判断自动登录
                editor.putBoolean(AppConstants.ISLOGIN, isLogin);
                editor.commit();
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            CustomDialog.showOkOrCancleDialog(this, "确定退出？", new IAlertDialogListener() {
                @Override
                public void onClick() {
                    finish();
                }
            });
        }

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            IntentUtils.toGoalAcrivity(this, FLPhotoActivity.class);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_main_page:
                img_main.setImageResource(R.mipmap.tabbar_home_highlighted);
                txt_main.setTextColor(Color.parseColor("#FF8000"));
                img_mess.setImageResource(R.mipmap.tabbar_message_center);
                txt_mess.setTextColor(Color.parseColor("#000000"));
                img_find.setImageResource(R.mipmap.tabbar_discover);
                txt_find.setTextColor(Color.parseColor("#000000"));
                img_mine.setImageResource(R.mipmap.tabbar_profile);
                txt_mine.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(0);
                title_txt.setText("首页");
                break;
            case R.id.ll_main_mess:
                img_mess.setImageResource(R.mipmap.tabbar_message_center_highlighted);
                txt_mess.setTextColor(Color.parseColor("#FF8000"));
                img_main.setImageResource(R.mipmap.tabbar_home);
                txt_main.setTextColor(Color.parseColor("#000000"));
                img_find.setImageResource(R.mipmap.tabbar_discover);
                txt_find.setTextColor(Color.parseColor("#000000"));
                img_mine.setImageResource(R.mipmap.tabbar_profile);
                txt_mine.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(1);
                title_txt.setText("消息");
                break;
            case R.id.ll_main_find:
                img_find.setImageResource(R.mipmap.tabbar_discover_highlighted);
                txt_find.setTextColor(Color.parseColor("#FF8000"));
                img_main.setImageResource(R.mipmap.tabbar_home);
                txt_main.setTextColor(Color.parseColor("#000000"));
                img_mess.setImageResource(R.mipmap.tabbar_message_center);
                txt_mess.setTextColor(Color.parseColor("#000000"));
                img_mine.setImageResource(R.mipmap.tabbar_profile);
                txt_mine.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(2);
                title_txt.setText("发现");
                break;
            case R.id.ll_main_mine:
                img_mine.setImageResource(R.mipmap.tabbar_profile_highlighted);
                txt_mine.setTextColor(Color.parseColor("#FF8000"));
                img_main.setImageResource(R.mipmap.tabbar_home);
                txt_main.setTextColor(Color.parseColor("#000000"));
                img_mess.setImageResource(R.mipmap.tabbar_message_center);
                txt_mess.setTextColor(Color.parseColor("#000000"));
                img_find.setImageResource(R.mipmap.tabbar_discover);
                txt_find.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(3);
                title_txt.setText("我的");
                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //当前view被选择的时候,改变底部菜单图片，文字颜色
        switch (position) {
            case 0:
                img_main.setImageResource(R.mipmap.tabbar_home_highlighted);
                txt_main.setTextColor(Color.parseColor("#FF8000"));
                img_mess.setImageResource(R.mipmap.tabbar_message_center);
                txt_mess.setTextColor(Color.parseColor("#000000"));
                img_find.setImageResource(R.mipmap.tabbar_discover);
                txt_find.setTextColor(Color.parseColor("#000000"));
                img_mine.setImageResource(R.mipmap.tabbar_profile);
                txt_mine.setTextColor(Color.parseColor("#000000"));
                title_txt.setText("首页");
                break;
            case 1:
                img_mess.setImageResource(R.mipmap.tabbar_message_center_highlighted);
                txt_mess.setTextColor(Color.parseColor("#FF8000"));
                img_main.setImageResource(R.mipmap.tabbar_home);
                txt_main.setTextColor(Color.parseColor("#000000"));
                img_find.setImageResource(R.mipmap.tabbar_discover);
                txt_find.setTextColor(Color.parseColor("#000000"));
                img_mine.setImageResource(R.mipmap.tabbar_profile);
                txt_mine.setTextColor(Color.parseColor("#000000"));
                title_txt.setText("消息");
                break;
            case 2:
                img_find.setImageResource(R.mipmap.tabbar_discover_highlighted);
                txt_find.setTextColor(Color.parseColor("#FF8000"));
                img_main.setImageResource(R.mipmap.tabbar_home);
                txt_main.setTextColor(Color.parseColor("#000000"));
                img_mess.setImageResource(R.mipmap.tabbar_message_center);
                txt_mess.setTextColor(Color.parseColor("#000000"));
                img_mine.setImageResource(R.mipmap.tabbar_profile);
                txt_mine.setTextColor(Color.parseColor("#000000"));
                title_txt.setText("发现");
                break;
            case 3:
                img_mine.setImageResource(R.mipmap.tabbar_profile_highlighted);
                txt_mine.setTextColor(Color.parseColor("#FF8000"));
                img_main.setImageResource(R.mipmap.tabbar_home);
                txt_main.setTextColor(Color.parseColor("#000000"));
                img_mess.setImageResource(R.mipmap.tabbar_message_center);
                txt_mess.setTextColor(Color.parseColor("#000000"));
                img_find.setImageResource(R.mipmap.tabbar_discover);
                txt_find.setTextColor(Color.parseColor("#000000"));
                title_txt.setText("我的");
                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
