package Unit;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Drug {
    private int drugId;
    private int substanceId;
    private int manId;
    private int inventoryId;
    private int prescribe;
    private SimpleStringProperty inventoryName;
    private SimpleStringProperty name;
    private SimpleIntegerProperty dose;
    private SimpleStringProperty prescribeLevel;
    private SimpleIntegerProperty minAge;
    private SimpleStringProperty manufacturer;
    private SimpleStringProperty substance;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty price;

    public Drug(int inventoryId, String inventoryName, int drugId, String name, int dose, int prescribeLevel,
                int minAge, String manufacturer, int quantity, Double price){
        this.inventoryId = inventoryId;
        this.drugId = drugId;
        this.inventoryName = new SimpleStringProperty(inventoryName);
        this.name = new SimpleStringProperty(name);
        this.dose = new SimpleIntegerProperty(dose);
        this.minAge = new SimpleIntegerProperty(minAge);
        this.prescribeLevel = parsePrescribeLevel(prescribeLevel);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
    }

    public Drug(int drugId, String name, int dose, int minAge, int prescribeLevel, int substanceId, int manId){
        this.drugId = drugId;
        this.name = new SimpleStringProperty(name);
        this.dose = new SimpleIntegerProperty(dose);
        this.minAge = new SimpleIntegerProperty(minAge);
        this.prescribe = prescribeLevel;
        this.prescribeLevel = parsePrescribeLevel(prescribeLevel);
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
        this.prescribeLevel = parsePrescribeLevel(prescribeLevel);
        this.substance = new SimpleStringProperty(substance);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.price = new SimpleDoubleProperty(0);
    }

    private SimpleStringProperty parsePrescribeLevel(int prescribe){
        if (prescribe == 0) {
            return new SimpleStringProperty("Green");
        }else if (prescribe == 1){
            return new SimpleStringProperty("Normal");
        }else if (prescribe > 1){
            return new SimpleStringProperty("Red");
        }else {
            return new SimpleStringProperty("Not Defined");
        }
    }

    public int getDrugId() {
        return drugId;
    }

    public int getSubstanceId() {
        return substanceId;
    }

    public int getManId() {
        return manId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public String getInventoryName() {
        return inventoryName.get();
    }

    public SimpleStringProperty inventoryNameProperty() {
        return inventoryName;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getDose() {
        return dose.get();
    }

    public SimpleIntegerProperty doseProperty() {
        return dose;
    }

    public int getPrescribeLevel() {
        return this.prescribe;
    }

    public SimpleStringProperty prescribeLevelProperty() {
        return prescribeLevel;
    }

    public int getMinAge() {
        return minAge.get();
    }

    public SimpleIntegerProperty minAgeProperty() {
        return minAge;
    }

    public String getManufacturer() {
        return manufacturer.get();
    }

    public SimpleStringProperty manufacturerProperty() {
        return manufacturer;
    }

    public String getSubstance() {
        return substance.get();
    }

    public SimpleStringProperty substanceProperty() {
        return substance;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }
}
