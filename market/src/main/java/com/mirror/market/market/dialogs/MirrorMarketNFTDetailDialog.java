//package com.mirror.market.market.dialogs;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.Button;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.widget.LinearLayoutCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mirror.sdk.R;
//import com.mirror.sdk.ui.market.enums.MirrorMarketConfig;
//import com.mirror.sdk.ui.market.model.NFTDetailData;
//import com.mirror.market.market.widgets.multiple.adapter.MainAdapter;
//import com.mirror.market.market.widgets.multiple.adapter.TabsAdapter;
//import com.mirror.market.market.widgets.multiple.type.ActivitiesType;
//import com.mirror.market.market.widgets.multiple.type.AttributeType;
//import com.mirror.market.market.widgets.multiple.type.BaseType;
//import com.mirror.market.market.widgets.multiple.type.Details;
//import com.mirror.market.market.widgets.multiple.type.GamePerformance;
//import com.mirror.market.market.widgets.multiple.type.HeaderType;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MirrorMarketNFTDetailDialog extends DialogFragment {
//    Activity mActivity = null;
//    boolean mInited = false;
//    NFTDetailData mNFTDetailData;
//    OnBuyListener mOnBuyListener;
//
//
//    private RecyclerView mutiple;
//
//    private int currentHeight = 60;
//
//    private RecyclerView tabs;
//
//    private LinearLayoutCompat customTabs;
//
//    private Button buyNft;
//
//
//
//    public void Init(Activity activity, NFTDetailData nftDetailData){
//        if(mInited){
//            return;
//        }
//        mInited = true;
//
//        mActivity = activity;
//        mNFTDetailData = nftDetailData;
//    }
//
//    public void setOnBuyListener(OnBuyListener listener){
//        mOnBuyListener = listener;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            Window window = dialog.getWindow();
//            if (window != null) {
//                int width = ViewGroup.LayoutParams.MATCH_PARENT;
//                int height = ViewGroup.LayoutParams.MATCH_PARENT;
//                window.setLayout(width, height);
//                if(MirrorMarketConfig.FULL_SCREEN_MODE) window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            }
//        }
//    }
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        if(!mInited){
//            Log.e("MirrorMarket","Not inited!");
//            return null;
//        }
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
//        View view = mActivity.getLayoutInflater().inflate(com.mirror.sdk.R.layout.mutiple_recyclerview, null);
//
//        mutiple = view.findViewById(com.mirror.sdk.R.id.mutiple_recyclerview);
//        customTabs = view.findViewById(com.mirror.sdk.R.id.custom_tabs_root);
//        tabs = view.findViewById(com.mirror.sdk.R.id.mutiple_position_tabs);
//        buyNft = view.findViewById(R.id.multiple_buy_nft);
//
//        buyNft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mOnBuyListener.onBuy(mNFTDetailData);
//            }
//        });
//
//
//        InitView();
//
////        ImageButton imageButton = view.findViewById(R.id.nftdetail_btn_buy);
////        imageButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if(mOnBuyListener != null) mOnBuyListener.onBuy(mNFTDetailData);
////            }
////        });
//
//        builder.setView(view);
//
//        return builder.create();
//    }
//
//
//    private void InitView()
//    {
//
//
//        mutiple.setLayoutManager(new LinearLayoutManager(mActivity));
//        mutiple.setAdapter(new MainAdapter(mockData()));
//        tabs.setLayoutManager(new LinearLayoutManager(mActivity));
//
//        mutiple.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                currentHeight+=dy;
//                AlphaMapping();
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
//
//
//
//
//    }
//
//    private List<BaseType> mockData(){
//
//        List<BaseType> datas = new ArrayList<>();
//
//        HeaderType headerType = new HeaderType();
//        GamePerformance gamePerformance = new GamePerformance();
//        AttributeType attributeType = new AttributeType();
//        Details details = new Details();
//        ActivitiesType activitiesType = new ActivitiesType();
//
//        datas.add(headerType);
//        datas.add(gamePerformance);
//        datas.add(attributeType);
//        datas.add(details);
//        datas.add(activitiesType);
//        return datas;
//    }
//
//    private List<String> getTabs(){
//        List<String> tabs = new ArrayList<>();
//
//        tabs.add("In-Game-Performance");
//        tabs.add("On-Chain-Attribute");
//        tabs.add("Details");
//        tabs.add("Activities");
//        return tabs;
//    }
//
//    private void AlphaMapping(){
//
//        if(currentHeight>0 && currentHeight <15){
//            customTabs.getBackground().setAlpha(0);
//
//            return;
//        }
//
//        if(currentHeight <= 400){
//            float rate = currentHeight*(1/400f);
//            int alpha = Math.round((rate*255));
//            customTabs.getBackground().setAlpha(alpha);
//        }
//
//
//        if(currentHeight> 400){
//            customTabs.getBackground().setAlpha(255);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
//            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            tabs.setLayoutManager(linearLayoutManager);
//            tabs.setAdapter(new TabsAdapter(getTabs(),mutiple));
//        }else{
//            List<String> empty = new ArrayList<>();
//            tabs.setAdapter(new TabsAdapter(empty,mutiple));
//        }
//
//
//
//
//    }
//}
