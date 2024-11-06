
package com.scan.keeper.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Administrator
 *
 */
public class StringTools {
    public static final String DEFAULTCHARSET = System.getProperty("file.encoding", "UTF-8");

    private static final char CHAR_ZERROR = '0';

    private static Pattern NUMBER_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * byte[],:01(30 31 D6 D0):3031D6D0
     *
     * @param data
     *            
     * @return 
     */
    public static String byte2HexString(byte[] data) {
        return byte2HexString(data, Boolean.FALSE);
    }

    /**
     * byte[]:
     *
     * @param data
     *            
     * @param lowercase
     *            
     * @return 
     */
    public static String byte2HexString(byte[] data, boolean lowercase) {
        if (data == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String tmpStr = Integer.toHexString(data[i] & 0xff);
            tmpStr = StringUtils.leftPad(tmpStr, 2, CHAR_ZERROR);
            builder.append(tmpStr);
        }
        if (!lowercase) {
            return StringUtils.upperCase(builder.toString());
        }
        return builder.toString();
    }

    /**
     * 16(0x)byte[] byte2String
     *
     * @param srcData
     *            
     * @return byte[]
     * @throws Exception
     */
    public static byte[] hexString2Byte(String srcData) {
        if (srcData == null) {
            return null;
        } else if (srcData.length() % 2 != 0) {
            throw new RuntimeException(":" + srcData.length());
        }
        int len = srcData.length() / 2;
        byte[] newData = new byte[len];
        for (int i = 0; i < len; i++) {
            newData[i] = (byte)((Character.digit(srcData.charAt(i * 2), 16) << 4)
                ^ Character.digit(srcData.charAt(i * 2 + 1), 16));
        }
        return newData;
    }

    /**
     * 
     *
     * @param e
     * @return
     * @throws IOException
     */
    public static String exception2String(Exception e) {
        if (e == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(os));
        byte[] msg = os.toByteArray();
        String str = new String(msg);
        try {
            os.close();
        } catch (IOException e1) {
        }
        return str;
    }

    /**
     * 16(0x):byte<br>
     * <p>
     * 0x :0x610x620x63\\r\\naa,:97 98 99 13 10 97 97
     *
     * @param str
     *            
     * @return 
     * @throws NumberFormatException
     *             16
     */
    public static byte[] escString2Byte(String str) throws NumberFormatException {
        byte[] srcData = str.getBytes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int dataLength = srcData.length;
        for (int i = 0; i < dataLength; i++) {
            if (srcData[i] == CHAR_ZERROR && dataLength >= i + 4 && (srcData[i + 1] == 'x' || srcData[i + 1] == 'X')) {
                baos.write((byte)Integer.parseInt(new String(srcData, i + 2, 2), 16));
                i += 3;
            } else if (srcData[i] == '\\') {
                if ((i + 1) < dataLength) {
                    if (srcData[i + 1] == 'r') {
                        baos.write('\r');
                    } else if (srcData[i + 1] == 't') {
                        baos.write('\t');
                    } else if (srcData[i + 1] == 'n') {
                        baos.write('\n');
                    } else if (srcData[i + 1] == 'b') {
                        baos.write('\b');
                    } else if (srcData[i + 1] == 'f') {
                        baos.write('\f');
                    } else if (srcData[i + 1] == '\'') {
                        baos.write('\'');
                    } else if (srcData[i + 1] == '\"') {
                        baos.write('\"');
                    } else if (srcData[i + 1] == '\\') {
                        baos.write('\\');
                    } else if (srcData[i + 1] == '0') {
                        baos.write('\0');
                    } else {
                        throw new IllegalArgumentException("[" + str + "]!");
                    }
                    i++;
                } else {
                    // baos.write('\\');
                    throw new IllegalArgumentException("[" + str + "]!");
                }
            } else {
                baos.write(srcData[i]);
            }
        }
        return baos.toByteArray();
    }

