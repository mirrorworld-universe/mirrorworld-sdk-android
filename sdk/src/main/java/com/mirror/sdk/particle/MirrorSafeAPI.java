package com.mirror.sdk.particle;

import com.google.gson.reflect.TypeToken;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorResCode;
import com.mirror.sdk.constant.MirrorSafeOptType;
import com.mirror.sdk.constant.MirrorUrl;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.listener.universal.OnCheckSDKUseable;
import com.mirror.sdk.request.BaseRequest;
import com.mirror.sdk.request.RequestMintNFT;
import com.mirror.sdk.response.CommonResponse;
import com.mirror.sdk.response.action.ActionAuthResponse;
import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;
import com.mirror.sdk.safe.ActionRequestOptional;
import com.mirror.sdk.utils.MirrorGsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MirrorSafeAPI {
    final public static void getSecurityToken(String type, String message, int value, BaseRequest request, MirrorCallback callback){
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

    final private static void requestActionAuthorization(String type, String message, int value, BaseRequest requestPrams){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
            jsonObject.put("message", message);
            jsonObject.put("value", value);
            JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(requestPrams);
            jsonObject.put("params",params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = MirrorSDK.getInstance().getActionRoot() + MirrorUrl.URL_ACTION_REQUEST;
        MirrorSDK.getInstance().checkParamsAndPost(url,data,MirrorSDK.getInstance().getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().logFlow("requestActionAuthorization result:"+result);
                CommonResponse<ActionAuthResponse> response = MirrorGsonUtils.getInstance().fromJson(result,new TypeToken<CommonResponse<ActionAuthResponse>>(){}.getType());
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
        String url = MirrorSDK.getInstance().getActionRootWithoutVersion() + MirrorUrl.URL_ACTION_APPROVE + actionUUID;// + "?useSchemeRedirect=true";
        MirrorSDK.getInstance().openUrl(url);
    }
}
