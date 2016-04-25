/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import bean.PrimaryStockBean;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author nishant
 */

public class Dao {
    private static  org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(Dao.class);
    private static Connection getConnection(){
        Connection con=null;
        try {
            File dir = new File("c:\\m");
            if(!dir.exists()){
                if(!dir.mkdir()){
                    logger.error("Unable to create m directory");
                }
            }
            File f = new File("c:\\m\\m.mdb");
            if(!f.exists()){
                DatabaseBuilder.create(Database.FileFormat.V2003, f);
            }
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=c:\\m\\m.mdb");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Database not configured properly","Database Error",JOptionPane.PLAIN_MESSAGE);
            logger.error("Error Code[DAO-CON-1]",e);
            return null;
        }
        return con;
    }
    public static String checkUser(){
        Connection con=getConnection();
        String returnMsg="";
        try { 
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "user", null); 
            if(!res.next()){
                stmt.execute("create table user (Username char,Password char,Licence char,StartDate char,Expire char)"); 
            }
            stmt.execute("select * from user");
            ResultSet rs=stmt.getResultSet();
            if(rs!=null && rs.next()){
                returnMsg = rs.getString("Username").trim(); 
            }
            else{
                returnMsg = "not_registered";
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Some Problem in login","Login Error",JOptionPane.PLAIN_MESSAGE);
            logger.error("ERROR[DAO-CU-1]",e);
            return null;
        } 
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return returnMsg;
    }
    public static void register(String username,String password){
        Connection con=getConnection();
        try { 
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "user", null); 
            if(res.next()){
                stmt.execute("insert into user (Username,Password) values ('"+username+"','"+password+"')"); 
                JOptionPane.showMessageDialog(null,"Registeration Successful","Registeration",JOptionPane.PLAIN_MESSAGE);
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Not Registered","Register Error",JOptionPane.PLAIN_MESSAGE);
            logger.error("ERROR[DAO-REG]",e);
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     public static String[] login(String username, String password){
        Connection con=getConnection();
        String[] returnMsg=new String[2];
        try { 
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "user", null); 
            if(res.next()){
               stmt.execute("select * from user where Username='"+username+"'");
                ResultSet rs=stmt.getResultSet();
                if(rs!=null && rs.next()){
                    String dbPass = rs.getString("Password");
                    String dbKey = rs.getString("Licence");
                    if(dbPass!=null && dbPass.trim().equals(password)){
                        returnMsg[0] = "right_password";
                    }
                    else {
                        returnMsg[0] = "wrong_password";
                    }
                    if(dbKey!=null && dbKey.trim().length()>0){
                        returnMsg[1] = dbKey;
                    }
                    else {
                        returnMsg[1] = "nokey";
                    }
                }
                else{
                    returnMsg[0] = "not_registered";
                    returnMsg[1] = "nokey";
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"Some Problem in login","Login Error",JOptionPane.PLAIN_MESSAGE);
                logger.error("ERROR[DAO-LOGIN-1]");
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Some Problem in login","Login Error",JOptionPane.PLAIN_MESSAGE);
            logger.error("ERROR[DAO-LOGIN-2]",e);
        } 
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return returnMsg;
    }
    public static void changePassword(String username,String password){
        Connection con=getConnection();
        try { 
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "user", null); 
            if(res.next()){
                stmt.execute("update user set Password ='"+password+"' where Username='"+username+"'" ); 
                JOptionPane.showMessageDialog(null,"Password successfully changed","Change Password",JOptionPane.PLAIN_MESSAGE);
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Password Not changed","Change Password",JOptionPane.PLAIN_MESSAGE);
            logger.error("ERROR[DAO-CP]",e);
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     public static boolean activateUser(String username,String key){
        Connection con=getConnection();
        boolean b = false;
        try { 
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "user", null); 
            if(res.next()){
                stmt.execute("update user set Licence ='"+key+"' where Username='"+username+"'" ); 
                JOptionPane.showMessageDialog(null,"Successfully Activated","Activation",JOptionPane.PLAIN_MESSAGE);
                b = true;
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"There is some problem in activation","Activation",JOptionPane.PLAIN_MESSAGE);
            logger.error("ERROR[DAO-ACT]",e);
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return b;
    }
     public static boolean setStartDate(String username,String startDate){
        Connection con=getConnection();
        boolean b = false;
        try { 
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "user", null); 
            if(res.next()){
                stmt.execute("update user set StartDate ='"+startDate+"' where Username='"+username+"'" );
                b = true;
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"There is some problem in Trial Version","Activation",JOptionPane.PLAIN_MESSAGE);
            logger.error("ERROR[DAO-FT]",e);
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return b;
    }
    public static String getStartDate(String username){
        Connection con=getConnection();
        String startDate = "";
        try { 
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "user", null); 
            if(res.next()){
                stmt.execute("select * from user where Username='"+username+"'" );
                ResultSet rs = stmt.getResultSet();
                if(rs.next()){
                    startDate = rs.getString("StartDate");
                }
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"There is some problem in Trial Version","Activation",JOptionPane.PLAIN_MESSAGE);
            logger.error("ERROR[DAO-FT]",e);
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return startDate;
    } 
    public static void setExpire(String username){
        Connection con=getConnection();
        try { 
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "user", null); 
            if(res.next()){
                stmt.execute("update user set Expire=1 where Username='"+username+"'" );
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"There is some problem in Trial Version","Activation",JOptionPane.PLAIN_MESSAGE);
            logger.error("ERROR[DAO-SE]",e);
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static String getExpire(String username){
        Connection con=getConnection();
        String expire = "";
        try { 
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "user", null); 
            if(res.next()){
                stmt.execute("select * from user where Username='"+username+"'" );
                ResultSet rs = stmt.getResultSet();
                if(rs.next()){
                    expire = rs.getString("Expire");
                }
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"There is some problem in Trial Version","Activation",JOptionPane.PLAIN_MESSAGE);
            logger.error("ERROR[DAO-GE]",e);
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return expire;
    }
    public void addVoucher(ArrayList<String> voucherList) {
        Connection con=getConnection();
        try {        
        Statement stmt = con.createStatement();
        DatabaseMetaData meta = con.getMetaData(); 
        ResultSet res = meta.getTables(null, null, "primary_stock", null); 
        if(!res.next()){ 
          stmt.execute("create table primary_stock  ( Voucher char, Quantity Integer )"); 
          stmt.execute("insert into primary_stock values ('Virtual_Topup','0')");
        } 
        StringBuilder alreadyAdded=new StringBuilder();
        StringBuilder successfullyAdded=new StringBuilder();
        for(String voucher:voucherList){
            stmt.execute("select Voucher from primary_stock where Voucher='"+voucher+"'");
            ResultSet rs = stmt.getResultSet();
            if((rs!=null) && (rs.next())) {   
                alreadyAdded.append(voucher).append(",");
            }
            else {
                 stmt.execute("insert into primary_stock values ('"+voucher+"','0')");
                 successfullyAdded.append(voucher).append(",");
            }
        }
     
        if(alreadyAdded.length()>0){
            alreadyAdded.deleteCharAt(alreadyAdded.length()-1);
            JOptionPane.showMessageDialog(null,"Voucher Denominations "+alreadyAdded+" Already Added!!!","Add Voucher",JOptionPane.PLAIN_MESSAGE);
        }
         if(successfullyAdded.length()>0){
            successfullyAdded.deleteCharAt(successfullyAdded.length()-1);
            JOptionPane.showMessageDialog(null,"Voucher Denominations "+successfullyAdded+" Successfully Added!!!","Add Voucher",JOptionPane.PLAIN_MESSAGE);
        }
        
        
        } catch(Exception e) {
            logger.error("Error Code[DAO-AV-1]There is some problem in adding voucher denominations",e);
            JOptionPane.showMessageDialog(null,"There is some problem in adding voucher denominations","Add Voucher",JOptionPane.PLAIN_MESSAGE);
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public  ArrayList<String> voucherList(){
        Connection con=getConnection();
        ArrayList<String> voucherList=new ArrayList<>();
        try {        
        Statement stmt = con.createStatement();
        DatabaseMetaData meta = con.getMetaData(); 
        ResultSet res = meta.getTables(null, null, "primary_stock", null); 
        if(!res.next()){ 
            //voucherList.add("-1");
          return voucherList; 
        } 
        stmt.execute("select Voucher from primary_stock where Voucher NOT LIKE '%V%' ORDER BY ABS(Voucher) ");
        ResultSet rs = stmt.getResultSet();
        while((rs!=null) && (rs.next()))
        {
            voucherList.add(rs.getString("Voucher").trim());
        }
         
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return voucherList;
    }
    
    public void removeVoucher(ArrayList<String> voucherList) {
        Connection con=getConnection();
        
        try {        
            Statement stmt = con.createStatement();
            for(int i=0;i<voucherList.size();i++){
                boolean removeFlag=true;
                stmt.execute("select * from primary_stock where Voucher='"+voucherList.get(i)+"'");
                ResultSet rs=stmt.getResultSet();
                int primary_stock=0;
                if(rs.next()){
                    primary_stock=rs.getInt("Quantity");
                }
                if(primary_stock>0){
                    JOptionPane.showMessageDialog(null,"Can't delete, voucher "+voucherList.get(i)+" has some primary stock","Remove Voucher",JOptionPane.PLAIN_MESSAGE);
                    removeFlag=false;
                }
                else {
                    DatabaseMetaData meta = con.getMetaData();
                    ResultSet table=meta.getTables(null,null,"stock_inhand",null);
                    if(table.next()){
                        ArrayList<String> headerList=new ArrayList<>();
                    
                    ResultSet columns=meta.getColumns(null, null, "stock_inhand", null);
                     while (columns.next()) {
                          headerList.add(columns.getString("COLUMN_NAME"));
                     }
                    
                    stmt.execute("select * from stock_inhand");
                   ResultSet rs1=stmt.getResultSet();
                   while(rs1!=null && rs1.next()){
                        
                       if(headerList.contains(voucherList.get(i)) && rs1.getInt(voucherList.get(i))>0){
                        JOptionPane.showMessageDialog(null,"Can't delete, voucher "+voucherList.get(i)+" is assigned to someone","Remove Voucher",JOptionPane.PLAIN_MESSAGE);                       
                        removeFlag=false;
                        break;
                    }
                } 
              }
                    
                    
                }
                if(removeFlag){
                    stmt.execute("delete from primary_stock where Voucher='"+voucherList.get(i)+"'");
                    JOptionPane.showMessageDialog(null,"Voucher "+voucherList.get(i)+" Successfully Removed!!!","Remove Voucher",JOptionPane.PLAIN_MESSAGE);                 
                }
                
            }
            
    
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void addFos(ArrayList<String> fosList) {
        Connection con=getConnection();
        try {        
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "fos", null); 
            if(!res.next()){ 
              stmt.execute("create table fos ( Name CHAR )"); 
            } 
            StringBuilder alreadyAdded=new StringBuilder();
            StringBuilder successfullyAdded=new StringBuilder();
            for(String fos:fosList){
                stmt.execute("select Name from fos where Name='"+fos+"'");
                ResultSet rs = stmt.getResultSet();
                if((rs!=null) && (rs.next()))
                {   
                    alreadyAdded.append(fos).append(",");
                }
                else {
                    stmt.execute("insert into fos (Name) values ('"+fos+"')");
                    successfullyAdded.append(fos).append(",");
                }
            }
            if(alreadyAdded.length()>0){
                alreadyAdded.deleteCharAt(alreadyAdded.length()-1);
                JOptionPane.showMessageDialog(null,"FOS "+alreadyAdded+" Already Added!!!","Add FOS",JOptionPane.PLAIN_MESSAGE);
            }
            if(successfullyAdded.length()>0){
                successfullyAdded.deleteCharAt(successfullyAdded.length()-1);
                JOptionPane.showMessageDialog(null,"FOS "+successfullyAdded+" Successfully Added!!!","Add FOS",JOptionPane.PLAIN_MESSAGE);
            }
        
            } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public  ArrayList<String> fosList(){
        Connection con=getConnection();
        ArrayList<String> fosList=new ArrayList<>();
        try {        
        Statement stmt = con.createStatement();
        DatabaseMetaData meta = con.getMetaData(); 
        ResultSet res = meta.getTables(null, null, "fos", null); 
        if(!res.next()){ 
            fosList.add("-1");
          return fosList; 
        } 
        stmt.execute("select Name from fos");
        ResultSet rs = stmt.getResultSet();
        while((rs!=null) && (rs.next()))
        {
            fosList.add(rs.getString("Name").trim());
        }
         
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fosList;
    }
    
    public void removeFos(ArrayList<String> fosList) {
        Connection con=getConnection();
        try {        
            Statement stmt = con.createStatement();
            ArrayList<String> headerList=new ArrayList<>();
            DatabaseMetaData meta = con.getMetaData();
            ResultSet columns=meta.getColumns(null, null, "stock_inhand", null);
            while (columns.next()) {
                headerList.add(columns.getString("COLUMN_NAME"));
            }
            for(int i=0;i<fosList.size();i++){
                boolean removeFlag=true;
                ResultSet r = meta.getTables(null, null, "stock_inhand", null);
                if(r!=null && r.next()){
                    stmt.execute("select * from stock_inhand where Fos='"+fosList.get(i)+"'");
                    ResultSet rs=stmt.getResultSet();
                    if(rs!=null && rs.next()){
                        for(int j=1;j<headerList.size();j++){
                            if(rs.getInt(headerList.get(j))>0){
                                JOptionPane.showMessageDialog(null,"Can't remove, FOS "+fosList.get(i)+" has some stock assigned","Remove FOS",JOptionPane.PLAIN_MESSAGE);
                                removeFlag=false;
                                break;
                            }
                        }
                    }
                }
                 
                if(removeFlag) {
                    stmt.execute("delete from fos where Name='"+fosList.get(i)+"'"); 
                    JOptionPane.showMessageDialog(null,"FOS "+fosList.get(i)+" Successfully Removed!!!","Remove FOS",JOptionPane.PLAIN_MESSAGE);
                 }
                                
            }
            
    
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void addPrimaryStock(HashMap<String,String> map,String assignment_date) {
        Connection con=getConnection();
        int n=0;
        try {        
        Statement stmt = con.createStatement();
        Statement stmt1 = con.createStatement();
        for(String key:map.keySet()){
            stmt.execute("select Quantity from primary_stock where Voucher='"+key+"'");
            ResultSet rs = stmt.getResultSet();
            if((rs!=null) && (rs.next()))
            {   
                n=rs.getInt("Quantity");
            }
            int updatedQuantity=n+Integer.parseInt(map.get(key));
            stmt.execute("update primary_stock set Quantity="+updatedQuantity+" where Voucher='"+key+"'");
        }
        
        ArrayList<String> voucherList=new ArrayList<>();
        stmt.execute("select Voucher from primary_stock");
        ResultSet rs = stmt.getResultSet();
        while((rs!=null) &&(rs.next())){
            voucherList.add(rs.getString("Voucher").trim());
        }
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getTables(null, null, "primary_stock_assignment", null);
        StringBuilder column=null;
        if(!res.next()){
            column=new StringBuilder();
            stmt.execute("select Voucher from primary_stock");
            for(String voucher:voucherList){
                column.append(voucher).append(" Integer,");
            }
            column.deleteCharAt(column.length()-1);
            stmt.execute("create table primary_stock_assignment(AssignedDate Integer, "+column.toString()+")"); 
        }
        else {
            for(String voucher:voucherList){
                ResultSet rs2 = meta.getColumns(null, null, "primary_stock_assignment", voucher);
                if(!rs2.next()){
                    stmt.execute("ALTER TABLE primary_stock_assignment ADD COLUMN "+voucher+" Integer");
                }
            }
        }
        
        stmt.execute("select * from primary_stock_assignment where AssignedDate="+assignment_date+"");
        ResultSet rs1=stmt.getResultSet();
        if(rs1.next()){
            int updatedAmount=0;
            for(String key:map.keySet()){
                updatedAmount=Integer.parseInt(map.get(key)) + rs1.getInt(key);
                stmt1.execute("update primary_stock_assignment set "+key+"="+updatedAmount+" where AssignedDate="+assignment_date+"");
            }
        }
        else{
            StringBuilder columnInsert=new StringBuilder();
            for(String key:map.keySet()){
                columnInsert.append(key).append(",");
            }
            columnInsert.deleteCharAt(columnInsert.length()-1);
            StringBuilder data=new StringBuilder();
            for(String key:map.keySet()){
                data.append(map.get(key)).append(",");
            }
            data.deleteCharAt(data.length()-1);
            stmt.execute("insert into primary_stock_assignment(AssignedDate,"+columnInsert.toString()+") values("+assignment_date+","+data.toString()+")");
        }
        
        
        
        JOptionPane.showMessageDialog(null,"Stock Successfully Added!!!","Primary Stock",JOptionPane.PLAIN_MESSAGE);
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void removePrimaryStock(String voucher, int quantity) {
        Connection con=getConnection();
        int n=0;
        try {        
        Statement stmt = con.createStatement();
        stmt.execute("select Quantity from primary_stock where Voucher='"+voucher+"'");
        ResultSet rs = stmt.getResultSet();
         if((rs!=null) && (rs.next()))
        {   
           n=rs.getInt("Quantity");
        }
         int updatedQuantity=n-quantity;
         if(updatedQuantity<0){
              JOptionPane.showMessageDialog(null,"Primary Stock cannot be less than zero","Primary Stock",JOptionPane.PLAIN_MESSAGE);
         return;
         }
        stmt.execute("update primary_stock set Quantity="+updatedQuantity+" where Voucher='"+voucher+"'");
        JOptionPane.showMessageDialog(null,"Stock Successfully Updated!!!","Primary Stock",JOptionPane.PLAIN_MESSAGE);
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public  ArrayList<PrimaryStockBean> primaryStockList(){
        Connection con=getConnection();
        ArrayList<PrimaryStockBean> primaryStockList=new ArrayList<>();
        try {        
        Statement stmt = con.createStatement();
        DatabaseMetaData meta = con.getMetaData(); 
        ResultSet res = meta.getTables(null, null, "primary_stock", null); 
        if(!res.next()){ 
          return primaryStockList; 
        } 
        stmt.execute("select * from primary_stock where Voucher NOT LIKE '%Topup%' ORDER BY ABS(Voucher) ");
        ResultSet rs = stmt.getResultSet();
        while((rs!=null) && (rs.next()))
        {
            PrimaryStockBean psb=new PrimaryStockBean();
            psb.setVoucher(rs.getString("Voucher").trim());
            psb.setQuantity(rs.getInt("Quantity"));
            primaryStockList.add(psb);
        }
         stmt.execute("select * from primary_stock where Voucher LIKE '%Topup%'");
        ResultSet rs1 = stmt.getResultSet();
        while((rs1!=null) && (rs1.next()))
        {
            PrimaryStockBean psb=new PrimaryStockBean();
            psb.setVoucher(rs1.getString("Voucher").trim());
            psb.setQuantity(rs1.getInt("Quantity"));
            primaryStockList.add(psb);
        }
         
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return primaryStockList;
    }
    
    public void assignStock(ArrayList<String> voucherList,ArrayList<String> rowData, String assignment_date) {
        Connection con=getConnection();
        try {
            StringBuilder column=null;
            StringBuilder columnInsert=null;
            StringBuilder addColumn=new StringBuilder();
            StringBuilder addVoucher=new StringBuilder();
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData();
            ResultSet res = meta.getTables(null, null, "stock_assignment", null); 
            if(!res.next()){
                column=new StringBuilder();
                for(int i=0;i<voucherList.size();i++){
                   column.append(voucherList.get(i)).append(" Integer,");
                }
                column.deleteCharAt(column.length()-1);
                stmt.execute("create table stock_assignment(AssignedDate Integer, FOS char,"+column.toString()+")"); 
              }
            else {
                for(int i=0;i<voucherList.size();i++){
                    ResultSet rs2 = meta.getColumns(null, null, "stock_assignment", voucherList.get(i));
                    if(!rs2.next()){
                        addColumn.append(voucherList.get(i)).append(" Integer,");
                    
                    }
                }
                if(addColumn.length()>0){
                    addColumn.deleteCharAt(addColumn.length()-1);
                    stmt.close();
                    con.close();
                    Connection c=getConnection();
                    Statement s=c.createStatement();
                    s.execute("ALTER TABLE stock_assignment ADD COLUMN "+addColumn.toString()+"");
                    c.close();
                    s.close();
                }
              }
            if(con.isClosed()){
                con=getConnection();
            }
            Statement stmt1=con.createStatement();
            Statement stmt2=con.createStatement();
            columnInsert=new StringBuilder();
            for(int i=0;i<voucherList.size();i++){
                columnInsert.append(voucherList.get(i)).append(",");
            }
            columnInsert.deleteCharAt(columnInsert.length()-1);
            for(int r=0;r<rowData.size()-1;r++){
                String parts[]=rowData.get(r).split(",");
                stmt1.execute("select * from stock_assignment where AssignedDate="+assignment_date+" AND FOS='"+parts[0]+"'");
                ResultSet rs=stmt1.getResultSet();
                
                if(rs.next()){
                        for(int j=1;j<parts.length;j++){
                            if(!parts[j].equals("0")){
                                int updatedAmount=Integer.parseInt(parts[j]) + rs.getInt(voucherList.get(j-1));
                                stmt2.execute("update stock_assignment set "+voucherList.get(j-1)+"="+updatedAmount+" where AssignedDate="+assignment_date+" AND FOS='"+parts[0]+"'");
                                
                            }
                        }
                }
                else {
                    StringBuilder data=new StringBuilder();
                    for(int i=1;i<parts.length;i++){
                        data.append(parts[i]).append(",");
                    }
                    data.deleteCharAt(data.length()-1);
                    stmt1.execute("insert into stock_assignment(AssignedDate,FOS,"+columnInsert.toString()+") values("+assignment_date+",'"+parts[0]+"',"+data.toString()+")");
                }
            }
            String [] primaryStock=rowData.get(rowData.size()-1).split(",");
            for(int i=1;i<=voucherList.size()-1;i++){
                stmt1.execute("update primary_stock set Quantity="+primaryStock[i]+" where Voucher='"+voucherList.get(i-1)+"'");
            }
              
               
                 
               ResultSet res2 = meta.getTables(null, null, "stock_inhand", null);
               if(!res2.next()){
                column=new StringBuilder();
                for(int i=0;i<voucherList.size();i++){
                   column.append(voucherList.get(i)).append(" Integer,");
                }
                column.deleteCharAt(column.length()-1);
                stmt1.execute("create table stock_inhand(FOS char,"+column.toString()+")"); 
              }
            else {
                for(int i=0;i<voucherList.size();i++){
                    ResultSet rs2 = meta.getColumns(null, null, "stock_inhand", voucherList.get(i));
                    if(!rs2.next()){
                        addVoucher.append(voucherList.get(i)).append(" Integer,");
                        
                    }
                    rs2.close();
                }
                if(addVoucher.length()>0){
                    addVoucher.deleteCharAt(addVoucher.length()-1);
                   
                    stmt1.close();
                    stmt2.close();
                    con.close();
                    Connection c=getConnection();
                    Statement s=c.createStatement();
                    s.execute("ALTER TABLE stock_inhand ADD COLUMN "+addVoucher+"");
                    c.close();
                    s.close();
                }
              }
               if(con.isClosed()){
                   con=getConnection();
               }
               Statement stmt3=con.createStatement();
               Statement stmt4=con.createStatement();
               String[] parts=null;
               int updatedStock=0;
               for(int i=0;i<rowData.size()-1;i++){
                   parts=rowData.get(i).split(",");
                   stmt3.execute("select * from stock_inhand where FOS='"+parts[0]+"'");
                   ResultSet rs=stmt3.getResultSet();
                   if(!rs.next()){
                       stmt3.execute("insert into stock_inhand(FOS) values('"+parts[0]+"')");
                   }
               }
               
               for(int i=0;i<rowData.size()-1;i++){
                   parts=rowData.get(i).split(",");
                   stmt3.execute("select * from stock_inhand where FOS='"+parts[0]+"'");
                   ResultSet rs=stmt3.getResultSet();
                   if(rs.next()){
                       for(int j=0;j<voucherList.size();j++){
                       updatedStock=rs.getInt(voucherList.get(j))+Integer.parseInt(parts[j+1]);
                       stmt4.execute("update stock_inhand set "+voucherList.get(j)+"="+updatedStock+" where FOS='"+parts[0]+"'");
                    }
                   }
                   
               }
               JOptionPane.showMessageDialog(null,"Stock successfully assigned","Stock Assignment",JOptionPane.PLAIN_MESSAGE);
               
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public ArrayList<String> stockInhand() {
        Connection con=getConnection();
        ArrayList<String> rowList=new ArrayList<>();
        try {
        ArrayList<Integer>voucherList=new ArrayList<>();
        ArrayList<String> fosList=new ArrayList<>(); 
        ArrayList<String> mainFosList=new ArrayList<>(); 
        ArrayList<String> removeFosList=new ArrayList<>();
        ArrayList<String> mainVoucherList=new ArrayList<>(); 
        ArrayList<Integer> removeVoucherList=new ArrayList<>();
        StringBuilder rows=null;
        StringBuilder voucherRemove=new StringBuilder();
        Statement stmt = con.createStatement();
        Statement stmt1 = con.createStatement();
        DatabaseMetaData meta = con.getMetaData(); 
        
        ResultSet r = meta.getTables(null, null, "fos", null);
        if(r.next()){
               
                stmt1.execute("select Name from fos");
                ResultSet rs1 = stmt1.getResultSet();
                if(!rs1.next()){
                    return null;
                }
                stmt.execute("select Name from fos");
                ResultSet rs = stmt.getResultSet();
                while((rs!=null) && (rs.next())) {
                    mainFosList.add(rs.getString("Name").trim());
                }
        }
        else{
            return null;
        }
        ResultSet r1 = meta.getTables(null, null, "primary_stock", null);
        if(r1.next()){
                stmt1.execute("select Voucher from primary_stock");
                
                ResultSet rs1 = stmt1.getResultSet();
                
                if(!rs1.next()){
                    return null;
                }
               stmt.execute("select Voucher from primary_stock");
                
                ResultSet rs = stmt.getResultSet();
                while(rs.next()) {
                mainVoucherList.add(rs.getString("Voucher").trim());
                }
        }
        else {
            return null;
        }
        
        ResultSet res1 = meta.getTables(null, null, "stock_inhand", null);
        if(res1.next()){
            
            stmt1.execute("select FOS from stock_inhand");
            ResultSet resultSet = stmt1.getResultSet();
            if(!resultSet.next()){
                return null;
            }
            
            stmt.execute("select Fos from stock_inhand");
            ResultSet rs1 = stmt.getResultSet();
            while((rs1!=null) && (rs1.next())) {
                fosList.add(rs1.getString("Fos").trim());
            }
            
            for(String fos:fosList){
                if(!mainFosList.contains(fos)){
                    removeFosList.add(fos);    
                }
            }

            for(String fos:removeFosList){
               fosList.remove(fos);
               stmt.execute("delete from stock_inhand where FOS='"+fos+"'");
            }
            if(fosList.isEmpty()){
                return null;
            }
            ResultSet columns=meta.getColumns(null, null, "stock_inhand", null);
            String columnNames="";
            while (columns.next()) {
                columnNames = columns.getString("COLUMN_NAME");
                if(!(columnNames.equalsIgnoreCase("FOS") || columnNames.equalsIgnoreCase("Virtual_Topup") || columnNames.equalsIgnoreCase("Total_Amount"))){
                    voucherList.add(Integer.parseInt(columnNames));
                }
            }
            Collections.sort(voucherList);
        
            for(int voucher:voucherList){
                if(!mainVoucherList.contains(String.valueOf(voucher))){
                    removeVoucherList.add(voucher);
                    voucherRemove.append(String.valueOf(voucher)).append(",");
                }
            }
            if(removeVoucherList.size()>0){
                for(int i=0;i<removeVoucherList.size();i++){
                    voucherList.remove((Integer)removeVoucherList.get(i));
                }
                 
            }
            if(voucherRemove.length()>0){
                voucherRemove.deleteCharAt(voucherRemove.length()-1);
                stmt.close();
                stmt1.close();
                con.close();
                Connection c=getConnection();
                Statement s=c.createStatement();
                s.execute("alter table stock_inhand drop column "+voucherRemove.toString()+"");
                s.close();
                c.close();
            }
            if(con.isClosed()){
                con=getConnection();
            }
            Statement stmt2=con.createStatement();
            rows=new StringBuilder("FOS,");
            for(int voucher:voucherList) {
                rows.append(voucher).append(",");
            }
            rows.append("Virtual_Topup,Total_Amount");
            rowList.add(rows.toString());
            for(String fos:fosList){
                
                stmt2.execute("select * from stock_inhand where FOS='"+fos+"'");
                ResultSet rs2 = stmt2.getResultSet();
                if((rs2!=null) && (rs2.next())) {
                    rows=new StringBuilder(fos+",");
                    for(int i=0;i<voucherList.size();i++){
                          rows.append(rs2.getInt(String.valueOf(voucherList.get(i)))).append(",");
                    }
                    rows.append(rs2.getInt("Virtual_Topup")).append(",").append(rs2.getInt("Total_Amount"));
                    rowList.add(rows.toString());
                }
            }
      }
     else {
               return null;
          }
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     return rowList;   
    }
     
    public void returnStock(ArrayList<String> voucherList,ArrayList<String> rowData, String return_date) {
        Connection con=getConnection();
        try {
            StringBuilder column=null;
            StringBuilder columnInsert=null;
            Statement stmt = con.createStatement();
            Statement stmt1 = con.createStatement();
            DatabaseMetaData meta = con.getMetaData();
            ResultSet res = meta.getTables(null, null, "stock_return", null); 
            if(!res.next()){
                column=new StringBuilder();
                for(int i=0;i<voucherList.size();i++){
                   column.append(voucherList.get(i)).append(" Integer,");
                }
                column.deleteCharAt(column.length()-1);
                stmt.execute("create table stock_return(ReturnedDate Integer, FOS char,"+column.toString()+")"); 
              }
            else {
                for(int i=0;i<voucherList.size();i++){
                    ResultSet rs2 = meta.getColumns(null, null, "stock_return", voucherList.get(i));
                    if(!rs2.next()){
                    stmt.execute("ALTER TABLE stock_return ADD COLUMN "+voucherList.get(i)+" Integer");
                    }
                }
              }
            columnInsert=new StringBuilder();
            for(int i=0;i<voucherList.size();i++){
                columnInsert.append(voucherList.get(i)).append(",");
            }
            columnInsert.deleteCharAt(columnInsert.length()-1);
            for(int r=0;r<rowData.size()-1;r++){
                String parts[]=rowData.get(r).split(",");
                stmt.execute("select * from stock_return where ReturnedDate="+return_date+" AND FOS='"+parts[0]+"'");
                ResultSet rs=stmt.getResultSet();
                if(rs.next()){
                        for(int j=1;j<parts.length;j++){
                            if(!parts[j].equals("0")){
                                int updatedAmount=Integer.parseInt(parts[j]) + rs.getInt(voucherList.get(j-1));
                                
                               stmt1.execute("update stock_return set "+voucherList.get(j-1)+"="+updatedAmount+" where ReturnedDate="+return_date+" AND FOS='"+parts[0]+"'");
                                
                            }
                    }
                }
                else {
                    StringBuilder data=new StringBuilder();
                    for(int i=1;i<parts.length;i++){
                        data.append(parts[i]).append(",");
                    }
                    data.deleteCharAt(data.length()-1);
                    stmt.execute("insert into stock_return(ReturnedDate,FOS,"+columnInsert.toString()+") values("+return_date+",'"+parts[0]+"',"+data.toString()+")");
                }
            }
            String [] primaryStock=rowData.get(rowData.size()-1).split(",");
            for(int i=1;i<=voucherList.size()-1;i++){
                stmt.execute("update primary_stock set Quantity="+primaryStock[i]+" where Voucher='"+voucherList.get(i-1)+"'");
            }
              
               
                 
                ResultSet res2 = meta.getTables(null, null, "stock_inhand", null);
               if(!res2.next()){
                column=new StringBuilder();
                for(int i=0;i<voucherList.size();i++){
                   column.append(voucherList.get(i)).append(" Integer,");
                }
                column.deleteCharAt(column.length()-1);
                stmt.execute("create table stock_inhand(FOS char,"+column.toString()+")"); 
              }
            else {
                for(int i=0;i<voucherList.size();i++){
                    ResultSet rs2 = meta.getColumns(null, null, "stock_inhand", voucherList.get(i));
                    if(!rs2.next()){
                    stmt.execute("ALTER TABLE stock_inhand ADD COLUMN "+voucherList.get(i)+" Integer");
                    }
                }
              }
               String[] parts=null;
               int updatedStock=0;
               for(int i=0;i<rowData.size()-1;i++){
                   parts=rowData.get(i).split(",");
                   stmt.execute("select * from stock_inhand where FOS='"+parts[0]+"'");
                   ResultSet rs=stmt.getResultSet();
                   if(!rs.next()){
                       stmt.execute("insert into stock_inhand(FOS) values('"+parts[0]+"')");
                   }
               }
               
               for(int i=0;i<rowData.size()-1;i++){
                   parts=rowData.get(i).split(",");
                   stmt.execute("select * from stock_inhand where FOS='"+parts[0]+"'");
                   ResultSet rs=stmt.getResultSet();
                   if(rs.next()){
                       for(int j=0;j<voucherList.size();j++){
                       updatedStock=rs.getInt(voucherList.get(j))-Integer.parseInt(parts[j+1]);
                       stmt1.execute("update stock_inhand set "+voucherList.get(j)+"="+updatedStock+" where FOS='"+parts[0]+"'");
                    }
                   }
                   
               }
               JOptionPane.showMessageDialog(null,"Stock successfully returned","Stock Assignment",JOptionPane.PLAIN_MESSAGE);
               
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
     
    public void dailyHisab(ArrayList<String> voucherList,ArrayList<String> rowData,ArrayList<String> soldData, long assignment_date) {
        Connection con=getConnection();
        try {
            
            StringBuilder column=null;
            StringBuilder columnInsert=null;
            Statement stmt = con.createStatement();
            Statement stmt1 = con.createStatement();
            DatabaseMetaData meta = con.getMetaData();
            ResultSet res = meta.getTables(null, null, "Total_Sold", null); 
            if(!res.next()){
                column=new StringBuilder();
                for(int i=0;i<voucherList.size()-3;i++){
                   column.append(voucherList.get(i)).append(" Integer,");
                }
                column.deleteCharAt(column.length()-1);
                stmt.execute("create table Total_Sold(AssignedDate Integer, FOS char,"+column.toString()+")"); 
              }
            else {
                for(int i=0;i<voucherList.size()-3;i++){
                    ResultSet rs2 = meta.getColumns(null, null, "Total_Sold", voucherList.get(i));
                    if(!rs2.next()){
                    stmt.execute("ALTER TABLE Total_Sold ADD COLUMN "+voucherList.get(i)+" Integer");
                    }
                }
              }
            columnInsert=new StringBuilder();
            for(int i=0;i<voucherList.size()-3;i++){
                columnInsert.append(voucherList.get(i)).append(",");
            }
            columnInsert.deleteCharAt(columnInsert.length()-1);
            for(int s=0;s<=soldData.size()-1;s++){
                String parts[]=soldData.get(s).split(",");
                stmt.execute("select * from Total_Sold where AssignedDate="+assignment_date+" AND FOS='"+parts[0]+"'");
                ResultSet rs=stmt.getResultSet();
                if(rs.next()){
                        for(int j=1;j<parts.length;j++){
                            if(!parts[j].equals("na")){
                                int updatedAmount=Integer.parseInt(parts[j]) + rs.getInt(voucherList.get(j-1));
                                stmt1.execute("update Total_Sold set "+voucherList.get(j-1)+"="+updatedAmount+" where AssignedDate="+assignment_date+" AND FOS='"+parts[0]+"'");
                                
                            }
                    }
                }
                else {
                    StringBuilder data=new StringBuilder();
                    for(int i=1;i<parts.length;i++){
                         if(parts[i].equals("na")){
                             data.append("0,");
                         }
                         else{
                              data.append(parts[i]).append(",");
                         }
                        
                    }
                    data.deleteCharAt(data.length()-1);
                        stmt.execute("insert into Total_Sold(AssignedDate,FOS,"+columnInsert.toString()+") values("+assignment_date+",'"+parts[0]+"',"+data.toString()+")");
                    
                    
                }
            }    
               
            //Updating Closing_Stock table
            
            ResultSet res1 = meta.getTables(null, null, "Closing_Stock", null); 
            if(!res1.next()){
                column=new StringBuilder();
                for(int i=0;i<voucherList.size();i++){
                   column.append(voucherList.get(i)).append(" Integer,");
                }
                column.deleteCharAt(column.length()-1);
                stmt.execute("create table Closing_Stock(AssignedDate Integer, FOS char,"+column.toString()+")"); 
              }
            else {
                for(int i=0;i<voucherList.size();i++){
                    ResultSet rs1 = meta.getColumns(null, null, "Closing_Stock", voucherList.get(i));
                    if(!rs1.next()){
                    stmt.execute("ALTER TABLE Closing_Stock ADD COLUMN "+voucherList.get(i)+" Integer");
                    }
                }
              }
            columnInsert=new StringBuilder();
            for(int i=0;i<voucherList.size();i++){
                columnInsert.append(voucherList.get(i)).append(",");
            }
            columnInsert.deleteCharAt(columnInsert.length()-1);
            for(int r=0;r<=rowData.size()-1;r++){
                String parts[]=rowData.get(r).split(",");
                stmt.execute("select * from Closing_Stock where AssignedDate="+assignment_date+" AND FOS='"+parts[0]+"'");
                ResultSet rs=stmt.getResultSet();
                if(rs.next()){
                        for(int j=1;j<parts.length-3;j++){
                            if(!parts[j].equals("na")){
                               stmt1.execute("update Closing_Stock set "+voucherList.get(j-1)+"="+parts[j]+" where AssignedDate="+assignment_date+" AND FOS='"+parts[0]+"'");
                                
                            }
                        }
                        for(int j=parts.length-3;j<parts.length-1;j++){
                            if(!parts[j].equals("na")){
                               int updatedAmount=Integer.parseInt(parts[j]) + rs.getInt(voucherList.get(j-1));
                               stmt1.execute("update Closing_Stock set "+voucherList.get(j-1)+"="+updatedAmount+" where AssignedDate="+assignment_date+" AND FOS='"+parts[0]+"'");
                                
                            }
                        }
                        if(!parts[parts.length-1].equals("na")){
                               double updatedAmount=Double.parseDouble(parts[parts.length-1]) + rs.getInt(voucherList.get(parts.length-2));
                               stmt1.execute("update Closing_Stock set "+voucherList.get(parts.length-2)+"="+updatedAmount+" where AssignedDate="+assignment_date+" AND FOS='"+parts[0]+"'");
                                
                            }
 
                }
                else {
                    StringBuilder data=new StringBuilder();
                    for(int i=1;i<parts.length;i++){
                         if(parts[i].equals("na")){
                             data.append("0,");
                         }
                         else{
                              data.append(parts[i]).append(",");
                         }
                        
                    }
                    data.deleteCharAt(data.length()-1);
                        stmt.execute("insert into Closing_Stock(AssignedDate,FOS,"+columnInsert.toString()+") values("+assignment_date+",'"+parts[0]+"',"+data.toString()+")");
                    
                    
                }
            }    
            
            //Updating stock_inhand table
               String[] parts=null;
               for(int i=0;i<rowData.size();i++){
                   parts=rowData.get(i).split(",");
                   stmt.execute("select * from stock_inhand where FOS='"+parts[0]+"'");
                   ResultSet rs=stmt.getResultSet();
                   if(rs.next()){
                       for(int j=0;j<voucherList.size()-3;j++){
                           if(!parts[j+1].equalsIgnoreCase("na")){
                               stmt1.execute("update stock_inhand set "+voucherList.get(j)+"="+Integer.parseInt(parts[j+1])+" where FOS='"+parts[0]+"'");
                           }
                            
                        }
                   }
                   
               }
               
               
               JOptionPane.showMessageDialog(null,"Saved successfully","Daily Hisab",JOptionPane.PLAIN_MESSAGE);
               
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public ArrayList<String> primaryStockReceived(long fromDate, long toDate){
        Connection con=getConnection();
        ArrayList<String> primaryStockList=new ArrayList<>();
        try {        
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "primary_stock_assignment", null); 
            if(!res.next()){
                return null; 
            }
            ArrayList<String> vouchers = new ArrayList<>();
            ResultSet columns = meta.getColumns(null, null, "primary_stock_assignment", null);
            while(columns!=null && columns.next()){
                String col = columns.getString("COLUMN_NAME");
                if(!(col.equalsIgnoreCase("Virtual_Topup")|| col.equalsIgnoreCase("AssignedDate"))){
                    vouchers.add(col.trim());
                }        
            }
            Collections.sort(vouchers);
            vouchers.add("Virtual_Topup");
            StringBuilder sb1 = new StringBuilder("Date,");
            for(String v:vouchers){
                sb1.append(v).append(",");
            }
            sb1.append("Total");
            primaryStockList.add(sb1.toString());
            stmt.execute("select * from primary_stock_assignment where AssignedDate>="+fromDate+" AND AssignedDate<="+toDate+" ");
            ResultSet rs = stmt.getResultSet();
            while((rs!=null) && (rs.next()))
            {
                int total = 0;
                StringBuilder sb = new StringBuilder("");
                sb.append(rs.getString("AssignedDate").trim()).append(",");
                for(String s:vouchers){
                    int amount = Integer.parseInt(rs.getString(s));
                    sb.append(amount).append(",");
                    if(s.equals("Virtual_Topup")){
                        total = total + amount;
                    }
                    else{
                        total = total + Integer.parseInt(s) * amount;
                    }
                    
                }
                sb.append(total);
                primaryStockList.add(sb.toString());
            }
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return primaryStockList;
    }
    public ArrayList<String> reportDateFos(long fromDate, long toDate, String fos, String tableName){
        Connection con=getConnection();
        ArrayList<String> rowList=new ArrayList<>();
        try {        
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, tableName, null); 
            if(!res.next()){ 
                return null; 
            }
            ArrayList<String> vouchers = new ArrayList<>();
            ResultSet columns = meta.getColumns(null, null, tableName, null);
            while(columns!=null && columns.next()){
                String col = columns.getString("COLUMN_NAME");
                if(!(col.equalsIgnoreCase("Virtual_Topup")|| col.equalsIgnoreCase("AssignedDate") || col.equalsIgnoreCase("FOS") || col.equalsIgnoreCase("Total_Amount"))){
                    vouchers.add(col.trim());
                }        
            }
            Collections.sort(vouchers);
            vouchers.add("Virtual_Topup");
            vouchers.add("Total_Amount");
            StringBuilder sb1 = new StringBuilder("Date,");
            for(String v:vouchers){
                sb1.append(v).append(",");
            }
            sb1.deleteCharAt(sb1.length()-1);
            rowList.add(sb1.toString());
            stmt.execute("select * from "+tableName+" where AssignedDate>="+fromDate+" AND AssignedDate<="+toDate+" AND FOS LIKE '%"+fos+"%'");
            ResultSet rs = stmt.getResultSet();
            while((rs!=null) && (rs.next()))
            {
                StringBuilder sb = new StringBuilder("");
                sb.append(rs.getString("AssignedDate").trim()).append(",");
                for(String s:vouchers){
                    String v = rs.getString(s);
                    int amount = 0;
                    if(v!=null){
                        amount = Integer.parseInt(v);
                    }
                    sb.append(amount).append(",");
                }
                sb.deleteCharAt(sb.length()-1);
                rowList.add(sb.toString());
            }
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rowList;
    }
    public ArrayList<String> reportOnlyDate(long fromDate, long toDate, String tableName){
        Connection con=getConnection();
        ArrayList<String> rowList=new ArrayList<>();
        try {        
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, tableName, null); 
            if(!res.next()){
                return null; 
            }
            ArrayList<String> vouchers = new ArrayList<>();
            ResultSet columns = meta.getColumns(null, null, tableName, null);
            while(columns!=null && columns.next()){
                String col = columns.getString("COLUMN_NAME");
                if(!(col.equalsIgnoreCase("Virtual_Topup")|| col.equalsIgnoreCase("AssignedDate") || col.equalsIgnoreCase("FOS") || col.equalsIgnoreCase("Total_Amount"))){
                    vouchers.add(col.trim());
                }        
            }
            Collections.sort(vouchers);
            vouchers.add("Virtual_Topup");
            vouchers.add("Total_Amount");
            StringBuilder sb1 = new StringBuilder("FOS,");
            StringBuilder query = new StringBuilder();
            for(String v:vouchers){
                sb1.append(v).append(",");
                query.append("SUM(`"+v+"`) AS v"+v+",");
            }
            sb1.deleteCharAt(sb1.length()-1);
            rowList.add(sb1.toString());
            stmt.execute("select "+query.toString()+" FOS from "+tableName+" where AssignedDate>="+fromDate+" AND AssignedDate<="+toDate+" group by FOS");
            ResultSet rs = stmt.getResultSet();
            while((rs!=null) && (rs.next()))
            {
            
                StringBuilder sb = new StringBuilder("");
                sb.append(rs.getString("FOS").trim()).append(",");
                for(String s:vouchers){
                    String v = rs.getString("v"+s);
                    int amount = 0;
                    if(v!=null){
                        amount = ((Double)Double.parseDouble(v)).intValue();
                    }
                    sb.append(amount).append(",");
                }
                sb.deleteCharAt(sb.length()-1);
                rowList.add(sb.toString());
            }
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rowList;
    }
    public ArrayList<String> reportOnlyDateClosing(long fromDate, long toDate, String tableName){
        Connection con=getConnection();
        ArrayList<String> rowList=new ArrayList<>();
        try {        
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, tableName, null); 
            if(!res.next()){
                return null; 
            }
            ArrayList<String> vouchers = new ArrayList<>();
            ResultSet columns = meta.getColumns(null, null, tableName, null);
            if(fromDate == toDate){
                while(columns!=null && columns.next()){
                    String col = columns.getString("COLUMN_NAME");
                    if(!(col.equalsIgnoreCase("Virtual_Topup")|| col.equalsIgnoreCase("AssignedDate") || col.equalsIgnoreCase("FOS") || col.equalsIgnoreCase("Total_Amount")||col.equalsIgnoreCase("Cash")||col.equalsIgnoreCase("Total_Sold")||col.equalsIgnoreCase("Difference"))){
                        vouchers.add(col.trim());
                    }        
                }
                Collections.sort(vouchers);
                vouchers.add("Virtual_Topup");
                vouchers.add("Total_Amount");
            }
            
            vouchers.add("Cash");
            vouchers.add("Total_Sold");
            vouchers.add("Difference");
            StringBuilder sb1 = new StringBuilder("FOS,");
            StringBuilder query = new StringBuilder();
            for(String v:vouchers){
                sb1.append(v).append(",");
                query.append("SUM(`"+v+"`) AS v"+v+",");
            }
            sb1.deleteCharAt(sb1.length()-1);
            rowList.add(sb1.toString());
            stmt.execute("select "+query.toString()+" FOS from "+tableName+" where AssignedDate>="+fromDate+" AND AssignedDate<="+toDate+" group by FOS");
            ResultSet rs = stmt.getResultSet();
            while((rs!=null) && (rs.next()))
            {
            
                StringBuilder sb = new StringBuilder("");
                sb.append(rs.getString("FOS").trim()).append(",");
                for(String s:vouchers){
                    String v = rs.getString("v"+s);
                    int amount = 0;
                    if(v!=null){
                        amount = ((Double)Double.parseDouble(v)).intValue();
                    }
                    sb.append(amount).append(",");
                }
                sb.deleteCharAt(sb.length()-1);
                rowList.add(sb.toString());
            }
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rowList;
    }
    public ArrayList<String> reportDateFosClosing(long fromDate, long toDate, String fos, String tableName){
        Connection con=getConnection();
        ArrayList<String> rowList=new ArrayList<>();
        try {        
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, tableName, null); 
            if(!res.next()){ 
                return null; 
            }
            ArrayList<String> vouchers = new ArrayList<>();
            ResultSet columns = meta.getColumns(null, null, tableName, null);
            while(columns!=null && columns.next()){
                String col = columns.getString("COLUMN_NAME");
                if(!(col.equalsIgnoreCase("Virtual_Topup")|| col.equalsIgnoreCase("AssignedDate") || col.equalsIgnoreCase("FOS") || col.equalsIgnoreCase("Total_Amount")||col.equalsIgnoreCase("Cash")||col.equalsIgnoreCase("Total_Sold")||col.equalsIgnoreCase("Difference"))){
                    vouchers.add(col.trim());
                }        
            }
            Collections.sort(vouchers);
            vouchers.add("Virtual_Topup");
            vouchers.add("Total_Amount");
            vouchers.add("Cash");
            vouchers.add("Total_Sold");
            vouchers.add("Difference");
            StringBuilder sb1 = new StringBuilder("Date,");
            for(String v:vouchers){
                sb1.append(v).append(",");
            }
            sb1.deleteCharAt(sb1.length()-1);
            rowList.add(sb1.toString());
            stmt.execute("select * from "+tableName+" where AssignedDate>="+fromDate+" AND AssignedDate<="+toDate+" AND FOS LIKE '%"+fos+"%'");
            ResultSet rs = stmt.getResultSet();
            while((rs!=null) && (rs.next()))
            {
                StringBuilder sb = new StringBuilder("");
                sb.append(rs.getString("AssignedDate").trim()).append(",");
                for(String s:vouchers){
                    String v = rs.getString(s);
                    int amount = 0;
                    if(v!=null){
                        amount = Integer.parseInt(v);
                    }
                    sb.append(amount).append(",");
                }
                sb.deleteCharAt(sb.length()-1);
                rowList.add(sb.toString());
            }
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rowList;
    }
    public boolean addExpense(ArrayList<String> rowList,String date){
        Connection con=getConnection();
        boolean rv = false;
        try {        
            Statement stmt = con.createStatement();
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "expense", null); 
            if(!res.next()){ 
                stmt.execute("create table expense( AssignedDate Integer,FOS char,Salary Integer,Petrol Integer,Other Integer,Total_Expense Integer)");
            } 
            String[] columns={"Salary","Petrol","Other","Total_Expense"};
            for(String row:rowList){
                String[] parts = row.split(",");
                stmt.execute("select * from expense where FOS='"+parts[0]+"' AND AssignedDate="+date+"");
                ResultSet rs = stmt.getResultSet();
                if((rs!=null) && (rs.next())) {
                    for(int j=1;j<parts.length;j++){
                            if(!parts[j].equals("0")){
                                int updatedAmount=Integer.parseInt(parts[j]) + rs.getInt(columns[j-1]);
                                stmt2.execute("update expense set "+columns[j-1]+"="+updatedAmount+" where AssignedDate="+date+" AND FOS='"+parts[0]+"'");                                
                            }
                    }
                }
                else {
                    stmt1.execute("insert into expense(AssignedDate,FOS,Salary,Petrol,Other,Total_Expense) values ("+date+",'"+parts[0]+"',"+parts[1]+","+parts[2]+","+parts[3]+","+parts[4]+")");
                }
            }
            rv = true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return rv;
    
    }
    public ArrayList<String> reportDateFosExpense(long fromDate, long toDate, String fos, String tableName){
        Connection con=getConnection();
        ArrayList<String> rowList=new ArrayList<>();
        try {        
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, tableName, null); 
            if(!res.next()){ 
                return null; 
            }
            ArrayList<String> c = new ArrayList<>();
            ResultSet columns = meta.getColumns(null, null, tableName, null);
            while(columns!=null && columns.next()){
                String col = columns.getString("COLUMN_NAME");
                if(!(col.equalsIgnoreCase("AssignedDate") || col.equalsIgnoreCase("FOS"))){
                    c.add(col.trim());
                }        
            }
            StringBuilder sb1 = new StringBuilder("Date,");
            for(String v:c){
                sb1.append(v).append(",");
            }
            sb1.deleteCharAt(sb1.length()-1);
            rowList.add(sb1.toString());
            stmt.execute("select * from "+tableName+" where AssignedDate>="+fromDate+" AND AssignedDate<="+toDate+" AND FOS LIKE '%"+fos+"%'");
            ResultSet rs = stmt.getResultSet();
            while((rs!=null) && (rs.next()))
            {
                StringBuilder sb = new StringBuilder("");
                sb.append(rs.getString("AssignedDate").trim()).append(",");
                for(String s:c){
                    String v = rs.getString(s);
                    int amount = 0;
                    if(v!=null){
                        amount = Integer.parseInt(v);
                    }
                    sb.append(amount).append(",");
                }
                sb.deleteCharAt(sb.length()-1);
                rowList.add(sb.toString());
            }
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rowList;
    }
    public ArrayList<String> reportOnlyDateExpense(long fromDate, long toDate, String tableName){
        Connection con=getConnection();
        ArrayList<String> rowList=new ArrayList<>();
        try {        
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, tableName, null); 
            if(!res.next()){
                return null; 
            }
            ArrayList<String> c = new ArrayList<>();
            ResultSet columns = meta.getColumns(null, null, tableName, null);
            while(columns!=null && columns.next()){
                String col = columns.getString("COLUMN_NAME");
                if(!(col.equalsIgnoreCase("AssignedDate") || col.equalsIgnoreCase("FOS"))){
                    c.add(col.trim());
                }        
            }
            StringBuilder sb1 = new StringBuilder("FOS,");
            StringBuilder query = new StringBuilder();
            for(String v:c){
                sb1.append(v).append(",");
                query.append("SUM(`"+v+"`) AS v"+v+",");
            }
            sb1.deleteCharAt(sb1.length()-1);
            rowList.add(sb1.toString());
            stmt.execute("select "+query.toString()+" FOS from "+tableName+" where AssignedDate>="+fromDate+" AND AssignedDate<="+toDate+" group by FOS");
            ResultSet rs = stmt.getResultSet();
            while((rs!=null) && (rs.next()))
            {
            
                StringBuilder sb = new StringBuilder("");
                sb.append(rs.getString("FOS").trim()).append(",");
                for(String s:c){
                    String v = rs.getString("v"+s);
                    int amount = 0;
                    if(v!=null){
                        amount = ((Double)Double.parseDouble(v)).intValue();
                    }
                    sb.append(amount).append(",");
                }
                sb.deleteCharAt(sb.length()-1);
                rowList.add(sb.toString());
            }
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rowList;
    }
    public ArrayList<String> reportOnlyDateProfit(long fromDate, long toDate){
        Connection con=getConnection();
        ArrayList<String> rowList=new ArrayList<>();
        try {        
            Statement stmt = con.createStatement();
            DatabaseMetaData meta = con.getMetaData(); 
            ResultSet res = meta.getTables(null, null, "Closing_Stock", null); 
            if(!res.next()){
                return null; 
            }
            rowList.add("FOS,Total Sold,Total Expenses,Net Profit");
            stmt.execute("select SUM(Total_Sold) AS TS,FOS from Closing_Stock where AssignedDate>="+fromDate+" AND AssignedDate<="+toDate+" group by FOS");
            ResultSet rs = stmt.getResultSet();
            HashMap totalSold = new HashMap();
            while((rs!=null) && (rs.next()))
            {
                String fos = rs.getString("FOS").trim();
                String ts = rs.getString("TS");
                int amount = 0;
                if(ts!=null){
                    amount = ((Double)Double.parseDouble(ts)).intValue();
                }
                totalSold.put(fos, amount);
            }
            ResultSet res1 = meta.getTables(null, null, "expense", null);
            HashMap totalExpense = new HashMap();
            if(res1.next()){
                stmt.execute("select SUM(Total_Expense) AS TE,FOS from expense where AssignedDate>="+fromDate+" AND AssignedDate<="+toDate+" group by FOS");
                ResultSet rs1 = stmt.getResultSet();
                while((rs1!=null) && (rs1.next()))
                {
                    String fos = rs.getString("FOS").trim();
                    String te = rs.getString("TE");
                    int expense = 0;
                    if(te!=null){
                        expense = ((Double)Double.parseDouble(te)).intValue();
                    }
                    totalExpense.put(fos, expense);
                }
            }
            
            for(Object fos: totalSold.keySet()){
                StringBuilder sb = new StringBuilder();
                int ts = (int)totalSold.get(fos);
                int te = 0;
                if(totalExpense.size()>0 && totalExpense.get(fos)!=null){
                    te = (int) totalExpense.get(fos);
                }
                sb.append(fos).append(",").append(ts).append(",").append(te).append(",").append(ts-te);
                
                rowList.add(sb.toString());
            }
            
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(con!=null) {
                    con.close();
                }                
            } catch (Exception ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rowList;
    }
}