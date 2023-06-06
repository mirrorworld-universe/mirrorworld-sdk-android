package com.mirror.mirrorworld_sdk_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mirror.mirrorworld_sdk_android.adapter.EntranceChainSpinnerAdapter;
import com.mirror.mirrorworld_sdk_android.adapter.EntranceEnvironmentSpinnerAdapter;
import com.mirror.mirrorworld_sdk_android.data.SpinnerBean;
import com.mirror.sdk.MirrorWorld;
import com.mirror.sdk.constant.MirrorChains;
import com.mirror.sdk.constant.MirrorEnv;

import java.util.ArrayList;
import java.util.List;

public class EntranceActivity extends AppCompatActivity {

    private MirrorChains mChain;
    private MirrorEnv mEnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String integerStr = "29";
        int integer = 0;
        try {
            integer = Integer.parseInt(integerStr);
        }catch (Exception e){
            Log.d("MirrorSDK",integerStr + " is not a integer.");
        }



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
        heroBeans.add(new SpinnerBean(5,"SUI"));

        EntranceChainSpinnerAdapter adapterChain = new EntranceChainSpinnerAdapter(heroBeans, this);
        spinnerChain.setAdapter(adapterChain);
        spinnerChain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    mChain = MirrorChains.Solana;
                }else if(i == 1){
                    mChain = MirrorChains.Ethereum;
                }else if(i == 2){
                    mChain = MirrorChains.Polygon;
                }else if(i == 3){
                    mChain = MirrorChains.BNB;
                }else if(i == 4){
                    mChain = MirrorChains.SUI;
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
                Log.i("MirrorSDK","Select"+i);
                if(i == 0){
                    mEnv = MirrorEnv.MainNet;
                }else if(i == 1){
                    mEnv = MirrorEnv.DevNet;
                }else {
                    Log.e("MirrorSDK","Unknown index");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Context activity = this;
        Activity activity1 = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String APIKey = editText.getText().toString();
                if(APIKey.isEmpty()){
                    activity1.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity1,"Please input apikey!",Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                MirrorEnv env = MirrorEnv.DevNet;
                if(mEnv == MirrorEnv.MainNet){
                    env = MirrorEnv.MainNet;
                }else if(mEnv == MirrorEnv.DevNet){
                    env = MirrorEnv.DevNet;
                }else {
                    Log.e("MirrorSDK","Unknwon mEnv");
                }

                Intent intent = new Intent(activity, MainActivity.class);
                if(mChain == MirrorChains.Solana){
                    MirrorWorld.initSDK(activity,APIKey,env,mChain);
                    intent.putExtra("chain", MirrorChains.Solana.getNumber());
                }else if(mChain == MirrorChains.Ethereum){
                    MirrorWorld.initSDK(activity,APIKey,env,mChain);
                    intent.putExtra("chain",MirrorChains.Ethereum.getNumber());
                }else if(mChain == MirrorChains.Polygon){
                    MirrorWorld.initSDK(activity,APIKey,env,mChain);
                    intent.putExtra("chain",MirrorChains.Polygon.getNumber());
                }else if(mChain == MirrorChains.BNB){
                    MirrorWorld.initSDK(activity,APIKey,env,mChain);
                    intent.putExtra("chain",MirrorChains.BNB.getNumber());
                }else if(mChain == MirrorChains.SUI){
                    MirrorWorld.initSDK(activity,APIKey,env,mChain);
                    intent.putExtra("chain",MirrorChains.SUI.getNumber());
                }else {
                    Log.e("MirrorSDK","Unknwon mChain");
                }
                startActivity(intent);
            }
        });

        mChain = MirrorChains.Solana;
        mEnv = MirrorEnv.MainNet;
    }
}
