package com.mirror.sdk.listener.marketui;

import com.mirror.sdk.response.marketui.GetCollectionFilterInfoRes;

public interface GetCollectionFilterInfoListener {
    void onSuccess(GetCollectionFilterInfoRes result);
    void onFail(long code,String message);
}
