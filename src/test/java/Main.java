import Database.Record;
import Unit.Pharmacy;

import java.sql.Connection;
import java.sql.Date;

public class Main {


    public static void main(String[] args) {
        Pharmacy pharmacy = new Pharmacy(2,"Bahar Eczanesi","Havlucu Sk, ğüşiçö Sk. No:1978", "+905070837523");
        Record.updatePharmacy(pharmacy);
    }
}
