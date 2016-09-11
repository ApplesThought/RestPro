package com.example.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.example.ccinterface.DialogListener;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class DialogUtils implements DialogListener {

    private SweetAlertDialog dialog;
    private static DialogUtils dialogManager = new DialogUtils();
    private DialogUtils(){}
    public static DialogUtils getInstance()
    {
        return dialogManager;
    }

    @Override
    public void showProgressDialog(Context context, String title) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText(title);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void showSuccessDialog(Context context, String title, String btnStr, SweetAlertDialog.OnSweetClickListener onConfirmListener) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText(title);
        dialog.setConfirmText(btnStr);
        dialog.setConfirmClickListener(onConfirmListener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void showSuccessDialog(Context context, String title) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText(title);
        dialog.setConfirmText("完成");
        dialog.setConfirmClickListener(onCancelListener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void showSuccessDialog(Context context, String title, String btnStr) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText(title);
        dialog.setConfirmText(btnStr);
        dialog.setConfirmClickListener(onCancelListener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void showSuccessDialog(Context context, String title, DialogInterface.OnDismissListener onDismissListener) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText(title);
        dialog.setConfirmText("完成");
        dialog.setConfirmClickListener(onCancelListener);
        dialog.setOnDismissListener(onDismissListener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void showWarningDialog(Context context, String title, String sureBtnStr, String cancelBtnStr, SweetAlertDialog.OnSweetClickListener onConfrimListener, SweetAlertDialog.OnSweetClickListener onCancelListener) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(title);
        dialog.setConfirmText(sureBtnStr);
        dialog.setCancelText(cancelBtnStr);
        dialog.setConfirmClickListener(onConfrimListener);
        dialog.setCancelClickListener(onCancelListener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void showWarningDialog(Context context, String title, String sureBtnStr, String cancelBtnStr, SweetAlertDialog.OnSweetClickListener onConfrimListener) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(title);
        dialog.setConfirmText(sureBtnStr);
        dialog.setCancelText(cancelBtnStr);
        dialog.setConfirmClickListener(onConfrimListener);
        dialog.setCancelClickListener(onCancelListener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void showWarningDialog(Context context, String title, String msg, String sureBtnStr, String cancelBtnStr, SweetAlertDialog.OnSweetClickListener onConfrimListener) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText(msg);
        dialog.setConfirmText(sureBtnStr);
        dialog.setCancelText(cancelBtnStr);
        dialog.setConfirmClickListener(onConfrimListener);
        dialog.setCancelClickListener(onCancelListener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void showWarningDialog(Context context, String title, SweetAlertDialog.OnSweetClickListener onConfrimListener) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(title);
        dialog.setConfirmText("确定");
        dialog.setCancelText("取消");
        dialog.setConfirmClickListener(onConfrimListener);
        dialog.setCancelClickListener(onCancelListener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void showWarningDialogWithoutCancel(Context context, String title, String msg, SweetAlertDialog.OnSweetClickListener onConfrimListener) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText(msg);
        dialog.setConfirmText("确定");
        dialog.setConfirmClickListener(onConfrimListener);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void showWarningDialogWithoutCancel(Context context, String title, SweetAlertDialog.OnSweetClickListener onConfrimListener) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(title);
        dialog.setConfirmText("确定");
        dialog.setConfirmClickListener(onConfrimListener);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void showInputDialog(Context context, String title, String btnStr, SweetAlertDialog.OnSweetClickListener onSweetClickListener) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText(title);
        dialog.setConfirmText("完成");
        dialog.setConfirmClickListener(onSweetClickListener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void showInputDialog(Context context, String title, SweetAlertDialog.OnSweetClickListener onSweetClickListener) {
        dimissDialog();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.INPUT_TYPE);
        dialog.setTitleText(title);
        dialog.setConfirmText("确定");
        dialog.setConfirmClickListener(onSweetClickListener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void dimissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private SweetAlertDialog.OnSweetClickListener onCancelListener = new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            dimissDialog();
        }
    };


}
