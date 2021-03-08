/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
/**
 *
 * @author laron
 */
public class Outsourced extends Part {
    
    //field
    String companyName;
    
    //constructor
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    //getter and setter
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
}
