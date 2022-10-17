package com.mirror.mirrorworld_sdk_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorEnv;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        MirrorSDK.getInstance().InitSDK(this, MirrorEnv.StagingDevNet);

        EditText et = findViewById(R.id.et);
        Button bt = findViewById(R.id.bt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MirrorSDK.getInstance().openInnerUrl(et.getText().toString());
            }
        });
    }
}