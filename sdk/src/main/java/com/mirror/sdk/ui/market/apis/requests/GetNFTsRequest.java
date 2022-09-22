package com.mirror.sdk.ui.market.apis.requests;

import java.util.List;

public class GetNFTsRequest {
    public String collection;
    public int page;
    public int page_size;
    public GetNFTsRequestOrder order;
    public List<GetNFTsRequestFilter> filter;
}

