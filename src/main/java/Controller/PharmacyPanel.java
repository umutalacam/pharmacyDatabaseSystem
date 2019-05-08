package Controller;

import Database.Record;
import Unit.Drug;
import Unit.Inventory;
import Unit.InventoryContent;
import Unit.Pharmacy;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;

import static Database.Login.currentUser;


public class PharmacyPanel extends Application implements Initializable {


    public Label pharmacyNameLabel;
    public Label pharmacyTelephoneLabel;
    public Button inventoriesAddButton;
    public Button inventoriesDeleteButton;
    public Button inventoriesEditButton;

    public TableView<Drug> drugsTableView;
    public TableColumn drugsNameColumn;
    public TableColumn drugsDoseColumn;
    public TableColumn drugsPrescribeColumn;
    public TableColumn drugsManufacturerColumn;
    public TableColumn drugsPriceColumn;

    public TableView<Inventory> inventoriesTableView;
    public TableColumn<Inventory, String> inventoriesNameColumn;
    public TableColumn<Inventory, String> inventoriesDrugsInStockColumn;

    public TableView<InventoryContent> inventoriesContentTableView;
    public TableColumn<InventoryContent, String> inventoriesDrugNameColumn;
    public TableColumn<InventoryContent, String> inventoriesDoseColumn;
    public TableColumn<InventoryContent, String> inventoriesManufacturerColumn;
    public TableColumn<InventoryContent, String> inventoriesExpiryDateColumn;
    public TableColumn<InventoryContent, Integer> inventoriesQuantityColumn;
    public TableColumn<InventoryContent, Double> inventoriesPriceColumn;

    public Button drugsAddButton;
    public Button drugsDeleteButton;
    public Button drugsSellButton;
    public Button pharmacyEditProfileButton;

    //Lists
    private ObservableList<Inventory> inventoriesList;
    private ObservableList<InventoryContent> inventoriesContentList;

    private Pharmacy currentPharmacy;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentPharmacy = (Pharmacy) currentUser;
        pharmacyNameLabel.setText(currentPharmacy.getName());
        pharmacyTelephoneLabel.setText(currentPharmacy.getTelephone());

        initInventoriesTable();
        initInventoriesContentTable();

    }

    public void initInventoriesTable(){
        //Set columns value factory
        inventoriesNameColumn.setCellValueFactory(new PropertyValueFactory<Inventory, String>("inventoryName"));
        inventoriesDrugsInStockColumn.setCellValueFactory(new PropertyValueFactory<Inventory, String>("drugsInStock"));
        //Set table values
        inventoriesList = FXCollections.observableArrayList();
        inventoriesTableView.setItems(inventoriesList);
        loadInventories();
        inventoriesTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Inventory selectedInventory = inventoriesTableView.getSelectionModel().getSelectedItem();
                loadInventoryContents(selectedInventory.getInventoryId());
            }
        });
    }

    public void initInventoriesContentTable(){
        //Set columns value factory
        inventoriesDrugNameColumn.setCellValueFactory(new PropertyValueFactory<>("drugName"));
        inventoriesDoseColumn.setCellValueFactory(new PropertyValueFactory<>("dose"));
        inventoriesManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
        inventoriesExpiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        inventoriesQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        inventoriesPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Set table values
        inventoriesContentList = FXCollections.observableArrayList();
        inventoriesContentTableView.setItems(inventoriesContentList);

    }

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

    private void loadInventories(){
        //Clear table
        inventoriesTableView.getItems().clear();
        //Select from database
        String sql = "SELECT inv_id, phar_id, inv_name, drugs_in_stock FROM " +
                "Inventory LEFT JOIN " +
                "(SELECT inv_id as Tinv_id, COUNT(inv_id) as drugs_in_stock FROM InventoryContains GROUP BY(Tinv_id)) AS T " +
                "ON (Inventory.inv_id = T.Tinv_id) WHERE phar_id = ?";

        Connection conn = Database.Connector.connect();
        if (conn == null) return;
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentPharmacy.getPhar_id());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                inventoriesList.add(new Inventory(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getInt(4)));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        inventoriesTableView.setItems(inventoriesList);
    }


    public void loadInventoryContents(int selectedInventoryIndex){
        //Clear table
        inventoriesContentTableView.getItems().clear();

        //Complex query
        String sql = "SELECT drug_name, dose, manufacturer_name, expiry_date, quantity, price FROM " +
                "InventoryContains JOIN " +
                "( select drug_id, Drug.name as drug_name, dose, Manufacturer.name as manufacturer_name from Drug JOIN Manufacturer ON (Drug.man_id=Manufacturer.man_id) ) AS T " +
                "ON (InventoryContains.drug_id = T.drug_id) WHERE  inv_id = ?";

        Connection conn = Database.Connector.connect();
        if (conn == null){
            return;
        }
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, selectedInventoryIndex);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                inventoriesContentList.add(new InventoryContent(
                        rs.getString(1), rs.getInt(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getDouble(6)
                ));
            }
            conn.close();
            inventoriesContentTableView.setItems(inventoriesContentList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Inventory table toolbar actions
     * **/
    public void inventoryAddAction(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Inventory");
        dialog.setHeaderText("Add New Inventory");
        dialog.setContentText("New inventory name:");
        dialog.showAndWait();

        String inventoryNameInput = dialog.getEditor().getText();

        boolean add = Record.addNewInventory(new Inventory(currentPharmacy.getPhar_id(), inventoryNameInput));

        if (add){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Inventory add");
            alert.setHeaderText(null);
            alert.setContentText("Inventory added succesfully");
            alert.showAndWait();
            loadInventories();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory add");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add inventory");
            alert.showAndWait();
        }
    }

    public void inventoryDeleteAction(){
        Inventory selectedInventory = inventoriesTableView.getSelectionModel().getSelectedItem();
        if (selectedInventory == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Inventory");
            alert.setHeaderText(null);
            alert.setContentText("Please select an inventory");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Inventory");
        alert.setHeaderText("Delete Inventory");
        alert.setContentText("Are you sure that you really want to delete "+ selectedInventory);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Record.deleteInventory(selectedInventory.getInventoryId());
            loadInventories();
        } else {
            //Nothing
        }
    }

    public void inventoryEditAction(){
        Inventory selectedInventory = inventoriesTableView.getSelectionModel().getSelectedItem();
        if (selectedInventory == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Edit Inventory");
            alert.setHeaderText(null);
            alert.setContentText("Please select an inventory");
            alert.showAndWait();
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Inventory");
        dialog.setHeaderText("Edit Inventory");
        dialog.setContentText("Inventory name:");
        dialog.getEditor().setText(selectedInventory.getInventoryName());
        dialog.showAndWait();

        String inventoryNameInput = dialog.getEditor().getText();
        boolean update = Record.updateInventory(new Inventory(selectedInventory.getInventoryId(),
                            currentPharmacy.getPhar_id(),
                            inventoryNameInput));

        if (!update){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Edit");
            alert.setHeaderText(null);
            alert.setContentText("Failed to change inventory name");
            alert.showAndWait();
            return;
        }
        loadInventories();
    }



}

