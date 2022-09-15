package com.mirror.sdk.ui.market;

import android.app.Dialog;
        import android.content.Context;
        import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
        import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.model.NFTDetailData;
enum ConfirmState{
    main,
    waitting,
    success,
    fail,
}
public class MirrorMarketConfirmDialog extends Dialog {

    private NFTDetailData mNFTData;
    private ConfirmState mState = ConfirmState.main;
    public MirrorMarketConfirmDialog(@NonNull Context context) {
        super(context, R.style.bottom_dialog_bg_style);
    }

    public void init(NFTDetailData data){
        mNFTData = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_confirm);
        setWindowTheme();
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setOnClickListeners();
    }

    private void setWindowTheme() {
        Window window = this.getWindow();
        // 设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        // 设置弹出动画
        window.setWindowAnimations(R.style.show_dialog_animStyle);
        // 设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void setOnClickListeners(){
        View main = findViewById(R.id.confirm_main);
        View success = findViewById(R.id.confirm_success);
        View fail = findViewById(R.id.confirm_fail);
        View waitting = findViewById(R.id.confirm_waitting);
        main.setVisibility(View.VISIBLE);
        success.setVisibility(View.GONE);
        fail.setVisibility(View.GONE);
        waitting.setVisibility(View.GONE);

        Button button = findViewById(R.id.confirm_button).findViewById(R.id.confirm_button_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mState){
                    case main:{
                        main.setVisibility(View.GONE);
                        waitting.setVisibility(View.VISIBLE);
                        mState = ConfirmState.waitting;
                        break;
                    }
                    case waitting:{
                        waitting.setVisibility(View.GONE);
                        success.setVisibility(View.VISIBLE);
                        mState = ConfirmState.success;
                        break;
                    }
                    case success:{
                        success.setVisibility(View.GONE);
                        fail.setVisibility(View.VISIBLE);
                        mState = ConfirmState.fail;
                        break;
                    }
                    case fail:{
                        fail.setVisibility(View.GONE);
                        main.setVisibility(View.VISIBLE);
                        mState = ConfirmState.main;
                        break;
                    }
                    default:{
                        Log.e("MirrorMarket:","Unknown confirm dialog state:"+mState);
                        break;
                    }
                }
            }
        });
    }

//    private void
}
