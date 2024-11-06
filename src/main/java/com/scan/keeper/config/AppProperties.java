package com.scan.keeper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class AppProperties {

    //key
    public static String BLOCK_KEY;
    //web3j
    public static String WEB3J_ADDRESS;
    
    //
    public static String SYSTEM_ENVIRONMENT;
    
    public static int CHAIN_ID;

    public static String COVID_ADDRESS;

    public static String MonitorAddress;

    public static String KeyStorePath;

    public static int Dividend;

    public static int Cashout;

    public static int CashoutEnd;

    public static int Transaction;

    public static int Price;

    public static int Config;

    public static String IconPath;

    @PostConstruct
    public void init() {
        WEB3J_ADDRESS = web3jAddress;
        SYSTEM_ENVIRONMENT = environment;
        CHAIN_ID = chainId;
        COVID_ADDRESS = tdfAddress;
        BLOCK_KEY = blockKey;
        MonitorAddress = monitorAddress;
        KeyStorePath = keyStorePath;
        Dividend = dividend;
        Cashout = cashout;
        CashoutEnd = cashoutEnd;
        Transaction = transaction;
        Price = price;
        Config = config;
        IconPath = iconPath;
    }


    @Value("${app.web3j.address}")
    private String web3jAddress;
    
    @Value("${spring.profiles.active}")
    private String environment;

    @Value("${app.web3j.chainId}")
    private int chainId;

    @Value("${app.web3j.tdfAddress}")
    private String tdfAddress;

    @Value("${app.web3j.tdfBlock}")
    private String blockKey;

    @Value("${app.web3j.monitorAddress}")
    private String monitorAddress;

    @Value("${app.web3j.keyStorePath}")
    private String keyStorePath;

    @Value("${app.taskConfig.dividend}")
    private int dividend;

    @Value("${app.taskConfig.cashout}")
    private int cashout;

    @Value("${app.taskConfig.cashoutEnd}")
    private int cashoutEnd;

    @Value("${app.taskConfig.transaction}")
    private int transaction;

    @Value("${app.taskConfig.price}")
    private int price;

    @Value("${app.taskConfig.config}")
    private int config;

    @Value("${app.icon.path}")
    private String iconPath;
}
