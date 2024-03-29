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

For a "Selection Size" of "1 sheet" the document is processed in groups of 4 
pages for each sheet of paper, where each page is captured as a BufferedImage. 
The 4th page is rotated anti-clockwise and scaled to fit on the bottom half of 
one side of the sheet. The 1st page is rotated anti-clockwise and scaled to 
fit on the top half of the same side of the sheet. On the reverse side, the 
2nd page is rotated clockwise and scaled to fit on the top half and the 3rd 
page is rotated clockwise and scaled to fit on the bottom half. This process 
is repeated for all groups of 4 pages in the source document.

For a "Selection Size" of more than 1 sheet, more pages are grouped in 
multiples of 4 and arranged in a similar, but more complex manner.

## Cloning and Running

Cloning is a bit awkward because an IDE is required to create the java build 
environment first. Then the code needs to be cloned and moved to the correct 
location in the java build environment before the code can be built.

To clone, build and run this code, use the following steps:

  1. Use NetBeans (or your favourite IDE) to build a project called PDFBooklet
  2. cd to the newly created src/ directory
  3. git clone https://github.com/PhilLockett/PDFBooklet.git
  4. Move the contents of PDFBooklet/ (.git, com, README.md) up into src/
  5. Delete the now empty PDFBooklet/ directory
  4. Refresh the IDE with the new content and run the code

## Points of interest

This code has the following points of interest:

  * PDFBooklet.java was developed as stand alone code.
  * A user GUI was developed using NetBeans to make using PDFBooklet easier.
  * The UserGui.form file, create by NetBeans is supplied.
  * The code provides functions for rotating a BufferedImage +/- 90 degrees.
  * The PDF processing can be performed in the background using a SwingWorker.
  * Using a SwingWorker enables a JProgressBar to be supported by the GUI.
