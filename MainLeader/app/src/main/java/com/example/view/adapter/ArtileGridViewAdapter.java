package com.example.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.view.activity.R;


/**
 * Created by ${hcc} on 2016/06/06.
 */
public class ArtileGridViewAdapter extends BaseAdapter {

    private int[] images = {R.mipmap.d_nanhaier, R.mipmap.d_nvhaier,R.mipmap.d_keai,
            R.mipmap.d_shayan,R.mipmap.d_taikaixin,R.mipmap.d_ganmao,R.mipmap.d_chanzui,
            R.mipmap.d_touxiao,R.mipmap.d_aini,R.mipmap.d_landelini};

    private String[] texts = {"爱情文章", "亲情文章", "友情文章", "生活随笔", "校园文章",
            "人生哲理", "励志文章", "搞笑文章", "程序文章", "英语文章"};

    private Context context;

    private LayoutInflater inflater;

    public ArtileGridViewAdapter() {
    }

    public ArtileGridViewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.length;
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
            convertView = inflater.inflate(R.layout.article_gridview_item, null);

            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.intro = (TextView) convertView.findViewById(R.id.intro);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setBackgroundResource(images[position]);

        holder.intro.setText(texts[position]);
        return convertView;
    }


    static class ViewHolder {
        ImageView icon;
        TextView intro;
    }
}
