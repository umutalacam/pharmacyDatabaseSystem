package Database;

import Unit.Drug;
import Unit.Inventory;
import Unit.Patient;
import Unit.Pharmacy;
import com.mysql.jdbc.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Record {

    /**
     * Adds new inventory record to the database
     * **/
    public static boolean addNewInventory(int phar_id, String inventoryName) {
        //Prepare sql statement
        String sql = "INSERT INTO Inventory (phar_id, inv_name) VALUES(?, ?)";
        Connection conn = Database.Connector.connect();
        if (conn == null) return false;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, phar_id);
            stmt.setString(2, inventoryName);
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an inventory record with the given Id
     * **/
    public static boolean deleteInventory(int inv_id){

        String sql = "DELETE FROM Inventory WHERE inv_id = ?";
        Connection conn = Database.Connector.connect();

        if (conn == null) return false;

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,inv_id);
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Adds a drug to an inventory
     * **/
    public static boolean addDrugToInventory(int inv_id, int drug_id, int quantity, Date expiryDate){

        //Prepare sql query and db connection
        String sql = "INSERT INTO InventoryContains (inv_id, drug_id, quantity, expiry_date) VALUES(?, ?, ?, ?)";
        Connection conn = Database.Connector.connect();
        if (conn == null) return false;

        try {
            //Prepare statement
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,inv_id);
            stmt.setInt(2,drug_id);
            stmt.setInt(3,quantity);
            stmt.setDate(4,expiryDate);
            //Execute query
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Adds a new pharmacy
     * **/
    public static boolean addNewPharmacy(Pharmacy pharmacy){
        //Prepare data fields
        String name = pharmacy.getName();
        String adress = pharmacy.getAdress();
        String telephone = pharmacy.getTelephone();

        //Prepare sql query
        String sql = "INSERT INTO Pharmacy (name, adress, telephone) VALUES(?, ?, ?)";

        //Connect to the database
        Connection conn = Database.Connector.connect();

        //Try to execute query
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2,adress);
            stmt.setString(3, telephone);
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    /**
     * Updates pharmacy information must be given an specific id
     * **/
    public static boolean updatePharmacy(Pharmacy pharmacy){
        int phar_id = pharmacy.getPhar_id();
        if (phar_id == 0) {
            System.err.println("Id must be specified!");
            return false;
        }
        String name = pharmacy.getName();
        String adress = pharmacy.getAdress();
        String telephone = pharmacy.getTelephone();

        String sql = "UPDATE Pharmacy " +
                "SET name = ?, adress = ?, telephone = ? " +
                "WHERE phar_id = ?";

        Connection conn = Database.Connector.connect();

        if (conn == null) return false;

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, adress);
            stmt.setString(3, telephone);
            stmt.setInt(4, phar_id);
            return stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }


    /**
     * Deletes pharmacy from database
     * **/
    public static boolean deletePharmacy(int phar_id){
        //Prepare query
        String sql = "DELETE FROM Pharmacy WHERE phar_id = ?";
        //Connect to the database
        Connection conn = Database.Connector.connect();
        //Check if connection is not null
        if (conn == null) return false;
        //Try to prepare statement
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, phar_id);
            //Execute query
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Record a new transaction to the database
     * **/
    public static boolean addTransaction(int phar_id, int patient_id, int drug_id){
        //Prepare sql query
        String sql = "INSERT INTO Transaction (buyer_id, seller_id, drug_id) VALUES (?, ?, ?)";
        //Connect to the database
        Connection conn = Database.Connector.connect();
        //Check if connection is not null
        if (conn == null) return false;

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patient_id);
            stmt.setInt(2, phar_id);
            stmt.setInt(3, drug_id);
            //excute query
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
