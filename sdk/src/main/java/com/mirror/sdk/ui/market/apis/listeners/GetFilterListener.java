package com.mirror.sdk.ui.market.apis.listeners;

import com.mirror.sdk.ui.market.apis.responses.CollectionFilter;

import java.util.List;

public interface GetFilterListener {
    public void onSuccess(List<CollectionFilter> filters);
    public void onFailed(long code,String message);
}
