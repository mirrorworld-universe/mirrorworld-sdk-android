package com.mirror.sdk.listener.metadata;

import com.mirror.sdk.response.metadata.GetCollectionInfoRes;

import java.util.List;

public interface GetCollectionInfoListener {
    void onSuccess(List<GetCollectionInfoRes> result);
    void onFail(long code,String message);
}
