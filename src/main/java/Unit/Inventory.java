package Unit;

import com.mysql.jdbc.Connection;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Inventory {
    private int inventoryId;
    private int pharmacyId;
    private SimpleStringProperty inventoryName;
    private SimpleIntegerProperty drugsInStock;


    public Inventory(int inventoryId, int pharmacyId, String inventoryName, int drugsInStock){
        this.inventoryId = inventoryId;
        this.pharmacyId = pharmacyId;
        this.inventoryName =  new SimpleStringProperty(inventoryName);
        this.drugsInStock = new SimpleIntegerProperty(drugsInStock);
    }

    public Inventory(int inventoryId, int pharmacyId, String inventoryName) {
        this.inventoryId = inventoryId;
        this.pharmacyId = pharmacyId;
        this.inventoryName = new SimpleStringProperty(inventoryName);
        this.drugsInStock = new SimpleIntegerProperty(this.getDrugsInStock());
    }

    public Inventory(int pharmacyId, String inventoryName) {
        //Constructor to instance an Inventory without Id for adding purpose.
        this.inventoryId = 0;
        this.pharmacyId = pharmacyId;
        this.inventoryName = new SimpleStringProperty(inventoryName);
        this.drugsInStock = new SimpleIntegerProperty(this.getDrugsInStock());
    }


    /**
     * Calculates and returns the drug count in Inventory
     */
    public int getDrugsInStock(){

        int sumOfDrugs = 0;
        String sql = "SELECT COUNT(inv_id) FROM InventoryContains WHERE inv_id = ?";
        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) return 0;

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.inventoryId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                sumOfDrugs = rs.getInt(1);
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sumOfDrugs;
    }

    public boolean addDrug(int drug_id, int quantity, double price, Date expiryDate){

        //Prepare sql query and db connection
        String sql = "INSERT INTO InventoryContains (inv_id, drug_id, quantity, price, expiry_date) VALUES(?, ?, ?, ?, ?)" +
                "ON DUPLICATE KEY UPDATE quantity = quantity+VALUES(quantity)";
        Connection conn = Database.Connector.connect();
        if (conn == null) return false;

        try {
            //Prepare statement
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.inventoryId);
            stmt.setInt(2, drug_id);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.setDate(5, expiryDate);
            stmt.execute();
            //Execute query
            if (stmt.getUpdateCount()!=-1) {
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Returns an Inventory instance with the given ID from database
     * **/
    public static Inventory getInventoryById(int inventoryId){
        //Prepare sql statement
        String sql = "SELECT * FROM Inventory WHERE inv_id = ?";

        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) {
            System.err.println("getInventoryById(): Couldn't connect to the database");
            return null;
        }

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,inventoryId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                conn.close();
                return new Inventory(rs.getInt(1),rs.getInt(2),rs.getString(3));
            }else {
                conn.close();
                System.err.println("getInventoryById(): Couldn't find Inventory in the database");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public int getInventoryId() {
        return inventoryId;
    }

    public int getPharmacyId() {
        return pharmacyId;
    }

    public String getInventoryName() {

        return inventoryName.get();
    }


    @Override
    public String toString() {
        return  inventoryName.get();
    }
}
