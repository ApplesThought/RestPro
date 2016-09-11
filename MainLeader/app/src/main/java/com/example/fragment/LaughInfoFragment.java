package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.activity.R;
import com.example.adapter.LaughRecyclerAdapter;
import com.example.ccinterface.IAlertDialogListener;
import com.example.ccinterface.MyItemClickListener;
import com.example.customview.CustomDialog;
import com.example.entity.MyUser;
import com.example.entity.Post;
import com.example.utils.SortComparator;
import com.example.utils.ToastUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class LaughInfoFragment extends Fragment {
    private RecyclerView lv_laugh;
    private LaughRecyclerAdapter adapter;
    private TextView totalTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_laugh_info, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        lv_laugh = (RecyclerView) view.findViewById(R.id.lv_laugh);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        lv_laugh.setLayoutManager(manager);

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
        query.addWhereEqualTo("type", "搞笑段子");
        query.findObjects(getActivity(), new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                Comparator comparator = new SortComparator();
                Collections.sort(list, comparator);
                adapter = new LaughRecyclerAdapter(getActivity(), list);
                lv_laugh.setAdapter(adapter);
                totalTxt.setText("全部段子( " + list.size() + " )");
                onItemClick(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    private void onItemClick(final List<Post> list) {
        adapter.setOnItemClickListner(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ArticleInfoFragment.toDetail(getActivity(), list, position);
            }
        });
    }

//    private void onClick(final List<Post> list) {
//        lv_laugh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ArticleInfoFragment.toDetail(getActivity(), list, position);
//            }
//        });
//    }


//    private void onItemClick(ListView lv, final List<Post> list) {
//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                showSelectDialog(list, position);
//                return true;
//            }
//        });
//    }

    private void showSelectDialog(final List<Post> listPost, final int position) {
        CustomDialog.showSelectDialog(getActivity(), "删除", "分享", "查看详情", new IAlertDialogListener() {
            @Override
            public void onClick() {//第一项点击
                ArticleInfoFragment.deleteArticle(getActivity(), listPost, position);
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
                ArticleInfoFragment.toDetail(getActivity(), listPost, position);
            }
        });
    }
}
