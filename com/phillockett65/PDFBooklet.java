/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phillockett65;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;



/**
 *
 * @author Phil
 */
public class PDFBooklet {

    private final static int DPI = 300;         // Dots Per Inch
    private final static int PPI = 72;          // Points Per Inch
    private final static float WIDTH = 8.5f;    // Paper width in Inches
    private final static float HEIGHT = 11f;    // Paper height in Inches
    private static int sheetCount = 1;
    private static boolean flip = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 1)
            PdfToImages(args[0], args[1]);
    }


    /**
     * Generate a booklet style PDF using a crude images of pages technique.
     * @param sourcePDF original document.
     * @param outputPDF booklet form of original document.
     */
    private static void PdfToImages(String sourcePDF, String outputPDF) {
        try (PDDocument inputDoc = PDDocument.load(new File(sourcePDF))) {
            final int MAX = inputDoc.getNumberOfPages();

            try (PDDocument outputDoc = new PDDocument()) {
                for (int firstPage = 0; firstPage < MAX; firstPage += 4*sheetCount) {
                    int lastPage = firstPage + 4*sheetCount;
                    if (lastPage > MAX)
                        lastPage = MAX;

                    BufferedImage[] imageArray = pdfToImageArray(inputDoc, firstPage, lastPage);
                    addImagesToPdf(imageArray, outputDoc);
                    System.out.printf("Pages %d to %d\n", firstPage+1, lastPage);
                }
                inputDoc.close();
                
                outputDoc.save(outputPDF);
            }

            System.out.println("File created in: " + outputPDF);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create an array of images of pages from a PDF document.
     * @param doc PDF document to read.
     * @param firstPage first page to grab from inputDoc (pages start from 0).
     * @param lastPage stop grabbing pages BEFORE reaching the last page.
     * @return a BufferedImage array containing the page images.
     */
    private static BufferedImage[] pdfToImageArray(PDDocument doc, int firstPage, int lastPage) {
        ArrayList<BufferedImage> images = new ArrayList<>();

        PDFRenderer renderer = new PDFRenderer(doc);
        for (int page = firstPage; page < lastPage; ++page) {
            try {
                BufferedImage bim = renderer.renderImageWithDPI(page, DPI, ImageType.GRAY);
                images.add(bim);
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        // Turn ArrayList into an array.
        BufferedImage[] imageArray = new BufferedImage[images.size()];
        imageArray = images.toArray(imageArray);

        return imageArray;
    }

    /**
     * Add images to a PDF document, currently only supports up to 4 images. 
     * Images 4 and 1 are drawn to the front of the sheet and images 2 and 3 are
     * drawn to the back of the sheet.
     * @param images to be added to document in booklet arrangement.
     * @param doc to add images to.
     */
    private static void addImagesToPdf(BufferedImage[] images, PDDocument doc) {
        try {
            // Draw images to front of sheet.
            PDPage page = new PDPage();
            PDRectangle rectangle = page.getMediaBox();
            float height = rectangle.getHeight();

            doc.addPage(page);
            final float scale = (float)PPI * HEIGHT / (DPI * WIDTH * 2);

            PDPageContentStream stream = new PDPageContentStream(doc, page);
            PDImageXObject image;
            BufferedImage bimage;

            if (images.length > 0) {
                bimage = rotateBack(images[0]);
                image = LosslessFactory.createFromImage(doc, bimage);
                stream.drawImage(image, 0, (height/2), scale * image.getWidth(), scale * image.getHeight());
            }
            if (images.length > 3) {
                bimage = rotateBack(images[3]);
                image = LosslessFactory.createFromImage(doc, bimage);
                stream.drawImage(image, 0, 0, scale * image.getWidth(), scale * image.getHeight());
            }

            stream.close();

            // Draw images to back of sheet.
            page = new PDPage();
            doc.addPage(page);

            stream = new PDPageContentStream(doc, page);

            if (flip) {
                if (images.length > 1) {
                    bimage = rotateForward(images[1]);
                    image = LosslessFactory.createFromImage(doc, bimage);
                    stream.drawImage(image, 0, (height/2), scale * image.getWidth(), scale * image.getHeight());
                }
                if (images.length > 2) {
                    bimage = rotateForward(images[2]);
                    image = LosslessFactory.createFromImage(doc, bimage);
                    stream.drawImage(image, 0, 0, scale * image.getWidth(), scale * image.getHeight());
                }
            } else {
                if (images.length > 1) {
                    bimage = rotateBack(images[1]);
                    image = LosslessFactory.createFromImage(doc, bimage);
                    stream.drawImage(image, 0, 0, scale * image.getWidth(), scale * image.getHeight());
                }
                if (images.length > 2) {
                    bimage = rotateBack(images[2]);
                    image = LosslessFactory.createFromImage(doc, bimage);
                    stream.drawImage(image, 0, (height/2), scale * image.getWidth(), scale * image.getHeight());
                }
            }

            stream.close();

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Rotate an image 90 degrees anti-clockwise.
     * @param image to be rotated.
     * @return the rotated image.
     */
    private static BufferedImage rotateBack(BufferedImage image) {
        final int w = image.getWidth();
        final int h = image.getHeight();
        final BufferedImage rotated = new BufferedImage(h, w, image.getType());
        Graphics2D g2d = (Graphics2D)rotated.getGraphics();

        // Create transform.
        final AffineTransform at = new AffineTransform();
        at.quadrantRotate(3);
        at.translate(-w, 0);

        // Draw image onto rotated.
        g2d.drawImage(image, at, null);

        return rotated;
    }

    /**
     * Rotate an image 90 degrees clockwise.
     * @param image to be rotated.
     * @return the rotated image.
     */
    private static BufferedImage rotateForward(BufferedImage image) {
        final int w = image.getWidth();
        final int h = image.getHeight();
        final BufferedImage rotated = new BufferedImage(h, w, image.getType());
        Graphics2D g2d = (Graphics2D)rotated.getGraphics();

        // Create transform.
        final AffineTransform at = new AffineTransform();
        at.quadrantRotate(1);
        at.translate(0, -h);

        // Draw image onto rotated.
        g2d.drawImage(image, at, null);

        return rotated;
    }
}
