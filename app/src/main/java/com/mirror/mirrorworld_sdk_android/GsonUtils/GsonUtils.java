//package com.mirror.mirrorworld_sdk_android.GsonUtils;
//
//import com.google.gson.Gson;
//
///*** @author Pu
// * @createTime 2022/8/18 17:16
// * @projectName mirrorworld-sdk-android
// * @className GsonUtils.java
// * @description TODO
// */
//public class GsonUtils {
//
//    private static volatile GsonUtils Instance;
//
//    private Gson gson;
//
//    private GsonUtils() {
//          gson = new Gson();
//    }
//    public static GsonUtils getInstance() {
//
//        if( Instance==null ) {
//            synchronized (GsonUtils.class ) {
//                if(Instance == null) {
//                    Instance =new GsonUtils();
//                }
//            }
//        }
//        return Instance;
//    }
//
//
//    public Gson GetGson(){
//        return this.gson;
//    }
//
//
//}
