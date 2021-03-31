package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Adam Sindermann
 */
public class DBCountry {
    
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> clist = FXCollections.observableArrayList();
        
        try{
            String sql = "SELECT * from countries";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
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
    
}
