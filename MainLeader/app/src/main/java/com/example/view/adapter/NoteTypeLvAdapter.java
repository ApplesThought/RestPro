package com.example.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.view.activity.R;

import java.util.List;

/**
 * Created by ${hcc} on 2016/05/29.
 */
public class NoteTypeLvAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;
    private List list;

//    private String[] types = {"全部","移动开发","前端开发","后端开发","数据处理","图像处理","其他"};

    public NoteTypeLvAdapter(Context context, List list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
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
            convertView = inflater.inflate(R.layout.lv_notetype_item,null);

            holder.noteType = (TextView) convertView.findViewById(R.id.noteType);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.noteType.setText(list.get(position).toString());

        return convertView;
    }


    static class ViewHolder{
        TextView noteType;
    }
}
