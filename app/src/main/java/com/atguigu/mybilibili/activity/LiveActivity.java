package com.atguigu.mybilibili.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.adapter.LivePagerAdapter;
import com.atguigu.mybilibili.utils.BitmapUtils;
import com.atguigu.mybilibili.video.DanmakuVideoPlayer;
import com.atguigu.mybilibili.video.OrientationUtils;
import com.atguigu.mybilibili.video.SampleListener;
import com.atguigu.mybilibili.view.CircleImageView;
import com.shuyu.gsyvideoplayer.GSYPreViewManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LiveActivity extends AppCompatActivity {


    @Bind(R.id.danmaku_player)
    DanmakuVideoPlayer danmakuVideoPlayer;
    @Bind(R.id.item_live_user_cover)
    CircleImageView itemLiveUserCover;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    private boolean isPlay;
    private boolean isPause;

    private OrientationUtils orientationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        ButterKnife.bind(this);
        viewpager.setAdapter(new LivePagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        String icon = getIntent().getStringExtra("icon");
        int livenum = getIntent().getIntExtra("livenum", 666);

        String name = getIntent().getStringExtra("name");
        if (TextUtils.isEmpty(url)) {
            url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        }
        if (TextUtils.isEmpty(title)) {
            title = "bilibili直播";
        }
        tvName.setText(numToStr(livenum) + "     " + name);
        BitmapUtils.glideToImage(icon, itemLiveUserCover);
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (TextUtils.isEmpty(image)) {
            imageView.setImageResource(R.mipmap.xxx1);
        } else {
            BitmapUtils.glideToImage(image, imageView);
        }
        danmakuVideoPlayer.setThumbImageView(imageView);


        //使用自定义的全屏切换图片，!!!注意xml布局中也需要设置为一样的
        //必须在setUp之前设置
        danmakuVideoPlayer.setShrinkImageRes(R.drawable.custom_shrink);
        danmakuVideoPlayer.setEnlargeImageRes(R.drawable.custom_enlarge);

        //String url = "https://res.exexm.com/cw_145225549855002";
        danmakuVideoPlayer.setUp(url, true, null, title);


        resolveNormalVideoUI();

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, danmakuVideoPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        danmakuVideoPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        danmakuVideoPlayer.setRotateViewAuto(false);
        danmakuVideoPlayer.setLockLand(false);
        danmakuVideoPlayer.setShowFullAnimation(false);
        danmakuVideoPlayer.setNeedLockFull(true);

        //detailPlayer.setOpenPreView(true);
        danmakuVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                danmakuVideoPlayer.startWindowFullscreen(LiveActivity.this, true, true);
            }
        });

        danmakuVideoPlayer.setStandardVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }
        });

        danmakuVideoPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!danmakuVideoPlayer.isIfCurrentIsFullscreen()) {
                    danmakuVideoPlayer.startWindowFullscreen(LiveActivity.this, true, true);
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (danmakuVideoPlayer.isIfCurrentIsFullscreen()) {
                    StandardGSYVideoPlayer.backFromWindowFull(this);
                }
                if (orientationUtils != null) {
                    orientationUtils.setEnable(true);
                }
            }
        }
    }


    private void resolveNormalVideoUI() {
        //增加title
        danmakuVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        danmakuVideoPlayer.getTitleTextView().setText("测试视频");
        danmakuVideoPlayer.getBackButton().setVisibility(View.GONE);
    }

    public String numToStr(int num) {
        if (num > 9999) {
            float playf = ((float) num - (float) num % 1000) / 10000;
            return playf + "万";
        } else {
            return num + "";
        }
    }
}
