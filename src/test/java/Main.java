import Database.Record;
import Unit.Pharmacy;

import java.sql.Connection;
import java.sql.Date;

public class Main {


    public static void main(String[] args) {
        Pharmacy pharmacy = new Pharmacy("g24 Eczanesi","Bir yerlerde", "+905007565656");
        Record.addNewInventory(3,"G24 Deposu");
    }


}
