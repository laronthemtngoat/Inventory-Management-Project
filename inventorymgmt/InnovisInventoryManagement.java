/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorymgmt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;
import model.Part;

/**
 *
 * @author laron
 */
public class InnovisInventoryManagement extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Part objects - test data
        //InHouse(int id, String name, double price, int stock, int min, int max, int machineID)
        //Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
        InHouse part1 = new InHouse(1, "abc", 1.00, 1, 1, 3, 123);
        Outsourced part2 = new Outsourced(2, "def", 2.00, 2, 2, 4, "Test Inc.");
        
        
        //Product objects - test data
        //Product(int id, String name, double price, int stock, int min, int max)
        Product product1 = new Product(1, "Pname", 2.50, 3, 2, 5);
        
        //Populate tables - test data
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        
        Inventory.addProduct(product1);
        
        launch(args);
    }
    
}
