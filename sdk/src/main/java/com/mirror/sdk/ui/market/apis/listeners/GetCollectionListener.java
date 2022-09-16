package com.mirror.sdk.ui.market.apis.listeners;

import com.mirror.sdk.ui.market.apis.responses.GetCollectionsResponse;

public interface GetCollectionListener {
    void onSuccess(GetCollectionsResponse response);
    void onFail(long code,String message);
}
