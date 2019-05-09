package Unit;

import Database.Connector;
import com.mysql.jdbc.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pharmacy implements User{
    private int phar_id;
    private String name;
    private String adress;
    private String telephone;

    public Pharmacy(int phar_id, String name, String adress, String telephone){
        this.phar_id = phar_id;
        this.name = name;
        this.adress = adress;
        this.telephone = telephone;
    }

    public Pharmacy(String name, String adress, String telephone) {
        //For adding pharmacy without id
        this.phar_id = 0;
        this.name = name;
        this.adress = adress;
        this.telephone = telephone;
    }

    public boolean makeTransaction(int inv_id, int patient_id, int drug_id, int quantity){
        //Prepare sql query
        String sql = "INSERT INTO Transaction (buyer_id, seller_id, drug_id, quantity) VALUES (?, ?, ?, ?)";
        //Connect to the database
        Connection conn = Database.Connector.connect();
        //Check if connection is not null
        if (conn == null) return false;

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patient_id);
            stmt.setInt(2, this.phar_id);
            stmt.setInt(3, drug_id);
            stmt.setInt(4, quantity);
            //excute query
            stmt.execute();
            if (stmt.getUpdateCount()==-1) return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


        //Remove sold drugs from inventory
        String sqlUpdate = "UPDATE InventoryContains SET quantity = quantity - ? WHERE inv_id = ? AND drug_id = ?";
        try {
            PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate);
            updateStmt.setInt(1,quantity);
            updateStmt.setInt(2,inv_id);
            updateStmt.setInt(3,drug_id);
            //execute query
            updateStmt.execute();
            if (updateStmt.getUpdateCount()==-1) return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        //Remove drugs with 0 quantity
        String sqlDelete = "DELETE FROM InventoryContains WHERE quantity=0";
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlDelete);
            stmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    public static Pharmacy getPharmacyWithId(int phar_id){
        String sql = "SELECT * FROM Pharmacy WHERE phar_id = ?";

        Connection conn = Database.Connector.connect();
        if (conn == null){
            System.err.println("getPharmacyWithId(): Couldn't connect to the database");
            return null;
        }

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,phar_id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return new Pharmacy(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4));

            }else {
                System.err.println("getPharmacyWithId(): Couldn't find pharmacy");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    public int getPhar_id() {
        return phar_id;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public String getTelephone() {
        return telephone;
    }
}
