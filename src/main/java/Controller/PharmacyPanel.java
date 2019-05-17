package Controller;

import Database.Record;
import Unit.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.print.attribute.standard.JobStateReason;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static Database.Login.currentUser;


public class PharmacyPanel extends Application implements Initializable {


    public Label pharmacyNameLabel;
    public Label pharmacyTelephoneLabel;
    public Button pharmacyEditProfileButton;

    public Button inventoriesAddButton;
    public Button inventoriesDeleteButton;
    public Button inventoriesEditButton;
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
    public TableView<Drug> drugsTableView;
    public TableColumn<Drug, String> drugsNameColumn;
    public TableColumn<Drug, Integer> drugsDoseColumn;
    public TableColumn<Drug, String> drugsPrescribeColumn;
    public TableColumn<Drug, String> drugsManufacturerColumn;
    public TableColumn<Drug, Double> drugsPriceColumn;
    public TableColumn<Drug, Integer> drugsMinAgeColumn;
    public TableColumn<Drug, String> drugsInventoryColumn;
    public TableColumn<Drug, Integer> drugsQuantityColumn;

    public TextField patientSearchField;
    public Button patientSearchButton;
    public TableView<Patient> sellDrugPatientsTableView;
    public TableColumn<Patient, String> patientsFirstNameColumn;
    public TableColumn<Patient, String> patientsLastNameColumn;
    public TableColumn<Patient, String> patientsSexColumn;
    public TableColumn<Patient, Integer> patientsAgeColumn;
    public TableColumn<Patient, String> patientsTelephoneColumn;

    public TextField drugSearchField;
    public Button drugSearchButton;
    public TableView<Drug> sellDrugDrugsTableView;
    public TableColumn<Drug, String> sellDrugNameColumn;
    public TableColumn<Drug, String> sellDrugPrescribeColumn;
    public TableColumn<Drug, Integer> sellDrugDoseColumn;
    public TableColumn<Drug, Integer> sellDrugMinAgeColumn;
    public TableColumn<Drug, String> sellDrugManufacturerColumn;
    public TableColumn<Drug, Double> sellDrugPriceColumn;
    public TableColumn<Drug, String> sellDrugInventoryColumn;
    public TableColumn<Drug, Integer> sellDrugQuantityColumn;
    public Button sellButton;
    public Spinner<Integer> quantitySpinner;
    public Label costLabel;
    public Label sellSelectedDrugNameLabel;
    public Label sellSelectedPatientNameLabel;

    public Button transactionDeleteButton;
    public TableView<Transaction> transactionsTableView;
    public TableColumn<Transaction, String> transactionsFirstNameColumn;
    public TableColumn<Transaction, String> transactionLastNameColumn;
    public TableColumn<Transaction, String> transactionDrugNameColumn;
    public TableColumn<Transaction, Integer> transactionDoseColumn;
    public TableColumn<Transaction, String> transactionManufacturerColumn;
    public TableColumn<Transaction, Integer> transactionQuantityColumn;
    public TableColumn<Transaction, String> transactionDateColumn;
    public TableColumn<Transaction, String> transactionCostColumn;

    public ListView<Warning> warningsListView;

    //Lists
    private ObservableList<Inventory> inventoriesList;
    private ObservableList<InventoryContent> inventoriesContentList;
    private ObservableList<Drug> drugsList;
    private ObservableList<Patient> patientsList;
    private ObservableList<Transaction> transactionsList;


