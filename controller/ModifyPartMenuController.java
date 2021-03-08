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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author laron
 */
public class ModifyPartMenuController implements Initializable {
    
    //containers
    Stage stage;
    Parent scene;

    //FXML annotations
    @FXML
    private TextField partIdTxt;
    @FXML
    private TextField partNameTxt;

    //User input
    @FXML
    private TextField inventoryTxt;
    @FXML
    private TextField costTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TextField maxTxt;
    
    //Radio buttons
    @FXML
    private Label lblToggle;
    @FXML
    private TextField txtToggle;        
    @FXML
    private RadioButton inHouseRBtn;
    @FXML
    private RadioButton outsourcedRBtn;        
    @FXML
    private ToggleGroup partCategory;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
    }
    
    //Populates input from part selected on main screen
    public void sendPart(Part part) {
        
        partIdTxt.setText(String.valueOf(part.getId()));
        partNameTxt.setText(part.getName());
        inventoryTxt.setText(String.valueOf(part.getStock()));
        costTxt.setText(String.valueOf(part.getPrice()));
        minTxt.setText(String.valueOf(part.getMin()));
        maxTxt.setText(String.valueOf(part.getMax()));
             
        if(part instanceof Outsourced) {
            
            outsourcedRBtn.setSelected(true);
            lblToggle.setText("Company Name");
            txtToggle.setText(((Outsourced) part).getCompanyName()); 
        }
        else {
            
            inHouseRBtn.setSelected(true);
            lblToggle.setText("Machine ID");
            txtToggle.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
   
    }
    
    //user selects Outsourced radio button
    @FXML
    void onActionDisplayCompanyName(ActionEvent event) {
        
            if(outsourcedRBtn.isSelected()) {
                
                lblToggle.setText("Company Name");
                txtToggle.setText("Company Name");                
            }

    }

    //user selects In-House radio button
    @FXML
    void onActionDisplayMachineID(ActionEvent event) {
        
            if(inHouseRBtn.isSelected()) {
                
                lblToggle.setText("Machine ID");
                txtToggle.setText("Machine ID");                   
            }
     
    }

    //User clicks Cancel button
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values. Do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();         
        }          
    }

    //User clicks save button
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        try{
            
            int id = Integer.parseInt(partIdTxt.getText());
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(inventoryTxt.getText());
            double price = Double.parseDouble(costTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            String companyName;
            int machineId;
            int index = Integer.parseInt(partIdTxt.getText()) - 1;
            
            if(min <= max) {
                
                if(outsourcedRBtn.isSelected()) {

                    companyName = txtToggle.getText();
                    Inventory.updatePart(index, new Outsourced(id, name, price, stock, min, max, companyName));

                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();  
                }    
                else {

                    machineId = Integer.parseInt(txtToggle.getText());
                    Inventory.updatePart(index, new InHouse(id, name, price, stock, min, max, machineId));

                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();  
                }                     
            }
            else {
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Min value cannot be greater than Max value.");
                alert.showAndWait();                  
            }
            
                       
        }
        
        catch(NumberFormatException e){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();
        }        
    }    
}
