package Controller;

import Database.Login;
import Unit.Pharmacy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditPharmacyDialog extends Application implements Initializable {

    public TextField pharmacyNameField;
    public TextArea pharmacyAdressArea;
    public TextField pharmacyTelField;
    public Button saveButton;
    public Button cancelButton;


    Pharmacy currentPharmacy;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentPharmacy = Login.getCurrentPharmacy();
        pharmacyNameField.setText(currentPharmacy.getName());
        pharmacyAdressArea.setText(currentPharmacy.getAdress());
        pharmacyTelField.setText(currentPharmacy.getTelephone());
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/editPharmacyDialog.fxml"));
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

    public void saveAction(){
        //Sql query
        String sql = "UPDATE Pharmacy SET name = ?, adress = ?, telephone = ? WHERE phar_id = ?";

        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) return;

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pharmacyNameField.getText());
            stmt.setString(2, pharmacyAdressArea.getText());
            stmt.setString(3, pharmacyTelField.getText());
            stmt.setInt(4, currentPharmacy.getPhar_id());

            stmt.execute();

            if (stmt.getUpdateCount()==-1){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Couldn't save data");
                alert.showAndWait();
            }else {
                //Do nothing
                Database.Login.getCurrentPharmacy().setName(pharmacyNameField.getText());
                Database.Login.getCurrentPharmacy().setAdress(pharmacyAdressArea.getText());
                Database.Login.getCurrentPharmacy().setTelephone(pharmacyTelField.getText());
                cancelAction();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }



}
