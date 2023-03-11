package com.mirror.mirrorworld_sdk_android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mirror.mirrorworld_sdk_android.adapter.EntranceChainSpinnerAdapter;
import com.mirror.mirrorworld_sdk_android.adapter.EntranceEnvironmentSpinnerAdapter;
import com.mirror.mirrorworld_sdk_android.data.SpinnerBean;
import com.mirror.mirrorworld_sdk_android.enums.DemoChain;
import com.mirror.mirrorworld_sdk_android.enums.DemoEnv;
import com.mirror.sdk.MWEVM;
import com.mirror.sdk.MWSolana;
import com.mirror.sdk.constant.MirrorEnv;

import java.util.ArrayList;
import java.util.List;

public class EntranceActivity extends AppCompatActivity {

    private DemoChain mChain;
    private DemoEnv mEnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.entrance);

        AdapterView spinnerChain = findViewById(R.id.entrance_line2_spinner);
        AdapterView spinnerEnv = findViewById(R.id.entrance_line3_spinner);
        Button button = findViewById(R.id.entrance_line5_button);
        EditText editText = findViewById(R.id.editTextTextPersonName);

        List<SpinnerBean> heroBeans = new ArrayList<>();
        heroBeans.add(new SpinnerBean(1,"Solana"));
        heroBeans.add(new SpinnerBean(2,"Ethereum"));
        heroBeans.add(new SpinnerBean(3,"Polygon"));
        heroBeans.add(new SpinnerBean(4,"BNB"));

        EntranceChainSpinnerAdapter adapterChain = new EntranceChainSpinnerAdapter(heroBeans, this);
        spinnerChain.setAdapter(adapterChain);
        spinnerChain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
                    mChain = DemoChain.Solana;
                }else if(i == 2){
                    mChain = DemoChain.Ethereum;
                }else if(i == 3){
                    mChain = DemoChain.Polygon;
                }else if(i == 4){
                    mChain = DemoChain.BNB;
                }else {
                    Log.e("MirrorSDK","Unknown index");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<SpinnerBean> heroBeans2 = new ArrayList<>();
        heroBeans2.add(new SpinnerBean(1,"MainNet"));
        heroBeans2.add(new SpinnerBean(2,"DevNet"));
        EntranceEnvironmentSpinnerAdapter adapterEnv = new EntranceEnvironmentSpinnerAdapter(heroBeans2,this);
        spinnerEnv.setAdapter(adapterEnv);
        spinnerEnv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
                    mEnv = DemoEnv.MainNet;
                }else if(i == 2){
                    mEnv = DemoEnv.DevNet;
                }else {
                    Log.e("MirrorSDK","Unknown index");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Activity activity = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String APIKey = editText.getText().toString();
                MirrorEnv env = MirrorEnv.DevNet;
                if(mEnv == DemoEnv.MainNet){
                    env = MirrorEnv.MainNet;
                }else if(mEnv == DemoEnv.DevNet){
                    env = MirrorEnv.DevNet;
                }else {
                    Log.e("MirrorSDK","Unknwon mEnv");
                }

                if(mChain == DemoChain.Solana){
                    MWSolana.initSDK(activity,env);
                }else if(mChain == DemoChain.Ethereum){
                    MWEVM.initSDK(activity,env);
                }else if(mChain == DemoChain.Polygon){
                    MWEVM.initSDK(activity,env);
                }else if(mChain == DemoChain.BNB){
                    MWEVM.initSDK(activity,env);
                }else {
                    Log.e("MirrorSDK","Unknwon mChain");
                }
            }
        });
    }
}
