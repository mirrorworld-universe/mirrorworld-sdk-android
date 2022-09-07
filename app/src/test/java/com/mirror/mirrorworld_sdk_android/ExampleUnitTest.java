package com.mirror.mirrorworld_sdk_android;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

import com.mirror.sdk.MirrorCallback;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.listener.MirrorListener;
import com.mirror.sdk.listener.market.CancelListListener;
import com.mirror.sdk.listener.market.ListNFTListener;
import com.mirror.sdk.listener.market.UpdateListListener;
import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.response.market.NFTObject;
import com.mirror.sdk.response.wallet.GetWalletTokenResponse;
import com.mirror.sdk.response.wallet.GetWalletTransactionsResponse;
import com.mirror.sdk.response.wallet.TransferResponse;
import com.mirror.sdk.response.wallet.WalletTransaction;

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

    private String GetWalletAddress(String response){
        String address = null;
        try {

            JSONObject jsonObject = new JSONObject(response);
            address = jsonObject.getJSONObject("data").getJSONObject("wallet").getString("sol_address");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(null == address){
            return "error";
        }

        return address;
    }

    @Test
    public void LoginWithEmail(){

        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock = new Object();
        MirrorSDK.getInstance().SetAppID(appid);

        MirrorSDK.getInstance().LoginWithEmail(userEmail,password, new MirrorCallback() {
            @Override
            public void callback(String result) {
                Status =GetStatus( result);
                synchronized (lock) {
                    lock.notify();
                }

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
    public void FetchUser(){

        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock = new Object();
        MirrorSDK.getInstance().SetAppID(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().FetchUser(new MirrorListener.FetchUserListener() {
                    @Override
                    public void onUserFetched(UserResponse userResponse) {
                        Status = "success";
                        synchronized (lock) {
                            lock.notify();
                        }
                    }

                    @Override
                    public void onFetchFailed(long code, String message) {
                        Status = "failed";
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
    public void QueryUser(){
        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock = new Object();
        MirrorSDK.getInstance().SetAppID(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().QueryUser(userEmail, new MirrorListener.FetchUserListener() {
                    @Override
                    public void onUserFetched(UserResponse userResponse) {
                        Status = "success";
                        synchronized (lock) {
                            lock.notify();
                        }
                    }

                    @Override
                    public void onFetchFailed(long code, String message) {
                        Status = "failed";
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
    public void CreateVerifiedCollection(){
        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock = new Object();
        MirrorSDK.getInstance().SetAppID(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Unknown);
                MirrorSDK.getInstance().CreateVerifiedCollection("confirm", "lll", "https://mirror-nft.s3.us-west-2.amazonaws.com/assets/111.json", new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        MirrorSDK.getInstance().CreateVerifiedSubCollection("FbDkjpV1YSsmY3zrGGkK5H33ed33APfdRXToQPg52PMr", "py19", "symbol13U", "https://mirror-nft.s3.us-west-2.amazonaws.com/assets/111.json", new MirrorCallback() {
                            @Override
                            public void callback(String result) {
                                MirrorSDK.getInstance().MintNFT("9mkx2CDjRa64xEpUxyBJKbBC4NRAQhEJGDN8Ei8xHRWi", "nameL", "symbolK", "https://market-assets.mirrorworld.fun/gen1/1.json", new MirrorCallback() {
                                    @Override
                                    public void callback(String result) {
                                        Status = GetStatus( result);
                                        synchronized (lock) {
                                            lock.notify();
                                        }

                                    }
                                });
                            }
                        });
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
    public void TransferNFTToAnotherSolanaWallet(){

        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock = new Object();
        MirrorSDK.getInstance().SetAppID(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));
                MirrorSDK.getInstance().TransferNFTToAnotherSolanaWallet("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx", "B63XUAv3ureYH9iJnQFaHnz94FPPMEjoFK9Psvv4bMPs", new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Status = GetStatus( result);
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
        MirrorSDK.getInstance().SetAppID(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));
                MirrorSDK.getInstance().ListNFT("DxL8GuDoqWLqMLkeLQmaDVh4jR25zbhZQeYh3nbaqw1D", 1.2, new ListNFTListener() {
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

                                    }
                                });
                            }

                            @Override
                            public void onUpdateFailed(long code, String message) {

                            }
                        });
                    }

                    @Override
                    public void onListFailed(long code, String message) {

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
        MirrorSDK.getInstance().SetAppID(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().FetchNFTsByOwnerAddresses( owners,1,1,new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Status = GetStatus( result);
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
        MirrorSDK.getInstance().SetAppID(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().FetchNFTMarketplaceActivity("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx",new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Status = GetStatus( result);
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
    public void FetchSingleNFTDetails(){
        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock = new Object();
        MirrorSDK.getInstance().SetAppID(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));


                MirrorSDK.getInstance().FetchSingleNFTDetails("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx", new MirrorListener.FetchSingleNFT() {
                    @Override
                    public void onNFTFetched(NFTObject nftObject) {
                        Status = "success";
                        synchronized (lock) {
                            lock.notify();
                        }
                    }

                    @Override
                    public void onNFTFetchFailed(long code, String message) {
                        Status = "failed";
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
        MirrorSDK.getInstance().SetAppID(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().FetchNFTsByMintAddresses(mint_address, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Status = GetStatus( result);
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
         MirrorSDK.getInstance().SetAppID(appid);
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
         MirrorSDK.getInstance().SetAppID(appid);
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
        MirrorSDK.getInstance().SetAppID(appid);
        MirrorSDK.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDK.getInstance().GetTransactionBySignature("5pTshp58jboUBUqHEPSn6KZ6hCc6ZU7NL4BghjQU15J8vBitKSaHqc8ms5XCkbUByYnabEY8MS8H12RbzzAMUxBn", new GetWalletTransactionBySigListener() {
                    @Override
                    public void onSuccess(List<WalletTransaction> walletTransactions) {
                        Status = GetStatus( result);
                        synchronized (lock) {
                            lock.notify();
                        }
                    }

                    @Override
                    public void onFailed(long code, String message) {

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
    public void TransferSQL(){
        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.Staging);
        final Object lock = new Object();
        MirrorSDK.getInstance().SetAppID(appid);
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