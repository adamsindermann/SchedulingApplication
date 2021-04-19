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
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import utility.Session;

/**
 *
 * @author Adam Sindermann
 */
public class DBAppointment {

    public static ObservableList<Appointment> getAllAppointments() {
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

            while (rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startStamp = rs.getTimestamp("Start");
                Timestamp endStamp = rs.getTimestamp("End");
                int apptCustID = rs.getInt("Customer_ID");
                int apptUserID = rs.getInt("User_ID");
                int apptContactID = rs.getInt("Contact_ID");

                LocalDateTime start = startStamp.toLocalDateTime();
                LocalDateTime end = endStamp.toLocalDateTime();

                Appointment appointment = new Appointment(apptID, title, description, location, type, start, end, apptCustID, apptUserID, apptContactID);
                allAppointments.add(appointment);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    public static boolean save(Appointment appointment) {
        Connection conn = DBConnection.getConnection();
        boolean executed = false;
        try {
            String insert = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type, Start, End, "
                    + "Created_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
            DBQuery.setPreparedStatement(conn, insert);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            Timestamp start = Timestamp.valueOf(appointment.getStart());
            Timestamp end = Timestamp.valueOf(appointment.getEnd());

            ps.setInt(1, appointment.getAppointmentID());
            ps.setString(2, appointment.getTitle());
            ps.setString(3, appointment.getDescription());
            ps.setString(4, appointment.getLocation());
            ps.setString(5, appointment.getType());
            ps.setTimestamp(6, start);
            ps.setTimestamp(7, end);
            ps.setString(8, Session.getCurrentUser().getUserName());
            if (appointment.getCustomerID() == 0) {
                ps.setString(9, null);
            } else {
                ps.setInt(9, appointment.getCustomerID());
            }
            ps.setInt(10, appointment.getUserID());
            if (appointment.getContactID() == 0) {
                ps.setString(11, null);
            } else {
                ps.setInt(11, appointment.getContactID());
            }
            executed = ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return executed;
    }
}