    private Pharmacy currentPharmacy;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPharmacyData();
        initInventoriesTable();
        initInventoriesContentTable();
        initDrugsInStockTable();
        initPatientsTable();
        initSellDrugsTable();
        initSearchEngines();
        initQuantitySpinner();
        initTransactionsTable();

    }

    private void initPharmacyData() {
        currentPharmacy = (Pharmacy) currentUser;
        pharmacyNameLabel.setText(currentPharmacy.getName());
        pharmacyTelephoneLabel.setText(currentPharmacy.getTelephone());
    }

    private void initInventoriesTable() {
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
                if (selectedInventory != null)
                    loadInventoryContents(selectedInventory.getInventoryId());
            }
        });

    }

    private void initInventoriesContentTable() {
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

    private void initDrugsInStockTable() {
        //Set factories
        drugsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryName"));
        drugsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        drugsDoseColumn.setCellValueFactory(new PropertyValueFactory<>("dose"));
        drugsPrescribeColumn.setCellValueFactory(new PropertyValueFactory<>("prescribeLevel"));
        drugsManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        drugsMinAgeColumn.setCellValueFactory(new PropertyValueFactory<>("minAge"));
        drugsQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        drugsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        drugsList = FXCollections.observableArrayList();
        drugsTableView.setItems(drugsList);
        loadDrugsInStock();
    }

    private void initPatientsTable() {
        patientsList = FXCollections.observableArrayList();
        patientsFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        patientsLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        patientsSexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        patientsAgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        patientsTelephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        sellDrugPatientsTableView.setItems(patientsList);
        loadPatients();

        sellDrugPatientsTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Patient selectedPatient = sellDrugPatientsTableView.getSelectionModel().getSelectedItem();
                sellSelectedPatientNameLabel.setText(selectedPatient.getFirst_name() + " " + selectedPatient.getLast_name());
            }
        });
    }

    private void initSellDrugsTable() {
        sellDrugInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryName"));
        sellDrugNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sellDrugDoseColumn.setCellValueFactory(new PropertyValueFactory<>("dose"));
        sellDrugPrescribeColumn.setCellValueFactory(new PropertyValueFactory<>("prescribeLevel"));
        sellDrugManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        sellDrugMinAgeColumn.setCellValueFactory(new PropertyValueFactory<>("minAge"));
        sellDrugQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sellDrugPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        sellDrugDrugsTableView.setItems(drugsList);

        sellDrugDrugsTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Drug selectedDrug = sellDrugDrugsTableView.getSelectionModel().getSelectedItem();
                if (selectedDrug != null) {
                    sellSelectedDrugNameLabel.setText(selectedDrug.getName());
                    costLabel.setText("$" + selectedDrug.getPrice() * quantitySpinner.getValue());
                    loadWarnings(selectedDrug.getDrugId());
                }
            }
        });
    }

    private void initSearchEngines() {
        patientSearchField.textProperty().addListener((observableValue, s, t1) -> searchPatientAction());

        drugSearchField.textProperty().addListener((observableValue, s, t1) -> searchDrugAction());
    }

    private void initQuantitySpinner() {

        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        quantitySpinner.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                Drug selectedDrug = sellDrugDrugsTableView.getSelectionModel().getSelectedItem();
                if (selectedDrug != null) {

                    costLabel.setText("$" + selectedDrug.getPrice() * quantitySpinner.getValue());
                }
            }
        });

    }

    private void initTransactionsTable() {
        transactionsList = FXCollections.observableArrayList();

        transactionsFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("buyerFirstName"));
        transactionLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("buyerLastName"));
        transactionDrugNameColumn.setCellValueFactory(new PropertyValueFactory<>("drugName"));
        transactionDoseColumn.setCellValueFactory(new PropertyValueFactory<>("drugDose"));
        transactionManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("drugManufacturer"));
        transactionQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("drugQuantity"));
        transactionCostColumn.setCellValueFactory(new PropertyValueFactory<>("drugCost"));
        transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));

        transactionsTableView.setItems(transactionsList);
        loadTransactions();
    }

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pharmacyPanel.fxml"));
        stage.setTitle("G24 Pharmacy Manager");
        double height = 640;
        double width = 970;

        stage.setScene(new Scene(root, width, height));

        //Center to the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); //Get screen dimensions
        stage.setX((dimension.getWidth() - width) / 2); //set width and height
        stage.setY((dimension.getHeight() - height) / 2);
        stage.show();

    }


    /**
     * Inventories tab tables
     */
    private void loadInventories() {
        //Clear table
        inventoriesTableView.getItems().clear();
        //Select from database
        String sql = "SELECT inv_id, phar_id, inv_name, drugs_in_stock FROM " +
                "Inventory LEFT JOIN " +
                "(SELECT inv_id as Tinv_id, COUNT(inv_id) as drugs_in_stock FROM InventoryContains GROUP BY(Tinv_id)) AS T " +
                "ON (Inventory.inv_id = T.Tinv_id) WHERE phar_id = ? ORDER BY (inv_name)";

        Connection conn = Database.Connector.connect();
        if (conn == null) return;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentPharmacy.getPhar_id());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                inventoriesList.add(new Inventory(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getInt(4)));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        inventoriesTableView.setItems(inventoriesList);
    }

    private void loadInventoryContents(int selectedInventoryIndex) {
        //Clear table
        inventoriesContentTableView.getItems().clear();

        //Complex query
        String sql = "SELECT drug_name, dose, manufacturer_name, expiry_date, quantity, price FROM " +
                "InventoryContains JOIN " +
                "( select drug_id, Drug.name as drug_name, dose, Manufacturer.name as manufacturer_name from Drug JOIN Manufacturer ON (Drug.man_id=Manufacturer.man_id) ) AS T " +
                "ON (InventoryContains.drug_id = T.drug_id) WHERE  inv_id = ?";

        Connection conn = Database.Connector.connect();
        if (conn == null) {
            return;
        }
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, selectedInventoryIndex);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
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
     * Drugs In Stock Tab
     */
    private void loadDrugsInStock() {
        drugsTableView.getItems().clear();
        sellDrugDrugsTableView.getItems().clear();
        //Very very complex query :(
        String sql = "SELECT inv_id, inv_name, drug_id, drug_name, dose, prescribe_level, min_age, name as manufacturer, quantity, price " +
                "FROM Manufacturer JOIN " +
                "(SELECT inv_id, inv_name, Iphar_id, drug_id, name as drug_name, dose, min_age, prescribe_level, man_id, price, quantity " +
                "FROM Drug LEFT JOIN " +
                "(SELECT inv_id, inv_name, drug_id as Kdrug_id, price, Iphar_id, quantity FROM " +
                "InventoryContains JOIN " +
                "( SELECT inv_id as Tinv_id, phar_id as Iphar_id, inv_name FROM Inventory) AS T " +
                "ON (T.Tinv_id = InventoryContains.inv_id)) AS K " +
                "ON (K.Kdrug_id = Drug.drug_id)) AS L " +
                "ON (L.man_id = Manufacturer.man_id) WHERE Iphar_id = ?";

        Connection conn = Database.Connector.connect();
        if (conn == null) return;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentPharmacy.getPhar_id());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                drugsList.add(new Drug(rs.getInt(1), rs.getString(2), rs.getInt(3),
                        rs.getString(4), rs.getInt(5), rs.getInt(6),
                        rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getDouble(10)));

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        drugsTableView.setItems(drugsList);
        sellDrugDrugsTableView.setItems(FXCollections.observableArrayList(drugsList));
    }


    /**
     * Sell tab
     */
    private void loadPatients() {
        sellDrugPatientsTableView.getItems().clear();
        //Simple query
        String sql = "SELECT * FROM Patient";
        Connection conn = Database.Connector.connect();
        if (conn == null) return;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                patientsList.add(new Patient(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5).charAt(0), rs.getDate(6)));
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sellDrugPatientsTableView.setItems(FXCollections.observableArrayList(patientsList));
    }

    private void loadWarnings(int drug_id) {
        if (warningsListView.getItems() == null) return;
        warningsListView.getItems().clear();
        ObservableList<Warning> warningsList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM DrugHasWarning JOIN Warnings ON (Warnings.warning_id = DrugHasWarning.warning_id) WHERE drug_id = ?";

        Connection conn = Database.Connector.connect();
        if (conn == null) return;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, drug_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                warningsList.add(new Warning(rs.getInt(2), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        warningsListView.setItems(warningsList);
    }

    private ObservableList<Patient> searchPatient(String query) {
        ObservableList<Patient> filteredList = FXCollections.observableArrayList();
        patientsList.stream()
                .filter(patient -> (patient.getFirst_name() + patient.getLast_name()).toLowerCase().contains(query.replaceAll(" ", "").toLowerCase()))
                .forEach(filteredList::add);
        return filteredList;
    }

    private ObservableList<Drug> searchDrug(String query) {
        ObservableList<Drug> filteredList = FXCollections.observableArrayList();
        drugsList.stream()
                .filter(drug -> drug.getName().toLowerCase().contains(query.toLowerCase()))
                .forEach(filteredList::add);
        return filteredList;
    }

    /**
     * Transactions tab
     */

    private void loadTransactions() {
        transactionsTableView.getItems().clear();

        String sql = "SELECT patient_id, first_name, last_name, drug_name, dose, name as manufacturer, quantity, date, cost " +
                "FROM Manufacturer JOIN " +
                "(SELECT patient_id, first_name, last_name, name as drug_name, dose, man_id, quantity, cost, date " +
                "FROM Drug JOIN (SELECT patient_id, first_name, last_name, drug_id as Tdrug_id, quantity, cost, date " +
                "FROM Transaction JOIN " +
                "Patient ON (Transaction.buyer_id=Patient.patient_id) WHERE seller_id = ?) AS T" +
                " ON (T.Tdrug_id = Drug.drug_id)) AS K " +
                "ON (Manufacturer.man_id = K.man_id)";

        Connection conn = Database.Connector.connect();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentPharmacy.getPhar_id());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactionsList.add(new Transaction(rs.getInt(1),
                        rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getDate(8), rs.getDouble(9)
                ));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        transactionsTableView.setItems(transactionsList);
    }

    /**
     * Inventory table toolbar actions
     **/
    public void inventoryAddAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Inventory");
        dialog.setHeaderText("Add New Inventory");
        dialog.setContentText("New inventory name:");


        Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) return;
        String inventoryNameInput = result.get();

        boolean add = Record.addNewInventory(new Inventory(currentPharmacy.getPhar_id(), inventoryNameInput));

        if (!add) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory add");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add inventory");
            alert.showAndWait();
        }
        loadInventories();

    }

    public void inventoryDeleteAction() {
        Inventory selectedInventory = inventoriesTableView.getSelectionModel().getSelectedItem();
        if (selectedInventory == null) {
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
        alert.setContentText("Are you sure that you really want to delete " + selectedInventory);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Record.deleteInventory(selectedInventory.getInventoryId());
            loadInventories();
        } else {
            //Nothing
        }
    }

    public void inventoryEditAction() {
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
        Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) return;
        String inventoryNameInput = result.get();
        boolean update = Record.updateInventory(new Inventory(selectedInventory.getInventoryId(),
                currentPharmacy.getPhar_id(),
                inventoryNameInput));

        if (!update) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Edit");
            alert.setHeaderText(null);
            alert.setContentText("Failed to change inventory name");
            alert.showAndWait();
            return;
        }

        loadInventories();
    }

    /**
     * Drugs in stock table toolbar actions
     */

    public void drugsAddAction() throws IOException {
        Pane myPane = FXMLLoader.load(getClass().getResource("/Layout/addDrugDialog.fxml"));
        Scene scene = new Scene(myPane);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        loadDrugsInStock();
        loadInventories();
    }

    public void drugsDeleteAction() {
        Drug selectedDrug = drugsTableView.getSelectionModel().getSelectedItem();
        if (selectedDrug == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Drug");
            alert.setHeaderText(null);
            alert.setContentText("Please select a drug");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Drug");
        alert.setHeaderText("Delete Drug");
        alert.setContentText("Are you sure that you really want to delete " + selectedDrug.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //Delete drug
            String sql = "DELETE FROM InventoryContains WHERE inv_id = ? AND drug_id = ?";
            //Connect to the database
            Connection conn = Database.Connector.connect();
            if (conn == null) return;

            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, selectedDrug.getInventoryId());
                stmt.setInt(2, selectedDrug.getDrugId());
                stmt.execute();
                loadDrugsInStock();
                loadInventories();

            } catch (SQLException e) {
                e.printStackTrace();
            }


        } else {
            //Nothing
        }

    }

    /**
     * Sell tab actions
     **/

    public void searchPatientAction() {
        sellDrugPatientsTableView.getItems().clear();
        String query = patientSearchField.getText();
        if (query.replaceAll(" ", "").equals("")) {
            sellDrugPatientsTableView.setItems(FXCollections.observableArrayList(patientsList));
        } else {
            sellDrugPatientsTableView.setItems(searchPatient(query));
        }
    }

    public void searchDrugAction() {
        sellDrugDrugsTableView.getItems().clear();
        String query = drugSearchField.getText();
        if (query.replaceAll(" ", "").equals("")) {
            sellDrugDrugsTableView.setItems(FXCollections.observableArrayList(drugsList));
        } else {
            sellDrugDrugsTableView.setItems(searchDrug(query));
        }
    }

    public void sellButtonAction() {
        int quantity = quantitySpinner.getValue();

        Drug selectedDrug = sellDrugDrugsTableView.getSelectionModel().getSelectedItem();
        Patient selectedPatient = sellDrugPatientsTableView.getSelectionModel().getSelectedItem();

        if (selectedDrug == null || selectedPatient == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Make Transaction");
            alert.setHeaderText(null);
            alert.setContentText("Please select a drug and patient to make a transaction");
            alert.showAndWait();
            return;
        } else if (quantity > selectedDrug.getQuantity()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Make Transaction");
            alert.setHeaderText(null);
            alert.setContentText("There is not enough " + selectedDrug.getName() + " in the stock to make transaction.");
            alert.showAndWait();
            return;
        }

        if (currentPharmacy.makeTransaction(selectedDrug.getInventoryId(),
                selectedPatient.getPatient_id(),
                selectedDrug.getDrugId(),
                quantity, quantity * selectedDrug.getPrice())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Make Transaction");
            alert.setHeaderText(null);
            alert.setContentText("Transaction recorded successfully.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Make Transaction");
            alert.setHeaderText(null);
            alert.setContentText("Failed to record transaction.");
            alert.showAndWait();
        }

        loadDrugsInStock();
        loadInventories();
        loadTransactions();
    }

    public void transactionDeleteAction() {
        Transaction selectedTransaction = transactionsTableView.getSelectionModel().getSelectedItem();

        if (selectedTransaction == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a transaction to delete");
            alert.showAndWait();
            return;
        }

        boolean delete = Record.deleteTransaction(selectedTransaction.getBuyer_id(),
                currentPharmacy.getPhar_id(),
                selectedTransaction.getDate());

        if (!delete) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Couldn't delete data");
            alert.showAndWait();
        }
        loadTransactions();
    }

    public void editPharmacyAction() throws IOException {
        Pane myPane = FXMLLoader.load(getClass().getResource("/Layout/editPharmacyDialog.fxml"));
        Scene scene = new Scene(myPane);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        initPharmacyData();

    }

    public void addPatientAction() throws IOException {
        Pane myPane = FXMLLoader.load(getClass().getResource("/Layout/addPatientDialog.fxml"));
        Scene scene = new Scene(myPane);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        loadPatients();
    }
}