package com.mirror.sdk.ui.sell;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

public class ManageBase extends DialogFragment {

     protected NFTDetailData mNFTData;
    protected Activity mActivity;
    protected double mServiceRate = 0.0425;

    protected Dialog mDialog;
    protected ProgressBar mProgressBar;
    protected ImageView mImageView;
    protected RecyclerView mInfoRecyclerView;
    protected TextView mNotice;
    protected EditText mEditText;
    protected ViewGroup mEditHint;
    protected ImageButton mConfirmButton;
    protected CornerButton mManageCancelButton;
    protected CornerButton mManageConfirmButton;
     protected ViewGroup mSellButtonParent;
    protected ViewGroup mManageButtonParent;
    protected TextView mTitleTextView;
    protected TextView mNameTextView;

    public ManageBase(){

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
        ViewGroup totalView = (ViewGroup) mActivity.getLayoutInflater().inflate(R.layout.sell_main, null);
        mProgressBar = totalView.findViewById(R.id.sell_line2_progress);
        mImageView = totalView.findViewById(R.id.sell_line2_iv);
        mInfoRecyclerView = totalView.findViewById(R.id.sell_line3_recyclerview);
        mNotice = totalView.findViewById(R.id.sell_line2_notice);
        mEditText = totalView.findViewById(R.id.sell_line2_et);
        mEditHint = totalView.findViewById(R.id.sell_line2_ex_tint);
        mConfirmButton = totalView.findViewById(R.id.sell_button);
        mManageCancelButton = totalView.findViewById(R.id.sell_managebuttons_cancel);
        mManageConfirmButton = totalView.findViewById(R.id.sell_managebuttons_confirm);
        mSellButtonParent = totalView.findViewById(R.id.sell_sellbuttons_parent);
        mManageButtonParent = totalView.findViewById(R.id.sell_managebuttons_parent);
        mTitleTextView = totalView.findViewById(R.id.sell_line1_tv);
        mNameTextView = totalView.findViewById(R.id.sell_line2_tv);

        initViews(totalView);
        initWithDifferentUse();

        builder.setView(totalView);
        mDialog = builder.create();
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setCancelable(true);

        return mDialog;
    }

    protected void initWithDifferentUse(){
        mSellButtonParent.setVisibility(View.GONE);
        mManageButtonParent.setVisibility(View.VISIBLE);
    }

