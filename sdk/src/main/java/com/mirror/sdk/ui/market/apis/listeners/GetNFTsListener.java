package com.mirror.sdk.ui.market.apis.listeners;

import com.mirror.sdk.ui.market.model.NFTDetailData;

import java.util.List;

public interface GetNFTsListener {
    void onSuccess(List<NFTDetailData> nfts);
    void onFailed();
}
