package Database;

import Unit.Pharmacy;
import Unit.User;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    public static User currentUser;

    public static boolean login(String username, String password){
        //Connect to the database
        Connection conn = Database.Connector.connect();

        //Prepare query
        String sql = "SELECT * FROM Pharmacy WHERE phar_id = (SELECT phar_id FROM Users WHERE username = ? AND password = ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()){
                currentUser = new Pharmacy(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4));
                System.out.printf("Login successfull, \nPharmacy id: %d\nPharmacy name: %s\nPharmacy adress: %s\nPharmacy telephone: %s\n",
                        ((Pharmacy) currentUser).getPhar_id(), ((Pharmacy) currentUser).getName(), ((Pharmacy) currentUser).getAdress(),((Pharmacy) currentUser).getTelephone());
                return true;
            }else {
                System.err.println("Login failed");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Login Failed");
                alert.setContentText("Please check your username and password!");

                alert.showAndWait();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public static Pharmacy getCurrentPharmacy(){
        return (Pharmacy) currentUser;
    }



}
