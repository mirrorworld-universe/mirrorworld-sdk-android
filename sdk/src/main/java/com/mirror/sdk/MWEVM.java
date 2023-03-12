package com.mirror.sdk;

import android.app.Activity;
import android.content.Context;

import com.mirror.sdk.chain.MWEVMWrapper;
import com.mirror.sdk.chain.MWSolanaWrapper;
import com.mirror.sdk.constant.MirrorChains;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.constant.MirrorSafeOptType;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.auth.LoginListener;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.listener.market.UpdateListListener;
import com.mirror.sdk.listener.metadata.GetCollectionFilterInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionSummaryListener;
import com.mirror.sdk.listener.metadata.GetNFTRealPriceListener;
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.particle.MirrorSafeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class MWEVM {
    /**
     * Type: SDK
     * Function: Init SDK
     */
    public final static void initSDK(Context activityContext, String APIKey, MirrorEnv env){
        MWEVMWrapper.initSDK(activityContext, APIKey, env);
    }

    /**
     * Type: SDK
     * Function: Show the sdk flow in console if true.
     * @param useDebugMode
     */
    final public static void setDebug(boolean useDebugMode){
        MWEVMWrapper.setDebug(useDebugMode);
    }

    /**
     * Type: SDK
     * Function: Login.
     */
    final public static void startLogin(LoginListener loginListener,Activity currentActivity){
        MWEVMWrapper.startLogin(loginListener,currentActivity);
    }
    final public static void startLogin(MirrorCallback loginListener,Activity currentActivity){
        MWEVMWrapper.startLogin(loginListener,currentActivity);
    }

    /**
     * Type: SDK
     * Function: Get now environment.
     * @return
     */
    final public static MirrorEnv getEnvironment(){
        return MWEVMWrapper.getEnvironment();
    }
    /**
     * Type: SDK
     * Function: Guest login.
     */
    final public static void guestLogin(LoginListener listener){
        MWEVMWrapper.guestLogin(listener);
    }

    /**
     * Type: SDK
     * Function: Logout.
     * @param listener
     */
    final public static void logout(BoolListener listener){
        MWEVMWrapper.logout(listener);
    }

    /**
     * Type: SDK
     * Function: Login with email and get all response.
     */
    final public static void loginWithEmail(String email,String password,MirrorCallback callback){
        MWEVMWrapper.loginWithEmail(email, password, callback);
    }

    /**
     * Type: SDK
     * Function: Open user's wallet page.
     */
    final public static void openWallet(MirrorCallback callback){
        MWEVMWrapper.openWallet("",callback);
    }

    final public static void openWallet(String walletUrl,MirrorCallback callback){
        MWEVMWrapper.openWallet(walletUrl,callback);
    }

    /**
     * Type: SDK
     * Function: Open market of this app.
     */
    final public static void openMarket(String marketUrl){
        MWEVMWrapper.openMarket(marketUrl);
    }

    /**
     * Type: SDK
     * Function: Open any url
     */
    final public static void openUrl(String url){
        MWEVMWrapper.openUrl(url);
    }

    /**
     * Type: SDK
     * Function: Fetch details of a user.
     * @param listener
     */
    final public static void fetchUser(FetchUserListener listener){
        MWEVMWrapper.fetchUser(listener);
    }

    /**
     * Type: SDK
     * Function: Query some user and get details;
     * @param email
     * @param listener
     */
    final public static void queryUser(String email, FetchUserListener listener){
        MWEVMWrapper.queryUser(email,listener);
    }

    /**
     * Type: SDK
     * Function: Checks whether the current user is logged in
     * @param listener
     */
    final public static void isLoggedIn(BoolListener listener){
        MWEVMWrapper.isLoggedIn(listener);
    }

    //Wallet
    //transfer-token
    final public static void transferSPLToken(String nonce, String gasPrice, String gasLimit, String to, int amount, String contract, MirrorCallback mirrorCallback){
        MWEVMWrapper.transferSPLToken(nonce, gasPrice, gasLimit, to, amount, contract, mirrorCallback);
    }

    //transfer-eth
    final public static void transferETH(String nonce, String gasPrice, String gasLimit, String to, int amount, TransferSOLListener listener){
        MWEVMWrapper.transferETH(nonce, gasPrice, gasLimit, to, amount, listener);
    }

    final public static void getTokens(MirrorCallback listener){
        MWEVMWrapper.getTokens(listener);
    }

    final public static void getTokensByWallet(String walletAddress, MirrorCallback walletTokenListener){
        MWEVMWrapper.getTokensByWallet(walletAddress, walletTokenListener);
    }

    final public static void getTransactionsOfLoggedUser(int limit, String before, MirrorCallback walletTransactionListener){
        MWEVMWrapper.getTransactionsOfLoggedUser(limit, before, walletTransactionListener);
    }

    final public static void getTransactionsByWallet(String walletAddress, int limit, MirrorCallback callback){
        MWEVMWrapper.getTransactionsByWallet(walletAddress, limit, callback);
    }

    final public static void getTransactionBySignature(String signature, MirrorCallback listener){
        MWEVMWrapper.getTransactionBySignature(signature, listener);
    }

    //Metadata
    final public static void getNFTRealPrice(String price, int fee, GetNFTRealPriceListener listener){
        MirrorSDK.getInstance().GetNFTRealPrice(price, fee, listener);
    }

    final public static void getCollectionInfo(List<String> collections, GetCollectionInfoListener listener){
        MWEVMWrapper.getCollectionInfo(collections, listener);
    }

    final public static void getCollectionFilterInfo(String collectionAddress, GetCollectionFilterInfoListener listener){
        MWEVMWrapper.getCollectionFilterInfo(collectionAddress, listener);
    }

    final public static void getCollectionSummary(List<String> collections, GetCollectionSummaryListener listener){
        MWEVMWrapper.getCollectionSummary(collections, listener);
    }

    final public static void getNFTInfo(String contractAddress, String tokenID , MirrorCallback listener){
        MWEVMWrapper.getNFTInfo(contractAddress, tokenID, listener);
    }

    final public static void getNFTsByUnabridgedParams(String collection, int page, int page_size, String order_by, boolean desc, double sale, List<JSONObject> filter, MirrorCallback listener){
        MWEVMWrapper.getNFTsByUnabridgedParams(collection, page, page_size, order_by, desc, sale, filter, listener);
    }

    final public static void getNFTEvents(String mint_address, int page, int page_size, MirrorCallback listener){
        MWEVMWrapper.getNFTEvents(mint_address, page, page_size, listener);
    }

    final public static void searchNFTs(List<String> collections, String searchStr, MirrorCallback listener){
        MWEVMWrapper.searchNFTs(collections, searchStr, listener);
    }

    final public static void recommendSearchNFT(List<String> collections, MirrorCallback listener){
        MWEVMWrapper.recommendSearchNFT(collections, listener);
    }

    //Asset/mint
    final public static void mintNFT(String collection_mint,String detailUrl, String confirmation,String to_wallet_address, MintNFTListener mintNFTListener) {
        MWEVMWrapper.mintNFT(collection_mint, detailUrl, confirmation, to_wallet_address, mintNFTListener);
    }
    //Asset/NFT
    final public static void updateNFTProperties(String mintAddress, String name, String symbol, String updateAuthority, String NFTJsonUrl,int seller_fee_basis_points, MintNFTListener mintNFTListener) {
        MWEVMWrapper.updateNFTProperties(mintAddress, name, symbol, updateAuthority, NFTJsonUrl, seller_fee_basis_points, mintNFTListener);
    }

    final public static void fetchNFTsByOwnerAddresses(List<String> owners, int limit, MirrorCallback fetchByOwnerListener){
        MWEVMWrapper.fetchNFTsByOwnerAddresses(owners, limit, fetchByOwnerListener);
    }

    final public static void fetchNFTsByMintAddresses(List<String> tokens, MirrorCallback fetchByMintAddressListener){
        MWEVMWrapper.fetchNFTsByMintAddresses(tokens, fetchByMintAddressListener);
    }

    final public static void fetchNFTsByCreatorAddresses(List<String> creators, int limit, int offset, MirrorCallback listener){
        MWEVMWrapper.fetchNFTsByCreatorAddresses(creators, limit, offset, listener);
    }

    final public static void fetchNFTsByUpdateAuthorities(List<String> update_authorities, int limit, int offset, MirrorCallback listener){
        MWEVMWrapper.fetchNFTsByUpdateAuthorities(update_authorities, limit, offset, listener);
    }

    final public static void fetchNFTMarketplaceActivity(String mint_address, MirrorCallback fetchSingleNFTActivityListener){
        MWEVMWrapper.fetchNFTMarketplaceActivity(mint_address, fetchSingleNFTActivityListener);
    }

    final public static void createVerifiedCollection(String contract_type, String detailUrl,String confirmation, MirrorCallback createTopCollectionListener){
        MWEVMWrapper.createVerifiedCollection(contract_type, detailUrl, confirmation, createTopCollectionListener);
    }

    //Asset/Auction
    final public static void transferNFT(String token_id,String collection_address, String to_wallet_address, MirrorCallback transferNFTListener){
        MWEVMWrapper.transferNFT(token_id, collection_address, to_wallet_address, transferNFTListener);
    }

    final public static void listNFT(String collection_address, String token_id, float price, String marketplace_address, MirrorCallback listener){
        MWEVMWrapper.listNFT(collection_address, token_id, price, marketplace_address, listener);
    }

    final public static void updateNFTListing(String mint_address, Double price,String confirmation, UpdateListListener listener){
        MirrorSDK.getInstance().UpdateNFTListing(mint_address, price, confirmation, listener);
    }

    final public static void cancelNFTListing(String collection_address, String token_id,String marketplace_address, MirrorCallback listener){
        MWEVMWrapper.cancelNFTListing(collection_address, token_id, marketplace_address, listener);
    }

    final public static void buyNFT(String collection_address, String token_id,float price,String marketplace_address, MirrorCallback buyNFTListener){
        MWEVMWrapper.buyNFT(collection_address, token_id, price, marketplace_address, buyNFTListener);
    }}
