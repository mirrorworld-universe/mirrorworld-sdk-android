package com.mirror.sdk.constant;

public class MirrorUrl {
//    public static final String URL_AUTH ="https://auth-staging.mirrorworld.fun/";//"https://next-version-test.vercel.app/";//"https://auth-staging.mirrorworld.fun/";//"https://app.mirrorworld.fun/";//
    //auth
    public static final String URL_REFRESH_TOKEN = "auth/refresh-token";
    public static final String URL_QUERY_USER = "auth/user";
    public static final String URL_IS_AUTHENTICATED = "auth/me";
    public static final String URL_LOGIN_WITH_EMAIL = "auth/login";
    public static final String URL_ME = "auth/me";
        //Action Authorization
    public static final String URL_ACTION_REQUEST = "auth/actions/request";
    public static final String URL_ACTION_APPROVE = "approve/";

    //market
    public static final String URL_QUERY_NFT_DETAIL = "solana/nft/";
    public static final String URL_FETCH_MULTIPLE_NFTDATA_BY_MINT_ADDRESS = "solana/nft/mints";
    public static final String URL_FETCH_MULTIPLE_NFTDATA_BY_CREATOR_ADDRESS = "solana/nft/creators";
    public static final String URL_FETCH_MULTIPLE_NFTDATA_BY_UPDATE_AUTHORITY_ADDRESS = "solana/nft/update-authorities";
    public static final String URL_MINT_NFT_COLLECTION = "solana/mint/nft";
    public static final String URL_MINT_TOP_LEVEL_COLLECTION = "solana/mint/collection";
    public static final String URL_MINT_LOWER_LEVEL_COLLECTION = "solana/mint/sub-collection";
    public static final String URL_FETCH_MULTIPLE_NFT = "solana/nft/owners";
    public static final String URL_FETCH_ACTIVITY = "solana/nft/activity/";
    public static final String URL_LIST_NFT_ON_THE_MARKETPLACE = "solana/marketplace/list";
    public static final String URL_UPDATE_LISTING_OF_NFT_ON_THE_MARKETPLACE = "solana/marketplace/update";
    public static final String URL_BUY_NFT_ON_THE_MARKETPLACE = "solana/marketplace/buy";
    public static final String URL_CANCEL_LISTING_OF_NFT_ON_THE_MARKETPLACE = "solana/marketplace/cancel";
    public static final String URL_TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET = "solana/marketplace/transfer";

    //wallet
    public static final String URL_TRANSFER_SQL = "wallet/transfer-sol";
    public static final String URL_TRANSFER_TOKEN = "wallet/transfer-token";
    public static  final String URL_GET_WALLET_TOKEN = "wallet/tokens";
    public static final String URL_GET_WALLET_TRANSACTIONS = "wallet/transactions";


    // new apis
    public static final String URL_CREATE_NEW_MARKET_PLACE = "solana/marketplaces/create";
    public static final String URL_UPDATE_MARKET_PLACE = "solana/marketplaces/update";
    public static final String URL_QUERY_MARKET_PLACE = "solana/marketplaces";

}
