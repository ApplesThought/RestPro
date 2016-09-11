package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.activity.R;

/**
 * Created by ${hcc} on 2016/06/06.
 */
public class MoreFuncAdapter extends BaseAdapter {

    private int[] images = {R.mipmap.shoujihaoma, R.mipmap.shenfenzheng,
            R.mipmap.ip,R.mipmap.bankcard,R.mipmap.circle_food,R.drawable.meinv,
            R.mipmap.chengyu,R.mipmap.chengyu,R.mipmap.wannianli,
            R.mipmap.zhougongjiemeng,R.mipmap.lishi,R.mipmap.xingzuoyunshi,
            R.mipmap.tianqiyubao};

    private String[] texts = {"手机号码归属地", "身份证查询","IP地址",
                            "银行卡查询","健康菜谱","福利图区","成语词典","新华字典","老黄历",
                            "周公解梦","历史上的今天","星座运势","天气预报"};
    private String[] intro = {"根据手机号或前7位，号码归属地信息查询","查询身份证信息",
            "根据查询的IP地址或者域名，查询该IP所属的区域","根据银行卡查询银行信息",
            "享受美食带来的快乐","最美的生活，最美的美女，为生活添加色彩",
            "最大最全的新华汉语词典，按拼音查、按部首查、按笔画查",
            "新华字典在线查字,最新最全","黄历每日吉凶宜忌查询","周公解梦",
            "回顾历史的长河，历史是生活的一面镜子","十二星座每日、每月、每年运势",
            "全国天气预报，生活指数、实况、PM2.5等信息"};

    private Context context;

    private LayoutInflater inflater;

    public MoreFuncAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.minefra_gv_data_item, null);

            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.intro = (TextView) convertView.findViewById(R.id.intro);
            holder.name = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.img.setBackgroundResource(images[position]);

        holder.name.setText(texts[position]);
        holder.intro.setText(intro[position]);
        return convertView;
    }


    static class ViewHolder {
        ImageView img;
        TextView intro,name;
    }
}
