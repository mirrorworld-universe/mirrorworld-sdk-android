package com.mirror.sdk.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.MirrorWorld;
import com.mirror.sdk.constant.MirrorConstant;

import java.util.Set;

public class RedirectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("RedirectActivity","onCreate");
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Log.d("MirrorSDK",intent.toURI());
        Uri uri = intent.getData();
        parseScheme(uri);
    }

    @Override
    protected void onResume() {
        Log.d("RedirectActivity","onResume");
        super.onResume();
        Intent intent = getIntent();
        Log.d("MirrorSDK resume",intent.toURI());
        Uri uri = intent.getData();
        parseScheme(uri);
    }

    private void parseScheme(Uri data){

        String dataKey = "data";
        String dataValue = "";
        String accessTokenKey = "access_token";
        String accessTokenValue = "";
        String refreshTokenKey = "refresh_token";
        String refreshTokenValue = "";

        String scheme = data.getScheme();
        String authority = data.getAuthority();
        String host = data.getHost();
        int port = data.getPort();
        String path = data.getPath();

        accessTokenValue = data.getQueryParameter(accessTokenKey);
        refreshTokenValue = data.getQueryParameter(refreshTokenKey);
        dataValue = data.getQueryParameter(dataKey);
        Log.d("MirrorSDK data origin:",dataValue);
        dataValue = Uri.decode(dataValue);
        Log.d("MirrorSDK data decoded:",dataValue);
        accessTokenValue = removeQuotation(accessTokenValue);
        refreshTokenValue = removeQuotation(refreshTokenValue);
        Log.d("MirrorSDK aac",accessTokenValue);
        Log.d("MirrorSDK ref",refreshTokenValue);

        MirrorSDK.getInstance().SetAccessToken(accessTokenValue);
        MirrorSDK.getInstance().SetRefreshToken(refreshTokenValue);
        MirrorSDK.getInstance().saveRefreshToken(refreshTokenValue);

        Intent intent = new Intent(this,MirrorSDK.getInstance().mActivity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);

        String loginResult = "{\n" +
                "        \"access_token\": \""+accessTokenValue+"\",\n" +
                "        \"refresh_token\": \""+refreshTokenValue+"\",\n" +
                "        \"user\": "+dataValue+"\n" +
                "    }";
        MirrorSDK.getInstance().logFlow("loginResult:"+loginResult);

        MirrorSDK.getInstance().setLoginResponse(loginResult);

        finish();
    }

    private String removeQuotation(String originStr){
        String resultStr = originStr;
        if(originStr.indexOf("\"") > -1){
            MirrorSDK.getInstance().logFlow("removeQuotation before:"+originStr);
            resultStr = originStr.substring(1,originStr.length() - 1);
            MirrorSDK.getInstance().logFlow("removeQuotation after:"+resultStr);
        }

        return resultStr;
    }
}