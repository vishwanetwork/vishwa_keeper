package com.scan.keeper.utils;

import javax.imageio.stream.FileImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Base64Util {
    /**
     * 
     * @param base64Str
     * @return
     */
    public static byte[] decode(String base64Str){
        byte[] b = null;
        b = Base64.getDecoder().decode(replaceEnter(base64Str));
        return b;
    }

    /**
     * 
     * @param image
     * @return
     */
    public static String encode(byte[] image){
        return replaceEnter(Base64.getEncoder().encodeToString(image));
    }

//    public static String encode(String uri){
//        BASE64Encoder encoder = new BASE64Encoder();
//        return replaceEnter(encoder.encode(uri.getBytes()));
//    }

    /**
     *
     * @path    
     * @return
     */

    public static byte[] imageTobyte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while((numBytesRead = input.read(buf)) != -1){
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }



    public static String replaceEnter(String str){
        String reg ="[\n-\r]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }


}