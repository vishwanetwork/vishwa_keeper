package com.scan.keeper.common.constant;

import java.math.BigInteger;

public class ChainConstant {

    //
    public static final int TransactionSTATUS_SUCCESS = 1;
    //
    public static final int TransactionSTATUS_FAIL = 2;

    //
    public static final String RECEIPT_STATUS_FAIL = "0x0";
    //
    public static final String RECEIPT_STATUS_SUCCESS = "0x1";

    //gas
    public static final BigInteger TRUE_GAS_PRICE = new BigInteger("1000000000");

    //gaseLimit
    public static final BigInteger TRUE_GAS_LIMIT = new BigInteger("21000") ;
}
