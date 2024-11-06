package com.scan.keeper.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BtcTx implements Serializable {
    @JsonProperty("txid")
    private String txid;
    @JsonProperty("version")
    private Integer version;
    @JsonProperty("locktime")
    private Integer locktime;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("weight")
    private Integer weight;
    @JsonProperty("fee")
    private Integer fee;
    @JsonProperty("status")
    private StatusDTO status;
    @JsonProperty("vin")
    private List<VinDTO> vin;
    @JsonProperty("vout")
    private List<VoutDTO> vout;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class StatusDTO implements Serializable {
        @JsonProperty("confirmed")
        private Boolean confirmed;
        @JsonProperty("block_height")
        private Integer blockHeight;
        @JsonProperty("block_hash")
        private String blockHash;
        @JsonProperty("block_time")
        private Integer blockTime;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class VinDTO implements Serializable {
        @JsonProperty("txid")
        private String txid;
        @JsonProperty("vout")
        private BigInteger vout;
        @JsonProperty("prevout")
        private PrevoutDTO prevout;
        @JsonProperty("scriptsig")
        private String scriptsig;
        @JsonProperty("scriptsig_asm")
        private String scriptsigAsm;
        @JsonProperty("is_coinbase")
        private Boolean isCoinbase;
        @JsonProperty("sequence")
        private Long sequence;
        @JsonProperty("witness")
        private List<String> witness;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public static class PrevoutDTO implements Serializable {
            @JsonProperty("scriptpubkey")
            private String scriptpubkey;
            @JsonProperty("scriptpubkey_asm")
            private String scriptpubkeyAsm;
            @JsonProperty("scriptpubkey_type")
            private String scriptpubkeyType;
            @JsonProperty("scriptpubkey_address")
            private String scriptpubkeyAddress;
            @JsonProperty("value")
            private BigInteger value;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class VoutDTO implements Serializable {
        @JsonProperty("scriptpubkey")
        private String scriptpubkey;
        @JsonProperty("scriptpubkey_asm")
        private String scriptpubkeyAsm;
        @JsonProperty("scriptpubkey_type")
        private String scriptpubkeyType;
        @JsonProperty("scriptpubkey_address")
        private String scriptpubkeyAddress;
        @JsonProperty("value")
        private BigInteger value;
    }
}
