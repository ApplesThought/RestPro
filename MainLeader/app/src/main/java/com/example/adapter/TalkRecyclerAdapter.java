package com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.activity.R;
import com.example.ccinterface.MyItemClickListener;
import com.example.ccinterface.MyItemLongClickListener;
import com.example.customview.CircleTextImageView;
import com.example.entity.DailyMood;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by ${hcc} on 2016/04/08.
 */
public class TalkRecyclerAdapter extends RecyclerView.Adapter<TalkRecyclerAdapter.TalkHolder> {

    private List<DailyMood> list;

    private int local_position = 0;
    private boolean iamgeChage = false;

    private LayoutInflater inflater;

    private Context context;


    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;

    public TalkRecyclerAdapter(Context context, List<DailyMood> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }


    public void updateData(List<DailyMood> newItems) {
        if (newItems != null) {
            list = newItems;
            notifyItemChanged(list.size());
        }
    }

    public void setOnItemClickListner(MyItemClickListener listener) {
        this.myItemClickListener = listener;
    }


    @Override
    public TalkHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = inflater.inflate(R.layout.lv_friend_item, null);
        TalkHolder holder = new TalkHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final TalkHolder holder, int position) {

//        holder.itemView.setTag(list.get(position));

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
        Log.i("TIMES", times + "");
        if (times == null) {
            times = 0;
            Integer a = times + 3;
            holder.lookTimesTxt.setText("浏览" + a + "次");
        } else {
            holder.lookTimesTxt.setText("浏览" + list.get(position).getLookTimes() + "次");
        }

        /*设置头像*/
        String photoStr = list.get(position).getUsername().getUserPhoto();
        if (TextUtils.isEmpty(photoStr)) {
            holder.img_friend_photo.setImageResource(R.drawable.health_guide_men_selected);
        } else {
            Picasso.with(context).load(photoStr).into(holder.img_friend_photo);
        }

        /*设置说说图片*/
        String imgUrl = list.get(position).getPhotoUrl();
        if (TextUtils.isEmpty(imgUrl)) {
            holder.moodImg.setVisibility(View.GONE);
        } else {
            holder.moodImg.setVisibility(View.VISIBLE);
            Picasso.with(context).load(imgUrl).error(R.drawable.load_error)
                    .into(holder.moodImg);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemClickListener != null) {
                    myItemClickListener.onItemClick(v, holder.getPosition());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static List<DailyMood> collectList;

    class TalkHolder extends RecyclerView.ViewHolder {

        CircleTextImageView img_friend_photo;
        TextView friend_nameTxt, friend_dateTxt, contentTxt, lookTimesTxt;
        ImageView moodImg;

        TalkHolder(View itemView) {
            super(itemView);
            img_friend_photo = (CircleTextImageView) itemView.findViewById(R.id.img_friend_photo);
            friend_nameTxt = (TextView) itemView.findViewById(R.id.friend_nameTxt);
            friend_dateTxt = (TextView) itemView.findViewById(R.id.friend_dateTxt);
            contentTxt = (TextView) itemView.findViewById(R.id.contentTxt);
            lookTimesTxt = (TextView) itemView.findViewById(R.id.lookTimesTxt);
            moodImg = (ImageView) itemView.findViewById(R.id.moodImg);

        }

    }

}
