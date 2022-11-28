package com.mirror.sdk;

import android.app.Activity;

import com.mirror.sdk.constant.MirrorEnv;

public class MirrorWorld {

    public static void initMirrorWorld(Activity activity, MirrorEnv mirrorEnv){
        MirrorSDK.getInstance().InitSDK(activity,mirrorEnv);
    }

    public static void startLogin(){
        MirrorSDK.getInstance().StartLogin();
    }

}
