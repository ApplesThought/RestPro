package com.example.error;

/**
 * Created by ${hcc} on 2016/04/20.
 */
public class ErrorList {
    public static String error(int errorCode){
        String errorMsg = "请检查网络是否可用";

        switch (errorCode){
            case 101:
                errorMsg = "用户名或密码不正确";
                break;

            case 109:
                errorMsg = "用户名或密码为空";
                break;

            case 202:
                errorMsg = "用户名已经存在";
                break;

            case 210:
                errorMsg = "旧密码不正确";
                break;

            case 304:
                errorMsg = "用户名或密码为空";
                break;

            case 9016:
                errorMsg = "无网络连接，请检查您的手机网络.";
                break;

            case 9018:
                errorMsg = "新密码不能为空";
                break;
        }

        return errorMsg;
    }
}
