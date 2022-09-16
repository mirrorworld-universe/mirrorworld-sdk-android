package com.mirror.sdk.ui.market;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.model.NFTDetailData;

public class MirrorMarketNFTDetailDialog extends DialogFragment {
    Activity mActivity = null;
    boolean mInited = false;
    NFTDetailData mNFTDetailData;
    OnBuyListener mOnBuyListener;

    public void Init(Activity activity, NFTDetailData nftDetailData){
        if(mInited){
            return;
        }
        mInited = true;

        mActivity = activity;
        mNFTDetailData = nftDetailData;
    }

    public void setOnBuyListener(OnBuyListener listener){
        mOnBuyListener = listener;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                window.setLayout(width, height);
                if(MirrorMarketConfig.FULL_SCREEN_MODE) window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(!mInited){
            Log.e("MirrorMarket","Not inited!");
            return null;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.market_nftdetail, null);
        ImageButton imageButton = view.findViewById(R.id.nftdetail_btn_buy);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnBuyListener != null) mOnBuyListener.onBuy(mNFTDetailData);
            }
        });

        builder.setView(view);

        return builder.create();
    }
}
