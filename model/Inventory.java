/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author laron
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    //methods
    public static void addPart(Part newPart) {
        
        allParts.add(newPart);
        
    }
    
    public static void addProduct(Product newProduct) {
        
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart(int partID) {
        
        for(Part part : Inventory.allParts) {
            
             if(part.getId() == partID)
                return part;       
        }
        
        return null;
    }
    
    public static Product lookupProduct(int productID) {

        for(Product product : Inventory.allProducts) {
            
            if(product.getId() == productID)
                return product;                        
        }
        
        return null;
    }
    
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        for(Part part : getAllParts()) {
            
            if(part.getName().contains(partName)){
                searchResults.add(part);
                return searchResults;            
            }
        }
        
        return searchResults;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
 
        
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        for(Product product : Inventory.getAllProducts()) {
            
            if(product.getName().contains(productName)) {
                searchResults.add(product);
                return searchResults;            
            }
        }
        
        return searchResults;
    }
    
    public static void updatePart(int index, Part selectedPart) {
        
        for(int i = 0; i < allParts.size(); i++) {
            
            if(allParts.get(i).getId() == selectedPart.getId()) {
                allParts.set(index, selectedPart);
            }
        }
        
    }
    
    public static void updateProduct(int index, Product selectedProduct) {
        
        for(int i = 0; i < allProducts.size(); i++){
            
            if(allProducts.get(i).getId() == selectedProduct.getId()) {
                allProducts.set(index, selectedProduct);
            }
        }
        
    }
    
    public static boolean deletePart(Part selectedPart) {
        
        for(Part part : Inventory.getAllParts()) {
            
            if(part == selectedPart){
                return Inventory.getAllParts().remove(selectedPart);
            }
        }
        return false;    
    }
    
    public static boolean deleteProduct(Product selectedProduct) {
        
        for(Product product : Inventory.getAllProducts()) {
            
            if(product == selectedProduct) {
                return Inventory.getAllProducts().remove(selectedProduct);
            }
        }
        return false;    
    }

    public static ObservableList<Part> getAllParts() {
        
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        
        return allProducts;
    }
    
}
