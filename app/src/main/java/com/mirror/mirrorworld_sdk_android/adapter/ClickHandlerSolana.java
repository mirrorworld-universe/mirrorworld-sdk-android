package com.mirror.mirrorworld_sdk_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.mirror.mirrorworld_sdk_android.DemoAPI;
import com.mirror.sdk.MWSolana;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorEnv;
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
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.listener.wallet.GetOneWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
import com.mirror.sdk.listener.wallet.TransactionsDTO;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.response.market.MintResponse;
import com.mirror.sdk.response.market.MultipleNFTsResponse;
import com.mirror.sdk.response.market.SingleNFTResponse;
import com.mirror.sdk.response.metadata.GetCollectionFilterInfoRes;
import com.mirror.sdk.response.metadata.GetCollectionInfoRes;
import com.mirror.sdk.response.metadata.GetCollectionSummaryRes;
import com.mirror.sdk.response.metadata.GetNFTEventsRes;
import com.mirror.sdk.response.metadata.GetNFTRealPriceRes;
import com.mirror.sdk.response.metadata.GetNFTsRes;
import com.mirror.sdk.response.metadata.MirrorMarketSearchNFTObj;
import com.mirror.sdk.response.wallet.GetWalletTokenResponse;
import com.mirror.sdk.response.wallet.GetWalletTransactionsResponse;
import com.mirror.sdk.response.wallet.TransferResponse;
import com.mirror.sdk.utils.MirrorGsonUtils;
import com.mirror.sdk.utils.MirrorStringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClickHandlerSolana extends ClickHandlerBase{

    ClickHandlerSolana(Activity context) {
        super(context);
    }

    public void handleClick(DemoAPI apiId, MultiParaItemRecyclerViewAdapter.ViewHolder holder, View view){
        if(apiId == DemoAPI.GET_ENVIRONMENT){
            holder.mResultView.setText("Environment is:" + MWSolana.getEnvironment());
        }else if(apiId == DemoAPI.SET_JWT){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            MirrorSDK.getInstance().SetAccessToken(String.valueOf(holder.mEt1.getText()));
            holder.mResultView.setText("JWT has been set!");
        }else if(apiId == DemoAPI.START_LOGIN){
            MWSolana.startLogin(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    //todo: Show result
                    holder.mResultView.setText(result);
                }
            },mContext);
        }else if(apiId == DemoAPI.IS_LOGGED){
            MWSolana.isLoggedIn(new BoolListener() {
                @Override
                public void onBool(boolean boolValue) {
                    holder.mResultView.setText("Current user login state:"+boolValue);
                }
            });
        }else if(apiId == DemoAPI.GUEST_LOGIN){
            MWSolana.guestLogin(new LoginListener() {
                @Override
                public void onLoginSuccess() {
                    holder.mResultView.setText("Guest login success!");
                }

                @Override
                public void onLoginFail() {
                    holder.mResultView.setText("Guest login failed!");
                }
            });
        }else if(apiId == DemoAPI.LOGOUT){
            MWSolana.logout(new BoolListener() {
                @Override
                public void onBool(boolean boolValue) {
                    holder.mResultView.setText("Logout result:"+boolValue);
                }
            });
        }else if(apiId == DemoAPI.OPEN_WALLET){
            MWSolana.openWallet(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    Log.d("MirrorSDK","Wallet logout callback runs!");
                }
            });
        }else if(apiId == DemoAPI.OPEN_MARKET){
            //Choice market url by environment
            MirrorEnv env = MWSolana.getEnvironment();
            String marketRoot;
            if(MirrorSDK.getInstance().env == MirrorEnv.StagingMainNet){
                marketRoot = "";//no url yet
            }else if(env == MirrorEnv.StagingDevNet){
                marketRoot = "https://jump-devnet.mirrorworld.fun";
            }else if(env == MirrorEnv.DevNet){
                marketRoot = "";//No url yet
            }else if(env == MirrorEnv.MainNet){
                marketRoot = "https://jump.mirrorworld.fun/";
            }else {
                MirrorSDK.getInstance().logFlow("Unknown env:"+env);
                marketRoot = "https://jump-devnet.mirrorworld.fun";
            }

            //Call API:openMarket
            MWSolana.openMarket(marketRoot);
        }else if(apiId == DemoAPI.LOGIN_With_EMAIL){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String email = String.valueOf(holder.mEt1.getText());
            String passWord = String.valueOf(holder.mEt2.getText());

            MWSolana.loginWithEmail(email,passWord,new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }else if(apiId == DemoAPI.FETCH_USER){
            MWSolana.fetchUser(new FetchUserListener() {
                @Override
                public void onUserFetched(UserResponse userResponse) {
                    holder.mResultView.setText(userResponse.email+" eth_address "+userResponse.wallet.eth_address+" solona_address "+userResponse.wallet.sol_address);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPI.QUERY_USER){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String email = String.valueOf(holder.mEt1.getText());
            MWSolana.queryUser(email,new FetchUserListener() {
                @Override
                public void onUserFetched(UserResponse userResponse) {
                    holder.mResultView.setText(userResponse.email+" sol_address "+userResponse.sol_address);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);

                }
            });
        }else if(apiId == DemoAPI.CREATE_VERIFIED_COLLECTION){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }
            String name = String.valueOf(holder.mEt1.getText());
            String symbol = String.valueOf(holder.mEt2.getText());
            String detailsUrl = String.valueOf(holder.mEt3.getText());

            MWSolana.createVerifiedCollection(name, symbol, detailsUrl, MirrorConfirmation.Default, new CreateTopCollectionListener() {
                @Override
                public void onCreateSuccess(MintResponse mintResponse) {
                    holder.mResultView.setText("Creating result is:"+ MirrorGsonUtils.getInstance().toJson(mintResponse));
                }

                @Override
                public void onCreateFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPI.MINT_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }

            String collection_mint = String.valueOf(holder.mEt1.getText());
            String detailUrl = String.valueOf(holder.mEt2.getText());

            MWSolana.mintNFT(collection_mint, detailUrl,MirrorConfirmation.Default, new MintNFTListener() {
                @Override
                public void onMintSuccess(MintResponse userResponse) {
                    MirrorSDK.getInstance().logFlow("Mint nft result:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                    holder.mResultView.setText("Mint NFT result is:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                }

                @Override
                public void onMintFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPI.UPDATE_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4) || !checkEt(holder.mEt5) || !checkEt(holder.mEt6)){
                showToast("Please input all params!");
                return;
            }
            String mintAddress = String.valueOf(holder.mEt1.getText());
            String name = String.valueOf(holder.mEt2.getText());
            String symbol = String.valueOf(holder.mEt3.getText());
            String updateAuthority = String.valueOf(holder.mEt4.getText());
            String NFTJsonUrl = String.valueOf(holder.mEt5.getText());
            int seller_fee_basis_points = Integer.parseInt(String.valueOf(holder.mEt6.getText()));

            MWSolana.updateNFTProperties(mintAddress, name, symbol, updateAuthority,NFTJsonUrl,seller_fee_basis_points, new MintNFTListener() {
                @Override
                public void onMintSuccess(MintResponse userResponse) {
                    MirrorSDK.getInstance().logFlow("Update NFT result:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                    holder.mResultView.setText("Update NFT result is:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                }

                @Override
                public void onMintFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPI.CHECK_STATUS_OFMINTING){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input all params!");
                return;
            }
            String mintAddress1 = String.valueOf(holder.mEt1.getText());
            String mintAddress2 = String.valueOf(holder.mEt2.getText());

            List<String> addresses = new ArrayList<>();
            if(!mintAddress1.isEmpty()) addresses.add(mintAddress1);
            if(!mintAddress2.isEmpty()) addresses.add(mintAddress2);

            MWSolana.checkStatusOfMinting(addresses,new CheckStatusOfMintingListener() {

                @Override
                public void onSuccess(CheckStatusOfMintingResponse response) {
                    holder.mResultView.setText("result is:" + MirrorGsonUtils.getInstance().toJson(response));
                }

                @Override
                public void onCheckFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("CHECK_STATUS_OFMINTING",code,message));
                }
            });
        }else if(apiId == DemoAPI.LIST_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());

            Double price = 0.0;

            try{
                price = Double.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MWSolana.listNFT(mint_address, price, MirrorConfirmation.Default, new ListNFTListener() {
                @Override
                public void onListSuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText("ListNFT success! price is:"+listingResponse.price);
                }

                @Override
                public void onListFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("ListNFT",code,message));
                }
            });
        }else if(apiId == DemoAPI.UPDATE_NFT_LISTING){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());
            Double price = 0.0;

            try{
                price = Double.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MWSolana.updateNFTListing(mint_address, price,MirrorConfirmation.Default, new UpdateListListener() {
                @Override
                public void onUpdateSuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText("UpdateNFTListing success! New price:"+listingResponse.price);
                }

                @Override
                public void onUpdateFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("UpdateNFTListing",code,message));
                }
            });
        }else if(apiId == DemoAPI.CANCEL_NFT_LISTING){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());
            Double price = 0.0;
            try{
                price = Double.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MWSolana.cancelNFTListing(mint_address, price,MirrorConfirmation.Default, new CancelListListener() {
                @Override
                public void onCancelSuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText("CancelNFTListing success! Mint address is "+listingResponse.mint_address);
                }

                @Override
                public void onCancelFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("CancelNFTListing",code,message));
                }
            });
        }else if(apiId == DemoAPI.FETCH_NFT_BY_OWNER_ADDRESSES){
            if(!checkEt(holder.mEt1)){
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
            MWSolana.fetchNFTsByOwnerAddresses(owners, limit, offset, new FetchByOwnerListener() {
                @Override
                public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                    int count = multipleNFTsResponse.nfts.size();
                    String countStr = "You have " + count + " NFTs";
                    holder.mResultView.setText(countStr);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPI.FETCH_NFT_BY_CREATOR){
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
            MWSolana.fetchNFTsByCreatorAddresses(owners, limit, offset, new FetchNFTsListener() {
                @Override
                public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                    int count = multipleNFTsResponse.nfts.size();
                    String countStr = "You have " + count + " NFTs";
                    holder.mResultView.setText(countStr);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPI.FETCH_NFT_BY_MINT_ADDRESSES){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            List<String> mint_address = new ArrayList<>();
            mint_address.add(String.valueOf(holder.mEt1.getText()));
            MWSolana.fetchNFTsByMintAddresses(mint_address, new FetchNFTsListener() {
                @Override
                public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                    String notice = "Fetched " + multipleNFTsResponse.nfts.size() + " NFTs";
                    holder.mResultView.setText(notice);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
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
            MWSolana.fetchNFTsByUpdateAuthorities(update_address, limit, offset, new FetchNFTsListener() {
                @Override
                public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                    holder.mResultView.setText("FetchNFTsByUpdateAuthorities success!nft count is:"+multipleNFTsResponse.nfts.size());
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("FetchNFTsByUpdateAuthorities",code,message));
                }
            });
        }else if(apiId == DemoAPI.FETCH_SINGLE_NFT_DETAILS){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String mint_address =String.valueOf(holder.mEt1.getText());
            MWSolana.getNFTDetails(mint_address, new FetchSingleNFTListener() {
                @Override
                public void onFetchSuccess(SingleNFTResponse nftObject) {
                    holder.mResultView.setText("NFT details is:"+MirrorGsonUtils.getInstance().toJson(nftObject));
                }

                @Override
                public void onFetchFailed(long code, String message) {

                }
            });
        }else if(apiId == DemoAPI.FETCH_NFT_MARKETPLACE_ACTIVITY){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String mint_address =String.valueOf(holder.mEt1.getText());
            MWSolana.fetchNFTMarketplaceActivity(mint_address, new FetchSingleNFTActivityListener() {
                @Override
                public void onFetchSuccess(ActivityOfSingleNftResponse activityOfSingleNftResponse) {
                    holder.mResultView.setText(activityOfSingleNftResponse.mintAddress);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPI.TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String to_wallet_address = String.valueOf(holder.mEt2.getText());
            MWSolana.transferNFT(mint_address, to_wallet_address, new TransferNFTListener() {
                @Override
                public void onTransferSuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText(listingResponse.mint_address);
                }

                @Override
                public void onTransferFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPI.TRANSFER_SPL_TOKEN){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4)){
                showToast("Please input!");
                return;
            }
            String toPublicKey = String.valueOf(holder.mEt1.getText());
            String amountStr = String.valueOf(holder.mEt2.getText());
            String tokenMint = String.valueOf(holder.mEt3.getText());
            String decimalsStr = String.valueOf(holder.mEt4.getText());

            float amount = 0.0f;
            int decimals = 0;
            try{
                amount =  Float.valueOf(amountStr);
                decimals =  Integer.valueOf(decimalsStr);
            }catch (NumberFormatException E){
            }

            MWSolana.transferSPLToken(toPublicKey, amount, tokenMint, decimals, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });
        }
        else if(apiId == DemoAPI.BUY_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            Double price = 0.0;
            try{
                price =  Double.valueOf(String.valueOf(holder.mEt2.getText()));
            }catch (NumberFormatException E){
            }

            MWSolana.buyNFT(mint_address, price, new BuyNFTListener() {
                @Override
                public void onBuySuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText(listingResponse.mint_address);
                }

                @Override
                public void onBuyFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPI.GET_WALLET_TOKEN){
            MWSolana.getTokens(new GetWalletTokenListener() {
                @Override
                public void onSuccess(GetWalletTokenResponse walletTokenResponse) {
                    holder.mResultView.setText("Get wallet token success!");
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("GetWalletToken",code,message));
                }
            });
        }else if(apiId == DemoAPI.GET_TOKENS_BY_WALLET){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String walletAddress = getStringFromEditText(holder.mEt1);
            MWSolana.getTokensByWallet(walletAddress, new GetWalletTokenListener() {
                @Override
                public void onSuccess(GetWalletTokenResponse walletTokenResponse) {
                    holder.mResultView.setText("Get wallet token success!");
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("GetWalletToken",code,message));
                }
            });
        }else if(apiId == DemoAPI.WALLET_TRANSACTIONS){
            if(!checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            int limit = 0;
            try{
                limit = Integer.valueOf(String.valueOf(holder.mEt1.getText()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            String before = String.valueOf(holder.mEt2.getText());
            MWSolana.getTransactionsOfLoggedUser(limit, before, new GetWalletTransactionListener() {
                @Override
                public void onSuccess(GetWalletTransactionsResponse walletTransactionsResponse) {
                    holder.mResultView.setText("GetTransactions success! count is "+walletTransactionsResponse.count);
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("Transactions",code,message));
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
            MWSolana.getTransactionsByWallet(walletAddress, limit, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText("GetTransactions success! result is "+result);
                }
            });
        }else if(apiId == DemoAPI.WALLET_TRANSACTIONS_BY_SIGNATURE){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String sig = String.valueOf(holder.mEt1.getText());
            MWSolana.getTransactionBySignature(sig, new GetOneWalletTransactionBySigListener() {
                @Override
                public void onSuccess(TransactionsDTO walletTransactions) {
                    holder.mResultView.setText("GetTransactionBySignature success!" + MirrorGsonUtils.getInstance().toJson(walletTransactions));
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("GetTransactionBySignature",code,message));
                }
            });
        }else if(apiId == DemoAPI.CHECK_STATUS_TRANSACTION){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String sig1 = String.valueOf(holder.mEt1.getText());
            String sig2 = String.valueOf(holder.mEt2.getText());
            List<String> signatures = new ArrayList<>();
            if(!sig1.isEmpty()) signatures.add(sig1);
            if(!sig2.isEmpty()) signatures.add(sig2);
            MWSolana.checkStatusOfTransactions(signatures, new CheckStatusOfMintingListener() {

                @Override
                public void onSuccess(CheckStatusOfMintingResponse response) {
                    holder.mResultView.setText("checkStatusOfTransactions success!" + MirrorGsonUtils.getInstance().toJson(response));
                }

                @Override
                public void onCheckFailed(long code, String message) {
                    holder.mResultView.setText("checkStatusOfTransactions failed!code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.TRANSFER_SOL){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String public_key = String.valueOf(holder.mEt1.getText());
            String amountStr = String.valueOf(holder.mEt2.getText());
            float amount = 0;
            try{
                amount = Integer.valueOf(amountStr);
            }catch (NumberFormatException e){

            }
            MWSolana.transferSOL(public_key, amount, new TransferSOLListener() {
                @Override
                public void onTransferSuccess(TransferResponse transferResponse) {
                    holder.mResultView.setText("transfer sol success!");
                }

                @Override
                public void onTransferFailed(long code, String message) {
                    holder.mResultView.setText("transfer sol failed!code:"+code+" message:"+message);
                }
            });
        }
//        else if(apiId == DemoAPI.WALLET_GET_TRANSACTION_OF_TRANSFER_SOL){
//            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
//                showToast("Please input!");
//                return;
//            }
//            String public_key = String.valueOf(holder.mEt1.getText());
//            String amountStr = String.valueOf(holder.mEt2.getText());
//            float amount = 0;
//            try{
//                amount = Integer.valueOf(amountStr);
//            }catch (NumberFormatException e){
//
//            }
//            MirrorWorld.getTransactionOfTransferSOL(public_key, amount, new GetWalletTransactionListener() {
//                @Override
//                public void onSuccess(GetWalletTransactionsResponse walletTransactionsResponse) {
//                    holder.mResultView.setText("getTransactionOfTransferSOL success!" + MirrorGsonUtils.getInstance().toJson(walletTransactionsResponse));
//                }
//
//                @Override
//                public void onFailed(long code, String message) {
//                    holder.mResultView.setText("transfer sol failed!code:"+code+" message:"+message);
//                }
//            });
//        }else if(apiId == DemoAPI.WALLET_GET_TRANSACTION_OF_TRANSFER_TOKEN){
//            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4)){
//                showToast("Please input!");
//                return;
//            }
//            String public_key = String.valueOf(holder.mEt1.getText());
//            String amountStr = String.valueOf(holder.mEt2.getText());
//            float amount = 0;
//            try{
//                amount = Integer.valueOf(amountStr);
//            }catch (NumberFormatException e){
//
//            }
//            String tokenMint = getStringFromEditText(holder.mEt3);
//            int decimals = 0;
//            try{
//                decimals = Integer.valueOf(getStringFromEditText(holder.mEt4));
//            }catch (NumberFormatException e){
//
//            }
//            MirrorWorld.getTransactionsOfTransferToken(public_key, amount,tokenMint,decimals, new GetWalletTransactionListener() {
//                @Override
//                public void onSuccess(GetWalletTransactionsResponse walletTransactionsResponse) {
//                    holder.mResultView.setText("getTransactionOfTransferSOL success!" + MirrorGsonUtils.getInstance().toJson(walletTransactionsResponse));
//                }
//
//                @Override
//                public void onFailed(long code, String message) {
//                    holder.mResultView.setText("transfer sol failed!code:"+code+" message:"+message);
//                }
//            });
//        }
        else if(apiId == DemoAPI.GET_COLLECTION_FILTER_INFO){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            MWSolana.getCollectionFilterInfo(collection, new GetCollectionFilterInfoListener() {
                @Override
                public void onSuccess(GetCollectionFilterInfoRes result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.GET_NFT_INFO_SOLANA){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            MWSolana.getNFTInfo(mint_address, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText("Visiting result:"+MirrorGsonUtils.getInstance().toJson(result));
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
            MWSolana.getCollectionInfo(collections, new GetCollectionInfoListener() {
                @Override
                public void onSuccess(List<GetCollectionInfoRes> result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.METADATA_GET_COLLECTION_SUMMARY){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            String collection2 = String.valueOf(holder.mEt2.getText());
            List<String> collections = new ArrayList<>();
            collections.add(collection);
            collections.add(collection2);
            MWSolana.getCollectionSummary(collections, new GetCollectionSummaryListener() {
                @Override
                public void onSuccess(List<GetCollectionSummaryRes> res) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(res));
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.GET_NFT_EVENTS_SOLANA){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }
            int page = 0;
            int page_size = 0;
            String mint_address = String.valueOf(holder.mEt1.getText());
            String pageStr = String.valueOf(holder.mEt2.getText());
            String pageSizeStr = String.valueOf(holder.mEt3.getText());
            page = Integer.valueOf(pageStr);
            page_size = Integer.valueOf(pageSizeStr);
            MWSolana.getNFTEvents(mint_address,page,page_size, new GetNFTEventsListener() {
                @Override
                public void onSuccess(GetNFTEventsRes result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
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
            MWSolana.searchNFTs(collections,searchString, new SOLSearchNFTsListener() {
                @Override
                public void onSuccess(List<MirrorMarketSearchNFTObj> result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
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
            MWSolana.recommendSearchNFT(collections, new SOLSearchNFTsListener() {
                @Override
                public void onSuccess(List<MirrorMarketSearchNFTObj> result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.GET_NFTS_SOLANA){
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
            Double sale = Double.valueOf(1);
            try{
                page = Integer.valueOf(String.valueOf(holder.mEt2.getText()));
                page_size = Integer.valueOf(String.valueOf(holder.mEt3.getText()));
                desc = Boolean.valueOf(String.valueOf(holder.mEt5.getText()));
                sale = Double.valueOf(String.valueOf(holder.mEt6.getText()));
            }catch(Exception e){

            }
            MWSolana.getNFTsByUnabridgedParams(collection,page,page_size,order_by,desc,sale,null, new GetNFTsListener() {
                @Override
                public void onSuccess(GetNFTsRes result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.GET_NFT_REAL_PRICE){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            int fee = Integer.parseInt(String.valueOf(holder.mEt2.getText()));
            String price = String.valueOf(holder.mEt1.getText());
            MWSolana.getNFTRealPrice(price,fee, new GetNFTRealPriceListener() {
                @Override
                public void onSuccess(GetNFTRealPriceRes result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }
    }
}
