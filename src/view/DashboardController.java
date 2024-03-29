package view;

import DOA.DBAppointment;
import DOA.DBCustomer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.StrokeLineCap;
import model.Appointment;
import model.Customer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utility.Alerts;
import utility.Sort;
import utility.TimeInterface;
import utility.ToggleInterface;
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
    private TableColumn<Appointment, String> appDateCol;
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
    private Button deleteAppButton;
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
    private Button deleteCustButton;
    @FXML
    private Tab custTab;

    //Date Range Controller
    @FXML
    private AnchorPane dateAnchor;
    @FXML
    private Button dateLeft;
    @FXML
    private Button dateRight;
    @FXML
    private Label dateRangeLabel;
    private LocalDateTime currentWeekStart;
    private Month currentMonth;
    private int year;

    //File locations
    private String apptWindow = "/view/Appointment.fxml";
    private String custWindow = "/view/Customer.fxml";
    private String reportWindow = "/view/Reports.fxml";

    private final WindowInterface loader = location -> {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(location));
        return loader;
    };

    private final TimeInterface now = () -> {
        return LocalDateTime.now();
    };

    private final ToggleInterface radio = radioButton -> {
        if (sortToggle.getSelectedToggle().equals(radioButton)) {
            return true;
        } else {
            return false;
        }
    };

    public void launchReportsWindow() throws IOException {
        WindowUtility.newWindow(loader.getLoader(reportWindow), "Reports");
    }

    public void launchAppointmentWindow() throws IOException {
        WindowUtility.newWindowWait(loader.getLoader(apptWindow), "Appointment");
        appointmentView.setItems(DBAppointment.getUserAppointments());
    }

    public void editAppointment() throws IOException {
        if (!appointmentView.getSelectionModel().getSelectedItems().isEmpty()) {
            FXMLLoader appointmentLoader = loader.getLoader(apptWindow);
            Parent parent = appointmentLoader.load();

            AppointmentController controller = appointmentLoader.getController();
            controller.editAppointment(appointmentView.getSelectionModel().getSelectedItem());

            Scene modifyAppointmentScene = new Scene(parent);
            Stage stage = new Stage();

            stage.setScene(modifyAppointmentScene);
            stage.showAndWait();
            appointmentView.setItems(DBAppointment.getUserAppointments());
        } else {
            Alerts.displayInputAlert("Please Select an Appointment");
        }
    }

    public void launchCustomerWindow() throws IOException {
        WindowUtility.newWindowWait(loader.getLoader(custWindow), "Customer");
        customerView.setItems(DBCustomer.getAllCustomers());

    }

    public void editCustomer() throws IOException {
        if (!customerView.getSelectionModel().getSelectedItems().isEmpty()) {
            FXMLLoader customerLoader = loader.getLoader(custWindow);
            Parent parent = customerLoader.load();

            CustomerController controller = customerLoader.getController();
            controller.editCustomer(customerView.getSelectionModel().getSelectedItem());

            Scene modifyCustomerScene = new Scene(parent);
            Stage stage = new Stage();

            stage.setScene(modifyCustomerScene);
            stage.showAndWait();
            customerView.setItems(DBCustomer.getAllCustomers());
        } else {
            Alerts.displayInputAlert("Please select a customer");
        }
    }

    public void toggleSwitch() {

        if (radio.isSelected(allRadio)) {
            appointmentView.setItems(DBAppointment.getUserAppointments());
            dateAnchor.visibleProperty().set(false);
        }
        if (radio.isSelected(weekRadio)) {
            dateAnchor.visibleProperty().set(true);
            dateRangeLabel.setText(Sort.getWeek(now.getTime()));
            currentWeekStart = Sort.getWeekStart(now.getTime());
            appointmentView.setItems(Sort.sortByWeek(now.getTime()));
        }
        if (radio.isSelected(monthRadio)) {
            currentMonth = now.getTime().getMonth();
            year = now.getTime().getYear();
            dateAnchor.visibleProperty().set(true);
            dateRangeLabel.setText(currentMonth.toString() + " " + year);
            appointmentView.setItems(Sort.sortByMonth(currentMonth, year));
        }

    }

    public void next() {
        if (radio.isSelected(weekRadio)) {
            nextWeek();
        }

        if (radio.isSelected(monthRadio)) {
            nextMonth();
        }
    }

    public void last() {
        if (radio.isSelected(weekRadio)) {
            lastWeek();
        }

        if (radio.isSelected(monthRadio)) {
            lastMonth();
        }
    }

    public void nextWeek() {
        LocalDateTime weekStart = currentWeekStart.plusDays(8);
        dateRangeLabel.setText(Sort.getWeek(weekStart));
        currentWeekStart = weekStart;
        appointmentView.setItems(Sort.sortByWeek(weekStart));
    }

    public void nextMonth() {
        if (currentMonth.equals(Month.DECEMBER)) {
            year++;
        }
        currentMonth = currentMonth.plus(1);
        dateRangeLabel.setText(currentMonth.toString() + " " + year);
        appointmentView.setItems(Sort.sortByMonth(currentMonth, year));
    }

    public void lastWeek() {
        LocalDateTime weekStart = currentWeekStart.minusDays(6);
        dateRangeLabel.setText(Sort.getWeek(weekStart));
        currentWeekStart = weekStart;
        appointmentView.setItems(Sort.sortByWeek(weekStart));
    }

    public void lastMonth() {
        if (currentMonth.equals(Month.JANUARY)) {
            year--;
        }
        currentMonth = currentMonth.minus(1);
        dateRangeLabel.setText(currentMonth.toString() + " " + year);
        appointmentView.setItems(Sort.sortByMonth(currentMonth, year));
    }

    public void deleteAppointment() {
        String header = "Are you sure you want to delete this Appointment?";
        String body = "This cannot be undone.";
        ObservableList<Appointment> deletedAppointment = FXCollections.observableArrayList();
        if (!appointmentView.getSelectionModel().getSelectedItems().isEmpty()) {
            if (Alerts.displayValidationAlert(header, body)) {
                Appointment appointmentSelected = appointmentView.getSelectionModel().getSelectedItem();
                deletedAppointment.add(appointmentSelected);
                DBAppointment.delete(appointmentSelected);
                Alerts.displayDeletedAppointments(deletedAppointment);
                appointmentView.setItems(DBAppointment.getUserAppointments());
            }
        } else {
            Alerts.displayInputAlert("Please Select an Appointment");
        }


    }

    public void deleteCustomer() {
        String header = "Are you sure you want to delete this customer?";
        String body = "All appointments associated with" +
                " this customer will be deleted. This cannot be undone.";

        if (!customerView.getSelectionModel().getSelectedItems().isEmpty()) {
            if (Alerts.displayValidationAlert(header, body)) {
                Customer customerSelected = customerView.getSelectionModel().getSelectedItem();
                ObservableList<Appointment> customerAppointments = DBAppointment.getCustomerAppointments(customerSelected.getCustID());
                for (Appointment appointment : customerAppointments) {
                    DBAppointment.delete(appointment);
                }
                DBCustomer.delete(customerSelected);
                Alerts.displayDeletedAppointments(customerAppointments);
            }
            customerView.setItems(DBCustomer.getAllCustomers());
            appointmentView.setItems(DBAppointment.getUserAppointments());
        } else {
            Alerts.displayInputAlert("Please select a customer");
        }
    }

    public static void checkUpcomingAppointments() {
        ObservableList<Appointment> appointments = DBAppointment.getUserAppointments();
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime currentPlus15Minutes = current.plusMinutes(15);
        Appointment upcomingAppointment = null;
        Boolean upcomingAppt = false;
        for (Appointment appointment : appointments) {
            LocalDateTime appointmentTime = appointment.getStart();
            if (appointmentTime.isAfter(current) && appointmentTime.isBefore(currentPlus15Minutes)) {
                upcomingAppointment = appointment;
                upcomingAppt = true;
                break;
            }
        }

        if (upcomingAppt) {
            Alerts.upcomingAppointmentAlert(upcomingAppointment);
        } else {
            Alerts.noUpcomingAppointmentAlert();
        }
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

        appointmentView.setItems(DBAppointment.getUserAppointments());

        sortToggle = new ToggleGroup();
        allRadio.setToggleGroup(sortToggle);
        weekRadio.setToggleGroup(sortToggle);
        monthRadio.setToggleGroup(sortToggle);

        checkUpcomingAppointments();

    }

}
