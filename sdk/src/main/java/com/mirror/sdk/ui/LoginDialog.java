//package com.mirror.sdk.ui;
//
//import android.app.ActionBar.LayoutParams;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Build;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.Window;
//import android.webkit.CookieManager;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//import com.mirror.sdk.MirrorSDK;
//import com.mirror.sdk.constant.MirrorConstant;
//import com.mirror.sdk.constant.MirrorUrl;
//
//import java.util.logging.Logger;
//
//public class LoginDialog extends Dialog {
//
//    WebView mWebView;
//    private Activity mActivity;
//    private Context mContext;
//
//    public void SetParams(Activity activity){
//        mActivity = activity;
//    }
//
//    public LoginDialog(Context context, String url) {
//        super(context);
//
//        mContext = context;
//        Log.i("MMMMMMM",url);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mWebView = new WebView(mContext);
//        setWebView(mWebView,url,MirrorConstant.AppName);
////        mWebView.loadUrl(url);
////        mWebView.setWebViewClient(new WebViewClient());
////        mWebView.getSettings().setJavaScriptEnabled(true);
//        setContentView(mWebView);
//
//        getWindow().setLayout(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
//            mWebView.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    class MyClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            mActivity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    // Toast.makeText(MainActivity.mActivity, "点击", 1).show();
//                }
//            });
//            return false;
//        }
//    }
//
//    private void setWebView(WebView webView,String finalUrl,String appName){
////        this.mLoginWebView = webView;
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl(finalUrl);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        //set autofit
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//
//        // Set User Agent
//        String userAgent = System.getProperty("http.agent");
//        webSettings.setUserAgentString(userAgent + appName);
//
//        // Enable Cookies
//        CookieManager.getInstance().setAcceptCookie(true);
//        if (Build.VERSION.SDK_INT >= 21) CookieManager.getInstance()
//                .setAcceptThirdPartyCookies(webView, true);
//
//        // Handle Popups
//        LoginChromeClient client = new LoginChromeClient();
//        client.SetParams(mContext,webView,userAgent);
//        webView.setWebChromeClient(client);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setSupportMultipleWindows(true);
////        globalContext = context.getApplicationContext();
//
//        // WebView Tweaks
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setSaveFormData(true);
//        webSettings.setEnableSmoothTransition(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
//
////        webView.addJavascriptInterface(mActivity, MirrorConstant.JsDelegateName);
//    }
//}