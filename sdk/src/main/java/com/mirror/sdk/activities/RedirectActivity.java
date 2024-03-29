package com.mirror.sdk.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorConstant;
import com.mirror.sdk.response.CommonResponse;
import com.mirror.sdk.response.action.ApproveResponse;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.utils.MirrorGsonUtils;

import java.net.URI;
import java.net.URLDecoder;
import java.util.Set;

public class RedirectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("RedirectActivity","onCreate");
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        MirrorSDK.getInstance().logFlow("Scheme origin:"+intent.toURI());
        Uri uri = intent.getData();
        uri = Uri.parse(URLDecoder.decode(String.valueOf(uri)));
        MirrorSDK.getInstance().logFlow("Scheme decoded:"+uri);
        parseScheme(uri);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("RedirectActivity","onResume");
//        Intent intent = getIntent();
//        Log.d("MirrorSDK resume",intent.toURI());
//        Uri uri = intent.getData();
//        parseScheme(uri);
    }

    private void parseScheme(Uri data){
        String scheme = data.getScheme();
        String authority = data.getAuthority();
        String host = data.getHost();
        int port = data.getPort();
        String path = data.getPath();

        if(host.equals("approve")){
            handleApprove(data);
        }else if(host.equals("userinfo")){
            handleUserInfo(data);
        }else if(host.equals("wallet")){
            handleWallet(data);
        }else {
            Log.e("MirrorSDK","Unknown scheme host:"+host);
        }
    }


    private void handleWallet(Uri data){
        MirrorSDK.getInstance().walletLogout();

        Intent intent = new Intent(this,MirrorSDK.getInstance().returnActivity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);

        finish();
    }

    private void handleApprove(Uri data){
        String dataKey = "data";
        String dataValue = "";
        String dataStr = data.toString();
        dataStr = dataStr.replace("#","");
        data = Uri.parse(dataStr);

        dataValue = data.getQueryParameter(dataKey);
//        JsonReader jsonReader = MirrorGsonUtils.getInstance().tests(dataValue);
        MirrorSDK.getInstance().logFlow("Handle Approve data:"+dataValue);
        ApproveResponse response = MirrorGsonUtils.getInstance().fromJson(dataValue,new TypeToken<ApproveResponse>(){}.getType());

        MirrorSDK.getInstance().logFlow("Scheme auth token:"+response.authorization_token);

        MirrorSDK.getInstance().setActionApprovalToken(response.authorization_token);

        Intent intent = new Intent(this,MirrorSDK.getInstance().returnActivity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);

        String loginResult = "{\n" +
                "        \"access_token\": \""+response.auth_token+"\",\n" +
                "        \"refresh_token\": \""+response.refresh_token+"\",\n" +
                "        \"user\": "+dataValue+"\n" +
                "    }";
        MirrorSDK.getInstance().setLoginResponse(loginResult);

        finish();
    }

    private void handleUserInfo(Uri data){
        String dataKey = "data";
        String dataValue = "";
        String accessTokenKey = "access_token";
        String accessTokenValue = "";
        String refreshTokenKey = "refresh_token";
        String refreshTokenValue = "";

        accessTokenValue = data.getQueryParameter(accessTokenKey);
        refreshTokenValue = data.getQueryParameter(refreshTokenKey);
        dataValue = data.getQueryParameter(dataKey);
//        Log.d("MirrorSDK data origin:",dataValue);
        dataValue = Uri.decode(dataValue);
//        Log.d("MirrorSDK data decoded:",dataValue);
        accessTokenValue = removeQuotation(accessTokenValue);
        refreshTokenValue = removeQuotation(refreshTokenValue);
//        Log.d("MirrorSDK aac",accessTokenValue);
//        Log.d("MirrorSDK ref",refreshTokenValue);

        MirrorSDK.getInstance().SetAccessToken(accessTokenValue);
        MirrorSDK.getInstance().SetRefreshToken(refreshTokenValue);
        MirrorSDK.getInstance().saveRefreshToken(refreshTokenValue);

        Intent intent = new Intent(this,MirrorSDK.getInstance().returnActivity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);

        String loginResult = "{\n" +
                "        \"access_token\": \""+accessTokenValue+"\",\n" +
                "        \"refresh_token\": \""+refreshTokenValue+"\",\n" +
                "        \"user\": "+dataValue+"\n" +
                "    }";
        MirrorSDK.getInstance().setLoginResponse(loginResult);

        finish();
    }

    private String removeQuotation(String originStr){
        String resultStr = originStr;
        if(originStr.indexOf("\"") > -1){
//            MirrorSDK.getInstance().logFlow("removeQuotation before:"+originStr);
            resultStr = originStr.substring(1,originStr.length() - 1);
//            MirrorSDK.getInstance().logFlow("removeQuotation after:"+resultStr);
        }

        return resultStr;
    }
}