package com.atguigu.mybilibili.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.utils.ClipboardUtil;

import butterknife.Bind;

public class WebActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.webview)
    WebView webview;
    private String url;
    private String title;

    @Override
    protected String setUrl() {
        return null;
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_share:
                        share();
                        break;
                    case R.id.menu_copy:
                        ClipboardUtil.setText(WebActivity.this,url);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void initView() {
        toolBar.inflateMenu(R.menu.menu_web);
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("link");
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }

    @Override
    protected void initData(String json, String error) {
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setBuiltInZoomControls(true);

        settings.setUseWideViewPort(true);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        if(!TextUtils.isEmpty(url)) {

            webview.loadUrl(url);
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_web;
    }
    //分享
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "来自「哔哩哔哩」的分享:" + url);
        startActivity(Intent.createChooser(intent, title));
    }
}
