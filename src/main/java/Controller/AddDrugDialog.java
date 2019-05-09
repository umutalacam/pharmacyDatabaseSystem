package Controller;

import Unit.Drug;
import Unit.Inventory;
import Unit.Pharmacy;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AddDrugDialog extends Application implements Initializable {
    public TextField searchTextField;
    public Button searchButton;
    public Button addDrugAddButton;
    public Button addDrugCancelButton;
    public TableView<Drug> drugSearchTableView;
    public TableColumn<Drug, String>drugSearchNameColumn;
    public TableColumn<Drug, Integer> drugSearchDoseColumn;
    public TableColumn<Drug, Integer> drugSearchPrescribeColumn;
    public TableColumn<Drug, String> drugSearchManufacturerColumn;
    public ChoiceBox<Inventory> inventoryChoiceBox;
    public TextField quantityTextField;
    public DatePicker addDrugExpiryDatePicker;
    public TextField priceTextField;

    //drugsList
    private ObservableList<Drug> drugList;
    private ObservableList<Inventory> inventoryList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      initSearchTable();
      inventoryList = FXCollections.observableArrayList();
      inventoryChoiceBox.converterProperty();
      loadInventories();

      searchTextField.textProperty().addListener(new ChangeListener<String>() {
          @Override
          public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
              searchDrugAction();
          }
      });
    }

    private void  initSearchTable(){
        drugSearchNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        drugSearchDoseColumn.setCellValueFactory(new PropertyValueFactory<>("dose"));
        drugSearchPrescribeColumn.setCellValueFactory(new PropertyValueFactory<>("prescribeLevel"));
        drugSearchManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        drugList = FXCollections.observableArrayList();
        drugSearchTableView.setItems(drugList);
        loadDrugs();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/addDrugDialog.fxml"));
        stage.setTitle("Add New Drug");
        double height = 460;
        double width = 600;

        stage.setScene(new Scene(root, width, height));
        stage.setResizable(false);

        //Center to the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); //Get screen dimensions
        stage.setX((dimension.getWidth()-width)/2); //set width and height
        stage.setY((dimension.getHeight()-height)/2);
        stage.show();
    }

    private void loadDrugs(){
        drugSearchTableView.getItems().clear();
        //Prepare query
        String sql = "SELECT drug_id, name, dose, prescribe_level, manufacturer FROM " +
                "Drug JOIN (SELECT man_id, name as manufacturer FROM Manufacturer) AS T ON (Drug.man_id = T.man_id)";

        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) return;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs =stmt.executeQuery();
            while (rs.next()){
                int drug_id = rs.getInt(1);
                String name = rs.getString(2);
                int dose = rs.getInt(3);
                int prescribe = rs.getInt(4);
                String manufacturer = rs.getString(5);
                drugList.add(new Drug(drug_id, name, dose,0, prescribe,null, manufacturer));

            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        drugSearchTableView.setItems(FXCollections.observableArrayList(drugList));

    }

    private void loadInventories(){
        //Prepare sql query
        String sql = "SELECT inv_id, inv_name FROM Inventory WHERE phar_id = ? ORDER BY(inv_name)";
        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) return;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Database.Login.getCurrentPharmacy().getPhar_id());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                inventoryList.add(new Inventory(rs.getInt(1), 1, rs.getString(2)));
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        inventoryChoiceBox.setItems(inventoryList);
    }

    private ObservableList<Drug> searchDrug(String query){
        if (query.equals("")) return FXCollections.observableArrayList(drugList);

        ObservableList<Drug> filteredObservableList = FXCollections.observableArrayList();
        drugList.stream()
                .filter(drug -> drug.getName().toLowerCase().contains(query.toLowerCase()))
                .forEach(filteredObservableList::add);
        return filteredObservableList;
    }

    /**
     * Button actions
     */

    public void searchDrugAction(){
        String query = searchTextField.getText();
        ObservableList<Drug> filteredList = searchDrug(query);
        drugSearchTableView.getItems().clear();
        drugSearchTableView.setItems(filteredList);

    }

    public void addDrugAction(){
        Inventory selectedInventory = inventoryChoiceBox.getSelectionModel().getSelectedItem();
        int drug_id = drugSearchTableView.getSelectionModel().getSelectedItem().getDrugId();
        int quantity;
        Date expiry_date = Date.valueOf(addDrugExpiryDatePicker.getValue());
        double price;

        try{
            quantity = Integer.parseInt(quantityTextField.getText());
            if (quantity <= 0) throw new ArithmeticException();

            price = Double.parseDouble(priceTextField.getText());
            if (price < 0) throw new ArithmeticException();

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Value Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid values");
            alert.showAndWait();
            return;
        }

        selectedInventory.addDrug(drug_id,quantity,price,expiry_date);
        cancelAction();

    }

    public void cancelAction(){
        Stage current = (Stage) addDrugCancelButton.getScene().getWindow();
        current.close();

    }
}
