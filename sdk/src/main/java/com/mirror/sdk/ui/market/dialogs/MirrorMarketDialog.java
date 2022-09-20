package com.mirror.sdk.ui.market.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.MarketDataController;
import com.mirror.sdk.ui.market.MarketUIController;
import com.mirror.sdk.ui.market.apis.listeners.GetFilterListener;
import com.mirror.sdk.ui.market.apis.responses.CollectionFilter;
import com.mirror.sdk.ui.market.apis.responses.CollectionOrder;
import com.mirror.sdk.ui.market.droplist.DropListSimpleAdapter;
import com.mirror.sdk.ui.market.enums.MirrorMarketConfig;
import com.mirror.sdk.ui.market.apis.MirrorMarketUIAPI;
import com.mirror.sdk.ui.market.apis.listeners.GetCollectionListener;
import com.mirror.sdk.ui.market.apis.listeners.GetNFTsListener;
import com.mirror.sdk.ui.market.apis.responses.CollectionInfo;
import com.mirror.sdk.ui.market.apis.responses.GetCollectionsResponse;
import com.mirror.sdk.ui.market.model.NFTDetailData;
import com.mirror.sdk.ui.market.widgets.FilterDetailDropListAdapter;
import com.mirror.sdk.ui.market.widgets.MarketMainCollectionTabsAdapter;
import com.mirror.sdk.ui.market.widgets.MarketMainFilterDetailRecyclerViewAdapter;
import com.mirror.sdk.ui.market.widgets.MarketMainRecyclerAdapter;
import com.mirror.sdk.ui.market.widgets.MirrorExpandedButton;
import com.mirror.sdk.ui.market.widgets.OnExpandedButtonClick;

import java.util.List;

public class MirrorMarketDialog extends DialogFragment {

    Activity mActivity = null;
    boolean mInited = false;

    //widgets
    ConstraintLayout mContentView;
    ConstraintSet mTotalConstraintSet;
    RecyclerView mNFTRecyclerView;
    ConstraintLayout mDropListParent;
    ConstraintLayout mStaticButtonParent;
    MirrorExpandedButton mOrderButton;
    MirrorExpandedButton mFilterButton;
    ConstraintLayout mFilterDetailParent;
    RecyclerView mFilterDetailRecyclerView;
    //dynamic
    ConstraintLayout mDropListContent;
//    ConstraintLayout mDynamicFilterDetail;

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
        ConstraintLayout totalView = (ConstraintLayout) mActivity.getLayoutInflater().inflate(R.layout.market_main, null);
        ConstraintLayout contentView = totalView.findViewById(R.id.market_main_content_parent);
        mTotalConstraintSet = new ConstraintSet();
        mContentView = contentView;

        mNFTRecyclerView = totalView.findViewById(R.id.market_main_nft_list);
        mDropListParent = totalView.findViewById(R.id.market_main_filter_expand_parent);
        mStaticButtonParent = totalView.findViewById(R.id.market_main_filter_buttons_parent);
        mOrderButton = totalView.findViewById(R.id.market_main_filter_order_button);
        mFilterButton = totalView.findViewById(R.id.market_main_filter_button);
        mFilterDetailParent = totalView.findViewById(R.id.market_main_filter_detail_parent);

        mFilterDetailRecyclerView = mFilterDetailParent.findViewById(R.id.market_main_filter_detail_recyclerview);

//        mFilterDetailParent.setVisibility(View.GONE);

