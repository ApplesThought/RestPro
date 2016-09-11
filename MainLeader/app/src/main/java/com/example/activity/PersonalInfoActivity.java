package com.example.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.ccinterface.IAlertDialogListener;
import com.example.customview.CircleTextImageView;
import com.example.customview.CustomDialog;
import com.example.entity.MyUser;
import com.example.fragment.ArticleInfoFragment;
import com.example.fragment.LaughInfoFragment;
import com.example.fragment.NoteInfoFragment;
import com.example.fragment.TalkInfoFragment;
import com.example.utils.BlurTransformation;
import com.example.utils.IntentUtils;
import com.example.utils.SDCardUtils;
import com.example.utils.StatusBarUtil;
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

public class PersonalInfoActivity extends AppCompatActivity {

    private LinearLayout head_layout;
    private TabLayout toolbar_tab;
    private ViewPager main_vp_container;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CoordinatorLayout root_layout;

    private List<Fragment> views;
    private NoteInfoFragment noteInfoFragment;
    private TalkInfoFragment talkInfoFragment;
    private ArticleInfoFragment articleInfoFragment;
    private LaughInfoFragment laughInfoFragment;

    private CircleTextImageView img_info_photo;
    private TextView cardTv;//资料卡

    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
    private static final int PHOTO_REQUEST_CUT = 3;
    private static final int PHOTO_REQUEST_GALLERY = 2;

    private TextView txt_info_name, txt_info_intro, txt_info_nick;

    private String url;
    private File outputImage;
    private Uri imageUri;

    private AppBarLayout app_bar_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        head_layout = (LinearLayout) findViewById(R.id.head_layout);
        root_layout = (CoordinatorLayout) findViewById(R.id.root_layout);

        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id
                .collapsing_toolbar_layout);

        toolbar_tab = (TabLayout) findViewById(R.id.toolbar_tab);
        main_vp_container = (ViewPager) findViewById(R.id.main_vp_container);

        views = new ArrayList<>();
        talkInfoFragment = new TalkInfoFragment();
        articleInfoFragment = new ArticleInfoFragment();
        laughInfoFragment = new LaughInfoFragment();
        noteInfoFragment = new NoteInfoFragment();
        views.add(talkInfoFragment);
        views.add(articleInfoFragment);
        views.add(laughInfoFragment);
        views.add(noteInfoFragment);
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

        main_vp_container.setAdapter(adapter);
        main_vp_container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener
                (toolbar_tab));
        toolbar_tab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener
                (main_vp_container));
        //tablayout和viewpager建立联系为什么不用下面这个方法呢？自己去研究一下，可能收获更多
        //toolbar_tab.setupWithViewPager(main_vp_container);

        /*ImageView img_info_photo = (ImageView) findViewById(R.id.img_info_photo);
        Glide.with(this).load(R.mipmap.d_doge).bitmapTransform(new RoundedCornersTransformation(this,
                90, 0)).into(img_info_photo);*/


        initView();
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        initData();
    }*/

    private void initView() {
        cardTv = (TextView) findViewById(R.id.cardTv);
        txt_info_name = (TextView) findViewById(R.id.txt_info_name);
        txt_info_intro = (TextView) findViewById(R.id.txt_info_intro);
        txt_info_nick = (TextView) findViewById(R.id.txt_info_nick);

        img_info_photo = (CircleTextImageView) findViewById(R.id.img_info_photo);

        txt_info_name = (TextView) findViewById(R.id.txt_info_name);
        txt_info_intro = (TextView) findViewById(R.id.txt_info_intro);

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
//                if (TextUtils.isEmpty(nick)) {
                    txt_info_name.setText(name);
//                } else {
                    txt_info_nick.setText(nick);
//                }
                if (TextUtils.isEmpty(signal)) {
                    txt_info_intro.setText("暂无介绍");
                } else {
                    txt_info_intro.setText(signal);
                }

                /*加载头像*/
                if (TextUtils.isEmpty(photoStr)) {
                    img_info_photo.setImageResource(R.drawable.health_guide_men_selected);
                } else {
                    Picasso.with(PersonalInfoActivity.this).load(photoStr).into(img_info_photo);
                }

                /*设置毛玻璃效果*/
                loadBlurAndSetStatusBar(photoStr);

                /*设置ToolBar的名字*/
                final String finalNick = nick;
                app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (verticalOffset <= -head_layout.getHeight() / 2) {
                            mCollapsingToolbarLayout.setTitle(finalNick);
                        } else {
                            mCollapsingToolbarLayout.setTitle(" ");
                        }
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.showToast(PersonalInfoActivity.this, "信息获取失败");
            }
        });
    }

    private void initEvent() {
        cardTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.toGoalAcrivity(PersonalInfoActivity.this, InfoCardActivity.class);
            }
        });

        img_info_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDialog();
            }
        });
    }


    /**
     * 设置毛玻璃效果和沉浸状态栏
     */
    private void loadBlurAndSetStatusBar(String urlStr) {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        Glide.with(this).load(urlStr).bitmapTransform(new BlurTransformation(this, 100))
                .into(new SimpleTarget<GlideDrawable>() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
                            GlideDrawable> glideAnimation) {
                        head_layout.setBackground(resource);
                        root_layout.setBackground(resource);
                    }
                });

        Glide.with(this).load(urlStr).bitmapTransform(new BlurTransformation(this, 100))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
                            GlideDrawable> glideAnimation) {
                        mCollapsingToolbarLayout.setContentScrim(resource);
                    }
                });
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
                ToastUtils.showToast(PersonalInfoActivity.this, "查看大图");
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

                            final String picPath = imageUri.getPath();
                            final BmobFile bmobFile = new BmobFile(new File(picPath));
                            final Bitmap finalBitmap = bitmap;
                            bmobFile.uploadblock(PersonalInfoActivity.this, new UploadFileListener() {
                                @Override
                                public void onSuccess() {
                                    MyUser bmobUser = BmobUser.getCurrentUser(PersonalInfoActivity.this, MyUser.class);
                                    //bmobFile.getFileUrl(context)--返回的上传文件的完整地址
                                    url = bmobFile.getFileUrl(PersonalInfoActivity.this);
                                    bmobUser.setUserPhoto(url);
                                    bmobUser.setUserImg(bmobFile);
                                    if (url != null) {
                                        bmobUser.update(PersonalInfoActivity.this, new UpdateListener() {
                                            @Override
                                            public void onSuccess() {
                                                img_info_photo.setImageBitmap(finalBitmap);
                                                loadBlurAndSetStatusBar(url);
                                                ToastUtils.showToast(PersonalInfoActivity.this,"头像上传成功");
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
                                    ToastUtils.showToast(PersonalInfoActivity.this, "头像上传失败");
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
