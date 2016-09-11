package com.example.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ${hcc} on 2016/06/17.
 */
public class SaveFileUtils {
    public static String saveBitMapToFile(Context context, String fileName, Bitmap bitmap, boolean isCover) {
        if (null == context || null == bitmap) {
            return null;
        }
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        FileOutputStream fOut = null;
        try {
            File file = null;
            String fileDstPath = "";
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                // 保存到sd卡
                fileDstPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "MyFile" + File.separator + fileName;

                File homeDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "MyFile" + File.separator);
                if (!homeDir.exists()) {
                    homeDir.mkdirs();
                }
            } else {
                // 保存到file目录
                fileDstPath = context.getFilesDir().getAbsolutePath()
                        + File.separator + "MyFile" + File.separator + fileName;

                File homeDir = new File(context.getFilesDir().getAbsolutePath()
                        + File.separator + "MyFile" + File.separator);
                if (!homeDir.exists()) {
                    homeDir.mkdir();
                }
            }

            file = new File(fileDstPath);

            if (!file.exists() || isCover) {
                // 简单起见，先删除老文件，不管它是否存在。
                file.delete();

                fOut = new FileOutputStream(file);
                if (fileName.endsWith(".jpg")) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fOut);
                } else {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                }
                fOut.flush();
                bitmap.recycle();
            }

            Log.i("FileSave", "saveDrawableToFile " + fileName
                    + " success, save path is " + fileDstPath);
            return fileDstPath;
        } catch (Exception e) {
            Log.e("FileSave", "saveDrawableToFile: " + fileName + " , error", e);
            return null;
        } finally {
            if (null != fOut) {
                try {
                    fOut.close();
                } catch (Exception e) {
                    Log.e("FileSave", "saveDrawableToFile, close error", e);
                }
            }
        }
    }


    public static void saveBitmap(Bitmap bitmap, String bitName) throws IOException {
        File file = new File("/sdcard/DCIM/Camera/" + bitName+".JPEG");
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
