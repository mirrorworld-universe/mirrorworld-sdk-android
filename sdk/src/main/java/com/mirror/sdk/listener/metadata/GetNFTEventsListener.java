package com.mirror.sdk.listener.metadata;

import com.mirror.sdk.response.metadata.GetNFTEventsRes;

public interface GetNFTEventsListener {
    void onSuccess(GetNFTEventsRes result);
    void onFail(long code,String message);
}
