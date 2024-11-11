package com.scan.keeper.utils;

import org.bitcoinj.core.*;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.*;
import org.bitcoinj.crypto.*;
import com.google.gson.*;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.bitcoinj.script.ScriptOpCodes.OP_ADD;
import static org.bitcoinj.script.ScriptOpCodes.OP_EQUAL;

public class BtcHTLCTransfer {
    private static final NetworkParameters params = TestNet3Params.get();
    private static final String BEARER_TOKEN = "cd14c0fb9ecb07ab42d73bcf4bf1eb7bd7eb2a4900969f8a679c7a98273279c9";

    // 修改 unlockFromP2WSH 方法
    public static void unlockFromP2WSH(String targetAddress, long amount) throws Exception {
        String p2wshAddress = "tb1qsaya3df5lw4pjja7h3al4pk5f2zg27hlz2j5n0enzmejxwf5ym6sly0v85";

        List<UTXO> utxos = getUTXOs(p2wshAddress);
        if (utxos.isEmpty()) {
            System.out.println("No UTXOs found for address " + p2wshAddress);
            return;
        }

        Transaction tx = new Transaction(params);
        long fee = 500;
        Coin inputAmount = Coin.ZERO;

        // 遍历 UTXOs 并添加输入
        for (UTXO utxo : utxos) {
            // 使用 txid 和 vout 来创建 TransactionOutPoint
            TransactionOutPoint outPoint = new TransactionOutPoint(params, utxo.getIndex(), utxo.getHash());

            // 使用出发点创建 TransactionInput
            TransactionInput input = new TransactionInput(params, tx, new byte[]{}, outPoint);

            // 将输入添加到交易
            tx.addInput(input);

            // 累加输入金额
            inputAmount = inputAmount.add(utxo.getValue());

            // 检查是否已达到交易金额+手续费
            if (inputAmount.isGreaterThan(Coin.valueOf(amount + fee))) break;
        }

        // 目标地址输出
        Address destinationAddress = Address.fromString(params, targetAddress);
        tx.addOutput(Coin.valueOf(amount), destinationAddress);

        // 计算找零金额
        Coin changeAmount = inputAmount.subtract(Coin.valueOf(amount + fee));
        if (changeAmount.isGreaterThan(Coin.ZERO)) {
            tx.addOutput(changeAmount, Address.fromString(params, p2wshAddress));
        }

        // 创建 witnessScript
        Script witnessScript = createWitnessScript();

        // 创建 TransactionWitness
        TransactionWitness witness = new TransactionWitness(2);
        witness.setPush(0, witnessScript.getProgram());  // 推入 witnessScript
        witness.setPush(1, Sha256Hash.hash(witnessScript.getProgram()));  // 推入 witnessScript 的哈希值

        // 为输入添加 witness
        for (TransactionInput input : tx.getInputs()) {
            input.setWitness(witness);
        }

        // 为每个输入调用 finalizeInput 来设置 redeem 脚本
        for (int i = 0; i < tx.getInputs().size(); i++) {
            finalizeInput(tx, i, tx.getInput(i));
        }

        // 打印交易的 Hex 编码
        String txHex = tx.toHexString();
        System.out.println("Transaction Hex: " + txHex);

        // 广播交易
        broadcastTransaction(tx);
    }

    // 创建 witnessScript
    private static Script createWitnessScript() {
        return new ScriptBuilder()
                .op(OP_ADD)  // 推入 3
                .number(11)  // 推入 8// 验证是否相等
                .op(OP_EQUAL)  // 验证是否相等
                .build();
    }

    // 在 Java 中实现 finalizeInput
    private static void finalizeInput(Transaction tx, int inputIndex, TransactionInput input) {
        // 创建 redeemScript，类似 JS 中的 redeem: { input: [3, 8] }
        Script redeemScript = new ScriptBuilder()
                .number(3)  // 推入 3
                .number(8)  // 推入 8
                .build();

        // 创建 redeemWitnessScript，这里包含了 redeemScript
        Script redeemWitnessScript = new ScriptBuilder()
                .data(redeemScript.getProgram())
                .build();

        // 创建 TransactionWitness，并逐个添加 redeemWitnessScript 和它的哈希值
        TransactionWitness witness = new TransactionWitness(2);
        witness.setPush(0, redeemWitnessScript.getProgram());  // 推入 redeemWitnessScript
        witness.setPush(1, Sha256Hash.hash(redeemWitnessScript.getProgram()));  // 推入它的哈希值

        // 为输入设置 witness
        input.setWitness(witness);
    }


    private static List<UTXO> getUTXOs(String address) throws Exception {
        String url = "https://open-api-testnet.unisat.io/v1/indexer/address/" + address + "/utxo-data";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + BEARER_TOKEN);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return parseUTXOs(response.toString());
        }
    }

    private static List<UTXO> parseUTXOs(String response) {
        List<UTXO> utxos = new ArrayList<>();
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();

        JsonObject dataObj = jsonResponse.getAsJsonObject("data");
        if (dataObj != null && dataObj.has("utxo")) {
            JsonArray utxoArray = dataObj.getAsJsonArray("utxo");
            for (JsonElement element : utxoArray) {
                JsonObject utxoObj = element.getAsJsonObject();
                String txid = utxoObj.get("txid").getAsString();
                int vout = utxoObj.get("vout").getAsInt();
                long satoshi = utxoObj.get("satoshi").getAsLong();

                UTXO utxo = new UTXO(Sha256Hash.wrap(txid), vout, Coin.valueOf(satoshi), 0, false, new Script(new byte[0]));
                utxos.add(utxo);
            }
        } else {
            System.out.println("Invalid data format or 'utxo' array missing.");
        }
        return utxos;
    }

    private static void broadcastTransaction(Transaction tx) throws Exception {
        URL url = new URL("https://go.getblock.io/4512aab0372b4250ad9a033701d687d4");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + BEARER_TOKEN);
        conn.setDoOutput(true);

        String txHex = tx.toHexString();
        String jsonPayload = String.format(
                "{\"jsonrpc\":\"2.0\",\"method\":\"sendrawtransaction\",\"params\":[\"%s\"],\"id\":1}",
                txHex
        );

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Transaction broadcast response: " + response.toString());
        } else {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            String errorLine;
            StringBuilder errorResponse = new StringBuilder();
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();
            System.err.println("Error response: " + errorResponse.toString());
            throw new IOException("Failed to broadcast transaction, server returned: " + responseCode);
        }
    }

    public static void main(String[] args) throws Exception {
        unlockFromP2WSH("tb1q5x0j6xuc0fkj2x6pjra8kydutj9h2p8lgyh9le", 1000);
    }
}