    /**
     * 
     *
     * @param t
     * @return
     */
    public static String printExceptionStackTrace(Throwable t) {
        PrintWriter pw = null;
        StringWriter sw = new StringWriter();
        try {
            pw = new PrintWriter(sw);
            t.printStackTrace(pw);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }

    public static String getMsgIdFromBytes(byte[] buff, int pos) {
        byte[] buffer = new byte[2];
        System.arraycopy(buff, pos, buffer, 0, 2);
        return StringTools.byte2HexString(buffer);
    }

    public static String hexString2String(String src) {
        String temp = "";
        for (int i = 0; i < src.length() / 2; i++) {
            temp = temp + (char)Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return temp;
    }

    public static void main(String[] args) throws Exception {
        /*byte[] bs = {0x15, 0x11};
        String s = byte2HexString(bs);
        byte[] bytes = hexString2Byte("1511");
        System.out.println(s);
        System.out.println(bytes);
        
        byte[] bas = hexString2Byte(s);
        System.out.println(ArrayUtils.toString(bas));*/

        System.out.println(filterChinese("ces--.jpg"));

    }

    public static byte[] string2Byte(String srcData) {
        if (srcData == null) {
            return null;
        } else if (srcData.length() % 2 != 0) {
            throw new RuntimeException(":" + srcData.length());
        }
        int len = srcData.length() / 2;
        byte[] newData = new byte[len];
        for (int i = 0; i < len; i++) {
            newData[i] = (byte)((Character.digit(srcData.charAt(i * 2), 16) << 4)
                ^ Character.digit(srcData.charAt(i * 2 + 1), 16));
        }
        return newData;
    }

    /**
     * <DL>
     * <DT><B>  </B></DT>
     * <p>
     * <DD>(),16,,,16</DD>
     * </DL>
     * <p>
     * <p>
     * <DL>
     * <DT><B></B></DT>
     * <p>
     * <DD></DD>
     * </DL>
     * <p>
     *
     * @author dcz $Author: wangmingsheng $
     * @author 
     * @version $Id: StringToolsTest.java 64 2011-12-05 02:30:04Z wangmingsheng $
     */

    /**
     * 
     */
    public final static int MAX_OUTPUT_LENGTH_SINGLE_VAR = 10240;
    public final static int MAX_OUTPUT_LENGTH = 38912;
    /**
     * 
     */
    public static final String CHARSET_GBK = "GBK";

    // public static void main(String[] args) throws Exception {
    // byte[] bs = StringTools.string2Byte("8100");
    // System.out.println(StringTools.toHexTable(bs));
    // }
    public static final String CHARSET_GB2312 = "GB2312";
    public static final String CHARSET_GB18030 = "GB18030";
    public static final String CHARSET_UTF_8 = "UTF-8";
    public static final String CHARSET_UTF8 = "UTF8";
    /**
     * :
     */
    public final static int LEFT = 0;
    /**
     * :
     */
    public final static int CENTER = 1;
    /**
     * :
     */
    public final static int RIGHT = 2;
    /**
     * :
     */
    public final static int ALL = 3;
    /**
     * 
     */
    public final static String BLANK = "";
    private static final char CHAR_BLANK = ' ';

    private static final String STR_COLON = ": ";

    private static final char CHAR_SEMICOLON = ';';

    private static final char CHAR_DOT = '.';

    private static final String STR_EMPTY = "";

    private static final char CHAR_NEWLINE = '\n';

    /**
     * byte[],:01(30 31 D6 D0):3031D6D0
     *
     * @param data
     *            
     * @return 
     */
    public static String byte2String(byte[] data) {
        return byte2String(data, Boolean.FALSE);
    }

    /**
     * byte[]:
     *
     * @param data
     *            
     * @param lowercase
     *            
     * @return 
     */
    public static String byte2String(byte[] data, boolean lowercase) {
        if (data == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String tmpStr = Integer.toHexString(data[i] & 0xff);
            tmpStr = StringUtils.leftPad(tmpStr, 2, CHAR_ZERROR);
            builder.append(tmpStr);
        }
        if (!lowercase) {
            return StringUtils.upperCase(builder.toString());
        }
        return builder.toString();
    }

    /**
     * 38K,16（,）<br>
     * 38K,38*2
     *
     * @param src
     *            
     * @return ,38K,,48K
     */
    public static String toString(String src) {
        int max = MAX_OUTPUT_LENGTH;
        return toString(src, max);
    }

    /**
     * srcmax,srcmax,
     *
     * @param src
     *            
     * @param max
     *            ,-1,
     * @return
     */
    protected static String toString(String src, int max) {
        if (max == -1) {
            return src;
        }
        if (src.length() <= max) { // Stringbytes,
            return src;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(src.substring(0, max));
        sb.append("................");
        return sb.toString();
    }

    /**
     * Map
     *
     * @param str
     *            ,eg. aaa=111,bbb=222
     * @return Map
     */
    public static Map<String, String> string2Map(String mapStr) {
        Map<String, String> map = new HashMap<String, String>();
        String split = ",";
        String[] entrys = mapStr.split(split);
        for (String entry : entrys) {
            String[] strs = entry.split("=");
            if (strs.length > 1) {
                map.put(strs[0].trim(), strs[1].trim());
            }
        }
        return map;
    }

    /**
     * byte[]
     *
     * @param srcData
     *            
     * @return byte[]
     * @throws Exception
     */
    public static byte[] hexTableString2Byte(String srcData) throws Exception {
        if (srcData == null) {
            return null;
        }
        int hexStartIndex = 0;
        if (srcData.indexOf(CHAR_BLANK) == 4) {
            hexStartIndex = 6;
        }
        String[] lineStrArray = StringUtils.split(srcData, '\n');
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lineStrArray.length; i++) {
            int hexEndIndex = StringUtils.indexOf(lineStrArray[i], ';');
            String hexPart = StringUtils.substring(lineStrArray[i], hexStartIndex, hexEndIndex - 1);
            String[] byte2StringArray = StringUtils.split(hexPart, ' ');
            for (int j = 0; j < byte2StringArray.length; j++) {
                builder.append(byte2StringArray[j]);
            }
        }
        return string2Byte(builder.toString());
    }

    // 1 
    // 
    // 
    // 
    // GB2312
    // 0xB0-0xF7(176-247)
    // 0xA0-0xFE（160-254）
    // GBK
    // 0x81-0xFE（129-254）
    // 0x40-0xFE（64-254）
    // Big5
    // 0x81-0xFE（129-255）
    // 0x40-0x7E（64-126）
    // 0xA1－0xFE（161-254）
    // URL http://ir.hit.edu.cn/~taozi/bianma.htm
    // UTF8URL http://star.sgst.cn/questionDetail.do?id=45664

    /**
     * 16,4、4(‘0’-‘9’,’A’-‘F’),32。<br>
     * eg.<br>
     * :0x3c 5b 34 5f 6c 2b 9a 0d 6f 7c 9a 7d 2b 5e 1c 0f<br>
     * '3C5B345F6C2B9A0D6F7C9A7D2B5E1C0F'
     *
     * @param bt
     * @return String
     */
    public static String toHexString(byte[] bt) {
        // StringBuffer sb = new StringBuffer();
        // for (int i = 0; i < bt.length; i++)
        // {
        // String s = Integer.toHexString(bt[i] & 0xFF);
        // if (s.length() == 1)
        // {
        // sb.append("0");
        // }
        // sb.append(s);
        // }
        // String s = sb.toString().toUpperCase();
        return byte2String(bt);
    }

    public static String toHexTable(byte[] data) {
        return toHexTable(data, DEFAULTCHARSET);
    }

    /**
     * byte[]16
     *
     * @param data
     *            
     * @return 16
     */
    public static String toHexTable(byte[] data, String charset) {
        if (data.length <= MAX_OUTPUT_LENGTH) {
            return toHexTable(data, 16, 7, charset);
        }
        byte[] b = new byte[MAX_OUTPUT_LENGTH + 16];
        String suspension = "................";
        int srcLength = data.length;
        String length = String.valueOf(srcLength);
        suspension = length + suspension;
        String sus = suspension.substring(0, suspension.length() - length.length());
        System.arraycopy(data, 0, b, 0, MAX_OUTPUT_LENGTH);
        System.arraycopy(sus.getBytes(), 0, b, MAX_OUTPUT_LENGTH, 16);
        return toHexTable(b, 16, 7, charset);
    }

    /**
     * byte[]16,
     * <p>
     * <B>:</B>toHexTable(byte[] data, int numOfLine, int column,String charset),.
     *
     * @param data
     *            
     * @param column
     *            :7,4,2,1
     * @return 16
     */
    public static String toHexTable(byte[] data, int numOfLine, int column) {
        return toHexTable(data, numOfLine, column, DEFAULTCHARSET);
    }

    /**
     * byte[]16,
     *
     * @param data
     *            
     * @param column
     *            :7,4,2,1
     * @param charset
     *            
     * @return 16
     */
    public static String toHexTable(byte[] data, int numOfLine, int column, String charset) {
        if (data == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder(2048);
        int lines = data.length % numOfLine == 0 ? data.length / numOfLine : data.length / numOfLine + 1;

        int columnA = column & 4;
        int columnB = column & 2;
        int columnC = column & 1;

        for (int i = 0; i < lines; i++) {
            if (columnA == 4) {
                String lineCountPart = Integer.toString(i);
                lineCountPart = StringUtils.leftPad(lineCountPart, 4, CHAR_ZERROR);
                builder.append(lineCountPart);
                builder.append(STR_COLON);
            }
            int length = (data.length - i * numOfLine) > numOfLine ? numOfLine : (data.length - i * numOfLine);
            byte[] hexPartArray = new byte[length];
            System.arraycopy(data, i * numOfLine, hexPartArray, 0, length);
            if (columnB == 2) {

                StringBuilder hsb = new StringBuilder();
                for (int j = 0; j < length; j++) {
                    String tmpStr = StringUtils.leftPad(Integer.toHexString(hexPartArray[j] & 0xff), 2, CHAR_ZERROR);
                    hsb.append(tmpStr);
                    if (j < numOfLine - 1) {
                        hsb.append(CHAR_BLANK);
                    }
                }
                String hexPart = StringUtils.rightPad(hsb.toString(), 47, CHAR_BLANK);
                builder.append(hexPart);
                builder.append(CHAR_SEMICOLON);
                builder.append(CHAR_BLANK);
            }
            if (columnC == 1) {
                builder.append(hex2String(hexPartArray, charset));
            }
            if (i == lines - 1) {
                break;
            }
            builder.append(CHAR_NEWLINE);
        }
        return builder.toString();
    }

    public static String hex2String(byte[] hex, String charset) {
        int length = hex.length;
        StringBuilder sb = new StringBuilder(32);
        try {
            for (int j = 0; j < length; j++) {
                char ch1 = (char)hex[j];
                if (ch1 <= 32) {
                    ch1 = CHAR_DOT;
                } else if (ch1 <= 128) {
                    // ascII,
                } else {
                    if (charset.equalsIgnoreCase(CHARSET_GB2312)) {
                        if (j + 1 <= length - 1) {
                            char ch2 = (char)hex[j + 1];
                            if ((byte)ch1 > (byte)0xb0 && (byte)ch1 < (byte)0xf7 && (byte)ch2 > (byte)0xa0
                                && (byte)ch2 < (byte)0xf0) {
                                sb.append(new String(hex, j, 2, charset));
                                j++;
                                continue;
                            }
                        }
                    }
                    if (charset.equalsIgnoreCase(CHARSET_GBK) || charset.equalsIgnoreCase(CHARSET_GB18030)) {
                        if (j + 1 <= length - 1) {
                            char ch2 = (char)hex[j + 1];
                            if ((byte)ch1 >= (byte)0x81 && (byte)ch1 <= (byte)0xfe && ((byte)ch2 & 0xFF) >= (byte)0x40
                                && (byte)ch2 <= (byte)0xfe) {
                                sb.append(new String(hex, j, 2, charset));
                                j++;
                                continue;
                            }
                        }
                    } else if (charset.equalsIgnoreCase(CHARSET_UTF8) || charset.equalsIgnoreCase(CHARSET_UTF_8)) {
                        if (j + 2 <= length - 1) // 3
                        {
                            char ch2 = (char)hex[j + 1];
                            char ch3 = (char)hex[j + 2];
                            if ((byte)ch1 >= (byte)0xe0 && (byte)ch2 >= (byte)0x80) {
                                String tmpStr = STR_EMPTY;
                                if ((byte)ch3 >= (byte)0x80) {
                                    tmpStr = new String(hex, j, 3, charset);
                                    j += 2;
                                } else {
                                    tmpStr = new String(hex, j, 2, charset);
                                    j++;
                                }
                                sb.append(tmpStr);
                                continue;
                            }
                        }
                    }

                }
                sb.append(ch1);
            }

        } catch (UnsupportedEncodingException e) {
            // TODO: ？
            e.printStackTrace();
            // throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * null
     *
     * @param src
     *            
     * @return 
     */
    public static boolean isEmpty(String src) {
        if (src == null || src.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     *
     * @param obj
     *            
     * @return 
     */
    public static String getString(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof String) {
            return (String)obj;
        } else if (obj instanceof byte[]) {
            return new String((byte[])obj);
        } else {
            return obj.toString();
        }
    }

    public static Integer getInt(Object obj) throws Exception {
        int i_obj;
        if (obj == null) {
            throw new Exception("null");
        } else if (obj instanceof Integer) {
            i_obj = (Integer)obj;
        } else if (obj instanceof String) {
            i_obj = Integer.parseInt((String)obj);
        } else if (obj instanceof byte[]) {
            i_obj = Integer.parseInt(new String((byte[])obj));
        } else if (obj instanceof Byte) {
            i_obj = (Byte)obj & 0xff; // ,,
        } else if (obj instanceof Short) {
            i_obj = (Short)obj;
        } else if (obj instanceof Long) {
            i_obj = ((Long)obj).intValue();
        } else if (obj instanceof Double) {
            i_obj = ((Double)obj).intValue();
        } else {
            i_obj = Integer.parseInt(obj.toString());
        }
        return i_obj;
    }

    public static String getString(Object obj, String charset) throws UnsupportedEncodingException {
        if (obj == null) {
            return null;
        } else if (obj instanceof String) {
            return (String)obj;
        } else if (obj instanceof byte[]) {
            return new String((byte[])obj, charset);
        } else {
            return obj.toString();
        }
    }

    /**
     * 
     *
     * @param iLen
     *            
     * @return 
     */
    public static String getBlankStr(int iLen) {
        return getBlankStr(" ", iLen);
    }

    /**
     * 
     *
     * @param cIn
     *            
     * @param iLen
     *            
     * @return 
     */
    public static String getBlankStr(char cIn, int iLen) {
        return getBlankStr(getString(cIn), iLen);
    }

    /**
     *  <BR>
     * , <b> </B>
     *
     * @param sIn
     *            ,null
     * @param iLen
     *            
     * @return 
     */
    public static String getBlankStr(String sIn, int iLen) {
        String sBlank = "";

        // 2 row(s) below added by mlrain @2007-8-4 05:14:29
        // for: 
        if (isEmpty(sIn)) {
            sIn = " ";
        }

        while (sBlank.length() < iLen) {
            sBlank += sIn;
        }
        return sBlank;
    }

    /**
     * <DL>
     * <DT><B> </B></DT>
     * <p>
     * <DD>,</DD>
     * </DL>
     * <p>
     *
     * @param source
     *            
     * @param len
     *            
     * @return 
     */
    public static String fillStr(String source, int len) {
        return fillStr(source, " ", len, RIGHT);
    }

    /**
     *  <BR>
     *  <BR>
     * 
     *
     * @param source
     *            
     * @param len
     *            
     * @param align
     *            （0-,1-,2-）
     * @return 
     */
    public static String fillStr(String source, int len, int align) {
        return fillStr(source, " ", len, align);
    }

    /**
     * <DL>
     * <DT><B> </B></DT>
     * <p>
     * <DD></DD>
     * </DL>
     * <p>
     * : 2005-6-16 13:59:08
     *
     * @param source
     *            
     * @param cIn
     *            
     * @param len
     *            
     * @return 
     */
    public static String fillStr(String source, char cIn, int len) {
        return fillStr(source, cIn, len, RIGHT);
    }

    /**
     * <p>
     *  <BR>
     *  <BR>
     * 
     * </p>
     *  <BR>
     *
     * @param source
     *            
     * @param cIn
     *            
     * @param len
     *            
     * @param align
     *            （0-,1-,2-）
     * @return 
     */
    public static String fillStr(String source, char cIn, int len, int align) {
        return fillStr(source, getString(cIn), len, align);
    }

    /**
     * <p>
     *  <BR>
     *  <BR>
     * 
     * </p>
     *  <BR>
     *
     * @param source
     *            ,
     * @param sIn
     *            ,
     * @param len
     *            
     * @param align
     *            （0-,1-,2-）
     * @return 
     */
    public static String fillStr(String source, String sIn, int len, int align) {
        String sTmp = "";

        // 1 row(s) below edited by mlrain @2007-8-22 04:38:08
        // for: BUG；BUG
        // 2 row(s) below added by mlrain @2007-8-4 05:15:47
        // for: ,
        if (source == null || sIn == null) {
            return source;
        }

        if (source.getBytes().length < len) {
            if (align == LEFT) {
                sTmp = source + getBlankStr(sIn, len - source.getBytes().length);
            } else if (align == CENTER) {
                int iTmp = (len - source.getBytes().length) / 2;
                sTmp = getBlankStr(sIn, iTmp) + source + getBlankStr(sIn, len - source.getBytes().length - iTmp);
            } else if (align == RIGHT) {
                sTmp = getBlankStr(sIn, len - source.getBytes().length) + source;
            } else {
                sTmp = source;
            }
        } else {
            sTmp = source;
        }

        return sTmp;
    }

    /**
     * <DL>
     * <DT><B>split </B></DT>
     * <p>
     * <DD> <code>String.split(String)</code> jdk1.4,。 <code>StringTokenizer</code>
     * 。,。, <code>splitEx(String, char)</code> 。</DD>
     * </DL>
     * <p>
     *
     * @param source
     *            
     * @param s
     *            
     * @return ,null
     */
    public static String[] split(String source, String s) {
        // 2 row(s) below added by mlrain @2007-8-4 05:17:15
        // for: null
        if (isEmpty(source)) {
            return null;
        }

        // N row(s) below edited by mlrain @2006-12-12 22:08:41
        // for: commons-lang,
        return StringUtils.split(source, s);
        // StringTokenizer st = new StringTokenizer(source, s);
        // String[] ss = new String[st.countTokens()];
        // int i = 0;
        // while (st.hasMoreTokens())
        // {
        // ss[i++] = st.nextToken();
        // }
        // return ss;
    }

    /**
     * <DL>
     * <DT><B>replace </B></DT>
     * <p>
     * <DD>jdk1.5,</DD>
     * </DL>
     * <p>
     *
     * @param source
     *            
     * @param from
     *            
     * @param to
     *            
     * @return ,、null,null
     */
    public static String replace(String source, String from, String to) {
        // 2 row(s) below added by mlrain @2007-8-4 05:18:59
        // for: 、null,null
        if (isEmpty(source) || isEmpty(from) || to == null) {
            return source;
        }

        // TODO: 
        StringBuffer sb = new StringBuffer(source);
        Stack<String> stack = new Stack<String>();
        int index = source.indexOf(from);
        while (index >= 0) {
            stack.push(String.valueOf(index));
            index = source.indexOf(from, index + from.length());
        }
        while (!stack.empty()) {
            index = Integer.parseInt(stack.pop().toString());
            sb.replace(index, index + from.length(), to);
        }
        return sb.toString();
    }

    public static String jsonSerailize(Map<String, String> msg) {
        return JSON.toJSONString(msg);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonDeserailize(String json) throws IOException {
        return JSON.parseObject(json, Map.class);
    }

    /**
     * 
     * 
     * @param str
     *            
     * @return 
     */
    public static String filterChinese(String str) {
        // 
        String result = str;
        boolean flag = isContainChinese(str);
        if (flag) {// 
            // 
            StringBuffer sb = new StringBuffer();
            // 
            boolean flag2 = false;
            // 
            char chinese = 0;
            // 5.
            // char[]
            char[] charArray = str.toCharArray();
            // 
            for (int i = 0; i < charArray.length; i++) {
                chinese = charArray[i];
                flag2 = isChinese(chinese);
                if (!flag2) {// 
                    sb.append(chinese);
                }
            }
            result = sb.toString();
        }
        return result;
    }

    /**
     * 
     * 
     * @param str
     *            
     * @return 
     * @warn 
     */
    public static boolean isContainChinese(String str) {
        Matcher m = NUMBER_PATTERN.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 
     * 
     * @param c
     *            
     * @return true
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 
     * 
     * @param string
     * @return
     */
    public static boolean isLetterOrDigits(String string) {
        boolean flag = false;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isLowerCase(string.charAt(i)) || Character.isUpperCase(string.charAt(i))
                || Character.isDigit(string.charAt(i))) {
                flag = true;
            } else {
                flag = false;
                return flag;
            }
        }
        return flag;
    }

    /**
     * 
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0;) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * 
     * @param str
     * @return
     */
    public static boolean isAllChars(String str) {
        if (str.matches("[a-zA-Z]+")) {
            return true;
        } else {
            return false;
        }
    }

    // json
    public static boolean isJson(String content){
        try {
            JSONObject jsonStr = JSONObject.parseObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //
    public static String countBfb(int a,int b){
        //    
        NumberFormat format = NumberFormat.getInstance();   
        // 2   
        format.setMaximumFractionDigits(2);   
        return format.format((float)a / (float)b * 100);
    }
    
    //
    public static float countBfbWithInt(int a,int b){
        float f = (float)a / (float)b;
        BigDecimal bd= new BigDecimal(f); 
        return bd.setScale(5, BigDecimal.ROUND_HALF_UP).floatValue();
    }
    
    /**
     * 
     * n ： 
     * @return
     */
    public static String getRandomCode(int n) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            String str = random.nextInt(2) % 2 == 0 ? "num" : "char";
            if ("char".equalsIgnoreCase(str)) { // 
                int nextInt = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(nextInt + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(str)) { // 
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    
    /**
     * 320
     */
    public static String get32Str(String str) {
        if(str.length() < 32){
            String tmp = "";
            for(int i=0;i<(32-str.length());i++){
                tmp = tmp.concat("0");
            }
            return str.concat(tmp);
        }else if(str.length() > 32){
            return str.substring(0, 31);
        }else{
            return str;
        }
    }
}