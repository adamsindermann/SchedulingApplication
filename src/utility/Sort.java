/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import DOA.DBAppointment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

/**
 *
 * @author Adam Sindermann
 */
public class Sort {

        private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
    public static String getWeek(LocalDateTime dayInWeek) {
        LocalDateTime weekStart = getWeekStart(dayInWeek);
        LocalDateTime weekEnd = getWeekEnd(dayInWeek);
        String weekEndString = weekEnd.format(dateFormatter);
        String weekStartString = weekStart.format(dateFormatter);
        
        return weekStartString + " - " + weekEndString;

    }
    
    public static LocalDateTime getWeekStart(LocalDateTime dayInWeek){
        LocalDateTime weekStart = dayInWeek.minusDays(dayInWeek.getDayOfWeek().getValue());
        weekStart = weekStart.minusHours(weekStart.getHour());
        weekStart = weekStart.minusMinutes(weekStart.getMinute());
        weekStart =  weekStart.minusSeconds(weekStart.getSecond());
        return weekStart.minusNanos(weekStart.getNano());
    }
    
    public static LocalDateTime getWeekEnd(LocalDateTime dayInWeek){
        LocalDateTime weekEnd = getWeekStart(dayInWeek).plusDays(6);
        weekEnd = weekEnd.plusHours(23);
        weekEnd = weekEnd.plusMinutes(59);
        return weekEnd.plusSeconds(59);
       
    }
    
    public static ObservableList<Appointment> sortByWeek(LocalDateTime dayInWeek){
        ObservableList<Appointment> allAppointments = DBAppointment.getAllAppointments();
        ObservableList<Appointment> sorted = FXCollections.observableArrayList();
        
        LocalDateTime weekStart = getWeekStart(dayInWeek);
        LocalDateTime weekEnd = getWeekEnd(dayInWeek);
        
        for (Appointment appointment: allAppointments){
            LocalDateTime appointmentDate = appointment.getStart();
            if (appointmentDate.isAfter(weekStart) || appointmentDate.equals(weekStart)){
                if(appointmentDate.isBefore(weekEnd) || appointmentDate.isEqual(weekEnd)){
                    sorted.add(appointment);
                }
            }
        }
        
        return sorted;
    }
}
