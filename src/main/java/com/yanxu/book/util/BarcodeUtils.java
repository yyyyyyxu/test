package com.yanxu.book.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class BarcodeUtils {

    public static BufferedImage createPdf417(BarcodeParam bp, boolean drawImage, boolean drawText) throws IOException {      String fontFamily = bp.getTextFontFamily();
        int fontSize = bp.getTextFontSize();
        double scaling = bp.getScaling();
        int marginTop = bp.getTextMarginTop();
        String textAlignment = bp.getTextAlignment();
        BarcodePDF417 pdf = new BarcodePDF417();
        pdf.setErrorLevel(4);// 容错等级4
        pdf.setOptions(BarcodePDF417.PDF417_FIXED_COLUMNS);// 固定列宽
        pdf.setCodeColumns(bp.getColumns()); // 列宽数 //条码列宽数为cols+2，cols最小值为1
        String barcode = bp.getImageContent();
        pdf.setText(barcode.getBytes("GBK"));

        pdf.setYHeight(1);// 一个模块的宽高比例
        // 获取生成二维码图片
        Image pdfImg = pdf.createAwtImage(Color.black, Color.white);
//      int width = (int) (pdfImg.getWidth(null) * scaling); // 154
//      int height = (int) (pdfImg.getHeight(null) * scaling); // 34
        double dotsPerMilli = 1.0 * Integer.valueOf(bp.getDpi()) / 10 / INCH_2_CM;
        int targetWidth = (int) (Integer.valueOf(bp.getWidth())*dotsPerMilli*scaling);
        int targetHight = (int) (Integer.valueOf(bp.getHeight())*dotsPerMilli*scaling);
        pdfImg = pdfImg.getScaledInstance(targetWidth, targetHight, Image.SCALE_DEFAULT);
        // BufferedImage.TYPE_BYTE_BINARY设置位深度为1
//          BufferedImage img = new BufferedImage(width+10, height+fontSize+marginTop+7,BufferedImage.TYPE_BYTE_BINARY);
        BufferedImage img = new BufferedImage(targetWidth + 10, targetHight + fontSize + marginTop + 7,
                BufferedImage.TYPE_INT_RGB);

        // 将空白图片的颜色设置白色
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                img.setRGB(i, j, RGB_WHITE);
            }
        }
        Graphics2D g = (Graphics2D) img.getGraphics();
        Font font = new Font(fontFamily, Font.PLAIN, fontSize);
        g.setFont(font);
        // 设置自体颜色
        g.setColor(Color.BLACK);
        // 画图片

        if(drawImage) {
            g.drawImage(pdfImg, 5, 5, targetWidth, targetHight, null);
        }
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // 画文字
        String barcodeText = bp.getTextContent();
        if(drawText && StringUtils.isNoneEmpty(barcodeText)) {
            AttributedString drawString = new AttributedString(barcodeText);
            drawString.addAttribute(TextAttribute.FONT, font, 0, barcodeText.length());
            //          g.drawString(drawString.getIterator(), 0 , height + TEXT_HEIGHT + 60);
            int w = (int) (g.getFontMetrics().stringWidth(barcodeText));
            int txtX = 0;
            if ("bottomright".equals(textAlignment)) {
                txtX = targetWidth - w;
            } else if (!"bottomleft".equals(textAlignment)) {
                txtX = (targetWidth - w) / 2;
            }
            g.drawString(barcodeText, txtX, targetHight + marginTop + fontSize);
        }
        return img;
    }


    public static void main(String[] args) {
        List<String> stringList= Arrays.asList("1","2","3");
    }
}
