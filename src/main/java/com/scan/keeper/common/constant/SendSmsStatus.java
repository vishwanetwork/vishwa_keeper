package com.scan.keeper.common.constant;

/*
 * 
 * @author Administrator
 */
public class SendSmsStatus {

    /**  */
    public static int SUCCESS = 2;
    /**  */
    public final static String SUCCESS_MESSAGE = "send message success";

    /**  */
    public final static int FAIL = -1;
    /**  */
    public final static String FAIL_MESSAGE = "send message fail";

    /** */
    public final static int WAIT = 0;
    /** */
    public final static String WAIT_MESSAGE = "sms is waiting to send";

    /** */
    public final static int ERROR = 4;
    /** */
    public final static String ERROR_MESSAGE = "server inner error";

   /*** */
    public final static int INVAIDARGU = 5;
    /** */
    public final static String INVAIDARGU_MESSAGE = "parameter error,please check it";

    /*** */
    public final static int REPEATINSHORT = 6;
    /** */
    public final static String REPEATINSHORT_MESSAGE = "cancel send sms message,please send it after 30 seconds";

    /*** */
    public final static int MAXSMSNUM = 7;
    /** */
    public final static String MAXSMSNUM_MESSAGE = "cancel send sms message,exceed the maximum number";

    /** */
    public final static int CANCEL_SEND = 3;
    /** */
    public final static String CANCEL_SEND_MESSAGE = "cancel send sms message";

    /** */
    public final static   int CANCEL_SEND_FAIL = 7;
    /** */
    public final static   String CANCEL_SEND_FAIL_MESSAGE = "cancel send sms message fail,message has already been sended";

    /** */
    public final static int TOO_MUCH_SMS = 6;
    /** 100*/
    public final static String TOO_MUCH_SMS_MESSAGE = "no more than 100 sms messages can be send";

    /***/
    public static final String GDPP_SUCCESS_CODE = "DELIVRD";

    public final static String UPDATE_DEFAULT_CODE_SUCCESS_MESSAGE = "update new channel code success";

    /** */
    public final static   int BLANKLIST_FAIL = 8;
    /** */
    public final static   String BLANKLIST_MESSAGE = "Blacklist";

}
