package com.mirror.mirrorworld_sdk_android.utils;

import android.util.Log;

import com.mirror.sdk.MirrorCallback;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.listener.market.ListNFTListener;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.response.market.MintResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BatchNFT {

    private List<Integer> mDelays;
    private List<Double> mPrices;
    private List<String> mGenerateJsons;
    List<String> mRares;
    List<String> mRoles;
    private int mJsonIdx = 0;

    private String mGenerateJsonUrl = "https://mirror-jump-json.mirrorworld.fun/api/v1/metadata/json";
    private String mAPIKey = "mw_testAmRKdRbBsBbIAw3CeMqS9GORmcG5BRUCU4D";
    String mCollection = "BXqCckKEidhJUpYrg4u2ocdiDKwJY3WujHvVDPTMf6nL";
    String mSolanaNamePre = "Mirror Jump #";
    String mMarket = "AiEqGqMDMa7G6sDF484pqfgsxRGuCcjgxv43MxoiVCsz";
    String mEmail = "squall19871987@163.com";
    String mPwd = "edu2act.org";

    public void init(){
        mDelays = new ArrayList<>();
        for(int i=0;i<25;i++){
            mDelays.add(i*2);
        }

        mPrices = new ArrayList<>();
        mPrices.add(0.5);
        mPrices.add(1.0);
        mPrices.add(2.0);
        mPrices.add(3.0);
        mPrices.add(4.0);

        mRares = new ArrayList<>();
        mRares.add("Common");
        mRares.add("Rare");
        mRares.add("Elite");
        mRares.add("Ledengary");
        mRares.add("Mythical");
        mRoles = new ArrayList<>();
        mRoles.add("Samuraimon");
        mRoles.add("Zombie");
        mRoles.add("Cat Maid");
        mRoles.add("Pirate Captain");
        mRoles.add("Astronautcal");

        mGenerateJsons = new ArrayList<>();
        for(int i=0;i<mRares.size();i++){
            for(int j=0;j<mRoles.size();j++){
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("rarity", mRares.get(i));
                    jsonObject.put("type_name", mRoles.get(j));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String data = jsonObject.toString();
                mGenerateJsons.add(data);
            }
        }
    }

    public void List5NFT(){
        init();

        MirrorSDK.getInstance().InitSDK(null, MirrorEnv.DevNet);
        MirrorSDK.getInstance().SetApiKey(mAPIKey);
        MirrorSDK.getInstance().LoginWithEmail(mEmail, mPwd, new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().SetAccessToken(GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(GetRefreshTokenFromResponse(result));


                String postData = mGenerateJsons.get(mJsonIdx);
                Double price = mPrices.get(mJsonIdx);
                mJsonIdx++;
                generateNFT(postData,price);
            }
        });
    }

    private int generateCount = 0;
    private int maxCount = 2;

    private void generateNFT(String data,Double price){
        Log.i("BatchNFT","start "+mJsonIdx);
        MirrorSDK.getInstance().doPostRequest(mGenerateJsonUrl, data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    JSONObject data = json.getJSONObject("data");
                    String urlPath = data.getString("json_path");
                    String solanaName = mSolanaNamePre + (id);
                    String solanaSymbol = "MRJ"+id;
                    id++;

                    MirrorSDK.getInstance().MintNFT(mCollection, solanaName, solanaSymbol, urlPath, MirrorConfirmation.Finalized ,new MintNFTListener() {
                        @Override
                        public void onMintSuccess(MintResponse userResponse) {
                            Log.i("MintSuccess:",userResponse.mint_address+"   delaySec:");
                            MirrorSDK.getInstance().ListNFT(userResponse.mint_address, price,MirrorConfirmation.Finalized,mMarket, new ListNFTListener() {
                                @Override
                                public void onListSuccess(ListingResponse listingResponse) {
                                    Log.i("onListSuccess:",listingResponse.mint_address);

                                    if(++generateCount != maxCount){
                                        String postData = mGenerateJsons.get(mJsonIdx);
                                        Double price = mPrices.get(mJsonIdx);
                                        mJsonIdx++;
                                        generateNFT(postData,price);
                                    }
                                }

                                @Override
                                public void onListFailed(long code, String message) {
                                    Log.e("onListSuccess:",code+" "+message);
                                }
                            });
                        }

                        @Override
                        public void onMintFailed(long code, String message) {
                            Log.e("onMintFailed:",code+" "+message);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    int id = 1;
    int delayId = 0;

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
}
