package com.mirror.sdk.listener.auth;

import com.mirror.sdk.response.auth.UserResponse;

/*** @author Pu
 * @createTime 2022/9/7 15:27
 * @projectName mirrorworld-sdk-android
 * @className FetchUserListener.java
 * @description TODO
 */
public interface FetchUserListener {
    void onUserFetched(UserResponse userResponse);
    void onFetchFailed(long code,String message);
}
