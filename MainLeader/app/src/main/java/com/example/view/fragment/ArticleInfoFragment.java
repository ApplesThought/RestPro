package com.example.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.activity.R;
import com.example.ccinterface.IAlertDialogListener;
import com.example.ccinterface.MyItemClickListener;
import com.example.mvp.model.Comment;
import com.example.mvp.model.MyUser;
import com.example.mvp.model.Post;
import com.example.utils.SortComparator;
import com.example.utils.ToastUtils;
import com.example.view.activity.TalkDetailActivity;
import com.example.view.adapter.ArticleRecyclerAdapter;
import com.example.view.customview.CustomDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

public class ArticleInfoFragment extends Fragment {

    private RecyclerView lv_article;
    private ArticleRecyclerAdapter adapter;
    private TextView totalTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        lv_article = (RecyclerView) view.findViewById(R.id.lv_article);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),1);
        lv_article.setLayoutManager(manager);


        totalTxt = (TextView) view.findViewById(R.id.totalTxt);

        getData();
    }


    private void getData() {
        MyUser user = BmobUser.getCurrentUser(getActivity(), MyUser.class);
        String objectId = user.getObjectId();

        BmobQuery<MyUser> queryInner = new BmobQuery<>();
        String[] id = {objectId};
        queryInner.addWhereContainedIn("objectId", Arrays.asList(id));

        /*查询段子*/
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereMatchesQuery("username", "_User", queryInner);
        query.include("username");
        query.addWhereEqualTo("type","精美文章");
        query.findObjects(getActivity(), new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                Comparator comparator = new SortComparator();
                Collections.sort(list, comparator);
                adapter = new ArticleRecyclerAdapter(getActivity(), list);
                lv_article.setAdapter(adapter);
                totalTxt.setText("全部文章( " + list.size() + " )");
                onItemClick(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    private void onItemClick(final List<Post> list){
        adapter.setOnItemClickListner(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toDetail(getActivity(),list,position);
            }
        });
    }

//    private void onClick(final List<Post> list){
//        lv_article.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                toDetail(getActivity(),list,position);
//            }
//        });
//    }


    public static void toDetail(Context context,List<Post> list,int position) {
        String title = list.get(position).getTalkTitle();
        String objectId = list.get(position).getObjectId();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("objectId", objectId);
        Log.i("OVJHome", objectId);
        Intent intent = new Intent(context, TalkDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

//    private void onItemClick(ListView lv, final List<Post> list) {
//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                showSelectDialog(list,position);
//                return true;
//            }
//        });
//    }

    private void showSelectDialog(final List<Post> listPost, final int position) {
        CustomDialog.showSelectDialog(getActivity(), "删除", "分享", "查看详情", new IAlertDialogListener() {
            @Override
            public void onClick() {//第一项点击
                deleteArticle(getActivity(), listPost, position);
                getData();
            }
        }, new IAlertDialogListener() {
            @Override
            public void onClick() {//第二项点击
                ToastUtils.showToast(getActivity(), "分享");
            }
        }, new IAlertDialogListener() {
            @Override
            public void onClick() {
                toDetail(getActivity(), listPost, position);
            }
        });
    }



    public static void deleteArticle(final Context context,List<Post> list,int position) {
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
                for (Comment comment : listComment) {
                    String objID = comment.getObjectId();
                    List<BmobObject> comments = new ArrayList<>();
                    Comment c = new Comment();
                    c.setObjectId(objID);
                    comments.add(c);

                    new BmobObject().deleteBatch(context, comments, new DeleteListener() {
                        @Override
                        public void onSuccess() {
                            ToastUtils.showToast(context, "批量删除成功");
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            ToastUtils.showToast(context, "批量删除失败:" + msg);
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
