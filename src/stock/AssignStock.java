/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import bean.PrimaryStockBean;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import main.Dao;
import static main.Home.logger;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.NumberEditorExt;

/**
 *
 * @author nishant
 */
public class AssignStock extends javax.swing.JFrame {

    private static final Logger logger=Logger.getLogger(AssignStock.class);
    /**
     * Creates new form AssignStock
     */
    public AssignStock() {
        initComponents();
        try {
            this.setIconImage( new javax.swing.ImageIcon(getClass().getResource("m_logo.png")).getImage());
        }
        catch (Exception exc) {
            logger.error("ERROR in setting icon",exc);
        }
    }
    public void stockAssign(ArrayList<String> fosList, final ArrayList<PrimaryStockBean> psbList){
         stockInHand();
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
             if(sumValue < 0){
                 JOptionPane.showMessageDialog(null,"Negative Values cannot be assigned","Stock Assignment",JOptionPane.PLAIN_MESSAGE);
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
             if(sumValue>primaryValue){
                 JOptionPane.showMessageDialog(null,"Cannot assign...Insufficient Primary Stock","Stock Assignment",JOptionPane.PLAIN_MESSAGE);
             }
             else {
                long updatedPrimaryValue = primaryValue- sumValue;
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
             }
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
         
            logger.error("Assign Stock");
          
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
            int width=0;
            for(int i=0;i<jTable1.getColumnCount();i++){
               jTable1.getColumnModel().getColumn(i).setCellRenderer(dtcr);
               jTable1.getColumnModel().getColumn(i).setCellEditor(new NumberEditorExt(NumberFormat.getInstance(),true));
               width = width + jTable1.getColumnModel().getColumn(i).getWidth();
            }
            jTable1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
           //jTable1.getTableHeader().setFont(new Font("sansserif",Font.BOLD,12));
           DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            jXDatePicker1.setDate(new Date());
            jXDatePicker1.setFormats(dateFormat);
            jTable1.getModel().setValueAt("Available Primary Stock",jTable1.getRowCount()-1 , 0);
            int height = jTable1.getRowHeight()*(jTable1.getRowCount()+1);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int maxWidth=screenSize.width-150;
           
           if( width > maxWidth && height > 200){
               jScrollPane1.setPreferredSize(new Dimension(maxWidth,200));
           }
           else if(width > maxWidth){
                jScrollPane1.setPreferredSize(new Dimension(maxWidth,height+18));
            }
           else if(height > 200){
                jScrollPane1.setPreferredSize(new Dimension(width+21,200));
            }
            else{
                jScrollPane1.setPreferredSize(new Dimension(width+6, height+3));
           }
            int maxHeight = screenSize.height - 300;
           if(jPanel1.getWidth()>= maxWidth && jPanel1.getHeight()>=maxHeight){
               jPanel1.setSize(maxWidth, maxHeight);
           }
           else if(jPanel1.getWidth()>= maxWidth){
               jPanel1.setSize(maxWidth, jPanel1.getHeight());
           }
           else if(jPanel1.getHeight()>= maxHeight){
               jPanel1.setSize(jPanel1.getWidth(), maxHeight);
           }
           jScrollPane1.setMinimumSize(new Dimension(maxWidth-500, maxHeight-700));
           jPanel1.add(jScrollPane1);
           this.revalidate();
    }
    private void stockInHand(){
        Dao dao=new Dao();
        ArrayList<String> rowList=dao.stockInhand();
        if(rowList==null){
            jPanel2.setVisible(false);
            jLabel3.setVisible(false);
        }
        else{
            DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }};
            String columns[]=rowList.get(0).split(",");
            model.setColumnIdentifiers(columns);
            
            for(int i=1;i<rowList.size();i++){
                 String parts[]=rowList.get(i).split(",");
                 model.addRow(parts);
            }
       
            jTable2.setModel(model);
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable2.setFillsViewportHeight(true);
            jTable2.setRowHeight(22);
            jTable2.setShowGrid(true);
            jTable2.getColumnModel().getColumn(0).setMinWidth(90);
            jTable2.getColumnModel().getColumn(jTable2.getColumnCount()-1).setMinWidth(100);
            jTable2.getColumnModel().getColumn(jTable2.getColumnCount()-2).setMinWidth(100);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            ((DefaultTableCellRenderer)jTable2.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
             int width=0;
            for(int i=0;i<jTable2.getColumnCount();i++){
               jTable2.getColumnModel().getColumn(i).setCellRenderer(dtcr);
               width = width + jTable2.getColumnModel().getColumn(i).getWidth();
           }
            int height = jTable2.getRowHeight()*(jTable2.getRowCount()+1);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int maxWidth=screenSize.width-150;
           if( width > maxWidth && height > 150){
               jScrollPane2.setPreferredSize(new Dimension(maxWidth,150));
           }
           else if(width > maxWidth){
                jScrollPane2.setPreferredSize(new Dimension(maxWidth,height+21));
            }
           else if(height > 150){
                jScrollPane2.setPreferredSize(new Dimension(width+21,150));
            }
            else{
                jScrollPane2.setPreferredSize(new Dimension(width+6, height+6));
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
          
          //jTable1.setMinimumSize(new Dimension(maxWidth, maxHeight));
          jScrollPane2.setMinimumSize(new Dimension(maxWidth-500, maxHeight-500));
           jPanel2.add(jScrollPane2);
          
          this.revalidate();
        }
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
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Assign Stock");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fos"
            }
        ));
        jTable1.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1303;
        gridBagConstraints.ipady = 539;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        jPanel1.add(jScrollPane1, gridBagConstraints);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jButton1.setText("Assign Stock");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Assignment Date:");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Assign Stock");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/small_logo.png"))); // NOI18N

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTable2.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(jTable2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1313;
        gridBagConstraints.ipady = 227;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        jPanel2.add(jScrollPane2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Stock In Hand");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(189, 189, 189))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1346, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed

    }//GEN-LAST:event_jButton1KeyPressed

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

        String assignment_date=formater.format(d);
        dao.assignStock(vouchers,rowData,assignment_date);
        ArrayList<PrimaryStockBean> psbList=dao.primaryStockList();
        ArrayList<String> fosList=dao.fosList();
        this.stockAssign(fosList, psbList);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed
        Date d1 = jXDatePicker1.getDate();
        Date d2 = new Date();
        long diff = (d2.getTime() - d1.getTime())/1000/60/60/24;
        if(diff > 60){
            JOptionPane.showMessageDialog(null,"You can't select date older than 60 days","Assign Stock",JOptionPane.PLAIN_MESSAGE);
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            jXDatePicker1.setDate(new Date());
            jXDatePicker1.setFormats(format);
            return;
        }
    }//GEN-LAST:event_jXDatePicker1ActionPerformed

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
            java.util.logging.Logger.getLogger(AssignStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AssignStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AssignStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AssignStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AssignStock().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    // End of variables declaration//GEN-END:variables
}
