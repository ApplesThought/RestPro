package com.example.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.view.activity.ISaidActivity;
import com.example.view.activity.R;


public class MessFragment extends Fragment {

    private LinearLayout ll_said;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_two, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        ll_said = (LinearLayout) view.findViewById(R.id.ll_said);
        ll_said.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ISaidActivity.class);
                startActivity(intent);
            }
        });
    }
}
