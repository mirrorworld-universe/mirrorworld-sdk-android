package com.mirror.sdk.constant;

import android.util.Log;

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
    //Action Authorization
    public static final String URL_ACTION_REQUEST = "auth/actions/request";
    public static final String URL_ACTION_APPROVE = "approve/";

    //market
    public static final String URL_FETCH_MULTIPLE_NFTDATA_BY_MINT_ADDRESS = "mints";
    public static final String URL_FETCH_MULTIPLE_NFTDATA_BY_CREATOR_ADDRESS = "creators";
    public static final String URL_FETCH_MULTIPLE_NFTDATA_BY_UPDATE_AUTHORITY_ADDRESS = "update-authorities";
    public static final String URL_MINT_NFT_COLLECTION = "nft";
    public static final String URL_UPDATE_NFT_PROPERTIES = "update";
    public static final String URL_MINT_TOP_LEVEL_COLLECTION = "collection";
    //Asset search
    public static final String URL_FETCH_MULTIPLE_NFT_SOLANA = "owners";
    public static final String URL_FETCH_MULTIPLE_NFT_EVM = "owner";
    public static final String URL_QUERY_NFT_DETAIL = "nft";
    public static final String URL_FETCH_ACTIVITY = "activity/";
    public static final String URL_LIST_NFT_ON_THE_MARKETPLACE = "list";
    public static final String URL_UPDATE_LISTING_OF_NFT_ON_THE_MARKETPLACE = "update";
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
    public static final String URL_CREATE_NEW_MARKET_PLACE = "marketplaces/create";
    public static final String URL_UPDATE_MARKET_PLACE = "marketplaces/update";
    public static final String URL_QUERY_MARKET_PLACE = "marketplaces";

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
            return MirrorChains.Solana;
        }
        MirrorChains chain = MirrorSDK.getInstance().getChain();
        return chain;
    }

    public static final String getUrlHost(MirrorService service){
        MirrorEnv env = getEnv();
        if(service == MirrorService.Wallet || belongToAsset(service) || belongToMetadata(service)
        || service == MirrorService.Confirmation){
            if(env == MirrorEnv.StagingMainNet){
                return "https://api-staging.mirrorworld.fun";
            }else if(env == MirrorEnv.StagingDevNet){
                return "https://api-staging.mirrorworld.fun";
            }else if(env == MirrorEnv.DevNet){
                return "https://api.mirrorworld.fun";
            }else if(env == MirrorEnv.MainNet){
                return "https://api.mirrorworld.fun";
            }else {
                MirrorSDK.logError("Unknown env:"+env);
                return "https://api-staging.mirrorworld.fun";
            }
        }else {
            if(env.equals(MirrorEnv.StagingMainNet)){
                return "https://auth-staging.mirrorworld.fun";
            }else if(env.equals(MirrorEnv.StagingDevNet)){
                return "https://auth-staging.mirrorworld.fun";
            }else if(env.equals(MirrorEnv.DevNet)){
                return "https://auth.mirrorworld.fun";
            }else if(env.equals(MirrorEnv.MainNet)){
                return "https://auth.mirrorworld.fun";
            }else {
                MirrorSDK.logError("Unknown env:"+env+".Will use production host.");
                return "https://auth.mirrorworld.fun";
            }
        }
    }

    public static final String getMirrorUrl(MirrorService serviceEnum,String APIPath){
        MirrorAPIVersion APIVersion = MirrorAPIVersion.V2;
        MirrorEnv env = getEnv();
        MirrorChains chainEnum = getChain();
        String service = getServiceString(serviceEnum);
        String host = getUrlHost(serviceEnum);
        String version = getVersionString(APIVersion);
        String chain = getChainString(chainEnum);
        String network = getNetworkString(chainEnum,env);

        String finalUrl = host + "/" + version + "/" + chain + "/" + network + "/" + service + "/" + APIPath;
        return finalUrl;
    }

    public static final String getGetMirrorUrl(MirrorService serviceEnum){
        MirrorAPIVersion APIVersion = MirrorAPIVersion.V2;
        MirrorEnv env = getEnv();
        MirrorChains chainEnum = getChain();
        String host = getUrlHost(serviceEnum);
        String version = getVersionString(APIVersion);
        String chain = getChainString(chainEnum);
        String network = getNetworkString(chainEnum,env);
        String service = getServiceString(serviceEnum);

        String finalUrl = host + "/" + version + "/" + chain + "/" + network + "/" + service + "/";
        return finalUrl;
    }

    public static final String getActionRoot(){
        MirrorEnv env = getEnv();
        MirrorAPIVersion APIVersion = MirrorAPIVersion.V2;
        String version = getVersionString(APIVersion);
        if(env == MirrorEnv.StagingMainNet){
            return "https://api-staging.mirrorworld.fun/"+version+"/";
        }else if(env == MirrorEnv.StagingDevNet){
            return "https://api-staging.mirrorworld.fun/"+version+"/";
        }else if(env == MirrorEnv.DevNet){
            return "https://api.mirrorworld.fun/"+version+"/";
        }else if(env == MirrorEnv.MainNet){
            return "https://api.mirrorworld.fun/"+version+"/";
        }else {
            MirrorSDK.logWarn("Unknown env:"+env);
            return "https://api.mirrorworld.fun/v1/";
        }
    }

    private static final String getServiceString(MirrorService serviceEnum){
        if(serviceEnum.equals(MirrorService.Marketplace)){
            return "marketplaces";
        }else if(serviceEnum.equals(MirrorService.Wallet)){
            return "wallet";
        }else if(serviceEnum.equals(MirrorService.AssetAuction)){
            return "asset/auction";
        }else if(serviceEnum.equals(MirrorService.AssetMint)){
            return "asset/mint";
        }else if(serviceEnum.equals(MirrorService.AssetNFT)){
            return "asset/nft";
        }else if(serviceEnum.equals(MirrorService.Confirmation)){
            return "asset/confirmation";
        }else if(serviceEnum.equals(MirrorService.Metadata)){
            return "metadata";
        }else if(serviceEnum.equals(MirrorService.MetadataCollection)){
            return "metadata/collection";
        }else if(serviceEnum.equals(MirrorService.MetadataNFT)){
            return "metadata/nft";
        }else if(serviceEnum.equals(MirrorService.MetadataNFTSearch)){
            return "metadata/nft/search";
        }else {
            MirrorSDK.logError("Unknown service:"+serviceEnum);
            return "";
        }
    }

    private static final String getNetworkString(MirrorChains chain, MirrorEnv env){
        if(chain == MirrorChains.Solana){
            if(env.equals(MirrorEnv.StagingMainNet)){
                return "mainnet";
            }else if(env.equals(MirrorEnv.StagingDevNet)){
                return "devnet";
            }else if(env.equals(MirrorEnv.MainNet)){
                return "mainnet";
            }else if(env.equals(MirrorEnv.DevNet)){
                return "devnet";
            }else {
                MirrorSDK.logError("Unknown env:"+env+".Will use mainnet.");
                return "devnet";
            }
        }else if(chain == MirrorChains.Ethereum){
            if(env.equals(MirrorEnv.StagingMainNet)){
                return "mainnet";
            }else if(env.equals(MirrorEnv.StagingDevNet)){
                return "goerli";
            }else if(env.equals(MirrorEnv.MainNet)){
                return "mainnet";
            }else if(env.equals(MirrorEnv.DevNet)){
                return "goerli";
            }else {
                MirrorSDK.logError("Unknown env:"+env+".Will use mainnet.");
                return "goerli";
            }
        }else if(chain == MirrorChains.Polygon){
            if(env.equals(MirrorEnv.StagingMainNet)){
                return "mumbai-mainnet";
            }else if(env.equals(MirrorEnv.StagingDevNet)){
                return "mumbai-testnet";
            }else if(env.equals(MirrorEnv.MainNet)){
                return "mumbai-mainnet";
            }else if(env.equals(MirrorEnv.DevNet)){
                return "mumbai-testnet";
            }else {
                MirrorSDK.logError("Unknown env:"+env+".Will use mainnet.");
                return "mumbai-testnet";
            }
        }else if(chain == MirrorChains.BNB){
            if(env.equals(MirrorEnv.StagingMainNet)){
                return "bnb-mainnet";
            }else if(env.equals(MirrorEnv.StagingDevNet)){
                return "bnb-testnet";
            }else if(env.equals(MirrorEnv.MainNet)){
                return "bnb-mainnet";
            }else if(env.equals(MirrorEnv.DevNet)){
                return "bnb-testnet";
            }else {
                MirrorSDK.logError("Unknown env:"+env+".Will use mainnet.");
                return "bnb-testnet";
            }
        }
        else {
            Log.e("MirrorSDK","Unknwon chain"+chain);
            return "unknwon-net";
        }
    }

    private static final String getChainString(MirrorChains chain){
        if(chain.equals(MirrorChains.Solana)){
            return "solana";
        }else if(chain.equals(MirrorChains.Ethereum)){
            return "ethereum";
        }else if(chain.equals(MirrorChains.Polygon)){
            return "polygon";
        }else if(chain.equals(MirrorChains.BNB)){
            return "bnb";
        }else if(chain.equals(MirrorChains.SUI)){
            return "sui";
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

    private static final boolean belongToAsset(MirrorService service){
        return service == MirrorService.AssetAuction || service == MirrorService.AssetMint || service == MirrorService.AssetNFT;
    }

    private static final boolean belongToMetadata(MirrorService service){
        return service == MirrorService.Metadata || service == MirrorService.MetadataNFT
                || service == MirrorService.MetadataCollection || service == MirrorService.MetadataNFTSearch;
    }
}
