package DOA;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import utility.WindowInterface;

/**
 *
 * @author Adam Sindermann
 */
public class DBCountry {

    private final WindowInterface loader = location -> {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(location));
        return loader;
    };
    
    private final String appointmentWindow ="/view/Appointment.fxml";

    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country c = new Country(countryId, countryName);
                clist.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clist;
    }

    public static Country getCountry(int countryID) {
        Connection conn = DBConnection.getConnection();
        Country country = new Country(-1, "");
        try {
            String query = "SELECT Country FROM countries WHERE Country_ID = ?";
            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, Integer.toString(countryID));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Country");

                country = new Country(countryID, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return country;
    }
    
    public static Country getCountry(String countryName){
        Connection conn = DBConnection.getConnection();
        Country country = new Country(-1, "");
        try {
            String query = "SELECT * FROM countries WHERE Country = ?";
            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, countryName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");

                country = new Country(countryID, countryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return country;
    }

}
