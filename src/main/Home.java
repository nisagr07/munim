/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import bean.PrimaryStockBean;
import expense.AddExpense;
import fos.AddFos;
import fos.RemoveFos;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import voucher.RemoveVoucher;
import voucher.AddVoucher;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import reports.ClosingStock;
import reports.NetProfit;
import reports.PrimaryStockReceived;
import reports.StockAssigned;
import reports.TotalExpense;
import reports.TotalSold;
import stock.AddPrimaryStock;
import stock.AssignStock;
import stock.ReturnStock;
import stock.StockInhand;


import stock.ViewPrimaryStock;



/**
 *
 * @author nishant
 */
public class Home extends javax.swing.JFrame {

    public static Logger logger= Logger.getLogger(Home.class);
    /**
     * Creates new form NewJFrame
     */
    public Home() {
       initComponents();
       this.setSize(600, 400);
       this.setLocationRelativeTo(null);
       this.setResizable(false);
       try {
            this.setIconImage( new javax.swing.ImageIcon(getClass().getResource("m_logo.png")).getImage());
        }
        catch (Exception exc) {
            logger.error("ERROR [HO-ICON]",exc);
        }
        Date d = new Date();
        long currentDay = (d.getTime())/1000/60/60/24;
        long thresholdDay = (currentDay - 60);
        long thresholdTime = thresholdDay *24*60*60*1000;
        d.setTime(thresholdTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        long thresholdDate = Long.parseLong(dateFormat.format(d));
        Dao.cleanup("Closing_Stock",thresholdDate);
        Dao.cleanup("expense",thresholdDate);
        Dao.cleanup("primary_stock_assignment",thresholdDate);
        Dao.cleanup("stock_assignment",thresholdDate);
        Dao.cleanup("Total_Sold",thresholdDate);
       
    }
    public Home(String username){
        this();
        jLabel2.setText("Welcome "+username+" !!!");
        jLabel3.setText("<html><li>Hey "+username+", I am your Munim.</li>"
                + "<li>I will be working as your Personal Accountant.</li>"
                + "<li>I will manage all the denominations of vouchers you are dealing with.</li>"
                + "<li>I will keep record of all the FOS working under you.</li>"
                + "<li>I will track all the Primary Stocks you are purchasing.</li>"
                + "<li>I will monitor all your expenses.</li>"
                + "<li>I will look into the stocks which are assigned to your FOS.</li>"
                + "<li>I will do daily calculations for you.</li>"
                + "<li>I will give you complete report of stock purchased, stock sold, total expenses and overall profit in your business.</li>"
                + "</html>");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Munim");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/main_page_logo.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Cooper Black", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 0));

        jMenu1.setText("Voucher");

