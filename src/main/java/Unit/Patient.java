package Unit;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;

public class Patient {
    private int patient_id;
    private SimpleStringProperty first_name;
    private SimpleStringProperty last_name;
    private SimpleStringProperty telephone;
    private SimpleStringProperty sex;
    private Date dateOfBirth;
    private SimpleIntegerProperty age;

    public Patient(int patient_id, String first_name, String last_name, String telephone, char sex, Date dateOfBirth) {
        this.patient_id = patient_id;
        this.first_name = new SimpleStringProperty(first_name);
        this.last_name = new SimpleStringProperty(last_name);
        this.telephone = new SimpleStringProperty(telephone);
        this.sex = new SimpleStringProperty(Character.toString(sex));
        this.dateOfBirth = dateOfBirth;
        Date now = new Date(System.currentTimeMillis());
        Calendar today = Calendar.getInstance();
        today.setTime(now);
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(dateOfBirth);
        int period = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR)-1;
        if (today.get(Calendar.MONTH)>birthday.get(Calendar.MONTH)) period++;
        this.age = new SimpleIntegerProperty(period);

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
        return first_name.get();
    }

    public SimpleStringProperty first_nameProperty() {
        return first_name;
    }

    public String getLast_name() {
        return last_name.get();
    }

    public SimpleStringProperty last_nameProperty() {
        return last_name;
    }

    public String getTelephone() {
        return telephone.get();
    }

    public SimpleStringProperty telephoneProperty() {
        return telephone;
    }

    public String getSex() {
        return sex.get();
    }

    public SimpleStringProperty sexProperty() {
        return sex;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }
}
