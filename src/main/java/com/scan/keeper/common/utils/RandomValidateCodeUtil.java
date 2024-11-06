package com.scan.keeper.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tangbinqi
 * 
 */
public class RandomValidateCodeUtil {

	public static final String RANDOMCODEKEY= "RANDOMVALIDATECODEKEY";//sessionkey
//    private String randString = "0123456789";// private String
    //private String randString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//
    private static final String RAND_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//
    private static final int WIDTH = 95;// 
    private static final int HEIGHT = 25;// 
    private static final int LINE_SIZE = 40;// 
    private static final int STRING_NUM = 4;// 

    private static final Logger logger = LoggerFactory.getLogger(RandomValidateCodeUtil.class);

    private static final Random RANDOM = new Random();

    /**
     * 
     */
    private static Font getFont() {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
    }

    /**
     * 
     */
    private static Color getRandColor(int fc, int bc) {
        if (fc > 255){
            fc = 255;
        }
        if (bc > 255){
            bc = 255;
        }
        int r = fc + RANDOM.nextInt(bc - fc - 16);
        int g = fc + RANDOM.nextInt(bc - fc - 14);
        int b = fc + RANDOM.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * 
     */
    public static String getRandcode(OutputStream outputStream){
//    		HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession();
        // BufferedImageImage,Image
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// ImageGraphics,
        g.fillRect(0, 0, WIDTH, HEIGHT);//
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));//
        g.setColor(getRandColor(110, 133));//
        // 
        for (int i = 0; i <= LINE_SIZE; i++) {
            drowLine(g);
        }
        // 
        String randomString = "";
        for (int i = 1; i <= STRING_NUM; i++) {
            randomString = drowString(g, randomString, i);
        }
        logger.info(randomString);
        //session
//        session.removeAttribute(RANDOMCODEKEY);
//        session.setAttribute(RANDOMCODEKEY, randomString);
        g.dispose();
        try {
            // 
//            ImageIO.write(image, "JPEG", response.getOutputStream());
        	ImageIO.write(image, "JPEG", outputStream);
        } catch (Exception e) {
            logger.error(">>>>   ", e);
        }
        return randomString;
    }

    /**
     * 
     */
    private static String drowString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(RANDOM.nextInt(101), RANDOM.nextInt(111), RANDOM
                .nextInt(121)));
        String rand = String.valueOf(getRandomString(RANDOM.nextInt(RAND_STRING
                .length())));
        randomString += rand;
        g.translate(RANDOM.nextInt(3), RANDOM.nextInt(3));
        g.drawString(rand, 13 * i, 16);
        return randomString;
    }

    /**
     * 
     */
    private static void drowLine(Graphics g) {
        int x = RANDOM.nextInt(WIDTH);
        int y = RANDOM.nextInt(HEIGHT);
        int xl = RANDOM.nextInt(13);
        int yl = RANDOM.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * 
     */
    public static String getRandomString(int num) {
        return String.valueOf(RAND_STRING.charAt(num));
    }

}
