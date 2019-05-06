package Unit;

import java.sql.*;

public class Patient {
    private int patient_id;
    private String first_name;
    private String last_name;
    private String telephone;
    private char sex;
    private Date dateOfBirth;

    public Patient(int patient_id, String first_name, String last_name, String telephone, char sex, Date dateOfBirth) {
        this.patient_id = patient_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.telephone = telephone;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
    }


    /**
     * Returns Patient object with the given Id from database.
     * @param patient_id=Patient ID to select from database
     * @return Patient
     */
    public static Patient getPatientById(int patient_id){
        //Prepare sql statement
        String sql = "SELECT * FROM Patient WHERE patient_id = ?";
        //Connect to the database
        Connection conn = Database.Connector.connect();
        if (conn == null) return null;

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,patient_id);
            //Get results
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                //Patient found
                Patient patient = new Patient(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4),
                        rs.getString(5).charAt(0),rs.getDate(6));
                return patient;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int getPatient_id() {
        return patient_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public char getSex() {
        return sex;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

}
