/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import utility.WindowInterface;
import utility.WindowUtility;

/**
 * FXML Controller class
 *
 * @author Adam Sindermann
 */
public class ReportsController implements Initializable {


    private final WindowInterface loader = location -> {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(location));
        return loader;
    };

    //Paths
    private final String apptTotals = "/view/TotalReport.fxml";
    private final String clientSchedule = "/view/ContactSchedule.fxml";
    private final String inactiveCustomers = "/view/InactiveCustomers.fxml";

    public void launchApptTotals() throws IOException {
        WindowUtility.newWindow(loader.getLoader(apptTotals), "Appointment Totals");
    }

    public void launchClientSchedule() throws IOException {
        WindowUtility.newWindow(loader.getLoader(clientSchedule), "Client Schedules");
    }

    public void launchInactiveCustomers() throws IOException {
        WindowUtility.newWindow(loader.getLoader(inactiveCustomers), "Inactive Customers");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
