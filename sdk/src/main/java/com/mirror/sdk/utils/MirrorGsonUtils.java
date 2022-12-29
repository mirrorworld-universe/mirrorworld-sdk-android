package com.mirror.sdk.utils;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
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

    public <T> T fromJson(JsonReader jr, Type type) {
        return mGson.fromJson(jr, type);
    }

    public JsonReader tests(String s){
        JsonReader jr = new JsonReader(new StringReader(s.trim()));
        jr.setLenient(true);
        return jr;
    }

    public String toJson(Object object){
        return mGson.toJson(object);
    }

    public JSONObject toJsonObj(Object object){
        String jsonStr = mGson.toJson(object);
        try {
            return new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
