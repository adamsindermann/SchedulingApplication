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
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Attendee;
import model.Contact;
import model.Customer;
import utility.Session;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

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
        if (appointment.getCustomerID() != 0){
            int custID = appointment.getCustomerID();
            Customer customer = DBCustomer.getCustomer(custID);
            customerCombo.setValue(custID + " - " + customer.getName());
            addCustomer();
        }
        
        if(appointment.getContactID() != 0){
            int contactID = appointment.getContactID();
            Contact contact = DBContact.getContact(contactID);
            contactCombo.setValue(contactID + " - " + contact.getName());
            addContact();
        }
    }

    /**
     * Converts 24 hour time to 12 hour and sets it to the ChoiceBox.
     *
     * @param hourBox ChoiceBox - The ChoiceBox that the hour will be set to.
     * @param amPm ChoiceBox - the ChoiceBox that will be set to either AM or
     * PM.
     * @param hour Integer - The hour value to be converted and set.
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
     * to.
     * @param minute Integer - The minute value that will be set.
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

        customerCombo.getItems().add("New Customer +");
        contactCombo.getItems().add("New Contact +");
    }

    /**
     * Initializes the Attendee TableView. 
     */
    public void initTableView() {
        nameCol.setCellValueFactory(new PropertyValueFactory<Attendee, String>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Attendee, String>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Attendee, String>("phone"));

        attendeeTable.setItems(attendeeList);
    }

    public void comboBoxSelection() {
        if (customerCombo.getSelectionModel().getSelectedItem().equals("New Customer +")) {
            System.out.println("New Selected");
        }
    }

    /**
     * Gets customer from Customer ComboBox and adds it to the Attendee table. 
     * Also converts customer object to Attendee and updates the current appointment
     * ID with the selected customer ID. 
     */
    public void addCustomer() {
        if (!customerCombo.getSelectionModel().isEmpty()) {
            String customerString = customerCombo.getValue().toString();
            String custIDString = customerString.substring(0, customerString.indexOf(" "));
            int custID = Integer.parseInt(custIDString);
            thisAppointment.setCustomerID(custID);
            Customer customer = DBCustomer.getCustomer(custID);
            Attendee attendee = new Attendee(customer.getName());
            attendee.setPhone(customer.getPhone());
            attendeeList.add(attendee);
        }

    }

    public void addContact() {
        if (!contactCombo.getSelectionModel().isEmpty()) {
            String contactString = contactCombo.getValue().toString();
            String ContactIDString = contactString.substring(0, contactString.indexOf(" "));
            int contactID = Integer.parseInt(ContactIDString);
            thisAppointment.setContactID(contactID);
            Contact contact = DBContact.getContact(contactID);
            Attendee attendee = new Attendee(contact.getName());
            attendee.setEmail(contact.getEmail());
            attendeeList.add(attendee);
        }
    }

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

    public void save() {
        thisAppointment.setTitle(subjectBox.getText());
        thisAppointment.setType(typeBox.getText());
        thisAppointment.setLocation(locationBox.getText());
        thisAppointment.setDescription(descBox.getText());
        int startH = Integer.parseInt(startHour.getValue().toString());
        int endH = Integer.parseInt(endHour.getValue().toString());
        int startM = Integer.parseInt(startMin.getValue().toString());
        int endM = Integer.parseInt(endMin.getValue().toString());
        if (startAmPm.getValue().toString().equals("PM")) {
            startH = startH + 12;
        }
        if (endAmPm.getValue().toString().equals("PM")) {
            endH = endH + 12;
        }

        LocalDate date = datePicker.getValue();
        LocalTime startTime = LocalTime.of(startH, startM);
        LocalTime endTime = LocalTime.of(endH, endM);
        thisAppointment.setStart(LocalDateTime.of(date, startTime));
        thisAppointment.setEnd(LocalDateTime.of(date, endTime));
        if(!editing){
            boolean saved = DBAppointment.save(thisAppointment);
        } else {
            boolean saved =DBAppointment.update(thisAppointment);
        }

        Stage stage = (Stage) subjectBox.getScene().getWindow();
        stage.close();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!editing) {
            int apptID = DBAppointment.getAllAppointments().get(DBAppointment.getAllAppointments().size() - 1).getAppointmentID() + 1;
            thisAppointment = new Appointment(apptID);
            thisAppointment.setUserID(Session.getCurrentUser().getUserID());
            idBox.setText(Integer.toString(apptID));

        }
        attendeeList.add(new Attendee(Session.getCurrentUser().getUserName()));
        initChoiceBoxes();
        initComboBoxes();
        initTableView();
    }

}
