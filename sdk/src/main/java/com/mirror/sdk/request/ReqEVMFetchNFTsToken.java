package com.mirror.sdk.request;

import java.util.List;

public class ReqEVMFetchNFTsToken extends BaseRequest{
    public String token_address;
    public int token_id;

    public ReqEVMFetchNFTsToken(String tokenAddress,int tokenID){
        token_address = tokenAddress;
        token_id = tokenID;
    }
}

