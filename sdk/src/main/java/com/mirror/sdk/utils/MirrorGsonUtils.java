package com.mirror.sdk.utils;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class MirrorGsonUtils {


    private static volatile MirrorGsonUtils instance;
    public static MirrorGsonUtils getInstance(){
        if (instance == null){
            synchronized(MirrorGsonUtils.class){
                instance = new MirrorGsonUtils();
            }
        }
        return instance;
    }

    private Gson mGson;

    public MirrorGsonUtils(){
        mGson = new Gson();
    }

    public <T> T fromJson(String str, Type type) {
        return mGson.fromJson(str, type);
    }

    public String toJson(Object object){
        return mGson.toJson(object);
    }


}
