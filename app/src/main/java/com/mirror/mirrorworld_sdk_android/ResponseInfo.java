package com.mirror.mirrorworld_sdk_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mirror.sdk.MirrorCallback;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.MirrorSDK;

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
        MirrorSDK.getInstance().InitSDK(this, MirrorEnv.Staging);
        MirrorSDK.getInstance().SetAppID("TWikEmOHJxb4xbSLEkFqsi9ddJ9u6RNdbe5");
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

        MirrorSDK.getInstance().LoginWithEmail("suqiang@rct.studio", "yuebaobao", new MirrorCallback() {
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

    public void GetWalletToken(){
        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().GetWalletToken(new MirrorCallback() {
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

        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().TransferNFTToAnotherSolanaWallet("mint_address", "to_wallet_address", new MirrorCallback() {
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
        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().ListNFT("mint_address", 22.0, new MirrorCallback() {
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
        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().UpdateNFTListing("mint_address", 22.0, new MirrorCallback() {
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
        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().BuyNFT("mint_address", 22.0, new MirrorCallback() {
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
        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {


                MirrorSDK.getInstance().CancelNFTListing("mint_address", 22.5, new MirrorCallback() {

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

        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().FetchNFTsByOwnerAddresses(owners, 2, 2, new MirrorCallback() {
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

        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().FetchNFTsByCreatorAddresses(creators, 1.0, 1.0, new MirrorCallback() {
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

        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().FetchNFTsByMintAddresses(mint_address, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }


    public void MintNewNFTOnCollection(){



        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().MintNFT("collection_mint", "name", "symbol", "https://mirrormetaplextest.s3.amazonaws.com/assets/15976.json", new MirrorCallback() {
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

        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().CreateVerifiedCollection("jhglfjkdkng", "jasdoif", "https://mirrormetaplextest.s3.amazonaws.com/assets/15976.json", new MirrorCallback() {
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
        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().FetchNFTsByOwnerAddresses(owners, 1, 1, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    public void FetchActivitiesOfSingleNFT(){
        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().FetchNFTMarketplaceActivity("mint_address", new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



//    public void FetchSingleNFTDetails(){
//        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                MirrorSDK.getInstance().FetchSingleNFTDetails("mint_address", new MirrorCallback() {
//                    @Override
//                    public void callback(String result) {
//                        Log.d("Test", "callback: "+result);
//                    }
//                });
//            }
//        });
//    }



    // about wallet


    public void TransferSQL(){
        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().APIPostTransferSQL("to_public_key", 1, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    public void WalletTransactions(){
        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().Transactions("limit", "before", new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }



    public void WalletToken(){
        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().GetWalletToken(new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }


    public void WalletAddress(){
        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().GetWallet(new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        Log.d("Test", "callback: "+result);
                    }
                });
            }
        });
    }


    public void TransferToken(){
        MirrorSDK.getInstance().LoginWithEmail("jonas@rct.ai", "jonas123", new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().PostTransferToken("TO_PULICKEY", 1, "token_mint", 2, new MirrorCallback() {
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