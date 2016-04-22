/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import bean.PrimaryStockBean;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import main.Dao;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.table.NumberEditorExt;

/**
 *
 * @author nishant
 */
public class ReturnStock extends javax.swing.JFrame {

    private static final Logger logger=Logger.getLogger(ReturnStock.class);
    /**
     * Creates new form ReturnStock
     */
    public ReturnStock() {
        initComponents();
        try {
            this.setIconImage( new javax.swing.ImageIcon(getClass().getResource("m_logo.png")).getImage());
        }
        catch (Exception exc) {
            logger.error("ERROR in setting icon",exc);
        }
    }
    
    public void stockReturn(ArrayList<String> fosList, final ArrayList<PrimaryStockBean> psbList, ArrayList<String> stockAssignedList){
        
         final HashMap<String,String> h = new HashMap();
            String[] vouchers=stockAssignedList.get(0).split(",");
            String[] parts=null;
            for(int i=1;i<stockAssignedList.size();i++){
               parts=stockAssignedList.get(i).split(",");
               for(int j=1;j<vouchers.length;j++){
                   h.put(parts[0]+vouchers[j], parts[j]);
               }            
            }
         DefaultTableModel model = new DefaultTableModel(){
         @Override
         public void setValueAt(Object value, int row, int col) {
             try{
                 if(value!=null && value.equals("Available Primary Stock")){
                     super.setValueAt(value, row, col);
                     return;
                 }
                long sumValue=0;
             if(value!=null){
                 sumValue=((Long)value).longValue();
             }
             String key1=(String)jTable1.getModel().getValueAt(row, 0);
             String key2=(String)jTable1.getModel().getColumnName(col);
              long stockInHand=0;
             if(h.get(key1+key2)!=null){
                  stockInHand=Long.parseLong(h.get(key1+key2));
             }
             
             if(stockInHand<sumValue){
                 JOptionPane.showMessageDialog(null,"Cannot return..Insufficient Assigned Stock","Stock Assignment",JOptionPane.PLAIN_MESSAGE);
                 return;
             }
             if(sumValue < 0){
                 JOptionPane.showMessageDialog(null,"Negative Values cannot be returned","Stock Assignment",JOptionPane.PLAIN_MESSAGE);
                 return;
             }
             for(int i=0;i<getRowCount()-1;i++){
                 if(i==row){
                     continue;
                 }
                 if((Long)jTable1.getModel().getValueAt(i, col)!=null){
                     sumValue = sumValue + ((Long)jTable1.getModel().getValueAt(i, col)).longValue();
                 }
                 else {
                     sumValue=sumValue + 0;
                 }
                 
             }
             long primaryValue=(long)(psbList.get(col-1).getQuantity());
//             if(sumValue>primaryValue){
//                 JOptionPane.showMessageDialog(null,"Cannot assign...Insufficient Primary Stock","Stock Assignment",JOptionPane.PLAIN_MESSAGE);
//             }
             
                long updatedPrimaryValue = primaryValue+ sumValue;
                super.setValueAt((Object)(updatedPrimaryValue),jTable1.getRowCount()-1, col);
                super.setValueAt(value, row, col);
                long totalAmount=0;
                long totalPrimaryAmount=0;
                for(int i=1;i<=jTable1.getColumnCount()-2;i++){
                    if((Long)jTable1.getModel().getValueAt(row, i)!=null){
                        if(jTable1.getColumnName(i).equalsIgnoreCase("Virtual_Topup")){
                            totalAmount = totalAmount + ((Long)jTable1.getModel().getValueAt(row, i)).longValue();
                        } else {
                            totalAmount = totalAmount + ((Long)jTable1.getModel().getValueAt(row, i)).longValue() * Long.parseLong(jTable1.getColumnName(i));
                        }
                        
                    }
                    else {
                        totalAmount = totalAmount + 0;
                    }
                    
                    //Calculating total amount of primary stock after assignment
                     if(jTable1.getColumnName(i).equalsIgnoreCase("Virtual_Topup")){
                            totalPrimaryAmount = totalPrimaryAmount + ((Long)jTable1.getModel().getValueAt(jTable1.getRowCount()-1, i)).longValue();
                     } else {
                            totalPrimaryAmount = totalPrimaryAmount + ((Long)jTable1.getModel().getValueAt(jTable1.getRowCount()-1, i)).longValue() * Long.parseLong(jTable1.getColumnName(i));
                     } 
                }
                super.setValueAt(totalAmount, row, jTable1.getColumnCount()-1);
                super.setValueAt(totalPrimaryAmount, jTable1.getRowCount()-1, jTable1.getColumnCount()-1);
             
            } catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Invalid Entry","Stock Assignment",JOptionPane.PLAIN_MESSAGE);
            }
             
             
         }

            @Override
            public boolean isCellEditable(int row, int column) {
                if(row == jTable1.getRowCount()-1 || column == 0 || column == jTable1.getColumnCount()-1){
                    return false;
                }
                else {
                    return super.isCellEditable(row, column);
                }
                
            }
         
         
         };
         
