package com.mirror.sdk.listener.auth;

import com.mirror.sdk.response.auth.UserResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:27
 * @projectName mirrorworld-sdk-android
 * @className QueryUserListener.java
 * @description TODO
 */
public interface QueryUserListener {
    void onQuerySuccess(UserResponse userResponse);
    void onQueryFailed(long code,String message);
}
