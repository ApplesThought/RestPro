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
public class HomeGridViewAdapter extends BaseAdapter {

    private int[] images = {R.mipmap.main_xiaoxi,R.mipmap.people_mynote_icon, R.mipmap.d_xixi,
            R.mipmap.f_jiong,R.mipmap.lost_and_found,R.mipmap.people_myarticle_icon,
            R.mipmap.dailycheckin,R.mipmap.tabbar_compose};

    private String[] texts = {"帖子文章","读书笔记", "搞笑段子", "情感天地", "失物招领", "精美文章",
                            "每日签到","即兴说说"};

    private Context context;

    private LayoutInflater inflater;

    public HomeGridViewAdapter() {
    }

    public HomeGridViewAdapter(Context context) {
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
            convertView = inflater.inflate(R.layout.gv_home_item, null);

            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.intro = (TextView) convertView.findViewById(R.id.intro);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setBackgroundResource(images[position]);

        holder.intro.setText(texts[position]);
        return convertView;
    }


    static class ViewHolder {
        ImageView icon;
        TextView intro;
    }
}
