package DOA;

import Database.DBConnection;
import javafx.collections.ObservableList;
import model.Appointment;
import java.sql.*;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import utility.Session;

/**
 * Database accessor methods for Appointment table.  
 * @author Adam Sindermann
 */
public class DBAppointment {

    
    /**
     * Gets all appointments from DB and returns an ObservableList.
     *
     * @return ObservableList - All appointments in DB.
     */
    public static ObservableList<Appointment> getUserAppointments() {
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

    /**
     * Gets all appointments from DB and returns an ObservableList.
     *
     * @return ObservableList - All appointments in DB.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        Connection conn = DBConnection.getConnection();
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM appointments";

            DBQuery.setPreparedStatement(conn, query);
            PreparedStatement ps = DBQuery.getPreparedStatement();

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

    public static ObservableList<Appointment> getCustomerAppointments(int customerID) {
        ObservableList<Appointment> appointments = getAllAppointments();
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : appointments) {
            if (customerID == appointment.getCustomerID()) {
                customerAppointments.add(appointment);
            }
        }
        return customerAppointments;
    }

    /**
     * Saves a new appointment to DB.
     *
     * @param appointment Appointment - The new appointment to be saved.
     * @return Boolean - True if the appointment is saved.
     */
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
                ps.setInt(9, 0);
            } else {
                ps.setInt(9, appointment.getCustomerID());
            }
            if (appointment.getUserID() == 0) {
                ps.setInt(10, 0);
            } else {
                ps.setInt(10, appointment.getUserID());
            }
            if (appointment.getContactID() == 0) {
                ps.setInt(11, 0);
            } else {
                ps.setInt(11, appointment.getContactID());
            }
            executed = ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return executed;
    }

    /**
     * Saves an existing appointment to the DB. 
     * @param appointment Appointment - The appointment to be updated. 
     * @return Boolean - True if the appointment is updated. 
     */
    public static boolean update(Appointment appointment) {
        Connection conn = DBConnection.getConnection();
        boolean executed = false;
        try {
            String update = "UPDATE appointments SET Title = ?, Description = ?, "
                    + "Location = ?, Type = ?, Start = ?, End = ?,"
                    + " Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? "
                    + "WHERE Appointment_ID = ?;";
            DBQuery.setPreparedStatement(conn, update);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            Timestamp start = Timestamp.valueOf(appointment.getStart());
            Timestamp end = Timestamp.valueOf(appointment.getEnd());

            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setString(7, Session.getCurrentUser().getUserName());

            if (appointment.getCustomerID() == 0) {
                ps.setString(8, null);
            } else {
                ps.setInt(8, appointment.getCustomerID());
            }
            if (appointment.getUserID() == 0) {
                ps.setString(9, null);
            } else {
                ps.setInt(9, appointment.getUserID());
            }
            if (appointment.getContactID() == 0) {
                ps.setString(10, null);
            } else {
                ps.setInt(10, appointment.getContactID());
            }
            ps.setInt(11, appointment.getAppointmentID());
            executed = ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return executed;
    }

    public static boolean delete(Appointment appointment) {
        Connection conn = DBConnection.getConnection();
        boolean executed = false;
        try {
            String delete = "DELETE FROM appointments WHERE Appointment_ID = ?";
            DBQuery.setPreparedStatement(conn, delete);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, appointment.getAppointmentID());
            executed = ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();


        }
        return executed;
    }
}
