/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import DOA.DBCustomer;
import com.mysql.cj.protocol.a.NativeConstants;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;
import model.Customer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

/**
 *
 * @author Adam Sindermann
 */
public class Alerts {
    public static void displayInputAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static boolean displayValidationAlert(String header, String body) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText(header);
        alert.setContentText(body);
        Optional<ButtonType> option = alert.showAndWait();

        return option.get() == ButtonType.OK;

    }

    public static void displayDeletedAppointments(ObservableList<Appointment> deletedList) {
        String header = "The following appointments have been deleted:";
        String body = "";
        for (Appointment appointment : deletedList) {
            String type = appointment.getType();
            Integer id = appointment.getAppointmentID();
            body = body + "Type: " + type + "\nID: " + id + "\n\n";
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(body);

        alert.showAndWait();
    }

    public static void displayOverlapError(Appointment overlappingAppointment) {
        Customer customer = DBCustomer.getCustomer(overlappingAppointment.getCustomerID());
        String header = customer.getName() + " already has an appointment scheduled for that time.";
        String body = "Overlapping Appointment:\n" +
                "Appointment ID: " + overlappingAppointment.getAppointmentID() +
                "\nType: " + overlappingAppointment.getType();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Appointment Overlap");
        alert.setHeaderText(header);
        alert.setContentText(body);

        alert.showAndWait();
    }

    public static void upcomingAppointmentAlert(Appointment appointment) {
        String header = "You have an upcoming appointment:";

        int id = appointment.getAppointmentID();
        LocalDate date = appointment.getStart().toLocalDate();
        LocalTime time = appointment.getStart().toLocalTime();
        String dateString = date.toString();
        String timeString = time.toString();
        String body = "Appointment ID: " + id +
                "\n Date: " + dateString +
                "\n Time: " + timeString;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upcoming Appointments");
        alert.setHeaderText(header);
        alert.setContentText(body);

        alert.showAndWait();
    }

    public static void noUpcomingAppointmentAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upcoming Appointments");
        alert.setHeaderText("You have no upcoming appointments");

        alert.showAndWait();
    }
}
