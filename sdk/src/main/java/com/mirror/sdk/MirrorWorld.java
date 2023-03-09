package com.mirror.sdk;

import android.app.Activity;

import com.mirror.sdk.constant.MirrorChains;
import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.constant.MirrorSafeOptType;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.auth.LoginListener;
import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingListener;
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
import com.mirror.sdk.listener.metadata.GetCollectionFilterInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionSummaryListener;
import com.mirror.sdk.listener.metadata.GetNFTEventsListener;
import com.mirror.sdk.listener.metadata.GetNFTRealPriceListener;
import com.mirror.sdk.listener.metadata.GetNFTsListener;
import com.mirror.sdk.listener.metadata.SearchNFTsListener;
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.listener.wallet.GetOneWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.particle.MirrorSafeAPI;
import com.mirror.sdk.request.ApproveReqUpdateNFTProperties;
import com.mirror.sdk.request.ReqBuyNFT;
import com.mirror.sdk.request.ReqCancelListing;
import com.mirror.sdk.request.ReqCreateCollection;
import com.mirror.sdk.request.ReqListingNFT;
import com.mirror.sdk.request.ReqMintNFT;
import com.mirror.sdk.request.ReqTransNFT;
import com.mirror.sdk.request.ReqTransSOL;
import com.mirror.sdk.request.ReqTransSPLToken;
import com.mirror.sdk.utils.MirrorGsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MirrorWorld {
    /**
     * Type: SDK
     * Function: Init SDK
     * @param activity
     * @param mirrorEnv
     */
    final public static void initMirrorWorld(Activity activity, String apiKey, MirrorChains chain, MirrorEnv mirrorEnv){
        if(apiKey.isEmpty()){
            MirrorSDK.logError("APIKey is invalid!");
            return;
        }
        MirrorSDK.getInstance().InitSDK(activity,mirrorEnv,chain);
        MirrorSDK.getInstance().SetApiKey(apiKey);
    }
    final public static void initMirrorWorld(Activity activity, String apiKey, MirrorChains chain, int env){
        MirrorSDK.getInstance().SetApiKey(apiKey);
        MirrorEnv data=MirrorEnv.values()[env];
        MirrorSDK.getInstance().InitSDK(activity,data,chain);
    }

    /**
     * Type: SDK
     * Function: Get now environment.
     * @return
     */
    final public static MirrorEnv getEnvironment(){
        return MirrorSDK.getInstance().getEnv();
    }

    /**
     * Type: SDK
     * Function: Show the sdk flow in console if true.
     * @param useDebugMode
     */
    final public static void setDebug(boolean useDebugMode){
        MirrorSDK.getInstance().SetDebug(useDebugMode);
    }

    /**
     * Type: SDK
     * Function: Login.
     */
    final public static void startLogin(LoginListener loginListener){
        MirrorSDK.getInstance().openLoginPage(loginListener);
    }

    final public static void startLogin(MirrorCallback callback){
        MirrorSDK.getInstance().openLoginPage(callback);
    }

    /**
     * Type: SDK
     * Function: Guest login.
     */
    final public static void guestLogin(LoginListener listener){
        MirrorSDK.getInstance().guestLogin(listener);
    }

    /**
     * Type: SDK
     * Function: Logout.
     * @param listener
     */
    final public static void logout(BoolListener listener){
        MirrorSDK.getInstance().logout(listener);
    }

    /**
     * Type: SDK
     * Function: Login with email and get all response.
     */
    final public static void loginWithEmail(String email,String password,MirrorCallback callback){
        MirrorSDK.getInstance().LoginWithEmail(email, password, callback);
    }

    /**
     * Type: SDK
     * Function: Open user's wallet page.
     */
    final public static void openWallet(MirrorCallback callback){
        MirrorSDK.getInstance().OpenWallet("",callback);
    }

    final public static void openWallet(String walletUrl,MirrorCallback callback){
        MirrorSDK.getInstance().OpenWallet(walletUrl,callback);
    }

    /**
     * Type: SDK
     * Function: Open market of this app.
     */
    final public static void openMarket(String marketUrl){
        MirrorSDK.getInstance().openMarket(marketUrl);
    }

    /**
     * Type: SDK
     * Function: Open any url
     */
    final public static void openUrl(String url){
        MirrorSDK.getInstance().openUrl(url);
    }

    /**
     * Type: SDK
     * Function: Fetch details of a user.
     * @param listener
     */
    final public static void fetchUser(FetchUserListener listener){
        MirrorSDK.getInstance().FetchUser(listener);
    }

    /**
     * Type: SDK
     * Function: Query some user and get details;
     * @param email
     * @param listener
     */
    final public static void queryUser(String email, FetchUserListener listener){
        MirrorSDK.getInstance().QueryUser(email,listener);
    }

    /**
     * Type: SDK
     * Function: Checks whether the current user is logged in
     * @param listener
     */
    final public static void isLoggedIn(BoolListener listener){
        MirrorSDK.getInstance().CheckAuthenticated(listener);
    }

    /**
     * Type:Wallet
     * Function: Get tokens from the current user.
     * @param listener
     */
    final public static void getTokens(GetWalletTokenListener listener){
        MirrorSDK.getInstance().GetWalletTokens(listener);
    }

    /**
     * Type: wallet
     * Function: Get tokens of a wallet address
     * @param walletAddress
     * @param walletTokenListener
     */
    final public static void getTokensByWallet(String walletAddress, GetWalletTokenListener walletTokenListener){
        MirrorSDK.getInstance().GetWalletTokensByWallet(walletAddress, walletTokenListener);
    }

    /**
     * Type: Wallet
     * Function: Get transactions of the logged user.
     * @param before
     * @param walletTransactionListener
     */
    final public static void getTransactionsOfLoggedUser(String before, GetWalletTransactionListener walletTransactionListener){
        MirrorSDK.getInstance().Transactions(0, before, walletTransactionListener);
    }
    final public static void getTransactionsOfLoggedUser(int limit, String before, GetWalletTransactionListener walletTransactionListener){
        MirrorSDK.getInstance().Transactions(limit, before, walletTransactionListener);
    }

    /**
     * Type: Wallet
     * Function: Get transactions from a wallet address.
     * @param walletAddress
     * @param limit
     */
    final public static void getTransactionsByWallet(String walletAddress, int limit, MirrorCallback callback){
        MirrorSDK.getInstance().getTransactionsByWallet(walletAddress,limit,callback);
    }

    /**
     * Type: Wallet
     * Function: Get transaction with its signature.
     * @param signature
     * @param listener
     */
    final public static void getTransactionBySignature(String signature, GetOneWalletTransactionBySigListener listener){
        MirrorSDK.getInstance().GetTransactionBySignature(signature, listener);
    }


    /**
     * Type: Wallet
     * Function: Transfer sol to another wallet
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
     * Type: Wallet
     * Function: Get transaction of sol transfering
     * @param toPublickey
     * @param amount
     * @param listener
     */
//    final public static void getTransactionOfTransferSOL(String toPublickey,float amount,GetWalletTransactionListener listener){
//        MirrorSDK.getInstance().getTransactionOfTransferSOL(toPublickey, amount, listener);
//    }
//
//    final public static void getTransactionsOfTransferToken(String toPublickey,float amount,String tokenMint,int decimals,GetWalletTransactionListener listener){
//        MirrorSDK.getInstance().getTransactionOfTransferToken(toPublickey, amount, tokenMint, decimals, listener);
//    }
    /**
     * Type: Wallet
     * Function: Transfer SPL token to a recipient
     * @param toPublickey
     * @param amount
     * @param token_mint
     * @param decimals
     * @param mirrorCallback
     */
    final public static void transferSPLToken(String toPublickey, float amount, String token_mint, int decimals, MirrorCallback mirrorCallback){

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
     * Type: Wallet
     * Function: transferETH
     * @param nonce
     * @param gasPrice
     * @param gasLimit
     * @param to
     * @param amount
     * @param mirrorCallback
     */
    final public static void transferETH(String nonce, String gasPrice, String gasLimit, String to,int amount, MirrorCallback mirrorCallback){
        MirrorSDK.getInstance().TransferETH(nonce, gasPrice, gasLimit, to, amount, mirrorCallback);
    }


    /**
     * Type: Asset/NFT
     * Fetch NFTs
     * @param mint_addresses
     * @param fetchByMintAddressListener
     */
    final public static void fetchNFTsByMintAddresses(List<String> mint_addresses, FetchNFTsListener fetchByMintAddressListener){
        MirrorSDK.getInstance().FetchNFTsByMintAddresses(mint_addresses, fetchByMintAddressListener);
    }

    /**
     * Type: Asset/NFT
     * Function: Get details of a single NFT.
     * @param mint_address
     * @param fetchSingleNFT
     */
    final public static void getNFTDetails(String mint_address, String mint_id, FetchSingleNFTListener fetchSingleNFT){
        MirrorSDK.getInstance().GetNFTDetails(mint_address, mint_id, fetchSingleNFT);
    }

    /**
     * Type: Asset/NFT
     * Function: Get NFTs by owner's wallet address
     * @param ownerWalletAddress
     * @param listener
     */
    final public static void getNFTsOwnedByAddress(String ownerWalletAddress, FetchNFTsListener listener){
        List<String> wallets = new ArrayList<>();
        wallets.add(ownerWalletAddress);
        MirrorSDK.getInstance().FetchNFTsByMintAddresses(wallets,listener);
    }

    /**
     * Type: Asset/Mint
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
     * Type: Asset/Mint
     * Function: Mint NFT
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
     * Type: Asset/Auction
     * Update a NFT's info
     * @param mintAddress
     * @param name
     * @param symbol
     * @param updateAuthority
     * @param NFTJsonUrl
     * @param seller_fee_basis_points
     * @param confirmation
     * @param mintNFTListener
     */
    final public static void updateNFTProperties(String mintAddress, String name, String symbol, String updateAuthority, String NFTJsonUrl,int seller_fee_basis_points, String confirmation, MintNFTListener mintNFTListener){
        ApproveReqUpdateNFTProperties req = new ApproveReqUpdateNFTProperties();
        req.mint_address = mintAddress;
        req.name = name;
        req.symbol = symbol;
        req.update_authority = updateAuthority;
        req.seller_fee_basis_points = seller_fee_basis_points;
        req.confirmation = confirmation;

        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.UpdateNFT, "Update NFT", 0, params, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().updateNFTProperties(mintAddress, name, symbol, updateAuthority, NFTJsonUrl, seller_fee_basis_points, confirmation, mintNFTListener);
            }
        });
    }

    final public static void updateNFTProperties(String mintAddress, String name, String symbol, String updateAuthority, String NFTJsonUrl,int seller_fee_basis_points, MintNFTListener mintNFTListener){
        ApproveReqUpdateNFTProperties req = new ApproveReqUpdateNFTProperties();
        req.mint_address = mintAddress;
        req.name = name;
        req.symbol = symbol;
        req.update_authority = updateAuthority;
        req.seller_fee_basis_points = seller_fee_basis_points;
        req.confirmation = MirrorConfirmation.Confirmed;

        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.UpdateNFT, "Update NFT", 0, params, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().updateNFTProperties(mintAddress, name, symbol, updateAuthority, NFTJsonUrl, seller_fee_basis_points, MirrorConfirmation.Confirmed, mintNFTListener);
            }
        });
    }


    /**
     * Type: Asset/Auction
     * Function: Buy a NFT from marketplace.
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
            public void callback(String nothing) {
                MirrorSDK.getInstance().BuyNFT(mint_address, price, buyNFTListener);
            }
        });
    }

    /**
     * Type: Asset/Auction
     * Function: List a NFT to marketplace.
     * @param mint_address
     * @param price
     * @param listener
     */
    final public static void listNFT(String mint_address, Double price, ListNFTListener listener){
        MirrorSDK.getInstance().ListNFT(mint_address, price, MirrorConfirmation.Default, listener);
    }
    final public static void listNFT(String mint_address, Double price, String confirmation, String auction_house, ListNFTListener listener){
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
     * Type: Asset/Auction
     * Function: Update a nft which is listing
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
     * Type: Asset/Auction
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
     * Type: Asset/Auction
     * Function: Transfer a NFT to another wallet on Solana
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
     * Type: Confirmation
     * Function: Check status of minting and transactions
     * @param mintAddresses
     * @param listener
     */
    final public static void checkStatusOfMinting(List<String> mintAddresses, CheckStatusOfMintingListener listener){
        MirrorSDK.getInstance().checkStatusOfMinting(mintAddresses, listener);
    }

    /**
     * Type: Confirmation
     * Function: Get status of transactions by transactions' signatures.
     * @param signatures
     * @param listener
     */
    final public static void checkStatusOfTransactions(List<String> signatures, CheckStatusOfMintingListener listener){
        MirrorSDK.getInstance().checkStatusOfTransactions(signatures, listener);
    }

    /**
     * Type: Marketplace
     * Function: Fetch NFTs by creator addresses.
     * @param creators
     * @param limit
     * @param offset
     * @param listener
     */
    final public static void fetchNFTsByCreatorAddresses(List<String> creators, int limit, int offset, FetchNFTsListener listener){
        MirrorSDK.getInstance().FetchNFTsByCreatorAddresses(creators, limit, offset, listener);
    }

    /**
     * Type: Marketplace
     * Function: Fetch NFTs by authorities
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
     * Type: Marketplace
     * Function: Fetch NFTs By Owner Addresses
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
     * Type: Marketplace
     * Function: Fetch NFT's activity
     * @param mint_address
     * @param fetchSingleNFTActivityListener
     */
    final public static void fetchNFTMarketplaceActivity(String mint_address, FetchSingleNFTActivityListener fetchSingleNFTActivityListener){
        MirrorSDK.getInstance().FetchNFTMarketplaceActivity(mint_address, fetchSingleNFTActivityListener);
    }

    /**
     * Type: Metadata
     * Function: Get info of collections.
     * @param collections
     * @param listener
     */
    final public static void getCollectionInfo(List<String> collections, GetCollectionInfoListener listener){
        MirrorSDK.getInstance().GetCollectionInfo(collections, listener);
    }

    /**
     * Type: Metadata
     * Function: Get collection filters from a collection.
     * @param collectionAddress
     * @param listener
     */
    final public static void getCollectionFilterInfo(String collectionAddress, GetCollectionFilterInfoListener listener){
        MirrorSDK.getInstance().GetCollectionFilterInfo(collectionAddress, listener);
    }

    /**
     * Type: Metadata
     * Function: Get collection summary.
     * @param collections
     * @param listener
     */
    final public static void getCollectionSummary(List<String> collections, GetCollectionSummaryListener listener){
        MirrorSDK.getInstance().getCollectionsSummary(collections, listener);
    }

    /**
     * Type: Metadata
     * Function: Get NFT info
     * @param mintAddress
     * @param listener
     */
    final public static void getNFTInfoOnSolana(String mintAddress, MirrorCallback listener){
        MirrorSDK.getInstance().GetNFTInfoOnSolana(mintAddress, listener);
    }

    final public static void getNFTInfoOnMultiChain(String mintAddress, String tokenID, MirrorCallback listener){
        MirrorSDK.getInstance().GetNFTInfoOnMultiChain(mintAddress, tokenID, listener);
    }

    /**
     * Type: Metadata
     * Function: Get NFTs from a collection.
     * @param collection
     * @param page
     * @param page_size
     * @param order_by
     * @param desc
     * @param sale
     * @param filter
     * @param listener
     */
    final public static void getNFTsByUnabridgedParamsOnSolana(String collection, int page, int page_size, String order_by, boolean desc, double sale, List<JSONObject> filter, GetNFTsListener listener){
        MirrorSDK.getInstance().getNFTsByUnabridgedParamsOnSolana(collection, page, page_size, order_by, desc, sale, filter, listener);
    }

    final public static void getNFTsByUnabridgedParamsOnMultiChain(String collection, int page, int page_size, String order_by, boolean desc, double sale, List<JSONObject> filter, GetNFTsListener listener){
        MirrorSDK.getInstance().getNFTsByUnabridgedParamsOnMultiChain(collection, page, page_size, order_by, desc, sale, filter, listener);
    }

    /**
     * Type: Metadata
     * Function: Get NFT events with a NFT mint address.
     * @param mint_address
     * @param page
     * @param page_size
     * @param listener
     */
    final public static void getNFTEventsOnSolana(String mint_address, int page, int page_size, GetNFTEventsListener listener){
        MirrorSDK.getInstance().GetNFTEventsOnSolana(mint_address, page, page_size, listener);
    }

    final public static void getNFTEventsOnMultiChain(String contract, int page, int page_size, GetNFTEventsListener listener){
        MirrorSDK.getInstance().GetNFTEventsOnMultiChain(contract, page, page_size, listener);
    }

    /**
     * Type: Metadata
     * Function: Search NFTs which fitting given search string.
     * @param collections
     * @param searchStr
     * @param listener
     */
    final public static void searchNFTs(List<String> collections, String searchStr, SearchNFTsListener listener){
        MirrorSDK.getInstance().SearchNFTs(collections, searchStr, listener);
    }

    /**
     * Type: Metadata
     * Function: Get recommend NFTs from server.
     * @param collections
     * @param listener
     */
    final public static void recommendSearchNFT(List<String> collections, SearchNFTsListener listener){
        MirrorSDK.getInstance().RecommendSearchNFT(collections, listener);
    }

    /**
     * Type: Metadata
     * Function: Get NFT real price.
     * @param price
     * @param fee
     * @param listener
     */
    final public static void getNFTRealPrice(String price, int fee, GetNFTRealPriceListener listener){
        MirrorSDK.getInstance().GetNFTRealPrice(price, fee, listener);
    }
}
