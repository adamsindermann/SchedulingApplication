/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DOA.DBAppointment;
import DOA.DBContact;
import DOA.DBCustomer;

import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Appointment;
import model.Attendee;
import model.Contact;
import model.Customer;
import utility.Alerts;
import utility.Session;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.WindowInterface;


/**
 * FXML Controller class
 *
 * @author Adam Sindermann
 */
public class AppointmentController implements Initializable {

    private Boolean editing = false;
    private Appointment thisAppointment;
    @FXML
    private Label headerLabel;

    //Time and Date
    @FXML
    private ChoiceBox startHour;
    @FXML
    private ChoiceBox startMin;
    @FXML
    private ChoiceBox startAmPm;
    @FXML
    private ChoiceBox endHour;
    @FXML
    private ChoiceBox endMin;
    @FXML
    private ChoiceBox endAmPm;
    @FXML
    private DatePicker datePicker;

    //ComboBoxes
    @FXML
    private ComboBox customerCombo;
    @FXML
    private ComboBox contactCombo;

    //Buttons
    @FXML
    private Button addCustomer;
    @FXML
    private Button addContact;

    //Table
    @FXML
    private TableView attendeeTable;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn emailCol;
    @FXML
    private TableColumn phoneCol;
    private ObservableList<Attendee> attendeeList = FXCollections.observableArrayList();

    //Text Boxes 
    @FXML
    private TextField idBox;
    @FXML
    private TextField subjectBox;
    @FXML
    private TextField typeBox;
    @FXML
    private TextField locationBox;
    @FXML
    private TextArea descBox;

    //Required field stars
    @FXML
    private Label titleStar;
    @FXML
    private Label typeStar;
    @FXML
    private Label locationStar;
    @FXML
    private Label customerStar;
    @FXML
    private Label dateStar;
    @FXML
    private Label startStar;
    @FXML
    private Label endStar;
    @FXML
    private Label contactStar;

    private final WindowInterface loader = location -> {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(location));
        return loader;
    };

    public void editAppointment(Appointment appointment) {
        LocalDateTime start = appointment.getStart();
        LocalDateTime end = appointment.getEnd();

        editing = true;
        headerLabel.setText("Edit Appointment");
        thisAppointment = appointment;
        idBox.setText(Integer.toString(appointment.getAppointmentID()));
        subjectBox.setText(appointment.getTitle());
        typeBox.setText(appointment.getType());
        locationBox.setText(appointment.getLocation());
        descBox.setText(appointment.getDescription());
        datePicker.setValue(start.toLocalDate());
        setHourValue(startHour, startAmPm, start.getHour());
        setHourValue(endHour, endAmPm, end.getHour());
        setMinuteValue(startMin, start.getMinute());
        setMinuteValue(endMin, end.getMinute());
        if (appointment.getCustomerID() != 0) {
            int custID = appointment.getCustomerID();
            Customer customer = DBCustomer.getCustomer(custID);
            customerCombo.setValue(custID + " - " + customer.getName());
            thisAppointment.setCustomerID(custID);
        }

        if (appointment.getContactID() != 0) {
            int contactID = appointment.getContactID();
            Contact contact = DBContact.getContact(contactID);
            contactCombo.setValue(contactID + " - " + contact.getName());
            thisAppointment.setCustomerID(contactID);
        }
    }

    /**
     * Converts 24 hour time to 12 hour and sets it to the ChoiceBox.
     *
     * @param hourBox ChoiceBox - The ChoiceBox that the hour will be set to.
     * @param amPm    ChoiceBox - the ChoiceBox that will be set to either AM or
     *                PM.
     * @param hour    Integer - The hour value to be converted and set.
     */
    public void setHourValue(ChoiceBox hourBox, ChoiceBox amPm, int hour) {
        if (hour == 12) {
            amPm.setValue("PM");
            hourBox.setValue(12);
        } else if (hour > 12) {
            amPm.setValue("PM");
            hourBox.setValue(hour - 12);
        } else {
            amPm.setValue("AM");
            hourBox.setValue(hour);
        }
    }

    /**
     * Sets a minute value to a ChoiceBox.
     *
     * @param minuteBox ChoiceBox - The ChoiceBox that the minute will be set
     *                  to.
     * @param minute    Integer - The minute value that will be set.
     */
    public void setMinuteValue(ChoiceBox minuteBox, int minute) {
        if (minute == 0) {
            minuteBox.setValue("00");
        } else {
            minuteBox.setValue(minute);
        }
    }

    /**
     * Initializes ChoiceBoxes.
     */
    @SuppressWarnings("unchecked")
    public void initChoiceBoxes() {
        startMin.getItems().add("00");
        endMin.getItems().add("00");
        for (int i = 1; i < 13; i++) {
            startHour.getItems().add(i);
            endHour.getItems().add(i);
        }

        for (int i = 1; i < 60; i++) {
            startMin.getItems().add(i);
            endMin.getItems().add(i);
        }

        startAmPm.getItems().add("AM");
        endAmPm.getItems().add("AM");
        startAmPm.getItems().add("PM");
        endAmPm.getItems().add("PM");
        startAmPm.setValue("AM");
        endAmPm.setValue("AM");

    }

    /**
     * Initializes ComboBoxes.
     */
    @SuppressWarnings("unchecked")
    public void initComboBoxes() {
        ObservableList<Customer> allCustomers = DBCustomer.getAllCustomers();
        ObservableList<Contact> allContacts = DBContact.getAllContacts();
        for (Customer customer : allCustomers) {
            customerCombo.getItems().add(customer.getCustID() + " - " + customer.getName());
        }

        for (Contact contact : allContacts) {
            contactCombo.getItems().add(contact.getContactID() + " - " + contact.getName());
        }

    }

    /**
     * Initializes the Attendee TableView.
     */
