package com.mirror.sdk.ui.market.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.R;
import com.mirror.sdk.constant.MirrorConstant;
import com.mirror.sdk.ui.market.MarketDataController;
import com.mirror.sdk.ui.market.MarketUIController;
import com.mirror.sdk.ui.market.apis.listeners.GetFilterListener;
import com.mirror.sdk.ui.market.apis.requests.GetNFTsRequestOrder;
import com.mirror.sdk.ui.market.apis.responses.CollectionFilter;
import com.mirror.sdk.ui.market.apis.responses.CollectionOrder;
import com.mirror.sdk.ui.market.droplist.DropListSimpleAdapter;
import com.mirror.sdk.ui.market.enums.MarketFilterTypes;
import com.mirror.sdk.ui.market.enums.MirrorMarketConfig;
import com.mirror.sdk.ui.market.apis.MirrorMarketUIAPI;
import com.mirror.sdk.ui.market.apis.listeners.GetCollectionListener;
import com.mirror.sdk.ui.market.apis.listeners.GetNFTsListener;
import com.mirror.sdk.ui.market.apis.responses.CollectionInfo;
import com.mirror.sdk.ui.market.apis.responses.GetCollectionsResponse;
import com.mirror.sdk.ui.market.model.NFTDetailData;
import com.mirror.sdk.ui.market.utils.MarketUtils;
import com.mirror.sdk.ui.market.widgets.FilterDetailDropListAdapter;
import com.mirror.sdk.ui.market.widgets.MainRecyclerView;
import com.mirror.sdk.ui.market.widgets.MarketMainCollectionTabsAdapter;
import com.mirror.sdk.ui.market.widgets.MarketMainFilterDetailRecyclerViewAdapter;
import com.mirror.sdk.ui.market.widgets.MarketMainRecyclerAdapter;
import com.mirror.sdk.ui.market.widgets.MarketMainScrollView;
import com.mirror.sdk.ui.market.widgets.MirrorExpandedButton;
import com.mirror.sdk.ui.market.widgets.OnExpandedButtonClick;

import java.util.List;

public class MirrorMarketDialog extends DialogFragment {

    Activity mActivity = null;
    boolean mInited = false;
    private List<String> mCollectionAddresses;

    //widgets
    ConstraintLayout mContentView;
    MarketMainScrollView mTotalScrollView;
    ConstraintSet mTotalConstraintSet;
    NestedScrollView mBottomParent;
    MainRecyclerView mNFTRecyclerView;
    ConstraintLayout mDropListParent;
    ConstraintLayout mStaticButtonParent;
    MirrorExpandedButton mOrderButton;
    MirrorExpandedButton mFilterButton;
    ConstraintLayout mLine3Parent;
    ConstraintLayout mLoadingView;
    CardView mSearchExpand;
    SearchView mSearchView;
    RecyclerView mFilterDetailRecyclerView;
    ConstraintLayout mDropListContent;

    private boolean showLoading;
    private boolean showLine3;

