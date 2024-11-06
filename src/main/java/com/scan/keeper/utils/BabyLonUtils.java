package com.scan.keeper.utils;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class BabyLonUtils {
    private static final String WEBSOCKET_URI = "https://rpc.devnet.babylonlabs.io:443";

    public static void main(String[] args) {
        try {
            WebSocketClient client = new WebSocketClient(new URI(WEBSOCKET_URI)) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected to Cosmos WebSocket");

                    // ， `NewBlock` 
                    String subscribeMessage = "{ \"jsonrpc\": \"2.0\", \"method\": \"subscribe\", \"id\": \"1\", \"params\": { \"query\": \"tm.event = 'NewBlock'\" } }";
                    send(subscribeMessage);
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received event: " + message);

                    // ，
                    if (message.contains("\"NewBlock\"")) {
                        // 
                        System.out.println("New Block Event: " + message);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Disconnected from Cosmos WebSocket: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };

            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
