# PDFBooket

A simple, crude program to generate a booklet from of a PDF.

## Overview

PDFBooklet.java can be used as a standalone file or with the GUI front end 
defined in UserGui.java. In both cases it is dependent on PDFbox. I used
pdfbox-app-2.0.19.jar (https://pdfbox.apache.org/download.cgi).

## Usage

PDFBooklet is a simple, crude program to generate a booklet from of a source 
PDF document. It requires 2 command line parameters, the source PDF and the 
name of the new PDF. Example usage:
    java -jar path-to-PDFBooklet.jar path-to-source.pdf path-to-new.pdf

The executable PDFBooklet.jar must contain pdfbox-app-2.x.x.jar.

PDFBooklet can also be used as an external java class, in which case 
PDFBooklet.main() should be superseded. UserGui.java is an example that 
instantiates the class, sets the user selected attributes and then executes 
the generator in the background using a SwingWorker.

## Bookbinding

This code supports multi-sheet sections. For more information on bookbinding
terms and techniques refer to:
 * [Terms](https://en.wikipedia.org/wiki/Bookbinding#Terms_and_techniques)
 * [Layout](https://www.formaxprinting.com/blog/2016/11/booklet-layout-how-to-arrange-the-pages-of-a-saddle-stitched-booklet/)
 * [Bindings](https://www.studentbookbinding.co.uk/blog/how-to-set-up-pagination-section-sewn-bindings)


## Implementation Summary

The implementation is crude in that the source pages are captured as images
which are then rotated, scaled and arranged on the pages. As a result, the
generated document is significantly larger and grainier.

The document is processed in groups of 4 pages for each sheet of paper, where
each page is captured as a BufferedImage. The 4th page is rotated anti-
clockwise and scaled to fit on the bottom half of one side of the sheet. The
1st page is rotated anti-clockwise and scaled to fit on the top half of the
same side of the sheet. On the reverse side, the 2nd page is rotated
clockwise and scaled to fit on the top half and the 3rd page is rotated
clockwise and scaled to fit on the bottom half. This process is repeated for
all groups of 4 pages in the source document.
