/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.table.NumberEditorExt;

/**
 *
 * @author nishant
 */
public class DailyHisab extends javax.swing.JFrame {
    
    private static final Logger logger=Logger.getLogger(DailyHisab.class);
    /**
     * Creates new form DailyHisab
     */
    public DailyHisab() {
        initComponents();
        try {
            this.setIconImage( new javax.swing.ImageIcon(getClass().getResource("m_logo.png")).getImage());
        }
        catch (Exception exc) {
            logger.error("ERROR [DH-ICON]",exc);
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       
        jLabel1.setFont(new Font("Serif", Font.PLAIN, 20));
        jLabel2.setFont(new Font("Serif", Font.PLAIN, 20));
    }
     public void openingStock(ArrayList<String> rowList){

       DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
          
            };
            
            String columns[]=rowList.get(0).split(",");
            model.setColumnIdentifiers(columns);
            
             for(int i=1;i<rowList.size();i++){
                 String parts[]=rowList.get(i).split(",");
                 model.addRow(parts);
             }
       
            jTable1.setModel(model);
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable1.setFillsViewportHeight(true);
            jTable1.setRowHeight(22);
            jTable1.setShowGrid(true);
            jTable1.getColumnModel().getColumn(0).setMinWidth(90);
            jTable1.getColumnModel().getColumn(jTable1.getColumnCount()-1).setMinWidth(100);
            jTable1.getColumnModel().getColumn(jTable1.getColumnCount()-2).setMinWidth(100);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            ((DefaultTableCellRenderer)jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
             int width=0;
            for(int i=0;i<jTable1.getColumnCount();i++){
               jTable1.getColumnModel().getColumn(i).setCellRenderer(dtcr);
               width = width + jTable1.getColumnModel().getColumn(i).getWidth();
              // jTable1.getColumnModel().getColumn(i).setCellEditor(new NumberEditorExt(NumberFormat.getInstance(),true));
           }
            int height = jTable1.getRowHeight()*(jTable1.getRowCount()+1);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int maxWidth=screenSize.width-150;
           if( width > maxWidth && height > 150){
               jScrollPane1.setPreferredSize(new Dimension(maxWidth,150));
           }
           else if(width > maxWidth){
                jScrollPane1.setPreferredSize(new Dimension(maxWidth,height+21));
            }
           else if(height > 150){
                jScrollPane1.setPreferredSize(new Dimension(width+21,150));
            }
            else{
                jScrollPane1.setPreferredSize(new Dimension(width+6, height+6));
           }
           int maxHeight = screenSize.height - 300;
           if(jPanel3.getWidth()>= maxWidth && jPanel3.getHeight()>=maxHeight){
               jPanel3.setSize(maxWidth, maxHeight);
           }
           else if(jPanel3.getWidth()>= maxWidth){
               jPanel3.setSize(maxWidth, jPanel3.getHeight());
           }
           else if(jPanel3.getHeight()>= maxHeight){
               jPanel3.setSize(jPanel3.getWidth(), maxHeight);
           }
          
          //jTable1.setMinimumSize(new Dimension(maxWidth, maxHeight));
          jScrollPane1.setMinimumSize(new Dimension(maxWidth-400, maxHeight-300));
           jPanel3.add(jScrollPane1);
          
          this.revalidate();
            //Preparing closing table
           
            DefaultTableModel model1 = new DefaultTableModel(){        
                                
                
                @Override
             public void setValueAt(Object value, int row, int col) {
             try{
                 
             long enteredValue=0;
             if(value!=null){
                 enteredValue=((Long)value);
             }
            
             if(enteredValue < 0){
                 JOptionPane.showMessageDialog(null,"Negative values are not allowed","Daily Hisab",JOptionPane.PLAIN_MESSAGE);
                 return;
             }
             if(jTable2.getColumn("Cash").getModelIndex()!=col){
                 long openingStock=Long.parseLong((String)(jTable1.getModel().getValueAt(row, col)));
                if(enteredValue>openingStock){
                 JOptionPane.showMessageDialog(null,"Closing stock can not be greater than opening stock","Daily Hisab",JOptionPane.PLAIN_MESSAGE);
                 return;
                }
             }
                super.setValueAt(value, row, col);
                long totalAmount=0;
                for(int i=1;i<=jTable2.getColumnCount()-5;i++){
                    if((Long)jTable2.getModel().getValueAt(row, i)!=null){
                        if(jTable2.getColumnName(i).equalsIgnoreCase("Virtual_Topup")){
                            totalAmount = totalAmount + ((Long)jTable2.getModel().getValueAt(row, i)).longValue();
                        } else {
                            totalAmount = totalAmount + ((Long)jTable2.getModel().getValueAt(row, i)).longValue() * Long.parseLong(jTable2.getColumnName(i));
                        }
                        
                    }
                    else {
                        totalAmount = totalAmount + 0;
                    }
                }
                super.setValueAt(totalAmount, row, jTable2.getColumnCount()-4);
                
                long totalAmountOpening = Long.parseLong((String)jTable1.getModel().getValueAt(row, jTable1.getColumn("Total_Amount").getModelIndex()));
                long totalAmountClosing = (Long)jTable2.getModel().getValueAt(row, jTable2.getColumn("Total_Amount").getModelIndex());
                long totalSold = totalAmountOpening - totalAmountClosing;
                super.setValueAt(totalSold, row, jTable2.getColumn("Total_Sold").getModelIndex());
                long cash = 0;
                if((Long)jTable2.getModel().getValueAt(row, jTable2.getColumn("Cash").getModelIndex())!=null){
                     cash = (Long)jTable2.getModel().getValueAt(row, jTable2.getColumn("Cash").getModelIndex());
                }
                double margin=(100+Double.parseDouble(jTextField1.getText()))/100;
                double difference=totalSold-(cash*margin);
                super.setValueAt(difference, row, jTable2.getColumn("Difference").getModelIndex());
                Long l=Long.valueOf(0);
                for(int i=1;i<jTable2.getColumnCount();i++){
                    if(jTable2.getModel().getValueAt(row, i)==null){
                        super.setValueAt(l, row, i);
                    }
                }
             
            } catch(Exception e){
                logger.error("ERROR [DH-Invalid Entry]",e);
                JOptionPane.showMessageDialog(null,"Invalid Entry","Daily Calculation",JOptionPane.PLAIN_MESSAGE);
            }
             
             
         }

            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 0 || column == jTable2.getColumnCount()-4 || column == jTable2.getColumnCount()-1 || column == jTable2.getColumnCount()-2){
                    return false;
                }
                else {
                    return super.isCellEditable(row, column);
                }
                
            }
            };
            String col=rowList.get(0)+",Cash,Total_Sold,Difference";
            String columns1[]=col.split(",");
            model1.setColumnIdentifiers(columns1);
            String rows[]=new String[columns1.length];
            for(int i=1;i<rowList.size();i++){
                 String parts[]=rowList.get(i).split(",");
                 rows[0]=parts[0]; 
                  model1.addRow(rows);
             }

