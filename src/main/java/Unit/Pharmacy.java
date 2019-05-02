package Unit;

public class Pharmacy {
    private int phar_id;
    private String name;
    private String adress;
    private int telephone;

    public Pharmacy(int phar_id, String name, String adress, int telephone){
        this.phar_id = phar_id;
        this.name = name;
        this.adress = adress;
        this.telephone = telephone;
    }

    public int getPhar_id() {
        return phar_id;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public int getTelephone() {
        return telephone;
    }
}
