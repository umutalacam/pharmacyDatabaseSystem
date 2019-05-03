package Unit;

public class Pharmacy implements User{
    private int phar_id;
    private String name;
    private String adress;
    private String telephone;

    public Pharmacy(int phar_id, String name, String adress, String telephone){
        this.phar_id = phar_id;
        this.name = name;
        this.adress = adress;
        this.telephone = telephone;
    }

    public Pharmacy(String name, String adress, String telephone) {
        this.phar_id = 0;
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

    public String getTelephone() {
        return telephone;
    }
}
