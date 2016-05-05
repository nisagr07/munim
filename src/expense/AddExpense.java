/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expense;

import java.awt.Dimension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import main.Dao;
import main.MyCellEditor;
import org.apache.log4j.Logger;

/**
 *
 * @author nishant
 */
public class AddExpense extends javax.swing.JFrame {
    private static final Logger logger=Logger.getLogger(AddExpense.class);
    /**
     * Creates new form AddExpense
     */
    public AddExpense() {
        initComponents();
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        try {
            this.setIconImage( new javax.swing.ImageIcon(getClass().getResource("m_logo.png")).getImage());
        }
        catch (Exception exc) {
            logger.error("Error Code[AE-ICON]",exc);
        }
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        jXDatePicker1.setDate(new Date());
        jXDatePicker1.setFormats(format);
    }
    public void expenseTable(ArrayList<String> fosList){
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0 || column==jTable1.getColumn("Total").getModelIndex()){
                    return false;
                }
                else {
                    return true;
                }
            }
            @Override
             public void setValueAt(Object value, int row, int col) {
                long sumValue=0;   
                if(value!=null){
                    try{
                        sumValue=Long.parseLong(String.valueOf(value));
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Please enter proper value","Add Expense",JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    
                }
                if(sumValue < 0){
                    JOptionPane.showMessageDialog(null,"Negative values not allowed","Add Expense",JOptionPane.PLAIN_MESSAGE);
                    logger.error("Negative values not allowed");
                    return;
                }
                for(int i=1;i<=getColumnCount()-2;i++){
                    if(i==col){
                        continue;
                    }
                    if(jTable1.getModel().getValueAt(row, i)!=null){
                        sumValue = sumValue + Long.parseLong(String.valueOf(jTable1.getModel().getValueAt(row, i)));
                    }
                    else {
                        sumValue=sumValue + 0;
                    }
                }
                super.setValueAt(sumValue, row, jTable1.getColumn("Total").getModelIndex());
                super.setValueAt(value, row, col);
             }
        };
        String [] column = {"FOS","Salary","Petrol","Other","Total"};
        model.setColumnIdentifiers(column);
        String rows[]=new String[column.length];
        for(int i=0;i<fosList.size();i++){
             rows[0]=fosList.get(i); 
              model.addRow(rows);
        }
        jTable1.setModel(model);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.setFillsViewportHeight(true);
        jTable1.setRowHeight(25);
        jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumn(0).setMinWidth(90);
        jTable1.getColumnModel().getColumn(1).setMinWidth(90);
        jTable1.getColumnModel().getColumn(2).setMinWidth(90);
        jTable1.getColumnModel().getColumn(3).setMinWidth(90);
        jTable1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer)jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<jTable1.getColumnCount();i++){
               jTable1.getColumnModel().getColumn(i).setCellRenderer(dtcr);
               jTable1.getColumnModel().getColumn(i).setCellEditor(new MyCellEditor());
        }
        int height = jTable1.getRowHeight()*(jTable1.getRowCount()+1);
        if(height>180){
            jScrollPane1.setPreferredSize(new Dimension(435+21, 168));
        } else{
            jScrollPane1.setPreferredSize(new Dimension(435+6, height+3));
        }
           
        jPanel1.add(jScrollPane1);
        this.revalidate();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Expense");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/small_logo.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Add Expense");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 402;
        gridBagConstraints.ipady = 182;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(jLabel2)))
                .addGap(0, 25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jButton1)
                .addGap(182, 182, 182))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        Dao dao=new Dao();
        ArrayList<String> rowData=new ArrayList<>();
        for(int row=0;row<=jTable1.getRowCount()-1;row++) {
            StringBuilder sb=new StringBuilder();
            for(int column=0;column<=jTable1.getColumnCount()-1;column++){
                if(jTable1.getModel().getValueAt(row,column)!=null){
                    sb.append(jTable1.getModel().getValueAt(row,column)+",");
                }
                else {
                    sb.append(0+",");
                }
            }
            sb.deleteCharAt(sb.length()-1);
            rowData.add(sb.toString());
        }
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        Date d=new Date();
        if(jXDatePicker1.getDate()!=null){
            d=jXDatePicker1.getDate();
        }
        String expense_date=formater.format(d);
        if(dao.addExpense(rowData,expense_date)){
            JOptionPane.showMessageDialog(null,"Expenses successfully saved",this.getTitle(),JOptionPane.PLAIN_MESSAGE);
            ArrayList<String> fosList=dao.fosList();
            this.expenseTable(fosList);
        }
        else {
            JOptionPane.showMessageDialog(null,"Problem in saving expenses",this.getTitle(),JOptionPane.PLAIN_MESSAGE);
            logger.error("Problem in saving expenses");
        }
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
            java.util.logging.Logger.getLogger(AddExpense.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddExpense.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddExpense.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddExpense.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddExpense().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    // End of variables declaration//GEN-END:variables
}
