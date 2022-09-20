package com.mirror.sdk.ui.market.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MarketUtils {
    public static int dpToPx(Context context,int dp) {
        float density = context.getResources().getDisplayMetrics().density; return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }
}
