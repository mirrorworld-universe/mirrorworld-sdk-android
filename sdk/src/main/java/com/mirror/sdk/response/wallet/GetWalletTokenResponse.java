package com.mirror.sdk.response.wallet;

import java.util.List;

public class GetWalletTokenResponse {
    public List<Token> tokens;
    public long sol;
}

class Token {
    public String ata;
    public String mint;
    public long amount;
}
