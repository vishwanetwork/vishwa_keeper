package com.scan.keeper.utils;

import org.stellar.sdk.*;
import org.stellar.sdk.requests.sorobanrpc.SorobanRpcErrorResponse;
import org.stellar.sdk.responses.sorobanrpc.GetTransactionResponse;
import org.stellar.sdk.responses.sorobanrpc.SendTransactionResponse;
import org.stellar.sdk.scval.Scv;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class StellarUtils {
    private static String pk = "SBQ76UW5CXE2L27ZJAFLSMVUZCJPGEZEIYMK7CUKHDVUZD2RMKYTSCVK";

    public static void runCmd(boolean isMint, String amount) throws SorobanRpcErrorResponse, IOException {
        // The source account will be used to sign and send the transaction.
        KeyPair sourceKeypair = KeyPair.fromSecretSeed(pk);

        String cmd = isMint ? "mint" : "burn";

        // Configure SorobanClient to use the `soroban-rpc` instance of your choosing.
        SorobanServer sorobanServer = new SorobanServer("https://soroban-testnet.stellar.org:443");

        // Here we will use a deployed instance of the `increment` example contract.
        String contractAddress = "CAOKWZ3VMEYLS724NUDRWCM6PZMOYDPEEGYAT2QJUH7VVJLHEJ4Q2P3X";


        // Transactions require a valid sequence number (which varies from one account to
        // another). We fetch this sequence number from the RPC server.
        TransactionBuilderAccount sourceAccount = null;
        try {
            sourceAccount = sorobanServer.getAccount(sourceKeypair.getAccountId());
        } catch (AccountNotFoundException e) {
            throw new RuntimeException("Account not found, please activate it first");
        } catch (SorobanRpcErrorResponse sorobanRpcErrorResponse) {
            sorobanRpcErrorResponse.printStackTrace();
        }

        // The invocation of the `increment` function.
        InvokeHostFunctionOperation operation = InvokeHostFunctionOperation.invokeContractFunctionOperationBuilder(contractAddress, cmd,
                List.of(
                        Scv.toAddress(sourceAccount.getAccountId()),
                        Scv.toInt128(new BigInteger(amount))
                )
        ).build();

        // Create a transaction with the source account and the operation we want to invoke.
        Transaction transaction = new TransactionBuilder(sourceAccount, Network.TESTNET)
                .addOperation(operation) // The invocation of the `increment` function of our contract is added to the transaction.
                .setTimeout(30) // This transaction will be valid for the next 30 seconds
                .setBaseFee(100000)  // The base fee is 100 stroops (0.00001 XLM)
                .build();

        // We use the RPC server to "prepare" the transaction. This simulating the
        // transaction, discovering the soroban data, and updating the
        // transaction to include that soroban data. If you know the soroban data ahead of
        // time, you could manually use `setSorobanData` and skip this step.
        try {
            transaction = sorobanServer.prepareTransaction(transaction);
        } catch (PrepareTransactionException e) {
            // You should handle the error here
            System.out.println("Prepare Transaction Failed: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (SorobanRpcErrorResponse sorobanRpcErrorResponse) {
            sorobanRpcErrorResponse.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sign the transaction with the source account's keypair.
        transaction.sign(sourceKeypair);

        // Let's see the base64-encoded XDR of the transaction we just built.
        System.out.println("Signed prepared transaction XDR: " + transaction.toEnvelopeXdrBase64());

        // Submit the transaction to the Soroban-RPC server. The RPC server will then
        // submit the transaction into the network for us. Then we will have to wait,
        // polling `getTransaction` until the transaction completes.
        SendTransactionResponse response = sorobanServer.sendTransaction(transaction);
        if (!SendTransactionResponse.SendTransactionStatus.PENDING.equals(response.getStatus())) {
            throw new RuntimeException("Sending transaction failed");
        }

        // Poll `getTransaction` until the status is not "NOT_FOUND"
        GetTransactionResponse getTransactionResponse;
        while (true) {
            System.out.println("Waiting for transaction confirmation...");
            // See if the transaction is complete
            getTransactionResponse = sorobanServer.getTransaction(response.getHash());
            if (!GetTransactionResponse.GetTransactionStatus.NOT_FOUND.equals(getTransactionResponse.getStatus())) {
                break;
            }
            // Wait one second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Get transaction response: " + getTransactionResponse);

        if (GetTransactionResponse.GetTransactionStatus.SUCCESS.equals(getTransactionResponse.getStatus())) {
            System.out.println("Transaction result: ok ");
        } else {
            // The transaction failed, so we can extract the `resultXdr`
            System.out.println("Transaction failed: " + getTransactionResponse.getResultXdr());
        }
    }

    public static void main(String[] args) {
        try {
            runCmd(true, "1000");
            runCmd(false, "1000");
        } catch (SorobanRpcErrorResponse | IOException sorobanRpcErrorResponse) {
            sorobanRpcErrorResponse.printStackTrace();
        }
    }
}
