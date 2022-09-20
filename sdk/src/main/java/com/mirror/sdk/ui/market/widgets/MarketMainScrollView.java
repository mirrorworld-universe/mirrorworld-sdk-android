package com.mirror.sdk.ui.market.widgets;

import android.app.Instrumentation;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

import androidx.annotation.Nullable;

import com.mirror.sdk.ui.market.MarketUIController;
import com.mirror.sdk.ui.market.utils.MarketUtils;

public class MarketMainScrollView extends ScrollView {
    Context mContext;
    MotionEvent mTmpEvent;
    public MarketMainScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context);
        this.setVerticalScrollBarEnabled(false);
        mContext = context;
    }
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        if(MarketUIController.getInstance().isOut) {
//            super.onInterceptTouchEvent(e);
//            return true;
//        }else {
//            return false;
//        }
//    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mTmpEvent = ev;
        if (MarketUIController.getInstance().isOut) {
             super.onTouchEvent(ev);
             return true;
        } else {
            return false;
        }
    }

    private int totalUp = 40;//42
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        Log.i("Market l", String.valueOf(l));
//        Log.i("Market t", String.valueOf(t));
//        Log.i("Market oldl", String.valueOf(oldl));
//        Log.i("Market oldt", String.valueOf(oldt)+" "+MarketUtils.dpToPx(mContext,totalUp));
        if(t >= MarketUtils.dpToPx(mContext,totalUp)){
            Log.e("Market","no out!");
            MarketUIController.getInstance().isOut = false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Instrumentation instrumentation = new Instrumentation();
                    instrumentation.sendPointerSync(mTmpEvent);
                }
            }).start();
        }
        else if(t <= 0){
            Log.e("Market","is out!");
            MarketUIController.getInstance().isOut = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Instrumentation instrumentation = new Instrumentation();
                    instrumentation.sendPointerSync(mTmpEvent);
                }
            }).start();
        }
    }
}
