package com.mirror.sdk.ui.market;

import com.mirror.sdk.ui.market.apis.responses.CollectionInfo;
import com.mirror.sdk.ui.market.apis.responses.CollectionOrder;
import com.mirror.sdk.ui.market.model.NFTDetailData;

import java.util.List;

public class MarketDataController {
    private static volatile MarketDataController instance;
    public static MarketDataController getInstance(){
        if (instance == null){
            synchronized(MarketDataController.class){
                instance = new MarketDataController();
            }
        }
        return instance;
    }

    //base data
    private List<CollectionInfo> mCollections;
    private List<NFTDetailData> mNFTs;
    //filter data
    private CollectionOrder mNowOrder;

    public void setCollections(List<CollectionInfo> collections){
        mCollections = collections;
    }

    public void setNFTs(List<NFTDetailData> nfts){
        mNFTs = nfts;
    }
    public List<NFTDetailData> getNFTs(){ return mNFTs; }

    public void setOrder(CollectionOrder order){
        mNowOrder = order;
    }

    public CollectionOrder getOrder(){
        return mNowOrder;
    }
}
