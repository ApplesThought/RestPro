package com.example.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodola.listview.extlib.ListViewExt;
import com.example.activity.R;
import com.example.adapter.NoteLvAdapter;
import com.example.customview.LoadingDialog;
import com.example.entity.MyUser;
import com.example.entity.ReadNote;
import com.example.utils.SortComparator;
import com.example.utils.ToNoteDetailUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class NoteAllFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipe;
    private static final int REFRESH = 200;

    private ListViewExt noteListView;


    private View emptyView;
    private LoadingDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_mobile, container, false);

        emptyView = view.findViewById(R.id.empty);

        initView(view);

        return view;
    }

    private void initView(View view) {
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

        noteListView = (ListViewExt) view.findViewById(R.id.noteListView);

        getData();
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
                    getData();
                    swipe.setRefreshing(false);
                    break;
            }
        }
    };


    private void getData() {
        dialog = new LoadingDialog(getActivity());
        if (!getActivity().isFinishing()) {
            dialog.show();
        }
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
                NoteLvAdapter adapter = new NoteLvAdapter(getActivity(), list);

                noteListView.setAdapter(adapter);
                noteListView.setEmptyView(emptyView);
                dialog.dismiss();
                ToNoteDetailUtils.toDetail(getActivity(),noteListView,list);
            }

            @Override
            public void onError(int i, String s) {
                dialog.dismiss();
            }
        });
    }

}
