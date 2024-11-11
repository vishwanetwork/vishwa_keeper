package com.scan.keeper.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scan.keeper.common.bean.BtcTx;
import com.scan.keeper.common.utils.HttpClientUtils;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BtcHTLCScan2 {

    // Base URL for querying Blockstream API
    static String baseUrl = "https://blockstream.info/testnet/api/address/tb1qsaya3df5lw4pjja7h3al4pk5f2zg27hlz2j5n0enzmejxwf5ym6sly0v85/txs";
    static String addressLock = "tb1qsaya3df5lw4pjja7h3al4pk5f2zg27hlz2j5n0enzmejxwf5ym6sly0v85";
    static Integer height = 3432310;

    private static JSONArray getTxInfo() {
        return HttpClientUtils.httpGetArray(baseUrl);
    }


    public static void doScan()   {
        List<BtcTx> result = getTxInfo().toJavaList(BtcTx.class);
        boolean isOut = true;
        BigInteger amount = BigInteger.ZERO;
        for (BtcTx tx : result){

            if (tx.getStatus().getBlockHeight() == null){
                continue;
            }

            if (tx.getStatus().getBlockHeight() <= height){
                continue;
            }

            height = tx.getStatus().getBlockHeight();

            if (tx.getVin().get(0).getPrevout().getScriptpubkeyAddress().equals(addressLock)){
                isOut = false;
                amount = tx.getVin().get(0).getPrevout().getValue();
            }

            if (isOut){
                for(BtcTx.VoutDTO v : tx.getVout()){
                    if (v.getScriptpubkeyAddress().equals(addressLock)){
                        amount = v.getValue();
                    }
                }
            }

            try {
//                BabylonCommandExecutor.executeBabylondTransaction(amount.toString(), isOut);
                StellarUtils.runCmd(true, amount.toString());
                Thread.sleep(10000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * Main method to test the transaction processing.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) throws InterruptedException {
        doScan();
    }
}
