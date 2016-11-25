package com.example.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.view.activity.R;


/**
 * Created by hcc on 2016/03/20.
 */
public class ToolBarHelper {
    private Context mContext;
    /*base view*/
    private FrameLayout mContentView;
    /*自定义的View*/
    private View mUserView;
    private Toolbar mToolBar;
    /*视图构造器*/
    private LayoutInflater mInflater;

    /**
     * 两个属性：
     * 1. toolbar是否悬浮在窗口之上
     * 2. toolbar的高度获取
     */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            45
    };

    public ToolBarHelper(Context context, int layoutId){
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        /*初始化整个布局*/
        initContentView();
        /*初始化用户自定义的布局*/
        initUserView(layoutId);
        /*初始化Toolbar*/
        initToolbar();
    }

    private void initToolbar() {
        View toolBar = mInflater.inflate(R.layout.toolbar,mContentView);
        mToolBar = (Toolbar) toolBar.findViewById(R.id.toolbar);
    }

    private void initUserView(int layoutId) {
        mUserView = mInflater.inflate(layoutId,null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中定义的悬浮标识*/
        boolean overly = typedArray.getBoolean(0,false);
        /*获取主题中定义的toolbar的高度*/
        int toolBarSize = (int) typedArray.getDimension(1,
                mContext.getResources().getDimension(
                        R.dimen.actionbar_height));
        typedArray.recycle();
        /*如果是悬浮窗，则不需要设置间距*/
        params.topMargin = overly ? 0 : toolBarSize;
        mContentView.addView(mUserView, params);
    }


    private void initContentView() {
        /*直接创建一个帧布局，作为视图容器的父容器*/
        mContentView = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    public FrameLayout getContentView() {
        return mContentView;
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }
}
