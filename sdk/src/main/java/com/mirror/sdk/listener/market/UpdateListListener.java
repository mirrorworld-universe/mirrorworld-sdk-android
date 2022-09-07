package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ListingResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:23
 * @projectName mirrorworld-sdk-android
 * @className UpdateListListener.java
 * @description TODO
 */
public interface UpdateListListener {
    void onUpdateSuccess(ListingResponse listingResponse);
    void onUpdateFailed(long code,String message);
}
