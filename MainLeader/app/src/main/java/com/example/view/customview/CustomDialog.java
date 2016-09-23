package com.example.view.customview;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.ccinterface.IAlertDialogListener;
import com.example.view.activity.R;

/**
 * Created by ${hcc} on 2016/06/16.
 * 显示自定义对话框
 */
public class CustomDialog {

    private static AlertDialog dialog;

    public static void showOkOrCancleDialog(Context context, String message,
                                            final IAlertDialogListener listener) {
        View dialogView = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.custom_ok_cancle_dialog, null);

        TextView dialog_message = (TextView) dialogView.findViewById(R.id.dialog_message);
        TextView dialog_cancel = (TextView) dialogView.findViewById(R.id.dialog_cancel);
        TextView dialog_comfire = (TextView) dialogView.findViewById(R.id.dialog_comfire);

        dialog_message.setText(message);

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        dialog_comfire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                /*事件回调*/
                if (listener != null) {
                    listener.onClick();
                }
            }
        });


        /*为dialog设置View*/
        builder.setView(dialogView);
        dialog = builder.create();

        /*显示对话框*/
        dialog.show();
    }


    public static void showTipsDialog(Context context, String message,
                                      final IAlertDialogListener listener) {
        View dialogView = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.custom_tips_dialog, null);

        TextView dialog_message = (TextView) dialogView.findViewById(R.id.dialog_message);
        TextView dialog_comfirm = (TextView) dialogView.findViewById(R.id.dialog_comfirm);

        dialog_message.setText(message);

        dialog_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                /*事件回调*/
                if (listener != null) {
                    listener.onClick();
                }
            }
        });


        /*为dialog设置View*/
        builder.setView(dialogView);
        dialog = builder.create();

        /*显示对话框*/
        dialog.show();
    }


    public static void showSelectDialog(Context context, String message, String message2, String message3,
                                        final IAlertDialogListener listener1, final IAlertDialogListener listener2,
                                        final IAlertDialogListener listener3) {
        View dialogView = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_selecct, null);

        TextView takePhotoTxt = (TextView) dialogView.findViewById(R.id.takePhotoTxt);
        TextView selectInAlbumTxt = (TextView) dialogView.findViewById(R.id.selectInAlbumTxt);
        TextView lookBigTxt = (TextView) dialogView.findViewById(R.id.lookBigTxt);

        takePhotoTxt.setText(message);
        selectInAlbumTxt.setText(message2);
        lookBigTxt.setText(message3);

        takePhotoTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                /*事件回调*/
                if (listener1 != null) {
                    listener1.onClick();
                }
            }
        });
        selectInAlbumTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                /*事件回调*/
                if (listener2 != null) {
                    listener2.onClick();
                }
            }
        });

        lookBigTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                /*事件回调*/
                if (listener3 != null) {
                    listener3.onClick();
                }
            }
        });
        /*为dialog设置View*/
        builder.setView(dialogView);
        dialog = builder.create();
        /*显示对话框*/
        dialog.show();
    }


    /*发表说说选择对话框*/
    public static void showDialog(Context context, String message, String message2,
                                  final IAlertDialogListener listener1, final IAlertDialogListener listener2) {
        View dialogView = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_selecct_talk, null);

        TextView takePhotoTxt = (TextView) dialogView.findViewById(R.id.takePhotoTxt);
        TextView selectInAlbumTxt = (TextView) dialogView.findViewById(R.id.selectInAlbumTxt);

        takePhotoTxt.setText(message);
        selectInAlbumTxt.setText(message2);

        takePhotoTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                /*事件回调*/
                if (listener1 != null) {
                    listener1.onClick();
                }
            }
        });
        selectInAlbumTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                /*事件回调*/
                if (listener2 != null) {
                    listener2.onClick();
                }
            }
        });

        /*为dialog设置View*/
        builder.setView(dialogView);
        dialog = builder.create();
        /*显示对话框*/
        dialog.show();
    }


    public static void showAddressSelectDialog(Context context,
                                               final IAlertDialogListener listener) {
        View dialogView = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.custom_addressselect_dialog, null);

        TextView dialog_comfirm = (TextView) dialogView.findViewById(R.id.dialog_comfirm);

        dialog_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                /*事件回调*/
                if (listener != null) {
                    listener.onClick();
                }
            }
        });


        /*为dialog设置View*/
        builder.setView(dialogView);
        dialog = builder.create();

        /*显示对话框*/
        dialog.show();
    }
}
