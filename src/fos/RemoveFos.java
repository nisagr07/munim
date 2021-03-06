/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fos;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;
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
public class RemoveFos extends javax.swing.JFrame {
    private static final Logger logger=Logger.getLogger(RemoveFos.class);
    /**
     * Creates new form RemoveFos
     */
    public RemoveFos() {
        initComponents();
        try {
            this.setIconImage( new javax.swing.ImageIcon(getClass().getResource("m_logo.png")).getImage());
        }
        catch (Exception exc) {
            logger.error("ERROR [RF-ICON]",exc);
        }
    }

     public RemoveFos(ArrayList<String> fosList){
            this();
             MyTableModel model = new MyTableModel();
             for(String fos:fosList){
             model.addRow(new Object[]{fos,false});
             }
            jTable1.setModel(model);
             jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
           jTable1.getColumnModel().getColumn(0).setMinWidth(90);
           jTable1.getColumnModel().getColumn(1).setMinWidth(90);
            jTable1.setFillsViewportHeight(true);
            jTable1.setRowHeight(20);
           jTable1.setShowGrid(true);

           DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            ((DefaultTableCellRenderer)jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
            jTable1.getColumnModel().getColumn(0).setCellRenderer(dtcr);
           int width=jTable1.getColumnModel().getColumn(0).getWidth()+jTable1.getColumnModel().getColumn(1).getWidth();
           int height = jTable1.getRowHeight()*(jTable1.getRowCount()+1);
           if(height>160){
               jScrollPane1.setPreferredSize(new Dimension(width+21, 168));
           } else{
               jScrollPane1.setPreferredSize(new Dimension(width+6, height+8));
           }
           
           jPanel1.add(jScrollPane1);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Remove FOS");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FOS", "Remove"
            }
        ));
        jTable1.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.ipady = 136;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(24, 42, 41, 57);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/small_logo.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("Remove FOS");
        jLabel1.setToolTipText("Remove FOS");

        jButton1.setText("Remove");
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
                .addGap(1, 1, 1)
                .addComponent(jLabel2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(158, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(239, 239, 239))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int rowCount=jTable1.getRowCount();
        ArrayList<String> fosList=new ArrayList<String>();
        for(int i=0;i<rowCount;i++) {
            if((boolean)jTable1.getModel().getValueAt(i,1)){
                fosList.add((String)jTable1.getModel().getValueAt(i,0));
            }
        }

        if(fosList.size() > 0){
            this.setVisible(false);
            Dao dao=new Dao();
            dao.removeFos(fosList);
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
            java.util.logging.Logger.getLogger(RemoveFos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RemoveFos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RemoveFos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RemoveFos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RemoveFos().setVisible(true);
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
    // End of variables declaration//GEN-END:variables
}
class MyTableModel extends DefaultTableModel {

    public MyTableModel() {
      super(new String[]{"FOS","Remove"}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      Class clazz = String.class;
      switch (columnIndex) {
        case 0:
          clazz = String.class;
          break;
        case 1:
          clazz = Boolean.class;
          break;
      }
      return clazz;
    }

    

    @Override
    public void setValueAt(Object aValue, int row, int column) {
      if (aValue instanceof Boolean && column == 1) {
        Vector rowData = (Vector)getDataVector().get(row);
        rowData.set(1, (boolean)aValue);
        fireTableCellUpdated(row, column);
      }
    }
}