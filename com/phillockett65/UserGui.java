/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phillockett65;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;

/**
 *
 * @author Phil
 */
public class UserGui extends javax.swing.JFrame {

    private PDFBooklet booklet;
    private String baseDirectory;
    private String sourcePDF;     // The source PDF filepath.
    private String outputPDF;     // The generated PDF filepath.

    /**
     * Creates new form UserGui
     */
    public UserGui() {
        initComponents();
        initMyComponent();
    }

    /**
     * Creates and initializes all the screen components.
     */
    private void initMyComponent() {
    }

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

    private int getDPI() {
        return Integer.parseInt(dotsPerInchjComboBox.getSelectedItem().toString());
    }

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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PDF Booklet Generator 1.0");
        setResizable(false);

        sourcePDFjLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sourcePDFjLabel.setText("Source Document:");
        sourcePDFjLabel.setToolTipText("File path to the Source PDF Document.");

        sourcePDFjTextField.setEditable(false);
        sourcePDFjTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sourcePDFjTextField.setText(".");

        browsejButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        browsejButton.setText("Browse...");
        browsejButton.setToolTipText("Select the Source PDF Document.");
        browsejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browsejButtonActionPerformed(evt);
            }
        });

        pageSizejLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pageSizejLabel.setText("Page Size:");
        pageSizejLabel.setToolTipText("Page Size of the generated PDF document.");

        pageSizejComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pageSizejComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A0", "A1", "A2", "A3", "A4", "A5", "A6", "Legal", "Letter" }));
        pageSizejComboBox.setSelectedIndex(8);

        dotsPerInchjLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dotsPerInchjLabel.setText("Dots Per Inch:");
        dotsPerInchjLabel.setToolTipText("Resolution of the page images.");

        dotsPerInchjComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dotsPerInchjComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "300", "600", "1200", "2400" }));

        imageTypejLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        imageTypejLabel.setText("Image Type:");
        imageTypejLabel.setToolTipText("Image Type of captured page.");

        imageTypejComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        imageTypejComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ARGB", "Binary", "Gray", "RGB" }));
        imageTypejComboBox.setSelectedIndex(2);

        outputPDFjLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        outputPDFjLabel.setText("Output file name:");
        outputPDFjLabel.setToolTipText("Output file name, pdf extension will be added.");

        outputPDFjTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        outputPDFjTextField.setText("booklet");

        generatejButton.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        generatejButton.setText("Generate");
        generatejButton.setToolTipText("Generate the PDF in booklet form.");
        generatejButton.setEnabled(false);
        generatejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatejButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sourcePDFjLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sourcePDFjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(browsejButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(outputPDFjLabel)
                            .addComponent(pageSizejLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pageSizejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(dotsPerInchjLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dotsPerInchjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(imageTypejLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(imageTypejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(outputPDFjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(generatejButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sourcePDFjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sourcePDFjLabel)
                    .addComponent(browsejButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dotsPerInchjLabel)
                    .addComponent(dotsPerInchjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imageTypejLabel)
                    .addComponent(imageTypejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pageSizejLabel)
                    .addComponent(pageSizejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputPDFjLabel)
                    .addComponent(outputPDFjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generatejButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void generatejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatejButtonActionPerformed
        outputPDF = baseDirectory + "\\" + outputPDFjTextField.getText() + ".pdf";
        booklet = new PDFBooklet(sourcePDF, outputPDF);

        booklet.setPageSize(getPS());
        booklet.setDotsPerInch(getDPI());
        booklet.setImageType(getIT());

        booklet.genBooklet();
    }//GEN-LAST:event_generatejButtonActionPerformed

    private boolean selectSourcePDF() {
        JFileChooser choice = new JFileChooser(baseDirectory);
        choice.setDialogTitle("Select source PDF document");
        choice.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
        choice.setFileFilter(filter);
        choice.setAcceptAllFileFilterUsed(false);
        int selected = choice.showOpenDialog(this);
        if (selected == JFileChooser.APPROVE_OPTION) {
            File source = choice.getSelectedFile();
            baseDirectory = source.getParent();
            if (source.isFile()) {
                sourcePDF = source.getPath();
                System.out.printf("sourcePDF %s\n", sourcePDF);
                System.out.printf("baseDirectory %s\n", baseDirectory);
                sourcePDFjTextField.setText(sourcePDF); 

                return true;
            }
        }
        return false;
    }

    private void browsejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browsejButtonActionPerformed
        generatejButton.setEnabled(selectSourcePDF());
    }//GEN-LAST:event_browsejButtonActionPerformed

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
    private javax.swing.JButton browsejButton;
    private javax.swing.JComboBox<String> dotsPerInchjComboBox;
    private javax.swing.JLabel dotsPerInchjLabel;
    private javax.swing.JButton generatejButton;
    private javax.swing.JComboBox<String> imageTypejComboBox;
    private javax.swing.JLabel imageTypejLabel;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel outputPDFjLabel;
    private javax.swing.JTextField outputPDFjTextField;
    private javax.swing.JComboBox<String> pageSizejComboBox;
    private javax.swing.JLabel pageSizejLabel;
    private javax.swing.JLabel sourcePDFjLabel;
    private javax.swing.JTextField sourcePDFjTextField;
    // End of variables declaration//GEN-END:variables
}
