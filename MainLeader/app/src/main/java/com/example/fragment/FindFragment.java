package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.activity.AllFuncActivity;
import com.example.activity.ArticleClassActivity;
import com.example.activity.CheckInActivity;
import com.example.activity.ChengyuDicActivity;
import com.example.activity.HealthFoodClassifyActivity;
import com.example.activity.LaughActivity;
import com.example.activity.LostAndFoundActivity;
import com.example.activity.PublishTalkActivity;
import com.example.activity.R;
import com.example.activity.ReadNoteActivity;
import com.example.activity.TieZiActivity;
import com.example.activity.XingzuoActivity;
import com.example.adapter.HomeGVDataAdapter;
import com.example.adapter.HomeGridViewAdapter;
import com.example.customview.MyGridView;
import com.example.utils.IntentUtils;
import com.youth.banner.Banner;


public class FindFragment extends Fragment {

//    private MyGridView gv_find;
//    private GridViewAdapter adapter;

    private HomeGridViewAdapter adapter;
    private HomeGVDataAdapter dataAdapter;
    private MyGridView gv_main, gv_main_data;
    private Banner banner;

    private RelativeLayout rel_funcBlock;//更多功能

    String[] images = new String[]{
            "http://img.zcool.cn/community/01ae5656e1427f6ac72531cb72bac5.jpg",
            "http://pic.90sjimg.com/back_pic/00/00/69/40/9ecb5c0b6dd4471000559917b2c56d58.jpg",
            "http://pic.90sjimg.com/back_pic/00/00/69/40/8b8ce207010c7134316aaf00181d8deb.jpg",
            "http://pic.90sjimg.com/back_pic/u/00/02/82/06/561b48885c54e.jpg"};

    String[] titles = new String[]{"十大星级品牌联盟，全场2折起", "全场2折起",
            "十大星级品牌联盟", "嗨购5折不要停"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_three, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {

        gv_main = (MyGridView) view.findViewById(R.id.gv_main);
        gv_main_data = (MyGridView) view.findViewById(R.id.gv_main_data);

        banner = (Banner) view.findViewById(R.id.banner);

        rel_funcBlock = (RelativeLayout) view.findViewById(R.id.rel_funcBlock);

        adapter = new HomeGridViewAdapter(getActivity());
        gv_main.setAdapter(adapter);

        dataAdapter = new HomeGVDataAdapter(getActivity());
        gv_main_data.setAdapter(dataAdapter);

        initBanner();

        initEvent();
    }


    private void initBanner() {
        banner.setBannerStyle(Banner.NUM_INDICATOR_TITLE);
        banner.setBannerTitle(titles);
        banner.setIndicatorGravity(Banner.CENTER);
        banner.setDelayTime(3000);//设置轮播间隔时间
        banner.setImages(images);//可以选择设置图片网址，或者资源文件，默认用Glide加载
        //自定义图片加载框架
        banner.setImages(images, new Banner.OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                Glide.with(getActivity()).load(url).into(view);
            }
        });
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {//设置点击事件
            @Override
            public void OnBannerClick(View view, int position) {
                Toast.makeText(getActivity(), "你点击了：" + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initEvent() {
        gv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        IntentUtils.toGoalAcrivity(getActivity(), TieZiActivity.class);
                        break;

                    case 1:
                        IntentUtils.toGoalAcrivity(getActivity(), ReadNoteActivity.class);
                        break;

                    case 2:
                        IntentUtils.toGoalAcrivity(getActivity(), LaughActivity.class);
                        break;
                    case 3:
                        IntentUtils.toGoalAcrivity(getActivity(), LaughActivity.class);
                        break;
                    case 4:
                        IntentUtils.toGoalAcrivity(getActivity(), LostAndFoundActivity.class);
                        break;
                    case 5:
                        IntentUtils.toGoalAcrivity(getActivity(), ArticleClassActivity.class);
                        break;
                    case 6:
                        IntentUtils.toGoalAcrivity(getActivity(), CheckInActivity.class);
                        break;
                    case 7:
                        Intent intent = new Intent(getActivity(), PublishTalkActivity.class);
                        intent.putExtra("type", "即兴说说");
                        startActivity(intent);
                        break;
                }
            }
        });

        rel_funcBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.toGoalAcrivity(getActivity(), AllFuncActivity.class);
            }
        });

        /*下方功能*/
        gv_main_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        IntentUtils.toGoalAcrivity(getActivity(), HealthFoodClassifyActivity.class);
                        break;
                    case 1:
                        IntentUtils.toGoalAcrivity(getActivity(), ChengyuDicActivity.class);
                        break;
                    case 2:
                        IntentUtils.toGoalAcrivity(getActivity(), XingzuoActivity.class);
                        break;
                }
            }
        });
    }


    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        banner.isAutoPlay(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.isAutoPlay(false);
    }

}
