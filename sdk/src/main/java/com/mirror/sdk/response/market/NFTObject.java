package com.mirror.sdk.response.market;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NFTObject {
    public String name;
    public BigDecimal sellerFeeBasisPoints;
    public String updateAuthorityAddress;
    public String description;
    public String string;
    public String externalUrl;
    public List<NFTCreatorObj> creators;
    public NFTOwnerObj owner;
    public List<NFTAttributeObj> attributes;
    public List<String> listings;
}

class NFTCreatorObj {
    public String address;
    public boolean verified;
    public BigDecimal share;
}

class NFTOwnerObj {
    public String address;
}

class NFTAttributeObj
{
    public String trait_type;
    public String value;
}