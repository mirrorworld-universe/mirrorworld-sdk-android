package com.mirror.sdk.utils;

import android.app.Activity;
import android.util.Log;

import com.mirror.sdk.listener.universal.MSimpleCallback;

public class MirrorThreadUtils {
    public static void  runLogicInUIThread(Activity mActivity, MSimpleCallback callback){
        if(mActivity == null){
            Log.e("Mirror","no activity now!");
        }else{
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(callback != null) callback.callback();
                }
            });
        }
    }
}
