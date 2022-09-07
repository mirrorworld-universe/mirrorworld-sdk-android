package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:25
 * @projectName mirrorworld-sdk-android
 * @className FetchSingleNFTActivityListener.java
 * @description TODO
 */
public interface FetchSingleNFTActivityListener {
    void onFetchSuccess(ActivityOfSingleNftResponse activityOfSingleNftResponse);
    void onFetchFailed(long code,String message);
}
