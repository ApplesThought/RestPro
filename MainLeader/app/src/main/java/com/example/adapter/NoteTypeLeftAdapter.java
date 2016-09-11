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
 * Created by ${hcc} on 2016/06/06.
 */
public class NoteTypeLeftAdapter extends BaseAdapter {

    private String[] texts = {"全部笔记","移动开发", "前端开发", "后端开发", "数据处理", "图像处理",
                            "其他笔记"};
    private List list;

    private Context context;

    private LayoutInflater inflater;

    public NoteTypeLeftAdapter() {
    }

    public NoteTypeLeftAdapter(Context context,List list) {
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
            convertView = inflater.inflate(R.layout.lv_notetype_item, null);

            holder.noteType = (TextView) convertView.findViewById(R.id.noteType);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.noteType.setText(list.get(position).toString());
        return convertView;
    }


    static class ViewHolder {
        TextView noteType;
    }

}
