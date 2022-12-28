package com.mirror.sdk;

import android.app.Activity;
import android.util.Log;

import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.constant.MirrorSafeOptType;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.auth.LoginListener;
import com.mirror.sdk.listener.auth.QueryUserListener;
import com.mirror.sdk.listener.market.BuyNFTListener;
import com.mirror.sdk.listener.market.CancelListListener;
import com.mirror.sdk.listener.market.CreateTopCollectionListener;
import com.mirror.sdk.listener.market.FetchByOwnerListener;
import com.mirror.sdk.listener.market.FetchNFTsListener;
import com.mirror.sdk.listener.market.FetchSingleNFTActivityListener;
import com.mirror.sdk.listener.market.FetchSingleNFTListener;
import com.mirror.sdk.listener.market.ListNFTListener;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.listener.market.TransferNFTListener;
import com.mirror.sdk.listener.market.UpdateListListener;
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MSimpleCallback;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.listener.wallet.GetOneWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.particle.MirrorSafeAPI;
import com.mirror.sdk.request.ReqBuyNFT;
import com.mirror.sdk.request.ReqCancelListing;
import com.mirror.sdk.request.ReqCreateCollection;
import com.mirror.sdk.request.ReqListingNFT;
import com.mirror.sdk.request.ReqMintNFT;
import com.mirror.sdk.request.ReqTransNFT;
import com.mirror.sdk.request.ReqTransSOL;
import com.mirror.sdk.request.ReqTransSPLToken;
import com.mirror.sdk.request.ReqUpdateListingNFT;
import com.mirror.sdk.utils.MirrorGsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MirrorWorld {

    /**
     * Init SDK
     * @param activity
     * @param mirrorEnv
     */
    final public static void initMirrorWorld(Activity activity,String apiKey, MirrorEnv mirrorEnv){
        MirrorSDK.getInstance().InitSDK(activity,mirrorEnv);
        MirrorSDK.getInstance().SetApiKey(apiKey);
    }
    final public static void initMirrorWorld(Activity activity,String apiKey, int env){
        MirrorSDK.getInstance().SetApiKey(apiKey);
        MirrorEnv data=MirrorEnv.values()[env];
        MirrorSDK.getInstance().InitSDK(activity,data);
    }

    /**
     * Show the sdk flow in console if true.
     * @param useDebugMode
     */
    final public static void setDebug(boolean useDebugMode){
        MirrorSDK.getInstance().SetDebug(useDebugMode);
    }

    /**
     * Login.
     */
    final public static void startLogin(LoginListener loginListener){
        MirrorSDK.getInstance().openLoginPage(loginListener);
    }

    final public static void startLogin(MirrorCallback callback){
        MirrorSDK.getInstance().openLoginPage(callback);
    }

    /**
     * Logout.
     * @param listener
     */
    final public static void logout(BoolListener listener){
        MirrorSDK.getInstance().logout(listener);
    }

    /**
     * Login with email and get all response.
     */
    final public static void loginWithEmail(String email,String password,MirrorCallback callback){
        MirrorSDK.getInstance().LoginWithEmail(email, password, callback);
    }

    /**
     * Open user's wallet page.
     */
    final public static void openWallet(MirrorCallback callback){
        MirrorSDK.getInstance().OpenWallet(callback);
    }

    /**
     * Open market of this app.
     */
    final public static void openMarket(){
        MirrorSDK.getInstance().openMarket();
    }

    /**
     * Fetch details of a user.
     * @param listener
     */
    final public static void fetchUser(FetchUserListener listener){
        MirrorSDK.getInstance().FetchUser(listener);
    }

    /**
     * Query some user and get details;
     * @param email
     * @param listener
     */
    final public static void queryUser(String email, FetchUserListener listener){
        MirrorSDK.getInstance().QueryUser(email,listener);
    }

    /**
     * Get tokens from the current user.
     * @param listener
     */
    final public static void getTokens(GetWalletTokenListener listener){
        MirrorSDK.getInstance().GetWalletTokens(listener);
    }

    /**
     * Get transactions of the logged user.
     * @param before
     * @param walletTransactionListener
     */
    final public static void getTransactions(String before, GetWalletTransactionListener walletTransactionListener){
        MirrorSDK.getInstance().Transactions(0, before, walletTransactionListener);
    }
    final public static void getTransactions(int limit, String before, GetWalletTransactionListener walletTransactionListener){
        MirrorSDK.getInstance().Transactions(limit, before, walletTransactionListener);
    }

    /**
     * Get transaction with its signature.
     * @param signature
     * @param listener
     */
    final public static void getTransaction(String signature, GetOneWalletTransactionBySigListener listener){
        MirrorSDK.getInstance().GetTransactionBySignature(signature, listener);
    }

//    final public static void getNFTs(){
//        MirrorSDK.getInstance().Fetch
//    }

//    final public static void getNFTs(){
//        MirrorSDK.getInstance().FetchNFT
//    }

    /**
     * Get details of a single NFT.
     * @param mint_address
     * @param fetchSingleNFT
     */
    final public static void getNFTDetails(String mint_address, FetchSingleNFTListener fetchSingleNFT){
        MirrorSDK.getInstance().GetNFTDetails(mint_address, fetchSingleNFT);
    }

    /**
     * Get NFTs by owner's wallet address
     * @param ownerWalletAddress
     * @param listener
     */
    final public static void getNFTsOwnedByAddress(String ownerWalletAddress, FetchNFTsListener listener){
        List<String> wallets = new ArrayList<>();
        wallets.add(ownerWalletAddress);
        MirrorSDK.getInstance().FetchNFTsByMintAddresses(wallets,listener);
    }

    /**
     * Transfer sol to another wallet
     * @param toPublicKey
     * @param amount
     * @param listener
     */
    final public static void transferSOL(String toPublicKey, float amount, TransferSOLListener listener){

        ReqTransSOL requestMintNFT = new ReqTransSOL();
        requestMintNFT.toPublicKey = toPublicKey;
        requestMintNFT.amount = amount;

        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(requestMintNFT);
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.TransferSol, "TransferSol", 0, params, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().TransferSOL(toPublicKey, amount, listener);
            }
        });
    }

    /**
     * Transfer SPL token to a recipient
     * @param toPublickey
     * @param amount
     * @param token_mint
     * @param decimals
     * @param mirrorCallback
     */
    final public static void transferSPLToken(String toPublickey, float amount, String token_mint, float decimals, MirrorCallback mirrorCallback){

        ReqTransSPLToken req = new ReqTransSPLToken();
        req.toPublickey = toPublickey;
        req.amount = amount;
        req.token_mint = token_mint;
        req.decimals = decimals;

        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.TransferSPLToken, "TransferSPLToken", 0, params, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().TransferToken(toPublickey, amount, token_mint, decimals, mirrorCallback);
            }
        });
    }

    /**
     * Create a collection for minting NFTs
     * @param name
     * @param symbol
     * @param detailUrl
     * @param createTopCollectionListener
     */
    final public static void createVerifiedCollection(String name, String symbol, String detailUrl, CreateTopCollectionListener createTopCollectionListener){
        createVerifiedCollection(name, symbol, detailUrl, MirrorConfirmation.Default, createTopCollectionListener);
    }

    final public static void createVerifiedCollection(String name, String symbol, String detailUrl,String confirmation, CreateTopCollectionListener createTopCollectionListener){
        ReqCreateCollection req = new ReqCreateCollection();
        req.name = name;
        req.symbol = symbol;
        req.detailUrl = detailUrl;
        req.confirmation = confirmation;

        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.CreateCollection, "CreateCollection", 0, params, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().CreateVerifiedCollection(name, symbol, detailUrl, confirmation, createTopCollectionListener);
            }
        });
    }

    /**
     * Mint NFT
     * @param collection_mint
     * @param name
     * @param symbol
     * @param detailUrl
     * @param mintNFTListener
     */
    final public static void mintNFT(String collection_mint, String name, String symbol, String detailUrl,MintNFTListener mintNFTListener){
        mintNFT(collection_mint,name,symbol,detailUrl, MirrorConfirmation.Default,mintNFTListener);
    }

    final public static void mintNFT(String collection_mint, String name, String symbol, String detailUrl, String confirmation,MintNFTListener mintNFTListener){
        ReqMintNFT requestMintNFT = new ReqMintNFT();
        requestMintNFT.collection_mint = collection_mint;
        requestMintNFT.name = name;
        requestMintNFT.symbol = symbol;
        requestMintNFT.url = detailUrl;
        requestMintNFT.confirmation = confirmation;

        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(requestMintNFT);
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.MintNFT, "mint nft", 0, params, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().MintNFT(collection_mint,name,symbol,detailUrl,confirmation,mintNFTListener);
            }
        });
    }

    /**
     * Update a listing NFT
     * @param mint_address
     * @param price
     * @param listener
     */
