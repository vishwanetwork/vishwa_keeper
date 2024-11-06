package com.scan.keeper.utils;

import cosmos.auth.v1beta1.QueryGrpc;
import cosmos.bank.v1beta1.QueryOuterClass;
import cosmos.tx.v1beta1.ServiceGrpc;
import cosmos.tx.v1beta1.ServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CosmosUtils {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("https://rpc.cosmos.directory/")
                .usePlaintext()
                .build();

        // new query service
        QueryGrpc.QueryBlockingStub queryBlockingStub = QueryGrpc.newBlockingStub(channel);

//        // query the balance
//        QueryOuterClass.QueryAllBalancesResponse balance = queryBlockingStub.allBalances(QueryOuterClass.QueryAllBalancesRequest.newBuilder().setAddress("cosmos1wjuh6ee7gzkr489pmfc3qcw6qvjensquzv3s0x").build());
//        System.out.println(balance.getBalances(0).getAmount());

        // new transactions service
        ServiceGrpc.ServiceBlockingStub txServiceBlockingStub = ServiceGrpc.newBlockingStub(channel);
        // query tx detail
        ServiceOuterClass.GetTxResponse txResponse = txServiceBlockingStub.getTx(ServiceOuterClass.GetTxRequest.newBuilder().setHash("9A500E826296559DEF05E9D0C7D354F05A37FCF69DDD79B78EF0FCA438E8660B").build());
        System.out.println(txResponse.getTxResponse().getTxhash());

        // broadcast transactions
        ServiceOuterClass.BroadcastTxResponse broadcastTxResponse = txServiceBlockingStub.broadcastTx(ServiceOuterClass.BroadcastTxRequest.newBuilder().build());
        System.out.println(broadcastTxResponse.getTxResponse().getTxhash());
    }
}
