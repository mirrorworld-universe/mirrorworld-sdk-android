package com.mirror.sdk.ui.market.dialogs;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.MarketUIController;
import com.mirror.sdk.ui.market.enums.MirrorMarketConfig;
import com.mirror.sdk.ui.market.apis.MirrorMarketUIAPI;
import com.mirror.sdk.ui.market.apis.listeners.GetCollectionListener;
import com.mirror.sdk.ui.market.apis.listeners.GetNFTsListener;
import com.mirror.sdk.ui.market.apis.responses.CollectionInfo;
import com.mirror.sdk.ui.market.apis.responses.GetCollectionsResponse;
import com.mirror.sdk.ui.market.model.NFTDetailData;
import com.mirror.sdk.ui.market.widgets.MarketMainCollectionTabsAdapter;
import com.mirror.sdk.ui.market.widgets.MarketMainRecyclerAdapter;
import com.mirror.sdk.ui.market.widgets.MirrorExpandedButton;

import java.util.ArrayList;
import java.util.List;

public class MirrorMarketDialog extends DialogFragment {

    Activity mActivity = null;
    boolean mInited = false;

    //widgets
    RecyclerView mNFTRecyclerView;
    ConstraintLayout mFilterParent;

    public void Init(Activity activity){
        if(mInited){
            return;
        }
        mInited = true;

        mActivity = activity;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(!mInited){
            Log.e("MirrorMarket","Not inited!");
            return null;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View totalView = mActivity.getLayoutInflater().inflate(R.layout.market_main, null);
        mFilterParent = totalView.findViewById(R.id.market_main_filter_parent);
        mNFTRecyclerView = totalView.findViewById(R.id.market_main_nft_list);

        RecyclerView collectionTabView = totalView.findViewById(R.id.market_main_type_parent);
        MarketUIController.getInstance().setMainParent(collectionTabView);
        collectionTabView.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
        startRequestCollections(collectionTabView);


        //设置 布局管理器 这里是实现GridView的效果所以 我们加载GrdiLayoutMannager
        GridLayoutManager gridLayoutManager = new
                //第一个参数是 这个Activity的上下文Context 然后第二个参数是 一行显示的个数 2就是一行2个
                GridLayoutManager(mActivity,2);
        mNFTRecyclerView.setLayoutManager(gridLayoutManager);

        builder.setView(totalView);
        Dialog dialog = builder.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
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

    private List<NFTDetailData> getdata() {
        List<NFTDetailData> iconbeans = new ArrayList<>();
        String title[] = new String[]{"语文","数学","英语","物理","化学","生物"};
        String Engtitle[] = new String[]{"Chinese","Math","English","Physics","Chemistry","Biology"};
//        int iconid[] = new int[]{R.mipmap.yuwen,R.mipmap.shuxue,R.mipmap.yingyu,R.mipmap.wuli,R.mipmap.huaxue,R.mipmap.shengwu};
        String Color[] = new String[]{"#255bb5","#fcd1be","#4fb279","#ffcf6d","#cdd8ee","#cb8dff"};
        for(int i=0; i<title.length; i++){
            NFTDetailData bean = new NFTDetailData();
            bean.name = title[i];
//            bean.color = Color[i];
            iconbeans.add(bean);
        }
        return iconbeans;
    }

    private void startRequestCollections(RecyclerView view){
        MirrorMarketUIAPI.GetCollections(new GetCollectionListener() {
            @Override
            public void onSuccess(GetCollectionsResponse response) {
                if(response.collections.size() == 0){
                    View collectionParent = view.findViewById(R.id.market_main_type_parent);
                    collectionParent.setVisibility(View.GONE);
                }else if(response.collections.size() == 1){
                    View collectionParent = view.findViewById(R.id.market_main_type_parent);
                    collectionParent.setVisibility(View.GONE);

                    CollectionInfo collection = response.collections.get(0);
                    MarketUIController.getInstance().selectCollection(collection);
                    setFilterBar(mFilterParent,collection);
                    startRequestNFT();
                }
                MarketMainCollectionTabsAdapter adapter = new MarketMainCollectionTabsAdapter(response.collections);
                view.setAdapter(adapter);
            }

            @Override
            public void onFail(long code, String message) {
                Log.e("MirrorMarket:","code:"+code+" message:"+message);
            }
        });
    }

    private void startRequestNFT(){
        MirrorMarketUIAPI.GetNFTs(new GetNFTsListener() {
            @Override
            public void onSuccess(List<NFTDetailData> nfts) {
                MarketMainRecyclerAdapter adapter = new MarketMainRecyclerAdapter(nfts);
                adapter.setCardViewOnClickListener(new MarketMainRecyclerAdapter.OnNFTItemClickListener() {
                    @Override
                    public void onClicked(View view, NFTDetailData data) {
                        logMarket("nft clicked "+data.name);
                        MirrorMarketNFTDetailDialog detailDialog = new MirrorMarketNFTDetailDialog();
                        detailDialog.Init(mActivity,data);
                        detailDialog.show(mActivity.getFragmentManager(), "Add group dialog");
                        detailDialog.setOnBuyListener(new OnBuyListener() {
                            @Override
                            public void onBuy(NFTDetailData nftDetailData) {
                                //todo: show thumb page and conform
                                MirrorMarketConfirmDialog confirmDialog = new MirrorMarketConfirmDialog(mActivity);
                                confirmDialog.init(nftDetailData);
                                confirmDialog.show();
                            }
                        });
                    }
                });
                mNFTRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    private void setFilterBar(View view,CollectionInfo collectionInfo){
        MirrorExpandedButton orderButton = view.findViewById(R.id.market_main_filter_order);
        orderButton.setText(collectionInfo.collection_orders.get(0).order_desc);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        MirrorExpandedButton expandButton = view.findViewById(R.id.market_main_filter_expand);
        expandButton.setText("Filter");
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void logMarket(String content){
        Log.i("market:",content);
    }
}
