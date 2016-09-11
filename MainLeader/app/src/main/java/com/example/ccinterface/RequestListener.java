package com.example.ccinterface;

import com.android.volley.error.VolleyError;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/7/25.
 */
public interface RequestListener {
    void dealDataSuccess(JSONObject jsonObject);
    void dealDataFail(VolleyError volleyError);
}
