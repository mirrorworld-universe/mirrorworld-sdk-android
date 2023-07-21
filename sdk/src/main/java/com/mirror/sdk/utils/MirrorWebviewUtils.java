package com.mirror.sdk.utils;
import static androidx.browser.customtabs.CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorConstant;

import java.util.ArrayList;
import java.util.List;

public class MirrorWebviewUtils {
    private static String customPackageName = "";
    public static boolean isWebviewSupport(Context activity, WebView webView,String notice,boolean isDebug){
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

    @SuppressWarnings("unchecked")
    public static String GetWebViewUserAgent(Context ctx) {
        String ua = "";
        try {
            if(Build.VERSION.SDK_INT < 19) {
                WebView web = new WebView(ctx);
                ua = web.getSettings().getUserAgentString();
                web.destroy();
            }
            else
            {
                ua = WebSettings.getDefaultUserAgent(ctx);
            }
        }
        catch(Exception e) {
            Log.e("SDK", "GetWebViewUserAgent Exception");
            e.printStackTrace();
        }
        return ua;
    }

    public static boolean isSupportCustomTab(Context activity){
        String nameToUse = MirrorWebviewUtils.getPackageNameToUse(activity);

//        MirrorSDK.getInstance().logFlow("Browser name to use:" + nameToUse);
        ArrayList<ResolveInfo> infos = MirrorWebviewUtils.getCustomTabsPackages(activity);
        if(nameToUse == null){
            return false;
        }else {
            return true;
        }
    }

    /**
     * Returns a list of packages that support Custom Tabs.
     */
    public static ArrayList<ResolveInfo> getCustomTabsPackages(Context context) {
        PackageManager pm = context.getPackageManager();
        // Get default VIEW intent handler.
        Intent activityIntent = new Intent()
                .setAction(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setData(Uri.fromParts("http", "", null));

        // Get all apps that can handle VIEW intents.
        List<ResolveInfo> resolvedActivityList = pm.queryIntentActivities(activityIntent, PackageManager.MATCH_ALL);
        ArrayList<ResolveInfo> packagesSupportingCustomTabs = new ArrayList<>();
        for (ResolveInfo info : resolvedActivityList) {
            Intent serviceIntent = new Intent();
            serviceIntent.setAction(ACTION_CUSTOM_TABS_CONNECTION);
            serviceIntent.setPackage(info.activityInfo.packageName);
            // Check if this package also resolves the Custom Tabs service.
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info);
            }
        }
        return packagesSupportingCustomTabs;
    }

    public static String getPackageNameToUse(Context context) {
        if(customPackageName != "") return customPackageName;

        PackageManager pm = context.getPackageManager();
        // Get default VIEW intent handler.
        Intent activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com"));
        ResolveInfo defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0);
        String defaultViewHandlerPackageName = null;
        if (defaultViewHandlerInfo != null) {
            defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName;
        }

        // Get all apps that can handle VIEW intents.
        List<ResolveInfo> resolvedActivityList = pm.queryIntentActivities(activityIntent, PackageManager.MATCH_ALL);
        List<String> packagesSupportingCustomTabs = new ArrayList<>();
        for (ResolveInfo info : resolvedActivityList) {
            Intent serviceIntent = new Intent();
            serviceIntent.setAction(ACTION_CUSTOM_TABS_CONNECTION);
            serviceIntent.setPackage(info.activityInfo.packageName);
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info.activityInfo.packageName);
            }
        }

        // Now packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
        // and service calls. Prefer the default browser if it supports Custom Tabs.
        if (packagesSupportingCustomTabs.isEmpty()) {
            customPackageName = null;
        } else if (!TextUtils.isEmpty(defaultViewHandlerPackageName)
                && !hasSpecializedHandlerIntents(context, activityIntent)
                && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)) {
            customPackageName = defaultViewHandlerPackageName;
        } else {
            // Otherwise, pick the next favorite Custom Tabs provider.
            customPackageName = packagesSupportingCustomTabs.get(0);
        }
        return customPackageName;
    }

    private static boolean hasSpecializedHandlerIntents(Context context, Intent intent) {
        try {
            PackageManager pm = context.getPackageManager();
            List<ResolveInfo> handlers = pm.queryIntentActivities(
                    intent,
                    PackageManager.GET_RESOLVED_FILTER);
            if (handlers.size() == 0) {
                return false;
            }
            for (ResolveInfo resolveInfo : handlers) {
                IntentFilter filter = resolveInfo.filter;
                if (filter == null) continue;
                if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue;
                if (resolveInfo.activityInfo == null) continue;
                return true;
            }
        } catch (RuntimeException e) {
            Log.e("MirrorSDK", "Runtime exception while getting specialized handlers");
        }
        return false;
    }
}
