package com.mirror.sdk.ui.market.apis.listeners;

import com.mirror.sdk.ui.market.apis.responses.NFTSellSummary;

import java.util.List;

public interface GetSellSummaryListener {
    void OnSuccess(List<NFTSellSummary> summaries);
    void OnFail();
}
