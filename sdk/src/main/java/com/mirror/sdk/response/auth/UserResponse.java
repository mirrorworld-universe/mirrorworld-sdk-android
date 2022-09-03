package com.mirror.sdk.response.auth;

import java.util.Date;

public class UserResponse {
    public long id;
    public String eth_address;
    public String sol_address;
    public String email;
    public boolean email_verified;
    public String username;
    public long main_user_id;
    public boolean allow_spend;
    public Date createdAt;
    public Date updatedAt;
    public boolean is_subaccount;
    public WalletResponse wallet;
}