//    public void initTableView() {
//        nameCol.setCellValueFactory(new PropertyValueFactory<Attendee, String>("name"));
//        emailCol.setCellValueFactory(new PropertyValueFactory<Attendee, String>("email"));
//        phoneCol.setCellValueFactory(new PropertyValueFactory<Attendee, String>("phone"));
//
//        attendeeTable.setItems(attendeeList);
//    }


    /**
     * Gets customer from Customer ComboBox and adds it to the Attendee table.
     * Also converts customer object to Attendee and updates the current
     * appointment ID with the selected customer ID.
     */
//    public void addCustomer() {
//        if (!customerCombo.getSelectionModel().isEmpty()) {
//            String customerString = customerCombo.getValue().toString();
//            String custIDString = customerString.substring(0, customerString.indexOf(" "));
//            int custID = Integer.parseInt(custIDString);
//            thisAppointment.setCustomerID(custID);
//            Customer customer = DBCustomer.getCustomer(custID);
//            Attendee attendee = new Attendee(customer.getName());
//            attendee.setPhone(customer.getPhone());
//            attendeeList.add(attendee);
//        }
//
//    }
//
//    public void addContact() {
//        if (!contactCombo.getSelectionModel().isEmpty()) {
//            String contactString = contactCombo.getValue().toString();
//            String ContactIDString = contactString.substring(0, contactString.indexOf(" "));
//            int contactID = Integer.parseInt(ContactIDString);
//            thisAppointment.setContactID(contactID);
//            Contact contact = DBContact.getContact(contactID);
//            Attendee attendee = new Attendee(contact.getName());
//            attendee.setEmail(contact.getEmail());
//            attendeeList.add(attendee);
//        }
//    }
    public void remove() {
        boolean itemSelected = !attendeeTable.getSelectionModel().getSelectedItems().isEmpty();
        if (itemSelected) {
            Attendee selected = (Attendee) attendeeTable.getSelectionModel().getSelectedItem();
            if (selected.getType() == 0) {
                thisAppointment.setCustomerID(0);
            } else if (selected.getType() == 1) {
                thisAppointment.setContactID(0);
            }
            attendeeList.remove(selected);
            //attendeeTable.setItems(attendeeList);
        }
    }

    public LocalTime getStartTime() {
        int startH = Integer.parseInt(startHour.getValue().toString());
        int startM = Integer.parseInt(startMin.getValue().toString());
        if (startAmPm.getValue().toString().equals("PM")) {
            startH = startH + 12;
        }
        return LocalTime.of(startH, startM);
    }

    public LocalTime getEndTime() {
        int endH = Integer.parseInt(endHour.getValue().toString());
        int endM = Integer.parseInt(endMin.getValue().toString());
        if (endAmPm.getValue().toString().equals("PM")) {
            endH = endH + 12;
        }

        return LocalTime.of(endH, endM);
    }

    public void save() {
        Boolean formFilled = validateFill();
        if (formFilled) {
            if (validateTime() && !overlappingAppointments()) {
                thisAppointment.setTitle(subjectBox.getText());
                thisAppointment.setType(typeBox.getText());
                thisAppointment.setLocation(locationBox.getText());
                thisAppointment.setDescription(descBox.getText());


                if (!customerCombo.getSelectionModel().isEmpty()) {
                    String customerString = customerCombo.getValue().toString();
                    String custIDString = customerString.substring(0, customerString.indexOf(" "));
                    int custID = Integer.parseInt(custIDString);
                    thisAppointment.setCustomerID(custID);
                }

                if (!contactCombo.getSelectionModel().isEmpty()) {
                    String contactString = contactCombo.getValue().toString();
                    String contactIDString = contactString.substring(0, contactString.indexOf(" "));
                    int contactID = Integer.parseInt(contactIDString);
                    thisAppointment.setContactID(contactID);
                }

                //*--------Can be refactored into function that returns time-----*
                LocalDate date = datePicker.getValue();
                thisAppointment.setStart(LocalDateTime.of(date, getStartTime()));
                thisAppointment.setEnd(LocalDateTime.of(date, getEndTime()));
                //*--------------------------------------------------------------*

                System.out.println(thisAppointment.toString());
                if (!editing) {
                    boolean saved = DBAppointment.save(thisAppointment);
                } else {
                    boolean saved = DBAppointment.update(thisAppointment);
                }

                Stage stage = (Stage) subjectBox.getScene().getWindow();
                stage.close();
            }
        } else if (!formFilled) {
            Alerts.displayInputAlert("Please enter all required fields");
        }

    }

    public boolean validateFill() {
        boolean valid = true;
        if (subjectBox.getText().isEmpty()) {
            valid = false;
            titleStar.setVisible(true);
        }
        if (typeBox.getText().isEmpty()) {
            valid = false;
            typeStar.setVisible(true);
        }
        if (locationBox.getText().isEmpty()) {
            valid = false;
            locationStar.setVisible(true);
        }
        if (datePicker.getValue() == null) {
            valid = false;
            dateStar.setVisible(true);
        }
        if (startHour.getSelectionModel().isEmpty() || startMin.getSelectionModel().isEmpty()
                || startAmPm.getSelectionModel().isEmpty()) {
            valid = false;
            startStar.setVisible(true);
        }
        if (endHour.getSelectionModel().isEmpty() || endMin.getSelectionModel().isEmpty()
                || endAmPm.getSelectionModel().isEmpty()) {
            valid = false;
            endStar.setVisible(true);
        }
        if (customerCombo.getSelectionModel().isEmpty()) {
            valid = false;
            customerStar.setVisible(true);
        }
        if (contactCombo.getSelectionModel().isEmpty()) {
            valid = false;
            contactStar.setVisible(true);
        }

        return valid;
    }

    public boolean validateTime() {

        LocalDate date = datePicker.getValue();

        LocalDateTime startDateTime = LocalDateTime.of(date, getStartTime());
        LocalDateTime endDateTime = LocalDateTime.of(date, getEndTime());

        //Convert LocalDateTime to ZonedDateTime
        ZoneId ESTId = ZoneId.of("America/New_York");
        ZoneId localZoneID = ZoneId.of(Session.getZoneID().toString());
        ZonedDateTime startZonedDateTime = ZonedDateTime.of(startDateTime, localZoneID);
        ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime, localZoneID);
        ZonedDateTime startEST = startZonedDateTime.withZoneSameInstant(ESTId);
        ZonedDateTime endEST = endZonedDateTime.withZoneSameInstant(ESTId);

        //Set LocalDateTime of business hours in EST

        LocalDateTime open = LocalDateTime.of(date, LocalTime.of(8, 0));
        LocalDateTime close = LocalDateTime.of(date, LocalTime.of(22, 0));
        ZonedDateTime zonedOpen = ZonedDateTime.of(open, ESTId);
        ZonedDateTime zonedClose = ZonedDateTime.of(close, ESTId);


        if (endEST.isBefore(startEST)) {
            Alerts.displayInputAlert("End Time Must Be After Start Time");
            return false;
        }
        if (startEST.isBefore(zonedOpen) || startEST.isAfter(zonedClose) || endEST.isAfter(zonedClose)
                || endEST.isBefore(zonedOpen)) {
            Alerts.displayInputAlert("Appointment is outside of business hours (8am - 10pm EST)");
            return false;
        }


        return true;


    }

    public boolean overlappingAppointments() {
        //Get All Customer Appointments
        String customerString = customerCombo.getValue().toString();
        String custIDString = customerString.substring(0, customerString.indexOf(" "));
        int custID = Integer.parseInt(custIDString);

        LocalDateTime start = LocalDateTime.of(datePicker.getValue(), getStartTime());
        LocalDateTime end = LocalDateTime.of(datePicker.getValue(), getEndTime());

        ObservableList<Appointment> customerAppointments = DBAppointment.getCustomerAppointments(custID);
        Appointment overlappingAppointment = null;
        boolean appointmentOverlap = false;
        if (!customerAppointments.isEmpty()) {
            for (Appointment appointment : customerAppointments) {
                boolean startOverlap = appointment.getStart().isAfter(start) && appointment.getStart().isBefore(end);
                boolean startEqual = appointment.getStart().equals(start);
                boolean endEqual = appointment.getEnd().equals(end);
                boolean endOverlap = appointment.getEnd().isAfter(start) && appointment.getEnd().isBefore(end);
                if (startOverlap || endOverlap || startEqual || endEqual) {
                    overlappingAppointment = appointment;
                    appointmentOverlap = true;
                }
            }
        }

        if (appointmentOverlap) {
            Alerts.displayOverlapError(overlappingAppointment);
        }

        return appointmentOverlap;

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!editing) {
            int apptID;
            if (!DBAppointment.getAllAppointments().isEmpty()) {
                apptID = DBAppointment.getAllAppointments().get(DBAppointment.getAllAppointments().size() - 1).getAppointmentID() + 1;
            } else {
                apptID = 1;
            }
            thisAppointment = new Appointment(apptID);
            thisAppointment.setUserID(Session.getCurrentUser().getUserID());
            idBox.setText(Integer.toString(apptID));

        }
//        attendeeList.add(new Attendee(Session.getCurrentUser().getUserName()));
        initChoiceBoxes();
        initComboBoxes();
//        initTableView();
    }

}
