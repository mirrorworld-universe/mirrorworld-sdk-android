package com.mirror.mirrorworld_sdk_android;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

import com.mirror.sdk.MirrorCallback;
import com.mirror.sdk.MirrorSDKJava;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private String  Status;

    private String appid = "bU21hAbKFrKUBnCcSf9oZ84pq82aWhUfF6u";

    private String userEmail = "squall19871987@163.com";

    private String password = "yuebaobao";


    private String OtherAppId = "E6Drdi3qYsX1WkPoTFardZY7XF0AOXVrDq4";

    private String OtherUserEmail = "kukret@163.com";

    private String OtherPassword = "KYg56897";


    // get response status by response result
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

    // get token from response
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

    // get wallet address from response
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

        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail,password, new MirrorCallback() {
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


    // Checks whether is authenticated or not and returns the user object if true
    @Test
    public void FetchUser(){

        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDKJava.getInstance().FetchUser(new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Status = GetStatus( result);
                        MirrorSDKJava.getInstance().SetWalletAddress(GetWalletAddress(result));
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
    public void QueryUser(){

        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDKJava.getInstance().QueryUser(userEmail, new MirrorCallback() {
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


    // merge MintTopLevelCollectionCreate and LowLevelCollection Create Test
    //API is broken 22.8.30
//    @Test
//    public void CreateVerifiedCollection(){
//
//        final Object lock = new Object();
//        MirrorSDKJava.getInstance().SetAppID(appid);
//        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//
//                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
//                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));
//
//                MirrorSDKJava.getInstance().CreateVerifiedCollection("puyu1", "test1", "https://mirror-nft.s3.us-west-2.amazonaws.com/assets/111.json", new MirrorCallback() {
//                    @Override
//                    public void callback(String result) {
//                        MirrorSDKJava.getInstance().CreateVerifiedSubCollection("FbDkjpV1YSsmY3zrGGkK5H33ed33APfdRXToQPg52PMr", "py13", "symbol13", "https://mirror-nft.s3.us-west-2.amazonaws.com/assets/111.json", new MirrorCallback() {
//                            @Override
//                            public void callback(String result) {
//                                MirrorSDKJava.getInstance().MintNFT("9mkx2CDjRa64xEpUxyBJKbBC4NRAQhEJGDN8Ei8xHRWi", "name", "symbol", "https://market-assets.mirrorworld.fun/gen1/1.json", new MirrorCallback() {
//                                    @Override
//                                    public void callback(String result) {
//                                        Status = GetStatus( result);
//                                        synchronized (lock) {
//                                            lock.notify();
//                                        }
//
//                                    }
//                                });
//                            }
//                        });
//                    }
//                });
//            }
//        });
//
//
//        try {
//            synchronized (lock) {
//                lock.wait();
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals("success",Status);
//
//    }


    // market
    @Test
    public void TransferNFTToAnotherSolanaWallet(){
        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));
                MirrorSDKJava.getInstance().TransferNFTToAnotherSolanaWallet("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx", "B63XUAv3ureYH9iJnQFaHnz94FPPMEjoFK9Psvv4bMPs", new MirrorCallback() {
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

        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));
                MirrorSDKJava.getInstance().ListNFT( "DxL8GuDoqWLqMLkeLQmaDVh4jR25zbhZQeYh3nbaqw1D",1.2,new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        MirrorSDKJava.getInstance().UpdateNFTListing( "DxL8GuDoqWLqMLkeLQmaDVh4jR25zbhZQeYh3nbaqw1D",1.3,new MirrorCallback() {
                            @Override
                            public void callback(String result) {
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                MirrorSDKJava.getInstance().CancelNFTListing("DxL8GuDoqWLqMLkeLQmaDVh4jR25zbhZQeYh3nbaqw1D",1.3,new MirrorCallback() {
                                    @Override
                                    public void callback(String result) {
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
    public void CreateVerifiedSubCollection(){
        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);


        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MirrorSDKJava.getInstance().CreateVerifiedSubCollection("FbDkjpV1YSsmY3zrGGkK5H33ed33APfdRXToQPg52PMr", "py13", "symbol13", "https://mirror-nft.s3.us-west-2.amazonaws.com/assets/111.json", new MirrorCallback() {
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
    public void MintNFT(){


        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDKJava.getInstance().MintNFT("9mkx2CDjRa64xEpUxyBJKbBC4NRAQhEJGDN8Ei8xHRWi", "name", "symbol", "https://market-assets.mirrorworld.fun/gen1/1.json", new MirrorCallback() {
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
    public void FetchNFTsByOwnerAddresses(){
        List<String> owners = new ArrayList<>();
        owners.add("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx");

        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDKJava.getInstance().FetchNFTsByOwnerAddresses( owners,1,1,new MirrorCallback() {
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
        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDKJava.getInstance().FetchNFTMarketplaceActivity("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx",new MirrorCallback() {
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
        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));


                MirrorSDKJava.getInstance().FetchSingleNFTDetails("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx",new MirrorCallback() {
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


    // response info is "not implement this method";
//    @Test
//    public void FetchNFTsByCreators(){
//
//        List<String> creators = new ArrayList<>();
//        creators.add("GCeY1zY2QFz1iYekbsX1jQjtJnjyxWXtBhxAJPrvG3Bg");
//
//        final Object lock = new Object();
//        MirrorSDKJava.getInstance().SetAppID(appid);
//        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//
//                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
//                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));
//
//                MirrorSDKJava.getInstance().FetchNFTsByCreatorAddresses( creators,1.0,1.0,new MirrorCallback() {
//                    @Override
//                    public void callback(String result) {
//                        Status = GetStatus( result);
//                        synchronized (lock) {
//                            lock.notify();
//                        }
//
//                    }
//                });
//            }
//        });
//
//
//        try {
//            synchronized (lock) {
//                lock.wait();
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//       // assertEquals("success",Status);
//
//
//    }


    @Test
    public void FetchNFTsByMintAddress(){

        List<String> mint_address = new ArrayList<>();
        mint_address.add("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx");

        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDKJava.getInstance().FetchNFTsByMintAddresses(mint_address, new MirrorCallback() {
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

    // response info is "not implement this method"
    @Test
    public void FetchNFTsByUpdateAuthorities() {
        List<String> authorities = new ArrayList<>();
        authorities.add("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx");

        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDKJava.getInstance().FetchNFTsByUpdateAuthorities(authorities, 2.0, 2.0, new MirrorCallback(){
                    @Override
                    public void callback(String result) {
                        Status = GetStatus(result);
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

       // assertEquals("success", Status);


    }

     // wallet
     @Test
     public void GetWalletToken(){

         final Object lock = new Object();
         MirrorSDKJava.getInstance().SetAppID(appid);
         MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
             @Override
             public void callback(String result) {

                 MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                 MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                 MirrorSDKJava.getInstance().GetWalletToken(new MirrorCallback() {
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
     public void WalletTransactions(){
         final Object lock = new Object();
         MirrorSDKJava.getInstance().SetAppID(appid);
         MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
             @Override
             public void callback(String result) {

                 MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                 MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                 MirrorSDKJava.getInstance().Transactions("1", "5pTshp58jboUBUqHEPSn6KZ6hCc6ZU7NL4BghjQU15J8vBitKSaHqc8ms5XCkbUByYnabEY8MS8H12RbzzAMUxBn", new MirrorCallback()  {
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
    public void WalletTransactionsBySignature(){
        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(appid);
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDKJava.getInstance().GetTransactionBySignature( "5pTshp58jboUBUqHEPSn6KZ6hCc6ZU7NL4BghjQU15J8vBitKSaHqc8ms5XCkbUByYnabEY8MS8H12RbzzAMUxBn", new MirrorCallback()  {
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
    public void TransferSQL(){

        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID("WsPRi3GQz0FGfoSklYUYzDesdKjKvxdrmtQ");
        MirrorSDKJava.getInstance().LoginWithEmail(userEmail, password, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NTg2MCwiZXRoX2FkZHJlc3MiOiJCNjNYVUF2M3VyZVlIOWlKblFGYUhuejk0RlBQTUVqb0ZLOVBzdnY0Yk1QcyIsInNvbF9hZGRyZXNzIjoiQjYzWFVBdjN1cmVZSDlpSm5RRmFIbno5NEZQUE1Fam9GSzlQc3Z2NGJNUHMiLCJlbWFpbCI6InNxdWFsbDE5ODcxOTg3QDE2My5jb20iLCJ3YWxsZXQiOnsiZXRoX2FkZHJlc3MiOiIweDYyODRmNTk2MTNCN2MxMDliNWQ1NjA3NmMxRjcxMDY2OGExRkUyQWUiLCJzb2xfYWRkcmVzcyI6IkI2M1hVQXYzdXJlWUg5aUpuUUZhSG56OTRGUFBNRWpvRks5UHN2djRiTVBzIn0sImNsaWVudF9pZCI6bnVsbCwiaWF0IjoxNjYxMjUxNDQyLCJleHAiOjE2NjM4NDM0NDIsImp0aSI6ImF1dGg6NTg2MCJ9.efc0hlWvNRrV9XOQ309j-W95hT_deP8__M5pz8w380A");
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDKJava.getInstance().APIPostTransferSQL("HkGWQxFspfcaHQbbnnwwGrUDGyKFTYmFgSrB6p238Tqz", 10, new MirrorCallback()  {
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

    // not found block hash
    @Test
    public void BuyNFT(){
        final Object lock = new Object();
        MirrorSDKJava.getInstance().SetAppID(OtherAppId);
        MirrorSDKJava.getInstance().LoginWithEmail(OtherUserEmail,OtherPassword, new MirrorCallback() {
            @Override
            public void callback(String result) {

                MirrorSDKJava.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDKJava.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));

                MirrorSDKJava.getInstance().BuyNFT("FvD7WTyBMfGbxsyhidBrGUw8Y4ojpQNim8jNyE3NTKHx",9.8,new MirrorCallback() {
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

        // assertEquals("success",Status);

    }
}