package com.example.ccinterface;

import android.content.Context;
import android.content.DialogInterface;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by yinhaojun on 15/11/6.
 */
public interface DialogListener {

    void showProgressDialog(Context context, String title);
    void showSuccessDialog(Context context, String title, String btnStr, SweetAlertDialog.OnSweetClickListener onConfirmListener);
    void showSuccessDialog(Context context, String title, String btnStr);
    void showSuccessDialog(Context context, String title);
    void showSuccessDialog(Context context, String title, DialogInterface.OnDismissListener onDismissListener);

    void showWarningDialog(Context context, String title, String sureBtnStr, String cancelBtnStr, SweetAlertDialog.OnSweetClickListener onConfrimListener, SweetAlertDialog.OnSweetClickListener onCancelListener);
    void showWarningDialog(Context context, String title, String sureBtnStr, String cancelBtnStr, SweetAlertDialog.OnSweetClickListener onConfrimListener);
    void showWarningDialog(Context context, String title, String msg, String sureBtnStr, String cancelBtnStr, SweetAlertDialog.OnSweetClickListener onConfrimListener);
    void showWarningDialogWithoutCancel(Context context, String title, String msg, SweetAlertDialog.OnSweetClickListener onConfrimListener);
    void showWarningDialogWithoutCancel(Context context, String title, SweetAlertDialog.OnSweetClickListener onConfrimListener);

    void showWarningDialog(Context context, String title, SweetAlertDialog.OnSweetClickListener onConfrimListener);

    void showInputDialog(Context context, String title, String btnStr, SweetAlertDialog.OnSweetClickListener onSweetClickListener);
    void showInputDialog(Context context, String title, SweetAlertDialog.OnSweetClickListener onSweetClickListener);

    void dimissDialog();

}
