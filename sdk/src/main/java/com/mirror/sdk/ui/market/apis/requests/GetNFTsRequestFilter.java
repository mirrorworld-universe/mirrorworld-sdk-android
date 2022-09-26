package com.mirror.sdk.ui.market.apis.requests;

import java.util.List;

public class GetNFTsRequestFilter {
    public String filter_name;
    public String filter_type;
    //enum
    public List<String> filter_value;
    //range
    public List<Integer> filter_range;
}
