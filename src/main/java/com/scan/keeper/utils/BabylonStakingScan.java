package com.scan.keeper.utils;

import com.alibaba.fastjson.JSONObject;
import com.scan.keeper.common.bean.BabylonTx;
import com.scan.keeper.common.utils.HttpClientUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class BabylonStakingScan {

    // Base URL for querying Babylon blockchain RPC
    static String url = "https://rpc-euphrates.devnet.babylonlabs.io/";

    // URL to search for transactions
    static String urlSearch = url + "tx_search?";

    // Variable to keep track of the number of transactions already processed
    static Integer count = 0;

    /**
     * This method fetches and processes staking transactions from Babylon blockchain.
     * It checks if there are new staking transactions and prints the relevant details.
     */
    public static void getStaking() throws UnsupportedEncodingException {

        // Define the query parameters
        String paramName = "query";
        String paramValue5 = "\"message.action='/babylon.btcstaking.v1.MsgCreateBTCDelegation'\"";

        // URL-encode the query parameters
        String encodedParam = URLEncoder.encode(paramName, "UTF-8") + "=" + URLEncoder.encode(paramValue5, "UTF-8");

        // Define pagination parameters (fetch first 100 records)
        String page = "&page=1&per_page=100";

        // Construct the full URL with the query and pagination parameters
        String urlParam = urlSearch + encodedParam + page;

        // Send GET request to the Babylon RPC endpoint
        JSONObject jsonObject = HttpClientUtils.httpGet(urlParam);

        // Parse the JSON response into BabylonTx object
        BabylonTx babylonTx = JSONObject.parseObject(jsonObject.toJSONString(), BabylonTx.class);

        // Get the total number of transactions returned in the response
        int cnt = babylonTx.getResult().getTotalCount();

        // If there are new transactions, process them
        if (cnt > count) {

            // Loop through the transactions starting from the last processed one
            for (int i = count; i < cnt; i++) {
                BabylonTx.ResultDTO.TxsDTO tx = babylonTx.getResult().getTxs().get(i);

                String address = "";
                String amount = "";

                // Retrieve events from the transaction and process them
                List<BabylonTx.ResultDTO.TxsDTO.TxResultDTO.EventsDTO> events = tx.getTxResult().getEvents();

                // Loop through the events to extract relevant information
                for (BabylonTx.ResultDTO.TxsDTO.TxResultDTO.EventsDTO ev : events) {
                    // Check if the event type is 'coin_received' (which indicates staking-related action)
                    if ("coin_received".equals(ev.getType())) {
                        // Extract address and amount from the event attributes
                        address = ev.getAttributes().get(0).getValue();
                        amount = ev.getAttributes().get(1).getValue();
                    }
                }

                // Print the transaction details (address, amount)
                System.out.println(String.format("Received Babylon staking transaction: Address = %s, Amount = %s",
                        address, amount));
            }

            // Update the count to the latest number of transactions processed
            count = cnt;
        }
    }

    /**
     * Main method to initiate the staking transaction fetching process.
     * This is the entry point of the program.
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        // Call the method to fetch and process staking transactions
        getStaking();
    }
}
