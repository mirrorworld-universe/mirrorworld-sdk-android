package com.mirror.sdk.listener.marketui;

import java.util.List;

public class MirrorMarketUINFTObject {
    public String collection;
    public String name;
    public String mint_address;
    public String owner_address;
    public String image;
    public String price;
    public boolean listed;
    public double seller_fee_basis_points;
    public List<MirrorMarketUINFTAttribute> attributes;
    public List<String> off_chain_attribute;
    public List<String> skill_attributes;
    public String mint_authority;
    public String update_authority;
}

class MirrorMarketUINFTAttribute{
    public String trait_type;
    public String value;
}
