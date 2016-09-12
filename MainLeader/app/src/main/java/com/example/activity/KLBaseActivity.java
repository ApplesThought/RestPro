package com.example.activity;

import android.annotation.TargetApi;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helper.ToolBarHelper;
import com.example.addressmodel.CityModel;
import com.example.addressmodel.DistrictModel;
import com.example.addressmodel.ProvinceModel;
import com.example.service.XmlParserHandler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class KLBaseActivity extends AppCompatActivity {

    private ToolBarHelper mToolBarHelper;
    public Toolbar toolBar;

    private TextView backText, titleText;
    private TextView lostTxt, foundTxt;

    private ImageView addImg, comfirmImg, refresh;

    private LinearLayout ll_lostAndFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolBarHelper = new ToolBarHelper(this, layoutResID);
        toolBar = mToolBarHelper.getToolBar();

        //改变状态栏颜色
        getWindow().setStatusBarColor(Color.parseColor("#FF8000"));

        setContentView(mToolBarHelper.getContentView());
        /*把toolbar设置到activity中*/
        setSupportActionBar(toolBar);
        onCreateCustomToolBar(toolBar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /**
     * 设置ToolBar标题
     */
    public void setToolBarName(String title) {
        titleText.setText(title);
    }

    public void setToolBarName(int res) {
        titleText.setText(res);
    }


    /*设置标题隐藏*/
    public void setToolBarGone() {
        titleText.setVisibility(View.GONE);
    }


    public void setLostAndFoundVisiable() {
        setToolBarGone();
        ll_lostAndFound.setVisibility(View.VISIBLE);
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        /*返回*/
        backText = (TextView) view.findViewById(R.id.backText);
        /*标题*/
        titleText = (TextView) findViewById(R.id.titleText);
        lostTxt = (TextView) findViewById(R.id.lostTxt);
        foundTxt = (TextView) findViewById(R.id.foundTxt);
        ll_lostAndFound = (LinearLayout) findViewById(R.id.ll_lostAndFound);

        addImg = (ImageView) findViewById(R.id.addImg);
        comfirmImg = (ImageView) findViewById(R.id.comfirmImg);
        refresh = (ImageView) findViewById(R.id.refresh);

        clickBack();
    }


    /*编辑*/
    public void addNew(View.OnClickListener listener) {
        addImg.setVisibility(View.VISIBLE);
        addImg.setOnClickListener(listener);
    }

    /*确定*/
    public void comfirm(View.OnClickListener listener) {
        comfirmImg.setVisibility(View.VISIBLE);
        comfirmImg.setOnClickListener(listener);
    }

    /*刷新*/
    public void refresh(View.OnClickListener listener) {
        refresh.setVisibility(View.VISIBLE);
        refresh.setOnClickListener(listener);
    }

    /*返回监听事件*/
    public void clickBack() {
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void lostClick(View.OnClickListener listener) {
        lostTxt.setOnClickListener(listener);
    }

    public void foundClick(View.OnClickListener listener) {
        foundTxt.setOnClickListener(listener);
    }


    public void setLostSelect() {
        lostTxt.setBackgroundResource(R.drawable.left_select_lost);
        foundTxt.setBackgroundResource(R.drawable.right_unselect_found);
        lostTxt.setTextColor(getResources().getColor(R.color.orange));
        foundTxt.setTextColor(getResources().getColor(R.color.white));
    }


    public void setFoundSelect() {
        lostTxt.setBackgroundResource(R.drawable.left_unselect_lost);
        foundTxt.setBackgroundResource(R.drawable.right_select_found);
        lostTxt.setTextColor(getResources().getColor(R.color.white));
        foundTxt.setTextColor(getResources().getColor(R.color.orange));
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void toast(int res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    private void onCreateCustomToolBar(Toolbar toolBar) {
        toolBar.setContentInsetsRelative(10, 10);
    }

    /* 隐藏toolbar */
    public void hideToolBar() {
        toolBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

}
