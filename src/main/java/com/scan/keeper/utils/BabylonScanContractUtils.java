package com.scan.keeper.utils;

import com.alibaba.fastjson.JSONObject;
import com.scan.keeper.common.bean.BabylonTx;
import com.scan.keeper.common.utils.HttpClientUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class BabylonScanContractUtils {

    // URL endpoint to query the Babylon contract transactions
    static String url = "https://rpc-euphrates.devnet.babylonlabs.io/tx_search?query=%22wasm._contract_address=%27bbn1q0n8p69qjj32turuwhr97yc087ygevtglcmf7pfre38q5js0mxrq27rjtg%27%22";

    // Variable to track the number of transactions already processed
    static Integer count = 0;

    /**
     * This method fetches and processes transactions from the Babylon contract.
     * It checks if there are new transactions and prints details about the actions.
     */
    public static void getMint() throws UnsupportedEncodingException {
        // URL parameter to request the contract transaction data
        String urlParam = url;

        // Send GET request to the Babylon contract API
        JSONObject jsonObject = HttpClientUtils.httpGet(urlParam);

        // Parse the JSON response into BabylonTx object
        BabylonTx babylonTx = JSONObject.parseObject(jsonObject.toJSONString(), BabylonTx.class);

        // Get the total number of transactions returned in the response
        int cnt = babylonTx.getResult().getTotalCount();

        // If there are more transactions than previously processed, process them
        if (cnt > count) {

            // Loop through the transactions starting from the last processed one
            for (int i = count; i < cnt; i++) {
                BabylonTx.ResultDTO.TxsDTO tx = babylonTx.getResult().getTxs().get(i);
                String action = "";
                String address = "";
                String amount = "";

                // Retrieve events from the transaction and process them
                List<BabylonTx.ResultDTO.TxsDTO.TxResultDTO.EventsDTO> events = tx.getTxResult().getEvents();

                // Loop through the events to extract relevant information
                for (BabylonTx.ResultDTO.TxsDTO.TxResultDTO.EventsDTO ev : events) {
                    // Check if the event type is 'wasm' (which indicates a contract interaction)
                    if ("wasm".equals(ev.getType())) {

                        // Extract action, address, and amount from the event attributes
                        action = ev.getAttributes().get(1).getValue();
                        address = ev.getAttributes().get(2).getValue();
                        amount = ev.getAttributes().get(3).getValue();
                    }
                }

                // Print the transaction details (address, action, amount)
                System.out.println(String.format("Received Babylon contract transaction: Address = %s, Action = %s, Amount = %s",
                        address, action, amount));
            }

            // Update the count to the latest number of transactions processed
            count = cnt;
        }
    }

    /**
     * Main method to initiate the transaction fetching process.
     * This is the entry point of the program.
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        // Call the method to fetch and process mint transactions
        getMint();
    }
}
