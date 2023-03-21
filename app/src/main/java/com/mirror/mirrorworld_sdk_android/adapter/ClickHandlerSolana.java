package com.mirror.mirrorworld_sdk_android.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.mirror.mirrorworld_sdk_android.enums.DemoAPI;
import com.mirror.sdk.MWEVM;
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

    public void handleClick(Activity returnActivity, DemoAPI apiId, MultiParaItemRecyclerViewAdapter.ViewHolder holder, View view){
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
                    runInUIThread(holder,result);
                }
            }, mActivity);
        }else if(apiId == DemoAPI.IS_LOGGED){
            MWSolana.isLoggedIn(new BoolListener() {
                @Override
                public void onBool(boolean boolValue) {
                    String r = "Current user login state:"+boolValue;
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.GUEST_LOGIN){
            MWSolana.guestLogin(new LoginListener() {
                @Override
                public void onLoginSuccess() {
                    runInUIThread(holder,"Guest login success!");
                }

                @Override
                public void onLoginFail() {
                    runInUIThread(holder,"Guest login failed!");
                }
            });
        }else if(apiId == DemoAPI.LOGOUT){
            MWSolana.logout(new BoolListener() {
                @Override
                public void onBool(boolean boolValue) {
                    runInUIThread(holder,"Logout result:"+boolValue);
                }
            });
        }else if(apiId == DemoAPI.OPEN_WALLET){
            MWSolana.openWallet(mActivity,new MirrorCallback() {
                @Override
                public void callback(String result) {
                    runInUIThread(holder,"Wallet logout callback runs!");
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

            if(marketRoot.isEmpty()){
                showToast("Threre is no marketplace in this environment!");
                return;
            }
            //Call API:openMarket
            MWSolana.openMarket(marketRoot,mActivity);
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
                    runInUIThread(holder,s);
                }
            });
        }else if(apiId == DemoAPI.FETCH_USER){
            MWSolana.fetchUser(new FetchUserListener() {
                @Override
                public void onUserFetched(UserResponse userResponse) {
                    String r = userResponse.email+" eth_address "+userResponse.wallet.eth_address+" solona_address "+userResponse.wallet.sol_address;
                    runInUIThread(holder,r);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    runInUIThread(holder,message);
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
                    String r = userResponse.email+" sol_address "+userResponse.sol_address;
                    runInUIThread(holder,r);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    runInUIThread(holder,message);
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

            MWSolana.createVerifiedCollection(mActivity, name, symbol, detailsUrl, MirrorConfirmation.Default, new CreateTopCollectionListener() {
                @Override
                public void onCreateSuccess(MintResponse mintResponse) {
                    runInUIThread(holder,"Creating result is:"+ MirrorGsonUtils.getInstance().toJson(mintResponse));
                }

                @Override
                public void onCreateFailed(long code, String message) {
                    runInUIThread(holder,message);
                }
            });
        }else if(apiId == DemoAPI.MINT_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }

            String collection_mint = String.valueOf(holder.mEt1.getText());
            String detailUrl = String.valueOf(holder.mEt2.getText());

            MWSolana.mintNFT(mActivity, collection_mint, detailUrl,MirrorConfirmation.Default, new MintNFTListener() {
                @Override
                public void onMintSuccess(MintResponse userResponse) {
                    MirrorSDK.getInstance().logFlow("Mint nft result:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                    runInUIThread(holder,"Mint NFT result is:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                }

                @Override
                public void onMintFailed(long code, String message) {
                    runInUIThread(holder,message);
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

            MWSolana.updateNFTProperties(mActivity, mintAddress, name, symbol, updateAuthority,NFTJsonUrl,seller_fee_basis_points, new MintNFTListener() {
                @Override
                public void onMintSuccess(MintResponse userResponse) {
                    MirrorSDK.getInstance().logFlow("Update NFT result:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                    runInUIThread(holder,"Update NFT result is:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                }

                @Override
                public void onMintFailed(long code, String message) {
                    runInUIThread(holder,message);
                }
            });
        }else if(apiId == DemoAPI.CHECK_STATUS_OFMINTING){
            if(!checkEt(holder.mEt1) && !checkEt(holder.mEt2)){
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
                    runInUIThread(holder,MirrorGsonUtils.getInstance().toJson(response));
                }

                @Override
                public void onCheckFailed(long code, String message) {
                    runInUIThread(holder,MirrorStringUtils.GetFailedNotice("CHECK_STATUS_OFMINTING",code,message));
                }
            });
        }else if(apiId == DemoAPI.LIST_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());

            if(!isDouble(priceStr)){
                showToast("price must be a double!");
                return;
            }
            double price = getDouble(priceStr);
            MWSolana.listNFT(mActivity, mint_address, price, MirrorConfirmation.Default, new ListNFTListener() {
                @Override
                public void onListSuccess(ListingResponse listingResponse) {
                    String result = "ListNFT success! price is:"+listingResponse.price;
                    runInUIThread(holder,result);
                }

                @Override
                public void onListFailed(long code, String message) {
                    String result = MirrorStringUtils.GetFailedNotice("ListNFT",code,message);
                    runInUIThread(holder,result);
                }
            });
        }else if(apiId == DemoAPI.CANCEL_NFT_LISTING){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());
            String auctionHouse = String.valueOf(holder.mEt3.getText());

            if(!isDouble(priceStr)){
                showToast("price must be a double!");
                return;
            }
            double price = getDouble(priceStr);

            MWSolana.cancelNFTListing(mActivity, mint_address, price, auctionHouse, new CancelListListener() {
                @Override
                public void onCancelSuccess(ListingResponse listingResponse) {
                    String result = "CancelNFTListing success! Mint address is "+listingResponse.mint_address;
                    runInUIThread(holder,result);
                }

                @Override
                public void onCancelFailed(long code, String message) {
                    String result = MirrorStringUtils.GetFailedNotice("CancelNFTListing",code,message);
                    runInUIThread(holder,result);
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
                    runInUIThread(holder,countStr);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    runInUIThread(holder,message);
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
            MWSolana.fetchNFTsByCreatorAddresses(owners, limit, offset, new FetchNFTsListener() {
                @Override
                public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                    int count = multipleNFTsResponse.nfts.size();
                    String countStr = "You have " + count + " NFTs";
                    runInUIThread(holder,countStr);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    runInUIThread(holder,message);
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
                    runInUIThread(holder,notice);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    runInUIThread(holder,message);
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
                    String result = "FetchNFTsByUpdateAuthorities success!nft count is:"+multipleNFTsResponse.nfts.size();
                    runInUIThread(holder,result);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    String result = MirrorStringUtils.GetFailedNotice("FetchNFTsByUpdateAuthorities",code,message);
                    runInUIThread(holder,result);
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
                    String result = "NFT details is:"+MirrorGsonUtils.getInstance().toJson(nftObject);
                    runInUIThread(holder,result);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    String result = MirrorStringUtils.GetFailedNotice("FetchNFTByUpdateAuthorities",code,message);
                    runInUIThread(holder,result);
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
                    String result = activityOfSingleNftResponse.mintAddress;
                    runInUIThread(holder,result);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    runInUIThread(holder,message);
                }
            });
        }else if(apiId == DemoAPI.TRANSFER_NFT_TO_ANOTHER_WALLET){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String to_wallet_address = String.valueOf(holder.mEt2.getText());
            MWSolana.transferNFT(mActivity, mint_address, to_wallet_address,MirrorConfirmation.Default, new TransferNFTListener() {
                @Override
                public void onTransferSuccess(ListingResponse listingResponse) {
                    runInUIThread(holder,listingResponse.mint_address);
                }

                @Override
                public void onTransferFailed(long code, String message) {
                    runInUIThread(holder,message);
                }
            });
        }else if(apiId == DemoAPI.TRANSFER_TOKEN){
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

            MWSolana.transferSPLToken(mActivity, toPublicKey, amount, tokenMint, decimals, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    runInUIThread(holder,result);
                }
            });
        }
        else if(apiId == DemoAPI.BUY_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String doubleStr = String.valueOf(holder.mEt2.getText());
            String auctionHouse = String.valueOf(holder.mEt3.getText());
            if(!isDouble(doubleStr)){
                showToast("price must be a double!");
                return;
            }
            double price = getDouble(doubleStr);

            MWSolana.buyNFT(mActivity, mint_address, price, auctionHouse, new BuyNFTListener() {
                @Override
                public void onBuySuccess(ListingResponse listingResponse) {
                    runInUIThread(holder,listingResponse.mint_address);
                }

                @Override
                public void onBuyFailed(long code, String message) {
                    runInUIThread(holder,message);
                }
            });
        }else if(apiId == DemoAPI.GET_WALLET_TOKEN){
            MWSolana.getTokens(new GetWalletTokenListener() {
                @Override
                public void onSuccess(GetWalletTokenResponse walletTokenResponse) {
                    runInUIThread(holder,"Get wallet token success!");
                }

                @Override
                public void onFailed(long code, String message) {
                    runInUIThread(holder,MirrorStringUtils.GetFailedNotice("GetWalletToken",code,message));
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
                    runInUIThread(holder,"Get wallet token success!");
                }

                @Override
                public void onFailed(long code, String message) {
                    runInUIThread(holder,MirrorStringUtils.GetFailedNotice("GetWalletToken",code,message));
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
                    String result = "GetTransactions success! count is "+walletTransactionsResponse.count;
                    runInUIThread(holder,result);
                }

                @Override
                public void onFailed(long code, String message) {
                    String result = MirrorStringUtils.GetFailedNotice("Transactions",code,message);
                    runInUIThread(holder,result);
                }
            });
        }else if(apiId == DemoAPI.WALLET_TRANSACTIONS_BY_WALLET){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }
            String walletAddress = getStringFromEditText(holder.mEt1);
            String limitStr = String.valueOf(holder.mEt2.getText());
            String nextBeforeStr = getStringFromEditText(holder.mEt3);
            if(!isInteger(limitStr)){
                showToast("token_id must be an integer!");
                return;
            }
            int limit = getInteger(limitStr);
            MWSolana.getTransactionsByWallet(walletAddress, limit, nextBeforeStr, new MirrorCallback() {
                @Override
                public void callback(String r) {
                    String result = "GetTransactions success! result is "+r;
                    runInUIThread(holder,result);
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
                    String result = "GetTransactionBySignature success!" + MirrorGsonUtils.getInstance().toJson(walletTransactions);
                    runInUIThread(holder,result);
                }

                @Override
                public void onFailed(long code, String message) {
                    String result = MirrorStringUtils.GetFailedNotice("GetTransactionBySignature",code,message);
                    runInUIThread(holder,result);
                }
            });
        }else if(apiId == DemoAPI.CHECK_STATUS_TRANSACTION){
            if(!checkEt(holder.mEt1) && !checkEt(holder.mEt2)){
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
                    String result = "checkStatusOfTransactions success!" + MirrorGsonUtils.getInstance().toJson(response);
                    runInUIThread(holder,result);
                }

                @Override
                public void onCheckFailed(long code, String message) {
                    String result = "checkStatusOfTransactions failed!code:"+code+" message:"+message;
                    runInUIThread(holder,result);
                }
            });
        }else if(apiId == DemoAPI.TRANSFER_SOL){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String public_key = String.valueOf(holder.mEt1.getText());
            String amountStr = String.valueOf(holder.mEt2.getText());
            if(!isInteger(amountStr)){
                showToast("token_id must be an float!");
                return;
            }
            int amount = getInteger(amountStr);

            MWSolana.transferSOL(mActivity, public_key, amount, new TransferSOLListener() {
                @Override
                public void onTransferSuccess(TransferResponse transferResponse) {
                    String result = "transfer sol success!";
                    runInUIThread(holder,result);
                }

                @Override
                public void onTransferFailed(long code, String message) {
                    String result = "transfer sol failed!code:"+code+" message:"+message;
                    runInUIThread(holder,result);
                }
            });
        }
        else if(apiId == DemoAPI.GET_COLLECTION_FILTER_INFO){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            MWSolana.getCollectionFilterInfo(collection, new GetCollectionFilterInfoListener() {
                @Override
                public void onSuccess(GetCollectionFilterInfoRes r) {
                    String result = "Visiting success:"+MirrorGsonUtils.getInstance().toJson(r);
                    runInUIThread(holder,result);
                }

                @Override
                public void onFail(long code, String message) {
                    String result = "Visit Failed! code:"+code+" message:"+message;
                    runInUIThread(holder,result);
                }
            });
        }else if(apiId == DemoAPI.METADATA_GET_NFT_INFO){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            MWSolana.getNFTInfo(mint_address, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = "Visiting result:"+MirrorGsonUtils.getInstance().toJson(result);
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
            MWSolana.getCollectionInfo(collections, new GetCollectionInfoListener() {
                @Override
                public void onSuccess(List<GetCollectionInfoRes> result) {
                    String r = "Visiting success:"+MirrorGsonUtils.getInstance().toJson(result);
                    runInUIThread(holder,r);
                }

                @Override
                public void onFail(long code, String message) {
                    String r = "Visit Failed! code:"+code+" message:"+message;
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
            MWSolana.getCollectionSummary(collections, new GetCollectionSummaryListener() {
                @Override
                public void onSuccess(List<GetCollectionSummaryRes> res) {
                    String r = "Visiting success:"+MirrorGsonUtils.getInstance().toJson(res);
                    runInUIThread(holder,r);
                }

                @Override
                public void onFailed(long code, String message) {
                    String r = "Visit Failed! code:"+code+" message:"+message;
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.GET_NFT_EVENTS){
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
                    String r = "Visiting success:"+MirrorGsonUtils.getInstance().toJson(result);
                    runInUIThread(holder,r);
                }

                @Override
                public void onFail(long code, String message) {
                    String r = "Visit Failed! code:"+code+" message:"+message;
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
            MWSolana.searchNFTs(collections,searchString, new SOLSearchNFTsListener() {
                @Override
                public void onSuccess(List<MirrorMarketSearchNFTObj> result) {
                    String r = "Visiting success:"+MirrorGsonUtils.getInstance().toJson(result);
                    runInUIThread(holder,r);
                }

                @Override
                public void onFail(long code, String message) {
                    String r = "Visit Failed! code:"+code+" message:"+message;
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
            MWSolana.recommendSearchNFT(collections, new SOLSearchNFTsListener() {
                @Override
                public void onSuccess(List<MirrorMarketSearchNFTObj> result) {
                    String r = "Visiting success:"+MirrorGsonUtils.getInstance().toJson(result);
                    runInUIThread(holder,r);
                }

                @Override
                public void onFail(long code, String message) {
                    String r = "Visit Failed! code:"+code+" message:"+message;
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
            MWSolana.getNFTsByUnabridgedParams(collection,page,page_size,order_by,desc,sale,null, new GetNFTsListener() {
                @Override
                public void onSuccess(GetNFTsRes result) {
                    String r = "Visiting success:"+MirrorGsonUtils.getInstance().toJson(result);
                    runInUIThread(holder,r);
                }

                @Override
                public void onFail(long code, String message) {
                    String r = "Visit Failed! code:"+code+" message:"+message;
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
            MWSolana.getNFTRealPrice(price,fee, new GetNFTRealPriceListener() {
                @Override
                public void onSuccess(GetNFTRealPriceRes result) {
                    String r = "Visiting success:"+MirrorGsonUtils.getInstance().toJson(result);
                    runInUIThread(holder,r);
                }

                @Override
                public void onFail(long code, String message) {
                    String r = "Visit Failed! code:"+code+" message:"+message;
                    runInUIThread(holder,r);
                }
            });
        }
    }
}
