//package com.mirror.sdk.ui;
//
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//
//import android.app.DialogFragment;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Message;
//import android.util.Log;
//import android.view.Display;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.webkit.CookieManager;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.RelativeLayout;
//
//import androidx.annotation.Nullable;
//import androidx.constraintlayout.widget.ConstraintLayout;
//
//import com.mirror.sdk.MirrorSDK;
//import com.mirror.sdk.R;
//import com.mirror.sdk.constant.MirrorUrl;
//
//
//public class SDKFragmentDialog extends DialogFragment {
//
//    private String mApiKey = "";
//    private Activity mActivity = null;
//    private Context mContext = null;
//    private String mAppName = "";
//    private String delegateName = "mwm";
//
//
//    public void SetParams(String apiKey, Activity activity,String appName,Context context){
//        mApiKey = apiKey;
//        mActivity = activity;
//        mAppName = appName;
//        mContext = context;
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.sdk_dialog, container, false);
//        WebView webView = rootView.findViewById(R.id.first_webview);
//
//        ViewGroup.LayoutParams params = webView.getLayoutParams();
//        Display display = mActivity.getWindowManager().getDefaultDisplay(); // 为获取屏幕宽、高
//        params.height = (int) (display.getHeight() * 0.9);
//        params.width = (int) (display.getWidth() * 0.9);
//        webView.setLayoutParams(params);
////        rootView.setPadding(0,0,0,0);
////        rootView.layout(0,0,0,0);
//        setWebView(mContext,webView);
//
////        final Window window = getDialog().getWindow();
//////        window.setBackgroundDrawableResource(R.color.);
////        window.getDecorView().setPadding(0, 0, 0, 0);
////        WindowManager.LayoutParams wlp = window.getAttributes();
////        wlp.gravity = Gravity.BOTTOM;
////        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
////        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
////        window.setAttributes(wlp);
//
//        return rootView;
//    }
//    /**
//     * 设置主题需要在 onCreate() 方法中调用 setStyle() 方法
//     */
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
//    }
//
//
//    private void setWebView(Context context, WebView webView){
////        this.webView = webView;
//        webView.setWebViewClient(new WebViewClient());
//        final String finalUrl = MirrorUrl.URL_AUTH + mApiKey;
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
//        webSettings.setUserAgentString(userAgent + mAppName);
//
//        // Enable Cookies
//        CookieManager.getInstance().setAcceptCookie(true);
//        if (Build.VERSION.SDK_INT >= 21) CookieManager.getInstance()
//                .setAcceptThirdPartyCookies(webView, true);
//
//        // Handle Popups
//        MirrorChromeClient client = new MirrorChromeClient();
//        client.SetParams(context,userAgent,mAppName,mActivity,webView);
//        webView.setWebChromeClient(client);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setSupportMultipleWindows(true);
//
//        // WebView Tweaks
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setSaveFormData(true);
//        webSettings.setEnableSmoothTransition(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
//        webSettings.setSupportZoom(true); // 支持缩放
//        webSettings.setLoadWithOverviewMode(true);
//
//        webView.addJavascriptInterface(mActivity, delegateName);
//
////        webView.setInitialScale(2);
//    }
//}
//
//
//class MirrorChromeClient extends WebChromeClient {
//    private WebView webViewPopUp = null;
//    private Context mContext = null;
//    private String mUserAgent = "";
//    private String mAppName = "";
//    private WebView mParentWebView = null;
//    private Activity mActivity = null;
//    private AlertDialog builder = null;
//
//    public void SetParams(Context context,String userAgent,String appName,Activity activity,WebView parentWebView){
//        mContext = context;
//        mUserAgent = userAgent;
//        mAppName = appName;
//        mActivity = activity;
//        mParentWebView = parentWebView;
//    }
//    @Override
//    public boolean onCreateWindow(WebView view, boolean isDialog,
//                                  boolean isUserGesture, Message resultMsg) {
//        webViewPopUp = new WebView(mContext);
//        WebSettings webViewPopupSetting = webViewPopUp.getSettings();
//        webViewPopUp.setVerticalScrollBarEnabled(false);
//        webViewPopUp.setHorizontalScrollBarEnabled(false);
//        webViewPopUp.setWebChromeClient(new MirrorChromeClient());
//        webViewPopUp.getSettings().setJavaScriptEnabled(true);
//        webViewPopUp.getSettings().setSaveFormData(true);
//        webViewPopUp.getSettings().setEnableSmoothTransition(true);
//        webViewPopUp.getSettings().setUserAgentString(mUserAgent + mAppName);
//
//        //To resolve the exceed on Android
//        if (Build.VERSION.SDK_INT >= 19) {
//            webViewPopUp.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else {
//            webViewPopUp.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
//        webViewPopupSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webViewPopupSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
//
//        // pop the  webview with alert dialog
//        builder = new AlertDialog.Builder(mActivity).create();
//        builder.setTitle("");
//
//        RelativeLayout layout = getPopupWindowLayout(mActivity);
//
//        Display display = mActivity.getWindowManager().getDefaultDisplay(); // 为获取屏幕宽、高
////        params.height = (int) (display.getHeight() * 0.9);
////        params.width = (int) (display.getWidth() * 0.9);
////        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (display.getHeight() * 0.9),(int) (display.getHeight() * 0.9));
////        layout.setLayoutParams(params);
//        webViewPopUp.setLayoutParams(new ViewGroup.LayoutParams((int) (display.getHeight() * 0.8),(int) (display.getHeight() * 0.8)));
//        layout.addView(webViewPopUp);
//        builder.setView(layout);
//
//
//        builder.setButton("Close", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                webViewPopUp.destroy();
//                dialog.dismiss();
//            }
//        });
//
//        builder.show();
//        builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        if(Build.VERSION.SDK_INT >= 21) {
//            cookieManager.setAcceptThirdPartyCookies(webViewPopUp, true);
//            cookieManager.setAcceptThirdPartyCookies(mParentWebView, true);
//        }
//
//        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
//        transport.setWebView(webViewPopUp);
//        resultMsg.sendToTarget();
//
//        return true;
//    }
//
//    @Override
//    public void onCloseWindow(WebView window) {
//        //Toast.makeText(contextPop,"onCloseWindow called",Toast.LENGTH_SHORT).show();
//        try {
//            webViewPopUp.destroy();
//        } catch (Exception e) {
//            Log.d("Destroyed with Error ", e.getStackTrace().toString());
//        }
//
//        try {
//            builder.dismiss();
//        } catch (Exception e) {
//            Log.d("Dismissed with Error: ", e.getStackTrace().toString());
//        }
//
//    }
//
//    private RelativeLayout getPopupWindowLayout(Context context) {
//        RelativeLayout relative = new RelativeLayout(context);
////        addProgressBar(context,relative);
//        return relative;
//    }
//}
