package com.mirror.sdk.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewDialog extends Dialog {

    WebView mWebView;
    private Activity mActivity;

    public void SetParams(Activity activity){
        mActivity = activity;
    }

    public WebViewDialog(Context context, String url) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mWebView = new WebView(context);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new MyClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        setContentView(mWebView);

        getWindow().setLayout(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Toast.makeText(MainActivity.mActivity, "点击", 1).show();
                }
            });
            return false;
        }
    }

}