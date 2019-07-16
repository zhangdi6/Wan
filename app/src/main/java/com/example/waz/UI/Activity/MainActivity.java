package com.example.waz.UI.Activity;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.waz.Base.BaseActivity;
import com.example.waz.Night.UIModeUtil;
import com.example.waz.R;
import com.example.waz.UI.Fragment.KnowledgeFragment;
import com.example.waz.UI.Fragment.NavigationFragment;
import com.example.waz.UI.Fragment.NavigationItemFragment;
import com.example.waz.UI.Fragment.ProjectArticleFragment;
import com.example.waz.UI.Fragment.ProjectFragment;
import com.example.waz.UI.Fragment.WanFragment;
import com.example.waz.UI.Fragment.WechatFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private int mode = 1;
    private static final int mode_first = 1;
    private static final int mode_second = 2;
    private static final int mode_third = 3;
    private static final int mode_fourth = 4;
    private static final int mode_fifth = 5;
    private NavigationView mMainNv;
    private Toolbar mTb;
    private WanFragment fragment;
    private FrameLayout mMainFrag;
    private BottomNavigationView mMainButtomNv;
    private FloatingActionButton mMianFloatBtn;
    private DrawerLayout mDl;
    private CoordinatorLayout mCc;
    private FragmentManager manager;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE =
            {"android.permission.READ_EXTERNAL_STORAGE"
                    , "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final int CAMERA_CODE = 1;
    private static final int ALBUM_CODE = 2;
    private File file;
    private static final int REQUEST_CODE = 100;
    private ImageView img;
    private Uri mImageUri;
    public static TextView name2;
    private TextView style;
    private SharedPreferences loginToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        manager = getSupportFragmentManager();
        addFragment(manager,
                WanFragment.class, R.id.main_frag, null);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        verifyStoragePermissions(this);
    }

    private void initView() {
        initPermission();
        mMainNv = (NavigationView) findViewById(R.id.main_nv);
        mTb = (Toolbar) findViewById(R.id.tb);
        mMainFrag = (FrameLayout) findViewById(R.id.main_frag);
        mMainButtomNv = (BottomNavigationView) findViewById(R.id.main_buttom_nv);
        mMianFloatBtn = (FloatingActionButton) findViewById(R.id.mian_float_btn);
        mDl = (DrawerLayout) findViewById(R.id.dl);
        mCc = (CoordinatorLayout) findViewById(R.id.cc);
        mMainButtomNv.setSelectedItemId(R.id.main_bv_first);
        MyToolBar();
        MyDrawer();
        MyNavigation();
        MyHeader();
        mMainFrag();
        if (!TextUtils.isEmpty(name2.getText())) {
            MenuItem item = mMainNv.getMenu().findItem(R.id.nv_exits);
            item.setVisible(true);
        } else {
            MenuItem item = mMainNv.getMenu().findItem(R.id.nv_exits);
            item.setVisible(false);
            name2.setHint("登录");
            name2.setHighlightColor(Color.WHITE);
        }

    }

    private void mMainFrag() {
        mMianFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == mode_first) {
                    WanFragment.mRlvWan.smoothScrollToPosition(0);
                } else if (mode == mode_second) {
                    KnowledgeFragment.mRlv.smoothScrollToPosition(0);
                } else if (mode == mode_third) {
                } else if (mode == mode_fourth) {
                    NavigationItemFragment.mRlv.smoothScrollToPosition(0);
                } else if (mode == mode_fifth) {

                    ProjectArticleFragment.mRlv.smoothScrollToPosition(0);
                }
            }
        });
    }

    private void initPermission() {
        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，
         还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }

    /* * android 动态权限申请 * */
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tbmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tb_menu:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void MyNavigation() {


        mMainNv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.nv_love:
                        if (!TextUtils.isEmpty(name2.getText())) {
                            Intent intent = new Intent(MainActivity.this, LoveActivity.class);
                            startActivity(intent);
                            mDl.closeDrawer(mMainNv);

                        } else {
                            Toast.makeText(MainActivity.this, "请先登录!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivityForResult(intent, 11);
                        }

                        break;
                    case R.id.nv_todo:
                        Intent intent2 = new Intent(MainActivity.this, TodoActivity.class);
                        startActivity(intent2);
                        mDl.closeDrawer(mMainNv);
                        break;
                    case R.id.nv_nignt:
                        UIModeUtil.changeModeUI(MainActivity.this);
                        mDl.closeDrawer(mMainNv);
                        break;
                    case R.id.nv_settings:
                        mDl.closeDrawer(mMainNv);
                        Intent intent3 = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nv_aboutus:
                        Intent intent4 = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent4);
                        mDl.closeDrawer(mMainNv);
                        break;
                    case R.id.nv_exits:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("确认退出登录?")
                                .setPositiveButton("取消",null)
                                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferences.Editor edit = loginToken.edit();
                                        edit.remove("username");
                                        edit.commit();
                                        name2.setText("");
                                        MenuItem item = mMainNv.getMenu().findItem(R.id.nv_exits);
                                        item.setVisible(false);
                                        name2.setHint("登录");
                                        name2.setHighlightColor(Color.WHITE);
                                        mDl.closeDrawer(mMainNv);
                                    }
                                }).show();

                        break;
                }
                return true;
            }
        });
        mMainButtomNv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.main_bv_first:
                        addFragment(manager, WanFragment.class, R.id.main_frag, null);
                        mTb.setTitle(R.string.bv_first);
                        mode = mode_first;
                        break;
                    case R.id.main_bv_know:
                        addFragment(manager, KnowledgeFragment.class, R.id.main_frag, null);
                        mTb.setTitle(R.string.bv_know);
                        mode = mode_second;
                        break;
                    case R.id.main_bv_wechat:
                        addFragment(manager, WechatFragment.class, R.id.main_frag, null);
                        mTb.setTitle(R.string.bv_wechat);
                        mode = mode_third;

                        break;
                    case R.id.main_bv_navi:
                        addFragment(manager, NavigationFragment.class, R.id.main_frag, null);
                        mTb.setTitle(R.string.bv_navi);
                        mode = mode_fourth;

                        break;
                    case R.id.main_bv_project:
                        addFragment(manager, ProjectFragment.class, R.id.main_frag, null);
                        mTb.setTitle(R.string.bv_project);
                        mode = mode_fifth;

                        break;
                }
                return true;
            }
        });
    }

    private void MyHeader() {
        LinearLayout view = (LinearLayout) mMainNv.getHeaderView(0);
        img = view.findViewById(R.id.nv_userimg);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPop();
            }
        });
        name2 = view.findViewById(R.id.nv_head_user);
        name2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 11);
            }
        });
        style = view.findViewById(R.id.nv_head_user_style);
        loginToken = getSharedPreferences("loginToken", 0);

        if (loginToken != null) {
            name2.setText(loginToken.getString("username", null));
            style.setText("我算你是个社会人");
            RequestOptions requestOptions = new RequestOptions().circleCrop();
            Glide.with(this).load("https://yunxue-bucket.oss-cn-shanghai.aliyuncs." +
                    "com/imgurl/teacher/headimg_1512118772788.jpg").apply(requestOptions).into(img);

        }
    }

    private void enterPop() {
        View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.mnv_pop, null);
        final PopupWindow pp = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pp.setBackgroundDrawable(new ColorDrawable());
        pp.setOutsideTouchable(true);
        pp.setAnimationStyle(R.style.pop);
        pp.showAtLocation(mMainNv, Gravity.CENTER, 0, 0);
        RelativeLayout rela = inflate.findViewById(R.id.mNv_pop_rela);
        rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pp.dismiss();
            }
        });
        final TextView camera = inflate.findViewById(R.id.pop_camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*opencamera();*/
                showToast("相机故障,请稍后重试");
                pp.dismiss();
            }
        });
        TextView photo = inflate.findViewById(R.id.pop_photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openphoto();
                showLoadingPage(mMainNv, 0);
                pp.dismiss();

            }
        });
    }

    private void openphoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission
                .READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openXc();
        } else {
            ActivityCompat.requestPermissions(this
                    , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        }
    }

    private void opencamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                .PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 100) {
                openCamera();
            } else if (requestCode == 200) {
                openXc();
            }
        }
    }

    //打开相册
    private void openXc() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, ALBUM_CODE);

    }

    //拍照
    private void openCamera() {
//1,创建临时保存文件位置
        file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //2.转换为Uri路径（对7.0兼容）
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mImageUri = FileProvider.getUriForFile(this, "com.waz.provider", file);
        }
        //3:开启相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        //将拍照图片存入mImageUri
        startActivityForResult(intent, CAMERA_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 22) {
            name2.setText(data.getStringExtra("username"));
            style.setText("一个人一座城~");
            RequestOptions requestOptions = new RequestOptions().circleCrop();
            Glide.with(this).load("https://yunxue-bucket.oss-cn-shanghai.aliyuncs." +
                    "com/imgurl/teacher/headimg_1512118772788.jpg").apply(requestOptions).into(img);
            if (!TextUtils.isEmpty(name2.getText())) {
                MenuItem item = mMainNv.getMenu().findItem(R.id.nv_exits);
                item.setVisible(true);
            } else {
                MenuItem item = mMainNv.getMenu().findItem(R.id.nv_exits);
                item.setVisible(false);
                name2.setHint("登录");
                name2.setHighlightColor(Color.WHITE);
            }

        }
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CODE) {
                //拍照成功显示图片
                try {
                    if (mImageUri != null) {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
                        img.setImageBitmap(bitmap);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //文件上传
                uploadFile(file);
            } else if (requestCode == ALBUM_CODE) {
                //相册选择显示
                //获取到图片对应的Uri路径
                Uri data1 = data.getData();
                File mf = getFileFromUri(data1, this);
                uploadFile(mf);
            }
        }
    }

    //判断相册还是拍照
    private File getFileFromUri(Uri mImageUri, Context context) {
        if (mImageUri == null) {
            return null;
        }
        switch (mImageUri.getScheme()) {
            case "content":
                return getFileFromContentUri(mImageUri, context);
            case "file":
                return new File(mImageUri.getPath());
            default:
                return null;
        }
    }

    //从相册获取图片上传
    private File getFileFromContentUri(Uri mImageUri, Context context) {
        File file = null;
        Cursor cursor = context.getContentResolver().query(mImageUri, new String[]{MediaStore
                .MediaColumns.DATA}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                String filepath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                cursor.close();
                if (!TextUtils.isEmpty(filepath)) {
                    file = new File(filepath);
                }
            }
        }
        return file;
    }
    //上传拍的照片

    private void uploadFile(File file) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody build = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "21444")
                .addFormDataPart("file", file.getName(), requestBody)
                .build();
        Request request = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(build)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                try {
                    JSONObject object = new JSONObject(string);
                    final int code = object.getInt("code");
                    final String res = object.getString("res");
                    JSONObject data = object.getJSONObject("data");
                    final String url = data.getString("url");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (code == 200) {
                                RequestOptions requestOptions = new RequestOptions().circleCrop();
                                Glide.with(MainActivity.this).load(url).apply(requestOptions).into(img);
                                Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                                dismiss();
                            } else {
                                Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void MyToolBar() {
        mTb.setTitle("首页");

        setSupportActionBar(mTb);
    }

    private void MyDrawer() {
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, mDl, mTb, R.string.app_name, R.string.app_name);
        toggle.syncState();
        mDl.addDrawerListener(toggle);
        mDl.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float x = mMainNv.getWidth() * slideOffset;
                mCc.setX(x);
            }
        });
    }
}