         ArrayList<String> column = new ArrayList();
         column.add("FOS");
         for(PrimaryStockBean psb:psbList){
             column.add(psb.getVoucher());
         }
         column.add("Total_Amount");
         model.setColumnIdentifiers((String[])column.toArray(new String[column.size()]));
         String[] row=new String[column.size()];
         Long[] primaryRow=new Long[column.size()];
         long totalPrimaryValue=0;
         for(int i=0;i<fosList.size();i++){
             
             row[0]=fosList.get(i);
             model.addRow(row);
         }
       
          for(int i=1;i<=psbList.size();i++){
             primaryRow[i]=(long)(psbList.get(i-1).getQuantity());
         }   
          for(int i=1;i<=column.size()-2;i++){
              if(psbList.get(i-1).getVoucher().equalsIgnoreCase("Virtual_Topup")){
                            totalPrimaryValue = totalPrimaryValue + (long)(psbList.get(i-1).getQuantity());
                     } else {
                            
                            totalPrimaryValue = totalPrimaryValue + (long)(psbList.get(i-1).getQuantity()) * Long.parseLong(psbList.get(i-1).getVoucher());
                     } 
                }
          primaryRow[column.size()-1]=totalPrimaryValue;
          model.addRow(primaryRow);
         
          
            jTable1.setModel(model);
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable1.setFillsViewportHeight(true);
            jTable1.setRowHeight(25);
            jTable1.setShowGrid(true);
            jTable1.getColumnModel().getColumn(0).setMinWidth(150);
            jTable1.getColumnModel().getColumn(jTable1.getColumnCount()-1).setMinWidth(100);
            jTable1.getColumnModel().getColumn(jTable1.getColumnCount()-2).setMinWidth(100);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            ((DefaultTableCellRenderer)jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int maxWidth=screenSize.width-150;
            int width = 0;
            for(int i=0;i<jTable1.getColumnCount();i++){
               jTable1.getColumnModel().getColumn(i).setCellRenderer(dtcr);
               width = width + jTable1.getColumnModel().getColumn(i).getWidth();
               jTable1.getColumnModel().getColumn(i).setCellEditor(new NumberEditorExt(NumberFormat.getInstance(),true));
           }
            jTable1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
           //jTable1.getTableHeader().setFont(new Font("sansserif",Font.BOLD,12));
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            jXDatePicker1.setDate(new Date());
            jXDatePicker1.setFormats(dateFormat);
            jTable1.getModel().setValueAt("Available Primary Stock",jTable1.getRowCount()-1 , 0);
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
           if(jPanel1.getWidth()>= maxWidth && jPanel1.getHeight()>=maxHeight){
               jPanel1.setSize(maxWidth, maxHeight);
           }
           else if(jPanel1.getWidth()>= maxWidth){
               jPanel1.setSize(maxWidth, jPanel1.getHeight());
           }
           else if(jPanel1.getHeight()>= maxHeight){
               jPanel1.setSize(jPanel1.getWidth(), maxHeight);
           }
           jScrollPane1.setMinimumSize(new Dimension(maxWidth-500, maxHeight-500));
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Return Stock");
        setLocationByPlatform(true);
        setPreferredSize(new java.awt.Dimension(1366, 768));

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FOS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1313;
        gridBagConstraints.ipady = 786;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 11, 10);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jButton1.setText("Return Stock");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Return Date");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Return Stock");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/small_logo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(638, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 956, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(105, 105, 105))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed
        Date d1 = jXDatePicker1.getDate();
        Date d2 = new Date();
        long diff = (d2.getTime() - d1.getTime())/1000/60/60/24;
        if(diff > 60){
            JOptionPane.showMessageDialog(null,"You can't select date older than 60 days","Return Stock",JOptionPane.PLAIN_MESSAGE);
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            jXDatePicker1.setDate(new Date());
            jXDatePicker1.setFormats(format);
            return;
        }
    }//GEN-LAST:event_jXDatePicker1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Dao dao=new Dao();
        ArrayList<String> vouchers=new ArrayList<>();
        ArrayList<String> rowData=new ArrayList<>();
        for(int header=1;header<=jTable1.getColumnCount()-1;header++){
            vouchers.add(jTable1.getModel().getColumnName(header));
        }

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

        String return_date=formater.format(d);
        dao.returnStock(vouchers,rowData,return_date);
        ArrayList<PrimaryStockBean> psbList=dao.primaryStockList();
        ArrayList<String> fosList=dao.fosList();
        ArrayList<String> stockAssignedList = dao.stockInhand();
        this.stockReturn(fosList, psbList,stockAssignedList);
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
            java.util.logging.Logger.getLogger(ReturnStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnStock().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    // End of variables declaration//GEN-END:variables
}
