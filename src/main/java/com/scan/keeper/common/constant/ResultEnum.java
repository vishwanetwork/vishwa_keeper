package com.scan.keeper.common.constant;

public enum ResultEnum {
    /*
     * 
     */
    SUCCESS(200,"success"),
    /*
     * 
     */
    ERROR_PARAMS(402,"params error"),
    /*
     * 
     */
	ERROR_MSG(502,"system error"),

    USERNAME_EXIST(601, "USERNAME_EXIST"),

    USER_NOT_EXIST(602, "USER_NOT_EXIST"),

    MailFail(603, "Mail_Fail"),

    MailCodeFail(604, "Mail_Code_Fail"),

    USER_PASSWORD_ERROR(605, "USER_PASSWORD_ERROR"),

    USER_PASSWORD_NOT_NULL(606, "USER_PASSWORD_NOT_NULL"),

    USER_PAYPASSWORD_NOT_NULL(607, "USER_PAYPASSWORD_NOT_NULL"),

    USERNAME_NOT_NULL(608, "USERNAME_NOT_NULL"),

    NICKNAME_NOT_NULL(609, "NICKNAME_NOT_NULL"),

    CODE_ERROR(610, "CODE_ERROR"),

    MOBILE_NOT_NULL(611, "MOBILE_NOT_NULL"),

    PWD_TYPE_NOT_NULL(612, "PWD_TYPE_NOT_NULL"),

    OLD_PASSWORD_ERROR(613, "OLD_PASSWORD_ERROR"),

    VerifyTimeOut(614, "Verify Time Out"),

    ADDRESS_NOT_NULL(615, "ADDRESS_NOT_NULL"),

    COIN_NOT_NULL(616, "COIN_NOT_NULL"),

    MOBILE_SENDCODE_TIMESEXEEDED(617, "MOBILE_SENDCODE_TIMESEXEEDED 3"),

    REDEMPTION_FAILED(618, "REDEMPTION_FAILED"),

    NOT_SUPPORT_COIN(619, "This currency is not currently supported"),

    REFERRERID_NOT_EXIST(620, "REFERRERID_NOT_EXIST"),

    MOBILE_EXIST(621, "MOBILE_EXIST"),

    EMAIL_ERROR(622, "EMAIL_ERROR"),

    CAN_NOT_TRANSFER_TO_MYSELF(623, "Can not transfer to myself");

    public int code;

    public String message;

    private ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
