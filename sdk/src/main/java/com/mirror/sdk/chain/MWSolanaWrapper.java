package com.mirror.sdk.chain;

import android.app.Activity;
import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorChains;
import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.constant.MirrorResCode;
import com.mirror.sdk.constant.MirrorSafeOptType;
import com.mirror.sdk.constant.MirrorUrl;
import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingListener;
import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingResponse;
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
import com.mirror.sdk.listener.metadata.SOLSearchNFTsListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.listener.wallet.GetOneWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
import com.mirror.sdk.listener.wallet.TransactionsDTO;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.particle.MirrorSafeAPI;
import com.mirror.sdk.request.ApproveReqUpdateNFTProperties;
import com.mirror.sdk.response.CommonResponse;
import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.response.market.MintResponse;
import com.mirror.sdk.response.market.MultipleNFTsResponse;
import com.mirror.sdk.response.metadata.MirrorMarketSearchNFTObj;
import com.mirror.sdk.response.wallet.GetWalletTokenResponse;
import com.mirror.sdk.response.wallet.GetWalletTransactionsResponse;
import com.mirror.sdk.utils.MirrorGsonUtils;
import com.mirror.sdk.utils.MirrorStringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class MWSolanaWrapper extends MWBaseWrapper{

    //SDK
    public final static void initSDK(Context activityContext, String apiKey, MirrorEnv env){
        if(apiKey == ""){
            MirrorSDK.logWarn("Please input API key");
            return;
        }
        MirrorChains chain = MirrorChains.Solana;
        MirrorSDK.getInstance().InitSDK(activityContext,env,chain);
        MirrorSDK.getInstance().SetApiKey(apiKey);
    }

    final public static void getNFTRealPrice(String price, int fee, GetNFTRealPriceListener listener){
        MirrorSDK.getInstance().GetNFTRealPrice(price, fee, listener);
    }
    final public static void updateNFTListing(String mint_address, Double price,String confirmation, UpdateListListener listener){
        MirrorSDK.getInstance().UpdateNFTListing(mint_address, price, confirmation, listener);
    }
    final public static void getNFTDetails(String mint_address, FetchSingleNFTListener fetchSingleNFT){
        MirrorSDK.getInstance().GetNFTDetailsOnSolana(mint_address, fetchSingleNFT);
    }
    //Wallet
    final public static void transferSPLToken(Activity returnActivity, String toPublickey, float amount, String token_mint, int decimals, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to_publickey", toPublickey);
            jsonObject.put("amount", MirrorStringUtils.floatToString(amount));
            jsonObject.put("token_mint", token_mint);
            jsonObject.put("decimals", decimals);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(returnActivity,MirrorSafeOptType.TransferSPLToken, "TransferSPLToken", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().transferToken(data, mirrorCallback);
            }
        });
    }

    final public static void transferSOL(Activity returnActivity, String toPublicKey, int amount, TransferSOLListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to_publickey", toPublicKey);
            jsonObject.put("amount", MirrorStringUtils.handleInt(amount));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(returnActivity,MirrorSafeOptType.TransferSol, "TransferSol", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().transferSOL(data, listener);
            }
        });
    }

    final public static void getTokens(GetWalletTokenListener listener){
        MirrorSDK.getInstance().getWalletTokens(new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<GetWalletTokenResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetWalletTokenResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else{
                    listener.onFailed(response.code,response.message);
                }
            }
        });
    }

    final public static void getTokensByWallet(String walletAddress, GetWalletTokenListener walletTokenListener){
        MirrorSDK.getInstance().GetWalletTokensByWallet(walletAddress, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<GetWalletTokenResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetWalletTokenResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    walletTokenListener.onSuccess(response.data);
                }else{
                    walletTokenListener.onFailed(response.code,response.message);
                }
            }
        });
    }

    final public static void getTransactionsOfLoggedUser(int limit, String before, GetWalletTransactionListener walletTransactionListener){
        HashMap<String,String> map = new HashMap<String,String>();
        if(limit != 0) map.put("limit", String.valueOf(limit));
        map.put("next_before",before);
        MirrorSDK.getInstance().transactions(map, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<GetWalletTransactionsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetWalletTransactionsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    walletTransactionListener.onSuccess(response.data);
                }else{
                    walletTransactionListener.onFailed(response.code,response.message);
                }
            }
        });
    }

    final public static void getTransactionsByWallet(String walletAddress, int limit, String nextBeforeStr, MirrorCallback callback){
        HashMap<String,String> map = new HashMap<String,String>();
        if(limit != 0) map.put("limit", String.valueOf(limit));
        map.put("next_before", nextBeforeStr);
        MirrorSDK.getInstance().getTransactionsByWalletOnSolana(walletAddress,map,callback);
    }

    final public static void getTransactionBySignature(String signature, GetOneWalletTransactionBySigListener listener){
        MirrorSDK.getInstance().getTransactionBySignatureOnSolana(signature, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<TransactionsDTO> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<TransactionsDTO>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else if(response.code == MirrorResCode.NO_RESOURCES){
                    listener.onFailed(response.code,"No this transaction.");
                }else{
                    listener.onFailed(response.code,response.message);
                }
            }
        });
    }

    //Metadata
    final public static void getCollectionInfo(List<String> collections, GetCollectionInfoListener listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : collections) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("collections", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().getCollectionInfo(data, listener);
    }

    final public static void getCollectionFilterInfo(String collectionAddress, GetCollectionFilterInfoListener listener){
        MirrorSDK.getInstance().getCollectionFilterInfo(collectionAddress, listener);
    }

    final public static void getCollectionSummary(List<String> collections, GetCollectionSummaryListener listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : collections) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("collections", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().getCollectionsSummary(data, listener);
    }

    final public static void getNFTInfo(String mintAddress, MirrorCallback listener){
        MirrorSDK.getInstance().GetNFTInfoOnSolana(mintAddress, listener);
    }

    final public static void getNFTsByUnabridgedParams(String collection, int page, int page_size, String order_by, boolean desc, int sale, List<JSONObject> filter, GetNFTsListener listener){
        MirrorSDK.getInstance().getNFTsByUnabridgedParamsOnSolana(collection, page, page_size, order_by, desc, sale, filter, listener);
    }

    final public static void getNFTEvents(String mint_address, int page, int page_size, GetNFTEventsListener listener){
        MirrorSDK.getInstance().GetNFTEventsOnSolana(mint_address, page, page_size, listener);
    }

    final public static void searchNFTs(List<String> collections, String searchStr, SOLSearchNFTsListener listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : collections) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("collections", jsonArray);
            jsonObject.put("search", searchStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().searchNFTs(data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<List<MirrorMarketSearchNFTObj>> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<List<MirrorMarketSearchNFTObj>>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else {
                    listener.onFail(response.code, response.message);
                }
            }
        });
    }

    final public static void recommendSearchNFT(List<String> collections, SOLSearchNFTsListener listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : collections) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("collections", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().RecommendSearchNFT(data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<List<MirrorMarketSearchNFTObj>> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<List<MirrorMarketSearchNFTObj>>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else {
                    listener.onFail(response.code, response.message);
                }
            }
        });
    }

    //Asset/Mint
    final public static void mintNFT(Activity returnActivity, String collection_mint,String detailUrl, String confirmation, MintNFTListener mintNFTListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("collection_mint", collection_mint);
            jsonObject.put("url", detailUrl);
            jsonObject.put("confirmation", confirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(returnActivity, MirrorSafeOptType.MintNFT, "mint nft", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().mintNFT(data, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        CommonResponse<MintResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MintResponse>>(){}.getType());
                        if(response.code == MirrorResCode.SUCCESS){
                            mintNFTListener.onMintSuccess(response.data);
                        }else{
                            mintNFTListener.onMintFailed(response.code,response.message);
                        }
                    }
                });
            }
        });
    }

    final public static void updateNFTProperties(Activity returnActivity, String mintAddress, String name, String symbol, String updateAuthority, String NFTJsonUrl,int seller_fee_basis_points, MintNFTListener mintNFTListener){
        ApproveReqUpdateNFTProperties req = new ApproveReqUpdateNFTProperties();
        req.mint_address = mintAddress;
        req.name = name;
        req.symbol = symbol;
        req.update_authority = updateAuthority;
        req.seller_fee_basis_points = seller_fee_basis_points;
        req.confirmation = MirrorConfirmation.Confirmed;

        JSONObject params = MirrorGsonUtils.getInstance().toJsonObj(req);
        MirrorSafeAPI.getSecurityToken(returnActivity, MirrorSafeOptType.UpdateNFT, "Update NFT", 0, params, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().updateNFTProperties(mintAddress, name, symbol, updateAuthority, NFTJsonUrl, seller_fee_basis_points, MirrorConfirmation.Confirmed, mintNFTListener);
            }
        });
    }
    //Asset/NFT
    final public static void fetchNFTsByOwnerAddresses(List<String> owners, int limit, int offset, FetchByOwnerListener fetchByOwnerListener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : owners) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("owners", jsonArray);
            if(limit != 0) jsonObject.put("limit", limit);
            if(offset != 0) jsonObject.put("offset", offset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        MirrorSDK.getInstance().fetchNFTsByOwnerAddresses(data, MirrorUrl.URL_FETCH_MULTIPLE_NFT_SOLANA, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<MultipleNFTsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MultipleNFTsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    fetchByOwnerListener.onFetchSuccess(response.data);
                }else{
                    fetchByOwnerListener.onFetchFailed(response.code,response.message);
                }

            }
        });
    }

    final public static void fetchNFTsByMintAddresses(List<String> mint_addresses, FetchNFTsListener fetchByMintAddressListener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : mint_addresses) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("mint_addresses", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().fetchNFTsByMintAddresses(data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<MultipleNFTsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MultipleNFTsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    fetchByMintAddressListener.onFetchSuccess(response.data);
                }else{
                    fetchByMintAddressListener.onFetchFailed(response.code,response.message);
                }
            }
        });
    }

    final public static void fetchNFTsByCreatorAddresses(List<String> creators, int limit, int offset, FetchNFTsListener listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : creators) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("creators", jsonArray);
            jsonObject.put("limit", limit);
            jsonObject.put("offset", offset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String  data = jsonObject.toString();
        MirrorSDK.getInstance().FetchNFTsByCreatorAddresses(data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<MultipleNFTsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MultipleNFTsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onFetchSuccess(response.data);
                }else{
                    listener.onFetchFailed(response.code,response.message);
                }
            }
        });
    }

    final public static void fetchNFTsByUpdateAuthorities(List<String> update_authorities, int limit, int offset, FetchNFTsListener listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : update_authorities) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("update_authorities", jsonArray);
            if(limit != 0) jsonObject.put("limit", limit);
            if(offset != 0) jsonObject.put("offset", offset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().FetchNFTsByUpdateAuthorities(data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<MultipleNFTsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MultipleNFTsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onFetchSuccess(response.data);
                }else{
                    listener.onFetchFailed(response.code,response.message);
                }
            }
        });
    }

    final public static void fetchNFTMarketplaceActivity(String mint_address, FetchSingleNFTActivityListener fetchSingleNFTActivityListener){
        MirrorSDK.getInstance().fetchNFTMarketplaceActivity(mint_address, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<ActivityOfSingleNftResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ActivityOfSingleNftResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    fetchSingleNFTActivityListener.onFetchSuccess(response.data);
                }else{
                    fetchSingleNFTActivityListener.onFetchFailed(response.code,response.message);
                }
            }
        });
    }

    final public static void createVerifiedCollection(Activity returnActivity, String name, String symbol, String detailUrl,String confirmation, CreateTopCollectionListener createTopCollectionListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("symbol", symbol);
            jsonObject.put("url", detailUrl);
            jsonObject.put("confirmation", confirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(returnActivity, MirrorSafeOptType.CreateCollection, "CreateCollection", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().CreateVerifiedCollection(data, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        CommonResponse<MintResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MintResponse>>(){}.getType());
                        if(response.code == MirrorResCode.SUCCESS){
                            createTopCollectionListener.onCreateSuccess(response.data);
                        }else{
                            createTopCollectionListener.onCreateFailed(response.code,response.message);
                        }
                    }
                });
            }
        });
    }

    //Confirmation
    final public static void checkStatusOfMinting(List<String> mintAddresses, CheckStatusOfMintingListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for (String tag : mintAddresses) {
                jsonArray.put(tag);
            }
            jsonObject.put("mint_addresses", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().checkStatusOfMinting(data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<CheckStatusOfMintingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<CheckStatusOfMintingResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else{
                    listener.onCheckFailed(response.code,response.message);
                }
            }
        });
    }

    final public static void checkStatusOfTransactions(List<String> signatures, CheckStatusOfMintingListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for (String tag : signatures) {
                jsonArray.put(tag);
            }
            jsonObject.put("signatures", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSDK.getInstance().checkStatusOfTransactions(data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<CheckStatusOfMintingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<CheckStatusOfMintingResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else{
                    listener.onCheckFailed(response.code,response.message);
                }
            }
        });
    }

    //Asset/Auction
    final public static void transferNFT(Activity returnActivity, String mint_address, String to_wallet_address,String confirmation, TransferNFTListener transferNFTListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("to_wallet_address", to_wallet_address);
            jsonObject.put("confirmation", confirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(returnActivity, MirrorSafeOptType.TransferNFT, "TransferNFT", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().TransferNFTToAnotherWallet(data, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
                        if(response.code == MirrorResCode.SUCCESS){
                            transferNFTListener.onTransferSuccess(response.data);
                        }else{
                            transferNFTListener.onTransferFailed(response.code,response.message);
                        }
                    }
                });
            }
        });
    }

    final public static void listNFT(Activity returnActivity, String mint_address, Double price, String confirmation, ListNFTListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("price", MirrorStringUtils.doubleToString(price));
            jsonObject.put("confirmation",confirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(returnActivity, MirrorSafeOptType.ListNFT, "ListNFT", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().ListNFT(data, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
                        if(response.code == MirrorResCode.SUCCESS){
                            listener.onListSuccess(response.data);
                        }else{
                            listener.onListFailed(response.code,response.message);
                        }
                    }
                });
            }
        });
    }
    final public static void buyNFT(Activity returnActivity, String mint_address, Double price,String auctionHouse, BuyNFTListener buyNFTListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("price",  MirrorStringUtils.doubleToString(price));
            jsonObject.put("auction_house", auctionHouse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(returnActivity, MirrorSafeOptType.BuyNFT, "BuyNFT", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().BuyNFT(data, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
                        if(response.code == MirrorResCode.SUCCESS){
                            buyNFTListener.onBuySuccess(response.data);
                        }else{
                            buyNFTListener.onBuyFailed(response.code,response.message);
                        }
                    }
                });
            }
        });
    }
    final public static void cancelNFTListing(Activity returnActivity, String mint_address, Double price,String auction_house, CancelListListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("price",  MirrorStringUtils.doubleToString(price));
            jsonObject.put("auction_house",auction_house);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        MirrorSafeAPI.getSecurityToken(returnActivity, MirrorSafeOptType.CancelListing, "CancelListing", 0, jsonObject, new MirrorCallback() {
            @Override
            public void callback(String nothing) {
                MirrorSDK.getInstance().CancelNFTListing(data, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
                        if(response.code == MirrorResCode.SUCCESS){
                            listener.onCancelSuccess(response.data);
                        }else{
                            listener.onCancelFailed(response.code,response.message);
                        }
                    }
                });
            }
        });
    }
}
