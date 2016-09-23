package com.example.view.activity;

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

import com.example.ccinterface.IAlertDialogListener;
import com.example.mvp.model.DailyMood;
import com.example.mvp.model.DailyMoodComment;
import com.example.view.adapter.DailyMoodCommentLvAdapter;
import com.example.view.customview.CircleTextImageView;
import com.example.view.customview.CustomDialog;
import com.example.view.customview.LoadingDialog;
import com.example.view.customview.MyListView;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

public class MoodDetailActivity extends KLBaseActivity implements
        SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipe;
    private static final int REFRESH = 200;

    private TextView publisher, publish_date, contentTxt, commentTXT;
    private MyListView lv_comment;
    private LinearLayout ll_comment;
    private ScrollView scrollView;
    private String objectId;
    private DailyMoodCommentLvAdapter adapter;
    private CircleTextImageView img_photo;

    private ImageView img_detail;

    private static String publisherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_detail);

        setToolBarName("说说详情");
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
        contentTxt = (TextView) findViewById(R.id.contentTxt);

        commentTXT = (TextView) findViewById(R.id.commentTXT);

        lv_comment = (MyListView) findViewById(R.id.lv_comment);
        lv_comment.setFocusable(false);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setFocusable(true);
        scrollView.smoothScrollTo(0, 0);

        img_photo = (CircleTextImageView) findViewById(R.id.img_photo);

        img_detail = (ImageView) findViewById(R.id.img_detail);

        ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
        initData();

//        onClick();
    }


    private void initData() {
        final LoadingDialog dialog = new LoadingDialog(this);
        /*判断当前的Activity是否存在，不存在则不弹出对话框，避免造成程序崩溃*/
        if (!isFinishing()) {
            dialog.show();
        }
        /*初始化说说数据*/
        Intent intent = getIntent();
//        final String title = intent.getStringExtra("title");
        objectId = intent.getStringExtra("objectId");//帖子ID

        final BmobQuery<DailyMood> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.include("username");
        query.findObjects(this, new FindListener<DailyMood>() {
            @Override
            public void onSuccess(List<DailyMood> list) {
                String content = "", date = "", pub = "", nick = "",userPhoto = "",
                        imgUrl = "";
                for (DailyMood t : list) {
                    content = t.getContent();
                    date = t.getCreatedAt();
                    /*获取到帖子发布者*/
                    pub = t.getUsername().getUsername();
                    nick = t.getUsername().getNick();
                    userPhoto = t.getUsername().getUserPhoto();
                    imgUrl = t.getPhotoUrl();
                }

                if (TextUtils.isEmpty(userPhoto)) {
                    img_photo.setImageResource(R.drawable.health_guide_men_selected);
                } else {
                    Picasso.with(MoodDetailActivity.this).load(userPhoto).into(img_photo);
                }

                if (TextUtils.isEmpty(imgUrl)) {
                    img_detail.setVisibility(View.GONE);
                } else {
                    img_detail.setVisibility(View.VISIBLE);
                    Picasso.with(MoodDetailActivity.this).load(imgUrl).into(img_detail);
                }
//                if (TextUtils.isEmpty(title)) {
//                    talkTitle.setVisibility(View.GONE);
//                } else {
//                    talkTitle.setText(title);
//                }
                if (TextUtils.isEmpty(content)) {
                    contentTxt.setVisibility(View.GONE);
                } else {
                    contentTxt.setVisibility(View.VISIBLE);
                    contentTxt.setText(content);
                }

                publish_date.setText(date);
                if (TextUtils.isEmpty(nick)) {
                    publisher.setText(pub);
                } else {
                    publisher.setText(nick);
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });


        /*初始化评论数据*/
        final BmobQuery<DailyMoodComment> commentQuery = new BmobQuery<>();
        DailyMood mood = new DailyMood();
        mood.setObjectId(objectId);
        Log.i("Talk", objectId);
        commentQuery.addWhereEqualTo("mood", new BmobPointer(mood));
        commentQuery.include("user,mood.username");
        commentQuery.order("-updatedAt");
        commentQuery.findObjects(this, new FindListener<DailyMoodComment>() {
            @Override
            public void onSuccess(final List<DailyMoodComment> list) {
                commentTXT.setText("评论 " + list.size());
                dialog.dismiss();
                adapter = new DailyMoodCommentLvAdapter(MoodDetailActivity.this, list);
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
                Intent intent = new Intent(MoodDetailActivity.this, CommentActivity.class);
//                intent.putExtra("title", title);
                intent.putExtra("type","即兴说说");
                intent.putExtra("objectId", objectId);
                startActivity(intent);
            }
        });
    }



    private void deleteComment(final List<DailyMoodComment> list) {
        /*点击删除TextView删除评论*/
        lv_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                TextView deleteTxt = (TextView) view.findViewById(R.id.deleteTxt);
                deleteTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog.showOkOrCancleDialog(MoodDetailActivity.this, "确认删除此条评论吗？", new IAlertDialogListener() {
                            @Override
                            public void onClick() {
                                DailyMoodComment comment = new DailyMoodComment();
                                comment.setObjectId(list.get(position).getObjectId());
                                comment.delete(MoodDetailActivity.this, new DeleteListener() {
                                    @Override
                                    public void onSuccess() {
                                        toast("删除成功");
                                        initData();
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        toast("删除失败" + s);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
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


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
