package com.mirror.sdk.listener.metadata;

import com.mirror.sdk.response.metadata.GetCollectionSummaryRes;

public interface GetCollectionSummaryListener {
    void onSuccess(GetCollectionSummaryRes res);
    void onFailed(long code,String message);
}
