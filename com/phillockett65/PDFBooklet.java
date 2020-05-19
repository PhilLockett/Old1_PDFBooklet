/*
 * PDFBooklet is a simple, crude program to generate a booklet form of a PDF
 * document. It requires 2 parameters, the source PDF and the name of the new
 * PDF.
 * 
 * Example usage:
 *  java -jar path-to-PDFBooklet.jar path-to-source.pdf path-to-new.pdf
 * 
 * Dependencies:
 *  PDFbox (pdfbox-app-2.0.19.jar)
 * 
 * Currently this code only supports a single sheet bifolium. In other words, a
 * single sheet containing 4 pages, 2 on each side. In this way, when the sheet
 * is folded in half a booklet is formed. For more information, see:
 *  https://en.wikipedia.org/wiki/Bookbinding#Terms_and_techniques
 *  https://www.formaxprinting.com/blog/2016/11/
 *      booklet-layout-how-to-arrange-the-pages-of-a-saddle-stitched-booklet/
 *  https://www.studentbookbinding.co.uk/blog/
 *      how-to-set-up-pagination-section-sewn-bindings
 * 
 * The implementation is crude in that the source pages are captured as images
 * which are then rotated, scaled and arranged on the pages. As a result, the
 * generated document is significantly larger and grainier.
 * 
 * The document is processed in groups of 4 pages for each sheet of paper, where
 * each page is captured as a BufferedImage. The 4th page is rotated anti-
 * clockwise and scaled to fit on the bottom half of one side of the sheet. The
 * 1st page is rotated anti-clockwise and scaled to fit on the top half of the
 * same side of the sheet. On the reverse side, the 2nd page is rotated
 * clockwise and scaled to fit on the top half and the 3rd page is rotated
 * clockwise and scaled to fit on the bottom half. This process is repeated for
 * all groups of 4 pages in the source document.
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

    private int DPI = 300;         // Dots Per Inch
    private PDRectangle PS = PDRectangle.LETTER;
    private ImageType IT = ImageType.GRAY;

    private final String sourcePDF;     // The source PDF filepath.
    private final String outputPDF;     // The generated PDF filepath.

    private PDDocument inputDoc;        // The source PDF document.
    private PDDocument outputDoc;       // The generated PDF document.
    private PDPage page;                // Current page of "outputDoc".
    private PDPageContentStream stream; // Current stream of "outputDoc".
    private float width;                // "page" width in Points Per Inch.
    private float height;               // "page" height in Points Per Inch.
    private float hHeight;              // Half height.

    // Calculate the Aspect Ratio of half the page (view port).
    private float VPAR;                 // View Port Aspect Ratio.

    private static int sheetCount = 1;
    private static boolean flip = true;         // Required?

    /**
     * Constructor.
     *
     * @param inPDF file path for source PDF.
     * @param outPDF file path for generated PDF.
     */
    public PDFBooklet(String inPDF, String outPDF) {
        sourcePDF = inPDF;
        outputPDF = outPDF;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            PDFBooklet app = new PDFBooklet(args[0], args[1]);
            app.setDotsPerInch(300);
            app.setPageSize(PDRectangle.LETTER);
            app.setImageType(ImageType.GRAY);

            app.PdfToImages();
        }
    }

    public void setDotsPerInch(int val) {
        DPI = val;
    }

    public void setPageSize(PDRectangle size) {
        PS = size;
    }

    public void setImageType(ImageType type) {
        IT = type;
    }

    /**
     * Add a new page to "outputDoc".
     */
    public void addNewPage() {
        page = new PDPage(PS);
        outputDoc.addPage(page);

        final PDRectangle rectangle = page.getMediaBox();
        width = rectangle.getWidth();
        height = rectangle.getHeight();
        hHeight = (height / 2);

        // Calculate the Aspect Ratio of half the page (view port).
        VPAR = width / hHeight; // View Port Aspect Ratio.
    }

    /**
     * Start a new stream on the current page of "outputDoc".
     */
    public void startNewStream() {
        try {
            stream = new PDPageContentStream(outputDoc, page);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Close the current stream of "outputDoc".
     */
    public void endStream() {
        try {
            stream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generate a booklet style PDF using a crude images of pages technique.
     */
    private void PdfToImages() {
        try {
            inputDoc = PDDocument.load(new File(sourcePDF));
            final int MAX = inputDoc.getNumberOfPages();

            try {
                outputDoc = new PDDocument();
                for (int first = 0; first < MAX; first += 4 * sheetCount) {
                    int last = first + 4 * sheetCount;
                    if (last > MAX) {
                        last = MAX;
                    }

                    BufferedImage[] imageArray = pdfToImageArray(first, last);
                    addImagesToPdf(imageArray);
                    System.out.printf("Pages %d to %d\n", first + 1, last);
                }
                inputDoc.close();

                outputDoc.save(outputPDF);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("File created in: " + outputPDF);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create an array of images of pages from a PDF document.
     *
     * @param first page to grab from inputDoc (pages start from 0).
     * @param last stop grabbing pages BEFORE reaching the last page.
     * @return a BufferedImage array containing the page images.
     */
    private BufferedImage[] pdfToImageArray(int first, int last) {
        ArrayList<BufferedImage> images = new ArrayList<>();

        PDFRenderer renderer = new PDFRenderer(inputDoc);
        for (int page = first; page < last; ++page) {
            try {
                BufferedImage bim = renderer.renderImageWithDPI(page, DPI, IT);
                images.add(bim);
            } catch (IOException e) {
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
     *
     * @param images array to be added to document in booklet arrangement.
     */
    private void addImagesToPdf(BufferedImage[] images) {
        try {
            final int count = images.length;
            BufferedImage image;

            // Draw images to front of sheet.
            addNewPage();
            startNewStream();
            if (count > 0) {
                image = flip(images[0], false);
                addImageToPdf(image, true);
            }
            if (count > 3) {
                image = flip(images[3], false);
                addImageToPdf(image, false);
            }
            endStream();

            // Draw images to back of sheet.
            addNewPage();
            startNewStream();
            if (flip) {
                if (count > 1) {
                    image = flip(images[1], true);
                    addImageToPdf(image, true);
                }
                if (count > 2) {
                    image = flip(images[2], true);
                    addImageToPdf(image, false);
                }
            } else {
                if (count > 1) {
                    image = flip(images[1], false);
                    addImageToPdf(image, true);
                }
                if (count > 2) {
                    image = flip(images[2], false);
                    addImageToPdf(image, false);
                }
            }
            endStream();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Add a buffered image to the top or bottom of a page in a PDF document.
     * The image is scaled to fit and centered.
     *
     * @param image to add to document.
     * @param top flag to indicate top or bottom of the page
     * @throws IOException
     */
    private void addImageToPdf(BufferedImage image, boolean top)
            throws IOException {

        final float base = top ? hHeight : 0f;

        // Calculate the Aspect Ratio of "image".
        final int w = image.getWidth();
        final int h = image.getHeight();
        final float IAR = w / h;    // "image" Aspect Ratio.

        // Calculate "scale" based on the aspect ratio of "image" and centre it.
        float scale;
        float dx = 0f;
        float dy = 0f;
        if (IAR < VPAR) {
            scale = hHeight / h;
            dx = (width - (w * scale)) / 2;
        } else {
            scale = width / w;
            dy = (hHeight - (h * scale)) / 2;
        }

        // Create the PDImage and draw it on the page.
        PDImageXObject img = LosslessFactory.createFromImage(outputDoc, image);
        stream.drawImage(img, dx, base + dy, scale * w, scale * h);
    }

    /**
     * Rotate an image 90 degrees clockwise or anti-clockwise.
     *
     * @param image to be rotated.
     * @param clockwise direction flag
     * @return the rotated image.
     */
    private static BufferedImage flip(BufferedImage image, boolean clockwise) {
        final int w = image.getWidth();
        final int h = image.getHeight();

        // Create transform.
        final AffineTransform at = new AffineTransform();
        if (clockwise) {
            at.quadrantRotate(1);
            at.translate(0, -h);
        } else {
            at.quadrantRotate(3);
            at.translate(-w, 0);
        }

        // Draw image onto rotated.
        final BufferedImage rotated = new BufferedImage(h, w, image.getType());
        Graphics2D g2d = (Graphics2D) rotated.getGraphics();
        g2d.drawImage(image, at, null);

        return rotated;
    }

}
