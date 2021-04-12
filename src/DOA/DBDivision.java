/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOA;

import Database.DBConnection;
import model.Division;
import java.sql.*;

/**
 *
 * @author Adam Sindermann
 */
public class DBDivision {
    
    public static Division getDivision(int divisionID){
        Connection conn = DBConnection.getConnection();
        Division division = new Division(-1, "", -1);
        try{
            String query = "SELECT Division, Country_ID FROM first_level_divisions WHERE Division_ID = ?";
            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ps.setString(1, Integer.toString(divisionID));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                String name = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                
                division = new Division(divisionID, name, countryID);
            } 
        } catch (SQLException e){
            e.printStackTrace();
        }
        return division;
    }
}
