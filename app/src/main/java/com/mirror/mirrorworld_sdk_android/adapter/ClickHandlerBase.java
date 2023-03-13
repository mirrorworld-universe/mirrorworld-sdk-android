package com.mirror.mirrorworld_sdk_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mirror.mirrorworld_sdk_android.DemoAPI;

public class ClickHandlerBase {
    Activity mContext;
    ClickHandlerBase(Activity context){
        mContext = context;
    }


    public void handleClick(DemoAPI apiId, MultiParaItemRecyclerViewAdapter.ViewHolder holder, View view){

    }

    boolean checkEt(EditText et1){
        if(et1 == null || (et1 != null && et1.getText().length() == 0)){
            Log.e("MirrorSDK","edit text is null!"+et1.getText().length());
            return false;
        }
        return true;
    }
    void showToast(String content){
        Toast.makeText(mContext,content,Toast.LENGTH_LONG).show();
    }

    String getStringFromEditText(EditText editText){
        return String.valueOf(editText.getText());
    }

    void runInUIThread(MultiParaItemRecyclerViewAdapter.ViewHolder holder, String showStr){
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                holder.mResultView.setText(showStr);
            }
        });
    }
}
