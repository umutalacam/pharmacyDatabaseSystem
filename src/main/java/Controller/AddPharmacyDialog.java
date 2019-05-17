package Controller;

import Database.Record;
import Unit.Pharmacy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPharmacyDialog extends Application implements Initializable {
    public TextField pharmacyNameField;
    public TextField pharmacyTelField;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button saveButton;
    public Button cancelButton;
    public TextArea pharmacyAdressArea;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/addPharmacyDialog.fxml"));
        stage.setTitle("Edit Pharmacy");
        double height = 282;
        double width = 458;

        stage.setScene(new Scene(root, width, height));
        stage.setResizable(false);

        //Center to the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); //Get screen dimensions
        stage.setX((dimension.getWidth()-width)/2); //set width and height
        stage.setY((dimension.getHeight()-height)/2);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveAction(){
        //Get fields
        //Filter username and passwords
        String username = usernameField.getText();
        String password = passwordField.getText();
        String pharmacyName = pharmacyNameField.getText();
        String pharmacyTel = pharmacyTelField.getText();
        String pharAddress = pharmacyAdressArea.getText();

        int addedPharmacyId = Record.addNewPharmacy(new Pharmacy(pharmacyName,pharAddress, pharmacyTel));
        if (addedPharmacyId == -1){
            new Alert(Alert.AlertType.ERROR,"Couldn't add new pharmacy!\nPlease contact your system administrator",
                    ButtonType.OK).showAndWait();
            return;
        }

        boolean addUser = Record.addUser(username, password, addedPharmacyId);
        if (!addUser){
            new Alert(Alert.AlertType.ERROR, "Couldn't add user!\nPlease contact with your system admin.",
                    ButtonType.OK).showAndWait();
            return;
        }

        cancelAction();

    }

    public void cancelAction(){
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
