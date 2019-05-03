package Unit;

public class Drug {
    private int drugId;
    private String name;
    private int dose;
    private int minAge;
    private int prescribeLevel;
    private String substance;
    private int manId;

    public Drug(int drugId, String name, int dose, int minAge, int prescribeLevel, String substance, int manId) {
        this.drugId = drugId;
        this.name = name;
        this.dose = dose;
        this.minAge = minAge;
        this.prescribeLevel = prescribeLevel;
        this.substance = substance;
        this.manId = manId;
    }

    public int getDrugId() {
        return drugId;
    }

    public String getName() {
        return name;
    }

    public int getDose() {
        return dose;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getPrescribeLevel() {
        return prescribeLevel;
    }

    public String getSubstance() {
        return substance;
    }

    public int getManId() {
        return manId;
    }
}
