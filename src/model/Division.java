package model;

/**
 *
 * @author Adam Sindermann
 */
public class Division {
    private int divisionID;
    private String name;
    private int countryID;

    public Division(int divisionID, String name, int countryID) {
        this.divisionID = divisionID;
        this.name = name;
        this.countryID = countryID;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    
    
}