//    final public static void updateNFT(String mint_address, Double price, UpdateListListener listener){
//        MirrorSDK.getInstance().UpdateNFTListing(mint_address, price, MirrorConfirmation.Default, listener);
//    }
//
//    final public static void updateNFT(String mint_address, Double price,String confirmation, UpdateListListener listener){
//        ReqUpdateListingNFT req = new ReqUpdateListingNFT();
//        req.mint_address = mint_address;
//        req.price = price;
//        req.confirmation = confirmation;
//
//        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
//        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.UpdateListing, "UpdateListing", 0, params, new MirrorCallback() {
//            @Override
//            public void callback(String nothing) {
//                MirrorSDK.getInstance().UpdateNFTListing(mint_address, price, confirmation, listener);
//            }
//        });
//    }

    /**
     *
     * @param mint_address
     * @param price
     * @param listener
     */
    final public static void listingNFT(String mint_address, Double price,ListNFTListener listener){
        MirrorSDK.getInstance().ListNFT(mint_address, price, MirrorConfirmation.Default, listener);
    }
    final public static void listingNFT(String mint_address, Double price, String confirmation,String auction_house, ListNFTListener listener){
        ReqListingNFT req = new ReqListingNFT();
        req.mint_address = mint_address;
        req.price = price;
        req.confirmation = confirmation;
        req.auction_house = auction_house;

        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.ListNFT, "ListNFT", 0, params, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().ListNFT(mint_address, price, confirmation, auction_house, listener);
            }
        });
    }

    /**
     * Buy a NFT
     * @param mint_address
     * @param price
     * @param buyNFTListener
     */
    final public static void buyNFT(String mint_address, Double price, BuyNFTListener buyNFTListener){
        ReqBuyNFT req = new ReqBuyNFT();
        req.mint_address = mint_address;
        req.price = price;

        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.BuyNFT, "BuyNFT", 0, params, new MirrorCallback() {
            @Override
            public void callback(String nothing) {MirrorSDK.getInstance().BuyNFT(mint_address, price, buyNFTListener);
            }
        });
    }

    /**
     * Update a nft which is listing
     * @param mint_address
     * @param price
     * @param listener
     */
    final public static void updateNFTListing(String mint_address, Double price, UpdateListListener listener){
        MirrorSDK.getInstance().UpdateNFTListing(mint_address, price, MirrorConfirmation.Default, listener);
    }
    final public static void updateNFTListing(String mint_address, Double price,String confirmation, UpdateListListener listener){
        MirrorSDK.getInstance().UpdateNFTListing(mint_address, price, confirmation, listener);
    }

    /**
     * Cancel NFT listing
     * @param mint_address
     * @param price
     * @param listener
     */
    final public static void cancelNFTListing(String mint_address, Double price, CancelListListener listener){
        MirrorSDK.getInstance().CancelNFTListing(mint_address, price, MirrorConfirmation.Default, listener);
    }

    final public static void cancelNFTListing(String mint_address, Double price,String confirmation, CancelListListener listener){
        ReqCancelListing req = new ReqCancelListing();
        req.mint_address = mint_address;
        req.price = price;
        req.confirmation = confirmation;

        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.CancelListing, "CancelListing", 0, params, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().CancelNFTListing(mint_address, price, confirmation, listener);
            }
        });
    }

    /**
     * Transfer a NFT to another wallet on Solana
     * @param mint_address
     * @param to_wallet_address
     * @param transferNFTListener
     */
    final public static void transferNFT(String mint_address, String to_wallet_address, TransferNFTListener transferNFTListener){
        ReqTransNFT req = new ReqTransNFT();
        req.mint_address = mint_address;
        req.to_wallet_address = to_wallet_address;

        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.TransferNFT, "TransferNFT", 0, params, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().TransferNFTToAnotherSolanaWallet(mint_address, to_wallet_address, transferNFTListener);
            }
        });
    }

    /**
     * Fetch NFTs
     * @param mint_addresses
     * @param fetchByMintAddressListener
     */
    final public static void fetchNFTsByMintAddresses(List<String> mint_addresses, FetchNFTsListener fetchByMintAddressListener){
        MirrorSDK.getInstance().FetchNFTsByMintAddresses(mint_addresses, fetchByMintAddressListener);
    }

    /**
     * fetchNFTsByCreatorAddresses
     * @param creators
     * @param limit
     * @param offset
     * @param listener
     */
    final public static void fetchNFTsByCreatorAddresses(List<String> creators, int limit, int offset, FetchNFTsListener listener){
        MirrorSDK.getInstance().FetchNFTsByCreatorAddresses(creators, limit, offset, listener);
    }

    /**
     * Fetch NFTs by authorities
     * @param update_authorities
     * @param listener
     */
    final public static void fetchNFTsByUpdateAuthorities(List<String> update_authorities,FetchNFTsListener listener){
        MirrorSDK.getInstance().FetchNFTsByUpdateAuthorities(update_authorities, listener);
    }
    final public static void fetchNFTsByUpdateAuthorities(List<String> update_authorities, int limit, int offset, FetchNFTsListener listener){
        MirrorSDK.getInstance().FetchNFTsByUpdateAuthorities(update_authorities, limit, offset, listener);
    }

    /**
     * Fetch NFTs By Owner Addresses
     * @param owners
     * @param fetchByOwnerListener
     */
    final public static void fetchNFTsByOwnerAddresses(List<String> owners, FetchByOwnerListener fetchByOwnerListener){
        MirrorSDK.getInstance().FetchNFTsByOwnerAddresses(owners, fetchByOwnerListener);
    }
    final public static void fetchNFTsByOwnerAddresses(List<String> owners, int limit, int offset, FetchByOwnerListener fetchByOwnerListener){
        MirrorSDK.getInstance().FetchNFTsByOwnerAddresses(owners, limit, offset, fetchByOwnerListener);
    }

    /**
     * Fetch NFT's activity
     * @param mint_address
     * @param fetchSingleNFTActivityListener
     */
    final public static void fetchNFTMarketplaceActivity(String mint_address, FetchSingleNFTActivityListener fetchSingleNFTActivityListener){
        MirrorSDK.getInstance().FetchNFTMarketplaceActivity(mint_address, fetchSingleNFTActivityListener);
    }

//    final public static void createMarketplace(){
//        MirrorSDK.getInstance().CreateM
//    }

//    final public static void updateMarketplace(){
//
//    }

//    final public static void queryMarketplaces(){
//
//    }

//    final public static void user(){
//
//    }

    final public static MirrorEnv clusterEnv(){
        return MirrorSDK.getInstance().env;
    }

//    final public static String wallet(){
//
//    }

//    final public static void tokens(){
//
//    }

//    final public static void transactions(){
//        MirrorSDK.getInstance().Transactions();
//    }

//    /**
//     * Get current user's nfts
//     */
//    final public static void nfts(){
//
//    }

    /**
     * Checks whether the current user is logged in
     * @param listener
     */
    final public static void isLoggedIn(BoolListener listener){
        MirrorSDK.getInstance().CheckAuthenticated(listener);
    }
}
