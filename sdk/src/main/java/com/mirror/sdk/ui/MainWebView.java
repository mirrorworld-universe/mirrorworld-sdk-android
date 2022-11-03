package com.mirror.sdk.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.NonNull;

public class MainWebView extends WebView {
    Activity mActivity;
    public MainWebView(@NonNull Context context) {
        super(context);
    }

    public void setActivity(Activity activity){
        mActivity = activity;
    }

    @Override
    public boolean onCheckIsTextEditor() {
        try {
            return super.onCheckIsTextEditor();
        } catch (Throwable th) {
            // Probable deadlock detected due to WebView API being called on incorrect thread while the UI thread is blocked.
            return true; // or return false in your scenario.
        }
//        mActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                return super.onCheckIsTextEditor();
//            }
//        });

    }
//    @Override
//    public boolean onCheckIsTextEditor() {
//        if (Looper.myLooper() == Looper.getMainLooper()) {
//            return super.onCheckIsTextEditor();
//        } else {
//            return false;
//        }
//    }
}
