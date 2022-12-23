package com.mirror.sdk.listener.marketui;

import com.mirror.sdk.response.marketui.GetCollectionInfoRes;

import java.util.List;

public interface GetCollectionInfoListener {
    void onSuccess(List<GetCollectionInfoRes> result);
    void onFail(long code,String message);
}
