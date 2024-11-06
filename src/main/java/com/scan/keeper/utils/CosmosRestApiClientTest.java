package com.scan.keeper.utils;

import com.jeongen.cosmos.CosmosRestApiClient;
import com.jeongen.cosmos.crypro.CosmosCredentials;
import com.jeongen.cosmos.vo.SendInfo;
import cosmos.base.abci.v1beta1.Abci;
import cosmos.tx.v1beta1.ServiceOuterClass;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CosmosRestApiClientTest {
    public static void testSendMultiTx() throws Exception {
        CosmosRestApiClient gaiaApiService = new CosmosRestApiClient(" https://rpc-euphrates.devnet.babylonlabs.io", "euphrates-0.5.0", "ubbn");

        byte[] privateKey = Hex.decode("c2ad7a31c06ea8bb560a0467898ef844523f2f804dec96fedf65906dbb951f24");
        CosmosCredentials credentials = CosmosCredentials.create(privateKey);
        // generate address
        System.out.println("address:" + credentials.getAddress());
        List<SendInfo> sendList = new ArrayList<>();
        // add a send message
        SendInfo sendMsg1 = SendInfo.builder()
                .credentials(credentials)
                .toAddress("cosmos12kd7gu4lamw29pv4u6ug8aryr0p7wm207uwt30")
                .amountInAtom(new BigDecimal("0.0001"))
                .build();
        sendList.add(sendMsg1);
        // add a send message
        SendInfo sendMsg2 = SendInfo.builder()
                .credentials(credentials)
                .toAddress("cosmos1u3zluamfx5pvgha0dn73ah4pyu9ckv6scvdw72")
                .amountInAtom(new BigDecimal("0.0001"))
                .build();
        sendList.add(sendMsg2);
        // build、sign、broadcast transactions
        Abci.TxResponse txResponse = gaiaApiService.sendMultiTx(credentials, sendList, new BigDecimal("0.000001"), 200000);
        System.out.println(txResponse);

        // query send tx by height
        ServiceOuterClass.GetTxsEventResponse txsEventByHeight = gaiaApiService.getTxsEventByHeight(6900000L, "");
    }

    public static void main(String[] args) throws Exception {
        testSendMultiTx();
    }
}
