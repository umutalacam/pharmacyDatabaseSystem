package Unit;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Transaction {

    int buyer_id;
    private SimpleStringProperty buyerFirstName;
    private SimpleStringProperty buyerLastName;
    private SimpleStringProperty drugName;
    private SimpleIntegerProperty drugDose;
    private SimpleStringProperty drugManufacturer;
    private SimpleIntegerProperty drugQuantity;
    private SimpleStringProperty transactionDate;
    private SimpleStringProperty drugCost;

    private Date date;

    public Transaction(int buyer_id, String buyerFirstName, String buyerLastName,
                       String drugName, int drugDose, String drugManufacturer,
                       int drugQuantity, Date date, double cost) {
        this.buyer_id = buyer_id;
        this.buyerFirstName = new SimpleStringProperty(buyerFirstName);
        this.buyerLastName = new SimpleStringProperty(buyerLastName);
        this.drugName = new SimpleStringProperty(drugName);
        this.drugDose = new SimpleIntegerProperty(drugDose);
        this.drugManufacturer = new SimpleStringProperty(drugManufacturer);
        this.drugQuantity = new SimpleIntegerProperty(drugQuantity);
        this.date = date;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.drugCost = new SimpleStringProperty(cost+"");
        this.transactionDate = new SimpleStringProperty(dateFormat.format(this.date));
    }

    public String getBuyerFirstName() {
        return buyerFirstName.get();
    }

    public SimpleStringProperty buyerFirstNameProperty() {
        return buyerFirstName;
    }

    public String getBuyerLastName() {
        return buyerLastName.get();
    }

    public SimpleStringProperty buyerLastNameProperty() {
        return buyerLastName;
    }

    public String getDrugName() {
        return drugName.get();
    }

    public SimpleStringProperty drugNameProperty() {
        return drugName;
    }

    public int getDrugDose() {
        return drugDose.get();
    }

    public SimpleIntegerProperty drugDoseProperty() {
        return drugDose;
    }

    public String getDrugManufacturer() {
        return drugManufacturer.get();
    }

    public SimpleStringProperty drugManufacturerProperty() {
        return drugManufacturer;
    }

    public int getDrugQuantity() {
        return drugQuantity.get();
    }

    public SimpleIntegerProperty drugQuantityProperty() {
        return drugQuantity;
    }

    public String getTransactionDate() {
        return transactionDate.get();
    }

    public SimpleStringProperty transactionDateProperty() {
        return transactionDate;
    }

    public Date getDate() {
        return date;
    }

    public String getDrugCost() {
        return drugCost.get();
    }

    public SimpleStringProperty drugCostProperty() {
        return drugCost;
    }

    public int getBuyer_id() {
        return buyer_id;
    }
}
