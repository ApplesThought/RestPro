package com.example.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvp.model.CheckInImg;
import com.example.view.activity.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ${hcc} on 2016/05/29.
 */
public class CheckInGridViewAdapter extends BaseAdapter {

    private List<CheckInImg> list;
    private Context context;
    private LayoutInflater inflater;

    public CheckInGridViewAdapter(Context context, List<CheckInImg> list) {
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
            convertView = inflater.inflate(R.layout.lv_checkin_img_item, null);

            holder.checkInImg = (ImageView) convertView.findViewById(R.id.checkInImg);
            holder.checkInTitle = (TextView) convertView.findViewById(R.id.checkInTitle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        String imgUrl = list.get(position).getUrlStr();
//        Picasso.with(context).setDebugging(true);//检测是从网络获取还是从内存
        Picasso.with(context).load(imgUrl)
                .placeholder(R.drawable.defalut)//占位的图片
                .error(R.drawable.load_error)//加载错误时的图片
                .resize(400, 250).centerCrop()//把图片裁小，达到更好的体验
                .into(holder.checkInImg);
        holder.checkInTitle.setText(list.get(position).getPhotoTitle());


        return convertView;
    }


    static class ViewHolder {
        ImageView checkInImg;
        TextView checkInTitle;
    }
}
