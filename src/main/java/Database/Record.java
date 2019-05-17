package Database;

import Unit.Drug;
import Unit.Inventory;
import Unit.Patient;
import Unit.Pharmacy;
import com.mysql.jdbc.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Record {

    /**
     * Adds new inventory record to the database
     **/
    public static boolean addNewInventory(Inventory inventory) {
        //Get data fields
        int phar_id = inventory.getPharmacyId();
        String inventoryName = inventory.getInventoryName();

        //Prepare sql statement
        String sql = "INSERT INTO Inventory (phar_id, inv_name) VALUES(?, ?)";

        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) return false;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, phar_id);
            stmt.setString(2, inventoryName);
            stmt.execute();
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
     * Updates inventory data, needs specific id
     **/
    public static boolean updateInventory(Inventory inventory) {
        //Get data fields
        int inventoryId = inventory.getInventoryId();
        int pharmacyId = inventory.getPharmacyId();
        String inventoryName = inventory.getInventoryName();

        //Prepare sql query
        String sql = "UPDATE Inventory " +
                "SET phar_id=?, inv_name=? " +
                "WHERE inv_id=?";

        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) return false;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pharmacyId);
            stmt.setString(2, inventoryName);
            stmt.setInt(3, inventoryId);
            stmt.execute();
            if (stmt.getUpdateCount()!=-1){
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Deletes an inventory record with the given Id
     **/
    public static boolean deleteInventory(int inv_id) {

        String sql = "DELETE FROM Inventory WHERE inv_id = ?";
        Connection conn = Database.Connector.connect();

        if (conn == null) return false;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, inv_id);
            stmt.execute();
            if (stmt.getUpdateCount()!=-1){
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Adds a new pharmacy
     **/
    public static int addNewPharmacy(Pharmacy pharmacy) {
        //Prepare data fields
        String name = pharmacy.getName();
        String adress = pharmacy.getAdress();
        String telephone = pharmacy.getTelephone();

        //Prepare sql query
        String sql = "INSERT INTO Pharmacy (name, adress, telephone) VALUES(?, ?, ?)";
        String idSql = "SELECT LAST_INSERT_ID();";

        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) return -1;

        //Try to execute query
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, adress);
            stmt.setString(3, telephone);
            stmt.execute();
            if (stmt.getUpdateCount()==-1) return  -1;

            PreparedStatement idStmt = conn.prepareStatement(idSql);
            ResultSet rs = idStmt.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;

    }

    /**
     * Updates pharmacy information must be given an specific id
     **/
    public static boolean updatePharmacy(Pharmacy pharmacy) {
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

        try {
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
     **/
    public static boolean deletePharmacy(int phar_id) {
        //Prepare query
        String sql = "DELETE FROM Pharmacy WHERE phar_id = ?";
        //Connect to the database
        Connection conn = Database.Connector.connect();
        //Check if connection is not null
        if (conn == null) return false;
        //Try to prepare statement
        try {
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
     * Add patient to the database
     */
    public static boolean addPatient(Patient patient){
        //Prepare data fields
        String firstname = patient.getFirst_name();
        String lastname = patient.getLast_name();
        String telephone = patient.getTelephone();
        char sex = patient.getSex().charAt(0);
        Date dateofbirth = patient.getDateOfBirth();

        String sql = "INSERT INTO Patient (first_name, last_name, telephone, sex, date_of_birth) " +
                "VALUES(?, ?, ?, ?, ?)";
        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) return false;

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, telephone);
            stmt.setString(4, String.valueOf(sex));
            stmt.setDate(5,dateofbirth);
            stmt.execute();
            if (stmt.getUpdateCount()!=-1) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    /**
     * Update patient with given id.
     * **/
    public static boolean updatePatient(Patient patient){
        //Prepare data fields
        String firstname = patient.getFirst_name();
        String lastname = patient.getLast_name();
        String telephone = patient.getTelephone();
        char sex = patient.getSex().charAt(0);
        Date dateofbirth = patient.getDateOfBirth();
        int patient_id = patient.getPatient_id();

        String sql = "UPDATE Patient SET first_name = ?, lastname = ?, telephone = ?, sex = ?, date_of_birth = ? " +
                "WHERE patient_id = ?";
        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) return false;

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, telephone);
            stmt.setString(4, String.valueOf(sex));
            stmt.setDate(5,dateofbirth);
            stmt.setInt(6,patient_id);
            return stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     *  Deletes a patient with the given Id.
     */
    public static boolean deletePatient(int patient_id){
        String sql = "DELETE FROM Patient WHERE patient_id = ?";

        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) return false;

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,patient_id);
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * Deletes transaction
     */
    public static boolean deleteTransaction(int buyer_id, int seller_id, Date date){
        String sql = "DELETE FROM Transaction WHERE buyer_id = ? AND seller_id = ? AND date= ?";
        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) return false;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, buyer_id);
            stmt.setInt(2, seller_id);
            stmt.setDate(3, date);
            stmt.execute();
            if (stmt.getUpdateCount()!=-1) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean addUser(String username, String password, int phar_id){
        //Prepare sql query
        String sql = "INSERT INTO Users (username, password, phar_id) VALUES(?, ?, ?)";
        //Connect to the database
        Connection conn = Database.Connector.connect();
        if(conn == null) return false;

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, phar_id);
            stmt.execute();

            if (stmt.getUpdateCount()!=-1) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
