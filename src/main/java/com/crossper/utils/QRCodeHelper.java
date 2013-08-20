/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossper.utils;

import com.crossper.models.QRCodeOverlay;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

/**
 *
 * Generate QR code and PDf flyer containing the image
 */
@Component 
public class QRCodeHelper {
    private static QRCodeGenerator generator;
    public static final String QRCODE = "qrcode";
    public static final String PNG = "png";
    public static final int QRCODE_SIZE = 170;
    private String qrCodeDir = "src/test/data/images/qrcode";
    private String basePath;
    private String flyerTemplateName = "Flyer-V1.png";
    private static final String DEFAULT_TEMPLATE = "Flyer-V1.png";
    public enum FLYER_TYPES {FLYER_0, FLYER_1, FLYER_2};
    private List<QRCodeOverlay> overlays;
    private final int DEFAULT_X_OVERLAY = 154;
    private final int DEFAULT_Y_OVERLAY = 300;
    private int flyerType = 0;
    
    public String createQRCodePdf( String businessId, String message, int flyerType) {
        String pdfName = "QRcodeFlyer_"+businessId+"_"+flyerType+".pdf";
       
        File qrcodeFile = new File(getBasePath(), "QRCode-"+businessId+".png"); //new File(qrCodeDir, "QRCode-"+businessId+".png");
        generator = getGenerator();
        generator.setSize(QRCODE_SIZE,QRCODE_SIZE);
        try {
            BitMatrix matrix = generator.generateMatrix(message);
            generator.writeToFile(matrix, "png", qrcodeFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //crossper flyer
            File[] imgFiles = new File[2];
            imgFiles[0] = new File(getBasePath(), getFlyerTemplateName()); //new File(qrCodeDir, getFlyerTemplateName());
            imgFiles[1] = qrcodeFile;
            String outFileName = "Flyer"+businessId+".png";
            mergeImageFiles(imgFiles, outFileName);
            createPdfFromPng(outFileName,pdfName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            qrcodeFile.delete();
            File mergedPng =   new File(getBasePath(), "Flyer"+businessId+".png");
            mergedPng.delete();
        }
        return pdfName;
    }
    
    private  void mergeImageFiles(File[] imgFiles, String outFileName) {
        try {
         BufferedImage image = ImageIO.read(imgFiles[0]);
         BufferedImage overlay = ImageIO.read(imgFiles[1]);

        // create the new image, canvas size is the max. of both image sizes
        int w = Math.max(image.getWidth(), overlay.getWidth());
        int h = Math.max(image.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.drawImage(overlay, getXOverlay(),getYOverlay(), null);

        // Save as new image
        ImageIO.write(combined, "PNG", new File(getBasePath(), outFileName));
        }catch (Exception ex) {
            
        }

    }
    
    private void createPdfFromPng(String pngFileName, String pdfFileName) {
        try {
            Document convertPngToPdf = new Document();
            float height = convertPngToPdf.getPageSize().getHeight();
            float width = convertPngToPdf.getPageSize().getWidth();
            
            File file = new File(getBasePath(), pdfFileName);
            PdfWriter.getInstance(convertPngToPdf, new FileOutputStream(file));
            
            convertPngToPdf.open();
            File outFile = new File(getBasePath(), pngFileName);
            Image convertBmp = PngImage.getImage(outFile.getAbsolutePath());
            convertBmp.setAlignment(Image.ALIGN_CENTER);
            convertBmp.scaleToFit(width, height);
           
           convertPngToPdf.add(convertBmp);
            //Close Document
           convertPngToPdf.close();
            
        } catch (Exception i1) {
            i1.printStackTrace();
        }
    }

    public static QRCodeGenerator getGenerator() {
        if ( generator == null) {
            generator = new QRCodeGenerator();
        }
        return generator;
    }

    public static void setGenerator(QRCodeGenerator generator) {
        QRCodeHelper.generator = generator;
    }

    public String getQrCodeDir() {
        return qrCodeDir;
    }

    public void setQrCodeDir(String qrCodeDir) {
        this.qrCodeDir = qrCodeDir;
    }

    public String getFlyerTemplateName() {
        if ( flyerTemplateName == null) 
            return DEFAULT_TEMPLATE;
        else
            return flyerTemplateName;
    }

    public void setFlyerTemplateName(String flyerTemplateName) {
        this.flyerTemplateName = flyerTemplateName;
    }
    
    private String getBasePath() {
        basePath = getQrCodeDir();
        return basePath;
    }

    public List<QRCodeOverlay> getOverlays() {
        return overlays;
    }

    public void setOverlays(List<QRCodeOverlay> overlays) {
        this.overlays = overlays;
    }
    
    private int getXOverlay() {
        if ( getOverlays() != null  && !getOverlays().isEmpty() && getOverlays().get(flyerType) != null)
            return getOverlays().get(flyerType).getX();
        else
            return DEFAULT_X_OVERLAY;
            
    }
    private int getYOverlay() {
        if ( getOverlays() != null  && !getOverlays().isEmpty() && getOverlays().get(flyerType) != null)
            return getOverlays().get(flyerType).getY();
        else
            return DEFAULT_Y_OVERLAY;
    }
    
    public boolean isPdfPresent(String pdfFileName) {
        File file = new File(getBasePath(), pdfFileName);
        if (file.exists())
            return true;
        else
            return false;
    }
    
    public String getPdfName( String businessId, int type) {
        String pdfName = "QRcodeFlyer_"+ businessId+"_"+ type +".pdf";
        return pdfName;
    }
    
    public File getPdfFile( String businessId, int type) {
       String pdfFileName = getPdfName( businessId, type);
       File file = new File(getBasePath(), pdfFileName);
       return file;
    }
} 
