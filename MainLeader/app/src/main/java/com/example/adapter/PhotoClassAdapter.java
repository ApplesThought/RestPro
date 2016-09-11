package com.example.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.activity.R;
import com.example.entity.PhotoClassInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${hcc} on 2016/09/10.
 */
public class PhotoClassAdapter extends BaseAdapter {
    private List<PhotoClassInfo> data = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int curPositon;

    public void setCurPositon(int curPositon) {
        this.curPositon = curPositon;
    }

    public int getCurPositon() {
        return curPositon;
    }

    public PhotoClassAdapter(Context context, List<PhotoClassInfo> data) {
        mContext = context;
        this.data = data;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.photo_classify_item, null);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv.setText(data.get(position).getTitle());
        if (position == curPositon) {
            convertView.setBackgroundColor(Color.TRANSPARENT);
            vh.tv.setTextColor(Color.RED);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
            vh.tv.setTextColor(Color.BLACK);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv;
    }
}
