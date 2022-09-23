//package com.mirror.sdk.ui.market.adapters;
//
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mirror.sdk.R;
//import com.mirror.sdk.ui.market.MarketDataController;
//import com.mirror.sdk.ui.market.MarketUIController;
//import com.mirror.sdk.ui.market.apis.MirrorMarketUIAPI;
//import com.mirror.sdk.ui.market.apis.listeners.GetNFTsListener;
//import com.mirror.sdk.ui.market.dialogs.MirrorMarketConfirmDialog;
//import com.mirror.sdk.ui.market.dialogs.MirrorMarketNFTDetailDialog;
//import com.mirror.sdk.ui.market.dialogs.OnBuyListener;
//import com.mirror.sdk.ui.market.model.NFTDetailData;
//import com.mirror.sdk.ui.market.widgets.MarketMainRecyclerAdapter;
//
//import java.util.List;
//
//public class MainDialogItemContent extends MainDialogItemBase {
//
//    RecyclerView mNFTRecyclerView;
//
//    public MainDialogItemContent(@NonNull View itemView) {
//        super(itemView);
//        initView(itemView);
//    }
//
//    private void initView(View totalView){
//        mNFTRecyclerView = totalView.findViewById(R.id.main_nft_recyclerview);
//        MarketUIController.getInstance().mMainInnerRecycler = mNFTRecyclerView;
//
//        int nowPage = MarketDataController.getInstance().NFTNowPage;
//        MirrorMarketUIAPI.GetNFTs(nowPage, new GetNFTsListener() {
//            @Override
//            public void onSuccess(List<NFTDetailData> nfts) {
//                MarketDataController.getInstance().NFTNowPage++;
//                MarketMainRecyclerAdapter adapter = new MarketMainRecyclerAdapter(nfts);
//                adapter.setCardViewOnClickListener(new MarketMainRecyclerAdapter.OnNFTItemClickListener() {
//                    @Override
//                    public void onClicked(View view, NFTDetailData data) {
////                        MirrorMarketNFTDetailDialog detailDialog = new MirrorMarketNFTDetailDialog();
////                        detailDialog.Init(totalView.getContext(),data);
////                        detailDialog.show(mActivity.getFragmentManager(), "Add group dialog");
////                        detailDialog.setOnBuyListener(new OnBuyListener() {
////                            @Override
////                            public void onBuy(NFTDetailData nftDetailData) {
////                                //todo: show thumb page and conform
////                                MirrorMarketConfirmDialog confirmDialog = new MirrorMarketConfirmDialog(mActivity);
////                                confirmDialog.init(nftDetailData);
////                                confirmDialog.show();
////                            }
////                        });
//                    }
//                });
//
//                //设置 布局管理器 这里是实现GridView的效果所以 我们加载GrdiLayoutMannager
//                GridLayoutManager gridLayoutManager = new
//                        //第一个参数是 这个Activity的上下文Context 然后第二个参数是 一行显示的个数 2就是一行2个
//                        GridLayoutManager(totalView.getContext(),2);
//                mNFTRecyclerView.setLayoutManager(gridLayoutManager);
//                mNFTRecyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailed() {
//
//            }
//        });
//    }
//}
