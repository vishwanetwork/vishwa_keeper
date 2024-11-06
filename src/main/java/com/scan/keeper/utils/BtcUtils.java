package com.scan.keeper.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scan.keeper.common.utils.HttpClientUtils;

public class BtcUtils {
    //block https://api.blockcypher.com/v1/btc/test3/blocks/
    //tx https://api.blockcypher.com/v1/btc/test3/txs/
    static String baseUrl = "https://api.blockcypher.com/v1/btc/test3/";
    static String txUrl = baseUrl + "txs/";
    static String blockUrl = baseUrl + "blocks/";

    private static String getBlockInfo() {
        return HttpClientUtils.httpGet(blockUrl + "3196184").toString();
    }

    private static String[] getBlocktxids(){
        JSONObject jsonObject = JSONObject.parseObject(getBlockInfo());
        JSONArray jsonArray = jsonObject.getJSONArray("txids");
        return jsonArray.toArray(new String[jsonArray.size()]);
    }

    private static String getTxInfo(String txHash) {
        return HttpClientUtils.httpGet(txUrl + txHash).toString();
    }
    public static void main(String[] args) {
        String[] arr = getBlocktxids();
        for (String txHash : arr) {
            System.out.println(txHash);
            String txInfo = getTxInfo(txHash);
            System.out.println(txInfo);
        }
//
//        getTxInfo("c3b6bdda8f69257013d1094fd4c160dcb94f83aab71e5b7b25c78c1c5d42b9fe");
    }
}
