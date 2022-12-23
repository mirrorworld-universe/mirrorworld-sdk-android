package com.mirror.sdk.listener.marketui;

import com.mirror.sdk.response.marketui.GetNFTRealPriceRes;

import java.util.List;

public interface GetNFTRealPriceListener {
    void onSuccess(GetNFTRealPriceRes result);
    void onFail(long code,String message);
}
