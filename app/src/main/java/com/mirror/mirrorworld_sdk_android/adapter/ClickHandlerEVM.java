package com.mirror.mirrorworld_sdk_android.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mirror.mirrorworld_sdk_android.enums.DemoAPI;
import com.mirror.sdk.MWEVM;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.auth.LoginListener;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.listener.metadata.GetCollectionFilterInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionSummaryListener;
import com.mirror.sdk.listener.metadata.GetNFTRealPriceListener;
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.request.ReqEVMFetchNFTsToken;
import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.MintResponse;
import com.mirror.sdk.response.metadata.GetCollectionFilterInfoRes;
import com.mirror.sdk.response.metadata.GetCollectionInfoRes;
import com.mirror.sdk.response.metadata.GetCollectionSummaryRes;
import com.mirror.sdk.response.metadata.GetNFTRealPriceRes;
import com.mirror.sdk.utils.MirrorGsonUtils;

import java.util.ArrayList;
import java.util.List;

public class ClickHandlerEVM extends ClickHandlerBase{

    ClickHandlerEVM(Activity context) {
        super(context);
    }

    public void handleClick(Activity returnActivity, DemoAPI apiId, MultiParaItemRecyclerViewAdapter.ViewHolder holder, View view){
        if(apiId == DemoAPI.GET_ENVIRONMENT){
            String r = ("Environment is:" + MWEVM.getEnvironment());
            runInUIThread(holder,r);
        }else if(apiId == DemoAPI.SET_JWT){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            MirrorSDK.getInstance().SetAccessToken(String.valueOf(holder.mEt1.getText()));
            String r = ("JWT has been set!");
            runInUIThread(holder,r);
        }else if(apiId == DemoAPI.START_LOGIN){
            MWEVM.startLogin(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    //todo: Show result
                    String r = (result);
                    runInUIThread(holder,r);
                }
            }, mActivity);
        }else if(apiId == DemoAPI.IS_LOGGED){
            MWEVM.isLoggedIn(new BoolListener() {
                @Override
                public void onBool(boolean boolValue) {
                    String r = ("Current user login state:"+boolValue);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.GUEST_LOGIN){
            MWEVM.guestLogin(new LoginListener() {
                @Override
                public void onLoginSuccess() {
                    String r = ("Guest login success!");
                    runInUIThread(holder,r);
                }

                @Override
                public void onLoginFail() {
                    String r = ("Guest login failed!");
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.LOGOUT){
            MWEVM.logout(new BoolListener() {
                @Override
                public void onBool(boolean boolValue) {
                    String r = ("Logout result:"+boolValue);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.OPEN_WALLET){
            MWEVM.openWallet(mActivity,new MirrorCallback() {
                @Override
                public void callback(String result) {
                    Log.d("MirrorSDK","Wallet logout callback runs!");
                }
            });
        }else if(apiId == DemoAPI.OPEN_MARKET){
            //Choice market url by environment
            MirrorEnv env = MWEVM.getEnvironment();
            String marketRoot;
            if(MirrorSDK.getInstance().env == MirrorEnv.StagingMainNet){
                marketRoot = "";//no url yet
            }else if(env == MirrorEnv.StagingDevNet){
                marketRoot = "https://jump-devnet.mirrorworld.fun";
            }else if(env == MirrorEnv.DevNet){
                marketRoot = "";//No url yet
                Toast.makeText(mActivity,"No market on DevNet",Toast.LENGTH_SHORT).show();
                return;
            }else if(env == MirrorEnv.MainNet){
                marketRoot = "https://jump.mirrorworld.fun/";
            }else {
                MirrorSDK.getInstance().logFlow("Unknown env:"+env);
                marketRoot = "https://jump-devnet.mirrorworld.fun";
            }

            //Call API:openMarket
            MWEVM.openMarket(marketRoot,mActivity);
        }else if(apiId == DemoAPI.LOGIN_With_EMAIL){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String email = String.valueOf(holder.mEt1.getText());
            String passWord = String.valueOf(holder.mEt2.getText());

            MWEVM.loginWithEmail(email,passWord,new MirrorCallback() {
                @Override
                public void callback(String s) {
                    String r = (s);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.FETCH_USER){
            MWEVM.fetchUser(new FetchUserListener() {
                @Override
                public void onUserFetched(UserResponse userResponse) {
                    String r = (userResponse.email+" eth_address "+userResponse.wallet.eth_address+" solona_address "+userResponse.wallet.sol_address);
                    runInUIThread(holder,r);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    String r = (message);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.QUERY_USER){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String email = String.valueOf(holder.mEt1.getText());
            MWEVM.queryUser(email,new FetchUserListener() {
                @Override
                public void onUserFetched(UserResponse userResponse) {
                    String r = "Result is:" + MirrorGsonUtils.getInstance().toJson(userResponse);
                    runInUIThread(holder,r);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    String r = (message);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.CREATE_VERIFIED_COLLECTION){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String contract_type = String.valueOf(holder.mEt1.getText());
            String detailsUrl = String.valueOf(holder.mEt2.getText());

            MWEVM.createVerifiedCollection(mActivity,contract_type, detailsUrl, MirrorConfirmation.Default, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+ result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.MINT_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }

            String collection_address = String.valueOf(holder.mEt1.getText());
            String token_id = String.valueOf(holder.mEt2.getText());
            String to_wallet_address = String.valueOf(holder.mEt3.getText());

            if(!isInteger(token_id)){
                showToast("token_id must be an integer!");
                return;
            }
            int tokenID = getInteger(token_id);

            MWEVM.mintNFT(returnActivity,collection_address, tokenID, MirrorConfirmation.Default, to_wallet_address, new MintNFTListener() {
                @Override
                public void onMintSuccess(MintResponse userResponse) {
                    MirrorSDK.getInstance().logFlow("Mint nft result:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                    String r = ("Mint NFT result is:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                    runInUIThread(holder,r);
                }

                @Override
                public void onMintFailed(long code, String message) {
                    String r = (message);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.LIST_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String token_id = String.valueOf(holder.mEt2.getText());
            String priceStr = String.valueOf(holder.mEt3.getText());
            String marketplace_address = String.valueOf(holder.mEt4.getText());

            if(!isInteger(token_id)){
                showToast("token_id must be an integer!");
                return;
            }
            int tokenID = getInteger(token_id);

            float price = 0.0f;

            try{
                price = Float.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MWEVM.listNFT(mActivity, mint_address, tokenID, price, marketplace_address, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.FETCH_SINGLE_NFT_DETAILS){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String tokenAddress =String.valueOf(holder.mEt1.getText());
            String tokenID =String.valueOf(holder.mEt2.getText());
            MWEVM.getNFTDetails(tokenAddress, tokenID, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = "NFT details is:"+result;
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.METADATA_GET_NFT_INFO){
            if(!checkEt(holder.mEt1)||!checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String contract = String.valueOf(holder.mEt1.getText());
            String token_id = String.valueOf(holder.mEt2.getText());

            if(!isInteger(token_id)){
                showToast("token_id must be an integer!");
                return;
            }
            int tokenID = getInteger(token_id);

            MWEVM.getNFTInfo(contract,tokenID, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = "Visiting result:"+MirrorGsonUtils.getInstance().toJson(result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.CANCEL_NFT_LISTING){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String token_id = String.valueOf(holder.mEt2.getText());
            String marketplace_address = String.valueOf(holder.mEt3.getText());

            if(!isInteger(token_id)){
                showToast("token_id must be an integer!");
                return;
            }
            int tokenID = getInteger(token_id);

            MWEVM.cancelNFTListing(mActivity, mint_address, tokenID, marketplace_address, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is "+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.FETCH_NFT_BY_OWNER_ADDRESSES){
            if(!checkEt(holder.mEt1)||!checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String owner = String.valueOf(holder.mEt1.getText());

            int limit = 0;
            try{
                limit = Integer.valueOf(String.valueOf(holder.mEt2.getText()));
            }catch (NumberFormatException E){

            }
            MWEVM.fetchNFTsByOwnerAddresses(owner, limit, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:\n"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.FETCH_NFT_BY_CREATOR){
            if(MWEVM.getEnvironment() != MirrorEnv.MainNet && MWEVM.getEnvironment() != MirrorEnv.StagingMainNet){
                Toast.makeText(mActivity,"FetchNFTsByCreatorAddresses API can only run on MAINNET.",Toast.LENGTH_SHORT).show();
                return;
            }

            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }
            List<String>  owners = new ArrayList<>();
            owners.add(String.valueOf(holder.mEt1.getText()));

            int limit = 0;
            int offset = 0;
            try{
                limit =  Integer.valueOf(String.valueOf(holder.mEt2.getText()));
                offset = Integer.valueOf(String.valueOf(holder.mEt3.getText()));
            }catch (NumberFormatException E){

            }
            MWEVM.fetchNFTsByCreatorAddresses(owners, limit, offset, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.FETCH_NFT_BY_MINT_ADDRESSES){
            if(!checkEt(holder.mEt1)||!checkEt(holder.mEt2)||!checkEt(holder.mEt3)||!checkEt(holder.mEt4)){
                showToast("Please input!");
                return;
            }
            String token_address_1 = String.valueOf(holder.mEt1.getText());
            String token_id_1 = String.valueOf(holder.mEt2.getText());
            String token_address_2 = String.valueOf(holder.mEt3.getText());
            String token_id_2 = String.valueOf(holder.mEt4.getText());

            if(!isInteger(token_id_1)){
                showToast("token_id must be an integer!");
                return;
            }
            int tokenID1 = getInteger(token_id_1);

            if(!isInteger(token_id_2)){
                showToast("token_id must be an integer!");
                return;
            }
            int tokenID2 = getInteger(token_id_2);

            List<ReqEVMFetchNFTsToken> tokens = new ArrayList<>();
            tokens.add(new ReqEVMFetchNFTsToken(token_address_1,tokenID1));
            tokens.add(new ReqEVMFetchNFTsToken(token_address_2,tokenID2));
            MWEVM.fetchNFTsByMintAddresses(tokens, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:\n" + result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.FETCH_NFT_BY_UPDATE_AUTHORITIES){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            List<String> update_address = new ArrayList<>();
            update_address.add(String.valueOf(holder.mEt1.getText()));

            int limit = 0;
            int offset = 0;
            try{
                limit =  Integer.valueOf(String.valueOf(holder.mEt2.getText()));
                offset = Integer.valueOf(String.valueOf(holder.mEt3.getText()));
            }catch (NumberFormatException E){

            }
            MWEVM.fetchNFTsByUpdateAuthorities(update_address, limit, offset, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.FETCH_NFT_MARKETPLACE_ACTIVITY){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String mint_address =String.valueOf(holder.mEt1.getText());
            MWEVM.fetchNFTMarketplaceActivity(mint_address, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.TRANSFER_NFT_TO_ANOTHER_WALLET){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }
            String collection_address = String.valueOf(holder.mEt1.getText());
            String token_id = String.valueOf(holder.mEt2.getText());
            String to_wallet_address = String.valueOf(holder.mEt3.getText());
            if(!isInteger(token_id)){
                showToast("token_id must be an integer!");
                return;
            }
            int tokenID = getInteger(token_id);
            MWEVM.transferNFT(mActivity, collection_address, tokenID, to_wallet_address, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.TRANSFER_TOKEN){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4) || !checkEt(holder.mEt5) || !checkEt(holder.mEt6)){
                showToast("Please input!");
                return;
            }
            String nonce = String.valueOf(holder.mEt1.getText());
            String gasPrice = String.valueOf(holder.mEt2.getText());
            String gasLimit = String.valueOf(holder.mEt3.getText());
            String to = String.valueOf(holder.mEt4.getText());
            String amountStr = String.valueOf(holder.mEt5.getText());
            String contract = String.valueOf(holder.mEt6.getText());

            int decimals = 0;
            try{
                decimals =  Integer.valueOf(amountStr);
            }catch (NumberFormatException E){
            }

            MWEVM.transferToken(returnActivity, nonce, gasPrice, gasLimit, to, decimals,contract, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = (result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.TRANSFER_ETH){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4) || !checkEt(holder.mEt5)){
                showToast("Please input!");
                return;
            }
            String nonce = String.valueOf(holder.mEt1.getText());
            String gasPrice = String.valueOf(holder.mEt2.getText());
            String gasLimit = String.valueOf(holder.mEt3.getText());
            String to = String.valueOf(holder.mEt4.getText());
            String amountStr = String.valueOf(holder.mEt5.getText());

            int decimals = 0;
            try{
                decimals =  Integer.valueOf(amountStr);
            }catch (NumberFormatException E){
            }

            MWEVM.transferETH(mActivity, nonce, gasPrice, gasLimit, to, decimals, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = (result);
                    runInUIThread(holder,r);
                }
            });
        }
        else if(apiId == DemoAPI.BUY_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4)){
                showToast("Please input!");
                return;
            }
            String collection_address = String.valueOf(holder.mEt1.getText());
            String token_id_str = String.valueOf(holder.mEt2.getText());
            String priceStr = String.valueOf(holder.mEt3.getText());
            String marketplace_address = String.valueOf(holder.mEt4.getText());
            if(!isInteger(token_id_str)){
                showToast("token_id must be an integer!");
                return;
            }
            int tokenID = getInteger(token_id_str);
            float price = 0.0f;
            try{
                price = Float.valueOf(priceStr);
            }catch (NumberFormatException E){
            }

            MWEVM.buyNFT(mActivity, collection_address, tokenID, price, marketplace_address, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.GET_WALLET_TOKEN){
            MWEVM.getTokens(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("Get wallet token result:\n"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.GET_TOKENS_BY_WALLET){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String walletAddress = getStringFromEditText(holder.mEt1);
            MWEVM.getTokensByWallet(walletAddress, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("Get wallet token result:\n"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.WALLET_TRANSACTIONS){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            int limit = 0;
            try{
                limit = Integer.valueOf(String.valueOf(holder.mEt1.getText()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            MWEVM.getTransactionsOfLoggedUser(limit, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("GetTransactions result is:\n"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.WALLET_TRANSACTIONS_BY_WALLET){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            int limit = 0;
            try{
                limit = Integer.valueOf(String.valueOf(holder.mEt2.getText()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            String walletAddress = getStringFromEditText(holder.mEt1);
            MWEVM.getTransactionsByWallet(walletAddress, limit, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("GetTransactions success! result is "+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.WALLET_TRANSACTIONS_BY_SIGNATURE){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String sig = String.valueOf(holder.mEt1.getText());
            MWEVM.getTransactionBySignature(sig, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("GetTransactionBySignature result:\n" + result);
                    runInUIThread(holder,r);
                }
            });
        }
        else if(apiId == DemoAPI.GET_COLLECTION_FILTER_INFO){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            MWEVM.getCollectionFilterInfo(collection, new GetCollectionFilterInfoListener() {
                @Override
                public void onSuccess(GetCollectionFilterInfoRes result) {
                    String r = ("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                    runInUIThread(holder,r);
                }

                @Override
                public void onFail(long code, String message) {
                    String r = ("Visit Failed! code:"+code+" message:"+message);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.GET_COLLECTION_INFO){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            List<String> collections = new ArrayList<>();
            collections.add(collection);
            MWEVM.getCollectionInfo(collections, new GetCollectionInfoListener() {
                @Override
                public void onSuccess(List<GetCollectionInfoRes> result) {
                    String r = ("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                    runInUIThread(holder,r);
                }

                @Override
                public void onFail(long code, String message) {
                    String r = ("Visit Failed! code:"+code+" message:"+message);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.METADATA_GET_COLLECTION_SUMMARY){
            if(!checkEt(holder.mEt1) && !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            String collection2 = String.valueOf(holder.mEt2.getText());
            List<String> collections = new ArrayList<>();
            if(!collection.isEmpty()) collections.add(collection);
            if(!collection2.isEmpty()) collections.add(collection2);
            MWEVM.getCollectionSummary(collections, new GetCollectionSummaryListener() {
                @Override
                public void onSuccess(List<GetCollectionSummaryRes> res) {
                    String r = ("Visiting success:"+MirrorGsonUtils.getInstance().toJson(res));
                    runInUIThread(holder,r);
                }

                @Override
                public void onFailed(long code, String message) {
                    String r = ("Visit Failed! code:"+code+" message:"+message);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.GET_NFT_EVENTS){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4)){
                showToast("Please input!");
                return;
            }
            int page = 0;
            int page_size = 0;
            String contract = String.valueOf(holder.mEt1.getText());
            String token_id = String.valueOf(holder.mEt2.getText());
            String pageStr = String.valueOf(holder.mEt3.getText());
            String pageSizeStr = String.valueOf(holder.mEt4.getText());
            if(!isInteger(token_id)){
                showToast("token_id must be an integer!");
                return;
            }
            int tokenID = getInteger(token_id);

            page = Integer.valueOf(pageStr);
            page_size = Integer.valueOf(pageSizeStr);
            MWEVM.getNFTEvents(contract,tokenID, page, page_size, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("Visiting result:\n"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.SEARCH_NFTS){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            String searchString = String.valueOf(holder.mEt2.getText());
            List<String> collections = new ArrayList<>();
            collections.add(collection);
            MWEVM.searchNFTs(collections, searchString, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("Visiting result:\n"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.RECOMMEND_SEARCH_NFT){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            List<String> collections = new ArrayList<>();
            collections.add(collection);
            MWEVM.recommendSearchNFT(collections, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("Visiting result:\n"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.METADATA_GET_NFTS_BY_PARAMS){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)
                    || !checkEt(holder.mEt4) || !checkEt(holder.mEt5) || !checkEt(holder.mEt6)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            int page = 1;
            int page_size = 10;
            String order_by = String.valueOf(holder.mEt4.getText());
            boolean desc = false;
            String saleStr = String.valueOf(holder.mEt6.getText());
            if(!isInteger(saleStr)){
                showToast("price must be a double!");
                return;
            }
            int sale = getInteger(saleStr);
            try{
                page = Integer.valueOf(String.valueOf(holder.mEt2.getText()));
                page_size = Integer.valueOf(String.valueOf(holder.mEt3.getText()));
                desc = Boolean.valueOf(String.valueOf(holder.mEt5.getText()));
            }catch(Exception e){

            }
            MWEVM.getNFTsByUnabridgedParams(collection, page, page_size, order_by, desc, sale, null, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("Visiting result:\n"+result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.GET_NFT_REAL_PRICE){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            int fee = Integer.parseInt(String.valueOf(holder.mEt2.getText()));
            String price = String.valueOf(holder.mEt1.getText());
            MWEVM.getNFTRealPrice(price,fee, new GetNFTRealPriceListener() {
                @Override
                public void onSuccess(GetNFTRealPriceRes result) {
                    String r = ("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                    runInUIThread(holder,r);
                }

                @Override
                public void onFail(long code, String message) {
                    String r = ("Visit Failed! code:"+code+" message:"+message);
                    runInUIThread(holder,r);
                }
            });
        }
    }
}
