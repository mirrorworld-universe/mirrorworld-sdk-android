package com.mirror.market.market.apis.listeners;

import com.mirror.market.market.apis.responses.NFTSellSummary;

import java.util.List;

public interface GetSellSummaryListener {
    void OnSuccess(List<NFTSellSummary> summaries);
    void OnFail();
}
