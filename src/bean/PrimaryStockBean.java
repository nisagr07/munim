/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author nishant
 */
public class PrimaryStockBean {
    private String voucher;
    private int quantity;
    public String getVoucher(){
        return voucher;
    }
    public void setVoucher(String voucher){
        this.voucher=voucher;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
}
