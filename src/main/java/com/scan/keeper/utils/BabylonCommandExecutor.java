package com.scan.keeper.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BabylonCommandExecutor {

    /**
     * Executes the babylond transaction command, choosing between mint or burn actions.
     *
     * @param amount The amount to mint or burn.
     * @param isMint If true, executes a mint transaction; if false, executes a burn transaction.
     * @throws IOException          If an I/O error occurs.
     * @throws InterruptedException If the process is interrupted.
     */
    public static void executeBabylondTransaction(String amount, boolean isMint) throws IOException, InterruptedException {
        // Fixed parameter values for the command execution
        String recipient = "bbn1789kuzxqn6qpaydpmcnlacl2j33vngn2mv3fym";
        String from = "babylonpk";
        String chainId = "euphrates-0.5.0";
        String homeDir = "/Users/wubaoqiang/.babylond";
        String nodeUrl = "https://rpc-euphrates.devnet.babylonlabs.io";
        String babylondPath = "/Users/wubaoqiang/develop/java/babylon/babylon/build/babylond";

        // Construct the JSON argument for the transaction based on mint or burn
        String jsonArg;
        if (isMint) {
            // Format JSON for minting the specified amount
            jsonArg = String.format(
                    "{ \"mint\": { \"recipient\": \"%s\", \"amount\": \"%s\" } }",
                    recipient,
                    amount
            );
        } else {
            // Format JSON for burning the specified amount
            jsonArg = String.format(
                    "{ \"burn\": { \"amount\": \"%s\" } }",
                    amount
            );
        }

        // Build the command to execute the Babylon transaction
        String[] command = {
                babylondPath,                  // Path to the babylond executable
                "tx", "wasm", "execute",       // Command for transaction execution
                "bbn1q0n8p69qjj32turuwhr97yc087ygevtglcmf7pfre38q5js0mxrq27rjtg", // Contract address
                jsonArg,                       // JSON argument (mint or burn)
                "--from=" + from,              // Sender's address
                "--gas=auto",                  // Automatically determine gas
                "--gas-prices=1ubbn",          // Gas price
                "--gas-adjustment=1.3",        // Gas adjustment
                "--chain-id=" + chainId,      // Chain ID for the network
                "-b=sync",                     // Block broadcast mode (synchronous)
                "--yes",                       // Automatically confirm transactions
                "--keyring-backend=test",      // Keyring backend for signing
                "--log_format=json",           // Log format in JSON
                "--home=" + homeDir,           // Home directory for configuration
                "--node=" + nodeUrl           // Node URL for the RPC connection
        };

        // Initialize ProcessBuilder to execute the command
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        // Merge standard error and standard output
        processBuilder.redirectErrorStream(true);

        // Start the process
        Process process = processBuilder.start();

        // Read and print the output from the command
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        // Wait for the process to complete and check the exit code
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Transaction executed successfully, exit code: " + exitCode);
        } else {
            System.err.println("Transaction failed, exit code: " + exitCode);
        }
    }

    /**
     * Main method for testing the Babylon transaction execution with mint and burn actions.
     *
     * @param args Command-line arguments (not used in this case).
     * @throws IOException          If an I/O error occurs.
     * @throws InterruptedException If the process is interrupted.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // Execute mint transaction for 100 units
        executeBabylondTransaction("100", true);

        // Execute burn transaction for 100 units
        executeBabylondTransaction("100", false);
    }
}
