import Database.Record;
import Unit.Inventory;
import Unit.Patient;
import Unit.Pharmacy;

import java.sql.Connection;
import java.sql.Date;

public class Main {


    public static void main(String[] args) {
        Pharmacy pharmacy = new Pharmacy("g24 Eczanesi","Bir yerlerde", "+905007565656");
        Inventory inventory = new Inventory(7,3,"G24 deposu");

        Date expirydate = new Date(System.currentTimeMillis());
        inventory.addDrug(1,100,expirydate);
    }


}
