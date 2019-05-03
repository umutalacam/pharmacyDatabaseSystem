import Database.Record;
import Unit.Inventory;
import Unit.Pharmacy;

import java.sql.Connection;

public class Main {


    public static void main(String[] args) {
        Connection conn = Database.Connector.connect();
        Pharmacy pharmacy = new Pharmacy(1, "Faruk Pharmacy",
                "2200 Eskisehir", 837523);

        Record.addNewInventory(pharmacy);
    }
}
