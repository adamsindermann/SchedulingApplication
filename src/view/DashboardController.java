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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import utility.Sort;
import utility.TimeInterface;
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
    @FXML private TableColumn<Appointment, String> appDateCol;
    @FXML
    private TableColumn<Appointment, String> appStartCol;
    @FXML
    private TableColumn<Appointment, String> appEndCol;
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
    private ToggleGroup sortToggle;
    
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
    
    //Date Range Controller
    @FXML private AnchorPane dateAnchor;
    @FXML private Button dateLeft;
    @FXML private Button dateRight;
    @FXML private Label dateRangeLabel;
    private LocalDateTime currentWeekStart;
    
    
    


    //File locations
    private String apptWindow = "/view/Appointment.fxml";

    private final WindowInterface loader = location -> {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(location));
        return loader;
    };
    
    private final TimeInterface now = () -> {
        return LocalDateTime.now();
    };

    public void launchAppointmentWindow() throws IOException {
        WindowUtility.newWindowWait(loader.getLoader(apptWindow), "Appointment");
        appointmentView.setItems(DBAppointment.getAllAppointments());
    }
    
    public void toggleSwitch(){

        if(sortToggle.getSelectedToggle().equals(allRadio)){
            appointmentView.setItems(DBAppointment.getAllAppointments());
            dateAnchor.visibleProperty().set(false);
        }
        if(sortToggle.getSelectedToggle().equals(weekRadio)){
            dateAnchor.visibleProperty().set(true);
            dateRangeLabel.setText(Sort.getWeek(now.getTime()));
            currentWeekStart = Sort.getWeekStart(now.getTime());
            appointmentView.setItems(Sort.sortByWeek(now.getTime()));
        }
        
        
    }
    
    public void nextWeek(){
        LocalDateTime weekStart = currentWeekStart.plusDays(8);
        dateRangeLabel.setText(Sort.getWeek(weekStart));
        currentWeekStart = weekStart;
        appointmentView.setItems(Sort.sortByWeek(weekStart));
    }
    
    public void lastWeek(){
        LocalDateTime weekStart = currentWeekStart.minusDays(6);
        dateRangeLabel.setText(Sort.getWeek(weekStart));
        currentWeekStart = weekStart;
        appointmentView.setItems(Sort.sortByWeek(weekStart));
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
        appDateCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("dateString"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startString"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("endString"));
        appCustIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));

        appointmentView.setItems(DBAppointment.getAllAppointments());
        
        sortToggle = new ToggleGroup();
        allRadio.setToggleGroup(sortToggle);
        weekRadio.setToggleGroup(sortToggle);
        monthRadio.setToggleGroup(sortToggle);

    }

}
