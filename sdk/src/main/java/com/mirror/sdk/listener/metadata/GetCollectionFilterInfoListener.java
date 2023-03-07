package com.mirror.sdk.listener.metadata;

import com.mirror.sdk.response.metadata.GetCollectionFilterInfoRes;

public interface GetCollectionFilterInfoListener {
    void onSuccess(GetCollectionFilterInfoRes result);
    void onFail(long code,String message);
}
