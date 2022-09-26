package com.mirror.sdk.ui.market.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mirror.sdk.ui.market.widgets.MarketMainRecyclerAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MarketUtils {
    public static int dpToPx(Context context,int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }
    public static float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }

    public static void startLoadImage(Handler handler,String url, View imageProgress, ImageView imageView){
        imageProgress.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = MarketUtils.getURLImage(url);
                Message msg = new Message();
                msg.what = 0;
                GiveBitmap ivUrl = new GiveBitmap();
                ivUrl.imageView = imageView;
                ivUrl.imageProgress = imageProgress;
                ivUrl.bitmap = bmp;
                msg.obj = ivUrl;
                handler.sendMessage(msg);
            }
        }).start();
    }

    public static Bitmap getURLImage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);//读取图像数据
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bmp;
    }


    public static int getScreenHeight(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        return display.getHeight();
    }

    public static double getStatusBarHeight(Context context){
        double statusBarHeight = Math.ceil(25 * context.getResources().getDisplayMetrics().density);
        return statusBarHeight;
    }

    public static int getStatusBarHeightBySys(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {

            result = context.getResources().getDimensionPixelSize(resourceId);

        }

        return result;

    }
}
