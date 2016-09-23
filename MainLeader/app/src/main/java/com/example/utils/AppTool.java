package com.example.utils;

import java.util.Map;

/**
 * Created by hcc on 2016/7/25.
 */
public class AppTool {
    public static String generateMapToString(String baseUrl, Map<String, Object> params) {
        if (params == null) {
            if (baseUrl.endsWith("?")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }
            return baseUrl;
        } else {
            StringBuilder sb = new StringBuilder(baseUrl);
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&&");
            }
            if (params.size() > 0) {
                return sb.toString().trim().substring(0, sb.length() - 2);//去除末尾的两个&&
            } else if (params.size() == 0) {
                return sb.toString().trim().substring(0, sb.length() - 1);//去除末尾的?
            }
            return sb.toString();//去除末尾的两个&&
        }
    }
}
