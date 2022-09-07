package com.mirror.sdk.utils;

public class MirrorStringUtils {
    public static String GetFailedNotice(String preNotice,long code,String message){
        return preNotice + "==" + "code:" + code + " message:" + message;
    }
}
