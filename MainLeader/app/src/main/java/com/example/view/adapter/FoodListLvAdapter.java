package com.example.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mvp.model.FoodList;
import com.example.view.activity.R;

import java.util.List;

/**
 * Created by ${hcc} on 2016/05/29.
 */
public class FoodListLvAdapter extends BaseAdapter{

    private List<FoodList> list;
    private Context context;
    private LayoutInflater inflater;

    public FoodListLvAdapter(Context context, List<FoodList> list) {
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
            convertView = inflater.inflate(R.layout.food_list_item,null);

            holder.foodTv = (TextView) convertView.findViewById(R.id.foodTv);
            holder.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
            holder.foodListImg = (ImageView) convertView.findViewById(R.id.foodListImg);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == list.size() - 1){

        }

        holder.foodTv.setText(list.get(position).getFood());
        holder.nameTv.setText(list.get(position).getName());
        holder.foodListImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(list.get(position).getImgUrl())
                .placeholder(R.mipmap.img_loading)
                .error(R.mipmap.img_error)
                .into(holder.foodListImg);

        return convertView;
    }


    static class ViewHolder{
        TextView foodTv,nameTv;
        ImageView foodListImg;
    }
}