        RecyclerView collectionTabView = totalView.findViewById(R.id.market_main_type_parent);
        MarketUIController.getInstance().setMainParent(collectionTabView);
        collectionTabView.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));

        setFilter();
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

    private void setFilter(){
        mFilterButton.setExpandListener(new OnExpandedButtonClick() {
            @Override
            public void OnExpand() {
                mFilterDetailParent.setVisibility(View.VISIBLE);
                mTotalConstraintSet.clone(mContentView);
                mTotalConstraintSet.connect(mNFTRecyclerView.getId(),ConstraintSet.TOP,mFilterDetailParent.getId(),ConstraintSet.BOTTOM);
                mTotalConstraintSet.applyTo(mContentView);
            }

            @Override
            public void OnFold() {
                mFilterDetailParent.setVisibility(View.GONE);
            }
        });
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
                    setFilterBar(collection);
                    startRequestNFT();
                    startRequestFilters(collection);
                }else {
                    View collectionParent = view.findViewById(R.id.market_main_type_parent);
                    collectionParent.setVisibility(View.VISIBLE);

                    CollectionInfo collection = response.collections.get(0);
                    MarketUIController.getInstance().selectCollection(collection);
                    setFilterBar(collection);
                    startRequestNFT();
                    startRequestFilters(collection);
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

    private void startRequestFilters(CollectionInfo collectionInfo){
        MirrorMarketUIAPI.GetFilters(collectionInfo, new GetFilterListener() {
            @Override
            public void onSuccess(List<CollectionFilter> filters) {
                //add tabs
                MarketMainFilterDetailRecyclerViewAdapter adapter = new MarketMainFilterDetailRecyclerViewAdapter(filters);
                adapter.setOnExpandedListener(new MarketMainFilterDetailRecyclerViewAdapter.OnFilterTabClicked() {
                    @Override
                    public void OnExpand(MarketMainFilterDetailRecyclerViewAdapter.ViewHolder tabLayout, CollectionFilter filter) {
                        //Show and add items
                        MarketUIController.getInstance().setCurTab(tabLayout);
                        mDropListParent.setVisibility(View.VISIBLE);
                        setExpandViewTop(mFilterDetailParent);
                        addFilterExpandView(filter);
                    }

                    @Override
                    public void OnFold(MarketMainFilterDetailRecyclerViewAdapter.ViewHolder tabLayout) {
                        mDropListParent.setVisibility(View.GONE);
                        removeOrderExpandView();
                    }
                });
                mFilterDetailRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
                mFilterDetailRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailed(long code, String message) {
                Log.e("MirrorMarket","request filters failed.");
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

    private void setFilterBar(CollectionInfo collectionInfo){
        mOrderButton.setText(collectionInfo.collection_orders.get(0).order_desc);
        mOrderButton.setExpandListener(new OnExpandedButtonClick() {
            @Override
            public void OnExpand() {
                mDropListParent.setVisibility(View.VISIBLE);
                addOrderExpandView(collectionInfo.collection_orders);
            }

            @Override
            public void OnFold() {
                mDropListParent.setVisibility(View.GONE);
                removeOrderExpandView();
            }
        });
    }

    private void addOrderExpandView(List<CollectionOrder> orders){
        setExpandViewTop(mStaticButtonParent);

        mDropListContent = (ConstraintLayout) LayoutInflater.from(mDropListParent.getContext()).inflate(R.layout.drop_list_simple, mDropListParent);
        RecyclerView recyclerView = mDropListContent.findViewById(R.id.drop_list_simple_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        DropListSimpleAdapter adapter = new DropListSimpleAdapter(orders,MarketDataController.getInstance().getOrder());
        adapter.setOrderSelectListener(new DropListSimpleAdapter.OnOrderItemClickListener() {
            @Override
            public void onClicked(CollectionOrder data) {
                MarketDataController.getInstance().setOrder(data);
                mOrderButton.setText(data.order_desc);
                mOrderButton.foldView();
                mDropListParent.setVisibility(View.GONE);
                removeOrderExpandView();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void addFilterExpandView(CollectionFilter filter){
        ConstraintLayout mDynamicFilterDetail = (ConstraintLayout) LayoutInflater.from(mDropListParent.getContext()).inflate(R.layout.drop_list_simple, mDropListParent);

        RecyclerView recyclerView = mDynamicFilterDetail.findViewById(R.id.drop_list_simple_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        FilterDetailDropListAdapter adapter = new FilterDetailDropListAdapter(filter);
        //After 'red' clicked
        adapter.setOrderSelectListener(new FilterDetailDropListAdapter.OnOrderItemClickListener() {
            @Override
            public void onClicked(String data) {
                removeOrderExpandView();
                mDropListParent.setVisibility(View.GONE);

                MarketMainFilterDetailRecyclerViewAdapter.ViewHolder tabView = MarketUIController.getInstance().getCurTab();
                tabView.mIsOpen = false;
                tabView.mImageFilterView.setRotation(0);
                tabView.mTextView.setText(data);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void removeOrderExpandView(){
        mDropListParent.removeAllViews();
    }

    private void setExpandViewTop(View targetView){
        mTotalConstraintSet.clone(mContentView);
        mTotalConstraintSet.connect(mDropListParent.getId(),ConstraintSet.TOP,targetView.getId(),ConstraintSet.BOTTOM);
        mTotalConstraintSet.applyTo(mContentView);
    }

    private void foldAllExpand(){
        removeOrderExpandView();
    }

    private void logMarket(String content){
        Log.i("market:",content);
    }
}
