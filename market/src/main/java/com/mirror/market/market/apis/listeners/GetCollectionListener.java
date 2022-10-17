package com.mirror.market.market.apis.listeners;

import com.mirror.market.market.apis.responses.CollectionInfo;
import com.mirror.market.market.apis.responses.GetCollectionsResponse;

import java.util.List;

public interface GetCollectionListener {
    void onSuccess(List<CollectionInfo> response);
    void onFail(long code,String message);
}
