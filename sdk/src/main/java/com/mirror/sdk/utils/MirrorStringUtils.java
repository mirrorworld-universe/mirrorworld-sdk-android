package com.mirror.sdk.utils;

public class MirrorStringUtils {
    public static String GetFailedNotice(String preNotice,long code,String message){
        return preNotice + "==" + "code:" + code + " message:" + message;
    }

    public static String floatToString(float number){
        return ""+number;
    }
    public static String doubleToString(double number){
        return ""+number;
    }
    public static int handleInt(int number){
        return number;
    }
}
