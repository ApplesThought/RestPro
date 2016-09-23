package com.example.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mvp.model.BaiduInfo;
import com.example.view.activity.R;

import java.util.List;

/**
 * Created by ${hcc} on 2016/07/26.
 */
public class BaiduHomeAdapter extends BaseAdapter {
    private List<BaiduInfo> list;
    private Context context;

    public BaiduHomeAdapter(Context context, List<BaiduInfo> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.baidu_lv_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.title.setText(list.get(position).getTitle());
        int imgSize = list.get(position).getImageurls().size();
        if (imgSize == 0) {
            viewHolder.llimg.removeAllViews();
            viewHolder.img.setVisibility(View.GONE);
            viewHolder.llTimeSingle.setVisibility(View.VISIBLE);
            viewHolder.llTimeMuti.setVisibility(View.GONE);
            viewHolder.llimg.setVisibility(View.GONE);
            viewHolder.timeSingle.setText(list.get(position).getPubDate());
            viewHolder.authorSingle.setText(list.get(position).getSource());
        } else if (imgSize == 1) {
            viewHolder.llimg.removeAllViews();
            viewHolder.img.setVisibility(View.VISIBLE);
            viewHolder.llTimeSingle.setVisibility(View.VISIBLE);
            viewHolder.llTimeMuti.setVisibility(View.GONE);
            viewHolder.llimg.setVisibility(View.GONE);
            viewHolder.timeSingle.setText(list.get(position).getPubDate());
            viewHolder.authorSingle.setText(list.get(position).getSource());
            for (int i = 0; i < imgSize; i++) {
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(0, 250, 1));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setCropToPadding(true);
                imageView.setPadding(5, 5, 5, 5);
                Glide.with(context)
                        .load(list.get(position).getImageurls().get(0).toString())
                        .placeholder(R.mipmap.img_loading)
                        .error(R.mipmap.img_error)
                        .into(viewHolder.img);
            }
        } else if (imgSize == 2) {
            viewHolder.llimg.removeAllViews();
            viewHolder.img.setVisibility(View.GONE);
            viewHolder.llTimeSingle.setVisibility(View.GONE);
            viewHolder.llTimeMuti.setVisibility(View.VISIBLE);
            viewHolder.llimg.setVisibility(View.VISIBLE);
            viewHolder.timeMuti.setText(list.get(position).getPubDate());
            viewHolder.authorMuti.setText(list.get(position).getSource());
            for (int i = 0; i < imgSize; i++) {
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(0, 300, 1));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setCropToPadding(true);
                imageView.setPadding(5, 5, 5, 5);
                viewHolder.llimg.addView(imageView);
                Glide.with(context)
                        .load(list.get(position).getImageurls().get(i).toString())
                        .placeholder(R.mipmap.img_loading)
                        .error(R.mipmap.img_error)
                        .into(imageView);
            }
        } else if (imgSize >= 3) {
            viewHolder.llimg.removeAllViews();
            int size = 3;
            viewHolder.img.setVisibility(View.GONE);
            viewHolder.llTimeSingle.setVisibility(View.GONE);
            viewHolder.llTimeMuti.setVisibility(View.VISIBLE);
            viewHolder.llimg.setVisibility(View.VISIBLE);
            viewHolder.timeMuti.setText(list.get(position).getPubDate());
            viewHolder.authorMuti.setText(list.get(position).getSource());
            for (int i = 0; i < size; i++) {
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(0, 250, 1));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setCropToPadding(true);
                imageView.setPadding(5, 5, 5, 5);
                viewHolder.llimg.addView(imageView);
                Glide.with(context)
                        .load(list.get(position).getImageurls().get(i).toString())
                        .placeholder(R.mipmap.img_loading)
                        .error(R.mipmap.img_error)
                        .into(imageView);
            }
        }

        return convertView;
    }


    private class ViewHolder {
        private ImageView img;
        private TextView title, timeSingle, authorSingle, timeMuti, authorMuti;
        private LinearLayout llimg, llTimeSingle, llTimeMuti;

        public ViewHolder(View view) {
            img = (ImageView) view.findViewById(R.id.img);
            title = (TextView) view.findViewById(R.id.title);
            timeSingle = (TextView) view.findViewById(R.id.timeSingle);
            authorSingle = (TextView) view.findViewById(R.id.authorSingle);
            timeMuti = (TextView) view.findViewById(R.id.timeMuti);
            authorMuti = (TextView) view.findViewById(R.id.authorMuti);
            llimg = (LinearLayout) view.findViewById(R.id.llimg);
            llTimeSingle = (LinearLayout) view.findViewById(R.id.llTimeSingle);
            llTimeMuti = (LinearLayout) view.findViewById(R.id.llTimeMuti);
        }
    }
}
