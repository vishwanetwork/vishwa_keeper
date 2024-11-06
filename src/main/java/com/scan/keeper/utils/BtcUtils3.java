package com.scan.keeper.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scan.keeper.common.bean.BtcTx;
import com.scan.keeper.common.utils.HttpClientUtils;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BtcUtils3 {

    static String baseUrl = "https://blockstream.info/testnet/";
    static String blockHash = baseUrl + "api/block-height/";
    static String blockTx = baseUrl + "api/block/%s/txids";
    static String txInfo = baseUrl + "api/tx/";

    static String scriptpubkey = "0020b6912a2725451cd5d28f7447cf76cdc818ff517063736f58c7419c413f1979ce";
    static String addressLock = "tb1qsaya3df5lw4pjja7h3al4pk5f2zg27hlz2j5n0enzmejxwf5ym6sly0v85";

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();

    private static String getBlockHashForHeight(String height) {
        return HttpClientUtils.httpGetStr(blockHash + height);
    }

    private static String[] getTXidsForBlockHash(String blockHash){
        JSONArray jsonArray =  HttpClientUtils.httpGetArray(String.format(blockTx,blockHash));
        return jsonArray.toArray(new String[jsonArray.size()]);
    }


    private static JSONObject getTxInfo(String tx) {
        Request request = new Request.Builder().url(txInfo + tx).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return JSONObject.parseObject(response.body().string());
            }
        } catch (Exception e) {
            System.err.println("Error fetching transaction: " + e.getMessage());
        }
        return null;
    }

    public static List<BtcTx> processTransactionsForBlockHeight(String height) throws InterruptedException {
        List<BtcTx> matchingTransactions = new ArrayList<>();
        String blockhash = getBlockHashForHeight(height);
        String[] txids = getTXidsForBlockHash(blockhash);
        System.out.println("Total transactions: " + txids.length);

        // ，
        ExecutorService executorService = new ThreadPoolExecutor(
                20,
                50,
                1,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>()); // ，

        AtomicInteger processedCount = new AtomicInteger(0);
        AtomicInteger matchedCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (String tx : txids) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    JSONObject txInfo = getTxInfo(tx);
                    if (txInfo != null) {
                        BtcTx btcTx = JSONObject.parseObject(txInfo.toString(), BtcTx.class);
                        List<BtcTx.VoutDTO> vouts = btcTx.getVout();

                        for (BtcTx.VoutDTO v : vouts) {
                            System.out.println(v.toString());
                            if (v.getScriptpubkeyAddress().equals(addressLock)) {
                                synchronized (matchingTransactions) {
                                    matchingTransactions.add(btcTx);
                                }
                                matchedCount.incrementAndGet(); // 
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error processing transaction " + tx + ": " + e.getMessage());
                    errorCount.incrementAndGet(); // 
                } finally {
                    processedCount.incrementAndGet(); // 
                }
            }, executorService);
            futures.add(future);
        }

        // 
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();

        // 
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // 
        System.out.println("Total processed transactions: " + processedCount.get());
        System.out.println("Total matching transactions: " + matchedCount.get());
        System.out.println("Total errors: " + errorCount.get());

        return matchingTransactions;
    }

    public static void main(String[] args) {
        try {
            List<BtcTx> result = processTransactionsForBlockHeight("3196184");
            System.out.println("Matching transactions count: " + result.size());
        } catch (InterruptedException e) {
            System.err.println("Process interrupted: " + e.getMessage());
        }

//        String tx = "affb59dacf4b11f960dd0e5197a6dce5267d4bbed96760910af3ed83a46f5aa8";
//        JSONObject txInfo = getTxInfo(tx);
//        if (txInfo != null) {
//            BtcTx btcTx = JSONObject.parseObject(txInfo.toString(), BtcTx.class);
//            System.out.println(btcTx.toString());
//        }
    }
}
