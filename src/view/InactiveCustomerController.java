package view;

import DOA.DBAppointment;
import DOA.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class InactiveCustomerController implements Initializable {

    //TableView
    @FXML
    private TableView<Customer> table;


    //TableColumns
    @FXML
    private TableColumn<Customer, Integer> iDColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;


    public ObservableList<Customer> getInactiveCustomers() {
        ObservableList<Customer> allCustomers = DBCustomer.getAllCustomers();
        ObservableList<Customer> inactiveCustomers = FXCollections.observableArrayList();

        for (Customer customer : allCustomers) {
            Boolean upcomingAppointments = false;
            ObservableList<Appointment> customerAppointments = DBAppointment.getCustomerAppointments(customer.getCustID());
            if (!customerAppointments.isEmpty()) {
                for (Appointment appointment : customerAppointments) {
                    if (appointment.getStart().isAfter(LocalDateTime.now())) {
                        upcomingAppointments = true;
                    }
                }
            }
            if (!upcomingAppointments) {
                inactiveCustomers.add(customer);
            }
        }

        return inactiveCustomers;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iDColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("custID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));

        table.setItems(getInactiveCustomers());
    }


}
