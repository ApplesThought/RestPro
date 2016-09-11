package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.adapter.ArtileGridViewAdapter;

public class ArticleClassActivity extends KLBaseActivity {

    private GridView gv_article;
    private ArtileGridViewAdapter adapter;

    private TextView introTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_class);

        setToolBarName("文章分类");

        intView();
    }

    private void intView() {
        gv_article = (GridView) findViewById(R.id.gv_article);
        adapter = new ArtileGridViewAdapter(this);
        gv_article.setAdapter(adapter);

        initEvent();
    }

    private void initEvent() {
        gv_article.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ArticleClassActivity.this, ArticleActivity.class);
                View v = adapter.getView(position,view,null);
                introTxt = (TextView) v.findViewById(R.id.intro);
                String type = introTxt.getText().toString().trim();
                Log.i("Article", type);
                intent.putExtra("class",type);
                startActivity(intent);
            }
        });
    }
}
