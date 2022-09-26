package com.mirror.sdk.ui.market.apis.requests;

import com.mirror.sdk.constant.MirrorConstant;

import java.util.List;

public class GetNFTsRequest {
    public String collection;
    public int page;
    public int page_size = MirrorConstant.pageSize;
    public GetNFTsRequestOrder order;
    public List<GetNFTsRequestFilter> filter;
}