        jMenuItem1.setText("Add Voucher Denomination");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
        });
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Remove Voucher Denomination");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("FOS");

        jMenuItem3.setText("Add FOS");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem4.setText("Remove FOS");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Primary Stock");

        jMenuItem5.setText("Add Primary Stock");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem7.setText("View Primary Stock");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuBar1.add(jMenu2);

        jMenu7.setText("Expenses");

        jMenuItem14.setText("Add Expense");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem14);

        jMenuBar1.add(jMenu7);

        jMenu4.setText("Stock Assignment");

        jMenuItem8.setText("Assign Stock");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuItem9.setText("Stock Inhand");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem6.setText("Return Stock");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Daily Calculation");
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu5);

        jMenu6.setText("Reports");

        jMenuItem10.setText("Primary Stock Received");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem10);

        jMenuItem11.setText("Stock Assigned");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem11);

        jMenuItem12.setText("Total Sold");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem12);

        jMenuItem13.setText("Closing Stock");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem13);

        jMenuItem15.setText("Total Expense");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem15);

        jMenuItem16.setText("Net Profit");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem16);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed

        Dao dao=new Dao();
        ArrayList<String> fosList=dao.fosList();
        if(fosList.isEmpty() || (fosList.size()==1 && fosList.get(0).equalsIgnoreCase("-1"))) {

            JOptionPane.showMessageDialog(null,"There are no FOS","FOS Remove",JOptionPane.PLAIN_MESSAGE);
        }
        else {
            RemoveFos rf=new RemoveFos(fosList);
            rf.pack();
            rf.setSize(600, 400);  
            rf.setLocationRelativeTo(null); 
            rf.setVisible(true);
            rf.setResizable(false);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        AddFos af=new AddFos();
        af.pack();
        af.setSize(600, 400);  
        af.setLocationRelativeTo(null); 
        af.setVisible(true);
        af.setResizable(false);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Dao dao=new Dao();
        ArrayList<String> voucherList=dao.voucherList();
        if(voucherList.size()==0) {

            JOptionPane.showMessageDialog(null,"There are no vouchers","Voucher Remove",JOptionPane.PLAIN_MESSAGE);
        }
        else {
            RemoveVoucher rv=new RemoveVoucher(voucherList);
            rv.pack();
            rv.setSize(600, 400);  
            rv.setLocationRelativeTo(null); 
            rv.setVisible(true);
            rv.setResizable(false);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        AddVoucher av=new AddVoucher();
        av.pack();
        av.setSize(600, 400);  
        av.setLocationRelativeTo(null); 
        av.setVisible(true);
        av.setResizable(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked

    }//GEN-LAST:event_jMenuItem1MouseClicked

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
       Dao dao=new Dao();
        ArrayList<String> voucherList=dao.voucherList();
        if(voucherList.size()==0) {

            JOptionPane.showMessageDialog(null,"There are no vouchers","Primary Stock",JOptionPane.PLAIN_MESSAGE);
        }
        else {
            AddPrimaryStock aps=new AddPrimaryStock(voucherList);
            aps.pack();
            aps.setSize(600, 400);  
            aps.setLocationRelativeTo(null); 
            aps.setVisible(true);
            aps.setResizable(false);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
       Dao dao=new Dao();
       ArrayList<PrimaryStockBean> primaryStockList=dao.primaryStockList();
       if(primaryStockList.isEmpty()) {
           JOptionPane.showMessageDialog(null,"There are no Stock","Primary Stock",JOptionPane.PLAIN_MESSAGE);
       }
       else {
               ViewPrimaryStock vps=new ViewPrimaryStock(primaryStockList);
               vps.pack();
               vps.setSize(600, 400);  
               vps.setLocationRelativeTo(null); 
               vps.setVisible(true);
               vps.setResizable(false);
       }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
       Dao dao = new Dao();
       ArrayList<PrimaryStockBean> psbList=dao.primaryStockList();
       ArrayList<String> fosList=dao.fosList();
       if(psbList.isEmpty()){
           JOptionPane.showMessageDialog(null,"There are no Stock","Stock Assignment",JOptionPane.PLAIN_MESSAGE);
       }
       else if(fosList.isEmpty()||(fosList.size()==1 && fosList.get(0).equals("-1"))){
           JOptionPane.showMessageDialog(null,"There are no FOS","Stock Assignment",JOptionPane.PLAIN_MESSAGE);
       }
       else {
           AssignStock as=new AssignStock();
           as.stockAssign(fosList, psbList);
           as.pack();
           //as.setSize(600, 400);  
           //as.setLocationRelativeTo(null); 
           as.setExtendedState(JFrame.MAXIMIZED_BOTH);
           as.setVisible(true);
       }
        
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        Dao dao=new Dao();
        ArrayList<String> rowList=dao.stockInhand();
        if(rowList!=null){
            StockInhand si=new StockInhand(rowList);
            si.pack();
           // si.setSize(600, 400);  
            //si.setLocationRelativeTo(null);
            si.setExtendedState(JFrame.MAXIMIZED_BOTH);
            si.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null,"Stock is not assigned","Stock Inhand",JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        Dao dao = new Dao();
       ArrayList<PrimaryStockBean> psbList=dao.primaryStockList();
       ArrayList<String> fosList=dao.fosList();
       ArrayList<String> stockAssignedList = dao.stockInhand();
        if(stockAssignedList!=null){
           ReturnStock rs = new ReturnStock();
           rs.stockReturn(fosList, psbList,stockAssignedList);
           rs.pack();
           //rs.setSize(600, 400);  
          // rs.setLocationRelativeTo(null); 
          rs.setExtendedState(JFrame.MAXIMIZED_BOTH);
           rs.setVisible(true);
           
       }
        else{
            JOptionPane.showMessageDialog(null,"Stock is not assigned","Return Stock",JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
       Dao dao=new Dao();
        ArrayList<String> rowList=dao.stockInhand();
        if(rowList!=null){
            DailyHisab dh=new DailyHisab();
            dh.openingStock(rowList);
            dh.pack();
            //dh.setSize(600, 400);  
            //dh.setLocationRelativeTo(null); 
            dh.setExtendedState(JFrame.MAXIMIZED_BOTH);
            dh.setVisible(true);
            
        }
        else{
            JOptionPane.showMessageDialog(null,"Stock is not assigned","Stock Assignment",JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_jMenu5MouseClicked

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
       PrimaryStockReceived psr = new PrimaryStockReceived();
       psr.setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        Dao dao=new Dao();
        ArrayList<String> fosList=dao.fosList();
        StockAssigned sa = new StockAssigned(fosList);
        sa.setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        Dao dao=new Dao();
        ArrayList<String> fosList=dao.fosList();
        TotalSold ts = new TotalSold(fosList);
        ts.setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        Dao dao=new Dao();
        ArrayList<String> fosList=dao.fosList();
        ClosingStock cs = new ClosingStock(fosList);
        cs.setVisible(true);
        
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        
        Dao dao=new Dao();
        ArrayList<String> fosList=dao.fosList();
        if(fosList.isEmpty() || (fosList.size()==1 && fosList.get(0).equalsIgnoreCase("-1"))) {

            JOptionPane.showMessageDialog(null,"There are no FOS","FOS Remove",JOptionPane.PLAIN_MESSAGE);
        }
        else {
            AddExpense ae = new AddExpense();
            ae.setVisible(true);
            ae.expenseTable(fosList);
        }
        
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        Dao dao=new Dao();
        ArrayList<String> fosList=dao.fosList();
        TotalExpense te = new TotalExpense(fosList);
        te.setVisible(true);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
       Dao dao=new Dao();
        ArrayList<String> fosList=dao.fosList();
        NetProfit np = new NetProfit(fosList);
        np.setVisible(true);
    }//GEN-LAST:event_jMenuItem16ActionPerformed



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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    // End of variables declaration//GEN-END:variables
}
