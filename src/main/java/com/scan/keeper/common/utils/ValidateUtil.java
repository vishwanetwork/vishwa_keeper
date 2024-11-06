package com.scan.keeper.common.utils;

import java.util.regex.Pattern;

public class ValidateUtil {

    /**
     * ：
     */
    // public static final String REGEX_MOBILE =
    // "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    public static final String REGEX_MOBILE = "^\\d{11}$";

    /**
     * 
     *
     * @param mobile
     * @return true，false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
}
