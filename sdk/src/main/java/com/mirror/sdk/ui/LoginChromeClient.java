package com.mirror.sdk.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.mirror.sdk.constant.MirrorConstant;

public
class LoginChromeClient extends WebChromeClient {
    private WebView webViewPopUp = null;
    private Context mContext = null;
    private String mUserAgent = "";
    private WebView mParentWebView = null;
//    private Activity mActivity = null;
    private AlertDialog mDialogBuilder = null;

    public void SetParams(Context context,WebView parentWebView,String userAgent){
        mContext = context;
        mUserAgent = userAgent;
        mParentWebView = parentWebView;
    }
    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog,
                                  boolean isUserGesture, Message resultMsg) {
        webViewPopUp = new WebView(mContext);
        WebSettings webViewPopupSetting = webViewPopUp.getSettings();
        webViewPopUp.setVerticalScrollBarEnabled(false);
        webViewPopUp.setHorizontalScrollBarEnabled(false);
        webViewPopUp.setWebChromeClient(new MirrorChromeClient());
        webViewPopUp.getSettings().setJavaScriptEnabled(true);
        webViewPopUp.getSettings().setSaveFormData(true);
        webViewPopUp.getSettings().setEnableSmoothTransition(true);
        webViewPopUp.getSettings().setUserAgentString(mUserAgent + MirrorConstant.AppName);

        //To resolve the exceed on Android
        if (Build.VERSION.SDK_INT >= 19) {
            webViewPopUp.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webViewPopUp.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webViewPopupSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webViewPopupSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // pop the  webview with alert dialog
        mDialogBuilder = new AlertDialog.Builder(mContext).create();
        mDialogBuilder.setTitle("");

        RelativeLayout layout = getPopupWindowLayout(mContext);

        layout.addView(webViewPopUp);
        mDialogBuilder.setView(layout);

        mDialogBuilder.setButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                webViewPopUp.destroy();
                dialog.dismiss();
            }
        });

        mDialogBuilder.show();
        mDialogBuilder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if(Build.VERSION.SDK_INT >= 21) {
            cookieManager.setAcceptThirdPartyCookies(webViewPopUp, true);
            cookieManager.setAcceptThirdPartyCookies(mParentWebView, true);
        }

        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(webViewPopUp);
        resultMsg.sendToTarget();

        return true;
    }

    @Override
    public void onCloseWindow(WebView window) {
        //Toast.makeText(contextPop,"onCloseWindow called",Toast.LENGTH_SHORT).show();
        try {
            webViewPopUp.destroy();
        } catch (Exception e) {
            Log.d("Destroyed with Error ", e.getStackTrace().toString());
        }

        try {
            mDialogBuilder.dismiss();
        } catch (Exception e) {
            Log.d("Dismissed with Error: ", e.getStackTrace().toString());
        }

    }

    private RelativeLayout getPopupWindowLayout(Context context) {
        RelativeLayout relative = new RelativeLayout(context);
//        addProgressBar(context,relative);
        return relative;
    }
}