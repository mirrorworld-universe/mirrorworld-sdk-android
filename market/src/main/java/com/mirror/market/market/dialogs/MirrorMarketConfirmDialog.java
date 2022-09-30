package com.mirror.market.market.dialogs;

import android.app.Activity;
import android.app.Dialog;
        import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
        import android.view.Window;
import android.widget.Button;

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

    private View main;
    private View success;
    private View fail;
    private View waitting;
    private Button button;
    private View buttonParent;
    private View notFound;

    private Activity activity;

    public MirrorMarketConfirmDialog(@NonNull Context context) {
        super(context, R.style.bottom_dialog_bg_style);
        this.activity = (Activity) context;
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
        initView();
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




    private void initView(){
        main = findViewById(R.id.confirm_main);
        success = findViewById(R.id.confirm_success);
        fail = findViewById(R.id.confirm_fail);
        waitting = findViewById(R.id.confirm_waitting);
        buttonParent = findViewById(R.id.confirm_button);
        button = buttonParent.findViewById(R.id.confirm_button_button);
        notFound = findViewById(R.id.confirm_not_found);
        enterConfirm();
    }

    private void enterConfirm(){

        main.setVisibility(View.VISIBLE);
        success.setVisibility(View.GONE);
        fail.setVisibility(View.GONE);
        waitting.setVisibility(View.GONE);
        buttonParent.setVisibility(View.VISIBLE);
        button.setText("Buy");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 enterWait();
                // call api buy nft
                //MirrorSDK.getInstance().BuyNFT();

                // just test
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         try {
                             Thread.sleep(3000);
                         } catch (InterruptedException e) {
                         e.printStackTrace();
                         }

                         activity.runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 enterSuccess();
                             }
                         });

                     }
                 }).start();

            }
        });

    }

    private void enterWait(){

        main.setVisibility(View.GONE);
        success.setVisibility(View.GONE);
        fail.setVisibility(View.GONE);
        waitting.setVisibility(View.VISIBLE);
        buttonParent.setVisibility(View.GONE);
    }

    private void enterSuccess(){

        main.setVisibility(View.GONE);
        success.setVisibility(View.VISIBLE);
        fail.setVisibility(View.GONE);
        waitting.setVisibility(View.GONE);
        buttonParent.setVisibility(View.VISIBLE);
        button.setText("Back to MarketPlace");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // back to market place
               // MirrorSDK.getInstance().OpenMarket();

                // just test
                enterFailure();
            }
        });

    }

    private void enterFailure(){

        main.setVisibility(View.GONE);
        success.setVisibility(View.GONE);
        fail.setVisibility(View.VISIBLE);
        waitting.setVisibility(View.GONE);
        buttonParent.setVisibility(View.VISIBLE);
        button.setText("Retry");

        // just test
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterWait();
                // call api buy nft
                //MirrorSDK.getInstance().BuyNFT();

                // just test
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               enterNotFound();
                            }
                        });

                    }
                }).start();

            }
        });

    }

    private void enterNotFound(){
        // todo add View
        main.setVisibility(View.GONE);
        success.setVisibility(View.GONE);
        fail.setVisibility(View.GONE);
        waitting.setVisibility(View.GONE);
        buttonParent.setVisibility(View.GONE);
        notFound.setVisibility(View.VISIBLE);

    }








}
