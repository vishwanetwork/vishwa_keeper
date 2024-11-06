package com.scan.keeper.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scan.keeper.common.bean.BtcTx;
import com.scan.keeper.common.utils.HttpClientUtils;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BtcHTLCScan {

    // Base URL for querying Blockstream API
    static String baseUrl = "https://blockstream.info/testnet/";
    static String blockHash = baseUrl + "api/block-height/";
    static String blockTx = baseUrl + "api/block/%s/txids";
    static String txInfo = baseUrl + "api/tx/";

    // The address to check for matching outputs
    static String addressLock = "tb1qsaya3df5lw4pjja7h3al4pk5f2zg27hlz2j5n0enzmejxwf5ym6sly0v85";

    // OkHttpClient for making requests with connection pooling and timeouts
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();

    /**
     * Fetch the block hash for a given block height.
     * @param height The block height.
     * @return The block hash as a String.
     */
    private static String getBlockHashForHeight(String height) {
        return HttpClientUtils.httpGetStr(blockHash + height);
    }

    /**
     * Fetch the transaction IDs for a given block hash.
     * @param blockHash The block hash.
     * @return An array of transaction IDs.
     */
    private static String[] getTXidsForBlockHash(String blockHash) {
        JSONArray jsonArray = HttpClientUtils.httpGetArray(String.format(blockTx, blockHash));
        return jsonArray.toArray(new String[jsonArray.size()]);
    }

    /**
     * Fetch detailed information about a transaction.
     * @param tx The transaction ID.
     * @return A JSONObject containing transaction information.
     */
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

    /**
     * Process all transactions for a given block height.
     * @param height The block height.
     * @return A list of matching transactions.
     * @throws InterruptedException If the process is interrupted.
     */
    public static List<BtcTx> processTransactionsForBlockHeight(String height) throws InterruptedException {
        List<BtcTx> matchingTransactions = new ArrayList<>();
        String blockhash = getBlockHashForHeight(height);
        String[] txids = getTXidsForBlockHash(blockhash);
        System.out.println("Total transactions: " + txids.length);

        // Create an ExecutorService for handling multiple transactions concurrently
        ExecutorService executorService = new ThreadPoolExecutor(
                20, // core threads
                50, // max threads
                1, TimeUnit.MINUTES, // idle timeout
                new LinkedBlockingQueue<>()); // queue for task submission

        AtomicInteger processedCount = new AtomicInteger(0); // Counter for processed transactions
        AtomicInteger matchedCount = new AtomicInteger(0); // Counter for matched transactions
        AtomicInteger errorCount = new AtomicInteger(0); // Counter for errors

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (String tx : txids) {
            // Asynchronously process each transaction using CompletableFuture
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    JSONObject txInfo = getTxInfo(tx);
                    if (txInfo != null) {
                        BtcTx btcTx = JSONObject.parseObject(txInfo.toString(), BtcTx.class);
                        List<BtcTx.VoutDTO> vouts = btcTx.getVout();

                        // Check if any output has the specified lock address
                        for (BtcTx.VoutDTO v : vouts) {
                            if (v.getScriptpubkeyAddress() != null &&
                                    v.getScriptpubkeyAddress().equals(addressLock)) {
                                synchronized (matchingTransactions) {
                                    matchingTransactions.add(btcTx);
                                }
                                matchedCount.incrementAndGet(); // Increment matched count
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error processing transaction " + tx + ": " + e.getMessage());
                    errorCount.incrementAndGet(); // Increment error count
                } finally {
                    processedCount.incrementAndGet(); // Increment processed count
                }
            }, executorService);
            futures.add(future);
        }

        // Wait for all CompletableFutures to complete
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();

        // Shutdown the executor and wait for all tasks to finish
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // Print the results of the processing
        System.out.println("Total processed transactions: " + processedCount.get());
        System.out.println("Total matching transactions: " + matchedCount.get());
        System.out.println("Total errors: " + errorCount.get());

        return matchingTransactions;
    }

    /**
     * Main method to test the transaction processing.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Fetch and process transactions for a specific block height
            List<BtcTx> result = processTransactionsForBlockHeight("3196558");
            System.out.println("Matching transactions count: " + result.size());
        } catch (InterruptedException e) {
            System.err.println("Process interrupted: " + e.getMessage());
        }
    }
}
