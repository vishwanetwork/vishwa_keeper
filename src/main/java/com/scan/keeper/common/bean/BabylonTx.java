package com.scan.keeper.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jnr.ffi.annotations.In;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BabylonTx implements Serializable {
    @JsonProperty("jsonrpc")
    private String jsonrpc;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("result")
    private ResultDTO result;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ResultDTO implements Serializable {
        @JsonProperty("total_count")
        private Integer totalCount;
        @JsonProperty("txs")
        private List<TxsDTO> txs;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public static class TxsDTO implements Serializable {
            @JsonProperty("hash")
            private String hash;
            @JsonProperty("height")
            private String height;
            @JsonProperty("index")
            private Integer index;
            @JsonProperty("tx_result")
            private TxResultDTO txResult;
            @JsonProperty("tx")
            private String tx;

            @JsonIgnoreProperties(ignoreUnknown = true)
            @Data
            public static class TxResultDTO implements Serializable {
                @JsonProperty("code")
                private Integer code;
                @JsonProperty("data")
                private String data;
                @JsonProperty("log")
                private String log;
                @JsonProperty("info")
                private String info;
                @JsonProperty("gas_wanted")
                private String gasWanted;
                @JsonProperty("gas_used")
                private String gasUsed;
                @JsonProperty("codespace")
                private String codespace;
                @JsonProperty("events")
                private List<EventsDTO> events;

                @JsonIgnoreProperties(ignoreUnknown = true)
                @Data
                public static class EventsDTO implements Serializable {
                    @JsonProperty("type")
                    private String type;
                    @JsonProperty("attributes")
                    private List<AttributesDTO> attributes;

                    @JsonIgnoreProperties(ignoreUnknown = true)
                    @Data
                    public static class AttributesDTO implements Serializable {
                        @JsonProperty("key")
                        private String key;
                        @JsonProperty("value")
                        private String value;
                        @JsonProperty("index")
                        private Boolean index;
                    }
                }
            }
        }
    }
}
