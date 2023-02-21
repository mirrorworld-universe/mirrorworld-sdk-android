package com.mirror.sdk.constant;

import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.MirrorWorld;

public class MirrorUrl {

    //auth
    public static final String URL_REFRESH_TOKEN = "auth/refresh-token";
    public static final String URL_QUERY_USER = "auth/user";
    public static final String URL_IS_AUTHENTICATED = "auth/me";
    public static final String URL_LOGIN_WITH_EMAIL = "auth/login";
    public static final String URL_GUEST_LOGIN = "auth/guest-login";
    public static final String URL_LOGOUT = "auth/logout";
    public static final String URL_ME = "auth/me";

    //Action Authorization
    public static final String URL_ACTION_REQUEST = "auth/actions/request";
    public static final String URL_ACTION_APPROVE = "approve/";

    //market
    public static final String URL_QUERY_NFT_DETAIL = "nft/";
    public static final String URL_FETCH_MULTIPLE_NFTDATA_BY_MINT_ADDRESS = "nft/mints";
    public static final String URL_FETCH_MULTIPLE_NFTDATA_BY_CREATOR_ADDRESS = "nft/creators";
    public static final String URL_FETCH_MULTIPLE_NFTDATA_BY_UPDATE_AUTHORITY_ADDRESS = "nft/update-authorities";
    public static final String URL_MINT_NFT_COLLECTION = "mint/nft";
    public static final String URL_UPDATE_NFT_PROPERTIES = "mint/update";
    public static final String URL_MINT_TOP_LEVEL_COLLECTION = "mint/collection";
    public static final String URL_FETCH_MULTIPLE_NFT = "nft/owners";
    public static final String URL_FETCH_ACTIVITY = "nft/activity/";
    public static final String URL_LIST_NFT_ON_THE_MARKETPLACE = "list";
    public static final String URL_UPDATE_LISTING_OF_NFT_ON_THE_MARKETPLACE = "marketplace/update";
    public static final String URL_BUY_NFT_ON_THE_MARKETPLACE = "buy";
    public static final String URL_CANCEL_LISTING_OF_NFT_ON_THE_MARKETPLACE = "cancel";
    public static final String URL_TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET = "transfer";

    //wallet
    public static final String URL_TRANSFER_SQL = "transfer-sol";
    public static final String URL_GET_TRANSFER_SOL_TRANSACTION = "transaction/transfer-sol";
    public static final String URL_TRANSFER_TOKEN = "transfer-token";
    public static final String URL_GET_WALLET_TOKEN = "tokens";
    public static final String URL_GET_TRANSFER_TOKEN_TRANSACTION = "transaction/transfer-token";
    public static final String URL_GET_WALLET_TRANSACTIONS = "transactions";

    //Confirmation
    public static final String URL_CHECK_STATUS_OF_TRANSACTION = "confirmation/transactions-status";
    public static final String URL_CHECK_STATUS_OF_MINTING = "confirmation/mints-status";

    // new apis
    public static final String URL_CREATE_NEW_MARKET_PLACE = "solana/marketplaces/create";
    public static final String URL_UPDATE_MARKET_PLACE = "solana/marketplaces/update";
    public static final String URL_QUERY_MARKET_PLACE = "solana/marketplaces";

    /**
     * Market Place
     */
    public static final String URL_GET_COLLECTION_FILTER_INFO = "marketplace/collection/filter_info";
    public static final String URL_GET_NFT_INFO = "marketplace/nft/";
    public static final String URL_GET_COLLECTION_INFO = "marketplace/collections";
    public static final String URL_GET_NFT_EVENTS = "marketplace/nft/events";
    public static final String URL_SEARCH_NFTS = "marketplace/nft/search";
    public static final String URL_RECOMMEND_SEARCH_NFT = "marketplace/nft/search/recommend";
    public static final String URL_GET_NFTS = "marketplace/nfts";
    public static final String URL_GET_NFT_REAL_PRICE = "marketplace/nft/real_price";

