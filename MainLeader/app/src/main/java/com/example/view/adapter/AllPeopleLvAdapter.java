package com.example.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvp.model.MyUser;
import com.example.view.activity.R;

import java.util.List;

/**
 * Created by ${hcc} on 2016/05/29.
 */
public class AllPeopleLvAdapter extends BaseAdapter{

    private List<MyUser> list;
    private Context context;
    private LayoutInflater inflater;

    public AllPeopleLvAdapter(Context context, List<MyUser> list) {
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
            convertView = inflater.inflate(R.layout.photo_detail_item,null);

//            holder.people_photoImg = (ImageView) convertView.findViewById(R.id.people_photoImg);
//            holder.people_nameTxt = (TextView) convertView.findViewById(R.id.people_nameTxt);
//            holder.people_introTxt = (TextView) convertView.findViewById(R.id.people_introTxt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /*String nick = list.get(position).getNick();
        Log.i("Nick",nick);
        if (TextUtils.isEmpty(nick)) {
            holder.people_nameTxt.setText(list.get(position).getUsername());
        } else {
            holder.people_nameTxt.setText(nick);
        }
        holder.people_introTxt.setText(list.get(position).getSignal());

        String photoStr  = list.get(position).getUserPhoto();
        if (TextUtils.isEmpty(photoStr)) {
            holder.people_photoImg.setImageResource(R.drawable.health_guide_men_selected);
        } else {
            Picasso.with(context).load(photoStr).into(holder.people_photoImg);
        }*/
        return convertView;
    }


    static class ViewHolder{
        ImageView people_photoImg;
        TextView people_nameTxt,people_introTxt;
    }
}
