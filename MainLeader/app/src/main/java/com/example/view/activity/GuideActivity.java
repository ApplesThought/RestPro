package com.example.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.view.timeanimation.CustomPresentationPagerFragment;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_guide);

        findViewById(R.id.bRetry).setOnClickListener(this);
        if (savedInstanceState == null) {
            replaceTutorialFragment();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRetry:
                replaceTutorialFragment();
                break;
        }
    }


    public void replaceTutorialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new CustomPresentationPagerFragment());
        fragmentTransaction.commit();
    }
}
