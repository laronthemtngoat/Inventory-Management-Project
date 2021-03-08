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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author laron
 */
public class MainMenuController implements Initializable {

    
    //containers
    Stage stage;
    Parent scene;
    
    //@FXML annotations
    //Parts table and columns    
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partidCol;
    @FXML
    private TableColumn<Part, String> partnameCol;
    @FXML
    private TableColumn<Part, Integer> partinventorycountCol;
    @FXML
    private TableColumn<Part, Double> costperunitCol;

    //Products table and columns
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> productidCol;
    @FXML
    private TableColumn<Product, String> productnameCol;
    @FXML
    private TableColumn<Product, Integer> productinventorycountCol;
    @FXML
    private TableColumn<Product, Double> priceperunitCol;    
 
    //Radio buttons
    @FXML
    private ToggleGroup inventoryType;   
    @FXML
    private RadioButton partRBtn;
    @FXML
    private RadioButton productRBtn;

    @FXML
    private TextField searchTxt;
    private ObservableList<Part> partSearch = FXCollections.observableArrayList();
    private ObservableList<Product> productSearch = FXCollections.observableArrayList();    

 
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //populate items in tables
        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());
        
        //populate items in cells of parts table
        partidCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partinventorycountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        costperunitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //populate items in cells of product table
        productidCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productnameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productinventorycountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceperunitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    //User clicks button to delete Part(s)
    @FXML
    void onActionDeletePart(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will DELETE the selected part(s). Do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {

            Part deletePart = partTableView.getSelectionModel().getSelectedItem();
            Inventory.deletePart(deletePart); 
        }
    }

    //User clicks button to delete Product(s)
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will DELETE the selected product(s). Do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {
            
            Product deleteProduct = productTableView.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(deleteProduct);
        }        
    }

    //User click Add Part Menu button
    @FXML
    void onActionDisplayAddPartMenu(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();        
        
    }

    //User clicks Add Product Menu button
    @FXML
    void onActionDisplayAddProductMenu(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();         

    }

    //User clicks Modify Part button; part they select populates in next screen
    @FXML
    void onActionDisplayModifyPartMenu(ActionEvent event) throws IOException {
        
        try {                     
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartMenu.fxml"));
            Parent load = loader.load();
            
            ModifyPartMenuController modifyPartController = loader.getController();
            modifyPartController.sendPart(partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyPartMenu.fxml"));
            stage.setScene(new Scene(load));
            stage.show();                
        }
        
        catch(NullPointerException e) {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a part to continue.");
            alert.showAndWait();
        }               
    }

    //User clicks Modify Product button
    @FXML
    void onActionDisplayModifyProductMenu(ActionEvent event) throws IOException {
        
        try {                     
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductMenu.fxml"));
            Parent load = loader.load();
            
            ModifyProductController modifyProductController = loader.getController();
            modifyProductController.sendProduct(productTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductMenu.fxml"));
            stage.setScene(new Scene(load));
            stage.show();                
        }
        
        catch(NullPointerException e) {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a part to continue.");
            alert.showAndWait();
        }                                 
    }

    //User clicks on Exit button
    @FXML
    void onActionExit(ActionEvent event) {
        
        System.exit(0);        

    }

    //User clicks on search button
    @FXML
    void onActionSearch(ActionEvent event) {
        
        String searchTerm = searchTxt.getText().trim();        
        boolean noMatch = false;
        partSearch.clear();
        productSearch.clear();
        
        if(searchTerm.isEmpty()) {
            
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning: Part Search");
                alert.setHeaderText("No Part(s) found!");
                alert.setContentText("Please search using a part ID or part name.");
                alert.showAndWait();        
        }
        else {
            try {
                                
                if(partRBtn.isSelected()) {
                                        
                    Part searchId = Inventory.lookupPart(Integer.parseInt(searchTerm));
                    if(searchId != null) {
                        
                        partSearch.add(searchId);
                        partTableView.setItems(partSearch);
                    }
                    else {
                                                
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning: Part Search");
                        alert.setHeaderText("No Part(s) found!");
                        alert.setContentText("No matching results.");
                        alert.showAndWait();    
                    }
                }
                
                if(productRBtn.isSelected()) {
                    
                    Product searchId = Inventory.lookupProduct(Integer.parseInt(searchTerm));
                    if(searchId != null) {
                        
                        productSearch.add(searchId);
                        productTableView.setItems(productSearch);
                    }
                    else{
                        
                        
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning: Product Search");
                        alert.setHeaderText("No Product(s) found!");
                        alert.setContentText("No matching results.");
                        alert.showAndWait();                          
                    }
                }
            }
            catch(NumberFormatException e) {
                
                if(partRBtn.isSelected()) {
                    
                    partSearch = Inventory.lookupPart(searchTerm);
                    if(partSearch != null) {
                        noMatch = false;
                        partTableView.setItems(partSearch);
                    }
                }
                                
                if(productRBtn.isSelected()) {
                    
                    productSearch = Inventory.lookupProduct(searchTerm);
                    if(productSearch != null) {
                        
                        noMatch = false;
                        productTableView.setItems(productSearch);
                    }                    
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
}
