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
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Log.d("MirrorSDK",intent.toURI());
        Uri uri = intent.getData();
        parseScheme(uri);
    }

    private void parseScheme(Uri data){
        String accessTokenKey = "access_token";
        String accessTokenValue = "";
        String refreshTokenKey = "refresh_token";
        String refreshTokenValue = "";

        String scheme = data.getScheme();
        String authority = data.getAuthority();
        String host = data.getHost();
        int port = data.getPort();
        String path = data.getPath();
//        Set<String> queryParameterNames = data.getQueryParameterNames();
//        for (String key : queryParameterNames) {
//            Log.d("MirrorSDK",key);
//            String value = data.getQueryParameter(key);
//            Log.d("MirrorSDK v",value);
//            if(key == accessTokenKey){
//                accessTokenValue = value;
//            }else if(key == refreshTokenKey){
//                refreshTokenValue = value;
//            }
//        }

        accessTokenValue = data.getQueryParameter(accessTokenKey);
        refreshTokenValue = data.getQueryParameter(refreshTokenKey);
        Log.d("MirrorSDK aac",accessTokenValue);
        Log.d("MirrorSDK ref",refreshTokenValue);

        if (port == MirrorConstant.CustomPort_Login){
            MirrorSDK.getInstance().SetAccessToken(accessTokenValue);
            MirrorSDK.getInstance().SetRefreshToken(refreshTokenValue);
            MirrorSDK.getInstance().saveRefreshToken(refreshTokenValue);

            Intent intent = new Intent(this,MirrorSDK.getInstance().mActivity.getClass());
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

            MirrorSDK.getInstance().setLoginResponse("{\n" +
                    "        \"access_token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTIzODgsImV0aF9hZGRyZXNzIjoiMHgzOTFmMjA3YzNlOEZDYjY3ZjdjNjMwREY3NjMzMjQ5YjMwMjkyMTg0Iiwic29sX2FkZHJlc3MiOiJGc2JVRVZvaXcyNXAxTXFtS0ZoM0MyMWdHWGdkOHFqclV1dlpUU1VCNG1kdyIsImVtYWlsIjoic3F1YWxsMTk4NzE5ODdAMTYzLmNvbSIsIndhbGxldCI6eyJldGhfYWRkcmVzcyI6IjB4MzkxZjIwN2MzZThGQ2I2N2Y3YzYzMERGNzYzMzI0OWIzMDI5MjE4NCIsInNvbF9hZGRyZXNzIjoiRnNiVUVWb2l3MjVwMU1xbUtGaDNDMjFnR1hnZDhxanJVdXZaVFNVQjRtZHcifSwiY2xpZW50X2lkIjoib0U1X3h3eUFhUFVTcU1zQUtSYS05Ym5yS09QS3d5NmtYdnBnLlhsYVk2VTMxLm1pcnJvcndvcmxkLmZ1biIsImlhdCI6MTY2OTE5NzI3OSwiZXhwIjoxNjcxNzg5Mjc5LCJqdGkiOiJhdXRoOjEyMzg4In0.jNDL9hDjE90sFBQDYNt6hCQiak022bF4_ExPvpvZcI0\",\n" +
                    "        \"refresh_token\": \"pY0XJ5qk6spIcIKwysSPH\",\n" +
                    "        \"user\": {\n" +
                    "            \"id\": 12388,\n" +
                    "            \"eth_address\": null,\n" +
                    "            \"sol_address\": null,\n" +
                    "            \"email\": \"squall19871987@163.com\",\n" +
                    "            \"email_verified\": true,\n" +
                    "            \"username\": \"yellow-close\",\n" +
                    "            \"main_user_id\": null,\n" +
                    "            \"allow_spend\": true,\n" +
                    "            \"has_security\": false,\n" +
                    "            \"createdAt\": \"2022-09-19T10:25:00.000Z\",\n" +
                    "            \"updatedAt\": \"2022-09-19T10:25:00.000Z\",\n" +
                    "            \"is_subaccount\": false,\n" +
                    "            \"wallet\": {\n" +
                    "                \"eth_address\": \"0x391f207c3e8FCb67f7c630DF7633249b30292184\",\n" +
                    "                \"sol_address\": \"FsbUEVoiw25p1MqmKFh3C21gGXgd8qjrUuvZTSUB4mdw\"\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"type\": \"email\"\n" +
                    "    }");
        }else {
            Log.e("MirrorSDK","Unknown scheme port:"+port);
        }
    }
}