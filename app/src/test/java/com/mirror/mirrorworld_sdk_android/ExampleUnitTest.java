package com.mirror.mirrorworld_sdk_android;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

import com.mirror.sdk.MirrorCallback;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.market.CancelListListener;
import com.mirror.sdk.listener.market.CreateSubCollectionListener;
import com.mirror.sdk.listener.market.CreateTopCollectionListener;
import com.mirror.sdk.listener.market.FetchNFTsListener;
import com.mirror.sdk.listener.market.FetchByOwnerListener;
import com.mirror.sdk.listener.market.FetchSingleNFTActivityListener;
import com.mirror.sdk.listener.market.FetchSingleNFTListener;
import com.mirror.sdk.listener.market.ListNFTListener;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.listener.market.TransferNFTListener;
import com.mirror.sdk.listener.market.UpdateListListener;
import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
import com.mirror.sdk.listener.wallet.TransactionsDTO;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.response.market.MintResponse;
import com.mirror.sdk.response.market.MultipleNFTsResponse;
import com.mirror.sdk.response.market.SingleNFTResponse;
import com.mirror.sdk.response.wallet.GetWalletTokenResponse;
import com.mirror.sdk.response.wallet.GetWalletTransactionsResponse;
import com.mirror.sdk.response.wallet.TransferResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private String  Status;

    private String appid = "I26bks9naPo3lJRtAvApRBhkqzrkKOdePhD";

    private String userEmail = "squall19871987@163.com";

    private String password = "yuebaobao";


    private String OtherAppId = "E6Drdi3qYsX1WkPoTFardZY7XF0AOXVrDq4";

    private String OtherUserEmail = "kukret@163.com";

    private String OtherPassword = "KYg56897";


    class TestCounter{
        public int finishCount = 0;
    }
    private String GetStatus(String res){

        String status = null;
        try {

            JSONObject jsonObject = new JSONObject(res);
            status = jsonObject.getString("status");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(null == status){
            return "error";
        }

        return status;

    }

    private String GetRefreshTokenFromResponse(String response){
        String refreshToken = null;
        try {

            JSONObject jsonObject = new JSONObject(response);
            refreshToken  = jsonObject.getJSONObject("data").getString("refresh_token");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(null == refreshToken ){
            return "error";
        }

        return refreshToken;
    }

    private String GetAccessTokenFromResponse(String response){
        String accessToken = null;
        try {

            JSONObject jsonObject = new JSONObject(response);
            accessToken = jsonObject.getJSONObject("data").getString("access_token");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(null == accessToken){
            return "error";
        }

        return accessToken;
    }

    @Test
    public void QueryAndFetchUser(){
        TestCounter counter = new TestCounter();
        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock1 = new Object();
        final Object lock2 = new Object();
        MirrorSDK.getInstance().SetApiKey(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().FetchUser(new FetchUserListener() {
                    @Override
                    public void onUserFetched(UserResponse userResponse) {
                        counter.finishCount ++;
                        Status = "success";
                        synchronized (lock1) {
                            lock1.notify();
                        }
                    }

                    @Override
                    public void onFetchFailed(long code, String message) {
                        counter.finishCount ++;
                        Status = "Failed";
                        synchronized (lock1) {
                            lock1.notify();
                        }
                    }
                });

                MirrorSDK.getInstance().QueryUser(userEmail, new FetchUserListener() {
                    @Override
                    public void onUserFetched(UserResponse userResponse) {
                        counter.finishCount ++;
                        Status = "success";
                        synchronized (lock2) {
                            lock2.notify();
                        }
                    }

                    @Override
                    public void onFetchFailed(long code, String message) {
                        counter.finishCount ++;
                        Status = "failed";
                        synchronized (lock2) {
                            lock2.notify();
                        }
                    }
                });
            }
        });

        try {
            synchronized (lock1) {
                lock1.wait();
            }
            synchronized (lock2) {
                lock2.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("success",Status);
    }

    @Test
    public void MintNFTFlow(){
        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lockFetchByCreator = new Object();
        final Object lockFetchByAuth = new Object();
        final Object lockFetchByMint = new Object();
        final Object lockMintSuccess = new Object();
        MirrorSDK.getInstance().SetApiKey(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().CreateVerifiedCollection("confirm", "lll", "https://mirror-nft.s3.us-west-2.amazonaws.com/assets/111.json", new CreateTopCollectionListener() {
                    @Override
                    public void onCreateSuccess(MintResponse mintResponse) {
                        MirrorSDK.getInstance().CreateVerifiedSubCollection("FbDkjpV1YSsmY3zrGGkK5H33ed33APfdRXToQPg52PMr", "py19", "symbol13U", "https://mirror-nft.s3.us-west-2.amazonaws.com/assets/111.json", new CreateSubCollectionListener() {
                            @Override
                            public void onCreateSuccess(MintResponse userResponse) {
                                MirrorSDK.getInstance().MintNFT("9mkx2CDjRa64xEpUxyBJKbBC4NRAQhEJGDN8Ei8xHRWi", "nameL", "symbolK", "https://market-assets.mirrorworld.fun/gen1/1.json", new MintNFTListener() {
                                    @Override
                                    public void onMintSuccess(MintResponse userResponse) {
                                        synchronized (lockMintSuccess) {
                                            lockMintSuccess.notify();
                                        }

//                                        String nftAddress = userResponse.mint_address;
//                                        MirrorSDK.getInstance().GetNFTDetails(nftAddress, new FetchSingleNFTListener() {
//
//                                            @Override
//                                            public void onFetchSuccess(SingleNFTResponse nftResponse) {
//                                                Status = "success";
//                                                synchronized (lockFetchByMint) {
//                                                    lockFetchByMint.notify();
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onFetchFailed(long code, String message) {
//                                                Status = "Failed";
//                                                synchronized (lockFetchByMint) {
//                                                    lockFetchByMint.notify();
//                                                }
//                                            }
//                                        });

                                        List<String> creators = new ArrayList<>();
                                        creators.add(userResponse.creator_address);
                                        Double limit = 10.0;
                                        MirrorSDK.getInstance().FetchNFTsByCreatorAddresses(creators, limit, limit, new FetchNFTsListener() {
                                            @Override
                                            public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                                                Status = "success";
                                                synchronized (lockFetchByCreator) {
                                                    lockFetchByCreator.notify();
                                                }
                                            }

                                            @Override
                                            public void onFetchFailed(long code, String message) {
                                                Status = "success";
                                                synchronized (lockFetchByCreator) {
                                                    lockFetchByCreator.notify();
                                                }
                                            }
                                        });

                                        List<String> authers = new ArrayList<>();
                                        authers.add(userResponse.update_authority);
                                        MirrorSDK.getInstance().FetchNFTsByUpdateAuthorities(authers, limit, limit, new FetchNFTsListener() {
                                            @Override
                                            public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                                                Status = "success";
                                                synchronized (lockFetchByAuth) {
                                                    lockFetchByAuth.notify();
                                                }
                                            }

                                            @Override
                                            public void onFetchFailed(long code, String message) {
                                                Status = "success";
                                                synchronized (lockFetchByAuth) {
                                                    lockFetchByAuth.notify();
                                                }
                                            }
                                        });
                                    }

                                    @Override
                                    public void onMintFailed(long code, String message) {
                                        Status = "Failed";
                                        synchronized (lockMintSuccess) {
                                            lockMintSuccess.notify();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onCreateFailed(long code, String message) {
                                Status = "Failed";
                                synchronized (lockMintSuccess) {
                                    lockMintSuccess.notify();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCreateFailed(long code, String message) {
                        Status = "Failed";
                        synchronized (lockMintSuccess) {
                            lockMintSuccess.notify();
                        }
                    }
                });
            }
        });

        try {
            synchronized (lockMintSuccess) {
                lockMintSuccess.wait();
            }

            synchronized (lockFetchByCreator) {
                lockFetchByCreator.wait();
            }

            synchronized (lockFetchByAuth) {
                lockFetchByAuth.wait();
            }

//            synchronized (lockFetchByMint) {
//                lockFetchByMint.wait();
//            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("success",Status);
    }

    @Test
    public void TransferNFTToAnotherSolanaWallet(){

        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock = new Object();
        MirrorSDK.getInstance().SetApiKey(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));
                MirrorSDK.getInstance().TransferNFTToAnotherSolanaWallet("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx", "B63XUAv3ureYH9iJnQFaHnz94FPPMEjoFK9Psvv4bMPs", new TransferNFTListener() {
                    @Override
                    public void onTransferSuccess(ListingResponse listingResponse) {
                        Status = "success";
                        synchronized (lock) {
                            lock.notify();
                        }
                    }

                    @Override
                    public void onTransferFailed(long code, String message) {
                        Status = "fail";
                        synchronized (lock) {
                            lock.notify();
                        }
                    }
                });
            }
        });

        try {
            synchronized (lock) {
                lock.wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("success",Status);
    }

    @Test
    public void ListNFT(){
        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock = new Object();
        MirrorSDK.getInstance().SetApiKey(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));
                MirrorSDK.getInstance().ListNFT("DxL8GuDoqWLqMLkeLQmaDVh4jR25zbhZQeYh3nbaqw1D", 1.0,MirrorConfirmation.Finalized,  new ListNFTListener() {
                    @Override
                    public void onListSuccess(ListingResponse listingResponse) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        MirrorSDK.getInstance().UpdateNFTListing("DxL8GuDoqWLqMLkeLQmaDVh4jR25zbhZQeYh3nbaqw1D", 1.3, new UpdateListListener() {
                            @Override
                            public void onUpdateSuccess(ListingResponse listingResponse) {
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                MirrorSDK.getInstance().CancelNFTListing("DxL8GuDoqWLqMLkeLQmaDVh4jR25zbhZQeYh3nbaqw1D", 1.3, new CancelListListener() {
                                    @Override
                                    public void onCancelSuccess(ListingResponse listingResponse) {
                                        try {
                                            Thread.sleep(3000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        Status = GetStatus( result);
                                        synchronized (lock) {
                                            lock.notify();
                                        }
                                    }

                                    @Override
                                    public void onCancelFailed(long code, String message) {
                                        Status = "Failed";
                                        synchronized (lock) {
                                            lock.notify();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onUpdateFailed(long code, String message) {
                                Status = "Failed";
                                synchronized (lock) {
                                    lock.notify();
                                }
                            }
                        });
                    }

                    @Override
                    public void onListFailed(long code, String message) {
                        Status = "Failed";
                        synchronized (lock) {
                            lock.notify();
                        }
                    }
                });
            }
        });

        try {
            synchronized (lock) {
                lock.wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("success",Status);
    }

    @Test
    public void FetchNFTsByOwnerAddresses(){
        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        List<String> owners = new ArrayList<>();
        owners.add("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx");

        final Object lock = new Object();
        MirrorSDK.getInstance().SetApiKey(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().FetchNFTsByOwnerAddresses(owners, 1, 1, new FetchByOwnerListener() {
                    @Override
                    public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                        Status = "success";
                        synchronized (lock) {
                            lock.notify();
                        }
                    }

                    @Override
                    public void onFetchFailed(long code, String message) {
                        Status = "fail";
                        synchronized (lock) {
                            lock.notify();
                        }

                    }
                });
            }
        });


        try {
            synchronized (lock) {
                lock.wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("success",Status);

    }

    @Test
    public void FetchActivitiesOfSingleNFT(){
        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock = new Object();
        MirrorSDK.getInstance().SetApiKey(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().FetchNFTMarketplaceActivity("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx", new FetchSingleNFTActivityListener() {
                    @Override
                    public void onFetchSuccess(ActivityOfSingleNftResponse activityOfSingleNftResponse) {
                        Status = "success";
                        synchronized (lock) {
                            lock.notify();
                        }
                    }

                    @Override
                    public void onFetchFailed(long code, String message) {
                        Status = "fail";
                        synchronized (lock) {
                            lock.notify();
                        }

                    }
                });
            }
        });


        try {
            synchronized (lock) {
                lock.wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("success",Status);

    }

    @Test
    public void FetchNFTsByMintAddress(){
        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        List<String> mint_address = new ArrayList<>();
        mint_address.add("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx");

        final Object lock = new Object();
        MirrorSDK.getInstance().SetApiKey(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().FetchNFTsByMintAddresses(mint_address, new FetchNFTsListener() {
                    @Override
                    public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                        Status = "success";
                        synchronized (lock) {
                            lock.notify();
                        }
                    }

                    @Override
                    public void onFetchFailed(long code, String message) {
                        Status = "fail";
                        synchronized (lock) {
                            lock.notify();
                        }

                    }
                });
            }
        });


        try {
            synchronized (lock) {
                lock.wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("success",Status);

    }

     @Test
     public void GetWalletToken(){
         MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
         final Object lock = new Object();
         MirrorSDK.getInstance().SetApiKey(appid);
         MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
             @Override
             public void callback(String result) {

                 MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                 MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                 MirrorSDK.getInstance().GetWalletTokens(new GetWalletTokenListener() {
                     @Override
                     public void onSuccess(GetWalletTokenResponse walletTokenResponse) {
                         Status = GetStatus( result);
                         synchronized (lock) {
                             lock.notify();
                         }
                     }

                     @Override
                     public void onFailed(long code, String message) {
                         Status = "Failed";
                         synchronized (lock) {
                             lock.notify();
                         }
                     }
                 });
             }
         });


         try {
             synchronized (lock) {
                 lock.wait();
             }

         } catch (InterruptedException e) {
             e.printStackTrace();
         }

         assertEquals("success",Status);


     }

     @Test
     public void WalletTransactions(){
         MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
         final Object lock = new Object();
         MirrorSDK.getInstance().SetApiKey(appid);
         MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
             @Override
             public void callback(String result) {

                 MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                 MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                 MirrorSDK.getInstance().Transactions("1", "5pTshp58jboUBUqHEPSn6KZ6hCc6ZU7NL4BghjQU15J8vBitKSaHqc8ms5XCkbUByYnabEY8MS8H12RbzzAMUxBn", new GetWalletTransactionListener() {
                     @Override
                     public void onSuccess(GetWalletTransactionsResponse walletTransactionsResponse) {
                         Status = GetStatus( result);
                         synchronized (lock) {
                             lock.notify();
                         }
                     }

                     @Override
                     public void onFailed(long code, String message) {
                         Status = "Failed";
                         synchronized (lock) {
                             lock.notify();
                         }
                     }
                 });
             }
         });


         try {
             synchronized (lock) {
                 lock.wait();
             }

         } catch (InterruptedException e) {
             e.printStackTrace();
         }

         assertEquals("success",Status);
    }

    @Test
    public void WalletTransactionsBySignature(){
        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock = new Object();
        MirrorSDK.getInstance().SetApiKey(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().GetTransactionBySignature("5pTshp58jboUBUqHEPSn6KZ6hCc6ZU7NL4BghjQU15J8vBitKSaHqc8ms5XCkbUByYnabEY8MS8H12RbzzAMUxBn", new GetWalletTransactionBySigListener() {
                    @Override
                    public void onSuccess(List<TransactionsDTO> walletTransactions) {
                        Status = GetStatus( result);
                        synchronized (lock) {
                            lock.notify();
                        }
                    }

                    @Override
                    public void onFailed(long code, String message) {
                        Status = "Failed";
                        synchronized (lock) {
                            lock.notify();
                        }
                    }
                });
            }
        });


        try {
            synchronized (lock) {
                lock.wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("success",Status);
    }

    @Test
    public void TransferSOL(){
        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock = new Object();
        MirrorSDK.getInstance().SetApiKey(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NTg2MCwiZXRoX2FkZHJlc3MiOiJCNjNYVUF2M3VyZVlIOWlKblFGYUhuejk0RlBQTUVqb0ZLOVBzdnY0Yk1QcyIsInNvbF9hZGRyZXNzIjoiQjYzWFVBdjN1cmVZSDlpSm5RRmFIbno5NEZQUE1Fam9GSzlQc3Z2NGJNUHMiLCJlbWFpbCI6InNxdWFsbDE5ODcxOTg3QDE2My5jb20iLCJ3YWxsZXQiOnsiZXRoX2FkZHJlc3MiOiIweDYyODRmNTk2MTNCN2MxMDliNWQ1NjA3NmMxRjcxMDY2OGExRkUyQWUiLCJzb2xfYWRkcmVzcyI6IkI2M1hVQXYzdXJlWUg5aUpuUUZhSG56OTRGUFBNRWpvRks5UHN2djRiTVBzIn0sImNsaWVudF9pZCI6bnVsbCwiaWF0IjoxNjYxMjUxNDQyLCJleHAiOjE2NjM4NDM0NDIsImp0aSI6ImF1dGg6NTg2MCJ9.efc0hlWvNRrV9XOQ309j-W95hT_deP8__M5pz8w380A");
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().TransferSOL("HkGWQxFspfcaHQbbnnwwGrUDGyKFTYmFgSrB6p238Tqz", 10, new TransferSOLListener() {
                    @Override
                    public void onTransferSuccess(TransferResponse transferResponse) {
                        Status = GetStatus( result);
                        synchronized (lock) {
                            lock.notify();
                        }
                    }

                    @Override
                    public void onTransferFailed(long code, String message) {
                        Status = "Failed";
                        synchronized (lock) {
                            lock.notify();
                        }
                    }
                });
            }
        });

        try {
            synchronized (lock) {
                lock.wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("success",Status);
    }
}