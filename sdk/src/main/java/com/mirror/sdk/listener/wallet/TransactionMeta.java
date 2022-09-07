package com.mirror.sdk.listener.wallet;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TransactionMeta {
    @SerializedName("err")
    public Object err;
    @SerializedName("fee")
    public int fee;
    @SerializedName("innerInstructions")
    public List<TransactionsDTO.MetaDTO.InnerInstructionsDTO> innerInstructions;
    @SerializedName("loadedAddresses")
    public TransactionsDTO.MetaDTO.LoadedAddressesDTO loadedAddresses;
    @SerializedName("logMessages")
    public List<String> logMessages;
    @SerializedName("postBalances")
    public List<Integer> postBalances;
    @SerializedName("postTokenBalances")
    public List<TransactionsDTO.MetaDTO.PostTokenBalancesDTO> postTokenBalances;
    @SerializedName("preBalances")
    public List<Integer> preBalances;
    @SerializedName("preTokenBalances")
    public List<?> preTokenBalances;
    @SerializedName("rewards")
    public List<?> rewards;
    @SerializedName("status")
    public TransactionsDTO.MetaDTO.StatusDTO status;

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
        public List<TransactionsDTO.MetaDTO.InnerInstructionsDTO.InstructionsDTO> instructions;

    }

    public class InstructionsDTO {
        @SerializedName("parsed")
        public TransactionsDTO.MetaDTO.InnerInstructionsDTO.InstructionsDTO.ParsedDTO parsed;
        @SerializedName("program")
        public String program;
        @SerializedName("programId")
        public String programId;


        public class ParsedDTO {
            @SerializedName("info")
            public TransactionsDTO.MetaDTO.InnerInstructionsDTO.InstructionsDTO.ParsedDTO.InfoDTO info;
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
        public TransactionsDTO.MetaDTO.PostTokenBalancesDTO.UiTokenAmountDTO uiTokenAmount;
    }

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
