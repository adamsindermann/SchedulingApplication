package view;

import DOA.DBAppointment;
import DOA.DBContact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;

import java.net.URL;
import java.util.ResourceBundle;


public class ContactScheduleController implements Initializable {

    //Contact Combo Box
    @FXML
    private ComboBox contactPicker;

    //Table View
    @FXML
    private TableView table;

    //TableColumns
    @FXML
    private TableColumn apptIDColumn;
    @FXML
    private TableColumn dateColumn;
    @FXML
    private TableColumn startTimeColumn;
    @FXML
    private TableColumn endTimeColumn;
    @FXML
    private TableColumn titleColumn;
    @FXML
    private TableColumn typeColumn;
    @FXML
    private TableColumn custIDColumn;
    @FXML
    private TableColumn descriptionColumn;


    public void populateContactBox() {
        ObservableList<Contact> allContacts = DBContact.getAllContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contact contact : allContacts) {
            contactNames.add(contact.getName());
        }
        contactPicker.setItems(contactNames);
    }


    public void populateTableView() {
        if (!contactPicker.getSelectionModel().isEmpty()) {
            String contactName = contactPicker.getSelectionModel().getSelectedItem().toString();
            int contactID = DBContact.getContactID(contactName);
            ObservableList<Appointment> contactAppointments = DBAppointment.getContactAppointments(contactID);
            table.setItems(contactAppointments);
        }

    }


    public void initialize(URL url, ResourceBundle rb) {
        populateContactBox();
        apptIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("dateString"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startString"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("endString"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        custIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));


    }

}
