package com.mirror.sdk.ui.market;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.R;
import com.mirror.sdk.listener.market.BuyNFTListener;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.ui.market.model.NFTDetailData;
import com.mirror.sdk.ui.market.widgets.MarketMainRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MirrorMarketDialog extends DialogFragment {

    Activity mActivity = null;
    boolean mInited = false;

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
        List<NFTDetailData> iconbeans = getdata();

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.market_main, null);

        RecyclerView recyclerView = view.findViewById(R.id.market_main_nft_list);

        //设置 布局管理器 这里是实现GridView的效果所以 我们加载GrdiLayoutMannager
        GridLayoutManager gridLayoutManager = new
                //第一个参数是 这个Activity的上下文Context 然后第二个参数是 一行显示的个数 2就是一行2个
                GridLayoutManager(mActivity,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        //recyclerView的适配器
        MarketMainRecyclerAdapter adapter = new MarketMainRecyclerAdapter(iconbeans);
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
        recyclerView.setAdapter(adapter);

        builder.setView(view);

        return builder.create();
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

    private void logMarket(String content){
        Log.i("market:",content);
    }
}
