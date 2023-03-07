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
    public static final String URL_FETCH_MULTIPLE_NFTDATA_BY_MINT_ADDRESS = "mints";
    public static final String URL_FETCH_MULTIPLE_NFTDATA_BY_CREATOR_ADDRESS = "nft/creators";
    public static final String URL_FETCH_MULTIPLE_NFTDATA_BY_UPDATE_AUTHORITY_ADDRESS = "nft/update-authorities";
    public static final String URL_MINT_NFT_COLLECTION = "nft";
    public static final String URL_UPDATE_NFT_PROPERTIES = "mint/update";
    public static final String URL_MINT_TOP_LEVEL_COLLECTION = "collection";
    //Asset search
    public static final String URL_FETCH_MULTIPLE_NFT = "owners";
    public static final String URL_QUERY_NFT_DETAIL = "nft";
    public static final String URL_FETCH_ACTIVITY = "nft/activity/";
    public static final String URL_LIST_NFT_ON_THE_MARKETPLACE = "list";
    public static final String URL_UPDATE_LISTING_OF_NFT_ON_THE_MARKETPLACE = "marketplace/update";
    public static final String URL_BUY_NFT_ON_THE_MARKETPLACE = "buy";
    public static final String URL_CANCEL_LISTING_OF_NFT_ON_THE_MARKETPLACE = "cancel";
    public static final String URL_TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET = "transfer";

    //wallet
    public static final String URL_GET_WALLET_TRANSACTIONS = "transactions";
    public static final String URL_GET_WALLET_TRANSFER_ETH = "transfer-eth";
    public static final String URL_TRANSFER_SQL = "transfer-sol";
    public static final String URL_GET_TRANSFER_SOL_TRANSACTION = "transaction/transfer-sol";
    public static final String URL_TRANSFER_TOKEN = "transfer-token";
    public static final String URL_GET_WALLET_TOKEN = "tokens";
    public static final String URL_GET_TRANSFER_TOKEN_TRANSACTION = "transaction/transfer-token";

    //Confirmation
    public static final String URL_CHECK_STATUS_OF_TRANSACTION = "transaction-status";
    public static final String URL_CHECK_STATUS_OF_MINTING = "mints-status";

    // new apis
    public static final String URL_CREATE_NEW_MARKET_PLACE = "solana/marketplaces/create";
    public static final String URL_UPDATE_MARKET_PLACE = "solana/marketplaces/update";
    public static final String URL_QUERY_MARKET_PLACE = "solana/marketplaces";

    /**
     * Market Place
     */
    public static final String URL_GET_COLLECTION_INFO = "collections";
    public static final String URL_GET_COLLECTION_FILTER_INFO = "filter_info";
    public static final String URL_GET_COLLECTION_SUMMARY = "summary";
    public static final String URL_GET_NFT_INFO = "nft";
    public static final String URL_GET_NFTS = "nfts";
    public static final String URL_GET_NFT_EVENTS = "events";
    public static final String URL_SEARCH_NFTS = "search";
    public static final String URL_RECOMMEND_SEARCH_NFT = "recommend";

    public static final String URL_GET_NFT_REAL_PRICE = "marketplace/nft/real_price";

    /**
     * Hosts
     */
    public static final String HOST_STAGING = "https://auth-staging.mirrorworld.fun";
    public static final String HOST_PRODUCTION = "https://auth.mirrorworld.fun";

    public static final String NETWORK_MAINNET = "mainnet";
    public static final String NETWORK_DEVNET = "devnet";

    public static final String SERVICE_ASSET_AUCTION = "asset/auction";
    public static final String SERVICE_ASSET_MINT = "asset/mint";
    public static final String SERVICE_ASSET_NFT = "asset/nft";
    public static final String SERVICE_CONFIRMATION = "confirmation";
    public static final String SERVICE_METADATA = "metadata";
    public static final String SERVICE_METADATA_COLLECTION = "metadata/collection";
    public static final String SERVICE_METADATA_NFT = "metadata/nft";


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

    public static final String getMirrorUrl(MirrorService serviceEnum,String APIPath){
        MirrorAPIVersion APIVersion = MirrorAPIVersion.V2;
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

    public static final String getGetMirrorUrl(MirrorService serviceEnum){
        MirrorAPIVersion APIVersion = MirrorAPIVersion.V2;
        MirrorEnv env = getEnv();
        MirrorChains chainEnum = getChain();
        String host = getUrlHost();
        String version = getVersionString(APIVersion);
        String chain = getChainString(chainEnum);
        String network = getNetworkString(env);
        String service = getServiceString(serviceEnum);

        String finalUrl = host + "/" + version + "/" + chain + "/" + network + "/" + service + "/";
        return finalUrl;
    }

    private static final String getServiceString(MirrorService serviceEnum){
        if(serviceEnum.equals(MirrorService.Marketplace)){
            return "marketplaces";
        }else if(serviceEnum.equals(MirrorService.Wallet)){
            return "wallet";
        }else if(serviceEnum.equals(MirrorService.AssetAuction)){
            return SERVICE_ASSET_AUCTION;
        }else if(serviceEnum.equals(MirrorService.AssetMint)){
            return SERVICE_ASSET_MINT;
        }else if(serviceEnum.equals(MirrorService.AssetNFT)){
            return SERVICE_ASSET_NFT;
        }else if(serviceEnum.equals(MirrorService.Confirmation)){
            return SERVICE_CONFIRMATION;
        }else if(serviceEnum.equals(MirrorService.Metadata)){
            return SERVICE_METADATA;
        }else if(serviceEnum.equals(MirrorService.MetadataCollection)){
            return SERVICE_METADATA_COLLECTION;
        }else if(serviceEnum.equals(MirrorService.MetadataNFT)){
            return SERVICE_METADATA_NFT;
        }else if(serviceEnum.equals(MirrorService.MetadataNFTSearch)){
            return "metadata/nft/search";
        }else {
            MirrorSDK.logError("Unknown service:"+serviceEnum);
            return "";
        }
    }

    private static final String getNetworkString(MirrorEnv env){
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

    private static final String getChainString(MirrorChains chain){
        if(chain.equals(MirrorChains.SOLANA)){
            return "solana";
        }else {
            MirrorSDK.logError("Invalida chain enum:"+chain);
            return "solana";
        }
    }

    private static final String getVersionString(MirrorAPIVersion APIVersion){
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
