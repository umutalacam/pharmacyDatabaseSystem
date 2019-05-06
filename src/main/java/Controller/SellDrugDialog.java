package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

import java.awt.*;

public class SellDrugDialog extends Application {
    public Button sellButton;
    public Label drugNameLabel;
    public Label doseLabel;
    public Label minAgeLabel;
    public Label prescribeLevelLabel;
    public Label manufacturerLabel;
    public ListView<Object/*warningclass*/> warningsListView;
    public Spinner<Integer/*quantityclass*/> quantitySpinner;
    public Label costLabel;

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/sellDrugDialog.fxml"));
        stage.setTitle("G24 Pharmacy Manager");
        double height = 561;
        double width = 487;

        stage.setScene(new Scene(root, width, height));

        //Center to the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); //Get screen dimensions
        stage.setX((dimension.getWidth()-width)/2); //set width and height
        stage.setY((dimension.getHeight()-height)/2);

        stage.show();
    }

    }
