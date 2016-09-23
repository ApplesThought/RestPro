package com.example.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mvp.model.PhotoClassInfo;
import com.example.view.activity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${hcc} on 2016/09/10.
 */
public class PhotoListAdapter extends BaseAdapter {
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

    public PhotoListAdapter(Context context, List<PhotoClassInfo> data) {
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
            convertView = mInflater.inflate(R.layout.photo_list_item, null);
            vh = new ViewHolder();
            vh.listTv = (TextView) convertView.findViewById(R.id.listTv);
            vh.listImg = (ImageView) convertView.findViewById(R.id.listImg);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.listTv.setText(data.get(position).getTitle());
        vh.listImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext).load(data.get(position).getImgUrl())
                .placeholder(R.mipmap.img_loading)
                .error(R.mipmap.img_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(vh.listImg);
        return convertView;
    }

    class ViewHolder {
        TextView listTv;
        ImageView listImg;
    }
}
