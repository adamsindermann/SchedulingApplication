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
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author Adam Sindermann
 */
public class AppointmentController implements Initializable {
    private Boolean editing = false;
    private Appointment thisAppointment;
    //CoiceBoxes
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
    
    //ComboBoxes
    @FXML private ComboBox customerCombo;
    @FXML private ComboBox contactCombo;
    
    //Buttons
    @FXML private Button addCustomer;
    @FXML private Button addContact;
    
    //Table
    
    @FXML private TableView attendeeTable;
    @FXML private TableColumn nameCol;
    @FXML private TableColumn emailCol;
    @FXML private TableColumn phoneCol;
    private ObservableList<Attendee> attendeeList = FXCollections.observableArrayList();
    
    //Text Boxes 
    @FXML private TextField idField;
    
    

    public void initChoiceBoxes() {
        for (int i = 1; i < 13; i++){
            startHour.getItems().add(i);
            endHour.getItems().add(i);
        }
        
        for (int i = 1; i < 61; i++){
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

    public void initComboBoxes(){
        ObservableList<Customer> allCustomers = DBCustomer.getAllCustomers();
        ObservableList<Contact> allContacts = DBContact.getAllContacts();
        for (Customer customer: allCustomers){
            customerCombo.getItems().add(customer.getCustID() + " - " + customer.getName());
        }
        
        for(Contact contact: allContacts){
            contactCombo.getItems().add(contact.getContactID() + " - " + contact.getName());
        }
        
        customerCombo.getItems().add("New Customer +");
        contactCombo.getItems().add("New Contact +");
    }
    
    public void comboBoxSelection(){
        if (customerCombo.getSelectionModel().getSelectedItem().equals("New Customer +")){
            System.out.println("New Selected");
        }
    }
    
    public void initTableView(){
        nameCol.setCellValueFactory(new PropertyValueFactory<Attendee, String>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Attendee, String>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Attendee, String>("phone"));
        
        attendeeTable.setItems(attendeeList);
    }
    
    public void addCustomer(){
        String customerString = customerCombo.getValue().toString();
        String custIdString = customerString.substring(0, customerString.indexOf(" "));
        int custID = Integer.parseInt(custIdString);
        thisAppointment.setCustomerID(custID);
        Customer customer = DBCustomer.getCustomer(custID);
        Attendee attendee = new Attendee(customer.getName());
        attendee.setPhone(customer.getPhone());
        attendeeList.add(attendee);
        
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!editing){
            int apptID = DBAppointment.getAllAppointments().get(DBAppointment.getAllAppointments().size() - 1).getAppointmentID() + 1;
            thisAppointment = new Appointment(apptID);
            thisAppointment.setUserID(Session.getCurrentUser().getUserID());
            idField.setText(Integer.toString(apptID));
            
        }
        attendeeList.add(new Attendee(Session.getCurrentUser().getUserName()));
        initChoiceBoxes();
        initComboBoxes();
        initTableView();
    }

}
