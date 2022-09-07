package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.MintResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:22
 * @projectName mirrorworld-sdk-android
 * @className MintNFTListener.java
 * @description TODO
 */
public interface MintNFTListener {
    void onMintSuccess(MintResponse userResponse);
    void onMintFailed(long code,String message);
}
