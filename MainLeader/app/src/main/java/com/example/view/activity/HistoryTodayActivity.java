package com.example.view.activity;

import android.os.Bundle;


public class HistoryTodayActivity extends KLBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_today);

        setToolBarName("那年今日");
    }
}
