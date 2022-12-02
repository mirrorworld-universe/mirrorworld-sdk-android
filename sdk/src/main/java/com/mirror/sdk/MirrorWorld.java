package com.mirror.sdk;

import android.app.Activity;
import android.util.Log;

import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.constant.MirrorSafeOptType;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.auth.LoginListener;
import com.mirror.sdk.listener.market.BuyNFTListener;
import com.mirror.sdk.listener.market.CancelListListener;
import com.mirror.sdk.listener.market.CreateTopCollectionListener;
import com.mirror.sdk.listener.market.FetchByOwnerListener;
import com.mirror.sdk.listener.market.FetchNFTsListener;
import com.mirror.sdk.listener.market.FetchSingleNFTActivityListener;
import com.mirror.sdk.listener.market.ListNFTListener;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.listener.market.TransferNFTListener;
import com.mirror.sdk.listener.market.UpdateListListener;
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MSimpleCallback;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.particle.MirrorSafeAPI;
import com.mirror.sdk.request.RequestMintNFT;

import java.util.ArrayList;
import java.util.List;

public class MirrorWorld {

    /**
     * Init SDK
     * @param activity
     * @param mirrorEnv
     */
    final public static void initMirrorWorld(Activity activity, MirrorEnv mirrorEnv){
        MirrorSDK.getInstance().InitSDK(activity,mirrorEnv);
    }

    /**
     * Login.
     */
    final public static void startLogin(LoginListener loginListener){
        MirrorSDK.getInstance().StartLogin(loginListener);
    }

    /**
     * Login with email and get all response.
     */
    final public static void loginWithEmail(String email,String password,MirrorCallback callback){
        MirrorSDK.getInstance().LoginWithEmail(email, password, callback);
    }

    /**
     * Fetch details of a user.
     * @param listener
     */
    final public static void fetchUser(FetchUserListener listener){
        MirrorSDK.getInstance().FetchUser(listener);
    }

    /**
     * Get tokens from the current user.
     * @param listener
     */
    final public static void getTokens(GetWalletTokenListener listener){
        MirrorSDK.getInstance().GetWalletTokens(listener);
    }

//    final public static void getTransactions(){
//        MirrorSDK.getInstance().Transactions();
//    }

//    final public static void getNFTs(){
//        MirrorSDK.getInstance().Fetch
//    }

//    final public static void getNFTs(){
//        MirrorSDK.getInstance().FetchNFT
//    }

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
        MirrorSDK.getInstance().TransferSOL(toPublicKey, amount, listener);
    }

//    final public static void transferSPLToken(){
//        MirrorSDK.getInstance().Tran
//    }

    /**
     * Create a collection for minting NFTs
     * @param name
     * @param symbol
     * @param detailUrl
     * @param createTopCollectionListener
     */
    final public static void createVerifiedCollection(String name, String symbol, String detailUrl, CreateTopCollectionListener createTopCollectionListener){
        MirrorSDK.getInstance().CreateVerifiedCollection(name, symbol, detailUrl, MirrorConfirmation.Default, createTopCollectionListener);
    }

    final public static void createVerifiedCollection(String name, String symbol, String detailUrl,String confirmation, CreateTopCollectionListener createTopCollectionListener){
        MirrorSDK.getInstance().CreateVerifiedCollection(name, symbol, detailUrl, confirmation, createTopCollectionListener);
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
        RequestMintNFT requestMintNFT = new RequestMintNFT();
        requestMintNFT.collection_mint = collection_mint;
        requestMintNFT.name = name;
        requestMintNFT.symbol = symbol;
        requestMintNFT.url = detailUrl;
        requestMintNFT.confirmation = confirmation;

        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.MintNFT, "test", 0,requestMintNFT, new MirrorCallback() {
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
//        MirrorSDK.getInstance().UpdateNFTListing(mint_address, price, confirmation, listener);
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
        MirrorSDK.getInstance().ListNFT(mint_address, price, confirmation, auction_house, listener);
    }

    /**
     * Buy a NFT
     * @param mint_address
     * @param price
     * @param buyNFTListener
     */
    final public static void buyNFT(String mint_address, Double price, BuyNFTListener buyNFTListener){
        MirrorSDK.getInstance().BuyNFT(mint_address, price, buyNFTListener);
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
        MirrorSDK.getInstance().CancelNFTListing(mint_address, price, confirmation, listener);
    }

    /**
     * Transfer a NFT to another wallet on Solana
     * @param mint_address
     * @param to_wallet_address
     * @param transferNFTListener
     */
    final public static void transferNFT(String mint_address, String to_wallet_address, TransferNFTListener transferNFTListener){
        MirrorSDK.getInstance().TransferNFTToAnotherSolanaWallet(mint_address, to_wallet_address, transferNFTListener);
    }

    /**
     * Fetch NFTs
     * @param mint_addresses
     * @param fetchByMintAddressListener
     */
    final public static void fetchNFTsByMintAddresses(List<String> mint_addresses, FetchNFTsListener fetchByMintAddressListener){
        MirrorSDK.getInstance().FetchNFTsByMintAddresses(mint_addresses, fetchByMintAddressListener);
    }

    final public static void fetchNFTsByCreatorAddresses(List<String> creators, Double limit, Double offset, FetchNFTsListener listener){
        MirrorSDK.getInstance().FetchNFTsByCreatorAddresses(creators, limit, offset, listener);
    }

    final public static void fetchNFTsByUpdateAuthorities(List<String> update_authorities, Double limit, Double offset, FetchNFTsListener listener){
        MirrorSDK.getInstance().FetchNFTsByUpdateAuthorities(update_authorities, limit, offset, listener);
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
