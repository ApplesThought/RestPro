package com.example.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.view.activity.R;


/**
 * Created by ${hcc} on 2016/06/06.
 */
public class MineGridViewAdapter extends BaseAdapter {

    private int[] images = {R.mipmap.lost_and_found, R.mipmap.tabbar_compose,
            R.mipmap.shenfenzheng,R.mipmap.center_favour};

    private String[] texts = {"失物招领", "即兴说说","身份证查询","我的收藏"};
    private String[] intro = {"我的失物招领都在这", "我的说说在这里"
            ,"查询身份证信息","我的收藏都在这"};

    private Context context;

    private LayoutInflater inflater;

    public MineGridViewAdapter() {
    }

    public MineGridViewAdapter(Context context) {
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
            convertView = inflater.inflate(R.layout.minefra_gv_item, null);

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
