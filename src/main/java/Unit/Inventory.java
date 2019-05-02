package Unit;

import java.util.Date;

public class Inventory {

    private int inv_id;
    private int phar_id;
    private int drug_id;
    private int quantity;
    private Date expiryDate;

    public Inventory(int inv_id, int phar_id, int drug_id, int quantity, Date expiryDate) {
        this.inv_id = inv_id;
        this.phar_id = phar_id;
        this.drug_id = drug_id;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public int getInv_id() {
        return inv_id;
    }

    public int getPhar_id() {
        return phar_id;
    }

    public int getDrug_id() {
        return drug_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
