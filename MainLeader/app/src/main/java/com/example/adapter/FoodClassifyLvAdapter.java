package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.activity.R;
import com.example.customview.CircleTextImageView;
import com.example.entity.FoodClassify;

import java.util.List;
import java.util.Random;

/**
 * Created by ${hcc} on 2016/09/05.
 */
public class FoodClassifyLvAdapter extends BaseAdapter {

    private List<FoodClassify> list;
    private Context context;
    private LayoutInflater inflater;
    private int[] resId = {R.drawable.huang1, R.drawable.hui, R.drawable.lan,
            R.drawable.lan2, R.drawable.lv, R.drawable.qianlan,
            R.drawable.qianlv, R.drawable.shenhuang, R.drawable.zi};

    public FoodClassifyLvAdapter(Context context, List<FoodClassify> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.food_classify_item, null);

            holder.foodImg = (CircleTextImageView) convertView.findViewById(R.id.foodImg);
            holder.titleTv = (TextView) convertView.findViewById(R.id.titleTv);
            holder.describeTv = (TextView) convertView.findViewById(R.id.describeTv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleTv.setText(list.get(position).getTitle());
        holder.describeTv.setText(list.get(position).getDescription());

        Random random = new Random();
        int r = random.nextInt(resId.length);
        holder.foodImg.setImageResource(resId[r]);
        holder.foodImg.setText(list.get(position).getTitle().substring(0, 1));
        return convertView;
    }

    static class ViewHolder {
        CircleTextImageView foodImg;
        TextView titleTv, describeTv;
    }
}
