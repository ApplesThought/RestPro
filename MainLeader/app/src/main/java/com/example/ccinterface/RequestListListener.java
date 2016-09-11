package com.example.ccinterface;

import com.android.volley.error.VolleyError;

import org.json.JSONArray;

/**
 * Created by Administrator on 2016/7/25.
 */
public interface RequestListListener {
    void dealListSuccess(JSONArray jsonArray);

    void dealListFail(VolleyError volleyError);
}