    private void initViews(View totalView){
        mNameTextView.setText(mNFTData.name);
        ImageButton closeButton = totalView.findViewById(R.id.sell_line1_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mProgressBar.setVisibility(View.VISIBLE);
        mImageView.setVisibility(View.GONE);
        MarketUtils.startLoadImage(handle,mNFTData.image,mProgressBar,mImageView);
        startRequestSummary();
        mNotice.setVisibility(View.GONE);
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mEditHint.setVisibility(View.GONE);
                    mNotice.setVisibility(View.GONE);
                }else {
//                    mEditHint.setVisibility(View.VISIBLE);
                    mNotice.setVisibility(View.VISIBLE);
                    String inputStr = String.valueOf(mEditText.getText());
                    try{
                        double input = Double.parseDouble(inputStr);
                        double number = (1-mServiceRate) * input;
                        mNotice.setText("You Will Receive "+number+" SOL");
                    }catch (NumberFormatException e){
                        mNotice.setText("Please input a number.");
                    }
                }
            }
        });
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListNFT();
            }
        });
        mManageCancelButton.init(true,"Cancel Listing",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCancelListing();
            }
        });
        mManageConfirmButton.init(false,"Update",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpdateListing();
            }
        });
    }

    private void startUpdateListing(){
        String mintAddress = mNFTData.mint_address;
        String inputStr = String.valueOf(mEditText.getText());
        double price;
        mNotice.setVisibility(View.VISIBLE);
        try {
            double input = Double.parseDouble(inputStr);
            double number = (1-mServiceRate) * input;
            price = number;
            mNotice.setText("You Will Receive "+number+" SOL");
        }catch (NumberFormatException e){
            mNotice.setText("Please input a number.");
            return;
        }
        MirrorConfirmDialog dialog = new MirrorConfirmDialog(mActivity);
        dialog.init("Do you want to change the price to "+price+" SOL?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MirrorSDK.getInstance().UpdateNFTListing(mintAddress, price, new UpdateListListener() {
                    @Override
                    public void onUpdateSuccess(ListingResponse listingResponse) {
                        MirrorResultNotice noticeDialog = new MirrorResultNotice(mActivity);
                        noticeDialog.init(MirrorNoticeDialogType.SUCCESS,"Congratulations!",
                                "List Successfully");
                        noticeDialog.show(mActivity.getFragmentManager(),"notice");
                        mDialog.dismiss();
                        dialog.dismiss();
                    }

                    @Override
                    public void onUpdateFailed(long code, String message) {
                        MirrorResultNotice noticeDialog = new MirrorResultNotice(mActivity);
                        noticeDialog.init(MirrorNoticeDialogType.FAIL,"Ops!",
                                "Please Try Again!");
                        noticeDialog.show(mActivity.getFragmentManager(),"notice");
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show(mActivity.getFragmentManager(),"confirm");
    }

    private void startCancelListing(){
        String mintAddress = mNFTData.mint_address;
        String inputStr = String.valueOf(mEditText.getText());
        double price;
        mNotice.setVisibility(View.VISIBLE);
        try{
            double input = Double.parseDouble(inputStr);
            double number = (1-mServiceRate) * input;
            price = number;
            mNotice.setText("You Will Receive "+number+" SOL");
        }catch (NumberFormatException e){
            mNotice.setText("Please input a number.");
            return;
        }

        MirrorConfirmDialog dialog = new MirrorConfirmDialog(mActivity);
        dialog.init("Are you sure you want to cancel the list?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MirrorSDK.getInstance().CancelNFTListing(mintAddress, price, new CancelListListener() {
                    @Override
                    public void onCancelSuccess(ListingResponse listingResponse) {
                        MirrorResultNotice noticeDialog = new MirrorResultNotice(mActivity);
                        noticeDialog.init(MirrorNoticeDialogType.SUCCESS,"Congratulations!",
                                "List Successfully");
                        noticeDialog.show(mActivity.getFragmentManager(),"notice");
                        mDialog.dismiss();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelFailed(long code, String message) {
                        MirrorResultNotice noticeDialog = new MirrorResultNotice(mActivity);
                        noticeDialog.init(MirrorNoticeDialogType.FAIL,"Ops!",
                                "Please Try Again!");
                        noticeDialog.show(mActivity.getFragmentManager(),"notice");
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show(mActivity.getFragmentManager(),"confirm");
    }

    private void startListNFT(){
        String inputStr = String.valueOf(mEditText.getText());
        double price;
        mNotice.setVisibility(View.VISIBLE);
        try {
            double input = Double.parseDouble(inputStr);
            double number = (1-mServiceRate) * input;
            price = number;
            mNotice.setText("You Will Receive "+number+" SOL");
        }catch (NumberFormatException e){
            mNotice.setText("Please input a number.");
            return;
        }
        String mintAddress = mNFTData.mint_address;
        MirrorSDK.getInstance().ListNFT(mintAddress, price, new ListNFTListener() {
            @Override
            public void onListSuccess(ListingResponse listingResponse) {
                MirrorResultNotice notice = new MirrorResultNotice(mActivity);
                notice.init(MirrorNoticeDialogType.SUCCESS,"Congratulations!","List Successfully");
                notice.show(mActivity.getFragmentManager(),"success");
            }

            @Override
            public void onListFailed(long code, String message) {
                MirrorResultNotice notice = new MirrorResultNotice(mActivity);
                notice.init(MirrorNoticeDialogType.FAIL,"Ops!","Please Try Again");
                notice.show(mActivity.getFragmentManager(),"success");
            }
        });
    }

    private void startRequestSummary(){
        MirrorMarketUIAPI.GetSellSummary(new GetSellSummaryListener() {
            @Override
            public void OnSuccess(List<NFTSellSummary> summaries) {
                SellRecyclerViewAdapter adapter = new SellRecyclerViewAdapter(summaries);
                mInfoRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
                mInfoRecyclerView.setAdapter(adapter);
            }

            @Override
            public void OnFail() {

            }
        });
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
