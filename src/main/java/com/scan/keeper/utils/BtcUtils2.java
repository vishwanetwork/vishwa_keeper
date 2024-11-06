package com.scan.keeper.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scan.keeper.common.bean.BtcTx;
import com.scan.keeper.common.utils.HttpClientUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BtcUtils2 {
//    https://blockstream.info/testnet/api/block-height/3196299
//    https://blockstream.info/testnet/api/block/0000000000c5b3a452d684feeaf312296e1d7fd6ca8e3b8817eeb40c824a3aa2/txids
//    https://blockstream.info/testnet/api/tx/108d16c63afb63ca7b2b9b9a3bf20b0c3dc1e62eb41ce8c06236a83b24261b62


    static String baseUrl = "https://blockstream.info/testnet/";
    static String blockHash = baseUrl + "api/block-height/";
    static String blockTx = baseUrl + "api/block/%s/txids";
    static String txInfo = baseUrl + "api/tx/";

    static String scriptpubkey = "0020b6912a2725451cd5d28f7447cf76cdc818ff517063736f58c7419c413f1979ce";
    private static String getBlockHashForHeight(String height) {
        return HttpClientUtils.httpGetStr(blockHash + height);
    }

    private static String[] getTXidsForBlockHash(String blockHash){
         JSONArray jsonArray =  HttpClientUtils.httpGetArray(String.format(blockTx,blockHash));
         return jsonArray.toArray(new String[jsonArray.size()]);
    }

    private static JSONObject getTxInfo(String tx) {
        return HttpClientUtils.httpGet(txInfo + tx);
    }

    public static List<BtcTx> processTransactionsForBlockHeight(String height) throws InterruptedException, ExecutionException {
        List<BtcTx> matchingTransactions = new ArrayList<>();
        String blockhash = getBlockHashForHeight(height);
        String[] txids = getTXidsForBlockHash(blockhash);
        System.out.println("Total transactions: " + txids.length);

        AtomicInteger totalTasks = new AtomicInteger(txids.length);
        AtomicInteger completedTasks = new AtomicInteger(0);

        //  CompletableFuture 
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (String tx : txids) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    JSONObject txInfo = getTxInfo(tx);
                    BtcTx btcTx = JSONObject.parseObject(txInfo.toJSONString(), BtcTx.class);
                    List<BtcTx.VoutDTO> vo = btcTx.getVout();
                    for (BtcTx.VoutDTO v : vo) {
                        System.out.println(v.getScriptpubkey());
                        if (v.getScriptpubkey().equals(scriptpubkey)) {
                            synchronized (matchingTransactions) {
                                matchingTransactions.add(btcTx); // 
                            }
                            String out = "lockAddress:%s lockAmount:%s";
                            System.out.println(String.format(out, btcTx.getVin().get(0).getPrevout().getScriptpubkeyAddress(), v.getValue()));
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error processing transaction " + tx + ": " + e.getMessage());
                } finally {
                    int completed = completedTasks.incrementAndGet();
                    System.out.printf("Progress: %d/%d completed\n", completed, totalTasks.get());
                }
            });
            futures.add(future);
        }

        // 
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return matchingTransactions; // 
    }

    public static void main(String[] args) {
        try {
            List<BtcTx> result = processTransactionsForBlockHeight("3196184");
            System.out.println("Matching transactions count: " + result.size());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Process interrupted: " + e.getMessage());
        }
    }
}
