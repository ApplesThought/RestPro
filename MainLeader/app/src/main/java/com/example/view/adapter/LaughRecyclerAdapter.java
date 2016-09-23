package com.example.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ccinterface.MyItemClickListener;
import com.example.ccinterface.MyItemLongClickListener;
import com.example.mvp.model.DailyMood;
import com.example.mvp.model.Post;
import com.example.view.activity.OthersInfoActivity;
import com.example.view.activity.R;
import com.example.view.customview.CircleTextImageView;

import java.util.List;


/**
 * Created by ${hcc} on 2016/04/08.
 */
public class LaughRecyclerAdapter extends RecyclerView.Adapter<LaughRecyclerAdapter.LaughHolder> {

    private List<Post> list;

    private LayoutInflater inflater;

    private Context context;


    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;

    public LaughRecyclerAdapter(Context context, List<Post> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }


    public void updateData(List<Post> newItems) {
        if (newItems != null) {
            list = newItems;
            notifyItemChanged(list.size());
        }
    }

    public void setOnItemClickListner(MyItemClickListener listener){
        this.myItemClickListener = listener;
    }



    @Override
    public LaughHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = inflater.inflate(R.layout.lv_main_item,null);
        LaughHolder holder = new LaughHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final LaughHolder holder, final int position) {

//        holder.itemView.setTag(list.get(position));

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

        holder.type.setText(list.get(position).getType());

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
            Glide.with(context).load(photoStr).into(holder.img_main_photo);
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

    class LaughHolder extends RecyclerView.ViewHolder{

        CircleTextImageView img_main_photo;
        TextView publisher,publish_date,contentTxt,talkTitle,type;

        RelativeLayout rel_toInfo;

        LaughHolder(View itemView) {
            super(itemView);
            img_main_photo = (CircleTextImageView) itemView.findViewById(R.id.img_main_photo);
            publisher = (TextView) itemView.findViewById(R.id.publisher);
            publish_date = (TextView) itemView.findViewById(R.id.publish_date);
            contentTxt = (TextView) itemView.findViewById(R.id.contentTxt);
            type = (TextView) itemView.findViewById(R.id.type);
            talkTitle = (TextView) itemView.findViewById(R.id.talkTitle);
            rel_toInfo = (RelativeLayout) itemView.findViewById(R.id.rel_toInfo);
        }

    }

}
