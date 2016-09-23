package com.example.view.viewpager;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mvp.model.MyUser;
import com.example.view.activity.R;
import com.example.view.customview.CircleTextImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hcc on 16/6/22.
 */

public class MyViewpagerAdapter extends com.example.view.viewpager.PagerAdapter {

    public Context mContext;

    public List<MyUser> list;

    public MyViewpagerAdapter(Context context, List<MyUser> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.all_people_item, null);
        CircleTextImageView mIcon = (CircleTextImageView) view.findViewById(R.id.profile_image);
        TextView userNameTv = (TextView) view.findViewById(R.id.userNameTv);
        TextView mIntro = (TextView) view.findViewById(R.id.intro);
        Button mBtn = (Button) view.findViewById(R.id.item_btn);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) mOnItemClickListener.onClick(position);
            }
        });

        Picasso.with(mContext).load(list.get(position).getUserPhoto())
                .placeholder(R.drawable.health_guide_men_selected)
                .error(R.mipmap.img_error).into(mIcon);
        String nick = list.get(position).getNick();
        if (TextUtils.isEmpty(nick)) {
            userNameTv.setText(list.get(position).getUsername());
        } else {
            userNameTv.setText(list.get(position).getNick());
        }
        mIntro.setText(list.get(position).getSignal());
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, final int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
