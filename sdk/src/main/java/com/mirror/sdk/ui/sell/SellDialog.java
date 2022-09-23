package com.mirror.sdk.ui.sell;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.mirror.sdk.listener.market.ListNFTListener;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.ui.market.adapters.SellRecyclerViewAdapter;
import com.mirror.sdk.ui.market.apis.MirrorMarketUIAPI;
import com.mirror.sdk.ui.market.apis.listeners.GetSellSummaryListener;
import com.mirror.sdk.ui.market.apis.responses.NFTSellSummary;
import com.mirror.sdk.ui.market.model.NFTDetailData;
import com.mirror.sdk.ui.market.utils.GiveBitmap;
import com.mirror.sdk.ui.market.utils.MarketUtils;
import com.mirror.sdk.ui.share.MirrorNoticeDialogType;
import com.mirror.sdk.ui.share.MirrorResultNotice;

import java.util.List;

public class SellDialog extends DialogFragment {

    private NFTDetailData mNFTData;
    private Activity mActivity;
    private double mServiceRate = 0.0425;

    private Dialog mDialog;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    private RecyclerView mInfoRecyclerView;
    private TextView mNotice;
    private EditText mEditText;
    private ViewGroup mEditHint;
    private ImageButton mConfirmButton;

    public SellDialog(Activity activity){
        mActivity = activity;
    }

    public void init(NFTDetailData data){
        mNFTData = data;
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

        initViews(totalView);

        builder.setView(totalView);
        mDialog = builder.create();
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setCancelable(true);

        return mDialog;
    }

    private void initViews(View totalView){
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
                    if(inputStr.matches("^[0-9]*$")){
                        double input = Double.parseDouble(inputStr);
                        double number = (1-mServiceRate) * input;
                        mNotice.setText("You Will Receive "+number+" SOL");
                    }else {
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
    }

    private void startListNFT(){
        String inputStr = String.valueOf(mEditText.getText());
        double price;
        if(inputStr.matches("^[0-9]*$") && !inputStr.isEmpty()){
            double input = Double.parseDouble(inputStr);
            double number = (1-mServiceRate) * input;
            price = number;
            mNotice.setText("You Will Receive "+number+" SOL");
        }else {
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
