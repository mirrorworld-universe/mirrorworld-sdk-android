package com.mirror.sdk.particle;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorResCode;
import com.mirror.sdk.constant.MirrorUrl;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.listener.universal.OnCheckSDKUseable;
import com.mirror.sdk.response.CommonResponse;
import com.mirror.sdk.response.action.ActionDTO;
import com.mirror.sdk.utils.MirrorGsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class MirrorSafeAPI {
    final public static void getSecurityToken(String type, String message, int value, JSONObject request, MirrorCallback callback){
        MirrorSDK.getInstance().safeFlowCb = callback;
        MirrorSDK.getInstance().sdkSimpleCheck(new OnCheckSDKUseable() {
            @Override
            public void OnChecked() {
                requestActionAuthorization(type, message, value, request);
            }

            @Override
            public void OnUnUsable() {
                MirrorSDK.getInstance().logFlow("Please init SDK first.");
            }
        });
    }

    final private static void handleValue(JSONObject request,JSONObject requestParams) throws JSONException {
        Object amountObj = null;
        if(requestParams.has("amount")){
            amountObj = requestParams.get("amount");
        }

        Object priceObj = null;
        if(requestParams.has("price")) {
            priceObj = requestParams.get("price");
        }

        int decimals = -100;
        if(requestParams.has("decimals")) {
            Object decimalsObj = requestParams.get("decimals");
            decimals = Integer.valueOf(String.valueOf(decimalsObj));
        }

        BigDecimal valueValue = null;
        int valueCount = 0;
        if(amountObj != null){
            valueValue = new BigDecimal(String.valueOf(amountObj));
            valueCount++;
        }
        if(priceObj != null){
            valueValue = new BigDecimal(String.valueOf(priceObj));
            valueCount++;
        }

        if(valueCount == 0){
            return;
        }

        if(valueCount > 1){
            Log.e("MirrorSDK","There is both exists amount and price!");
            return;
        }

        if(decimals == -100){
            decimals = 9;
        }

        String valueKey = "value";
        if(request.has(valueKey)) {
            request.remove(valueKey);
            BigDecimal dec = new BigDecimal(Math.pow(10,decimals));
            BigDecimal v = valueValue.divide(dec,decimals,BigDecimal.ROUND_HALF_UP);
            request.put(valueKey,v.toPlainString());
        }else {
            Log.e("MirrorSDK","No value param when approving!");
        }
    }

    final private static String getDecimalValue(int decimals,String value){
        int offset = decimals - value.length();
        if(offset > 0){
            String finalStr = "0.";
            for(int i=0;i<offset;i++){
                finalStr += "0";
            }
            finalStr += value;

            return finalStr;
        }else if(offset == 0){
            String finalStr = "0." + value;
            return finalStr;
        }else {
            StringBuilder finalStr = new StringBuilder(value);
            finalStr.insert(offset,".");
            return finalStr.toString();
        }
    }

    final private static void requestActionAuthorization(String type, String message, int value, JSONObject requestPrams){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("mint_address", "1111");
//            jsonObject.put("price", 1.2);
//            jsonObject.put("decimals",4);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        JSONObject aaa = new JSONObject();
//        try {
//            aaa.put("type", type);
//            aaa.put("message", message);
//            aaa.put("value", value);
//            aaa.put("params",requestPrams);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String baseStr = "{\"collection_mint\":\"DUuMbpmH3oiREntViXfGZhrLMbVcYBwGeBa4Wn9X8QfM\",\"name\":\"1\",\"symbol\":\"1\",\"url\":\"https:\\/\\/metadata-assets.mirrorworld.fun\\/mirror_jump\\/metadata\\/1.json\"}\n";
//        try {
//            JSONObject baseObj = new JSONObject(baseStr);
//            handleValue(aaa,jsonObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            Log.d("MirrorSDK","adfasdf"+aaa.get("value"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
            jsonObject.put("message", message);
            jsonObject.put("value", value);
            jsonObject.put("params",requestPrams);
            handleValue(jsonObject,requestPrams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().logFlow("request auth data"+data);

        String url = MirrorSDK.getInstance().getActionRoot() + MirrorUrl.URL_ACTION_REQUEST;
        MirrorSDK.getInstance().checkParamsAndPost(url,data,MirrorSDK.getInstance().getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().logFlow("requestActionAuthorization result:"+result);
                CommonResponse<ActionDTO> response = MirrorGsonUtils.getInstance().fromJson(result,new TypeToken<CommonResponse<ActionDTO>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    openApprovePage(response.data.uuid);
                }else {
//                    if(callback != null) callback("");
                }
            }
        }));
    }

    final private static void openApprovePage(String actionUUID){
        if(actionUUID == ""){
            MirrorSDK.logError("uuid from server is null!");
            return;
        }
        String url = MirrorSDK.getInstance().getActionRootWithoutVersion() + MirrorUrl.URL_ACTION_APPROVE + actionUUID;
        MirrorSDK.getInstance().openApprovePage(url);
    }
}
