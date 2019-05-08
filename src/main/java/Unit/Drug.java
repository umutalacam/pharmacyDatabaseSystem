package Unit;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Drug {
    private int drugId;
    private int substanceId;
    private int manId;
    private SimpleStringProperty name;
    private SimpleIntegerProperty dose;
    private SimpleIntegerProperty minAge;
    private SimpleIntegerProperty prescribeLevel;
    private SimpleStringProperty substance;
    private SimpleStringProperty manufacturer;

    public Drug(int drugId, String name, int dose, int minAge, int prescribeLevel, int substanceId, int manId){
        this.drugId = drugId;
        this.name = new SimpleStringProperty(name);
        this.dose = new SimpleIntegerProperty(dose);
        this.minAge = new SimpleIntegerProperty(minAge);
        this.prescribeLevel = new SimpleIntegerProperty(prescribeLevel);
        this.substanceId = substanceId;
        this.manId = manId;
    }

    public Drug(int drugId, String name,
                int dose, int minAge,
                int prescribeLevel, String substance, String manufacturer) {
        this.drugId = drugId;
        this.name = new SimpleStringProperty(name);
        this.dose = new SimpleIntegerProperty(dose);
        this.minAge = new SimpleIntegerProperty(minAge);
        this.prescribeLevel = new SimpleIntegerProperty(prescribeLevel);
        this.substance = new SimpleStringProperty(substance);
        this.manufacturer = new SimpleStringProperty(manufacturer);
    }

    public int getDrugId() {
        return drugId;
    }

    public String getName() {
        return name.get();
    }

    public int getDose() {
        return dose.get();
    }

    public int getMinAge() {
        return minAge.get();
    }

    public int getPrescribeLevel() {
        return prescribeLevel.get();
    }

    public String getSubstance() {
        return substance.get();
    }

    public String getManufacturer(){
        return manufacturer.get();
    }

    public int getSubstanceId() {
        return substanceId;
    }

    public int getManId() {
        return manId;
    }
}
