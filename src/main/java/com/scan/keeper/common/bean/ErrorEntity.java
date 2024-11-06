package com.scan.keeper.common.bean;

public class ErrorEntity {
    
    /**  */
    private int code;
    /**  */
    private String message;
    /** */
    private int errorLevel;
    
    private ErrorEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public static final ErrorEntity define(int code, String message) {
        return new ErrorEntity(code, message);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorEntity{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", errorLevel=").append(errorLevel);
        sb.append('}');
        return sb.toString();
    }
}
