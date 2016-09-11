package com.example.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.activity.R;
import com.example.customview.CircleTextImageView;
import com.example.entity.Comment;
import com.example.entity.MyUser;
import com.example.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by ${hcc} on 2016/05/31.
 */
public class CommentLvAdapter extends BaseAdapter {
    private List<Comment> list;
    private Context context;
    private LayoutInflater inflater;

    public CommentLvAdapter(Context context, List<Comment> list) {
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
            convertView = inflater.inflate(R.layout.lv_comment_item, null);

            holder.comment_photo = (CircleTextImageView) convertView.findViewById(R.id.comment_photo);
            holder.comment_men = (TextView) convertView.findViewById(R.id.comment_men);
            holder.comment_date = (TextView) convertView.findViewById(R.id.comment_date);
            holder.contentTxt = (TextView) convertView.findViewById(R.id.contentTxt);
            holder.deleteTxt = (TextView) convertView.findViewById(R.id.deleteTxt);
            holder.comment_owner = (TextView) convertView.findViewById(R.id.comment_owner);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (TextUtils.isEmpty(list.get(position).getUser().getNick())) {
            holder.comment_men.setText(list.get(position).getUser().getUsername());
        } else {
            holder.comment_men.setText(list.get(position).getUser().getNick());
        }

        holder.comment_date.setText(list.get(position).getCreatedAt());
        holder.contentTxt.setText(list.get(position).getComment());

        /*评论人ID*/
        final String commenUsertId = list.get(position).getUser().getObjectId();
        /*当前用户ID*/
        MyUser user = BmobUser.getCurrentUser(context, MyUser.class);
        String currentUserId = user.getObjectId();
        /*帖子ID*/
        String postId = list.get(position).getPost().getObjectId();
        BmobQuery<Post> query = new BmobQuery();
        query.addWhereEqualTo("objectId",postId);
        query.include("username");
        final ViewHolder finalHolder = holder;
        final ViewHolder finalHolder1 = holder;
        /*根据帖子ID查询出发帖人的Id*/
        query.findObjects(context, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                String postOwnerId = "";
                for (Post post : list) {
                    postOwnerId = post.getUsername().getObjectId();
                    Log.i("发帖人ID", postOwnerId);
                }

                /*设置楼主*/
                if (postOwnerId.equals(commenUsertId)) {
                    finalHolder.comment_owner.setVisibility(View.VISIBLE);
                } else {
                    finalHolder1.comment_owner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

        if (commenUsertId.equals(currentUserId)) {
            holder.deleteTxt.setVisibility(View.VISIBLE);
            holder.comment_owner.setVisibility(View.VISIBLE);
        } else {
            holder.deleteTxt.setVisibility(View.GONE);
            holder.comment_owner.setVisibility(View.GONE);
        }




        String photoStr  = list.get(position).getUser().getUserPhoto();
        if (TextUtils.isEmpty(photoStr)) {
            holder.comment_photo.setImageResource(R.drawable.health_guide_men_selected);
        } else {
            Picasso.with(context).load(photoStr).into(holder.comment_photo);
        }

        return convertView;
    }


    static class ViewHolder {
        CircleTextImageView comment_photo;
        TextView comment_men, comment_date, contentTxt,deleteTxt,comment_owner;
    }
}
