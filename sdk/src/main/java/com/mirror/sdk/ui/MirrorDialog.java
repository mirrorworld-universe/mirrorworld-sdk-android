package com.mirror.sdk.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;

public class MirrorDialog extends Dialog {
    public MirrorDialog(Context context) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    public MirrorDialog(Context context, int themeResId) {
        super(context, themeResId);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    protected MirrorDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

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
