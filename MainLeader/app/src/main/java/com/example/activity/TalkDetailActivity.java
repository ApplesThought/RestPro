package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.adapter.CommentLvAdapter;
import com.example.ccinterface.IAlertDialogListener;
import com.example.customview.CircleTextImageView;
import com.example.customview.CustomDialog;
import com.example.customview.LoadingDialog;
import com.example.customview.MyListView;
import com.example.entity.Comment;
import com.example.entity.MyUser;
import com.example.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class TalkDetailActivity extends KLBaseActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipe;
    private static final int REFRESH = 200;

    private TextView publisher, publish_date, talkTitle, contentTxt, commentTXT;
//    private LinearLayout addAttention;

    private CommentLvAdapter adapter;
    private MyListView lv_comment;

    private LinearLayout ll_comment;

    private ScrollView scrollView;
    private String objectId;

    private CircleTextImageView img_photo;

    private ImageView img_attention;
    private TextView txt_attention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_detail);

        setToolBarName("正文详情");

        initView();
    }

    private void initView() {
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

        publisher = (TextView) findViewById(R.id.publisher);
        publish_date = (TextView) findViewById(R.id.publish_date);
        talkTitle = (TextView) findViewById(R.id.talkTitle);
        contentTxt = (TextView) findViewById(R.id.contentTxt);

        commentTXT = (TextView) findViewById(R.id.commentTXT);

        lv_comment = (MyListView) findViewById(R.id.lv_comment);
        lv_comment.setFocusable(false);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setFocusable(true);
        scrollView.smoothScrollTo(0, 0);

        img_photo = (CircleTextImageView) findViewById(R.id.img_photo);

        ll_comment = (LinearLayout) findViewById(R.id.ll_comment);

//        addAttention = (LinearLayout) findViewById(R.id.addAttention);
//        img_attention = (ImageView) findViewById(R.id.img_attention);
//        txt_attention = (TextView) findViewById(R.id.txt_attention);

        initData();

//        onClick();
    }

