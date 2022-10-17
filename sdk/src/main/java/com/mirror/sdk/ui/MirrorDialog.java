package com.mirror.sdk.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.mirror.sdk.R;

public class MirrorDialog extends Dialog {
    public MirrorDialog(Context context) {
//        super(context);
        super(context,R.style.fullDialog);
//        init();
    }

    public MirrorDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected MirrorDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init(){
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        hideNavigationBar();
//        adjustFullScreen(getWindow());
//        getWindow().setBackgroundDrawableResource(R.color.bg_color_translucent);

    }
    private void hideNavigationBar() {
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        this.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }
    public static void adjustFullScreen(Window window) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            window.setAttributes(lp);
            final View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
//    public void init(){
//        View view = LayoutInflater.from(context).inflate(R.style.fullDialog, null);
//        Window window = this.getWindow() ;
//        window.setContentView(view);
//
//        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        //一定要在setContentView之后调用，否则无效
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = this.getWindow();
        if (window != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setLayout(width, height);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
