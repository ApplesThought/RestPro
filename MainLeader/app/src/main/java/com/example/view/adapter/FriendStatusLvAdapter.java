package com.example.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mvp.model.DailyMood;
import com.example.view.activity.R;
import com.example.view.customview.CircleTextImageView;

import java.util.List;

/**
 * Created by ${hcc} on 2016/06/16.
 */
public class FriendStatusLvAdapter extends BaseAdapter{
    private List<DailyMood> list;
    private Context context;
    private LayoutInflater inflater;

    public FriendStatusLvAdapter(Context context, List<DailyMood> list) {
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
            convertView = inflater.inflate(R.layout.lv_friend_item,null);

            holder.img_friend_photo = (CircleTextImageView) convertView.findViewById(R.id.img_friend_photo);
            holder.friend_nameTxt = (TextView) convertView.findViewById(R.id.friend_nameTxt);
            holder.friend_dateTxt = (TextView) convertView.findViewById(R.id.friend_dateTxt);
            holder.contentTxt = (TextView) convertView.findViewById(R.id.contentTxt);
            holder.lookTimesTxt = (TextView) convertView.findViewById(R.id.lookTimesTxt);
            holder.moodImg = (ImageView) convertView.findViewById(R.id.moodImg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String name = list.get(position).getUsername().getNick();
        if (TextUtils.isEmpty(name)) {
            holder.friend_nameTxt.setText(list.get(position).getUsername().getUsername());
        } else {
            holder.friend_nameTxt.setText(list.get(position).getUsername().getNick());
        }


        String content = list.get(position).getContent();
        if (TextUtils.isEmpty(content)) {
            holder.contentTxt.setVisibility(View.GONE);
        } else {
            holder.contentTxt.setVisibility(View.VISIBLE);
            holder.contentTxt.setText(content);
        }
        holder.friend_dateTxt.setText(list.get(position).getUpdatedAt());

        Integer times = list.get(position).getLookTimes();
        if (times == null) {
            times = 0;
            Integer a = times + 3;
            holder.lookTimesTxt.setText("浏览"+a+"次");
        } else {
            holder.lookTimesTxt.setText("浏览"+list.get(position).getLookTimes()+"次");
        }

        /*设置头像*/
        String photoStr  = list.get(position).getUsername().getUserPhoto();
        if (TextUtils.isEmpty(photoStr)) {
            holder.img_friend_photo.setImageResource(R.drawable.health_guide_men_selected);
        } else {
            Glide.with(context).load(photoStr)
                    .placeholder(R.mipmap.img_loading)
                    .error(R.mipmap.img_error)
                    .into(holder.img_friend_photo);
        }

        /*设置说说图片*/
        String imgUrl = list.get(position).getPhotoUrl();
        if (TextUtils.isEmpty(imgUrl)) {
            holder.moodImg.setVisibility(View.GONE);
        } else {
            holder.moodImg.setVisibility(View.VISIBLE);
            Glide.with(context).load(imgUrl)
                    .placeholder(R.mipmap.img_loading_big)
                    .error(R.mipmap.img_error)
                    .into(holder.moodImg);
        }
        return convertView;
    }

    static class ViewHolder{
        CircleTextImageView img_friend_photo;
        TextView friend_nameTxt,friend_dateTxt,contentTxt,lookTimesTxt;
        ImageView moodImg;
    }
}
