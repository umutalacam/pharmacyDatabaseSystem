package Unit;

import java.sql.Date;

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
