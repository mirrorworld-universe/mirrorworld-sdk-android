package com.mirror.market.market.apis.listeners;

import com.mirror.market.market.model.NFTDetailData;

import java.util.List;

public interface GetNFTsListener {
    void onSuccess(List<NFTDetailData> nfts);
    void onFailed(long code,String message);
}
