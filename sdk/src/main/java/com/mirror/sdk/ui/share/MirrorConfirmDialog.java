package com.mirror.sdk.ui.share;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.enums.MirrorMarketConfig;

public class MirrorConfirmDialog extends DialogFragment {
    private Activity mActivity;
    private String mContont;
    private TextView mTextView;
    private Dialog mDialog;
    private View.OnClickListener mListener;

    public MirrorConfirmDialog(){
    }

    public void init(Activity activity,String content, View.OnClickListener listener){
        mContont = content;
        mListener = listener;
        mActivity = activity;
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
        ViewGroup totalView = (ViewGroup) mActivity.getLayoutInflater().inflate(R.layout.confirm_dialog, null);
        mTextView = totalView.findViewById(R.id.confirm_dialog_tv);
        Button cancel = totalView.findViewById(R.id.confirm_dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        Button confirm = totalView.findViewById(R.id.confirm_dialog_confirm);
        confirm.setOnClickListener(mListener);

        mTextView.setText(mContont);

        builder.setView(totalView);
        mDialog = builder.create();
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

//        setCancelable(true);

        return mDialog;
    }
}
