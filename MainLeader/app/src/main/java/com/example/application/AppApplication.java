package com.example.application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.BuildConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import java.util.LinkedHashMap;
import java.util.Map;

public class AppApplication extends Application {

    private static AppApplication appApplication;
    private RequestQueue requestQueue;
    public static final String TAG = "VolleyPatterns";
    private Map<String, Integer> mFaceMap = new LinkedHashMap<String, Integer>();

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
    }

    public static synchronized AppApplication getAppApplication() {
        return appApplication;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext(), new OkHttpStack());
        }
        return requestQueue;
    }


    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        if (BuildConfig.DEBUG) {
            VolleyLog.d("Adding request to queue: %s", req.getUrl());
        }

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    public Map<String, Integer> getFaceMap() {
        if (!mFaceMap.isEmpty())
            return mFaceMap;
        return null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
