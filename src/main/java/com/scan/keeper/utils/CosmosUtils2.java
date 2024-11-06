package com.scan.keeper.utils;


import com.scan.keeper.common.utils.HttpClientUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CosmosUtils2 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "https://rpc-euphrates.devnet.babylonlabs.io/tx_search?";

        String paramName = "query";
        String paramValue = "\"tx.height=41976\"";
        String paramValue2 = "\"event.type='transfer'\"";

        String paramValue3 = "\"event.type='/babylon.btcstaking.v1.EventBTCDelegationStateUpdate'\"";

        String paramValue4 = "\"tx.height=41976\"";
        String paramValue5 = "\"message.action='/babylon.btcstaking.v1.MsgCreateBTCDelegation'\"";

        String encodedParam = URLEncoder.encode(paramName, "UTF-8") + "=" + URLEncoder.encode( paramValue5, "UTF-8");
        String page = "&page=1&per_page=2";
        url = url + encodedParam ;//+ page;
        System.out.println(url);
        System.out.println(HttpClientUtils.httpGet(url));
    }
}
