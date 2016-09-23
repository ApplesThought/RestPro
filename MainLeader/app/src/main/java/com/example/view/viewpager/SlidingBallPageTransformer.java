package com.example.view.viewpager;

import android.view.View;


/**
 * Created by hcc on 2016/8/15.
 */
public class SlidingBallPageTransformer implements com.example.view.viewpager.ViewPager.PageTransformer {
    private float mScale = 0.5f;//缩放比例

    private float mAlpha = 0.7f;//左右透明度

    private static float MIN_SCALE = 0.85f;
    private static float MIN_ALPHA = 0.5f;

    /**
     * 构造方法
     *
     * @param mScale 缩放比例
     * @param mAlpha 透明度
     */
    public SlidingBallPageTransformer(float mScale, float mAlpha) {
        this.MIN_ALPHA = mAlpha;
        this.MIN_SCALE = mScale;
    }

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) {
            // [-Infinity,-1) 此范围是停止滑动左边屏幕的部分
            view.setAlpha(0);
        } else if (position <= 1) {
            // [-1,1]  滑动过程中的设置view的缩放和通明度,a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            //设置通明度在最低mAlpha 到1的通明度
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        } else {
            // (1,+Infinity] 这个范围是停止滑动的右面部分
            view.setAlpha(0);
        }
    }
}
