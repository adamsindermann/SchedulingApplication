/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOA;

import Database.DBConnection;
import javafx.collections.ObservableList;
import model.Appointment;
import java.sql.*;
import javafx.collections.FXCollections;
import utility.Session;

/**
 *
 * @author Adam Sindermann
 */
public class DBAppointment {
    public static ObservableList<Appointment> getAllAppointments(){
        Connection conn = DBConnection.getConnection();
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM appointments WHERE User_ID = ?";
            
            
            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            //Get Currently logged in user ID
            String userID = Integer.toString(Session.getCurrentUser().getUserID());
            ps.setString(1, userID);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int apptCustID = rs.getInt("Customer_ID");
                int apptUserID = rs.getInt("User_ID");
                int apptContactID = rs.getInt("Contact_ID");
                
                
                
                Appointment appointment = new Appointment(apptID, title, description, location, type, start, end, apptCustID, apptUserID, apptContactID);
                allAppointments.add(appointment);
                
            } 
        } catch (SQLException e){
            e.printStackTrace();
        }
        return allAppointments;
    }
}
