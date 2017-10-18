package com.edu.nbl.xinwxm.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.nbl.xinwxm.R;
import com.edu.nbl.xinwxm.biz.NewsDBUtils;
import com.edu.nbl.xinwxm.entity.News;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.webview)
    WebView mWebview;
    private News mNewsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        //获取news
        mNewsInfo = (News) getIntent().getExtras().getSerializable("NewsInfo");
        initTitle();
        initWebView();
    }

    private void initWebView() {
        mWebview.setWebViewClient(new WebViewClient());//禁止用自带浏览器打开
        mWebview.loadUrl(mNewsInfo.link);//加载网页
        WebSettings mSettings = mWebview.getSettings();
        mSettings.setJavaScriptEnabled(true);//允许使用JavaScript，脚本语言
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setUseWideViewPort(true);

        mWebview.setWebChromeClient(mChromeClient);
    }

    WebChromeClient mChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

        }
    };


    private void initTitle() {
        setSupportActionBar(mToolBar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);//显示箭头和标题
        mActionBar.setTitle("");
        mTitle.setText(mNewsInfo.title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newsdatlls, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (NewsDBUtils.insert(NewsActivity.this, mNewsInfo) == false) {
            menu.add(0, 0, 0, "已收藏");
        } else if (NewsDBUtils.deleteOne(NewsActivity.this, mNewsInfo) == true) {
            getMenuInflater().inflate(R.menu.menu_newsdatlls, menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NewsActivity.this.finish();
                break;
            case 0:
            case R.id.collect:
                EventBus.getDefault().post(new MessageEvent());
                savaNews();
                break;
        }
        return true;
    }

    private void savaNews() {
        if (NewsDBUtils.insert(NewsActivity.this, mNewsInfo)) {
            Toast.makeText(NewsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
        } else if (NewsDBUtils.deleteOne(NewsActivity.this, mNewsInfo)) {
            Toast.makeText(NewsActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
        }
        EventBus.getDefault().post(new MessageEvent());
    }
}