    /**
     * Hosts
     */
    public static final String HOST_STAGING = "https://auth-staging.mirrorworld.fun";
    public static final String HOST_PRODUCTION = "https://auth.mirrorworld.fun";

    public static final String NETWORK_MAINNET = "mainnet";
    public static final String NETWORK_DEVNET = "devnet";

    public static final String SERVICE_MARKET = "marketplaces";
    public static final String SERVICE_WALLET = "wallet";


    public static final MirrorEnv getEnv(){
        if (!MirrorSDK.getInstance().getInited()){
            MirrorSDK.logError("SDK not inited!");
            return MirrorEnv.MainNet;
        }
        MirrorEnv env = MirrorWorld.getEnvironment();
        return env;
    }

    public static final MirrorChains getChain(){
        if (!MirrorSDK.getInstance().getInited()){
            MirrorSDK.logError("SDK not inited!");
            return MirrorChains.SOLANA;
        }
        MirrorChains chain = MirrorSDK.getInstance().getChain();
        return chain;
    }

    public static final String getUrlHost(){
        MirrorEnv env = getEnv();
        if(env.equals(MirrorEnv.StagingMainNet)){
            return HOST_STAGING;
        }else if(env.equals(MirrorEnv.StagingDevNet)){
            return HOST_STAGING;
        }else if(env.equals(MirrorEnv.DevNet)){
            return HOST_PRODUCTION;
        }else if(env.equals(MirrorEnv.MainNet)){
            return HOST_PRODUCTION;
        }else {
            MirrorSDK.logError("Unknown env:"+env+".Will use production host.");
            return HOST_PRODUCTION;
        }
    }

    public static final String getMirrorUrl(MirrorAPIVersion APIVersion,MirrorService serviceEnum,String APIPath){
        MirrorEnv env = getEnv();
        MirrorChains chainEnum = getChain();
        String host = getUrlHost();
        String version = getVersionString(APIVersion);
        String chain = getChainString(chainEnum);
        String network = getNetworkString(env);
        String service = getServiceString(serviceEnum);

        String finalUrl = host + "/" + version + "/" + chain + "/" + network + "/" + service + "/" + APIPath;
        return finalUrl;
    }

    public static final String getServiceString(MirrorService serviceEnum){
        if(serviceEnum.equals(MirrorService.Marketplace)){
            return SERVICE_MARKET;
        }else if(serviceEnum.equals(MirrorService.Wallet)){
            return SERVICE_WALLET;
        }else {
            MirrorSDK.logError("Unknown service:"+serviceEnum);
            return "";
        }
    }

    public static final String getNetworkString(MirrorEnv env){
        if(env.equals(MirrorEnv.StagingMainNet)){
            return NETWORK_MAINNET;
        }else if(env.equals(MirrorEnv.StagingDevNet)){
            return NETWORK_DEVNET;
        }else if(env.equals(MirrorEnv.MainNet)){
            return NETWORK_MAINNET;
        }else if(env.equals(MirrorEnv.DevNet)){
            return NETWORK_DEVNET;
        }else {
            MirrorSDK.logError("Unknown env:"+env+".Will use mainnet.");
            return NETWORK_DEVNET;
        }
    }

    public static final String getChainString(MirrorChains chain){
        if(chain.equals(MirrorChains.SOLANA)){
            return "solana";
        }else {
            MirrorSDK.logError("Invalida chain enum:"+chain);
            return "solana";
        }
    }

    public static final String getVersionString(MirrorAPIVersion APIVersion){
        if(APIVersion.equals(MirrorAPIVersion.V1)){
            return "v1";
        }else if(APIVersion.equals(MirrorAPIVersion.V2)){
            return "v2";
        }else {
            MirrorSDK.logError("Invalida api version:"+APIVersion);
            return "v0";
        }
    }
}