//    private void onClick() {
//        /*加关注*/
//        addAttention.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                getAttention();
//            }
//        });
//    }


    private void getAttention() {
        /*根据说说查找出发布者信息，得到发布者的id，根据id关注*/
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.include("user");
        final MyUser user = BmobUser.getCurrentUser(TalkDetailActivity.this, MyUser.class);
        final MyUser myUser = new MyUser();

        query.findObjects(TalkDetailActivity.this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {

                String id = "";
                for (Post u : list) {
                    id = u.getUsername().getObjectId();//发帖者的ID
                }
                myUser.setObjectId(id);//将用户和关注关联起来
                Log.i("TalkDDDDDDDDD", id);

                //将当前用户添加到MyUser表中的special字段值中，表明当前用户关注该用户
                BmobRelation relation = new BmobRelation();
                relation.add(user);
                myUser.setSpecial(relation);
                myUser.update(TalkDetailActivity.this, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        toast("成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toast("失败" + i + s);
                    }
                });

                BmobQuery<MyUser> queryIsAttention = new BmobQuery<>();
                queryIsAttention.addWhereEqualTo("objectId", id);
                queryIsAttention.addQueryKeys("isSpecial");
                final String finalId = id;
                final String finalId1 = id;
                /*queryIsAttention.findObjects(TalkDetailActivity.this, new FindListener<MyUser>() {
                    @Override
                    public void onSuccess(List<MyUser> list) {
                        boolean isSpecial = false;
                        for (MyUser use : list) {
                            isSpecial = use.isSpecial();
                        }

                        if (isSpecial) {
                            *//*已经添加过关注了*//*
                            txt_attention.setText("已关注");
                            txt_attention.setTextColor(getResources().getColor(R.color.grey_big));
                            img_attention.setImageResource(R.mipmap.card_icon_attention);
                            addAttention.setClickable(false);
                        } else {
                            *//*还未添加过关注*//*
                            myUser.update(TalkDetailActivity.this, finalId, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    toast("添加关注成功");
                                    MyUser s = new MyUser();
                                    s.setIsSpecial(true);
                                    s.update(TalkDetailActivity.this, finalId1,new UpdateListener() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onFailure(int i, String s) {

                                        }
                                    });
                                    txt_attention.setText("已关注");
                                    img_attention.setImageResource(R.mipmap.card_icon_attention);
                                    txt_attention.setTextColor(getResources().getColor(R.color.grey_big));
                                    addAttention.setClickable(false);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    toast("添加关注失败");
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });*/
            }

            @Override
            public void onError(int i, String s) {
                toast("添加关注失败");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
//        getAttention();
    }

    private void initData() {
        final LoadingDialog dialog = new LoadingDialog(this);
        /*判断当前的Activity是否存在，不存在则不弹出对话框，避免造成程序崩溃*/
        if (!isFinishing()) {
            dialog.show();
        }
        /*初始化说说数据*/
        Intent intent = getIntent();
        final String title = intent.getStringExtra("title");
        objectId = intent.getStringExtra("objectId");//帖子ID

        final BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.include("username");
        query.findObjects(this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                String content = "", date = "", pub = "", nick = "",photoStr = "";
                for (Post t : list) {
                    content = t.getTalkContent();
                    date = t.getCreatedAt();
                    /*获取到帖子发布者*/
                    pub = t.getUsername().getUsername();
                    nick = t.getUsername().getNick();
                    photoStr = t.getUsername().getUserPhoto();
                }
                if (TextUtils.isEmpty(title)) {
                    talkTitle.setVisibility(View.GONE);
                } else {
                    talkTitle.setText(title);
                }
                contentTxt.setText(content);
                publish_date.setText(date);
                if (TextUtils.isEmpty(nick)) {
                    publisher.setText(pub);
                } else {
                    publisher.setText(nick);
                }

                if (TextUtils.isEmpty(photoStr)) {
                    img_photo.setImageResource(R.drawable.health_guide_men_selected);
                } else {
                    Picasso.with(TalkDetailActivity.this).load(photoStr).into(img_photo);
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });


        /*初始化评论数据*/
        final BmobQuery<Comment> commentQuery = new BmobQuery<>();
        Post post = new Post();
        post.setObjectId(objectId);
        Log.i("Talk", objectId);
        commentQuery.addWhereEqualTo("post", new BmobPointer(post));
        commentQuery.include("user,post.username");
        commentQuery.order("-updatedAt");
        commentQuery.findObjects(this, new FindListener<Comment>() {
            @Override
            public void onSuccess(final List<Comment> list) {
                commentTXT.setText("评论 " + list.size());
                dialog.dismiss();
                adapter = new CommentLvAdapter(TalkDetailActivity.this, list);
                lv_comment.setAdapter(adapter);

                deleteComment(list);
            }

            @Override
            public void onError(int i, String s) {
                dialog.dismiss();
                toast("获取评论失败");
            }
        });


        /*跳转评论*/
        ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TalkDetailActivity.this, CommentActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("type","非即兴说说");
                intent.putExtra("objectId", objectId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(REFRESH, 2000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH:
                    initData();
                    swipe.setRefreshing(false);
                    break;
            }
        }
    };


    private void deleteComment(final List<Comment> list) {
        /*点击删除TextView删除评论*/
        lv_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                TextView deleteTxt = (TextView) view.findViewById(R.id.deleteTxt);
                deleteTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog.showOkOrCancleDialog(TalkDetailActivity.this, "确认删除此条评论吗？", new IAlertDialogListener() {
                            @Override
                            public void onClick() {
                                Comment comment = new Comment();
                                comment.setObjectId(list.get(position).getObjectId());
                                comment.delete(TalkDetailActivity.this, new DeleteListener() {
                                    @Override
                                    public void onSuccess() {
                                        toast("删除成功");
                                        initData();
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        toast("删除失败"+s);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}
