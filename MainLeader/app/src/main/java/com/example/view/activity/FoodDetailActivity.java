package com.example.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.error.VolleyError;
import com.bumptech.glide.Glide;
import com.example.application.AppUrl;
import com.example.ccinterface.RequestListener;
import com.example.manager.RequestManager;
import com.example.utils.DialogUtils;
import com.example.utils.IntentUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FoodDetailActivity extends KLBaseActivity {

    private int foodId;
    private TextView nameTv, keyWordTv, describeTv, resTv, howTv;
    private TextView cloudTv;//FloatingActionButton打开时显示蒙版
    private ImageView detailImg;
    private FloatingActionButton fab;
    private boolean fabOpend;
    private LinearLayout ll_refresh, ll_other;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        foodId = getIntent().getExtras().getInt("id");
        title = getIntent().getExtras().getString("title");
        setToolBarName(title);

        initView();
    }

    private void initView() {
        detailImg = (ImageView) findViewById(R.id.detailImg);
        nameTv = (TextView) findViewById(R.id.nameTv);
        keyWordTv = (TextView) findViewById(R.id.keyWordTv);
        describeTv = (TextView) findViewById(R.id.describeTv);
        resTv = (TextView) findViewById(R.id.resTv);
        howTv = (TextView) findViewById(R.id.howTv);
        cloudTv = (TextView) findViewById(R.id.cloudTv);
        ll_refresh = (LinearLayout) findViewById(R.id.ll_refresh);
        ll_other = (LinearLayout) findViewById(R.id.ll_other);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        getData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fabOpend) {
                    openMenu(fab);
                } else {
                    closeMenu(fab);
                }
            }
        });

        cloudTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu(fab);
            }
        });

        /*刷新*/
        ll_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu(fab);
                getData();
            }
        });

        /*其他*/
        ll_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu(fab);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                IntentUtils.toGoalAcrivityWithBundle(v.getContext(), OtherFoodListActivity.class, bundle);
            }
        });
    }

    private void getData() {
        DialogUtils.getInstance().showProgressDialog(this, "拼命加载中");
        Map<String, Object> params = new HashMap<>();
        params.put("id", foodId);
        RequestManager.getRequestManager().dealDataByGetWithToken(this, AppUrl.foodDetailBaseUrl, params, new RequestListener() {
            @Override
            public void dealDataSuccess(JSONObject jsonObject) {
                DialogUtils.getInstance().dimissDialog();
                String description = jsonObject.optString("description");
                String food = jsonObject.optString("food");
                String img = jsonObject.optString("img");
                String imgUrl = AppUrl.imgBaseUrl + img;
                String keywords = jsonObject.optString("keywords");
                String message = jsonObject.optString("message");
                String name = jsonObject.optString("name");

                Spanned spanned = Html.fromHtml(message);
                describeTv.setText(description);
                resTv.setText(food);
                keyWordTv.setText(keywords);
                howTv.setText(spanned.toString());
                nameTv.setText(name);

                detailImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(FoodDetailActivity.this).load(imgUrl)
                        .placeholder(R.mipmap.img_loading)
                        .error(R.mipmap.img_error)
                        .into(detailImg);
            }

            @Override
            public void dealDataFail(VolleyError volleyError) {
                DialogUtils.getInstance().dimissDialog();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void openMenu(View view) {
        ObjectAnimator animator = ObjectAnimator.ofArgb(view, "rotation", 0, -155, -135);
        animator.setDuration(100);
        animator.start();
        cloudTv.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 0.7f);
        alphaAnimation.setDuration(100);
        alphaAnimation.setFillAfter(true);
        ll_other.setVisibility(View.VISIBLE);
        ll_refresh.setVisibility(View.VISIBLE);
        cloudTv.startAnimation(alphaAnimation);
        fabOpend = true;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void closeMenu(View view) {
        ObjectAnimator animator = ObjectAnimator.ofArgb(view, "rotation", -135, 20, 0);
        animator.setDuration(100);
        animator.start();
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.7f, 0);
        alphaAnimation.setDuration(100);
        ll_other.setVisibility(View.GONE);
        ll_refresh.setVisibility(View.GONE);
        cloudTv.startAnimation(alphaAnimation);
        cloudTv.setVisibility(View.GONE);
        fabOpend = false;
    }
}
