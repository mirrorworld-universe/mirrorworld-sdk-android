package com.mirror.market.share;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.enums.MirrorMarketConfig;
import com.mirror.sdk.ui.market.model.NFTDetailData;

public class MirrorResultNotice extends DialogFragment {

    private Activity mActivity;
    private MirrorNoticeDialogType mImagePositive;
    private String mNotice1;
    private String mNotice2;
    private TextView mTv1;
    private TextView mTv2;
    private Dialog mDialog;
    private ImageView mImageView;
    private View mProgress;

    public MirrorResultNotice(){

    }

    public void init(Activity activity,MirrorNoticeDialogType imageType,String notice1,String notice2){
        mImagePositive = imageType;
        mNotice1 = notice1;
        mActivity = activity;
        mNotice2 = notice2;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            Window window = dialog.getWindow();
            if (window != null) {
//                int width = ViewGroup.LayoutParams.MATCH_PARENT;
//                int height = ViewGroup.LayoutParams.MATCH_PARENT;
//                window.setLayout(width, height);
                if(MirrorMarketConfig.FULL_SCREEN_MODE) window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        ViewGroup totalView = (ViewGroup) mActivity.getLayoutInflater().inflate(R.layout.notice_dialog, null);
        mImageView = totalView.findViewById(R.id.notice_iv);
        mProgress = totalView.findViewById(R.id.notice_progress);
        mTv1 = totalView.findViewById(R.id.notice_tv1);
        mTv2 = totalView.findViewById(R.id.notice_tv2);
        ImageButton button = totalView.findViewById(R.id.notice_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mTv1.setText(mNotice1);
        mTv2.setText(mNotice2);
        if(mImagePositive == MirrorNoticeDialogType.SUCCESS){
            mImageView.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);
            mImageView.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_success_pic));
        }else if(mImagePositive == MirrorNoticeDialogType.FAIL){
            mImageView.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);
            mImageView.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_wrong_pic));
        }else {
            mImageView.setVisibility(View.GONE);
            mProgress.setVisibility(View.VISIBLE);
        }

        builder.setView(totalView);
        mDialog = builder.create();
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

//        setCancelable(true);

        return mDialog;
    }
}
