package com.mirror.sdk.ui.manage;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
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
import androidx.appcompat.app.AppCompatDialogFragment;
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
import com.mirror.sdk.ui.market.model.NFTDetailData;
import com.mirror.sdk.ui.market.utils.GiveBitmap;
import com.mirror.sdk.ui.market.utils.MarketUtils;
import com.mirror.sdk.ui.sell.ManageBase;
import com.mirror.sdk.ui.share.CornerButton;
import com.mirror.sdk.ui.share.MirrorConfirmDialog;
import com.mirror.sdk.ui.share.MirrorNoticeDialogType;
import com.mirror.sdk.ui.share.MirrorResultNotice;

import java.util.List;

public class ManageDialog extends ManageBase {

    @Override
    protected void initWithDifferentUse() {
        mSellButtonParent.setVisibility(View.GONE);
        mManageButtonParent.setVisibility(View.VISIBLE);
        mTitleTextView.setText("Manage List");
    }
}
