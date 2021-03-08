/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author laron
 */
public class ModifyProductController implements Initializable {
    
    //containers
    Stage stage;
    Parent scene;    

    //fields
    Inventory stock;
    Product prod;
    
    //User input
    @FXML
    private TextField productIdTxt;
    @FXML
    private TextField productNameTxt;
    @FXML
    private TextField inventoryTxt;
    @FXML
    private TextField costTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TextField maxTxt;    
    @FXML
    private TextField searchTxt;

    //ALL parts table
    @FXML
    private TableView<Part> tableViewTop;
    @FXML
    private TableColumn<Part, Integer> partIdTopCol;
    @FXML
    private TableColumn<Part, String> partNameTopCol;
    @FXML
    private TableColumn<Part, Integer> inventoryTopCol;
    @FXML
    private TableColumn<Part, Double> costTopCol;

    //Associated parts table
    @FXML
    private TableView<Part> TableViewBottom;
    @FXML
    private TableColumn<Part, Integer> partIdBtmCol;
    @FXML
    private TableColumn<Part, String> partNameBtmCol;
    @FXML
    private TableColumn<Part, Integer> inventoryBtmCol;
    @FXML
    private TableColumn<Part, Double> costBtmCol;
    
    private ObservableList<Part> aPartList = FXCollections.observableArrayList();
    private ObservableList<Part> partSearch = FXCollections.observableArrayList();    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        //populate ALL part table data
        tableViewTop.setItems(Inventory.getAllParts());
                
        //populate items in cells of parts table
        partIdTopCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTopCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryTopCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        costTopCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //populate items in cells of product table
        partIdBtmCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameBtmCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryBtmCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        costBtmCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    //Populates product data and associated part(s) data
    public void sendProduct(Product product) {
        
        productIdTxt.setText(String.valueOf(product.getId()));
        productNameTxt.setText(product.getName());
        inventoryTxt.setText(String.valueOf(product.getStock()));
        costTxt.setText(String.valueOf(product.getPrice()));
        minTxt.setText(String.valueOf(product.getMin()));
        maxTxt.setText(String.valueOf(product.getMax()));
    
        aPartList = product.getAllAssociatedParts();
        TableViewBottom.setItems(aPartList);
    }
   
    //User searches all parts
    @FXML
    private void onActionSearchProduct(ActionEvent event) {
        
        
        String searchTerm = searchTxt.getText().trim();
        boolean noMatch = true;
        
        if(searchTerm.isEmpty()) {
            
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning: Part Search");
                alert.setHeaderText("No Part(s) found!");
                alert.setContentText("Please search using a part ID or part name.");
                alert.showAndWait();        
        }
        else {

            try{
                
                partSearch.clear();
                
                Part searchId = Inventory.lookupPart(Integer.parseInt(searchTerm));
                if(searchId != null) {
                    
                    partSearch.add(searchId);
                    tableViewTop.setItems(partSearch);
                }
                else {
                    
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning: Part Search");
                    alert.setHeaderText("No Part(s) found!");
                    alert.setContentText("No matching results.");
                    alert.showAndWait();                      
                }
            }
            catch(NumberFormatException e) {
                
                partSearch = Inventory.lookupPart(searchTerm);
                if(partSearch != null) {
                    
                    noMatch = false;
                    tableViewTop.setItems(partSearch);
                }
                
                if(noMatch == true) {
                    
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("No matches!");
                    alert.setContentText("Please check your search term and try again!");
                    alert.showAndWait();                    
                }                
            }
        }        
    }

    //User adds part to table of parts associated with product
    @FXML
    private void onActionAddProduct(ActionEvent event) {
        
        try {
            
            Part part = tableViewTop.getSelectionModel().getSelectedItem();        
            aPartList.add(part);
            TableViewBottom.setItems(aPartList);            
        }
        
        catch(NullPointerException e) {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a part to continue.");
            alert.showAndWait();
        }        
    }

    //User clicks delete button to delete part(s) associated with product
    @FXML
    private void onActionDeleteProduct(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will DELETE the associated part(s). Do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {
            
            Part deleteAssocPart = TableViewBottom.getSelectionModel().getSelectedItem();
            aPartList.remove(deleteAssocPart);
        }       
    }

    //User clicks save button
    @FXML
    private void onActionSaveProduct(ActionEvent event) throws IOException {        
        
        try {
            
            int id = Integer.parseInt(productIdTxt.getText());;
            String name = productNameTxt.getText();
            int stock = Integer.parseInt(inventoryTxt.getText());
            double price = Double.parseDouble(costTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText()); 
            int index = Integer.parseInt(productIdTxt.getText()) - 1;
            
            if(min <= max) {
                
                Product newProduct = new Product(id, name, price, stock, min, max);

                Inventory.updateProduct(index, newProduct);

                for(int i = 0; i < aPartList.size(); i++) {

                        newProduct.addAssociatedPart(aPartList.get(i));

                }

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();                 
            }
            else {
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Min value cannot be greater than Max value.");
                alert.showAndWait();                
            }            
            
        }
        
        catch(NumberFormatException e) {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();            
        }
    }

    //User clicks cancel button
    @FXML
    private void onActionDisplayMainMenu(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values. Do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();         
        }            
    }
    
}
