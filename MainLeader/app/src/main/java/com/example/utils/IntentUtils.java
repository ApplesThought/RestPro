package com.example.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by ${hcc} on 2016/06/12.
 */
public class IntentUtils {
    public static void toGoalAcrivity(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    public static void toGoalAcrivityWithBundle(Context context, Class<?> clazz, Bundle bundle) {
        bundle.putAll(bundle);
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
