package com.mirror.sdk.utils;

import android.app.Activity;
import android.os.Debug;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.mirror.sdk.constant.MirrorConstant;

public class MirrorWebviewUtils {
    public static boolean isWebviewSupport(Activity activity, WebView webView,String notice,boolean isDebug){
        String ua = webView.getSettings().getUserAgentString();
        if(isDebug){
            Log.d("UA: ",ua);
        }
        String startStr = "Chrome/";
        String endStr = "Mobile Safari/";
        int startIdx = ua.indexOf(startStr);
        int endIdx = ua.indexOf(endStr);
        String versionStr = ua.substring(startIdx+startStr.length(),endIdx);
        String mainVersionStr = versionStr.substring(0,versionStr.indexOf('.'));
        int mainVersion = Integer.parseInt(mainVersionStr);

        if(mainVersion <= MirrorConstant.LowestWebviewVersion){
            Toast.makeText(activity, notice, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
