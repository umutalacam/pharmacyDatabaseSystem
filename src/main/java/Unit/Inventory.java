package Unit;

public class Inventory {
    private int inventoryId;
    private int pharmacyId;
    private String inventoryName;

    public Inventory(int inventoryId, int pharmacyId, String inventoryName) {
        this.inventoryId = inventoryId;
        this.pharmacyId = pharmacyId;
        this.inventoryName = inventoryName;
    }

    public Inventory(int pharmacyId, String inventoryName) {
        this.pharmacyId = pharmacyId;
        this.inventoryName = inventoryName;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public int getPharmacyId() {
        return pharmacyId;
    }

    public String getInventoryName() {
        return inventoryName;
    }
}
