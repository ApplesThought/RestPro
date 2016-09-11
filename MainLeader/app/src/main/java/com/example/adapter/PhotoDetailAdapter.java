package com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.activity.R;
import com.example.ccinterface.MyItemClickListener;
import com.example.ccinterface.MyItemLongClickListener;
import com.example.entity.PhotoDetailInfo;

import java.util.List;


/**
 * Created by ${hcc} on 2016/04/08.
 */
public class PhotoDetailAdapter extends RecyclerView.Adapter<PhotoDetailAdapter.PhotoDetailHolder> {

    private List<PhotoDetailInfo> list;

    private LayoutInflater inflater;

    private Context context;


    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;

    public PhotoDetailAdapter(Context context, List<PhotoDetailInfo> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }


    public void updateData(List<PhotoDetailInfo> newItems) {
        if (newItems != null) {
            list = newItems;
            notifyItemChanged(list.size());
        }
    }

    public void setOnItemClickListner(MyItemClickListener listener) {
        this.myItemClickListener = listener;
    }


    @Override
    public PhotoDetailHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = inflater.inflate(R.layout.photo_detail_item, null);
        PhotoDetailHolder holder = new PhotoDetailHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final PhotoDetailHolder holder, final int position) {


        Glide.with(context).load(list.get(position).getSrc())
                .placeholder(R.mipmap.img_loading)
                .error(R.mipmap.img_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(holder.photoImg);


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
        if (list == null) return 0;
        return list.size();
    }

    class PhotoDetailHolder extends RecyclerView.ViewHolder {

        ImageView photoImg;

        PhotoDetailHolder(View itemView) {
            super(itemView);
            photoImg = (ImageView) itemView.findViewById(R.id.photoImg);
        }

    }

}
