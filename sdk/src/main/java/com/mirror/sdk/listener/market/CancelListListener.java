package com.mirror.sdk.listener.market;

import com.mirror.sdk.response.auth.UserResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:23
 * @projectName mirrorworld-sdk-android
 * @className CancelListListener.java
 * @description TODO
 */
public interface CancelListListener {
    void onCancelSuccess(UserResponse userResponse);
    void onCancelFailed(long code,String message);
}
