/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DOA.DBAppointment;
import DOA.DBCustomer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;
import java.sql.Timestamp;
import javafx.fxml.FXMLLoader;
import utility.WindowInterface;
import utility.WindowUtility;

/**
 * FXML Controller class
 *
 * @author Adam Sindermann
 */
public class DashboardController implements Initializable {

    //Appointment TableView
    @FXML
    private TableView<Appointment> appointmentView;
    @FXML
    private TableColumn<Appointment, Integer> appIDCol;
    @FXML
    private TableColumn<Appointment, String> appTitleCol;
    @FXML
    private TableColumn<Appointment, String> appTypeCol;
    @FXML
    private TableColumn<Appointment, String> appDescCol;
    @FXML
    private TableColumn<Appointment, String> appLocationCol;
    @FXML
    private TableColumn<Appointment, Integer> appContactCol;
    @FXML
    private TableColumn<Appointment, Timestamp> appStartCol;
    @FXML
    private TableColumn<Appointment, Timestamp> appEndCol;
    @FXML
    private TableColumn<Appointment, Integer> appCustIDCol;

    //Appointment buttons
    @FXML
    private Button newAppButton;
    @FXML
    private Button editAppButton;
    @FXML
    private Tab appTab;

    //Appointment Radio Buttons
    @FXML
    private RadioButton allRadio;
    @FXML
    private RadioButton weekRadio;
    @FXML
    private RadioButton monthRadio;

    //Customer TableView
    @FXML
    private TableView<Customer> customerView;
    @FXML
    private TableColumn<Customer, Integer> custIDCol;
    @FXML
    private TableColumn<Customer, String> custNameCol;
    @FXML
    private TableColumn<Customer, String> custAddressCol;
    @FXML
    private TableColumn<Customer, String> custZipCol;
    @FXML
    private TableColumn<Customer, String> custStateCol;
    @FXML
    private TableColumn<Customer, String> custCountryCol;

    //Customer buttons
    @FXML
    private Button newCustButton;
    @FXML
    private Button editCustButton;
    @FXML
    private Tab custTab;

    //File locations
    private String apptWindow = "/view/Appointment.fxml";

    private final WindowInterface loader = location -> {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(location));
        return loader;
    };

    public void launchAppointmentWindow() throws IOException {
        WindowUtility.newWindow(loader.getLoader(apptWindow), "Appointment");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        custIDCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("custID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        custZipCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        custStateCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("division"));
        custCountryCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("country"));

        customerView.setItems(DBCustomer.getAllCustomers());

        appIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appDescCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        appContactCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
        appCustIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));

        appointmentView.setItems(DBAppointment.getAllAppointments());
    }

}
