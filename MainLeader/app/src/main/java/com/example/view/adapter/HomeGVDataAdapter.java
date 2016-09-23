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
public class HomeGVDataAdapter extends BaseAdapter {

    private int[] images = {R.mipmap.circle_food, R.mipmap.chengyu,
            R.mipmap.xingzuoyunshi};

    private String[] texts = {"健康菜谱", "成语词典", "星座运势"};
    private String[] intro = {"享受美食带来的快乐", "最大最全的新华汉语词典，按拼音查、按部首查、按笔画查",
            "十二星座每日、每月、每年运势"};

    private Context context;

    private LayoutInflater inflater;

    public HomeGVDataAdapter() {
    }

    public HomeGVDataAdapter(Context context) {
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
        TextView intro, name;
    }
}
