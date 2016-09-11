package com.example.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ccinterface.IAlertDialogListener;
import com.example.customview.CircleTextImageView;
import com.example.customview.CustomDialog;
import com.example.entity.MyUser;
import com.example.fragment.ArticleInfoFragment;
import com.example.fragment.LaughInfoFragment;
import com.example.fragment.MainPageInfoFragment;
import com.example.fragment.TalkInfoFragment;
import com.example.utils.SDCardUtils;
import com.example.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MyInfoActivity extends AppCompatActivity implements
        ViewPager.OnPageChangeListener, View.OnClickListener {
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
    private static final int PHOTO_REQUEST_CUT = 3;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private TextView backText;

    private ViewPager viewPager;
    private TextView info_main, info_talk, info_article, info_laugh;
    private List<Fragment> views;

    private MainPageInfoFragment mainPageInfoFragment;
    private TalkInfoFragment talkInfoFragment;
    private ArticleInfoFragment articleInfoFragment;
    private LaughInfoFragment laughInfoFragment;

    private CircleTextImageView img_info_photo;
    private TextView txt_info_name, txt_info_intro;


    private String url;
    private File outputImage;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_my_info);


        initView();

        initEvent();

        initData();
    }

    private void initData() {
        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        String objectId = user.getObjectId();
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(this, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                String nick = "", signal = "", name = "", gender = "",photoStr = "";
                for (MyUser u : list) {
                    nick = u.getNick();
                    signal = u.getSignal();
                    name = u.getUsername();
                    photoStr = u.getUserPhoto();
                }
                if (TextUtils.isEmpty(nick)) {
                    txt_info_name.setText(name);
                } else {
                    txt_info_name.setText(nick);
                }
                if (TextUtils.isEmpty(signal)) {
                    txt_info_intro.setText("简介:暂无介绍");
                } else {
                    txt_info_intro.setText("简介:" + signal);
                }

                /*加载头像*/
                if (TextUtils.isEmpty(photoStr)) {
                    img_info_photo.setImageResource(R.drawable.health_guide_men_selected);
                } else {
                    Picasso.with(MyInfoActivity.this).load(photoStr).into(img_info_photo);
                }
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.showToast(MyInfoActivity.this, "信息获取失败");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initEvent() {
        viewPager.setOnPageChangeListener(this);

        info_main.setOnClickListener(this);
        info_talk.setOnClickListener(this);
        info_article.setOnClickListener(this);
        info_laugh.setOnClickListener(this);

        img_info_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDialog();
            }
        });
    }

    private void initView() {
        img_info_photo = (CircleTextImageView) findViewById(R.id.img_info_photo);

        txt_info_name = (TextView) findViewById(R.id.txt_info_name);
        txt_info_intro = (TextView) findViewById(R.id.txt_info_intro);

        backText = (TextView) findViewById(R.id.backText);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        info_main = (TextView) findViewById(R.id.info_main);
        info_talk = (TextView) findViewById(R.id.info_talk);
        info_article = (TextView) findViewById(R.id.info_article);
        info_laugh = (TextView) findViewById(R.id.info_laugh);

        viewPager = (ViewPager) findViewById(R.id.vp_mainPage);
        views = new ArrayList<>();

        /*实例化四个Fragment*/
        mainPageInfoFragment = new MainPageInfoFragment();
        talkInfoFragment = new TalkInfoFragment();
        articleInfoFragment = new ArticleInfoFragment();
        laughInfoFragment = new LaughInfoFragment();
        views.add(mainPageInfoFragment);
        views.add(talkInfoFragment);
        views.add(articleInfoFragment);
        views.add(laughInfoFragment);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public Fragment getItem(int position) {
                return views.get(position);
            }
        };
        viewPager.setAdapter(adapter);

        onclick();
    }

    private void onclick() {
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                info_main.setBackgroundColor(Color.parseColor("#FF8000"));
                info_main.setTextColor(getResources().getColor(R.color.white));

                info_talk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_talk.setTextColor(getResources().getColor(R.color.black));
                info_article.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_article.setTextColor(getResources().getColor(R.color.black));
                info_laugh.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_laugh.setTextColor(getResources().getColor(R.color.black));

                break;

            case 1:
                info_talk.setBackgroundColor(Color.parseColor("#FF8000"));
                info_talk.setTextColor(getResources().getColor(R.color.white));

                info_main.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_main.setTextColor(getResources().getColor(R.color.black));
                info_article.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_article.setTextColor(getResources().getColor(R.color.black));
                info_laugh.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_laugh.setTextColor(getResources().getColor(R.color.black));
                break;

            case 2:
                info_article.setBackgroundColor(Color.parseColor("#FF8000"));
                info_article.setTextColor(getResources().getColor(R.color.white));

                info_talk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_talk.setTextColor(getResources().getColor(R.color.black));
                info_main.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_main.setTextColor(getResources().getColor(R.color.black));
                info_laugh.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_laugh.setTextColor(getResources().getColor(R.color.black));
                break;

            case 3:
                info_laugh.setBackgroundColor(Color.parseColor("#FF8000"));
                info_laugh.setTextColor(getResources().getColor(R.color.white));

                info_article.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_article.setTextColor(getResources().getColor(R.color.black));
                info_talk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_talk.setTextColor(getResources().getColor(R.color.black));
                info_main.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_main.setTextColor(getResources().getColor(R.color.black));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_main:
                info_main.setBackgroundColor(Color.parseColor("#FF8000"));
                info_main.setTextColor(getResources().getColor(R.color.white));

                info_talk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_talk.setTextColor(getResources().getColor(R.color.black));
                info_article.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_article.setTextColor(getResources().getColor(R.color.black));
                info_laugh.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_laugh.setTextColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(0);
                break;

            case R.id.info_talk:
                info_talk.setBackgroundColor(Color.parseColor("#FF8000"));
                info_talk.setTextColor(getResources().getColor(R.color.white));

                info_main.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_main.setTextColor(getResources().getColor(R.color.black));
                info_article.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_article.setTextColor(getResources().getColor(R.color.black));
                info_laugh.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_laugh.setTextColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(1);
                break;

            case R.id.info_article:
                info_article.setBackgroundColor(Color.parseColor("#FF8000"));
                info_article.setTextColor(getResources().getColor(R.color.white));

                info_talk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_talk.setTextColor(getResources().getColor(R.color.black));
                info_main.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_main.setTextColor(getResources().getColor(R.color.black));
                info_laugh.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_laugh.setTextColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(2);
                break;

            case R.id.info_laugh:
                info_laugh.setBackgroundColor(Color.parseColor("#FF8000"));
                info_laugh.setTextColor(getResources().getColor(R.color.white));

                info_article.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_article.setTextColor(getResources().getColor(R.color.black));
                info_talk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_talk.setTextColor(getResources().getColor(R.color.black));
                info_main.setBackgroundColor(Color.parseColor("#FFFFFF"));
                info_main.setTextColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(3);
                break;
        }
    }


    private void showSelectDialog() {
        CustomDialog.showSelectDialog(this, "拍照", "从相册选择", "查看大图", new IAlertDialogListener() {
            @Override
            public void onClick() {//第一项点击
                takePicture();
            }
        }, new IAlertDialogListener() {
            @Override
            public void onClick() {//第二项点击
                selectPhoto();

            }
        }, new IAlertDialogListener() {
            @Override
            public void onClick() {
                ToastUtils.showToast(MyInfoActivity.this,"查看大图");
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
            startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
        } else {
            ToastUtils.showToast(this, "请插入SD卡");
        }
    }


    /*从相册选择*/
    private void selectPhoto(){
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
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        //此处调用了图片选择器
        //如果直接写intent.setDataAndType("image/*");
        //调用的是系统图库
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == android.app.Activity.RESULT_OK) {
            switch (requestCode) {
                case PHOTO_REQUEST_TAKEPHOTO:
                    cropImageUri(imageUri, 55, 55, PHOTO_REQUEST_CUT);
                    break;

                case PHOTO_REQUEST_GALLERY:
                    if (resultCode == RESULT_OK) {
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(data.getData(), "image");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, PHOTO_REQUEST_CUT);
                    }
                    break;

                case PHOTO_REQUEST_CUT:
                    if (resultCode == RESULT_OK) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                            img_info_photo.setImageBitmap(bitmap);
                            String picPath = imageUri.getPath();
                            final BmobFile bmobFile = new BmobFile(new File(picPath));
                            bmobFile.uploadblock(MyInfoActivity.this, new UploadFileListener() {
                                @Override
                                public void onSuccess() {
                                    MyUser bmobUser = BmobUser.getCurrentUser(MyInfoActivity.this, MyUser.class);
                                    //bmobFile.getFileUrl(context)--返回的上传文件的完整地址
                                    url = bmobFile.getFileUrl(MyInfoActivity.this);
                                    bmobUser.setUserPhoto(url);
                                    bmobUser.setUserImg(bmobFile);
                                    if (url != null) {
                                        bmobUser.update(MyInfoActivity.this, new UpdateListener() {
                                            @Override
                                            public void onSuccess() {
                                                ToastUtils.showToast(MyInfoActivity.this,"头像上传成功");
                                            }

                                            @Override
                                            public void onFailure(int i, String s) {

                                            }
                                        });
                                    }
                                }
                                @Override
                                public void onProgress(Integer value) {
                                    // 返回的上传进度（百分比）
                                }

                                @Override
                                public void onFailure(int code, String msg) {
                                    ToastUtils.showToast(MyInfoActivity.this, "头像上传失败");
                                }
                            });
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }


    /*裁剪图片*/
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }
}
