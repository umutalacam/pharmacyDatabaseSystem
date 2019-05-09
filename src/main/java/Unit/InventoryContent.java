package Unit;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class InventoryContent {
    private SimpleStringProperty drugName;
    private SimpleIntegerProperty dose;
    private SimpleStringProperty manufacturerName;
    private SimpleStringProperty expiryDate;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty price;

    public InventoryContent(String drugName, int dose, String manufacturerName,
                            Date expiryDate, int quantity, double price){
        this.drugName = new SimpleStringProperty(drugName);
        this.dose = new SimpleIntegerProperty(dose);
        this.manufacturerName = new SimpleStringProperty(manufacturerName);
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        this.expiryDate = new SimpleStringProperty(dateFormat.format(expiryDate));
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getDrugName() {
        return drugName.get();
    }

    public SimpleStringProperty drugNameProperty() {
        return drugName;
    }

    public int getDose() {
        return dose.get();
    }

    public SimpleIntegerProperty doseProperty() {
        return dose;
    }

    public String getManufacturerName() {
        return manufacturerName.get();
    }

    public SimpleStringProperty manufacturerNameProperty() {
        return manufacturerName;
    }

    public String getExpiryDate() {
        return expiryDate.get();
    }

    public SimpleStringProperty expiryDateProperty() {
        return expiryDate;
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

    @Override
    public String toString() {
        return super.toString();
    }
}
