package com.example.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.activity.R;
import com.example.customview.CircleTextImageView;
import com.example.customview.SwipeView;
import com.example.entity.Comment;
import com.example.entity.Post;
import com.example.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by ${hcc} on 2016/05/29.
 */
public class ArticleLvAdapter extends BaseAdapter {

    private List<Post> list;
    private Context context;
    private LayoutInflater inflater;

    private SwipeView mOldSwipeView;

    public ArticleLvAdapter(Context context, List<Post> list) {
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
//        SwipeView swipeView = (SwipeView) convertView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lv_article_item, null);
//            swipeView = new SwipeView(context);
//            swipeView.setContentItemView(view);
            holder = new ViewHolder();

            holder.articlePhoto = (CircleTextImageView) convertView.findViewById(R.id.articlePhoto);
            holder.articleTitle = (TextView) convertView.findViewById(R.id.articleTitle);
            holder.articleTime = (TextView) convertView.findViewById(R.id.articleTime);
            holder.articleAuthor = (TextView) convertView.findViewById(R.id.articleAuthor);

//            holder.leftView = (TextView) convertView.findViewById(R.id.tv_left);
//            holder.rightView = (TextView) convertView.findViewById(R.id.tv_right);

            /*swipeView.setOnSlideListener(new OnSlideListener() {
                @Override
                public void onSlide(View view, int status) {

                    if (mOldSwipeView != null && mOldSwipeView != view) {
                        mOldSwipeView.shrink();
                    }

                    if (status == SLIDE_STATUS_ON) {
                        mOldSwipeView = (SwipeView) view;
                    }
                }
            });*/
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


//        if (SwipeListView.mSwipeView != null) {
//            SwipeListView.mSwipeView.shrink();
//        }


        final Post post = (Post) getItem(position);
        if (post == null) {
            return convertView;
        }

        holder.articleTitle.setText(list.get(position).getTalkTitle());
        holder.articleTime.setText(list.get(position).getUpdatedAt());

        String name = list.get(position).getUsername().getNick();
        if (TextUtils.isEmpty(name)) {
            holder.articleAuthor.setText(list.get(position).getUsername().getUsername());
        } else {
            holder.articleAuthor.setText(list.get(position).getUsername().getNick());
        }

        String photoUrl = list.get(position).getUsername().getUserPhoto();
        if (TextUtils.isEmpty(photoUrl)) {
            holder.articlePhoto.setImageResource(R.drawable.health_guide_men_selected);
        } else {
            Picasso.with(context).load(photoUrl).into(holder.articlePhoto);
        }


        /*holder.leftView.setText("删除");
        holder.rightView.setText("分享");
        holder.leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post == null) {
                    return;
                }

                deleteArticle(position);
                notifyDataSetChanged();
            }
        });
        holder.rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
            }
        });*/

        return convertView;
    }


    static class ViewHolder {
        CircleTextImageView articlePhoto;
        TextView articleTitle, articleTime, articleAuthor;

        private TextView leftView;
        private TextView rightView;

        /*ViewHolder(View convertView) {
            articlePhoto = (CircleTextImageView) convertView.findViewById(R.id.articlePhoto);
            articleTitle = (TextView) convertView.findViewById(R.id.articleTitle);
            articleTime = (TextView) convertView.findViewById(R.id.articleTime);
            articleAuthor = (TextView) convertView.findViewById(R.id.articleAuthor);

            leftView = (TextView) convertView.findViewById(R.id.tv_left);
            rightView = (TextView) convertView.findViewById(R.id.tv_right);
        }*/
    }


    private void deleteArticle(int position) {
        Post post = new Post();
        post.delete(context, list.get(position).getObjectId(), new DeleteListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showToast(context,"删除文章成功");
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showToast(context,"删除文章失败");
            }
        });


        /*删除此文章下的评论*/
        BmobQuery<Comment> commentQuery = new BmobQuery<>();
        Post post2 = new Post();
        post2.setObjectId(list.get(position).getObjectId());
        commentQuery.addWhereEqualTo("post", new BmobPointer(post2));
        commentQuery.include("user,post.username");
        commentQuery.order("-updatedAt");

        commentQuery.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(final List<Comment> listComment) {
                for (Comment comment:listComment) {
                    String objID = comment.getObjectId();
                    List<BmobObject> comments = new ArrayList<>();
                    Comment c = new Comment();
                    c.setObjectId(objID);
                    comments.add(c);

                    new BmobObject().deleteBatch(context, comments, new DeleteListener() {
                        @Override
                        public void onSuccess() {
                            ToastUtils.showToast(context,"批量删除成功");
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            ToastUtils.showToast(context,"批量删除失败:" + msg);
                        }
                    });
                }

            }


            @Override
            public void onError(int i, String s) {
            }
        });
    }
}
