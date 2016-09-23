package com.example.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ccinterface.IAlertDialogListener;
import com.example.mvp.model.DailyMood;
import com.example.mvp.model.MyUser;
import com.example.mvp.model.Post;
import com.example.utils.IntentUtils;
import com.example.utils.SDCardUtils;
import com.example.utils.ToastUtils;
import com.example.view.customview.CustomDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class PublishTalkActivity extends KLBaseActivity {

    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
    private static final int PHOTO_REQUEST_CUT = 3;
    private static final int PHOTO_REQUEST_GALLERY = 2;

    private String url;
    private File outputImage;
    private Uri imageUri;

    private ImageView img_pic, img_addPic;
    private String path;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_talk);

        initView();
    }

    private void initView() {
        img_pic = (ImageView) findViewById(R.id.img_pic);
        img_addPic = (ImageView) findViewById(R.id.img_addPic);

        final EditText publishEdt = (EditText) findViewById(R.id.publishEdt);
        final EditText publishTitleEdt = (EditText) findViewById(R.id.publishTitleEdt);
        Button publishBtn = (Button) findViewById(R.id.publishBtn);

        final String publicType = getIntent().getStringExtra("type");
        setToolBarName(publicType);

        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pubContent = publishEdt.getText().toString().trim();
                String pubTitle = publishTitleEdt.getText().toString().trim();
                if (TextUtils.isEmpty(pubContent)) {
                    CustomDialog.showTipsDialog(PublishTalkActivity.this, "内容不能为空", null);
                } else {
                    if (publicType.equals("搞笑段子")) {
                        MyUser user = BmobUser.getCurrentUser(PublishTalkActivity.this, MyUser.class);
                        // 创建帖子信息
                        Post post = new Post();
                        post.setTalkContent(pubContent);
                        //添加一对一关联
                        post.setUsername(user);
                        post.setTalkTitle(pubTitle);
                        post.setType(publicType);
                        post.save(PublishTalkActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                toast("发布成功");
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                toast("发布失败" + s);
                            }
                        });
                    } else {
                        /*即兴说说*/
                        final MyUser user = BmobUser.getCurrentUser(PublishTalkActivity.this, MyUser.class);
                        // 创建帖子信息
                        final DailyMood mood = new DailyMood();
                        mood.setContent(pubContent);
                        //添加一对一关联
                        mood.setUsername(user);


                        if (!TextUtils.isEmpty(path)) {
                            Bitmap zipBitmap = getImage(path);
                            int zipStr = zipBitmap.getAllocationByteCount();
                            Log.i("SIZE",zipStr+"压缩");
                            final BmobFile bmobFile = new BmobFile(new File(path));
                            bmobFile.uploadblock(PublishTalkActivity.this, new UploadFileListener() {
                                @Override
                                public void onSuccess() {
                                    //bmobFile.getFileUrl(context)--返回的上传文件的完整地址
                                    url = bmobFile.getFileUrl(PublishTalkActivity.this);
                                    mood.setPhotoUrl(url);
                                    mood.save(PublishTalkActivity.this, new SaveListener() {
                                        @Override
                                        public void onSuccess() {
                                            toast("发布成功");
                                            finish();
                                            IntentUtils.toGoalAcrivity(PublishTalkActivity.this, FriendStatusActivity.class);
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            toast("发布失败" + s);
                                        }
                                    });
                                    Log.i("URL", url);
                                }

                                @Override
                                public void onProgress(Integer value) {
                                    // 返回的上传进度（百分比）
                                }

                                @Override
                                public void onFailure(int code, String msg) {
                                }
                            });
                        } else {
                            /*无图片的时候*/
                            mood.save(PublishTalkActivity.this, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    toast("发布成功");
                                    finish();
                                    IntentUtils.toGoalAcrivity(PublishTalkActivity.this, FriendStatusActivity.class);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    toast("发布失败" + s);
                                }
                            });
                        }
                    }

                }
            }
        });


        img_addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDialog();
            }
        });
    }


    private void showSelectDialog() {
        CustomDialog.showDialog(this, "拍照", "从相册选择", new IAlertDialogListener() {
            @Override
            public void onClick() {//第一项点击
                takePicture();
            }
        }, new IAlertDialogListener() {
            @Override
            public void onClick() {//第二项点击
                selectPhoto();
            }
        });
    }


    /*拍照*/
    private void takePicture() {
        if (SDCardUtils.isSdcardExisting()) {
            outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageUri = Uri.fromFile(outputImage);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra("autofocus", true);// 自动对焦
            intent.putExtra("fullScreen", false);// 全屏
            intent.putExtra("showActionIcons", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, PHOTO_REQUEST_CUT);
        } else {
            ToastUtils.showToast(this, "请插入SD卡");
        }
    }


    /*从相册选择*/
    private void selectPhoto() {
        outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        Log.i("ImageUrl",imageUri+"");
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        //此处调用了图片选择器
//        如果直接写
        //调用的是系统图库
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == android.app.Activity.RESULT_OK) {
            switch (requestCode) {
                /*case PHOTO_REQUEST_TAKEPHOTO:
                    break;

                case PHOTO_REQUEST_GALLERY:
                    if (resultCode == RESULT_OK) {
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(data.getData(), "image");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, PHOTO_REQUEST_CUT);
                    }
                    break;*/

                case PHOTO_REQUEST_CUT:
                    if (resultCode == RESULT_OK) {
                        try {
                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                            int zipStr = bitmap.getAllocationByteCount();
                            Log.i("BITMAP", bitmap + "");
                            Log.i("SIZE", zipStr + "未压缩");
                            if (bitmap != null) {
                                img_pic.setVisibility(View.VISIBLE);
                                img_pic.setImageBitmap(bitmap);
                                path = imageUri.getPath();
                            } else {
                                img_pic.setVisibility(View.GONE);
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }




    /**
     * 压缩图片
     */
    private Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 4;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 4;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }


    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
