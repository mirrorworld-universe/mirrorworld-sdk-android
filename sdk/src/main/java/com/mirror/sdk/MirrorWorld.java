//package com.mirror.sdk;
//
//import android.app.Activity;
//
//import com.google.gson.reflect.TypeToken;
//import com.mirror.sdk.constant.MirrorChains;
//import com.mirror.sdk.constant.MirrorConfirmation;
//import com.mirror.sdk.constant.MirrorEnv;
//import com.mirror.sdk.constant.MirrorResCode;
//import com.mirror.sdk.constant.MirrorSafeOptType;
//import com.mirror.sdk.listener.auth.FetchUserListener;
//import com.mirror.sdk.listener.auth.LoginListener;
//import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingListener;
//import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingResponse;
//import com.mirror.sdk.listener.market.BuyNFTListener;
//import com.mirror.sdk.listener.market.CancelListListener;
//import com.mirror.sdk.listener.market.CreateTopCollectionListener;
//import com.mirror.sdk.listener.market.FetchNFTsListener;
//import com.mirror.sdk.listener.market.FetchSingleNFTActivityListener;
//import com.mirror.sdk.listener.market.ListNFTListener;
//import com.mirror.sdk.listener.market.MintNFTListener;
//import com.mirror.sdk.listener.market.TransferNFTListener;
//import com.mirror.sdk.listener.market.UpdateListListener;
//import com.mirror.sdk.listener.metadata.GetCollectionFilterInfoListener;
//import com.mirror.sdk.listener.metadata.GetCollectionInfoListener;
//import com.mirror.sdk.listener.metadata.GetCollectionSummaryListener;
//import com.mirror.sdk.listener.metadata.GetNFTRealPriceListener;
//import com.mirror.sdk.listener.metadata.GetNFTsListener;
//import com.mirror.sdk.listener.metadata.SOLSearchNFTsListener;
//import com.mirror.sdk.listener.universal.BoolListener;
//import com.mirror.sdk.listener.universal.MirrorCallback;
//import com.mirror.sdk.listener.wallet.GetOneWalletTransactionBySigListener;
//import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
//import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
//import com.mirror.sdk.listener.wallet.TransactionsDTO;
//import com.mirror.sdk.listener.wallet.TransferSOLListener;
//import com.mirror.sdk.particle.MirrorSafeAPI;
//import com.mirror.sdk.request.ApproveReqUpdateNFTProperties;
//import com.mirror.sdk.request.ReqTransSOL;
//import com.mirror.sdk.request.ReqTransSPLToken;
//import com.mirror.sdk.response.CommonResponse;
//import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;
//import com.mirror.sdk.response.market.ListingResponse;
//import com.mirror.sdk.response.market.MintResponse;
//import com.mirror.sdk.response.market.MultipleNFTsResponse;
//import com.mirror.sdk.response.metadata.MirrorMarketSearchNFTObj;
//import com.mirror.sdk.response.wallet.GetWalletTokenResponse;
//import com.mirror.sdk.utils.MirrorGsonUtils;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.List;
//
//public class MirrorWorld {
//    /**
//     * Type: SDK
//     * Function: Init SDK
//     * @param activity
//     * @param mirrorEnv
//     */
//    final public static void initMirrorWorld(Activity activity, String apiKey, MirrorChains chain, MirrorEnv mirrorEnv){
//        if(apiKey.isEmpty()){
//            MirrorSDK.logError("APIKey is invalid!");
//            return;
//        }
//        MirrorSDK.getInstance().InitSDK(activity,mirrorEnv,chain);
//        MirrorSDK.getInstance().SetApiKey(apiKey);
//    }
//    final public static void initMirrorWorld(Activity activity, String apiKey, MirrorChains chain, int env){
//        MirrorSDK.getInstance().SetApiKey(apiKey);
//        MirrorEnv data=MirrorEnv.values()[env];
//        MirrorSDK.getInstance().InitSDK(activity,data,chain);
//    }
//
//    /**
//     * Type: SDK
//     * Function: Get now environment.
//     * @return
//     */
//    final public static MirrorEnv getEnvironment(){
//        return MirrorSDK.getInstance().getEnv();
//    }
//
//    /**
//     * Type: SDK
//     * Function: Show the sdk flow in console if true.
//     * @param useDebugMode
//     */
//    final public static void setDebug(boolean useDebugMode){
//        MirrorSDK.getInstance().SetDebug(useDebugMode);
//    }
//
//    /**
//     * Type: SDK
//     * Function: Login.
//     */
//
//    final public static void startLogin(MirrorCallback callback,Activity activity){
//        MirrorSDK.getInstance().openLoginPage(callback,activity);
//    }
//
//    /**
//     * Type: SDK
//     * Function: Guest login.
//     */
//    final public static void guestLogin(LoginListener listener){
//        MirrorSDK.getInstance().guestLogin(listener);
//    }
//
//    /**
//     * Type: SDK
//     * Function: Logout.
//     * @param listener
//     */
//    final public static void logout(BoolListener listener){
//        MirrorSDK.getInstance().logout(listener);
//    }
//
//    /**
//     * Type: SDK
//     * Function: Login with email and get all response.
//     */
//    final public static void loginWithEmail(String email,String password,MirrorCallback callback){
//        MirrorSDK.getInstance().LoginWithEmail(email, password, callback);
//    }
//
//    /**
//     * Type: SDK
//     * Function: Open user's wallet page.
//     */
//    final public static void openWallet(MirrorCallback callback){
//        MirrorSDK.getInstance().OpenWallet("",callback);
//    }
//
//    final public static void openWallet(String walletUrl,MirrorCallback callback){
//        MirrorSDK.getInstance().OpenWallet(walletUrl,callback);
//    }
//
//    /**
//     * Type: SDK
//     * Function: Open market of this app.
//     */
//    final public static void openMarket(String marketUrl){
//        MirrorSDK.getInstance().openMarket(marketUrl);
//    }
//
//    /**
//     * Type: SDK
//     * Function: Open any url
//     */
//    final public static void openUrl(String url,Activity returnActivity){
//        MirrorSDK.getInstance().openUrl(url,returnActivity);
//    }
//
//    /**
//     * Type: SDK
//     * Function: Fetch details of a user.
//     * @param listener
//     */
//    final public static void fetchUser(FetchUserListener listener){
//        MirrorSDK.getInstance().FetchUser(listener);
//    }
//
//    /**
//     * Type: SDK
//     * Function: Query some user and get details;
//     * @param email
//     * @param listener
//     */
//    final public static void queryUser(String email, FetchUserListener listener){
//        MirrorSDK.getInstance().QueryUser(email,listener);
//    }
//
//    /**
//     * Type: SDK
//     * Function: Checks whether the current user is logged in
//     * @param listener
//     */
//    final public static void isLoggedIn(BoolListener listener){
//        MirrorSDK.getInstance().CheckAuthenticated(listener);
//    }
//
//    /**
//     * Type:Wallet
//     * Function: Get tokens from the current user.
//     * @param listener
//     */
//    final public static void getTokens(GetWalletTokenListener listener){
//        MirrorSDK.getInstance().getWalletTokens(new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                CommonResponse<GetWalletTokenResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetWalletTokenResponse>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    listener.onSuccess(response.data);
//                }else{
//                    listener.onFailed(response.code,response.message);
//                }
//            }
//        });
//    }
//
//    /**
//     * Type: wallet
//     * Function: Get tokens of a wallet address
//     * @param walletAddress
//     * @param walletTokenListener
//     */
//    final public static void getTokensByWallet(String walletAddress, GetWalletTokenListener walletTokenListener){
//        MirrorSDK.getInstance().GetWalletTokensByWallet(walletAddress, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                CommonResponse<GetWalletTokenResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetWalletTokenResponse>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    walletTokenListener.onSuccess(response.data);
//                }else{
//                    walletTokenListener.onFailed(response.code,response.message);
//                }
//            }
//        });
//    }
//
//    /**
//     * Type: Wallet
//     * Function: Get transactions of the logged user.
//     * @param before
//     * @param walletTransactionListener
//     */
//    final public static void getTransactionsOfLoggedUser(int limit, String before, GetWalletTransactionListener walletTransactionListener){
//        HashMap<String,String> map = new HashMap<String,String>();
//        if(limit != 0) map.put("limit", String.valueOf(limit));
//        map.put("next_before",before);
//        MirrorSDK.getInstance().transactions(map, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                MirrorSDK.logWarn(result);
//            }
//        });
//    }
//
//    /**
//     * Type: Wallet
//     * Function: Get transactions from a wallet address.
//     * @param walletAddress
//     * @param limit
//     */
//    final public static void getTransactionsByWallet(String walletAddress, int limit, MirrorCallback callback){
//        HashMap<String,String> map = new HashMap<String,String>();
//        if(limit != 0) map.put("limit", String.valueOf(limit));
//        MirrorSDK.getInstance().getTransactionsByWalletOnSolana(walletAddress,map,callback);
//    }
//
//    /**
//     * Type: Wallet
//     * Function: Get transaction with its signature.
//     * @param signature
//     * @param listener
//     */
//    final public static void getTransactionBySignature(String signature, GetOneWalletTransactionBySigListener listener){
//        MirrorSDK.getInstance().getTransactionBySignatureOnSolana(signature, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                CommonResponse<TransactionsDTO> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<TransactionsDTO>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    listener.onSuccess(response.data);
//                }else if(response.code == MirrorResCode.NO_RESOURCES){
//                    listener.onFailed(response.code,"No this transaction.");
//                }else{
//                    listener.onFailed(response.code,response.message);
//                }
//            }
//        });
//    }
//
//    /**
//     * Type: Wallet
//     * Function: Transfer sol to another wallet
//     * @param toPublicKey
//     * @param amount
//     * @param listener
//     */
////    final public static void transferSOL(String toPublicKey, float amount, TransferSOLListener listener){
////        ReqTransSOL requestMintNFT = new ReqTransSOL();
////        requestMintNFT.toPublicKey = toPublicKey;
////        requestMintNFT.amount = amount;
////
////        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(requestMintNFT);
////        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.TransferSol, "TransferSol", 0, params, new MirrorCallback() {
////            @Override
////            public void callback(String nothing) {
////                MirrorSDK.getInstance().TransferSOL(toPublicKey, amount, listener);
////            }
////        });
////    }
//
//    /**
//     * Type: Wallet
//     * Function: Transfer SPL token to a recipient
//     * @param toPublickey
//     * @param amount
//     * @param token_mint
//     * @param decimals
//     * @param mirrorCallback
//     */
//    final public static void transferSPLToken(String toPublickey, float amount, String token_mint, int decimals, MirrorCallback mirrorCallback){
//
//        ReqTransSPLToken req = new ReqTransSPLToken();
//        req.toPublickey = toPublickey;
//        req.amount = amount;
//        req.token_mint = token_mint;
//        req.decimals = decimals;
//
//        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
//        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.TransferSPLToken, "TransferSPLToken", 0, params, new MirrorCallback() {
//            @Override
//            public void callback(String nothing) {
//                MirrorSDK.getInstance().TransferToken(toPublickey, amount, token_mint, decimals, mirrorCallback);
//            }
//        });
//    }
//
//    /**
//     * Type: Wallet
//     * Function: transferETH
//     * @param nonce
//     * @param gasPrice
//     * @param gasLimit
//     * @param to
//     * @param amount
//     * @param mirrorCallback
//     */
//    final public static void transferETH(String nonce, String gasPrice, String gasLimit, String to,int amount, MirrorCallback mirrorCallback){
//        MirrorSDK.getInstance().TransferETH(nonce, gasPrice, gasLimit, to, amount, mirrorCallback);
//    }
//
//
//    /**
//     * Type: Asset/NFT
//     * Fetch NFTs
//     * @param mint_addresses
//     * @param fetchByMintAddressListener
//     */
//    final public static void fetchNFTsByMintAddresses(List<String> mint_addresses, FetchNFTsListener fetchByMintAddressListener){
//        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        for (String tag : mint_addresses) {
//            jsonArray.put(tag);
//        }
//        try {
//            jsonObject.put("mint_addresses", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSDK.getInstance().fetchNFTsByMintAddresses(data, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                CommonResponse<MultipleNFTsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MultipleNFTsResponse>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    fetchByMintAddressListener.onFetchSuccess(response.data);
//                }else{
//                    fetchByMintAddressListener.onFetchFailed(response.code,response.message);
//                }
//            }
//        });
//    }
//
//    /**
//     * Type: Asset/NFT
//     * Function: Get NFTs by owner's wallet address
//     * @param ownerWalletAddress
//     * @param listener
//     */
////    final public static void getNFTsOwnedByAddress(String ownerWalletAddress, FetchNFTsListener listener){
////        List<String> wallets = new ArrayList<>();
////        wallets.add(ownerWalletAddress);
////        MirrorSDK.getInstance().FetchNFTsByMintAddresses(wallets,listener);
////    }
//
//    /**
//     * Type: Asset/Mint
//     * Create a collection for minting NFTs
//     * @param name
//     * @param symbol
//     * @param detailUrl
//     * @param createTopCollectionListener
//     */
//    final public static void createVerifiedCollection(String name, String symbol, String detailUrl, CreateTopCollectionListener createTopCollectionListener){
//        createVerifiedCollection(name, symbol, detailUrl, MirrorConfirmation.Default, createTopCollectionListener);
//    }
//
//    final public static void createVerifiedCollection(String name, String symbol, String detailUrl,String confirmation, CreateTopCollectionListener createTopCollectionListener){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("name", name);
//            jsonObject.put("symbol", symbol);
//            jsonObject.put("url", detailUrl);
//            jsonObject.put("confirmation", confirmation);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.CreateCollection, "CreateCollection", 0, jsonObject, new MirrorCallback() {
//            @Override
//            public void callback(String nothing) {
//                MirrorSDK.getInstance().CreateVerifiedCollection(data, new MirrorCallback() {
//                    @Override
//                    public void callback(String result) {
//                        CommonResponse<MintResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MintResponse>>(){}.getType());
//                        if(response.code == MirrorResCode.SUCCESS){
//                            createTopCollectionListener.onCreateSuccess(response.data);
//                        }else{
//                            createTopCollectionListener.onCreateFailed(response.code,response.message);
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * Type: Asset/Mint
//     * Function: Mint NFT
//     * @param collection_mint
//     * @param name
//     * @param symbol
//     * @param detailUrl
//     * @param mintNFTListener
//     */
//    final public static void mintNFT(String collection_mint, String name, String symbol, String detailUrl,MintNFTListener mintNFTListener){
//        mintNFT(collection_mint,name,symbol,detailUrl, MirrorConfirmation.Default,mintNFTListener);
//    }
//
//    final public static void mintNFT(String collection_mint, String name, String symbol, String detailUrl, String confirmation,MintNFTListener mintNFTListener){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("collection_mint", collection_mint);
//            jsonObject.put("name", name);
//            jsonObject.put("symbol", symbol);
//            jsonObject.put("url", detailUrl);
//            jsonObject.put("confirmation", confirmation);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.MintNFT, "mint nft", 0, jsonObject, new MirrorCallback() {
//            @Override
//            public void callback(String nothing) {
//                MirrorSDK.getInstance().mintNFT(data, new MirrorCallback() {
//                    @Override
//                    public void callback(String result) {
//                        CommonResponse<MintResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MintResponse>>(){}.getType());
//                        if(response.code == MirrorResCode.SUCCESS){
//                            mintNFTListener.onMintSuccess(response.data);
//                        }else{
//                            mintNFTListener.onMintFailed(response.code,response.message);
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * Type: Asset/Auction
//     * Update a NFT's info
//     * @param mintAddress
//     * @param name
//     * @param symbol
//     * @param updateAuthority
//     * @param NFTJsonUrl
//     * @param seller_fee_basis_points
//     * @param confirmation
//     * @param mintNFTListener
//     */
//    final public static void updateNFTProperties(String mintAddress, String name, String symbol, String updateAuthority, String NFTJsonUrl,int seller_fee_basis_points, String confirmation, MintNFTListener mintNFTListener){
//        ApproveReqUpdateNFTProperties req = new ApproveReqUpdateNFTProperties();
//        req.mint_address = mintAddress;
//        req.name = name;
//        req.symbol = symbol;
//        req.update_authority = updateAuthority;
//        req.seller_fee_basis_points = seller_fee_basis_points;
//        req.confirmation = confirmation;
//
//        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
//        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.UpdateNFT, "Update NFT", 0, params, new MirrorCallback() {
//            @Override
//            public void callback(String nothing) {
//                MirrorSDK.getInstance().updateNFTProperties(mintAddress, name, symbol, updateAuthority, NFTJsonUrl, seller_fee_basis_points, confirmation, mintNFTListener);
//            }
//        });
//    }
//
//    final public static void updateNFTProperties(String mintAddress, String name, String symbol, String updateAuthority, String NFTJsonUrl,int seller_fee_basis_points, MintNFTListener mintNFTListener){
//        ApproveReqUpdateNFTProperties req = new ApproveReqUpdateNFTProperties();
//        req.mint_address = mintAddress;
//        req.name = name;
//        req.symbol = symbol;
//        req.update_authority = updateAuthority;
//        req.seller_fee_basis_points = seller_fee_basis_points;
//        req.confirmation = MirrorConfirmation.Confirmed;
//
//        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
//        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.UpdateNFT, "Update NFT", 0, params, new MirrorCallback() {
//            @Override
//            public void callback(String nothing) {
//                MirrorSDK.getInstance().updateNFTProperties(mintAddress, name, symbol, updateAuthority, NFTJsonUrl, seller_fee_basis_points, MirrorConfirmation.Confirmed, mintNFTListener);
//            }
//        });
//    }
//
//
//    /**
//     * Type: Asset/Auction
//     * Function: Buy a NFT from marketplace.
//     * @param mint_address
//     * @param price
//     * @param buyNFTListener
//     */
//    final public static void buyNFT(String mint_address, Double price, BuyNFTListener buyNFTListener){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("mint_address", mint_address);
//            jsonObject.put("price", price);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.BuyNFT, "BuyNFT", 0, jsonObject, new MirrorCallback() {
//            @Override
//            public void callback(String nothing) {
//                MirrorSDK.getInstance().BuyNFT(data, new MirrorCallback() {
//                    @Override
//                    public void callback(String result) {
//                        CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
//                        if(response.code == MirrorResCode.SUCCESS){
//                            buyNFTListener.onBuySuccess(response.data);
//                        }else{
//                            buyNFTListener.onBuyFailed(response.code,response.message);
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * Type: Asset/Auction
//     * Function: List a NFT to marketplace.
//     * @param mint_address
//     * @param price
//     * @param listener
//     */
//    final public static void listNFT(String mint_address, Double price, ListNFTListener listener){
//        listNFT(mint_address, price, MirrorConfirmation.Default,null, listener);
//    }
//    final public static void listNFT(String mint_address, Double price, String confirmation, String auction_house, ListNFTListener listener){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("mint_address", mint_address);
//            jsonObject.put("price", price);
//            jsonObject.put("confirmation",confirmation);
//            if(auction_house != null && auction_house != "") jsonObject.put("auction_house",auction_house);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.ListNFT, "ListNFT", 0, jsonObject, new MirrorCallback() {
//            @Override
//            public void callback(String nothing) {
//                MirrorSDK.getInstance().ListNFT(data, new MirrorCallback() {
//                    @Override
//                    public void callback(String result) {
//                        CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
//                        if(response.code == MirrorResCode.SUCCESS){
//                            listener.onListSuccess(response.data);
//                        }else{
//                            listener.onListFailed(response.code,response.message);
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * Type: Asset/Auction
//     * Function: Update a nft which is listing
//     * @param mint_address
//     * @param price
//     * @param listener
//     */
//    final public static void updateNFTListing(String mint_address, Double price, UpdateListListener listener){
//        MirrorSDK.getInstance().UpdateNFTListing(mint_address, price, MirrorConfirmation.Default, listener);
//    }
//    final public static void updateNFTListing(String mint_address, Double price,String confirmation, UpdateListListener listener){
//        MirrorSDK.getInstance().UpdateNFTListing(mint_address, price, confirmation, listener);
//    }
//
//    /**
//     * Type: Asset/Auction
//     * Cancel NFT listing
//     * @param mint_address
//     * @param price
//     * @param listener
//     */
//    final public static void cancelNFTListing(String mint_address, Double price, CancelListListener listener){
//        cancelNFTListing(mint_address, price, MirrorConfirmation.Default, listener);
//    }
//
//    final public static void cancelNFTListing(String mint_address, Double price,String confirmation, CancelListListener listener){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("mint_address", mint_address);
//            jsonObject.put("price", price);
//            jsonObject.put("confirmation",confirmation);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.CancelListing, "CancelListing", 0, jsonObject, new MirrorCallback() {
//            @Override
//            public void callback(String nothing) {
//                MirrorSDK.getInstance().CancelNFTListing(data, new MirrorCallback() {
//                    @Override
//                    public void callback(String result) {
//                        CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
//                        if(response.code == MirrorResCode.SUCCESS){
//                            listener.onCancelSuccess(response.data);
//                        }else{
//                            listener.onCancelFailed(response.code,response.message);
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * Type: Asset/Auction
//     * Function: Transfer a NFT to another wallet on Solana
//     * @param mint_address
//     * @param to_wallet_address
//     * @param transferNFTListener
//     */
//    final public static void transferNFT(String mint_address, String to_wallet_address, TransferNFTListener transferNFTListener){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("mint_address", mint_address);
//            jsonObject.put("to_wallet_address", to_wallet_address);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSafeAPI.getSecurityToken(MirrorSafeOptType.TransferNFT, "TransferNFT", 0, jsonObject, new MirrorCallback() {
//            @Override
//            public void callback(String nothing) {
//                MirrorSDK.getInstance().TransferNFTToAnotherWallet(data, new MirrorCallback() {
//                    @Override
//                    public void callback(String result) {
//                        CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
//                        if(response.code == MirrorResCode.SUCCESS){
//                            transferNFTListener.onTransferSuccess(response.data);
//                        }else{
//                            transferNFTListener.onTransferFailed(response.code,response.message);
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * Type: Confirmation
//     * Function: Check status of minting and transactions
//     * @param mintAddresses
//     * @param listener
//     */
//    final public static void checkStatusOfMinting(List<String> mintAddresses, CheckStatusOfMintingListener listener){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            JSONArray jsonArray = new JSONArray();
//            for (String tag : mintAddresses) {
//                jsonArray.put(tag);
//            }
//            jsonObject.put("mint_addresses", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSDK.getInstance().checkStatusOfMinting(data, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                CommonResponse<CheckStatusOfMintingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<CheckStatusOfMintingResponse>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    listener.onSuccess(response.data);
//                }else{
//                    listener.onCheckFailed(response.code,response.message);
//                }
//            }
//        });
//    }
//
//    /**
//     * Type: Confirmation
//     * Function: Get status of transactions by transactions' signatures.
//     * @param signatures
//     * @param listener
//     */
//    final public static void checkStatusOfTransactions(List<String> signatures, CheckStatusOfMintingListener listener){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            JSONArray jsonArray = new JSONArray();
//            for (String tag : signatures) {
//                jsonArray.put(tag);
//            }
//            jsonObject.put("signatures", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSDK.getInstance().checkStatusOfTransactions(data, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                CommonResponse<CheckStatusOfMintingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<CheckStatusOfMintingResponse>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    listener.onSuccess(response.data);
//                }else{
//                    listener.onCheckFailed(response.code,response.message);
//                }
//            }
//        });
//    }
//
//    /**
//     * Type: Marketplace
//     * Function: Fetch NFTs by creator addresses.
//     * @param creators
//     * @param limit
//     * @param offset
//     * @param listener
//     */
//    final public static void fetchNFTsByCreatorAddresses(List<String> creators, int limit, int offset, FetchNFTsListener listener){
//        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        for (String tag : creators) {
//            jsonArray.put(tag);
//        }
//        try {
//            jsonObject.put("creators", jsonArray);
//            jsonObject.put("limit", limit);
//            jsonObject.put("offset", offset);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String  data = jsonObject.toString();
//        MirrorSDK.getInstance().FetchNFTsByCreatorAddresses(data, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                CommonResponse<MultipleNFTsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MultipleNFTsResponse>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    listener.onFetchSuccess(response.data);
//                }else{
//                    listener.onFetchFailed(response.code,response.message);
//                }
//            }
//        });
//    }
//
//    /**
//     * Type: Marketplace
//     * Function: Fetch NFTs by authorities
//     * @param update_authorities
//     * @param listener
//     */
//    final public static void fetchNFTsByUpdateAuthorities(List<String> update_authorities, int limit, int offset, FetchNFTsListener listener){
//        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        for (String tag : update_authorities) {
//            jsonArray.put(tag);
//        }
//        try {
//            jsonObject.put("update_authorities", jsonArray);
//            if(limit != 0) jsonObject.put("limit", limit);
//            if(offset != 0) jsonObject.put("offset", offset);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSDK.getInstance().FetchNFTsByUpdateAuthorities(data, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                CommonResponse<MultipleNFTsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MultipleNFTsResponse>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    listener.onFetchSuccess(response.data);
//                }else{
//                    listener.onFetchFailed(response.code,response.message);
//                }
//            }
//        });
//    }
//
//    /**
//     * Type: Marketplace
//     * Function: Fetch NFT's activity
//     * @param mint_address
//     * @param fetchSingleNFTActivityListener
//     */
//    final public static void fetchNFTMarketplaceActivity(String mint_address, FetchSingleNFTActivityListener fetchSingleNFTActivityListener){
//        MirrorSDK.getInstance().fetchNFTMarketplaceActivity(mint_address, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                CommonResponse<ActivityOfSingleNftResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ActivityOfSingleNftResponse>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    fetchSingleNFTActivityListener.onFetchSuccess(response.data);
//                }else{
//                    fetchSingleNFTActivityListener.onFetchFailed(response.code,response.message);
//                }
//            }
//        });
//    }
//
//    /**
//     * Type: Metadata
//     * Function: Get info of collections.
//     * @param collections
//     * @param listener
//     */
//    final public static void getCollectionInfo(List<String> collections, GetCollectionInfoListener listener){
//        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        for (String tag : collections) {
//            jsonArray.put(tag);
//        }
//        try {
//            jsonObject.put("collections", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSDK.getInstance().getCollectionInfo(data, listener);
//    }
//
//    /**
//     * Type: Metadata
//     * Function: Get collection filters from a collection.
//     * @param collectionAddress
//     * @param listener
//     */
//    final public static void getCollectionFilterInfo(String collectionAddress, GetCollectionFilterInfoListener listener){
//        MirrorSDK.getInstance().getCollectionFilterInfo(collectionAddress, listener);
//    }
//
//    /**
//     * Type: Metadata
//     * Function: Get collection summary.
//     * @param collections
//     * @param listener
//     */
//    final public static void getCollectionSummary(List<String> collections, GetCollectionSummaryListener listener){
//        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        for (String tag : collections) {
//            jsonArray.put(tag);
//        }
//        try {
//            jsonObject.put("collections", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSDK.getInstance().getCollectionsSummary(data, listener);
//    }
//
//
//    /**
//     * Type: Metadata
//     * Function: Get NFTs from a collection.
//     * @param collection
//     * @param page
//     * @param page_size
//     * @param order_by
//     * @param desc
//     * @param sale
//     * @param filter
//     * @param listener
//     */
//    final public static void getNFTsByUnabridgedParamsOnSolana(String collection, int page, int page_size, String order_by, boolean desc, double sale, List<JSONObject> filter, GetNFTsListener listener){
//        MirrorSDK.getInstance().getNFTsByUnabridgedParamsOnSolana(collection, page, page_size, order_by, desc, sale, filter, listener);
//    }
//
//    /**
//     * Type: Metadata
//     * Function: Search NFTs which fitting given search string.
//     * @param collections
//     * @param searchStr
//     * @param listener
//     */
//    final public static void searchNFTs(List<String> collections, String searchStr, SOLSearchNFTsListener listener){
//        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        for (String tag : collections) {
//            jsonArray.put(tag);
//        }
//        try {
//            jsonObject.put("collections", jsonArray);
//            jsonObject.put("search", searchStr);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSDK.getInstance().searchNFTs(data, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                CommonResponse<List<MirrorMarketSearchNFTObj>> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<List<MirrorMarketSearchNFTObj>>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    listener.onSuccess(response.data);
//                }else {
//                    listener.onFail(response.code, response.message);
//                }
//            }
//        });
//    }
//
//    /**
//     * Type: Metadata
//     * Function: Get recommend NFTs from server.
//     * @param collections
//     * @param listener
//     */
//    final public static void recommendSearchNFT(List<String> collections, SOLSearchNFTsListener listener){
//        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        for (String tag : collections) {
//            jsonArray.put(tag);
//        }
//        try {
//            jsonObject.put("collections", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//        MirrorSDK.getInstance().RecommendSearchNFT(data, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                CommonResponse<List<MirrorMarketSearchNFTObj>> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<List<MirrorMarketSearchNFTObj>>>(){}.getType());
//                if(response.code == MirrorResCode.SUCCESS){
//                    listener.onSuccess(response.data);
//                }else {
//                    listener.onFail(response.code, response.message);
//                }
//            }
//        });
//    }
//
//    /**
//     * Type: Metadata
//     * Function: Get NFT real price.
//     * @param price
//     * @param fee
//     * @param listener
//     */
//    final public static void getNFTRealPrice(String price, int fee, GetNFTRealPriceListener listener){
//        MirrorSDK.getInstance().GetNFTRealPrice(price, fee, listener);
//    }
//}
