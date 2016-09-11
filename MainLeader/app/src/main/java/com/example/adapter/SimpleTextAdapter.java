package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.activity.R;

import java.util.List;

/**
 * Created by ${hcc} on 2016/09/10.
 */
public class SimpleTextAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    public SimpleTextAdapter(Context context, List<String> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.cy_item,null);
            holder.wordTv = (TextView) convertView.findViewById(R.id.wordTv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.wordTv.setText(list.get(position).toString());

        return convertView;
    }

    static class ViewHolder{
        TextView wordTv;
    }
}
