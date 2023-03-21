package com.mirror.sdk.listener.metadata;

import com.mirror.sdk.response.metadata.MirrorMarketSearchNFTObj;

import java.util.List;

public interface SOLSearchNFTsListener {
    void onSuccess(List<MirrorMarketSearchNFTObj> result);
    void onFail(long code,String message);
}
