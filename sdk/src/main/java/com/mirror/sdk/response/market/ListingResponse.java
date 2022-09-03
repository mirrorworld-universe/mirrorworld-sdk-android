package com.mirror.sdk.response.market;

import java.math.BigDecimal;
import java.util.Date;

public class ListingResponse {
    public long id;
    public String type;
    public String wallet_address;
    public String mint_address;
    public BigDecimal price;
    public String seller_address;
    public String signature;
    public String status;
    public Date updatedAt;
    public Date createdAt;
    public String to_wallet_address;
}
