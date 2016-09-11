package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.activity.R;
import com.example.entity.WeiXinInfo;

import java.util.List;

/**
 * Created by ${hcc} on 2016/07/26.
 */
public class HomeWeiXinAdapter extends BaseAdapter{
    private List<WeiXinInfo> list;
    private Context context;

    public HomeWeiXinAdapter(Context context, List<WeiXinInfo> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.home_lv_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        Glide.with(context).load(list.get(position).getFirstImg())
                .placeholder(R.mipmap.img_loading)
                .error(R.mipmap.img_error)
                .into(viewHolder.img);
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.author.setText(list.get(position).getSource());
        return convertView;
    }


    private class ViewHolder{
        private ImageView img;
        private TextView title,author;

        public ViewHolder(View view){
            img = (ImageView) view.findViewById(R.id.img);
            title = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
        }
    }
}
