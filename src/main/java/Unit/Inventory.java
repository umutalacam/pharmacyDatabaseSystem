package Unit;

import com.mysql.jdbc.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Inventory {
    private int inventoryId;
    private int pharmacyId;
    private String inventoryName;

    public Inventory(int inventoryId, int pharmacyId, String inventoryName) {
        this.inventoryId = inventoryId;
        this.pharmacyId = pharmacyId;
        this.inventoryName = inventoryName;
    }

    public Inventory(int pharmacyId, String inventoryName) {
        //Constructor to instance an Inventory without Id for adding purpose.
        this.inventoryId = 0;
        this.pharmacyId = pharmacyId;
        this.inventoryName = inventoryName;
    }

    public boolean addDrug(int drug_id, int quantity, Date expiryDate){

        //Prepare sql query and db connection
        String sql = "INSERT INTO InventoryContains (inv_id, drug_id, quantity, expiry_date) VALUES(?, ?, ?, ?)";
        Connection conn = Database.Connector.connect();
        if (conn == null) return false;

        try {
            //Prepare statement
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.inventoryId);
            stmt.setInt(2, drug_id);
            stmt.setInt(3, quantity);
            stmt.setDate(4, expiryDate);
            //Execute query
            return stmt.execute();
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
                return new Inventory(rs.getInt(1),rs.getInt(2),rs.getString(3));
            }else {
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
        return inventoryName;
    }
}
