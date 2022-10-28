package com.mirror.sdk.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.MirrorWorld;

public class RedirectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Log.d("mmmmmmmmm",intent.toURI());
        Uri uri = intent.getData();

        MirrorSDK.getInstance().saveRefreshToken("");
        startActivity(new Intent(this,MirrorSDK.getInstance().mActivity.getClass()));
    }
}