    public void Init(Activity activity,List<String> collectionAddresses){
        if(mInited){
            return;
        }
        mInited = true;

        mCollectionAddresses = collectionAddresses;
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

        mTotalScrollView = totalView.findViewById(R.id.market_main_content_scrollview);
        contentView.getParent().requestDisallowInterceptTouchEvent(true);

        mBottomParent = totalView.findViewById(R.id.main_bottom_parent);
        mNFTRecyclerView = totalView.findViewById(R.id.main_bottom_recyclerview);
        mDropListParent = totalView.findViewById(R.id.market_main_filter_expand_parent);
        mStaticButtonParent = totalView.findViewById(R.id.main_content_line2);
        mOrderButton = totalView.findViewById(R.id.market_main_filter_order_button);
        mFilterButton = totalView.findViewById(R.id.market_main_filter_button);
        mLine3Parent = totalView.findViewById(R.id.main_line3);
        mSearchExpand = totalView.findViewById(R.id.market_main_searchview_expand);
        mSearchView = totalView.findViewById(R.id.market_main_searchview);
        mLoadingView = totalView.findViewById(R.id.market_main_bottom_loading);

        mFilterDetailRecyclerView = mLine3Parent.findViewById(R.id.main_line3_recyclerview);

        MarketUIController.getInstance().FilterDetailLayout = mDropListParent;

        RecyclerView collectionTabView = totalView.findViewById(R.id.main_content_line1rv);
        MarketUIController.getInstance().setMainParent(collectionTabView);
        collectionTabView.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));

        initNFTRecyclerView();
        initSearchView();
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
                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                if(MirrorMarketConfig.FULL_SCREEN_MODE) window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }

    private void initNFTRecyclerView(){
        updateBottomHeight();
        mNFTRecyclerView.init(mActivity, new MainRecyclerView.OnBottomListener() {
            @Override
            public void OnBottom() {
                openLoading();
            }
        });
        MarketMainRecyclerAdapter adapter = new MarketMainRecyclerAdapter(MarketDataController.getInstance().getNFTs());
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

    private void initSearchView(){

        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchExpand.setVisibility(View.VISIBLE);
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mSearchExpand.setVisibility(View.GONE);
                return false;
            }
        });
    }

    private void setFilter(){
        mFilterButton.setText("Filter");
        mFilterButton.setExpandListener(new OnExpandedButtonClick() {
            @Override
            public void OnExpand() {
                openLine3();

                mTotalConstraintSet.clone(mContentView);
                mTotalConstraintSet.connect(mBottomParent.getId(),ConstraintSet.TOP, mLine3Parent.getId(),ConstraintSet.BOTTOM);
                mTotalConstraintSet.applyTo(mContentView);
            }

            @Override
            public void OnFold() {
                closeLine3();
            }
        });
    }

    private void startRequestCollections(RecyclerView view){
        MirrorSDK.getInstance().getMarketCollections(mCollectionAddresses,new GetCollectionListener() {
            @Override
            public void onSuccess(List<CollectionInfo> response) {
                if(response.size() == 0){
                    View collectionParent = view.findViewById(R.id.main_content_line1rv);
                    collectionParent.setVisibility(View.GONE);
                }else if(response.size() == 1){
                    View collectionParent = view.findViewById(R.id.main_content_line1rv);
                    collectionParent.setVisibility(View.GONE);

                    CollectionInfo collection = response.get(0);
                    MarketUIController.getInstance().selectCollection(collection);
                    setFilterBar(collection);
                    startRequestNFT();
                    startRequestFilters(collection);
                }else {
                    View collectionParent = view.findViewById(R.id.main_content_line1rv);
                    collectionParent.setVisibility(View.VISIBLE);

                    CollectionInfo collection = response.get(0);
                    MarketUIController.getInstance().selectCollection(collection);
                    setFilterBar(collection);
                    startRequestNFT();
                    startRequestFilters(collection);
                }
                MarketMainCollectionTabsAdapter adapter = new MarketMainCollectionTabsAdapter(response);
                view.setAdapter(adapter);
            }

            @Override
            public void onFail(long code, String message) {
                Log.e("MirrorMarket:","code:"+code+" message:"+message);
            }
        });
    }

    private void startRequestFilters(CollectionInfo collectionInfo){
        MirrorSDK.getInstance().getMarketFilters(collectionInfo.collection, new GetFilterListener() {
            @Override
            public void onSuccess(List<CollectionFilter> filters) {
                //add tabs
                MarketMainFilterDetailRecyclerViewAdapter adapter = new MarketMainFilterDetailRecyclerViewAdapter(filters);
                adapter.setOnExpandedListener(new MarketMainFilterDetailRecyclerViewAdapter.OnFilterTabClicked() {
                    @Override
                    public void OnExpand(MarketMainFilterDetailRecyclerViewAdapter.ViewHolder tabLayout, CollectionFilter filter) {
                        //Show and add items
                        MarketUIController.getInstance().setCurTab(tabLayout.TabExpandButton);
                        setExpandViewTop(mLine3Parent);
                        addFilterExpandView(filter);
                        MarketUIController.getInstance().expandFilterDetail(tabLayout.TabExpandButton);
                    }

                    @Override
                    public void OnFold(MarketMainFilterDetailRecyclerViewAdapter.ViewHolder tabLayout) {
                        removeOrderExpandView();
                        MarketUIController.getInstance().foldFilterDetail(false);
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
        MarketDataController.getInstance().NFTRequestInfo.page = MarketDataController.getInstance().NFTNowPage;
        MirrorSDK.getInstance().getMarketNFTs(MarketDataController.getInstance().NFTRequestInfo,new GetNFTsListener() {
            @Override
            public void onSuccess(List<NFTDetailData> nfts) {
                MarketDataController.getInstance().addNFTs(nfts);
                MarketDataController.getInstance().NFTNowPage++;

                MarketMainRecyclerAdapter adapter = (MarketMainRecyclerAdapter) mNFTRecyclerView.getAdapter();
                for(int i=0;i<nfts.size();i++){
                    adapter.addData(nfts.get(i));
                }
                closeLoading();
            }

            @Override
            public void onFailed(long code,String message) {
                closeLoading();
            }
        });
    }

    private void setFilterBar(CollectionInfo collectionInfo){
        mOrderButton.setText(collectionInfo.collection_orders.get(0).order_desc);
        mOrderButton.setExpandListener(new OnExpandedButtonClick() {
            @Override
            public void OnExpand() {
                MarketUIController.getInstance().expandFilterDetail(mOrderButton);
                addOrderExpandView(collectionInfo.collection_orders);
            }

            @Override
            public void OnFold() {
                MarketUIController.getInstance().foldFilterDetail(false);
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
                mOrderButton.fold();
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

                MarketUIController.getInstance().getCurTab().setText(data);
                MarketUIController.getInstance().getCurTab().fold();
                if(filter.filter_type == MarketFilterTypes.ENUM){
                    MarketDataController.getInstance().addRequestEnumFilter(data);
                }else {
                    Log.e("MirrorMarket","not support");
                }

            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void openLine3(){
        showLine3 = true;
        mLine3Parent.setVisibility(View.VISIBLE);
        updateBottomHeight();
    }

    private void closeLine3(){
        showLine3 = false;
        mLine3Parent.setVisibility(View.GONE);
        updateBottomHeight();
    }


    private void openLoading(){
        showLoading = true;
        mLoadingView.setVisibility(View.VISIBLE);
        updateBottomHeight();
        startRequestNFT();
    }

    private void closeLoading(){
        showLoading = false;
        mLoadingView.setVisibility(View.GONE);
        mNFTRecyclerView.scrollBy(0,MarketUtils.dpToPx(mActivity,50));
        updateBottomHeight();
    }

    private void updateBottomHeight(){
        ViewGroup.LayoutParams params = mBottomParent.getLayoutParams();
        Display display = mActivity.getWindowManager().getDefaultDisplay();

        int minues = 42;//searchview
        if(showLine3){
            minues += 40;//line3
        }

        int newHeight = (int) (display.getHeight() - MarketUtils.dp2px(minues));
        Log.i("Screen change to :", String.valueOf(newHeight)+" totao height is:"+display.getHeight());
        params.height = newHeight;
    }

    private void updateNFTRecyclerViewHeight(){
        ViewGroup.LayoutParams params = mNFTRecyclerView.getLayoutParams();
        Display display = mActivity.getWindowManager().getDefaultDisplay();

        int minues = 42;//searchview
        if(showLine3){
            minues += 42;//line3
        }
        if(showLoading){
            minues += 50;//loading
        }

        int newHeight = display.getHeight() - MarketUtils.dpToPx(mActivity,minues);
        params.height = newHeight;
    }

    private void removeOrderExpandView(){
        mDropListParent.removeAllViews();
    }

    private void setExpandViewTop(View targetView){
        mTotalConstraintSet.clone(mContentView);
        mTotalConstraintSet.connect(mDropListParent.getId(),ConstraintSet.TOP,targetView.getId(),ConstraintSet.BOTTOM);
        mTotalConstraintSet.applyTo(mContentView);
    }

    private void logMarket(String content){
        Log.i("market:",content);
    }
}
