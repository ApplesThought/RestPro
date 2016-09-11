package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.activity.R;
import com.example.entity.XiaoHuaInfo;

import java.util.List;

/**
 * Created by ${hcc} on 2016/07/26.
 */
public class HomeXiaoHuaAdapter extends BaseAdapter {
    private List<XiaoHuaInfo> list;
    private Context context;

    public HomeXiaoHuaAdapter(Context context, List<XiaoHuaInfo> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_xiaohua_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tv.setText(list.get(position).getContent());
        viewHolder.posTv.setText((position + 1) + "");
        return convertView;
    }


    private class ViewHolder {
        private TextView tv, tvTime, posTv;

        public ViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.tv);
            posTv = (TextView) view.findViewById(R.id.posTv);
        }
    }

    //屏蔽ListView点击的高亮显示
    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
