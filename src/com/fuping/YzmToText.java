package com.fuping;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLContext;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YzmToText {

    public static String getCode() {
        String result = "";
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("tmp\\yzm.jpg"));

            ImageIO.write(img, "JPG", new File("tmp\\yzm2.jpg"));
            img = ImageIO.read(new File("tmp\\yzm2.jpg"));
            ITesseract instance = new Tesseract();
            instance.setLanguage("num");
            result = instance.doOCR(img).replace(" ", "").replace("\n", "");
            System.out.println(result);
        } catch (Exception e) {

        }
        return result;
    }

    public static BufferedImage gray(BufferedImage srcImage) {

        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        srcImage = op.filter(srcImage, null);
        return srcImage;

    }

    public static String getOcr(BufferedImage img2) throws Exception {
        BufferedImage img = gray(img2);
        List<BufferedImage> listImg = splitImage(img);
        Map<BufferedImage, String> map = loadTrainData();
        String result = "";
        for (BufferedImage bi : listImg) {
            result += getSingleCharOcr(bi, map);
        }
        return result;
    }

    public static List<BufferedImage> splitImage(BufferedImage img)
            throws Exception {
        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        subImgs.add(img.getSubimage(16, 11, 13, 19));
        subImgs.add(img.getSubimage(32, 11, 13, 19));
        subImgs.add(img.getSubimage(48, 11, 13, 19));
        subImgs.add(img.getSubimage(64, 11, 13, 19));
        return subImgs;
    }

    public static Map<BufferedImage, String> loadTrainData() throws Exception {
        Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
        File dir = new File("train");
        File[] files = dir.listFiles();
        for (File file : files) {
            map.put(ImageIO.read(file), file.getName().charAt(0) + "");
        }
        return map;
    }

    public static String getSingleCharOcr(BufferedImage img,
                                          Map<BufferedImage, String> map) {
        String result = "";
        int width = img.getWidth();
        int height = img.getHeight();
        int min = width * height;
        for (BufferedImage bi : map.keySet()) {
            int count = 0;
            Label1:
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    if (isWhite(img.getRGB(x, y)) != isWhite(bi.getRGB(x, y))) {
                        count++;
                        if (count >= min)
                            break Label1;
                    }
                }
            }
            if (count < min) {
                min = count;
                result = map.get(bi);
            }
        }
        return result;
    }

    public static int isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 100) {
            return 1;
        }
        return 0;
    }

    public static int isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
            return 1;
        }
        return 0;
    }


    public static BufferedImage removeBackgroud(BufferedImage img) throws Exception {

//        BufferedImage img = ImageIO.read(new File(fileName));
        //获取图片的高宽
        int width = img.getWidth();
        int height = img.getHeight();

        //循环执行除去干扰像素
        for (int i = 1; i < width; i++) {
            Color colorFirst = new Color(img.getRGB(i, 1));
            int numFirstGet = colorFirst.getRed() + colorFirst.getGreen() + colorFirst.getBlue();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = new Color(img.getRGB(x, y));
//                    System.out.println("red:"+color.getRed()+" | green:"+color.getGreen()+" | blue:"+color.getBlue());
                    int num = color.getRed() + color.getGreen() + color.getBlue();
                    if (num >= numFirstGet) {
                        img.setRGB(x, y, Color.WHITE.getRGB());
                    }
                }
            }
        }

        //图片背景变黑色
        for (int i = 1; i < width; i++) {
            Color color1 = new Color(img.getRGB(i, 1));
            int num1 = color1.getRed() + color1.getGreen() + color1.getBlue();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = new Color(img.getRGB(x, y));
//                    System.out.println("red:"+color.getRed()+" | green:"+color.getGreen()+" | blue:"+color.getBlue());
                    int num = color.getRed() + color.getGreen() + color.getBlue();
                    if (num == num1) {
                        img.setRGB(x, y, Color.WHITE.getRGB());
                    } else {
                        img.setRGB(x, y, Color.BLACK.getRGB());
                    }
                }
            }
        }


        return img;
    }

    public static void main(String args[]) {
        System.out.println(getCode());
    }


}
