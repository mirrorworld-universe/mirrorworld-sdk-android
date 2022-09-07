package com.mirror.sdk.listener.wallet;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class TransactionsDTO {
    @SerializedName("blockTime")
    public int blockTime;
    @SerializedName("meta")
    public MetaDTO meta;
    @SerializedName("slot")
    public int slot;
    @SerializedName("transaction")
    public TransactionDTO transaction;

    public class MetaDTO {
        @SerializedName("err")
        public Object err;
        @SerializedName("fee")
        public int fee;
        @SerializedName("innerInstructions")
        public List<InnerInstructionsDTO> innerInstructions;
        @SerializedName("loadedAddresses")
        public LoadedAddressesDTO loadedAddresses;
        @SerializedName("logMessages")
        public List<String> logMessages;
        @SerializedName("postBalances")
        public List<Long> postBalances;
        @SerializedName("postTokenBalances")
        public List<PostTokenBalancesDTO> postTokenBalances;
        @SerializedName("preBalances")
        public List<Long> preBalances;
        @SerializedName("preTokenBalances")
        public List<?> preTokenBalances;
        @SerializedName("rewards")
        public List<?> rewards;
        @SerializedName("status")
        public StatusDTO status;

        public class LoadedAddressesDTO {
            @SerializedName("readonly")
            public List<?> readonly;
            @SerializedName("writable")
            public List<?> writable;
        }

        public class StatusDTO {
            @SerializedName("Ok")
            public Object ok;
        }

        public class InnerInstructionsDTO {
            @SerializedName("index")
            public int index;
            @SerializedName("instructions")
            public List<InstructionsDTO> instructions;

            public class InstructionsDTO {
                @SerializedName("parsed")
                public ParsedDTO parsed;
                @SerializedName("program")
                public String program;
                @SerializedName("programId")
                public String programId;

                public class ParsedDTO {
                    @SerializedName("info")
                    public InfoDTO info;
                    @SerializedName("type")
                    public String type;

                    public class InfoDTO {
                        @SerializedName("lamports")
                        public int lamports;
                        @SerializedName("newAccount")
                        public String newAccount;
                        @SerializedName("owner")
                        public String owner;
                        @SerializedName("source")
                        public String source;
                        @SerializedName("space")
                        public int space;
                    }
                }
            }
        }

        public class PostTokenBalancesDTO {
            @SerializedName("accountIndex")
            public int accountIndex;
            @SerializedName("mint")
            public String mint;
            @SerializedName("owner")
            public String owner;
            @SerializedName("programId")
            public String programId;
            @SerializedName("uiTokenAmount")
            public UiTokenAmountDTO uiTokenAmount;

            public class UiTokenAmountDTO {
                @SerializedName("amount")
                public String amount;
                @SerializedName("decimals")
                public int decimals;
                @SerializedName("uiAmount")
                public int uiAmount;
                @SerializedName("uiAmountString")
                public String uiAmountString;
            }
        }
    }

    public class TransactionDTO {
        @SerializedName("message")
        public MessageDTO message;
        @SerializedName("signatures")
        public List<String> signatures;

        public class MessageDTO {
            @SerializedName("accountKeys")
            public List<AccountKeysDTO> accountKeys;
            @SerializedName("addressTableLookups")
            public Object addressTableLookups;
            @SerializedName("instructions")
            public List<InstructionsDTO> instructions;
            @SerializedName("recentBlockhash")
            public String recentBlockhash;

            public class AccountKeysDTO {
                @SerializedName("pubkey")
                public String pubkey;
                @SerializedName("signer")
                public boolean signer;
                @SerializedName("writable")
                public boolean writable;
            }

            public class InstructionsDTO {
                @SerializedName("accounts")
                public List<String> accounts;
                @SerializedName("data")
                public String data;
                @SerializedName("programId")
                public String programId;
            }
        }
    }
}
