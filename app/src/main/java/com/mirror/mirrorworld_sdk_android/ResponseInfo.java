package com.mirror.mirrorworld_sdk_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mirror.sdk.MirrorCallback;
import com.mirror.sdk.MirrorSDKJava;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResponseInfo extends AppCompatActivity {


    private TextView response;
    private TextView sendRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_info);
        InitView();
        MirrorSDKJava.getInstance().InitSDK(this);
        MirrorSDKJava.getInstance().SetAppID("TWikEmOHJxb4xbSLEkFqsi9ddJ9u6RNdbe5");



    }





    private void InitView(){
        response = findViewById(R.id.response);
        sendRequest = findViewById(R.id.click);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginWithEmail();
            }
        });
    }


    private void SetResponseContent(String res){
        this.response.setText(res);
    }









    public void LoginWithEmail(){

        MirrorSDKJava.getInstance().LoginWithEmail("suqiang@rct.studio", "yuebaobao", new MirrorCallback() {
            @Override
            public void callback(String result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SetResponseContent(result);
                    }
                });

                Log.d("Test", "callback: "+result);



            }
        });
    }




    public void QueryUser(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().QueryUser("2573040560@qq.com", new MirrorCallback() {
                    @Override
                    public void callback(String result) {

                        try {
                            JudgeState(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("Test", "callback: "+result);

                    }
                });
            }
        });
    }




    public void GetWalletToken(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().GetWalletToken(new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        try {
                            JudgeState(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    public void TransferNFTToAnotherSolanaWallet(){

        // todo

        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().TransferNFTToAnotherSolanaWallet("mint_address", "to_wallet_address", new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        try {
                            JudgeState(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    public void ListNFTOnTheMarketplace(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().ListNFT("mint_address", 22.0, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        try {
                            JudgeState(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }


    public void UpdateListingOfNFT(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().UpdateNFTListing("mint_address", 22.0, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        try {
                            JudgeState(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }


    public void BuyNFT(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().BuyNFT("mint_address", 22.0, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        try {
                            JudgeState(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }


    public void CancelListingOfNFT(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {


                MirrorSDKJava.getInstance().CancelNFTListing("mint_address", 22.5, new MirrorCallback() {

                    @Override
                    public void callback(String result) {

                        try {
                            JudgeState(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    public void FetchMultipleByOwners(){

        List<String> owners = new ArrayList<>();

        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().FetchNFTsByOwnerAddresses(owners, 2, 2, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }


    public void FetchNFTsByCreators(){

        List<String> creators = new ArrayList<>();

        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().FetchNFTsByCreatorAddresses(creators, 1.0, 1.0, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    public void FetchNFTsByMintAddress(){

        List<String> mint_address = new ArrayList<>();

        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().FetchNFTsByMintAddresses(mint_address, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }


    public void MintNewNFTOnCollection(){



        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().MintNFT("collection_mint", "name", "symbol", "https://mirrormetaplextest.s3.amazonaws.com/assets/15976.json", new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }


//    public void MintNewLowerLevelCollection(){
//
//
//        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                MirrorSDKJava.getInstance().APIMintNewLowerLevelCollection("collection_mint", "name", "sybol", "https://mirrormetaplextest.s3.amazonaws.com/assets/15976.json", new MirrorCallback() {
//                    @Override
//                    public void callback(String result) {
//                        Log.d("Test", "callback: "+result);
//                    }
//                });
//            }
//        });
//    }



    public void MintNewTopLevelCollection(){

        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().CreateVerifiedCollection("jhglfjkdkng", "jasdoif", "https://mirrormetaplextest.s3.amazonaws.com/assets/15976.json", new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    public void FetchMultipleByMintAddresses(){
        List<String> owners = new ArrayList<>();
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().FetchNFTsByOwnerAddresses(owners, 1, 1, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    public void FetchActivitiesOfSingleNFT(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().FetchNFTMarketplaceActivity("mint_address", new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    public void FetchSingleNFTDetails(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().FetchSingleNFTDetails("mint_address", new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    // about wallet


    public void TransferSQL(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().APIPostTransferSQL("to_public_key", 1, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    public void WalletTransactions(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().Transactions("limit", "before", new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    public void WalletToken(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().GetWalletToken(new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }


    public void WalletAddress(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().GetWallet(new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }


    public void TransferToken(){
        MirrorSDKJava.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDKJava.getInstance().PostTransferToken("TO_PULICKEY", 1, "token_mint", 2, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }


    public void JudgeState(String res) throws Exception{
        try {
            JSONObject json = new JSONObject(res);
            String status = String.valueOf(json.getJSONObject("status"));

            if(status != "success"){
                throw new Exception();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}