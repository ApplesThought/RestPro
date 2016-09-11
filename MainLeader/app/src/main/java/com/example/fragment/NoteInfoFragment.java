package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.activity.NoteDetailActivity;
import com.example.activity.R;
import com.example.adapter.NoteRecyclerAdapter;
import com.example.ccinterface.IAlertDialogListener;
import com.example.ccinterface.MyItemClickListener;
import com.example.customview.CustomDialog;
import com.example.entity.MyUser;
import com.example.entity.Post;
import com.example.entity.ReadNote;
import com.example.utils.SortComparator;
import com.example.utils.ToastUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class NoteInfoFragment extends Fragment {
    private RecyclerView lv_note;
    private NoteRecyclerAdapter adapter;
    private TextView totalTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_info, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        lv_note = (RecyclerView) view.findViewById(R.id.lv_note);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        lv_note.setLayoutManager(manager);

        totalTxt = (TextView) view.findViewById(R.id.totalTxt);

        getData();
    }


    private void getData() {
        MyUser user = BmobUser.getCurrentUser(getActivity(), MyUser.class);
        String objectId = user.getObjectId();
        BmobQuery<MyUser> queryInner = new BmobQuery<>();
        String[] id = {objectId};
        queryInner.addWhereContainedIn("objectId", Arrays.asList(id));
        BmobQuery<ReadNote> query = new BmobQuery<>();
        query.addWhereMatchesQuery("username", "_User", queryInner);
        query.include("username");
        query.findObjects(getActivity(), new FindListener<ReadNote>() {
            @Override
            public void onSuccess(List<ReadNote> list) {
                Comparator comparator = new SortComparator();
                Collections.sort(list, comparator);
                adapter = new NoteRecyclerAdapter(getActivity(), list);
                totalTxt.setText("全部笔记( "+list.size()+" )");
                lv_note.setAdapter(adapter);
                onItemClick(list);
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }


    private void onItemClick(final List<ReadNote> list) {
        adapter.setOnItemClickListner(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
                String objectId = list.get(position).getObjectId();
                intent.putExtra("objectId",objectId);
                getActivity().startActivity(intent);
            }
        });
    }


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
