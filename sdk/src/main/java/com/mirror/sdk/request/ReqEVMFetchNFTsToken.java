package com.mirror.sdk.request;

public class ReqEVMFetchNFTsToken extends BaseRequest{
    public String token_address;
    public String token_id;

    public ReqEVMFetchNFTsToken(String tokenAddress,String tokenID){
        token_address = tokenAddress;
        token_id = tokenID;
    }
}
