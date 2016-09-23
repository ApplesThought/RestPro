package com.example.view.fragment;

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

import com.example.ccinterface.MyItemClickListener;
import com.example.mvp.model.DailyMood;
import com.example.mvp.model.MyUser;
import com.example.utils.SortComparator;
import com.example.view.activity.MoodDetailActivity;
import com.example.view.activity.R;
import com.example.view.adapter.TalkRecyclerAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class TalkInfoFragment extends Fragment {

    private RecyclerView lv_talk;
    private TalkRecyclerAdapter adapter;
    private TextView totalTxt;

    private static final int DELETE = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talk_info, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        lv_talk = (RecyclerView) view.findViewById(R.id.lv_talk);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        lv_talk.setLayoutManager(manager);

        totalTxt = (TextView) view.findViewById(R.id.totalTxt);

        getData();
    }


    private void getData() {
        MyUser user = BmobUser.getCurrentUser(getActivity(), MyUser.class);
        String objectId = user.getObjectId();
        Log.i("TalkFragment", objectId);

        BmobQuery<MyUser> queryInner = new BmobQuery<>();
        String[] id = {objectId};
        queryInner.addWhereContainedIn("objectId", Arrays.asList(id));

        /*查询帖子*/
        BmobQuery<DailyMood> query = new BmobQuery<>();
        query.addWhereMatchesQuery("username", "_User", queryInner);
        query.include("username");
        query.findObjects(getActivity(), new FindListener<DailyMood>() {
            @Override
            public void onSuccess(List<DailyMood> list) {
                Comparator comparator = new SortComparator();
                Collections.sort(list, comparator);
                adapter = new TalkRecyclerAdapter(getActivity(), list);
                lv_talk.setAdapter(adapter);
                totalTxt.setText("全部说说( " + list.size() + " )");
                onItemClick(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    private void onItemClick(final List<DailyMood> list) {
        adapter.setOnItemClickListner(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String objectId = list.get(position).getObjectId();
                Bundle bundle = new Bundle();
                bundle.putString("objectId", objectId);
                Log.i("OVJHome", objectId);
                Intent intent = new Intent(getActivity(), MoodDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
