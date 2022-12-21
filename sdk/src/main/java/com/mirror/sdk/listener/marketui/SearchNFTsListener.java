package com.mirror.sdk.listener.marketui;

import com.mirror.sdk.response.marketui.GetNFTsRes;
import com.mirror.sdk.response.marketui.MirrorMarketSearchNFTObj;

import java.util.List;

public interface SearchNFTsListener {
    void onSuccess(GetNFTsRes result);
    void onFail(long code,String message);
}
