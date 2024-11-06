package com.scan.keeper.common.constant;

/**
 * @author xsy
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: 
 * @date 2018/5/1710:41
 */
public enum ErrorConstant {

    BAD_REQUEST(400, "Bad Request!"),
    NOT_AUTHORIZATION(401, "NotAuthorization"),
    NOT_FOUND_REQUEST(404, "Not Found Request Path"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    LOGIN_FIRST(999, "[]"),

    RUNTIME_EXCEPTION(1000, "[]"),
    NULL_POINTER_EXCEPTION(1001, "[]"),
    CLASS_CAST_EXCEPTION(1002, "[]"),
    IO_EXCEPTION(1003, "[]IO"),
    NO_SUCH_METHOD_EXCEPTION(1004, "[]"),
    INDEX_OUT_OF_BOUNDS_EXCEPTION(1005, "[]"),
    CONNECT_EXCEPTION(1006, "[]"),
    ERROR_MEDIA_TYPE(1007, "[]Content-type，application/json"),
    EMPTY_REQUEST_BOYD(1008, "[]requestbody"),
    ERROR_REQUEST_BOYD(1009, "[]requestbodyjson"),
    ERROR_VERSION(2000, "[]"),
    ERROR_FORMAT_PARAMETER(2001, "[]");



    private final int code;
    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ErrorConstant(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getNameByValue(Integer val) {
        if (val != null) {
            int value = val;
            for (ErrorConstant constant : ErrorConstant.values()) {
                if (constant.code == value) {
                    return constant.msg;
                }
            }
        }
        return "";
    }

    public ErrorConstant getTypeByValue(int value) {
        for (ErrorConstant constant : ErrorConstant.values()) {
            if (constant.code == value) {
                return constant;
            }
        }
        return null;
    }
}
