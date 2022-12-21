package com.mirror.sdk.response.marketui;

import java.util.List;

public class GetCollectionInfoRes {
    public String collection;
    public String collection_name;
    public String collection_type;
    public List<MirrorMarketCollectionOrder> collection_orders;
    public String trace_id;
}

class MirrorMarketCollectionOrder{
    public String order_field;
    public String order_desc;
    public boolean desc;
}