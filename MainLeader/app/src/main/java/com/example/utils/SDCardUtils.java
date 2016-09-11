package com.example.utils;

import android.os.Environment;

/**
 * Created by ${hcc} on 2016/06/17.
 */
public class SDCardUtils {
    public static boolean isSdcardExisting() {//判断SD卡是否存在
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
