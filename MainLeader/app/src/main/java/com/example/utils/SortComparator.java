package com.example.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by ${hcc} on 2016/05/31.
 * 对List进行排序
 */
public class SortComparator implements Comparator<BmobObject> {
    @Override
    public int compare(BmobObject lhs, BmobObject rhs) {
        String createTime1 = lhs.getCreatedAt();
        String createTime2 = rhs.getCreatedAt();
        Long mill1 = dateToMillSecond(strToDate(createTime1));
        Long mill2 = dateToMillSecond(strToDate(createTime2));
        if (mill1 < mill2) {
            return 1;
        } else {
            return -1;
        }
    }


    /*字符串时间转成Date形式*/
    private Date strToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /*获取Date形式时间的毫秒值*/
    private Long dateToMillSecond(Date date) {
        return date.getTime();
    }
}
