package com.example.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ${hcc} on 2016/06/17.
 */
public class ToastUtils {
    public static void showToast(Context context,String tips) {
        Toast.makeText(context,tips,Toast.LENGTH_LONG).show();
    }
}
