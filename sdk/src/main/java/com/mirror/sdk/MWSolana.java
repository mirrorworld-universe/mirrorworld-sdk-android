package com.mirror.sdk;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.mirror.sdk.chain.MWSolanaWrapper;
import com.mirror.sdk.constant.MirrorChains;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.constant.MirrorResCode;
import com.mirror.sdk.constant.MirrorSafeOptType;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.auth.LoginListener;
import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingListener;
import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingResponse;
import com.mirror.sdk.listener.market.BuyNFTListener;
import com.mirror.sdk.listener.market.CancelListListener;
import com.mirror.sdk.listener.market.CreateTopCollectionListener;
import com.mirror.sdk.listener.market.FetchByOwnerListener;
import com.mirror.sdk.listener.market.FetchNFTsListener;
import com.mirror.sdk.listener.market.FetchSingleNFTActivityListener;
import com.mirror.sdk.listener.market.ListNFTListener;
import com.mirror.sdk.listener.market.TransferNFTListener;
import com.mirror.sdk.listener.metadata.GetCollectionFilterInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionSummaryListener;
import com.mirror.sdk.listener.metadata.GetNFTEventsListener;
import com.mirror.sdk.listener.metadata.GetNFTsListener;
import com.mirror.sdk.listener.metadata.SOLSearchNFTsListener;
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.listener.wallet.GetOneWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
import com.mirror.sdk.listener.wallet.TransactionsDTO;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.particle.MirrorSafeAPI;
import com.mirror.sdk.response.CommonResponse;
import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.response.market.MintResponse;
import com.mirror.sdk.response.market.MultipleNFTsResponse;
import com.mirror.sdk.response.metadata.MirrorMarketSearchNFTObj;
import com.mirror.sdk.response.wallet.GetWalletTokenResponse;
import com.mirror.sdk.response.wallet.GetWalletTransactionsResponse;
import com.mirror.sdk.utils.MirrorGsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class MWSolana {

    /**
     * Type: SDK
     * Function: Init SDK
     */
    public final static void initSDK(Activity activityContext, MirrorEnv env){
        MWSolanaWrapper.initSDK(activityContext, env);
    }

    /**
     * Type: SDK
     * Function: Show the sdk flow in console if true.
     * @param useDebugMode
     */
    final public static void setDebug(boolean useDebugMode){
        MWSolanaWrapper.setDebug(useDebugMode);
    }

    /**
     * Type: SDK
     * Function: Login.
     */
    final public static void startLogin(LoginListener loginListener){
        MWSolanaWrapper.startLogin(loginListener);
    }

    final public static void startLogin(MirrorCallback callback){
        MWSolanaWrapper.startLogin(callback);
    }

    /**
     * Type: SDK
     * Function: Guest login.
     */
    final public static void guestLogin(LoginListener listener){
        MWSolanaWrapper.guestLogin(listener);
    }

    /**
     * Type: SDK
     * Function: Logout.
     * @param listener
     */
    final public static void logout(BoolListener listener){
        MWSolanaWrapper.logout(listener);
    }

    /**
     * Type: SDK
     * Function: Login with email and get all response.
     */
    final public static void loginWithEmail(String email,String password,MirrorCallback callback){
        MWSolanaWrapper.loginWithEmail(email, password, callback);
    }

    /**
     * Type: SDK
     * Function: Open user's wallet page.
     */
    final public static void openWallet(MirrorCallback callback){
        MWSolanaWrapper.openWallet("",callback);
    }

    final public static void openWallet(String walletUrl,MirrorCallback callback){
        MWSolanaWrapper.openWallet(walletUrl,callback);
    }

    /**
     * Type: SDK
     * Function: Open market of this app.
     */
    final public static void openMarket(String marketUrl){
        MWSolanaWrapper.openMarket(marketUrl);
    }

    /**
     * Type: SDK
     * Function: Open any url
     */
    final public static void openUrl(String url){
        MWSolanaWrapper.openUrl(url);
    }

    /**
     * Type: SDK
     * Function: Fetch details of a user.
     * @param listener
     */
    final public static void fetchUser(FetchUserListener listener){
        MWSolanaWrapper.fetchUser(listener);
    }

    /**
     * Type: SDK
     * Function: Query some user and get details;
     * @param email
     * @param listener
     */
    final public static void queryUser(String email, FetchUserListener listener){
        MWSolanaWrapper.queryUser(email,listener);
    }

    /**
     * Type: SDK
     * Function: Checks whether the current user is logged in
     * @param listener
     */
    final public static void isLoggedIn(BoolListener listener){
        MWSolanaWrapper.isLoggedIn(listener);
    }

    //Wallet
    final public static void transferSPLToken(String toPublickey, float amount, String token_mint, int decimals, MirrorCallback mirrorCallback){
        MWSolanaWrapper.transferSPLToken(toPublickey, amount, token_mint, decimals, mirrorCallback);
    }

    final public static void transferSOL(String toPublicKey, float amount, TransferSOLListener listener){
        MWSolanaWrapper.transferSOL(toPublicKey, amount, listener);
    }

    final public static void getTokens(GetWalletTokenListener listener){
        MWSolanaWrapper.getTokens(listener);
    }

    final public static void getTokensByWallet(String walletAddress, GetWalletTokenListener walletTokenListener){
        MWSolanaWrapper.getTokensByWallet(walletAddress, walletTokenListener);
    }

    final public static void getTransactionsOfLoggedUser(int limit, String before, GetWalletTransactionListener walletTransactionListener){
        MWSolanaWrapper.getTransactionsOfLoggedUser(limit, before, walletTransactionListener);
    }

    final public static void getTransactionsByWallet(String walletAddress, int limit, MirrorCallback callback){
        MWSolanaWrapper.getTransactionsByWallet(walletAddress, limit, callback);
    }

    final public static void getTransactionBySignature(String signature, GetOneWalletTransactionBySigListener listener){
        MWSolanaWrapper.getTransactionBySignature(signature, listener);
    }

    //Metadata
    final public static void getCollectionInfo(List<String> collections, GetCollectionInfoListener listener){
        MWSolanaWrapper.getCollectionInfo(collections, listener);
    }

    final public static void getCollectionFilterInfo(String collectionAddress, GetCollectionFilterInfoListener listener){
        MWSolanaWrapper.getCollectionFilterInfo(collectionAddress, listener);
    }

    final public static void getCollectionSummary(List<String> collections, GetCollectionSummaryListener listener){
        MWSolanaWrapper.getCollectionSummary(collections, listener);
    }

    final public static void getNFTInfo(String mintAddress, MirrorCallback listener){
        MWSolanaWrapper.getNFTInfo(mintAddress, listener);
    }

    final public static void getNFTsByUnabridgedParams(String collection, int page, int page_size, String order_by, boolean desc, double sale, List<JSONObject> filter, GetNFTsListener listener){
        MWSolanaWrapper.getNFTsByUnabridgedParams(collection, page, page_size, order_by, desc, sale, filter, listener);
    }

    final public static void getNFTEvents(String mint_address, int page, int page_size, GetNFTEventsListener listener){
        MWSolanaWrapper.getNFTEvents(mint_address, page, page_size, listener);
    }

    final public static void searchNFTs(List<String> collections, String searchStr, SOLSearchNFTsListener listener){
        MWSolanaWrapper.searchNFTs(collections, searchStr, listener);
    }

    final public static void recommendSearchNFT(List<String> collections, SOLSearchNFTsListener listener){
        MWSolanaWrapper.recommendSearchNFT(collections, listener);
    }

    //Asset/NFT
    final public static void fetchNFTsByOwnerAddresses(List<String> owners, int limit, int offset, FetchByOwnerListener fetchByOwnerListener){
        MWSolanaWrapper.fetchNFTsByOwnerAddresses(owners, limit, offset, fetchByOwnerListener);
    }

    final public static void fetchNFTsByMintAddresses(List<String> mint_addresses, FetchNFTsListener fetchByMintAddressListener){
        MWSolanaWrapper.fetchNFTsByMintAddresses(mint_addresses, fetchByMintAddressListener);
    }

    final public static void fetchNFTsByCreatorAddresses(List<String> creators, int limit, int offset, FetchNFTsListener listener){
        MWSolanaWrapper.fetchNFTsByCreatorAddresses(creators, limit, offset, listener);
    }

    final public static void fetchNFTsByUpdateAuthorities(List<String> update_authorities, int limit, int offset, FetchNFTsListener listener){
        MWSolanaWrapper.fetchNFTsByUpdateAuthorities(update_authorities, limit, offset, listener);
    }

    final public static void fetchNFTMarketplaceActivity(String mint_address, FetchSingleNFTActivityListener fetchSingleNFTActivityListener){
        MWSolanaWrapper.fetchNFTMarketplaceActivity(mint_address, fetchSingleNFTActivityListener);
    }

    final public static void createVerifiedCollection(String name, String symbol, String detailUrl,String confirmation, CreateTopCollectionListener createTopCollectionListener){
        MWSolanaWrapper.createVerifiedCollection(name, symbol, detailUrl, confirmation, createTopCollectionListener);
    }

    //Confirmation
    final public static void checkStatusOfMinting(List<String> mintAddresses, CheckStatusOfMintingListener listener){
        MWSolanaWrapper.checkStatusOfMinting(mintAddresses, listener);
    }

    final public static void checkStatusOfTransactions(List<String> signatures, CheckStatusOfMintingListener listener){
        MWSolanaWrapper.checkStatusOfTransactions(signatures, listener);
    }

    final public static void transferNFT(String mint_address, String to_wallet_address, TransferNFTListener transferNFTListener){
        MWSolanaWrapper.transferNFT(mint_address, to_wallet_address, transferNFTListener);
    }

    final public static void listNFT(String mint_address, Double price, String confirmation, String auction_house, ListNFTListener listener){
        MWSolanaWrapper.listNFT(mint_address, price, confirmation, auction_house, listener);
    }

    final public static void cancelNFTListing(String mint_address, Double price,String confirmation, CancelListListener listener){
        MWSolanaWrapper.cancelNFTListing(mint_address, price, confirmation, listener);
    }

    final public static void buyNFT(String mint_address, Double price, BuyNFTListener buyNFTListener){
        MWSolanaWrapper.buyNFT(mint_address, price, buyNFTListener);
    }
}