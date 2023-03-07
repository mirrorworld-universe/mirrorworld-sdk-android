package com.mirror.sdk.listener.metadata;

import com.mirror.sdk.response.metadata.GetNFTRealPriceRes;

public interface GetNFTRealPriceListener {
    void onSuccess(GetNFTRealPriceRes result);
    void onFail(long code,String message);
}
