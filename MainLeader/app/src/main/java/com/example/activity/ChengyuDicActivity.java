package com.example.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.error.VolleyError;
import com.example.adapter.SimpleTextAdapter;
import com.example.application.AppUrl;
import com.example.ccinterface.RequestListener;
import com.example.customview.MyGridView;
import com.example.manager.RequestManager;
import com.example.utils.DialogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChengyuDicActivity extends KLBaseActivity {

    private EditText searchEdt;
    private ImageView searchImg;
    private TextView pinYinTv, yufaTv, cyExplainTv, yzExplainTv, fromTv, exampleTv;
    private TextView wordOne, wordTwo, wordThree, wordFour;
    private MyGridView synonymGv, antonymGv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chengyu_dic);

        setToolBarName("成语词典");

        initView();

        clickEvent();
    }


    private void initView() {
        searchEdt = (EditText) findViewById(R.id.searchEdt);
        searchImg = (ImageView) findViewById(R.id.searchImg);
        pinYinTv = (TextView) findViewById(R.id.pinYinTv);
        yufaTv = (TextView) findViewById(R.id.yufaTv);
        cyExplainTv = (TextView) findViewById(R.id.cyExplainTv);
        yzExplainTv = (TextView) findViewById(R.id.yzExplainTv);
        fromTv = (TextView) findViewById(R.id.fromTv);
        exampleTv = (TextView) findViewById(R.id.exampleTv);

        wordOne = (TextView) findViewById(R.id.wordOne);
        wordTwo = (TextView) findViewById(R.id.wordTwo);
        wordThree = (TextView) findViewById(R.id.wordThree);
        wordFour = (TextView) findViewById(R.id.wordFour);

        synonymGv = (MyGridView) findViewById(R.id.synonymGv);
        antonymGv = (MyGridView) findViewById(R.id.antonymGv);
    }

    private void clickEvent() {
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEdt.setFocusable(true);
                judgeEdtAndGetData();
            }
        });
    }

    private void judgeEdtAndGetData() {
        String word = searchEdt.getText().toString();
        if (TextUtils.isEmpty(word)) {
            DialogUtils.getInstance().showWarningDialogWithoutCancel(this, "请输入四字成语", null);
        } else if (word.contains(" ")) {
            DialogUtils.getInstance().showWarningDialogWithoutCancel(this, "禁止输入空格，请重新输入", null);
        } else {
            getCYData(word);
        }
    }

    private void getCYData(final String w) {
        DialogUtils.getInstance().showProgressDialog(ChengyuDicActivity.this, "努力获取中");
        Map<String, Object> params = new HashMap<>();
        params.put("word", w);
        params.put("key", AppUrl.cyAppKey);
        RequestManager.getRequestManager().dealDataByGet(this, AppUrl.cyBaseUrl, params, new RequestListener() {
            @Override
            public void dealDataSuccess(JSONObject jsonObject) {
                DialogUtils.getInstance().dimissDialog();
                parseJson(jsonObject);
            }

            @Override
            public void dealDataFail(VolleyError volleyError) {
                DialogUtils.getInstance().dimissDialog();
            }
        });
    }

    private void parseJson(JSONObject jsonObject) {
        String reason = jsonObject.optString("reason");
        if (reason.equals("success")) {
            setFourWords();
            searchEdt.setText("");
            JSONObject resultObj = jsonObject.optJSONObject("result");
            String pinyin = resultObj.optString("pinyin");
            String chengyujs = resultObj.optString("chengyujs");
            String from_ = resultObj.optString("from_");
            String example = resultObj.optString("example");
            String yufa = resultObj.optString("yufa");
            String yinzhengjs = resultObj.optString("yinzhengjs");

            pinYinTv.setText("[ " + pinyin + " ]");
            if (!TextUtils.isEmpty(chengyujs)) {
                cyExplainTv.setText(chengyujs);
            } else {
                cyExplainTv.setText("无");
            }

            fromTv.setText(from_);

            if (example.equals("null")) {
                exampleTv.setText("无");
            } else {
                exampleTv.setText(example);
            }

            yufaTv.setText(yufa.trim());

            if (!TextUtils.isEmpty(yinzhengjs)) {
                yzExplainTv.setText(yinzhengjs);
            } else {
                yzExplainTv.setText("无");
            }

            JSONArray tyArray = resultObj.optJSONArray("tongyi");
            JSONArray fyArray = resultObj.optJSONArray("fanyi");
            List<String> tyList = new ArrayList<>();
            List<String> fyList = new ArrayList<>();

            if (tyArray != null) {
                for (int i = 0; i < tyArray.length(); i++) {
                    String tyStr = tyArray.optString(i);
                    tyList.add(tyStr);
                }
            }

            if (fyArray != null) {
                for (int i = 0; i < fyArray.length(); i++) {
                    String fyStr = fyArray.optString(i);
                    fyList.add(fyStr);
                }
            }


            SimpleTextAdapter tyAdapter = new SimpleTextAdapter(this, tyList);
            SimpleTextAdapter fyAdapter = new SimpleTextAdapter(this, fyList);

            synonymGv.setAdapter(tyAdapter);
            antonymGv.setAdapter(fyAdapter);
        } else {
            DialogUtils.getInstance().showWarningDialogWithoutCancel(this, "貌似不是一个成语哦", null);
        }
    }

    private void setFourWords() {
        String w = searchEdt.getText().toString();
        String w1 = w.substring(0, 1);
        String w2 = w.substring(1, 2);
        String w3 = w.substring(2, 3);
        String w4 = w.substring(3, 4);

        wordOne.setText(w1);
        wordTwo.setText(w2);
        wordThree.setText(w3);
        wordFour.setText(w4);
    }
}
