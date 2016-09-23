package com.example.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mvp.model.Post;
import com.example.view.activity.OthersInfoActivity;
import com.example.view.activity.R;
import com.example.view.customview.CircleTextImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ${hcc} on 2016/05/29.
 */
public class MainLvAdapter extends BaseAdapter{

    private List<Post> list;
    private Context context;
    private LayoutInflater inflater;

    public MainLvAdapter(Context context, List<Post> list) {
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
            convertView = inflater.inflate(R.layout.lv_main_item,null);

            holder.img_main_photo = (CircleTextImageView) convertView.findViewById(R.id.img_main_photo);
            holder.publisher = (TextView) convertView.findViewById(R.id.publisher);
            holder.publish_date = (TextView) convertView.findViewById(R.id.publish_date);
            holder.contentTxt = (TextView) convertView.findViewById(R.id.contentTxt);
            holder.type = (TextView) convertView.findViewById(R.id.type);
//            holder.txt_share = (TextView) convertView.findViewById(R.id.txt_share);
//            holder.txt_comment = (TextView) convertView.findViewById(R.id.txt_comment);
//            holder.txt_zan = (TextView) convertView.findViewById(R.id.txt_zan);
//            holder.img_share = (ImageView) convertView.findViewById(R.id.img_comment);
//            holder.img_comment = (ImageView) convertView.findViewById(R.id.img_comment);
//            holder.img_zan = (ImageView) convertView.findViewById(R.id.img_zan);
            holder.talkTitle = (TextView) convertView.findViewById(R.id.talkTitle);

            holder.rel_toInfo = (RelativeLayout) convertView.findViewById(R.id.rel_toInfo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (TextUtils.isEmpty(list.get(position).getUsername().getNick())) {
            holder.publisher.setText(list.get(position).getUsername().getUsername());
        } else {
            holder.publisher.setText(list.get(position).getUsername().getNick());
        }

        holder.publish_date.setText(list.get(position).getCreatedAt());
        holder.contentTxt.setText(list.get(position).getTalkContent());

        Log.i("Title" + position, list.get(position).getTalkTitle());

        String title = list.get(position).getTalkTitle();
        if (!TextUtils.isEmpty(title)) {
            holder.talkTitle.setVisibility(View.VISIBLE);
            holder.talkTitle.setText(title);
        } else {
            holder.talkTitle.setVisibility(View.GONE);
        }

        if (list.get(position).getType().equals("精美文章")) {
            holder.type.setText(list.get(position).getType() + "·" + list.get(position).getTypeDetail());
        } else {
            holder.type.setText(list.get(position).getType());
        }

        holder.rel_toInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OthersInfoActivity.class);
                String objectId = list.get(position).getObjectId();
                Log.i("Adapter", objectId);
                intent.putExtra("objectId", objectId);
                context.startActivity(intent);
            }
        });

        /*加载头像*/
        String photoStr  = list.get(position).getUsername().getUserPhoto();
        if (TextUtils.isEmpty(photoStr)) {
            holder.img_main_photo.setImageResource(R.drawable.health_guide_men_selected);
        } else {
            Picasso.with(context).load(photoStr).into(holder.img_main_photo);
        }
        return convertView;
    }


    static class ViewHolder{
        CircleTextImageView img_main_photo;
        TextView publisher,publish_date,contentTxt,talkTitle,type;
        ImageView img_share,img_comment,img_zan;
        TextView txt_share,txt_comment,txt_zan;

        RelativeLayout rel_toInfo;
    }
}
