/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOA;

import Database.DBConnection;
import model.Division;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Adam Sindermann
 */
public class DBDivision {

    public static Division getDivision(int divisionID) {
        Connection conn = DBConnection.getConnection();
        Division division = new Division(-1, "", -1);
        try {
            String query = "SELECT Division, COUNTRY_ID FROM first_level_divisions WHERE Division_ID = ?";
            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, Integer.toString(divisionID));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");

                division = new Division(divisionID, name, countryID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return division;
    }

    public static ObservableList<Division> getAllDivisions(int countryID) {
        Connection conn = DBConnection.getConnection();
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ?";
            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Division");
                int divisionID = rs.getInt("Division_ID");

                Division division = new Division(divisionID, name, countryID);
                divisions.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return divisions;
    }

    public static Division getDivision(String divisionName) {
        Connection conn = DBConnection.getConnection();
        Division division = new Division(-1, "", -1);
        try {
            String query = "SELECT Division_ID, COUNTRY_ID FROM first_level_divisions WHERE Division = ?";
            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, divisionName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                int countryID = rs.getInt("Country_ID");

                division = new Division(divisionID, divisionName, countryID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return division;
    }

}
