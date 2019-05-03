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

    public static boolean addNewInventory(Pharmacy pharmacy) {
        int phar_id = pharmacy.getPhar_id();
        int drug_id = 0;
        int quantity = 0;
        Date expiry_date = new Date(System.currentTimeMillis());
        String sql = "INSERT INTO Inventory (phar_id, drug_id, quantity, expiry_date) VALUES(?, ?, ?, ?)";
        Connection conn = Database.Connector.connect();
        if (conn == null) return false;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,phar_id);
            stmt.setInt(2,0);
            stmt.setInt(3,0);
            stmt.setDate(4, expiry_date);
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addDrugToInventory(Drug drug, Inventory inventory){
        return false;
    }

    public static boolean updatePharmacyInfo(Pharmacy pharmacy, Pharmacy newPharmacy, int newId){
        return false;
    }

    public static boolean makeTransaction(Pharmacy pharmacy, Patient patient, Drug drug){
        return false;
    }

}
