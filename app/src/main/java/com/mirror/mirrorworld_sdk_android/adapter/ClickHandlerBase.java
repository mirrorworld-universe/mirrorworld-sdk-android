package com.mirror.mirrorworld_sdk_android.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mirror.mirrorworld_sdk_android.enums.DemoAPI;

public abstract class ClickHandlerBase {
    Activity mActivity;
    ClickHandlerBase(Activity context){
        mActivity = context;
    }


    abstract public void handleClick(Activity returnActivity, DemoAPI apiId, MultiParaItemRecyclerViewAdapter.ViewHolder holder, View view);

    boolean isInteger(String integerStr){
        int integer = 0;
        try {
            integer = Integer.parseInt(integerStr);
        }catch (Exception e){
            Log.d("MirrorSDK",integerStr + " is not a integer.");
            return false;
        }
        return true;
    }
    boolean isFloat(String integerStr){
        float integer = 0;
        try {
            integer = Float.parseFloat(integerStr);
        }catch (Exception e){
            Log.d("MirrorSDK",integerStr + " is not a float.");
            return false;
        }
        return true;
    }

    float getFloat(String integerStr){
        float integer = 0;
        try {
            integer = Float.parseFloat(integerStr);
        }catch (Exception e){
            Log.d("MirrorSDK",integerStr + " is not a float.");
            return -1.0f;
        }
        return integer;
    }
    boolean isDouble(String integerStr){
        double integer = 0;
        try {
            integer = Double.parseDouble(integerStr);
        }catch (Exception e){
            Log.d("MirrorSDK",integerStr + " is not a double.");
            return false;
        }
        return true;
    }

    double getDouble(String integerStr){
        double integer = 0;
        try {
            integer = Double.parseDouble(integerStr);
        }catch (Exception e){
            Log.d("MirrorSDK",integerStr + " is not a double.");
            return -1.0;
        }
        return integer;
    }

    int getInteger(String integerStr){
        int integer = 0;
        try {
            integer = Integer.parseInt(integerStr);
        }catch (Exception e){
            Log.d("MirrorSDK",integerStr + " is not a integer.");
            return -1;
        }
        return integer;
    }

    boolean checkEt(EditText et1){
        if(et1 == null || (et1 != null && et1.getText().length() == 0)){
            Log.e("MirrorSDK","edit text is null!"+et1.getText().length());
            return false;
        }
        return true;
    }
    void showToast(String content){
        Toast.makeText(mActivity,content,Toast.LENGTH_LONG).show();
    }

    String getStringFromEditText(EditText editText){
        return String.valueOf(editText.getText());
    }

    void runInUIThread(MultiParaItemRecyclerViewAdapter.ViewHolder holder, String showStr){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                holder.mResultView.setText(showStr);
            }
        });
    }
}
