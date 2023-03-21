package com.mirror.sdk.listener.metadata;

import com.mirror.sdk.response.metadata.GetCollectionSummaryRes;

import java.util.List;

public interface GetCollectionSummaryListener {
    void onSuccess(List<GetCollectionSummaryRes> res);
    void onFailed(long code,String message);
}
