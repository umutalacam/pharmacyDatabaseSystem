package Controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;



public class Login extends Application{

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/login.fxml"));
        stage.setTitle("G24 Pharmacy Manager");
        double height = 272;
        double width = 448;

        stage.setScene(new Scene(root, width, height));

        //Center to the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); //Get screen dimensions
        stage.setX((dimension.getWidth()-width)/2); //set width and height
        stage.setY((dimension.getHeight()-height)/2);

        stage.setResizable(false);
        stage.show();
    }

    public void loginAction(){
        String usernameInput = usernameField.getText();
        String passwordInput = passwordField.getText();
        Database.Login.login(usernameInput, passwordInput);
    }

    public void resetAction(){

    }
}
