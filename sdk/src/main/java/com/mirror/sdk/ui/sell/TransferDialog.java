package com.mirror.sdk.ui.sell;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.R;
import com.mirror.sdk.listener.market.CancelListListener;
import com.mirror.sdk.listener.market.ListNFTListener;
import com.mirror.sdk.listener.market.TransferNFTListener;
import com.mirror.sdk.listener.market.UpdateListListener;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.ui.market.adapters.SellRecyclerViewAdapter;
import com.mirror.sdk.ui.market.apis.MirrorMarketUIAPI;
import com.mirror.sdk.ui.market.apis.listeners.GetSellSummaryListener;
import com.mirror.sdk.ui.market.apis.responses.NFTSellSummary;
import com.mirror.sdk.ui.market.enums.MirrorMarketConfig;
import com.mirror.sdk.ui.market.model.NFTDetailData;
import com.mirror.sdk.ui.market.utils.GiveBitmap;
import com.mirror.sdk.ui.market.utils.MarketUtils;
import com.mirror.sdk.ui.share.CornerButton;
import com.mirror.sdk.ui.share.MirrorConfirmDialog;
import com.mirror.sdk.ui.share.MirrorNoticeDialogType;
import com.mirror.sdk.ui.share.MirrorResultNotice;

import java.util.List;

public class TransferDialog extends DialogFragment {
    protected NFTDetailData mNFTData;
    protected Activity mActivity;
    protected Dialog mDialog;
    protected ProgressBar mProgressBar;
    protected ImageView mImageView;
    protected EditText mEditText;
    protected View mEditHint;
    protected CornerButton mConfirmButton;
    protected TextView mTitleTextView;
    protected TextView mNameTextView;

    private boolean mButtonAvailable;

    public TransferDialog(){

    }

    public void init(Activity activity,NFTDetailData data){
        mActivity = activity;
        mNFTData = data;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            Window window = dialog.getWindow();
            if (window != null) {
                if(MirrorMarketConfig.FULL_SCREEN_MODE) window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        ViewGroup totalView = (ViewGroup) mActivity.getLayoutInflater().inflate(R.layout.transfer, null);
        mProgressBar = totalView.findViewById(R.id.transfer_line2_progress);
        mImageView = totalView.findViewById(R.id.transfer_line2_iv);
        mEditText = totalView.findViewById(R.id.transfer_line2_et);
        mEditHint = totalView.findViewById(R.id.transfer_line2_et_hint);
        mConfirmButton = totalView.findViewById(R.id.transfer_button);
        mTitleTextView = totalView.findViewById(R.id.transfer_line1_tv);
        mNameTextView = totalView.findViewById(R.id.transfer_line2_tv);

        initViews(totalView);

        builder.setView(totalView);
        mDialog = builder.create();
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setCancelable(true);

        return mDialog;
    }

    private void initViews(View totalView){
        mNameTextView.setText(mNFTData.name);
        ImageButton closeButton = totalView.findViewById(R.id.transfer_line1_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mProgressBar.setVisibility(View.VISIBLE);
        mImageView.setVisibility(View.GONE);
        MarketUtils.startLoadImage(handle,mNFTData.image,mProgressBar,mImageView);
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mEditHint.setVisibility(View.GONE);
                }else {
                    mEditHint.setVisibility(View.VISIBLE);
                }
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mButtonAvailable){
                    if(s.length() == 0){
                        mButtonAvailable = false;
                        mConfirmButton.setUnclickable(R.color.mirror_button_blue_unvailable);
                    }
                }else {
                    if(s.length() != 0){
                        mButtonAvailable = true;
                        mConfirmButton.setClickable();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mConfirmButton.init(false,"Confirm",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = String.valueOf(mEditText.getText());
                if(input.isEmpty()){
                    return;
                }
                MirrorSDK.getInstance().TransferNFTToAnotherSolanaWallet(mNFTData.mint_address, input, new TransferNFTListener() {
                    @Override
                    public void onTransferSuccess(ListingResponse listingResponse) {
                        MirrorResultNotice notice = new MirrorResultNotice();
                        notice.init(mActivity,MirrorNoticeDialogType.SUCCESS,"Transfer Success!","");
                        notice.show(mActivity.getFragmentManager(),"notice");
                        mDialog.dismiss();
                    }

                    @Override
                    public void onTransferFailed(long code, String message) {
                        MirrorResultNotice notice = new MirrorResultNotice();
                        notice.init(mActivity,MirrorNoticeDialogType.FAIL,"Transfer Failed!","Please try again");
                        notice.show(mActivity.getFragmentManager(),"notice");
                    }
                });
            }
        });
        mConfirmButton.setUnclickable(R.color.mirror_button_blue_unvailable);
    }

    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    GiveBitmap ivUrl=(GiveBitmap)msg.obj;
                    ivUrl.imageView.setImageBitmap(ivUrl.bitmap);

                    mProgressBar.setVisibility(View.GONE);
                    mImageView.setVisibility(View.VISIBLE);
                    break;
            }
        };
    };
}
