/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import main.Dao;
import org.apache.log4j.Logger;

/**
 *
 * @author nishant
 */
public class ClosingStock extends javax.swing.JFrame {
    private static final Logger logger=Logger.getLogger(ClosingStock.class);
    /**
     * Creates new form ClosingStock
     */
    public ClosingStock() {
        initComponents();
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jTable1.setVisible(false);
        jScrollPane1.setVisible(false);
        try {
            this.setIconImage( new javax.swing.ImageIcon(getClass().getResource("m_logo.png")).getImage());
        }
        catch (Exception exc) {
            logger.error("ERROR in setting icon",exc);
        }
    }
     public ClosingStock(ArrayList<String> fosList){
        this();
        if(String.valueOf(jComboBox2.getSelectedItem()).equalsIgnoreCase("FOS")){
            if(fosList.isEmpty() || (fosList.size()==1 && fosList.get(0).equalsIgnoreCase("-1"))) {
                jComboBox2.setSelectedItem("Date");
            }
            else {
                for(String fos:fosList){
                jComboBox1.addItem(fos);
                }
            }
        }
        
        Date d = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        jXDatePicker1.setDate(d);
        jXDatePicker1.setFormats(dateFormat);
        jXDatePicker2.setDate(d);
        jXDatePicker2.setFormats(dateFormat);
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jLabel4 = new javax.swing.JLabel();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Closing Stock");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/small_logo.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Closing Stock");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("From");

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("To");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FOS", "Date" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton1.setText("Show");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(336, 336, 336))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1303;
        gridBagConstraints.ipady = 503;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
       String comboValue = String.valueOf(jComboBox2.getSelectedItem());
        if(comboValue.equalsIgnoreCase("Date")){
            jComboBox1.setVisible(false);    
        }
        else if(comboValue.equalsIgnoreCase("FOS")){
            if(jComboBox1.getItemCount()==0){
                JOptionPane.showMessageDialog(null,"There are no FOS",this.getTitle(),JOptionPane.PLAIN_MESSAGE);
                jComboBox2.setSelectedItem("Date");
            }
            else {
                jComboBox1.setVisible(true);
            }
            
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed
       Date d1 = jXDatePicker1.getDate();
        Date d2 = new Date();
        long diff = (d2.getTime() - d1.getTime())/1000/60/60/24;
        if(diff > 60){
            JOptionPane.showMessageDialog(null,"You can't select date older than 60 days",this.getTitle(),JOptionPane.PLAIN_MESSAGE);
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            jXDatePicker1.setDate(new Date());
            jXDatePicker1.setFormats(format);
            return;
        }
    }//GEN-LAST:event_jXDatePicker1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       String comboValue = String.valueOf(jComboBox2.getSelectedItem());
        Dao d = new Dao();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };
        if(jXDatePicker1.getDate()==null || jXDatePicker2.getDate() == null){
            JOptionPane.showMessageDialog(null,"Please enter valid date ",this.getTitle(),JOptionPane.PLAIN_MESSAGE);
            return;
        }
        long fromDate = Long.parseLong(dateFormat.format(jXDatePicker1.getDate()));
        long toDate = Long.parseLong(dateFormat.format(jXDatePicker2.getDate()));
        if(fromDate>toDate){
            JOptionPane.showMessageDialog(null,"From-Date cannot be greater than To-Date ",this.getTitle(),JOptionPane.PLAIN_MESSAGE);
            return;
        }
        ArrayList<String> rowList = null;
        if(comboValue.equalsIgnoreCase("Date")){
            rowList = d.reportOnlyDateClosing(fromDate, toDate,"Closing_Stock");
        }
        else if(comboValue.equalsIgnoreCase("FOS")){
            rowList = d.reportDateFosClosing(fromDate,toDate,String.valueOf(jComboBox1.getSelectedItem()),"Closing_Stock");
        }
            
        if(rowList == null){
            JOptionPane.showMessageDialog(null,"No Closing Stock",this.getTitle(),JOptionPane.PLAIN_MESSAGE);
            return;
        }
        if(rowList!=null && rowList.size()==1){
            JOptionPane.showMessageDialog(null,"No Closing Stock between this period",this.getTitle(),JOptionPane.PLAIN_MESSAGE);
            return;
        }
        String columns[]=rowList.get(0).split(",");
        model.setColumnIdentifiers(columns);
        String parts[]=null;
        int totalVouchers [] = new int[columns.length];
        for(int i=1;i<rowList.size();i++){
            parts=rowList.get(i).split(",");
            if(comboValue.equalsIgnoreCase("FOS")){
                parts[0]=parts[0].substring(6, 8)+"-"+parts[0].substring(4,6)+"-"+parts[0].substring(0, 4);
            }
            for(int p=1;p<parts.length;p++){
                totalVouchers[p] = totalVouchers[p]+Integer.parseInt(parts[p]);
            }
            model.addRow(parts);
        }
        if(rowList.size()>2){
            String parts1[] = new String[totalVouchers.length];
            parts1[0] = "Total";
            for(int j=1;j<=totalVouchers.length-1;j++){
                parts1[j] = String.valueOf(totalVouchers[j]);
            }
        model.addRow(parts1);
        }
        jTable1.setModel(model);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.setFillsViewportHeight(true);
        jTable1.setRowHeight(25);
        jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumn(0).setMinWidth(150);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount()-1).setMinWidth(100);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount()-2).setMinWidth(100);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount()-3).setMinWidth(100);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount()-4).setMinWidth(100);
        if(jTable1.getColumnCount()>4){
            jTable1.getColumnModel().getColumn(jTable1.getColumnCount()-5).setMinWidth(100);
        }
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer)jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        int width=0;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth=screenSize.width-150;
        for(int i=0;i<jTable1.getColumnCount();i++){
            jTable1.getColumnModel().getColumn(i).setCellRenderer(dtcr);
            width = width + jTable1.getColumnModel().getColumn(i).getWidth();
        }
        int height = jTable1.getRowHeight()*(jTable1.getRowCount()+1);
        if( width > maxWidth && height > 250){
            jScrollPane1.setPreferredSize(new Dimension(maxWidth,250));
        }
        else if(width > maxWidth){
            jScrollPane1.setPreferredSize(new Dimension(maxWidth,height+18));
        }
        else if(height > 250){
            jScrollPane1.setPreferredSize(new Dimension(width+21,250));
        }
        else{
            jScrollPane1.setPreferredSize(new Dimension(width+6, height+3));
        }
        int maxHeight = screenSize.height - 100;
        if(jPanel2.getWidth()>= maxWidth && jPanel2.getHeight()>=maxHeight){
            jPanel2.setSize(maxWidth, maxHeight);
        }
        else if(jPanel2.getWidth()>= maxWidth){
            jPanel2.setSize(maxWidth, jPanel2.getHeight());
        }
        else if(jPanel2.getHeight()>= maxHeight){
            jPanel2.setSize(jPanel2.getWidth(), maxHeight);
        }
        jScrollPane1.setMinimumSize(new Dimension(maxWidth-500, maxHeight-500));
        jPanel2.add(jScrollPane1);
        jTable1.setVisible(true);
        jScrollPane1.setVisible(true);
        this.revalidate();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ClosingStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClosingStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClosingStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClosingStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClosingStock().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    // End of variables declaration//GEN-END:variables
}
