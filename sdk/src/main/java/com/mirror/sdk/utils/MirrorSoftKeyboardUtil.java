package com.mirror.sdk.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MirrorSoftKeyboardUtil {
    public static void showSoftInput(View view) {
        if (view == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    public static void hideSoftInput(View view) {
        if (view == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
