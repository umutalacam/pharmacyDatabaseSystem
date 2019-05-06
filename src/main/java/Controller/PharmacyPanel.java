package Controller;

import Unit.Drug;
import Unit.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.awt.*;


public class PharmacyPanel extends Application {

    public Button inventoriesAddButton;
    public TableView<Drug> drugsTableView;
    public TableView<Inventory> inventoriesTableView;
    public TableView<Inventory> inventoriesContentTableView;
    public TableColumn<Inventory, String> inventoriesNameColumn;
    public TableColumn<Inventory, String> inventoriesDurgsInStockColumn;
    public TableColumn<Inventory, String> inventoriesDrugNameColumn;
    public TableColumn<Inventory, String> inventoriesDoseColumn;


    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pharmacyPanel.fxml"));
        stage.setTitle("G24 Pharmacy Manager");
        double height = 640;
        double width = 970;

        stage.setScene(new Scene(root, width, height));

        //Center to the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); //Get screen dimensions
        stage.setX((dimension.getWidth()-width)/2); //set width and height
        stage.setY((dimension.getHeight()-height)/2);

        stage.show();
    }
}