            jTable2.setModel(model1);
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable2.setFillsViewportHeight(true);
            jTable2.setRowHeight(22);
            jTable2.setShowGrid(true);
            jTable2.getColumnModel().getColumn(0).setMinWidth(90);
            jTable2.getColumnModel().getColumn(jTable2.getColumnCount()-1).setMinWidth(100);
            jTable2.getColumnModel().getColumn(jTable2.getColumnCount()-2).setMinWidth(100);
            jTable2.getColumnModel().getColumn(jTable2.getColumnCount()-3).setMinWidth(100);
            jTable2.getColumnModel().getColumn(jTable2.getColumnCount()-4).setMinWidth(100);
            jTable2.getColumnModel().getColumn(jTable2.getColumnCount()-5).setMinWidth(100);
            DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
            dtcr1.setHorizontalAlignment(SwingConstants.CENTER);
            ((DefaultTableCellRenderer)jTable2.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            int width2=0;
            for(int i=0;i<jTable2.getColumnCount();i++){
               jTable2.getColumnModel().getColumn(i).setCellRenderer(dtcr);
               jTable2.getColumnModel().getColumn(i).setCellEditor(new NumberEditorExt(NumberFormat.getInstance(),true));
               width2 = width2 + jTable2.getColumnModel().getColumn(i).getWidth();
            }
            jTable2.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            jXDatePicker1.setDate(new Date());
            jXDatePicker1.setFormats(dateFormat);
            int height2 = jTable2.getRowHeight()*(jTable2.getRowCount()+1);
            if( width2 > maxWidth && height2 > 200){
               jScrollPane2.setPreferredSize(new Dimension(maxWidth,200));
            }
            else if(width2 > maxWidth){
                jScrollPane2.setPreferredSize(new Dimension(maxWidth,height2+21));
            }
            else if(height2 > 200){
                jScrollPane2.setPreferredSize(new Dimension(width2+21,200));
            }
            else{
                jScrollPane2.setPreferredSize(new Dimension(width2+6, height2+6));
            }
           if(jPanel1.getWidth()>= maxWidth && jPanel1.getHeight()>=maxHeight){
               jPanel1.setSize(maxWidth, maxHeight);
           }
           else if(jPanel1.getWidth()>= maxWidth){
               jPanel1.setSize(maxWidth, jPanel1.getHeight());
           }
           else if(jPanel1.getHeight()>= maxHeight){
               jPanel1.setSize(jPanel1.getWidth(), maxHeight);
           }
          // jTable2.setMinimumSize(new Dimension(maxWidth, maxHeight));
           jScrollPane2.setMinimumSize(new Dimension(maxWidth-400, maxHeight-300));
            jPanel1.add(jScrollPane2);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Daily Calculation");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTable2.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1083;
        gridBagConstraints.ipady = 199;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        jPanel1.add(jScrollPane2, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Margin");

        jTextField1.setText("2.5");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTextField1PropertyChange(evt);
            }
        });

        jLabel4.setText("%");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Closing Stock");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Date");

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        jLabel5.setText("Save");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addGap(57, 57, 57))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Opening Stock");

        jPanel3.setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.ipadx = 1083;
        gridBagConstraints.ipady = 257;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 11, 10);
        jPanel3.add(jScrollPane1, gridBagConstraints);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/small_logo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(29, 29, 29)))
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked

        Dao dao = new Dao();
        ArrayList<String> vouchers=new ArrayList<>();
        ArrayList<String> rowData=new ArrayList<>();
        ArrayList<String> soldData=new ArrayList<>();
        for(int header=1;header<=jTable2.getColumnCount()-1;header++){
            vouchers.add(jTable2.getModel().getColumnName(header));
        }

        for(int row=0;row<=jTable2.getRowCount()-1;row++) {
            StringBuilder sb=new StringBuilder();
            StringBuilder sb1=new StringBuilder();
            for(int column=0;column<=jTable2.getColumnCount()-1;column++){

                if(jTable2.getModel().getValueAt(row,column)!=null){
                    sb.append(jTable2.getModel().getValueAt(row,column)+",");
                }
                else {
                    sb.append("na,");
                }
            }
            for(int column1=0;column1<=jTable1.getColumnCount()-1;column1++){

                if(jTable2.getModel().getValueAt(row,column1)!=null){
                    if(column1==0){
                        sb1.append(jTable2.getModel().getValueAt(row,column1)+",");
                    }
                    else {
                        sb1.append(Long.parseLong((String)jTable1.getModel().getValueAt(row,column1))-(Long)jTable2.getModel().getValueAt(row,column1)+",");
                    }

                }
                else {
                    sb1.append("na,");
                }
            }
            sb.deleteCharAt(sb.length()-1);
            sb1.deleteCharAt(sb1.length()-1);
            rowData.add(sb.toString());
            soldData.add(sb1.toString());
        }

        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        Date d=new Date();
        if(jXDatePicker1.getDate()!=null){
            d=jXDatePicker1.getDate();
        }
        else{
             JOptionPane.showMessageDialog(null,"Please enter valid date","Daily Hisab",JOptionPane.PLAIN_MESSAGE);
             return;
        }

        long assignment_date=Long.parseLong(formater.format(d));

        dao.dailyHisab(vouchers,rowData,soldData,assignment_date);
        ArrayList<String> rowList=dao.stockInhand();
        if(rowList!=null){
            this.openingStock(rowList);
        }

    }//GEN-LAST:event_jLabel5MouseClicked

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed
       Date d1 = jXDatePicker1.getDate();
        Date d2 = new Date();
        long diff = (d2.getTime() - d1.getTime())/1000/60/60/24;
        if(diff > 60){
            JOptionPane.showMessageDialog(null,"You can't select date older than 60 days","Daily Calculation",JOptionPane.PLAIN_MESSAGE);
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            jXDatePicker1.setDate(new Date());
            jXDatePicker1.setFormats(format);
            return;
        }
    }//GEN-LAST:event_jXDatePicker1ActionPerformed

    private void jTextField1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTextField1PropertyChange

    }//GEN-LAST:event_jTextField1PropertyChange

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        try{
            Double.parseDouble(jTextField1.getText());
        }
        catch(Exception e){
            logger.error("Error [DH-Please enter proper margin]",e);
            JOptionPane.showMessageDialog(null,"Please enter proper margin","Daily Calculation",JOptionPane.PLAIN_MESSAGE);
            jTextField1.requestFocusInWindow();
        }
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

    }//GEN-LAST:event_jTable2MouseClicked

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
            java.util.logging.Logger.getLogger(DailyHisab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DailyHisab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DailyHisab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DailyHisab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DailyHisab().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    // End of variables declaration//GEN-END:variables
}
