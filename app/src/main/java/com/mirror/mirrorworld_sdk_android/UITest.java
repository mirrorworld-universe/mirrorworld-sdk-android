package com.mirror.mirrorworld_sdk_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.ui.market.dialogs.MirrorMarketConfirmDialog;
import com.mirror.sdk.ui.market.dialogs.MirrorMarketDialog;

public class UITest extends AppCompatActivity {


    private TextView Button;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uitest);
        activity = this;
        InitView();
    }


    private void InitView()
    {
         Button = findViewById(R.id.click);
         Button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 MirrorMarketDialog MirrorMarket = new MirrorMarketDialog();
                 MirrorMarket.Init(activity);
                 MirrorSDK.getInstance().InitSDK(activity, MirrorEnv.DevNet);
                 MirrorSDK.getInstance().OpenMarket();
             }
         });


    }





}