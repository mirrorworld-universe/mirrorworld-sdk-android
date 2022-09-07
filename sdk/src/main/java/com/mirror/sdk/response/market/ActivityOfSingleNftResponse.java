package com.mirror.sdk.response.market;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ActivityOfSingleNftResponse {
    public String mintAddress;
    public List<AuctionActivity> auctionActivities;
    public List<TokenTransfer> tokenTransfers;
}

class AuctionActivity{
    public long id;
    public String mintAddress;
    public String txSignature;
    public BigDecimal amount;
    public String receiptType;
    public BigDecimal tokenPrice;
    public Date blockTimeCreated;
    public Date blockTimeCanceled;
    public String tradeState;
    public String auctionHouseAddress;
    public String sellerAddress;
    public String buyerAddress;
    public String metadata;
    public Date blockTime;
}

class TokenTransfer{
    public long id;
    public String mintAddress;
    public String txSignature;
    public Object fromWalletAddress;
    public String toWalletAddress;
    public long amount;
    public Date blockTime;
    public long slot;
}
