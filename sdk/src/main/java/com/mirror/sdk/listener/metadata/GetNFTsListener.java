package com.mirror.sdk.listener.metadata;

import com.mirror.sdk.response.metadata.GetNFTsRes;

public interface GetNFTsListener {
    void onSuccess(GetNFTsRes result);
    void onFail(long code,String message);
}
