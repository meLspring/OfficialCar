package com.example.yysh.officialcar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

//信息平台回复和发起Activity
public class InforPlatformActivity extends AppCompatActivity {
    private final  static int CAMERA_REQUEST_CODE=1;    //调用摄像头照相请求码
    private final static int VIDEO_REQUEST_CODE=2;      //调用摄像头录像请求码
    private EditText infor_edit;
    private LinearLayout platform_linear;   //用来动态添加图片和视频
    private String title;       //保存信息平台详情页面传递过来的title
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_platform);
        getSupportActionBar().hide();
        initView();
        initData();
    }

    private void initData() {
        Intent intent=getIntent();
        if(intent!=null){
            title=intent.getStringExtra("information");
        }
    }


    private void initView() {
        infor_edit = ((EditText) findViewById(R.id.infor_edit));
        platform_linear = ((LinearLayout) findViewById(R.id.platform_linear));
    }

    //点击返回按钮，不保存
    public void platform_back(View view) {
        Intent intent=new Intent(this,InforPlatformDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("information",title);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    //点击添加图片按钮
    public void addImgMethod(View view) {
        //从照相机获取图片
        Intent getImageByCamera =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(getImageByCamera,CAMERA_REQUEST_CODE);
        //Toast.makeText(this, "添加图片", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取相机返回的图片
        if(requestCode==CAMERA_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            Bundle bundle=data.getExtras();
            Bitmap bitmap= (Bitmap) bundle.get("data");
            if(bitmap!=null){
                ImageView image=new ImageView(this);
                image.setLayoutParams(new ViewGroup.LayoutParams(bitmap.getWidth(),bitmap.getHeight()));
                image.setImageBitmap(bitmap);
                platform_linear.addView(image);
            }
        }
        if(requestCode==VIDEO_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            //获取数据路径
            Uri uri=data.getData();
            //根据uri获取数据
            Cursor cursor=getContentResolver().query(uri,null,null,null,null);
            if(cursor.moveToFirst()){
                //获取视频保存路径
                String path=cursor.getString(cursor.getColumnIndex("_data"));
                DisplayMetrics metrics=new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int vwidth=metrics.widthPixels/3;
                int vheight=metrics.heightPixels/5;
                Log.e("vwidth",""+vwidth);
                Log.e("vheight",""+vheight);
                //根据路径加载视频
                final VideoView videoView=new VideoView(this);
                videoView.setLayoutParams(new ViewGroup.LayoutParams(vwidth,vheight));
                videoView.setVideoPath(path);
                platform_linear.addView(videoView);
                //控制视频
                //videoView.setMediaController(new MediaController(this));
                //开始播放视频
                videoView.start();
            }
        }


    }

    //点击添加视频按钮
    public void addVideoMethod(View view) {
        Intent intent =new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent,VIDEO_REQUEST_CODE);
        //Toast.makeText(this, "添加视频", Toast.LENGTH_SHORT).show();
    }

    //信息提交,但是要将保存的数据传递到信息平台详情页面
    public void infor_sumbit(View view) {
        Intent intent=new Intent(this,InforPlatformDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("information",title);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}