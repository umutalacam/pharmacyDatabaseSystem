package Controller;

import Database.Record;
import Unit.Patient;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class PatientDialog extends Application implements Initializable {
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField telephoneField;
    public ComboBox<String> sexComboBox;
    public DatePicker birthDatePicker;

    private ObservableList<String> genders;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/addPatientDialog.fxml"));
        stage.setTitle("Add New Pharmacy");
        double height = 350;
        double width = 510;

        stage.setScene(new Scene(root, width, height));

        //Center to the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); //Get screen dimensions
        stage.setX((dimension.getWidth()-width)/2); //set width and height
        stage.setY((dimension.getHeight()-height)/2);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genders = FXCollections.observableArrayList();
        genders.addAll("Female", "Male");
        sexComboBox.setItems(genders);
    }

    public void saveAction(){
        Patient patient = new Patient(0,firstNameField.getText(),
                lastNameField.getText(),telephoneField.getText(),sexComboBox.getSelectionModel().getSelectedItem().charAt(0),
                Date.valueOf(birthDatePicker.getValue()));
        boolean add = Record.addPatient(patient);
        if (!add){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Couldn't save data");
            alert.showAndWait();
        }
        cancelAction();
    }

    public void cancelAction(){
        ((Stage) firstNameField.getScene().getWindow()).close();
    }
}
