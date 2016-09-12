package com.example.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/7/27.
 */
public class SPManager {
    private static SPManager SharedPreferencesManager;

    private static final String USER_INFO = "USER_INFO";

    //key
    public static String KEY_PHONE = "phone";
    public static String KEY_CITY = "city";
    public static String KEY_PROVINCE = "province";
    public static String KEY_HOME_NET = "home_net";
    public static String KEY_HOME_CAIJING = "home_caijing";
    public static String KEY_HOME_GUOJI = "home_guoji";
    public static String KEY_BAIDU_GUONEI = "baidu_guonei";
    public static String KEY_BAIDU_DIANYING = "baidu_dianying";
    public static String KEY_BAIDU_GAME = "baidu_game";
    public static String KEY_BAIDU_TV = "baidu_tv";
    public static String KEY_HOME_JUNSHI = "home_junshi";
    public static String KEY_HOME_KEJI = "home_keji";
    public static String KEY_HOME_QUTU = "home_qutu";
    public static String KEY_HOME_SHEHUI = "home_shehui";
    public static String KEY_HOME_EDU = "home_edu";
    public static String KEY_HOME_COMPUTER = "home_computer";
    public static String KEY_HOME_TIYU = "home_tiyu";
    public static String KEY_HOME_YULE = "home_yule";
    public static String KEY_HOME_WEIXIN = "home_weixin";
    public static String KEY_HOME_XIAOHUA = "home_xiaohua";
    public static String KEY_HOME_CAR = "home_car";
    public static String KEY_HOME_HOUSE = "home_house";
    public static String KEY_HOME_LICAI = "home_licai";
    public static String KEY_HOME_WOMEN = "home_women";
    public static String KEY_HOME_MEIRONG = "home_meirong";
    public static String KEY_HOME_EMOTION = "home_emotion";
    public static String KEY_HOME_HEALTH = "home_health";
    public static String KEY_HOME_SHUMA = "home_shuma";
    public static String KEY_HOME_KEPU = "home_kepu";
    public static String KEY_FOOD_CLASSIFY = "food_classify";//菜谱分类

    public static SPManager getInstance() {
        if (SharedPreferencesManager == null) {
            SharedPreferencesManager = new SPManager();
        }
        return SharedPreferencesManager;
    }

    public void saveString(Context context, String key, String value) {
        boolean isSaved = false;
        do {
            SharedPreferences sharedPreferences = context.getSharedPreferences(USER_INFO, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            isSaved = editor.commit();
        } while (!isSaved);
    }

    public void saveBoolean(Context context, String key, boolean value) {
        boolean isSaved = false;
        do {
            SharedPreferences sharedPreferences = context.getSharedPreferences(USER_INFO, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            isSaved = editor.commit();
        } while (!isSaved);
    }

    public void saveInt(Context context, String key, int value) {
        boolean isSaved = false;
        do {
            SharedPreferences sharedPreferences = context.getSharedPreferences(USER_INFO, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            isSaved = editor.commit();
        } while (!isSaved);
    }

    public String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_INFO, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_INFO, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_INFO, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }

    public void clear(Context context) {
        String phone = getString(context, KEY_PHONE);
        String city = getString(context, KEY_CITY);
        String province = getString(context, KEY_PROVINCE);
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_INFO, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        saveString(context, KEY_PHONE, phone);
        saveString(context, KEY_CITY, city);
        saveString(context, KEY_PROVINCE, province);
    }

//    public void saveMyUser(Context context, MyUser myUser) {
//        saveString(context, KEY_NAME, returnLoginInfo.getName());
//        saveString(context, KEY_LOGO_URL, returnLoginInfo.getPhoto());
//        saveString(context, KEY_ADDRESS, returnLoginInfo.getAddress());
//        saveInt(context, KEY_DATE_AMOUNT, returnLoginInfo.getDate_amount());
//        saveString(context, KEY_GENDER, returnLoginInfo.getGender());
//        saveInt(context, KEY_GOLDCOIN, returnLoginInfo.getGoldcoin_amount());
//        saveInt(context, KEY_USERID, returnLoginInfo.getUser_id());
//        saveInt(context, KEY_LEVEL, returnLoginInfo.getLevel());
//        saveInt(context, KEY_UN_MESSAGE, returnLoginInfo.getUn_message());
//        saveInt(context, KEY_UN_ORDER, returnLoginInfo.getUn_order());
//        saveInt(context, KEY_UN_COUPON, returnLoginInfo.getUn_coupon());
//        saveInt(context, KEY_COMMENT_AMOUNT, returnLoginInfo.getComment_amount());
//        saveBoolean(context, KEY_SHOW_FLOW, returnLoginInfo.isShow_flow());
//    }

}
