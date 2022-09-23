//package com.mirror.sdk.ui.market.dialogs;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mirror.sdk.R;
//import com.mirror.sdk.ui.market.MarketDataController;
//import com.mirror.sdk.ui.market.MarketUIController;
//import com.mirror.sdk.ui.market.apis.MirrorMarketUIAPI;
//import com.mirror.sdk.ui.market.apis.listeners.GetCollectionListener;
//import com.mirror.sdk.ui.market.apis.listeners.GetFilterListener;
//import com.mirror.sdk.ui.market.apis.listeners.GetNFTsListener;
//import com.mirror.sdk.ui.market.apis.responses.CollectionFilter;
//import com.mirror.sdk.ui.market.apis.responses.CollectionInfo;
//import com.mirror.sdk.ui.market.apis.responses.CollectionOrder;
//import com.mirror.sdk.ui.market.apis.responses.GetCollectionsResponse;
//import com.mirror.sdk.ui.market.droplist.DropListSimpleAdapter;
//import com.mirror.sdk.ui.market.enums.MirrorMarketConfig;
//import com.mirror.sdk.ui.market.model.NFTDetailData;
//import com.mirror.sdk.ui.market.utils.MarketUtils;
//import com.mirror.sdk.ui.market.widgets.MarketMainCollectionTabsAdapter;
//import com.mirror.sdk.ui.market.widgets.MarketMainFilterDetailRecyclerViewAdapter;
//import com.mirror.sdk.ui.market.widgets.MarketMainRecyclerAdapter;
//import com.mirror.sdk.ui.market.widgets.MirrorExpandedButton;
//import com.mirror.sdk.ui.market.widgets.OnExpandedButtonClick;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MarketDialog extends DialogFragment {
//    ConstraintLayout mStaticBarParent;
//    ConstraintLayout mPopupFilterParent;
//    //static header
//    RecyclerView mStaticHeaderCollectionParent;
//    RecyclerView mStaticHeaderFilterRecyclerView;
//    ConstraintLayout mStaticHeaderFilterParent;
//    ConstraintLayout mStaticHeaderLine2;
//    MirrorExpandedButton mOrderButton;
//    MirrorExpandedButton mFilterButton;
//    //nft list
//    RecyclerView mMainNFTParent;
//
//    private Activity mActivity;
//
//    public void init(Activity context){
//        mActivity = context;
//    }
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        CoordinatorLayout totalView = (CoordinatorLayout) mActivity.getLayoutInflater().inflate(R.layout.market_main2, null);
////        mRecyclerView = totalView.findViewById(R.id.main_nft_recyclerview);
//        mStaticBarParent = totalView.findViewById(R.id.main_static_header);
//        mPopupFilterParent = totalView.findViewById(R.id.main_filter_option_parent);
//        mStaticHeaderCollectionParent = mStaticBarParent.findViewById(R.id.main_static_header_line1rv);
//        mStaticHeaderFilterRecyclerView = mStaticBarParent.findViewById(R.id.market_main_filter_detail_recyclerview);
//        mStaticHeaderFilterParent = mStaticBarParent.findViewById(R.id.main_static_header_line3);
//        mStaticHeaderLine2 = mStaticBarParent.findViewById(R.id.main_static_header_line2);
//        mOrderButton = mStaticBarParent.findViewById(R.id.main_static_header_order);
//        mFilterButton = mStaticBarParent.findViewById(R.id.main_static_header_filterswitch);
//        mMainNFTParent = totalView.findViewById(R.id.main_nft_recyclerview);
//
//        startRequestInfo();
//        initView();
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
//        builder.setView(totalView);
//        Dialog dialog = builder.create();
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//
//        return dialog;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            Window window = dialog.getWindow();
//            if (window != null) {
//                WindowManager.LayoutParams paramsWindow = window.getAttributes();
//                paramsWindow.width = window.getWindowManager().getDefaultDisplay().getWidth();
//                int screenHeight = window.getWindowManager().getDefaultDisplay().getHeight();
//                Log.i("0000001", String.valueOf(screenHeight));
//                paramsWindow.height = screenHeight;//getWindowDefineHeight();
//                paramsWindow.gravity = Gravity.BOTTOM;
//                paramsWindow.windowAnimations = R.style.common_dialog;
//                window.setAttributes(paramsWindow);
//                if(MirrorMarketConfig.FULL_SCREEN_MODE) window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            }
//        }
//    }
//
//    private int getWindowDefineHeight() {
//        int height = 42+40;
//        int nftLine = 10/2;
//        int lineHeight = 200+10+10;
//        height += nftLine * lineHeight;
//        //todo:calculate
//        int finalHeight = MarketUtils.dpToPx(mActivity,height);
//        Log.i("000000", String.valueOf(finalHeight));
//        return 2000;
//    }
//
//    private void startRequestInfo(){
//        MirrorMarketUIAPI.GetCollections(new GetCollectionListener() {
//            @Override
//            public void onSuccess(GetCollectionsResponse response) {
//                if(response.collections.size() == 1){
//                    mStaticHeaderCollectionParent.setVisibility(View.GONE);
//
//                    CollectionInfo collection = response.collections.get(0);
//                    MarketUIController.getInstance().selectCollection(collection);
//                }else {
//                    mStaticHeaderCollectionParent.setVisibility(View.VISIBLE);
//
//                    CollectionInfo collection = response.collections.get(0);
//                    MarketUIController.getInstance().selectCollection(collection);
//
//                    setCollectionsBar(response.collections);
//                }
//
//                CollectionInfo curCollection = MarketUIController.getInstance().getCurCollection();
//                setLine2(curCollection);
//                startRequestNFT();
//                startRequestFilters(curCollection);
//            }
//
//            @Override
//            public void onFail(long code, String message) {
//                Log.e("MirrorMarket:","code:"+code+" message:"+message);
//            }
//        });
//    }
//
//    private void setCollectionsBar(List<CollectionInfo> collections){
//        MarketMainCollectionTabsAdapter adapter = new MarketMainCollectionTabsAdapter(collections);
//        mStaticHeaderCollectionParent.setAdapter(adapter);
//    }
//
//    private void setLine2(CollectionInfo collectionInfo){
//        mOrderButton.setText(collectionInfo.collection_orders.get(0).order_desc);
//        mOrderButton.setExpandListener(new OnExpandedButtonClick() {
//            @Override
//            public void OnExpand() {
//                mPopupFilterParent.setVisibility(View.VISIBLE);
//                addOrderExpandView(collectionInfo.collection_orders);
//            }
//
//            @Override
//            public void OnFold() {
////                mDropListParent.setVisibility(View.GONE);
////                removeOrderExpandView();
//            }
//        });
//        mFilterButton.setText("Filter");
//        mFilterButton.setExpandListener(new OnExpandedButtonClick() {
//            @Override
//            public void OnExpand() {
//                mStaticHeaderFilterParent.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void OnFold() {
//                mStaticHeaderFilterParent.setVisibility(View.GONE);
//            }
//        });
//    }
//
//    private void addOrderExpandView(List<CollectionOrder> orders){
//        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mPopupFilterParent.getLayoutParams();
//
//        WindowManager.LayoutParams paramsWindow = getDialog().getWindow().getAttributes();
//        paramsWindow.width = getDialog().getWindow().getWindowManager().getDefaultDisplay().getWidth();
//        int screenHeight = getDialog().getWindow().getWindowManager().getDefaultDisplay().getHeight();
//        int location[] = new int[2];
//        mOrderButton.getLocationInWindow(location);
//        Log.i("height:", String.valueOf(screenHeight));
//        Log.i("Y:", String.valueOf(location[1]));
//        Log.i("20dp:", String.valueOf(MarketUtils.getStatusBarHeightBySys(mActivity)));
//        Log.i("lin2 height:", String.valueOf(mStaticHeaderLine2.getHeight()));
//        lp.height = (int) (screenHeight - location[1] - mStaticHeaderLine2.getHeight());
//        Log.i("final:", String.valueOf(lp.height));
//        mPopupFilterParent.setLayoutParams(lp);
//
//        ConstraintLayout view = (ConstraintLayout) LayoutInflater.from(mPopupFilterParent.getContext()).inflate(R.layout.drop_list_simple, mPopupFilterParent);
//        RecyclerView recyclerView = view.findViewById(R.id.drop_list_simple_recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        DropListSimpleAdapter adapter = new DropListSimpleAdapter(orders,MarketDataController.getInstance().getOrder());
//        adapter.setOrderSelectListener(new DropListSimpleAdapter.OnOrderItemClickListener() {
//            @Override
//            public void onClicked(CollectionOrder data) {
//                MarketDataController.getInstance().setOrder(data);
//                mOrderButton.setText(data.order_desc);
//                mOrderButton.fold();
//                view.setVisibility(View.GONE);
//                mPopupFilterParent.removeAllViews();
//            }
//        });
//        recyclerView.setAdapter(adapter);
//    }
//
//    private void setLine3(List<CollectionFilter> filters){
//        MarketMainFilterDetailRecyclerViewAdapter adapter = new MarketMainFilterDetailRecyclerViewAdapter(filters);
//        adapter.setOnExpandedListener(new MarketMainFilterDetailRecyclerViewAdapter.OnFilterTabClicked() {
//            @Override
//            public void OnExpand(MarketMainFilterDetailRecyclerViewAdapter.ViewHolder tabLayout, CollectionFilter filter) {
//
//            }
//
//            @Override
//            public void OnFold(MarketMainFilterDetailRecyclerViewAdapter.ViewHolder tabLayout) {
//
//            }
//        });
//        mStaticHeaderFilterRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
//        mStaticHeaderFilterRecyclerView.setAdapter(adapter);
//    }
//
//    private void startRequestNFT(){
//        MirrorMarketUIAPI.GetNFTs(new GetNFTsListener() {
//            @Override
//            public void onSuccess(List<NFTDetailData> nfts) {
//                setMainNFTRecyclerView(nfts);
//            }
//
//            @Override
//            public void onFailed() {
//
//            }
//        });
//    }
//
//    private void setMainNFTRecyclerView(List<NFTDetailData> nfts){
//        MarketMainRecyclerAdapter adapter = new MarketMainRecyclerAdapter(nfts);
//        adapter.setCardViewOnClickListener(new MarketMainRecyclerAdapter.OnNFTItemClickListener() {
//            @Override
//            public void onClicked(View view, NFTDetailData data) {
//                MirrorMarketNFTDetailDialog detailDialog = new MirrorMarketNFTDetailDialog();
//                detailDialog.Init(mActivity,data);
//                detailDialog.show(mActivity.getFragmentManager(), "Add group dialog");
//                detailDialog.setOnBuyListener(new OnBuyListener() {
//                    @Override
//                    public void onBuy(NFTDetailData nftDetailData) {
//                        //todo: show thumb page and conform
//                        MirrorMarketConfirmDialog confirmDialog = new MirrorMarketConfirmDialog(mActivity);
//                        confirmDialog.init(nftDetailData);
//                        confirmDialog.show();
//                    }
//                });
//            }
//        });
//
//        //设置 布局管理器 这里是实现GridView的效果所以 我们加载GrdiLayoutMannager
//        GridLayoutManager gridLayoutManager = new
//                //第一个参数是 这个Activity的上下文Context 然后第二个参数是 一行显示的个数 2就是一行2个
//                GridLayoutManager(mActivity,2);
//
//        mMainNFTParent.setLayoutManager(gridLayoutManager);
//        mMainNFTParent.setAdapter(adapter);
//    }
//
//    private List<String> list;
//    private void initList() {
//        list = new ArrayList<>();
//        for (int i = 1; i <= 100; i++) {
//            list.add(i + "");
//        }
//    }
//
//    private void initNFTParent() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
//        NormalAdapter normalAdapter = new NormalAdapter(list);
//
//        mMainNFTParent.setLayoutManager(linearLayoutManager);
//        mMainNFTParent.setNestedScrollingEnabled(false);
//        mMainNFTParent.setAdapter(normalAdapter);
//    }
//
//    private void startRequestFilters(CollectionInfo collection){
//        MirrorMarketUIAPI.GetFilters(collection, new GetFilterListener() {
//            @Override
//            public void onSuccess(List<CollectionFilter> filters) {
//                setLine3(filters);
//            }
//
//            @Override
//            public void onFailed(long code, String message) {
//                Log.e("MirrorMarket","request filters failed.");
//            }
//        });
//    }
//
//    private void initView() {
//        mStaticHeaderFilterParent.setVisibility(View.GONE);
////        normalAdapter = new NormalAdapter(list);
////
////        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mMainNFTParent.setNestedScrollingEnabled(false);
////        mRecyclerView.setAdapter(normalAdapter);
//    }
//}
