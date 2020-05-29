/*  PDFBooklet - a simple, crude program to generate a booklet from of a PDF.
 *
 *  Copyright 2020 Philip Lockett.
 *
 *  This file is part of PDFBooklet.
 *
 *  PDFBooklet is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  PDFBooklet is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with CardGen.  If not, see <https://www.gnu.org/licenses/>.
 */

 /*
 * This code provides a simple GUI front-end to the PDFBooklet class and can be
 * used to generate a booklet from of a source PDF document. 
 * 
 * Remember to set the runnable main to UserGui.main().
 * 
 * This code is dependent on PDFBooklet.java, which itself is dependent on
 * PDFbox (pdfbox-app-2.0.19.jar).
 */
package com.phillockett65;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;

/**
 *
 * @author Phil
 */
public class UserGui extends javax.swing.JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private PDFBooklet booklet;
    private String baseDirectory;
    private String sourcePDF;     // The source PDF filepath.
    private String outputPDF;     // The generated PDF filepath.

    /**
     * Creates new form UserGui
     */
    public UserGui() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("acrobat-icon.png")).getImage());
    }

    /**
     * Takes the current selection from the Page Size combo box and converts it
     * to the corresponding PDRectangle value.
     *
     * @return the corresponding PDRectangle value.
     */
    private PDRectangle getPS() {
        PDRectangle PS = PDRectangle.LETTER;

        switch (pageSizejComboBox.getSelectedItem().toString()) {
            case "A0":
                PS = PDRectangle.A0;
                break;
            case "A1":
                PS = PDRectangle.A1;
                break;
            case "A2":
                PS = PDRectangle.A2;
                break;
            case "A3":
                PS = PDRectangle.A3;
                break;
            case "A4":
                PS = PDRectangle.A4;
                break;
            case "A5":
                PS = PDRectangle.A5;
                break;
            case "A6":
                PS = PDRectangle.A6;
                break;
            case "Legal":
                PS = PDRectangle.LEGAL;
                break;
            case "Letter":
                PS = PDRectangle.LETTER;
                break;
        }

        return PS;
    }

    /**
     * Takes the current selection from the Dots Per Inch combo box and converts
     * it to the corresponding int value.
     *
     * @return the corresponding PDRectangle value.
     */
    private int getDPI() {
        final String sel = dotsPerInchjComboBox.getSelectedItem().toString();
        return Integer.parseInt(sel);
    }

    /**
     * Takes the current selection from the Image Type combo box and converts it
     * to the corresponding ImageType value.
     *
     * @return the corresponding PDRectangle value.
     */
    private ImageType getIT() {
        ImageType IT = ImageType.ARGB;

        switch (imageTypejComboBox.getSelectedItem().toString()) {
            case "ARGB":
                IT = ImageType.ARGB;
                break;
            case "Binary":
                IT = ImageType.BINARY;
                break;
            case "Gray":
                IT = ImageType.GRAY;
                break;
            case "RGB":
                IT = ImageType.RGB;
                break;
        }

        return IT;
    }

    /**
     * Takes the current selection from the Section Size combo box and converts
     * it to the corresponding int value.
     *
     * @return the corresponding PDRectangle value.
     */
    private int getSheetCount() {
        final int sel = sectionSizejComboBox.getSelectedIndex();
        return sel + 1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sourcePDFjLabel = new javax.swing.JLabel();
        sourcePDFjTextField = new javax.swing.JTextField();
        browsejButton = new javax.swing.JButton();
        pageSizejLabel = new javax.swing.JLabel();
        pageSizejComboBox = new javax.swing.JComboBox<>();
        dotsPerInchjLabel = new javax.swing.JLabel();
        dotsPerInchjComboBox = new javax.swing.JComboBox<>();
        imageTypejLabel = new javax.swing.JLabel();
        imageTypejComboBox = new javax.swing.JComboBox<>();
        outputPDFjLabel = new javax.swing.JLabel();
        outputPDFjTextField = new javax.swing.JTextField();
        generatejButton = new javax.swing.JButton();
        sectionSizejLabel = new javax.swing.JLabel();
        sectionSizejComboBox = new javax.swing.JComboBox<>();
        pagesjLabel = new javax.swing.JLabel();
        generatejProgressBar = new javax.swing.JProgressBar();
        outputjLabel = new javax.swing.JLabel();
        backgroundjLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PDF Booklet Generator 1.0");
        setPreferredSize(new java.awt.Dimension(670, 280));
        getContentPane().setLayout(null);

        sourcePDFjLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sourcePDFjLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sourcePDFjLabel.setText("Source Document:");
        sourcePDFjLabel.setToolTipText("File path to the Source PDF Document.");
        getContentPane().add(sourcePDFjLabel);
        sourcePDFjLabel.setBounds(30, 10, 130, 17);

        sourcePDFjTextField.setEditable(false);
        sourcePDFjTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sourcePDFjTextField.setText(".");
        getContentPane().add(sourcePDFjTextField);
        sourcePDFjTextField.setBounds(170, 10, 352, 23);

        browsejButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        browsejButton.setText("Browse...");
        browsejButton.setToolTipText("Select the Source PDF Document.");
        browsejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browsejButtonActionPerformed(evt);
            }
        });
        getContentPane().add(browsejButton);
        browsejButton.setBounds(540, 10, 89, 25);

        pageSizejLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pageSizejLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        pageSizejLabel.setText("Output Page Size:");
        pageSizejLabel.setToolTipText("Page Size of the generated PDF document.");
        getContentPane().add(pageSizejLabel);
        pageSizejLabel.setBounds(20, 50, 130, 17);

        pageSizejComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pageSizejComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A0", "A1", "A2", "A3", "A4", "A5", "A6", "Legal", "Letter" }));
        pageSizejComboBox.setSelectedIndex(8);
        getContentPane().add(pageSizejComboBox);
        pageSizejComboBox.setBounds(170, 50, 88, 23);

        dotsPerInchjLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dotsPerInchjLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dotsPerInchjLabel.setText("Dots Per Inch:");
        dotsPerInchjLabel.setToolTipText("Resolution of the page images.");
        getContentPane().add(dotsPerInchjLabel);
        dotsPerInchjLabel.setBounds(40, 90, 110, 17);

        dotsPerInchjComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dotsPerInchjComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "300", "600", "1200", "2400" }));
        getContentPane().add(dotsPerInchjComboBox);
        dotsPerInchjComboBox.setBounds(170, 90, 88, 23);

        imageTypejLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        imageTypejLabel.setText("Image Type:");
        imageTypejLabel.setToolTipText("Image Type of captured page.");
        getContentPane().add(imageTypejLabel);
        imageTypejLabel.setBounds(70, 130, 78, 17);

        imageTypejComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        imageTypejComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ARGB", "Binary", "Gray", "RGB" }));
        imageTypejComboBox.setSelectedIndex(2);
        getContentPane().add(imageTypejComboBox);
        imageTypejComboBox.setBounds(170, 130, 88, 23);

        outputPDFjLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        outputPDFjLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        outputPDFjLabel.setText("Output File Name:");
        outputPDFjLabel.setToolTipText("Output file name, pdf extension will be added.");
        getContentPane().add(outputPDFjLabel);
        outputPDFjLabel.setBounds(340, 60, 120, 17);

        outputPDFjTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        outputPDFjTextField.setText("booklet");
        getContentPane().add(outputPDFjTextField);
        outputPDFjTextField.setBounds(470, 60, 156, 23);

        generatejButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        generatejButton.setText("Generate");
        generatejButton.setToolTipText("Select the Source Document first.");
        generatejButton.setEnabled(false);
        generatejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatejButtonActionPerformed(evt);
            }
        });
        getContentPane().add(generatejButton);
        generatejButton.setBounds(520, 170, 103, 31);

        sectionSizejLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sectionSizejLabel.setText("Section Size:");
        sectionSizejLabel.setToolTipText("Image Type of captured page.");
        getContentPane().add(sectionSizejLabel);
        sectionSizejLabel.setBounds(70, 170, 78, 17);

        sectionSizejComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sectionSizejComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 sheet", "2 sheets", "3 sheets", "4 sheets", "5 sheets", "6 sheets" }));
        sectionSizejComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectionSizejComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(sectionSizejComboBox);
        sectionSizejComboBox.setBounds(170, 170, 88, 23);

        pagesjLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pagesjLabel.setText("(4 pages)");
        getContentPane().add(pagesjLabel);
        pagesjLabel.setBounds(280, 170, 84, 17);

        generatejProgressBar.setOpaque(true);
        generatejProgressBar.setStringPainted(true);
        getContentPane().add(generatejProgressBar);
        generatejProgressBar.setBounds(340, 120, 280, 20);

        outputjLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(outputjLabel);
        outputjLabel.setBounds(40, 210, 580, 10);

        backgroundjLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        backgroundjLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/phillockett65/background.jpg"))); // NOI18N
        backgroundjLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(backgroundjLabel);
        backgroundjLabel.setBounds(-5, -6, 680, 270);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Acts on the "Generate" button click event.
     *
     * @param evt the event that triggered the handler.
     */
    private void generatejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatejButtonActionPerformed

        outputPDF = baseDirectory + "\\" + outputPDFjTextField.getText()
                + ".pdf";

        booklet = new PDFBooklet(sourcePDF, outputPDF);

        booklet.setPageSize(getPS());
        booklet.setDotsPerInch(getDPI());
        booklet.setImageType(getIT());
        booklet.setSheetCount(getSheetCount());

        generatejButton.setEnabled(false);
        outputjLabel.setText("");

        // Use PDFBooklet.ProgressWorker to generate PDF in the background and
        // update the progress bar as we go.
        PDFBooklet.ProgressWorker pw = booklet.new ProgressWorker();
        pw.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if (name.equals("progress")) {
                    int progress = (int) evt.getNewValue();
                    generatejProgressBar.setValue(progress);
                    repaint();
                } else if (name.equals("state")) {
                    SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
                    switch (state) {
                        case DONE:
                            generatejButton.setEnabled(true);

                            outputjLabel.setText("File created in: " + outputPDF);
                            break;
                        default:
                            break;
                    }
                }
            }

        });
        pw.execute();

    }//GEN-LAST:event_generatejButtonActionPerformed

    /**
     * Select a source PDF file using a JFileChooser.
     *
     * @return true if a PDF file is selected, false otherwise.
     */
    private boolean selectSourcePDF() {
        // Set up the file selector.
        JFileChooser choice = new JFileChooser(baseDirectory);
        choice.setDialogTitle("Select source PDF document");
        choice.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter;
        filter = new FileNameExtensionFilter("PDF Files", "pdf");
        choice.setFileFilter(filter);
        choice.setAcceptAllFileFilterUsed(false);

        // Launch the file selector.
        int selected = choice.showOpenDialog(this);
        if (selected == JFileChooser.APPROVE_OPTION) {
            // Verify the file selection.
            File source = choice.getSelectedFile();
            baseDirectory = source.getParent();
            if (source.isFile()) {
                sourcePDF = source.getPath();
                sourcePDFjTextField.setText(sourcePDF);

                return true;
            }
        }

        return false;
    }

    /**
     * Acts on the "Browse..." button click event and enables the "Generate"
     * button if a valid source PDF file is selected.
     *
     * @param evt the event that triggered the handler.
     */
    private void browsejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browsejButtonActionPerformed
        final boolean selected = selectSourcePDF();
        if (selected) {
            generatejButton.setEnabled(true);
            generatejButton.setToolTipText("Generate the PDF in booklet form.");
        } else {
            generatejButton.setEnabled(false);
            generatejButton.setToolTipText("Select the Source Document first.");
        }
    }//GEN-LAST:event_browsejButtonActionPerformed

    private void sectionSizejComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectionSizejComboBoxActionPerformed

        final int pages = getSheetCount() * 4;
        pagesjLabel.setText("(" + pages + " pages)");
    }//GEN-LAST:event_sectionSizejComboBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroundjLabel;
    private javax.swing.JButton browsejButton;
    private javax.swing.JComboBox<String> dotsPerInchjComboBox;
    private javax.swing.JLabel dotsPerInchjLabel;
    private javax.swing.JButton generatejButton;
    private javax.swing.JProgressBar generatejProgressBar;
    private javax.swing.JComboBox<String> imageTypejComboBox;
    private javax.swing.JLabel imageTypejLabel;
    private javax.swing.JLabel outputPDFjLabel;
    private javax.swing.JTextField outputPDFjTextField;
    private javax.swing.JLabel outputjLabel;
    private javax.swing.JComboBox<String> pageSizejComboBox;
    private javax.swing.JLabel pageSizejLabel;
    private javax.swing.JLabel pagesjLabel;
    private javax.swing.JComboBox<String> sectionSizejComboBox;
    private javax.swing.JLabel sectionSizejLabel;
    private javax.swing.JLabel sourcePDFjLabel;
    private javax.swing.JTextField sourcePDFjTextField;
    // End of variables declaration//GEN-END:variables
}
