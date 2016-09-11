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
public class GridViewAdapter extends BaseAdapter {

    private int[] images = {R.mipmap.dianyingpiaofang, R.mipmap.mianfeiwifi,
            R.mipmap.lishi,R.mipmap.nba,R.mipmap.tianqiyubao,
            R.mipmap.tushu,R.mipmap.wannianli,R.mipmap.weixinjingxuan,
            R.mipmap.xingzuoyunshi,R.mipmap.yingshiyingxun,R.mipmap.zhougongjiemeng};

    private String[] texts = {"电影票房", "免费WIFI", "历史的今天", "NBA赛事",
            "天气预报","好书发现","万年历","微信精选","星座运势","影视影讯","周公解梦"};

    private String[] intro = {"电影票房", "免费WIFI", "历史上的今天", "NBA赛事",
            "天气预报","好书发现","万年历","微信精选","星座运势","影视影讯","周公解梦"};

    private Context context;

    private LayoutInflater inflater;

    public GridViewAdapter() {
    }

    public GridViewAdapter(Context context) {
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

            holder.icon = (ImageView) convertView.findViewById(R.id.img);
            holder.intro = (TextView) convertView.findViewById(R.id.intro);
            holder.name = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setBackgroundResource(images[position]);
        holder.name.setText(texts[position]);
        holder.intro.setText(intro[position]);
        return convertView;
    }


    static class ViewHolder {
        ImageView icon;
        TextView intro,name;
    }
}
