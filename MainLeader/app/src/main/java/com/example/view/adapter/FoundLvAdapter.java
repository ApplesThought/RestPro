package com.example.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mvp.model.Found;
import com.example.view.activity.R;

import java.util.List;

/**
 * Created by ${hcc} on 2016/05/29.
 */
public class FoundLvAdapter extends BaseAdapter{

    private List<Found> list;
    private Context context;
    private LayoutInflater inflater;

    public FoundLvAdapter(Context context, List<Found> list) {
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
            convertView = inflater.inflate(R.layout.lv_lost_found_item,null);

            holder.isRuningTxt = (TextView) convertView.findViewById(R.id.isRuningTxt);
            holder.titleTxt = (TextView) convertView.findViewById(R.id.titleTxt);
            holder.describeTxt = (TextView) convertView.findViewById(R.id.describeTxt);
            holder.timeTxt = (TextView) convertView.findViewById(R.id.timeTxt);
            holder.phoneTxt = (TextView) convertView.findViewById(R.id.phoneTxt);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleTxt.setText(list.get(position).getFoundTitle());
        holder.describeTxt.setText(list.get(position).getFoundDescribe());
        holder.timeTxt.setText(list.get(position).getUpdatedAt());
        holder.phoneTxt.setText(list.get(position).getFoundPhone());
        holder.isRuningTxt.setText(list.get(position).getIsRuning());

        return convertView;
    }


    static class ViewHolder{
        TextView isRuningTxt,titleTxt,describeTxt,timeTxt,phoneTxt;
    }
}
