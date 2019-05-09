package Unit;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Drug {
    private int drugId;
    private int substanceId;
    private int manId;
    private int inventoryId;
    private SimpleStringProperty name;
    private SimpleIntegerProperty dose;
    private SimpleIntegerProperty minAge;
    private SimpleIntegerProperty prescribeLevel;
    private SimpleStringProperty substance;
    private SimpleStringProperty manufacturer;
    private SimpleDoubleProperty price;

    public Drug(int inventoryId, int drugId, String name, int dose, int prescribeLevel, int minAge, String manufacturer, Double price){
        this.inventoryId = inventoryId;
        this.drugId = drugId;
        this.name = new SimpleStringProperty(name);
        this.dose = new SimpleIntegerProperty(dose);
        this.minAge = new SimpleIntegerProperty(minAge);
        this.prescribeLevel = new SimpleIntegerProperty(prescribeLevel);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.price = new SimpleDoubleProperty(price);
    }

    public Drug(int drugId, String name, int dose, int minAge, int prescribeLevel, int substanceId, int manId){
        this.drugId = drugId;
        this.name = new SimpleStringProperty(name);
        this.dose = new SimpleIntegerProperty(dose);
        this.minAge = new SimpleIntegerProperty(minAge);
        this.prescribeLevel = new SimpleIntegerProperty(prescribeLevel);
        this.substanceId = substanceId;
        this.manId = manId;
        this.price = new SimpleDoubleProperty(0);

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
        this.price = new SimpleDoubleProperty(0);
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

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public int getInventoryId() {
        return inventoryId;
    }
}
