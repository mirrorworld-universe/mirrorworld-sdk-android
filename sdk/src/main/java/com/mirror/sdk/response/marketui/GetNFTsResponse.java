package com.mirror.sdk.response.marketui;

import com.mirror.sdk.ui.market.model.NFTDetailData;

import java.util.List;

public class GetNFTsResponse {
    public String collection;
    public int page;
    public int page_size;
    public List<NFTDetailData> nfts;
}
